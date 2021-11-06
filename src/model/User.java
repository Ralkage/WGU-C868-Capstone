package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * The User model class.
 *
 * @author Christian Lopez
 * Software Development Capstone – C868
 */
public class User {

    private static int userId;
    private static String userName;
    private static String password;
    private static LocalDateTime createDate;
    private static String createdBy;
    private static Timestamp lastUpdate;
    private static String lastUpdatedBy;

    /**
     * Instantiates a new Users.
     *
     * @param userId        the user id
     * @param password      the password
     * @param createDate    the create date
     * @param createdBy     the created by
     * @param lastUpdate    the last update
     * @param lastUpdatedBy the last updated by
     */
    public User(int userId, String password, LocalDateTime createDate, String createdBy, Timestamp lastUpdate,
                String lastUpdatedBy) {
        User.userId = userId;
        User.password = password;
        User.createDate = createDate;
        User.createdBy = createdBy;
        User.lastUpdate = lastUpdate;
    }

    /**
     * Instantiates a new Users.
     *
     * @param userId   the user id
     * @param userName the user name
     * @param password the password
     */
    public User(int userId, String userName, String password) {
        User.userId = userId;
        User.userName = userName;
        User.password = password;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public static int getUserId() {
        return userId;
    }

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public static void setUserId(int userId) {
        User.userId = userId;
    }

    /**
     * Gets user name.
     *
     * @return the user name
     */
    public static String getUserName() {
        return userName;
    }

    /**
     * Sets username.
     *
     * @param userName the username
     */
    public static void setUserName(String userName) {
        User.userName = userName;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public static String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public static void setPassword(String password) {
        User.password = password;
    }

    /**
     * Gets create date.
     *
     * @return the create date
     */
    public static LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Sets create date.
     *
     * @param createDate the create date
     */
    public static void setCreateDate(LocalDateTime createDate) {
        User.createDate = createDate;
    }

    /**
     * Gets created by.
     *
     * @return the created by
     */
    public static String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets created by.
     *
     * @param createdBy the created by
     */
    public static void setCreatedBy(String createdBy) {
        User.createdBy = createdBy;
    }

    /**
     * Gets last update.
     *
     * @return the last update
     */
    public static Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets last update.
     *
     * @param lastUpdate the last update
     */
    public static void setLastUpdate(Timestamp lastUpdate) {
        User.lastUpdate = lastUpdate;
    }

    /**
     * Gets last updated by.
     *
     * @return the last updated by
     */
    public static String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Sets last updated by.
     *
     * @param lastUpdatedBy the last updated by
     */
    public static void setLastUpdatedBy(String lastUpdatedBy) {
        User.lastUpdatedBy = lastUpdatedBy;
    }
}
