package model;

/**
 * The Contact model class.
 *
 * @author Christian Lopez
 * Software Development Capstone – C868
 */
public class Contact {
    private int contactID;
    private String contactName;
    private String email;

    /**
     * Instantiates a new Contact.
     *
     * @param contactID   the contact id
     * @param contactName the contact name
     * @param email       the email
     */
    public Contact(int contactID, String contactName, String email) {
        setContactID(contactID);
        setContactName(contactName);
        setEmail(email);
    }

    /**
     * Gets the contact id.
     *
     * @return the contact id
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * Sets the contact id.
     *
     * @param contactID the contact id
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * Gets the contact name.
     *
     * @return the contact name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Sets the contact name.
     *
     * @param contactName the contact name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Gets the contact email.
     *
     * @return the contact email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the contact email.
     *
     * @param email the contact email email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
