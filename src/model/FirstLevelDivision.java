package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * The First Level Division model class.
 *
 * @author Christian Lopez
 * Software Development Capstone – C868
 */
public class FirstLevelDivision {
    private int divisionID;
    private String division;
    private LocalDateTime createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String Contact;
    private int countryID;

    /**
     * Instantiates a new First level division.
     *
     * @param divisionID the division id
     * @param division   the division
     * @param createDate the create date
     * @param createdBy  the created by
     * @param lastUpdate the last update
     * @param Contact    the contact
     * @param countryID  the country id
     */
    public FirstLevelDivision(int divisionID, String division, LocalDateTime createDate,
                              String createdBy, Timestamp lastUpdate, String Contact, int countryID) {
        setDivisionID(divisionID);
        setDivision(division);
        setCreateDate(createDate);
        setCreatedBy(createdBy);
        setLastUpdate(lastUpdate);
        setContact(Contact);
        setCountryID(countryID);
    }

    /**
     * Gets division id.
     *
     * @return the division id
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * Sets division id.
     *
     * @param divisionID the division id
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * Gets division.
     *
     * @return the division
     */
    public String getDivision() {
        return division;
    }

    /**
     * Sets division.
     *
     * @param division the division
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Gets create date.
     *
     * @return the create date
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Sets create date.
     *
     * @param createDate the create date
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * Gets created by.
     *
     * @return the created by
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets created by.
     *
     * @param createdBy the created by
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets last update.
     *
     * @return the last update
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets last update.
     *
     * @param lastUpdate the last update
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Gets contact.
     *
     * @return the contact
     */
    public String getContact() {
        return Contact;
    }

    /**
     * Sets contact.
     *
     * @param Contact the contact
     */
    public void setContact(String Contact) {
        this.Contact = Contact;
    }

    /**
     * Gets country id.
     *
     * @return the country id
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * Sets country id.
     *
     * @param countryID the country id
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }
}
