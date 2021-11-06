package controller;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.FirstLevelDivision;
import model.User;
import utils.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import static utils.DBConnection.closeConnection;

/**
 * The Customers screen controller class.
 *
 * @author Christian Lopez
 * Software Development Capstone – C868
 */
public class CustomersScreenController implements Initializable {
    private static final Connection conn = DBConnection.getConn();
    /**
     * The Stage.
     */
    Stage stage;
    /**
     * The Scene.
     */
    Parent scene;
    /**
     * The prepared statement.
     */
    PreparedStatement ps;

    /**
     * The Division id.
     */
    int divisionID = -1;
    /**
     * The Customer list.
     */
    ObservableList<Customer> customerList = FXCollections.observableArrayList();
    /**
     * The Country list.
     */
    ObservableList<Country> countryList = FXCollections.observableArrayList();
    /**
     * The First Level Division list.
     */
    ObservableList<FirstLevelDivision> firstLevelList = FXCollections.observableArrayList();
    private Customer selectedCustomer;
    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<Customer, Integer> customerIdColumn;
    @FXML
    private TableColumn<Customer, String> nameColumn;
    @FXML
    private TableColumn<Customer, String> phoneColumn;
    @FXML
    private TableColumn<Customer, String> addressColumn;
    @FXML
    private TableColumn<Customer, String> countryColumn;
    @FXML
    private TableColumn<Customer, String> divisionColumn;
    @FXML
    private TableColumn<Customer, String> zipColumn;
    @FXML
    private Label addCustomerLabel;
    @FXML
    private TextField customerID;
    @FXML
    private TextField customerName;
    @FXML
    private TextField customerAddress;
    @FXML
    private ComboBox<String> stateComboBox;
    @FXML
    private ComboBox<String> countryComboBox;
    @FXML
    private TextField customerZip;
    @FXML
    private TextField customerPhone;
    @FXML
    private TextField keywordTextField;

    /**
     * The JavaFX initialize method.
     *
     * <p>
     * Lambda usage: This is the second case for lambda implementation for this project. We use lambda expressions over
     * PropertyValueFactory to initialize the table data within their respective Customers TableView columns. We don't
     * use a lambda expression for customerIdColumn because ReadOnlyIntegerWrapper() cannot be converted to
     * ObservableValue.
     * </p>
     *
     * @param url the url
     * @param rb  the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        nameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCustomerName()));
        phoneColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getPhone()));
        addressColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getAddress()));
        countryColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCountryName()));
        divisionColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getDivisionName()));
        zipColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getPostalCode()));

        populateTable();
        populateCountryCombo();
        populateFirstLevelCombo();
        addSearchFilter();
    }

    /**
     * The get Division ID from list method.
     *
     * @param temp the temporary Division ID filler.
     * @return -1
     */
    private int getDivisionIDFromList(String temp) {
        for (FirstLevelDivision look : firstLevelList) {
            if (look.getDivision().trim().toLowerCase().contains(
                    temp.trim().toLowerCase())) {
                return look.getDivisionID();
            }
        }
        return -1;
    }

    /**
     * The populate customer table method.
     */
    @FXML
    public void populateTable() {
        try {
            customerList.clear();
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM customers, first_level_divisions, countries "
                            + "WHERE customers.Division_ID = first_level_divisions.Division_ID "
                            + "AND first_level_divisions.COUNTRY_ID = countries.Country_ID "
                            + "ORDER BY Customer_ID");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int divisionID = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                String countryName = rs.getString("Country");

                customerList.add(new Customer(customerID, customerName, address,
                        postalCode, phone, createDate, createdBy, lastUpdate,
                        lastUpdatedBy, divisionID, divisionName, countryName));
            }
            customerTable.setItems(customerList);
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
     * The populate first level division combo method.
     */
    @FXML
    public void populateFirstLevelCombo() {
        ObservableList<String> stateCombo = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM first_level_divisions");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int countryID = rs.getInt("COUNTRY_ID");

                stateCombo.add(division);

                firstLevelList.add(new FirstLevelDivision(divisionID, division,
                        createDate, createdBy, lastUpdate, lastUpdatedBy, countryID));
            }
            stateComboBox.setItems(stateCombo);

