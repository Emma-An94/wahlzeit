package org.wahlzeit.model.flower;

import org.wahlzeit.main.ServiceMain;
import org.wahlzeit.model.*;
import org.wahlzeit.services.EmailAddress;
import org.wahlzeit.services.Language;
import org.wahlzeit.services.Persistent;
import org.wahlzeit.services.SysLog;
import org.wahlzeit.utils.StringUtil;

import java.io.File;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class FlowerPhoto extends Photo {
    private Location location;
    private int location_id;

    public Location getLocation(){
        return this.location;
    }

    /**
     *
     */
    public static final String IMAGE = "image";
    public static final String THUMB = "thumb";
    public static final String LINK = "link";
    public static final String PRAISE = "praise";
    public static final String NO_VOTES = "noVotes";
    public static final String CAPTION = "caption";
    public static final String DESCRIPTION = "description";
    public static final String KEYWORDS = "keywords";

    public static final String TAGS = "tags";

    public static final String STATUS = "status";
    public static final String IS_INVISIBLE = "isInvisible";
    public static final String UPLOADED_ON = "uploadedOn";

    /**
     *
     */
    public static final int MAX_PHOTO_WIDTH = 420;
    public static final int MAX_PHOTO_HEIGHT = 600;
    public static final int MAX_THUMB_PHOTO_WIDTH = 105;
    public static final int MAX_THUMB_PHOTO_HEIGHT = 150;

    /**
     *
     */
    protected PhotoId id = null;

    /**
     *
     */
    protected int ownerId = 0;
    protected String ownerName;

    /**
     *
     */
    protected boolean ownerNotifyAboutPraise = false;
    protected EmailAddress ownerEmailAddress = EmailAddress.EMPTY;
    protected Language ownerLanguage = Language.ENGLISH;
    protected URL ownerHomePage;

    /**
     *
     */
    protected int width;
    protected int height;
    protected PhotoSize maxPhotoSize = PhotoSize.MEDIUM; // derived

    /**
     *
     */
    protected Tags tags = Tags.EMPTY_TAGS;

    /**
     *
     */
    protected PhotoStatus status = PhotoStatus.VISIBLE;

    /**
     *
     */
    protected int praiseSum = 10;
    protected int noVotes = 1;

    /**
     *
     */
    protected long creationTime = System.currentTimeMillis();

    /**
     *
     */
    private Flower flower = null;

    public FlowerPhoto() {
        id = PhotoId.getNextId();
        incWriteCount();
        this.location = new Location();
    }

    /**
     *
     * @methodtype constructor
     */
    public FlowerPhoto(Location location){
        if (location == null){
            throw new IllegalArgumentException("Object Location should not be null");
        }
        id = PhotoId.getNextId();
        incWriteCount();
        this.location = location;
    }

    /**
     *
     * @methodtype constructor
     */
    public FlowerPhoto(PhotoId myId) {
        if (myId == null){
            throw new IllegalArgumentException("Object PhotoId should not be null");
        }
        id = myId;

        incWriteCount();
        this.location = new Location();
    }

    /**
     *
     * @methodtype constructor
     */
    public FlowerPhoto(ResultSet rset) throws SQLException {
        if (rset == null){
            throw new IllegalArgumentException("Object ResultSet should not be null");
        }
        readFrom(rset);
        this.location = new Location();
    }

    /**
     *
     * @methodtype constructor
     */
    public FlowerPhoto(Flower flower){
        if (flower == null){
            throw new IllegalArgumentException("Object Flower should not be null");
        }
        id = PhotoId.getNextId();
        incWriteCount();
        this.location = new Location();
        this.flower = flower;
    }

    /**
     *
     * @methodtype get
     */
    public String getIdAsString() {
        return String.valueOf(id.asInt());
    }

    /**
     *
     */
    public void readFrom(ResultSet rset) throws SQLException {
        id = PhotoId.getIdFromInt(rset.getInt("id"));

        ownerId = rset.getInt("owner_id");
        ownerName = rset.getString("owner_name");

        ownerNotifyAboutPraise = rset.getBoolean("owner_notify_about_praise");
        ownerEmailAddress = EmailAddress.getFromString(rset.getString("owner_email_address"));
        ownerLanguage = Language.getFromInt(rset.getInt("owner_language"));
        ownerHomePage = StringUtil.asUrl(rset.getString("owner_home_page"));

        width = rset.getInt("width");
        height = rset.getInt("height");

        tags = new Tags(rset.getString("tags"));

        status = PhotoStatus.getFromInt(rset.getInt("status"));
        praiseSum = rset.getInt("praise_sum");
        noVotes = rset.getInt("no_votes");

        creationTime = rset.getLong("creation_time");

        maxPhotoSize = PhotoSize.getFromWidthHeight(width, height);
        location_id = rset.getInt("location_id");
    }

    /**
     *
     */
    public void writeOn(ResultSet rset) throws SQLException {
        rset.updateInt("id", id.asInt());
        rset.updateInt("owner_id", ownerId);
        rset.updateString("owner_name", ownerName);
        rset.updateBoolean("owner_notify_about_praise", ownerNotifyAboutPraise);
        rset.updateString("owner_email_address", ownerEmailAddress.asString());
        rset.updateInt("owner_language", ownerLanguage.asInt());
        rset.updateString("owner_home_page", ownerHomePage.toString());
        rset.updateInt("width", width);
        rset.updateInt("height", height);
        rset.updateString("tags", tags.asString());
        rset.updateInt("status", status.asInt());
        rset.updateInt("praise_sum", praiseSum);
        rset.updateInt("no_votes", noVotes);
        rset.updateLong("creation_time", creationTime);
        rset.updateInt("location_id", location_id);
    }

    /**
     *
     */
    public void writeId(PreparedStatement stmt, int pos) throws SQLException {
        stmt.setInt(pos, id.asInt());
    }

    /**
     *
     * @methodtype get
     */
    public PhotoId getId() {
        return id;
    }

    /**
     *
     * @methodtype get
     */
    public int getOwnerId() {
        return ownerId;
    }

    /**
     *
     * @methodtype set
     */
    public void setOwnerId(int newId) {
        ownerId = newId;
        incWriteCount();
    }

    /**
     *
     * @methodtype get
     */
    public String getOwnerName() {
        return ownerName;
    }

    /**
     *
     * @methodtype set
     */
    public void setOwnerName(String newName) {
        ownerName = newName;
        incWriteCount();
    }

    /**
     *
     * @methodtype get
     */
    public String getSummary(ModelConfig cfg) {
        return cfg.asPhotoSummary(ownerName);
    }

    /**
     *
     * @methodtype get
     */
    public String getCaption(ModelConfig cfg) {
        return cfg.asPhotoCaption(ownerName, ownerHomePage);
    }

    /**
     *
     * @methodtype get
     */
    public boolean getOwnerNotifyAboutPraise() {
        return ownerNotifyAboutPraise;
    }

    /**
     *
     * @methodtype set
     */
    public void setOwnerNotifyAboutPraise(boolean newNotifyAboutPraise) {
        ownerNotifyAboutPraise = newNotifyAboutPraise;
        incWriteCount();
    }

    /**
     *
     * @methodtype get
     */
    public EmailAddress getOwnerEmailAddress() {
        return ownerEmailAddress;
    }

    /**
     *
     * @methodtype set
     */
    public void setOwnerEmailAddress(EmailAddress newEmailAddress) {
        ownerEmailAddress = newEmailAddress;
        incWriteCount();
    }

    /**
     *
     */
    public Language getOwnerLanguage() {
        return ownerLanguage;
    }

    /**
     *
     */
    public void setOwnerLanguage(Language newLanguage) {
        ownerLanguage = newLanguage;
        incWriteCount();
    }

    /**
     *
     * @methodtype get
     */
    public URL getOwnerHomePage() {
        return ownerHomePage;
    }

    /**
     *
     * @methodtype set
     */
    public void setOwnerHomePage(URL newHomePage) {
        ownerHomePage = newHomePage;
        incWriteCount();
    }

    /**
     *
     * @methodtype boolean-query
     */
    public boolean hasSameOwner(Photo photo) {
        return photo.getOwnerEmailAddress().equals(ownerEmailAddress);
    }

    /**
     *
     * @methodtype boolean-query
     */
    public boolean isWiderThanHigher() {
        return (height * MAX_PHOTO_WIDTH) < (width * MAX_PHOTO_HEIGHT);
    }

    /**
     *
     * @methodtype get
     */
    public int getWidth() {
        return width;
    }

    /**
     *
     * @methodtype get
     */
    public int getHeight() {
        return height;
    }

    /**
     *
     * @methodtype get
     */
    public int getThumbWidth() {
        return isWiderThanHigher() ? MAX_THUMB_PHOTO_WIDTH : (width * MAX_THUMB_PHOTO_HEIGHT / height);
    }

    /**
     *
     * @methodtype get
     */
    public int getThumbHeight() {
        return isWiderThanHigher() ? (height * MAX_THUMB_PHOTO_WIDTH / width) : MAX_THUMB_PHOTO_HEIGHT;
    }

    /**
     *
     * @methodtype set
     */
    public void setWidthAndHeight(int newWidth, int newHeight) {
        width = newWidth;
        height = newHeight;

        maxPhotoSize = PhotoSize.getFromWidthHeight(width, height);

        incWriteCount();
    }

    /**
     * Can this photo satisfy provided photo size?
     *
     * @methodtype boolean-query
     */
    public boolean hasPhotoSize(PhotoSize size) {
        return maxPhotoSize.asInt() >= size.asInt();
    }

    /**
     *
     * @methodtype get
     */
    public PhotoSize getMaxPhotoSize() {
        return maxPhotoSize;
    }

    /**
     *
     * @methodtype get
     */
    public double getPraise() {
        return (double) praiseSum / noVotes;
    }

    /**
     *
     * @methodtype get
     */
    public String getPraiseAsString(ModelConfig cfg) {
        return cfg.asPraiseString(getPraise());
    }

    /**
     *
     */
    public void addToPraise(int value) {
        praiseSum += value;
        noVotes += 1;
        incWriteCount();
    }

    /**
     *
     * @methodtype boolean-query
     */
    public boolean isVisible() {
        return status.isDisplayable();
    }

    /**
     *
     * @methodtype get
     */
    public PhotoStatus getStatus() {
        return status;
    }

    /**
     *
     * @methodtype set
     */
    public void setStatus(PhotoStatus newStatus) {
        status = newStatus;
        incWriteCount();
    }

    /**
     *
     * @methodtype boolean-query
     */
    public boolean hasTag(String tag) {
        return tags.hasTag(tag);
    }

    /**
     *
     * @methodtype get
     */
    public Tags getTags() {
        return tags;
    }

    /**
     *
     * @methodtype set
     */
    public void setTags(Tags newTags) {
        tags = newTags;
        incWriteCount();
    }

    /**
     *
     * @methodtype get
     */
    public long getCreationTime() {
        return creationTime;
    }

}
