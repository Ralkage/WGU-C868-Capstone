package model;

/**
 * The reportType model class.
 *
 * @author Christian Lopez
 * Software Development Capstone – C868
 */
public class ReportType {
    private String country;
    private String month;
    private String type;
    private int total;
    private int numberOfCustomers;


    /**
     * Instantiates a new Report type.
     *
     * @param month the month
     * @param type  the type
     * @param total the total
     */
    public ReportType(String month, String type, int total) {
        this.month = month;
        this.type = type;
        this.total = total;
    }

    /**
     * Instantiates a new Report type.
     *
     * @param country   the day
     * @param numberOfCustomers the total
     */
    public ReportType(String country, int numberOfCustomers) {
        this.country = country;
        this.numberOfCustomers = numberOfCustomers;
    }

    public ReportType(int total) {
        this.total = total;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets month.
     *
     * @return the month
     */
    public String getMonth() {
        return month;
    }

    /**
     * Sets month.
     *
     * @param month the month
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * Gets total.
     *
     * @return the total
     */
    public int getTotal() {
        return total;
    }

    /**
     * Sets total.
     *
     * @param total the total
     */
    public void setTotal(int total) {
        this.total = total;
    }

    public int getNumberOfCustomers() {
        return numberOfCustomers;
    }

    public void setNumberOfCustomers(int numberOfCustomers) {
        this.numberOfCustomers = numberOfCustomers;
    }
}
