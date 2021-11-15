package org.wahlzeit.model.flower;

import org.wahlzeit.main.ServiceMain;
import org.wahlzeit.model.*;
import org.wahlzeit.services.Persistent;
import org.wahlzeit.services.SysLog;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class FlowerPhotoManager extends PhotoManager {
    /**
     *
     */
    protected static final FlowerPhotoManager instance = new FlowerPhotoManager();

    /**
     * In-memory cache for photos
     */
    protected Map<PhotoId, FlowerPhoto> photoCache = new HashMap<PhotoId, FlowerPhoto>();

    /**
     *
     */
    protected PhotoTagCollector photoTagCollector = null;

    /**
     *
     */
    public static final FlowerPhotoManager getFlrInstance() {
        return instance;
    }

    /**
     *
     */
    public static final boolean hasFlowerPhoto(String id) {
        return hasFlowerPhoto(PhotoId.getIdFromString(id));
    }

    /**
     *
     */
    public static final boolean hasFlowerPhoto(PhotoId id) {
        return getFlowerPhoto(id) != null;
    }

    /**
     *
     */
    public static final Photo getFlowerPhoto(String id) {
        return getFlowerPhoto(PhotoId.getIdFromString(id));
    }

    /**
     *
     */
    public static final Photo getFlowerPhoto(PhotoId id) {
        return instance.getPhotoFromId(id);
    }

    /**
     *
     */
    public FlowerPhotoManager() {
        photoTagCollector = PhotoFactory.getInstance().createPhotoTagCollector();
    }

    /**
     * @methodtype boolean-query
     * @methodproperties primitive
     */
    protected boolean doHasPhoto(PhotoId id) {
        return photoCache.containsKey(id);
    }

    /**
     *
     */
    public FlowerPhoto getPhotoFromId(PhotoId id) {
        if (id.isNullId()) {
            return null;
        }

        FlowerPhoto result = doGetPhotoFromId(id);

        if (result == null) {
            try {
                PreparedStatement stmt = getReadingStatement("SELECT * FROM photos WHERE id = ?");
                result = (FlowerPhoto) readObject(stmt, id.asInt());
            } catch (SQLException sex) {
                SysLog.logThrowable(sex);
            }
            if (result != null) {
                doAddPhoto(result);
            }
        }

        return result;
    }

    /**
     * @methodtype get
     * @methodproperties primitive
     */
    protected FlowerPhoto doGetPhotoFromId(PhotoId id) {
        return photoCache.get(id);
    }

    /**
     *
     */
    protected FlowerPhoto createObject(ResultSet rset) throws SQLException {
        return FlowerPhotoFactory.getInstance().createPhoto(rset);
    }

    /**
     * @methodtype command
     *
     * Load all persisted photos. Executed when Wahlzeit is restarted.
     */
    public void addPhoto(FlowerPhoto photo) {
        PhotoId id = photo.getId();
        assertIsNewPhoto(id);
        doAddPhoto(photo);

        try {
            PreparedStatement stmt = getReadingStatement("INSERT INTO photos(id) VALUES(?)");
            createObject(photo, stmt, id.asInt());
            ServiceMain.getInstance().saveGlobals();
        } catch (SQLException sex) {
            SysLog.logThrowable(sex);
        }
    }

    /**
     * @methodtype command
     * @methodproperties primitive
     */
    protected void doAddPhoto(FlowerPhoto myPhoto) {
        photoCache.put(myPhoto.getId(), myPhoto);
    }

    /**
     * @methodtype command
     */
    public void loadFlowerPhotos(Collection<FlowerPhoto> result) {
        try {
            PreparedStatement stmt = getReadingStatement("SELECT * FROM photos");
            readObjects(result, stmt);
            for (Iterator<FlowerPhoto> i = result.iterator(); i.hasNext(); ) {
                FlowerPhoto photo = i.next();
                if (!doHasPhoto(photo.getId())) {
                    doAddPhoto(photo);
                } else {
                    SysLog.logSysInfo("photo", photo.getId().asString(), "photo had already been loaded");
                }
            }
        } catch (SQLException sex) {
            SysLog.logThrowable(sex);
        }

        SysLog.logSysInfo("loaded all photos");
    }

    /**
     *
     */
    public void savePhoto(Photo photo) {
        try {
            PreparedStatement stmt = getUpdatingStatement("SELECT * FROM photos WHERE id = ?");
            updateObject(photo, stmt);
        } catch (SQLException sex) {
            SysLog.logThrowable(sex);
        }
    }

    /**
     *
     */
    public void savePhotos() {
        try {
            PreparedStatement stmt = getUpdatingStatement("SELECT * FROM photos WHERE id = ?");
            updateObjects(photoCache.values(), stmt);
        } catch (SQLException sex) {
            SysLog.logThrowable(sex);
        }
    }

    /**
     * @methodtype command
     *
     * Persists all available sizes of the Photo. If one size exceeds the limit of the persistence layer, e.g. > 1MB for
     * the Datastore, it is simply not persisted.
     */
    public Set<Photo> findPhotosByOwner(String ownerName) {
        Set<Photo> result = new HashSet<Photo>();
        try {
            PreparedStatement stmt = getReadingStatement("SELECT * FROM photos WHERE owner_name = ?");
            readObjects(result, stmt, ownerName);
        } catch (SQLException sex) {
            SysLog.logThrowable(sex);
        }

        for (Iterator<Photo> i = result.iterator(); i.hasNext(); ) {
            doAddPhoto(i.next());
        }

        return result;
    }

    /**
     *
     */
    public FlowerPhoto getVisiblePhoto(PhotoFilter filter) {
        FlowerPhoto result = getPhotoFromFilter(filter);

        if(result == null) {
            java.util.List<PhotoId> list = getFilteredPhotoIds(filter);
            filter.setDisplayablePhotoIds(list);
            result = getPhotoFromFilter(filter);
        }

        return result;
    }

    /**
     *
     */
    protected FlowerPhoto getPhotoFromFilter(PhotoFilter filter) {
        PhotoId id = filter.getRandomDisplayablePhotoId();
        FlowerPhoto result = getPhotoFromId(id);
        while((result != null) && !result.isVisible()) {
            id = filter.getRandomDisplayablePhotoId();
            result = getPhotoFromId(id);
            if ((result != null) && !result.isVisible()) {
                filter.addProcessedPhoto(result);
            }
        }

        return result;
    }

    /**
     *
     */
    protected java.util.List<PhotoId> getFilteredPhotoIds(PhotoFilter filter) {
        java.util.List<PhotoId> result = new LinkedList<PhotoId>();

        try {
            java.util.List<String> filterConditions = filter.getFilterConditions();

            int noFilterConditions = filterConditions.size();
            PreparedStatement stmt = getUpdatingStatementFromConditions(noFilterConditions);
            for (int i = 0; i < noFilterConditions; i++) {
                stmt.setString(i + 1, filterConditions.get(i));
            }

            SysLog.logQuery(stmt);
            ResultSet rset = stmt.executeQuery();

            if (noFilterConditions == 0) {
                noFilterConditions++;
            }

            int[] ids = new int[PhotoId.getCurrentIdAsInt() + 1];
            while(rset.next()) {
                int id = rset.getInt("photo_id");
                if (++ids[id] == noFilterConditions) {
                    PhotoId photoId = PhotoId.getIdFromInt(id);
                    if (!filter.isProcessedPhotoId(photoId)) {
                        result.add(photoId);
                    }
                }
            }
        } catch (SQLException sex) {
            SysLog.logThrowable(sex);
        }

        return result;
    }

    /**
     *
     */
    protected PreparedStatement getUpdatingStatementFromConditions(int no) throws SQLException {
        String query = "SELECT * FROM tags";
        if (no > 0) {
            query += " WHERE";
        }

        for (int i = 0; i < no; i++) {
            if (i > 0) {
                query += " OR";
            }
            query += " (tag = ?)";
        }

        return getUpdatingStatement(query);
    }

    /**
     *
     */
    protected void updateDependents(Persistent obj) throws SQLException {
        FlowerPhoto photo = (FlowerPhoto) obj;

        PreparedStatement stmt = getReadingStatement("DELETE FROM tags WHERE photo_id = ?");
        deleteObject(obj, stmt);

        stmt = getReadingStatement("INSERT INTO tags VALUES(?, ?)");
        Set<String> tags = new HashSet<String>();
        photoTagCollector.collect(tags, photo);
        for (Iterator<String> i = tags.iterator(); i.hasNext(); ) {
            String tag = i.next();
            stmt.setString(1, tag);
            stmt.setInt(2, photo.getId().asInt());
            SysLog.logQuery(stmt);
            stmt.executeUpdate();
        }
    }

    /**
     *
     */
    public FlowerPhoto createPhoto(File file) throws Exception {
        PhotoId id = PhotoId.getNextId();
        Photo result = PhotoUtil.createPhoto(file, id);
        addPhoto(result);
        return (FlowerPhoto) result;
    }

    /**
     * @methodtype assertion
     */
    protected void assertIsNewPhoto(PhotoId id) {
        if (hasFlowerPhoto(id)) {
            throw new IllegalStateException("Photo already exists!");
        }
    }
}
