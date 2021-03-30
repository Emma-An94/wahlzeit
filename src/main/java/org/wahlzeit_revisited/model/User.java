package org.wahlzeit_revisited.model;

import org.wahlzeit.model.Gender;
import org.wahlzeit.model.PhotoId;
import org.wahlzeit.model.PhotoManager;
import org.wahlzeit.model.UserStatus;
import org.wahlzeit.services.Language;
import org.wahlzeit.utils.StringUtil;
import org.wahlzeit_revisited.auth.AccessRights;
import org.wahlzeit_revisited.repository.Persistent;
import org.wahlzeit_revisited.utils.EmailAddress;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User extends Client implements Persistent {

    private Long id;
    private String name;
    private String password;

    private long creationTime;

    /*
     * constructor
     */

    public User(ResultSet resultSet) throws SQLException {
        readFrom(resultSet);
    }

    User(String name, String email, String password) {
        this.name = name;
        this.emailAddress = EmailAddress.getFromString(email);
        this.password = password;
        creationTime = System.currentTimeMillis();
    }

    User(Long id, Long creationTime, String name, String email, String password, AccessRights rights) {
        this.id = id;
        this.creationTime = creationTime;
        this.name = name;
        this.emailAddress = EmailAddress.getFromString(email);
        this.password = password;
        this.rights = rights;
    }

    /*
     * Persistent contract
     */

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public void readFrom(ResultSet rset) throws SQLException {
        id = rset.getLong("id");
        name = rset.getString("name");
        emailAddress = EmailAddress.getFromString(rset.getString("email_address"));
        password = rset.getString("password");
        rights = AccessRights.getFromInt(rset.getInt("rights"));
        creationTime = rset.getLong("creation_time");
    }

    @Override
    public void writeOn(ResultSet rset) throws SQLException {
        rset.updateLong("id", id);
        rset.updateString("name", name);
        rset.updateString("email_address", (emailAddress == null) ? "" : emailAddress.asString());
        rset.updateString("password", password);
        rset.updateInt("rights", rights.asInt());
        rset.updateLong("creation_time", creationTime);
    }

    /*
     * getter/setter
     */

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return emailAddress.asString();
    }

    public void setEmail(String email) {
        this.emailAddress = EmailAddress.getFromString(email);
    }

    public long getCreationTime() {
        return creationTime;
    }
}