package org.wahlzeit_revisited.repository;

import jakarta.inject.Inject;
import org.wahlzeit_revisited.model.Photo;
import org.wahlzeit_revisited.model.PhotoFactory;
import org.wahlzeit_revisited.model.PhotoStatus;
import org.wahlzeit_revisited.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PhotoRepository extends AbstractRepository<Photo> {

    @Inject
    public PhotoFactory factory;

    /*
     * business methods
     */

    public List<Photo> findForUser(User user) throws SQLException {
        assertPersistedObject(user);
        return findForUser(user.getId());
    }

    public List<Photo> findForUser(Long userId) throws SQLException {
        assertIsNonNullArgument(userId, "User");

        String query = String.format("SELECT * FROM %s WHERE owner_id = ?", getTableName());
        PreparedStatement stmt = getReadingStatement(query);
        stmt.setLong(1, userId);

        List<Photo> result = new ArrayList<>();
        try (ResultSet resultSet = stmt.executeQuery()) {
            if (resultSet.next()) {
                Photo photo = factory.createPersistent(resultSet);
                result.add(photo);
            }
        }

        return result;
    }

    /*
     * AbstractRepository contract
     */

    @Override
    protected String getTableName() {
        return "photos";
    }

    @Override
    protected PersistentFactory<Photo> getFactory() {
        return factory;
    }
}