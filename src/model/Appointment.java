package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * The Appointments model class.
 *
 * @author Christian Lopez Software Development Capstone – C868
 */
public class Appointment {
    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private LocalDateTime createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int customerID;
    private int userID;
    private int contactID;
    private String contactName;
    private String customerName;
    private String userName;

    /**
     * Instantiates a new Appointment.
     *
     * @param appointmentID the appointment id
     * @param start         the start
     */
    public Appointment(int appointmentID, LocalDateTime start) {
        setAppointmentID(appointmentID);
        setStart(start);
    }

    /**
     * Instantiates a new Appointment.
     *
     * @param appointmentID the appointment id
     * @param title         the title
     * @param description   the description
     * @param location      the location
     * @param type          the type
     * @param start         the start
     * @param end           the end
     * @param createDate    the create date
     * @param createdBy     the created by
     * @param lastUpdate    the last update
     * @param lastUpdatedBy the last updated by
     * @param customerID    the customer id
     * @param userID        the user id
     * @param contactID     the contact id
     * @param contactName   the contact name
     * @param customerName  the customer name
     */
    public Appointment(int appointmentID, String title, String description, String location, String type,
                       LocalDateTime start, LocalDateTime end, LocalDateTime createDate, String createdBy,
                       Timestamp lastUpdate, String lastUpdatedBy, int customerID, int userID, int contactID,
                       String contactName, String customerName) {
        setAppointmentID(appointmentID);
        setTitle(title);
        setDescription(description);
        setLocation(location);
        setType(type);
        setStart(start);
        setEnd(end);
        setCreateDate(createDate);
        setCreatedBy(createdBy);
        setLastUpdate(lastUpdate);
        setLastUpdatedBy(lastUpdatedBy);
        setCustomerID(customerID);
        setUserID(userID);
        setContactID(contactID);
        setContactName(contactName);
        setCustomerName(customerName);
    }

    /**
     * Instantiates a new Appointment.
     *
     * @param appointmentID the appointment id
     * @param title         the title
     * @param description   the description
     * @param location      the location
     * @param type          the type
     * @param start         the start
     * @param end           the end
     * @param createdDate   the created date
     * @param createdBy     the created by
     * @param lastUpdate    the last update
     * @param lastUpdatedBy the last updated by
     * @param customerID    the customer id
     * @param userID        the user id
     * @param contactID     the contact id
     * @param contactName   the contact name
     */
    public Appointment(int appointmentID, String title, String description, String location, String type,
                       LocalDateTime start, LocalDateTime end, LocalDateTime createdDate,
                       String createdBy, Timestamp lastUpdate, String lastUpdatedBy,
                       int customerID, int userID, int contactID, String contactName) {
        setAppointmentID(appointmentID);
        setTitle(title);
        setDescription(description);
        setLocation(location);
        setType(type);
        setStart(start);
        setEnd(end);
        setCreateDate(createDate);
        setCreatedBy(createdBy);
        setLastUpdate(lastUpdate);
        setLastUpdatedBy(lastUpdatedBy);
        setCustomerID(customerID);
        setUserID(userID);
        setContactID(contactID);
        setContactName(contactName);
    }

    /**
     * Gets the appointment id.
     *
     * @return the appointment id
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * Sets the appointment id.
     *
     * @param appointmentID the appointment id
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     * Gets the appointment title.
     *
     * @return the appointment title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the appointment title.
     *
     * @param title the appointment title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the appointment description.
     *
     * @return the appointment description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the appointment description.
     *
     * @param description the appointment description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the appointment location.
     *
     * @return the appointment location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the appointment location.
     *
     * @param location the appointment location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets the appointment type.
     *
     * @return the appointment type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the appointment type.
     *
     * @param type the appointment type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the appointment start time.
     *
     * @return the appointment start time
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Sets the appointment start time.
     *
     * @param start the appointment start time
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * Gets the appointment end time.
     *
     * @return the appointment end time
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Sets the appointment end time.
     *
     * @param end the appointment end time
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /**
     * Gets the appointment create date.
     *
     * @return the appointment create date
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Sets the appointment create date.
     *
     * @param createDate the appointment create date
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * Gets the appointment created by user.
     *
     * @return the appointment created by user
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the appointment created by user.
     *
     * @param createdBy the appointment created by user
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets the appointment last update.
     *
     * @return the appointment last update
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets the appointment last update.
     *
     * @param lastUpdate the last appointment update
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Gets the appointment last updated by.
     *
     * @return the appointment last updated by
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Sets the appointment last updated by.
     *
     * @param lastUpdatedBy the appointment last updated by
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Gets the appointment customer id.
     *
     * @return the appointment customer id
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Sets the appointment customer id.
     *
     * @param customerID the appointment customer id
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Gets the appointment user id.
     *
     * @return the appointment user id
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Sets the appointment user id.
     *
     * @param userID the appointment user id
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Gets the appointment contact id.
     *
     * @return the appointment contact id
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * Sets the appointment contact id.
     *
     * @param contactID the appointment contact id
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * Gets the appointment contact name.
     *
     * @return the appointment contact name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Sets the appointment contact name.
     *
     * @param contactName the appointment contact name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Gets the appointment customer name.
     *
     * @return the appointment customer name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets the appointment customer name.
     *
     * @param customerName the appointment customer name
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Gets the appointment username.
     *
     * @return the appointment customer username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the appointment username.
     *
     * @param userName the appointment username
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
}