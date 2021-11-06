package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.ReportType;
import model.User;
import utils.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

import static utils.DBConnection.closeConnection;

/**
 * The Reports screen controller class.
 *
 * @author Christian Lopez Software Development Capstone – C868
 */
public class ReportsScreenController implements Initializable {
    private static final Connection conn = DBConnection.getConn();
    /**
     * The Contact table tab.
     */
    public Tab contactTableTab;
    /**
     * The Type month tab.
     */
    public Tab typeMonthTab;
    /**
     * The Custom report tab.
     */
    public Tab customReportTab;
    /**
     * The Pie Chart report tab.
     */
    public Tab pieChartTab;
    /**
     * The Stage.
     */
    Stage stage;
    /**
     * The Scene.
     */
    Parent scene;
    /**
     * The Report list.
     */
    ObservableList<ReportType> reportList = FXCollections.observableArrayList();
    /**
     * The Contact list report.
     */
    ObservableList<Contact> contactListReport = FXCollections.observableArrayList();
    /**
     * The Appointment list report.
     */
    ObservableList<Appointment> AppointmentListReport = FXCollections.observableArrayList();

    @FXML
    private TableView<ReportType> typeDayTable;
    @FXML
    private TableView<ReportType> typeMonthTable;
    @FXML
    private TableColumn<ReportType, String> reportTypeColumn;
    @FXML
    private TableColumn<ReportType, String> monthColumn;
    @FXML
    private TableColumn<ReportType, Integer> totalColumn;
    @FXML
    private ComboBox<String> contactComboBox;
    @FXML
    private TableView<Appointment> ContactTable;
    @FXML
    private TableColumn<Appointment, Integer> appointmentColumn;
    @FXML
    private TableColumn<Appointment, String> titleColumn;
    @FXML
    private TableColumn<Appointment, String> typeColumn;
    @FXML
    private TableColumn<Appointment, String> descriptionColumn;
    @FXML
    private TableColumn<Appointment, LocalDateTime> startColumn;
    @FXML
    private TableColumn<Appointment, LocalDateTime> endColumn;
    @FXML
    private TableColumn<Appointment, Integer> customerIdColumn;
    @FXML
    private TableColumn<ReportType, String> countryColumn;
    @FXML
    private TableColumn<ReportType, String> customReportTotalColumn;
    @FXML
    private PieChart customerPie;

    /**
     * The JavaFX initialize method.
     *
     * @param url the url
     * @param rb  the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
        reportTypeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));

        appointmentColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        populateTypeMonthTab();
        populateContactsComboBox();
        populateContactsTab();
        populateCustomReport();
    }

    /**
     * The populate appointment type by month report method.
     */
    private void populateTypeMonthTab() {
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT MONTHNAME(Start) AS Month, Type, "
                            + "COUNT(Type) AS TOTAL "
                            + "FROM appointments "
                            + "GROUP BY Month, Type");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String month = rs.getString("Month");
                String type = rs.getString("Type");
                int total = rs.getInt("TOTAL");

                reportList.add(new ReportType(month, type, total));

            }
            typeMonthTable.setItems(reportList);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println("SQL error! Please check your database logs for more information");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println("Non-SQL error! Please try again.");
        }
    }

    /**
     * The populate contacts report method.
     */
    private void populateContactsTab() {
        try {
            AppointmentListReport.clear();
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM appointments, customers, users, contacts "
                            + "WHERE appointments.User_ID = users.User_ID "
                            + "AND appointments.Contact_ID = contacts.Contact_ID "
                            + "AND appointments.Customer_ID = customers.Customer_ID "
                            + "ORDER BY Start");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                String contactName = rs.getString("Contact_Name");

                LocalDateTime createdDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int contactID = rs.getInt("Contact_ID");
                int userID = User.getUserId();

                AppointmentListReport.add(new Appointment(appointmentID, title, description,
                        location, type, start, end, createdDate, createdBy, lastUpdate,
                        lastUpdatedBy, customerID, userID, contactID, contactName));
            }
            ContactTable.setItems(AppointmentListReport);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println("SQL error! Please check your database logs for more information");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println("Non-SQL error! Please try again.");
        }
    }

    /**
     * The populate contacts combo box method.
     */
    private void populateContactsComboBox() {
        ObservableList<String> contactCombo = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * "
                    + "FROM contacts");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String email = rs.getString("Email");

                contactCombo.add(rs.getString("Contact_Name"));

                contactListReport.add(new Contact(contactID, contactName, email));
            }
            contactComboBox.setItems(contactCombo);
            contactComboBox.setButtonCell(new ListCell<String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText("Select Contact");
                    } else {
                        setText(item);
                    }
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println("SQL error! Please check your database logs for more information");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println("Non-SQL error! Please try again.");
        }
    }

    /**
     * The on action contact method.
     *
     * @param event the event
     */
    @FXML
    void onActionContact(ActionEvent event) {
        try {
            AppointmentListReport.clear();
            String selectedContact = contactComboBox.getValue();
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM appointments, customers, users, contacts "
                            + "WHERE appointments.User_ID = users.User_ID "
                            + "AND appointments.Contact_ID = contacts.Contact_ID "
                            + "AND appointments.Customer_ID = customers.Customer_ID AND "
                            + "Contact_Name = ? "
                            + "ORDER BY Start");

            ps.setString(1, selectedContact);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                String contactName = rs.getString("Contact_Name");
                String customerName = rs.getString("Customer_Name");

                LocalDateTime createdDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int contactID = rs.getInt("Contact_ID");

                int userID = User.getUserId();
                String userName = rs.getString("User_Name");

                AppointmentListReport.add(new Appointment(appointmentID, title, description,
                        location, type, start, end, createdDate, createdBy, lastUpdate,
                        lastUpdatedBy, customerID, userID, contactID, contactName, customerName));
            }

            ContactTable.setItems(AppointmentListReport);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println("SQL error! Please check your database logs for more information");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println("Non-SQL error! Please try again.");
        }
    }

    /**
     * The populate custom report method.
     * <p>
     * This is the custom report I created for section A3f of the requirements section. This report calculates the total
     * number of customers by country and presents this data in a JavaFX pie chart.
     * </p>
     */
    private void populateCustomReport() {
        try {
            ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
            ArrayList<String> country = new ArrayList<>();
            ArrayList<Integer> numOfCustomers = new ArrayList<>();

            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(customers.Customer_ID) AS NumOfCustomers, " +
                    "countries.Country FROM customers " +
                    "LEFT JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID " +
                    "LEFT JOIN countries ON first_level_divisions.COUNTRY_ID = countries.Country_ID " +
                    "GROUP BY Country");

            customerPie.setTitle("Customers by Country");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                pieData.add(new PieChart.Data(rs.getString("countries.Country"),
                        rs.getInt("NumOfCustomers")));

                country.add(rs.getString("countries.Country"));
                numOfCustomers.add(rs.getInt("NumOfCustomers"));
            }

            customerPie.setData(pieData);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println("SQL error! Please check your database logs for more information");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println("Non-SQL error! Please try again.");
        }
    }

    /**
     * The on action return to main menu method.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionReturnToMainMenu(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainMenuScreen.fxml")));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * The on action exit application method.
     *
     * @param event the event
     * @throws SQLException the sql exception
     */
    @FXML
    public void onActionExit(ActionEvent event) throws SQLException {
        closeConnection();
        System.exit(0);
    }
}