            stateComboBox.setButtonCell(new ListCell<String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText("Select State/Province");
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
     * The action customer country method.
     *
     * @param event the event
     */
    @FXML
    public void onActionCountry(ActionEvent event) {
        try {
            ObservableList<String> stateCombo = FXCollections.observableArrayList();


            ps = conn.prepareStatement(
                    "SELECT * FROM first_level_divisions");

            if (countryComboBox.getValue() == null) {
                populateFirstLevelCombo();
            }

            if (countryComboBox.getValue().contentEquals("United States")) {
                ps = conn.prepareStatement(
                        "SELECT * FROM first_level_divisions WHERE COUNTRY_ID = 231 "
                                + "ORDER BY Division");
            }
            if (countryComboBox.getValue().contentEquals("United Kingdom")) {
                ps = conn.prepareStatement(
                        "SELECT * FROM first_level_divisions WHERE COUNTRY_ID = 230 "
                                + "ORDER BY Division");
            }
            if (countryComboBox.getValue().contentEquals("Canada")) {
                ps = conn.prepareStatement(
                        "SELECT * FROM first_level_divisions WHERE COUNTRY_ID = 38 "
                                + "ORDER BY Division");
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int countryID = rs.getInt("COUNTRY_ID");

                stateCombo.add(division);

                firstLevelList.add(new FirstLevelDivision(divisionID, division,
                        createDate, createdBy, lastUpdate, lastUpdatedBy, countryID));
            }
            stateComboBox.setItems(stateCombo);

            stateComboBox.setButtonCell(new ListCell<String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText("Select State/Province");
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
     * The populate customer country combo method.
     */
    @FXML
    public void populateCountryCombo() {
        ObservableList<String> countryCombo = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM countries");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int countryID = rs.getInt("Country_ID");
                String country = rs.getString("Country");
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");

                countryCombo.add(country);

                countryList.add(new Country(countryID, country, createDate,
                        createdBy, lastUpdate, lastUpdatedBy));
            }
            countryComboBox.setItems(countryCombo);

            countryComboBox.setButtonCell(new ListCell<String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText("Select Country");
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
     * The on action add customer record method.
     *
     * @param event the event
     */
    @FXML
    public void onActionAdd(ActionEvent event) {
        addCustomerLabel.setText("Add New Customer");
        onActionClear(event);
    }

    /**
     * The on action update customer record method.
     *
     * @param event the event
     */
    @FXML
    public void onActionUpdate(ActionEvent event) {
        if (customerTable.getSelectionModel().getSelectedItem() != null) {
            onActionClear(event);
            addCustomerLabel.setText("Update Customer");
            selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
            customerID.setText(Integer.toString(selectedCustomer.getCustomerID()));
            customerName.setText(selectedCustomer.getCustomerName());
            customerAddress.setText(selectedCustomer.getAddress());
            stateComboBox.setValue(selectedCustomer.getDivisionName());
            countryComboBox.setValue(selectedCustomer.getCountryName());
            customerZip.setText(selectedCustomer.getPostalCode());
            customerPhone.setText(selectedCustomer.getPhone());
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a customer from the customers table.");
            alert.showAndWait();
        }

    }

    /**
     * The on action save customer record method.
     *
     * @param event the event
     */
    @FXML
    public void onActionSave(ActionEvent event) {
        try {
            String customerName = this.customerName.getText();
            String address = customerAddress.getText();
            String postalCode = customerZip.getText();
            String phoneNumber = customerPhone.getText();
            String lastUpdatedBy = User.getUserName();

            if (stateComboBox.getValue() != null) {

                divisionID = getDivisionIDFromList(stateComboBox.getValue());
            }

            if (customerName.isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("The Name field is empty.");
                alert.showAndWait();
                return;
            }
            if (address.isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("The Address field is empty.");
                alert.showAndWait();
                return;
            }
            if (stateComboBox.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please select a State/Province.");
                alert.showAndWait();
                return;
            }
            if (countryComboBox.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please select a Country.");
                alert.showAndWait();
                return;
            }
            if (postalCode.isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("The Postal Code field is empty.");
                alert.showAndWait();
                return;
            }
            if (phoneNumber.isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("The Phone Number field is empty.");
                alert.showAndWait();
                return;
            }

            if (addCustomerLabel.getText().contains("Add New Customer")) {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO customers"
                                + "(Customer_Name, Address, Postal_Code, Phone, Create_Date, "
                                + "Created_By, Last_Update, Last_Updated_By, Division_ID) "
                                + "VALUES(?, ?, ?, ?, NOW(), ?, NOW(), ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);

                ps.setString(1, customerName);
                ps.setString(2, address);
                ps.setString(3, postalCode);
                ps.setString(4, phoneNumber);
                ps.setString(5, lastUpdatedBy);
                ps.setString(6, lastUpdatedBy);
                ps.setInt(7, divisionID);

                int result = ps.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                if (result > 0) {
                    alert.setContentText("Customer " + customerName + " added!");
                } else {
                    alert.setContentText("There was an issue saving changes \nfor customer " + customerName);
                }

                populateTable();
                clearALL();
            }

            if (addCustomerLabel.getText().contains("Update Customer")) {
                PreparedStatement ps = conn.prepareStatement("UPDATE customers "
                        + "Set Customer_Name = ?, Address = ?, Postal_Code = ?, "
                        + "Phone = ?, Last_Update = NOW(), Last_Updated_By = ?, "
                        + "Division_ID =? "
                        + "WHERE Customer_ID = ?");

                ps.setString(1, customerName);
                ps.setString(2, address);
                ps.setString(3, postalCode);
                ps.setString(4, phoneNumber);
                ps.setString(5, lastUpdatedBy);
                ps.setInt(6, divisionID);
                ps.setInt(7, Integer.parseInt(customerID.getText()));

                int result = ps.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                if (result == 1) {
                    alert.setContentText("Customer " + customerName + " updated");
                } else {
                    alert.setContentText("There was an issue saving changes for customer " + customerName);
                }
                alert.showAndWait();

                populateTable();
                clearALL();
            }
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
     * The on action clear all fields method.
     *
     * @param event the event
     */
    @FXML
    public void onActionClear(ActionEvent event) {
        clearALL();
    }

    private void clearALL() {
        customerID.clear();
        customerID.setPromptText("Auto Gen.");
        customerName.clear();
        customerAddress.clear();
        customerZip.clear();
        customerPhone.clear();
    }

    /**
     * The on action delete customer method.
     *
     * @param event the event
     */
    @FXML
    public void onActionDelete(ActionEvent event) {
        selectedCustomer = customerTable.getSelectionModel().getSelectedItem();

        if (selectedCustomer != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Are you sure that you want to delete customer " + selectedCustomer.getCustomerName()
                    + "?");
            alert.setContentText("Deleting this customer will also delete all associating appointments.");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                try {
                    PreparedStatement ps = conn.prepareStatement(
                            "DELETE appointments.* FROM appointments "
                                    + "WHERE Customer_ID =?");
                    PreparedStatement ps2 = conn.prepareStatement(
                            "DELETE customers.* FROM customers "
                                    + "WHERE Customer_ID =?");

                    ps.setInt(1, selectedCustomer.getCustomerID());
                    ps2.setInt(1, selectedCustomer.getCustomerID());
                    ps.executeUpdate();
                    int rs2 = ps2.executeUpdate();

                    Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                    if (rs2 > 0) {
                        alert2.setContentText("Customer " + selectedCustomer.getCustomerName() + " and associated " +
                                "appointments have been deleted.");
                    } else {
                        alert.setContentText("There was an issue with deleting \nrecords for customer " + customerName);
                    }
                    populateTable();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                    System.out.println("SQL error! Please check your database logs for more information");
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                    System.out.println("Non-SQL error! Please try again.");
                }
            } else {
                return;
            }
        }
        if (selectedCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Select a customer to delete");
            alert.showAndWait();
        }
    }

    /**
     * The action return to main menu method.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    public void onActionReturnToMainMenu(ActionEvent event) throws IOException {
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

    /***
     * The addSearchFilter method.
     */
    void addSearchFilter() {
        FilteredList<Customer> filteredData = new FilteredList<>(customerList, b -> true);

        keywordTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(Customer -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                if (Customer.getCustomerName().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (Customer.getPhone().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (Customer.getPostalCode().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else return Customer.getAddress().toLowerCase().indexOf(searchKeyword) > -1;
            });
        });

        SortedList<Customer> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(customerTable.comparatorProperty());
        customerTable.setItems(sortedData);
    }
}

