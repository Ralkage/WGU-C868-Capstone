package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;
import model.User;
import utils.DBConnection;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import static utils.DBConnection.closeConnection;

/**
 * The Login screen controller class.
 *
 * @author Christian Lopez
 * Software Development Capstone – C868
 */
public class LoginScreenController implements Initializable {
    private static final Connection conn = DBConnection.getConn();
    private final ObservableList<Appointment> alertList = FXCollections.observableArrayList();
    /**
     * The Stage.
     */
    Stage stage;
    /**
     * The Scene.
     */
    Parent scene;
    /**
     * The Dt format.
     */
    DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("dd MMM'.' yyyy 'at' HH:mm'.'");
    /**
     * The Log format.
     */
    DateTimeFormatter logFormat = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm");
    @FXML
    private Button LogIn;
    @FXML
    private Button ExitButton;
    @FXML
    private Label appNameLabel;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label locationLabel;
    @FXML
    private Label zoneIDLabel;

    /**
     * The JavaFX initialize method.
     * @param url the url
     * @param rb the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Uncomment the line below to test second language
        // Locale.setDefault(new Locale("fr"));
        rb = ResourceBundle.getBundle("LoginScreen", Locale.getDefault());

        usernameField.setText("assistant");
        passwordField.setText("assistant");

        appNameLabel.setText(rb.getString("AppName"));
        usernameField.setPromptText(rb.getString("Username"));
        passwordField.setPromptText(rb.getString("Password"));
        locationLabel.setText(rb.getString("Location"));
        zoneIDLabel.setText(String.valueOf(ZoneId.systemDefault()));
        LogIn.setText(rb.getString("LogIn"));
        ExitButton.setText(rb.getString("Exit"));
    }

    /**
     * The on action log in method.
     *
     * @param event the event
     */
    @FXML
    public void onActionLogIn(ActionEvent event) {
        try {
            ResourceBundle rb = ResourceBundle.getBundle("LoginScreen", Locale.getDefault());

            if (usernameField.getText().trim().isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(rb.getString("UsernameIncorrect"));
                alert.showAndWait();
            }

            if (passwordField.getText().trim().isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(rb.getString("PasswordIncorrect"));
                alert.showAndWait();
            }

            if (!usernameField.getText().trim().isBlank() && !passwordField.getText().trim().isBlank()) {
                String username = this.usernameField.getText().trim();
                String password = this.passwordField.getText().trim();

                PreparedStatement ps = conn.prepareStatement(
                        "SELECT * FROM users WHERE User_Name = ? AND Password = ?");

                ps.setString(1, username);
                ps.setString(2, password);

                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    loginAttempts(false);

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(rb.getString("PasswordIncorrect"));
                    alert.showAndWait();
                } else {
                    do {
                        {
                            User.setUserId(rs.getInt("User_ID"));
                            User.setPassword(rs.getString("Password"));
                            User.setUserName(rs.getString("User_Name"));

                            appointmentAlert();
                            loginAttempts(true);

                            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainMenuScreen.fxml")));
                            stage.setScene(new Scene(scene));
                            stage.centerOnScreen();
                            stage.show();
                        }
                    }
                    while (rs.next());
                }
            }
        } catch (SQLException e) {
            ResourceBundle rb = ResourceBundle.getBundle("LoginScreen", Locale.getDefault());
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(rb.getString("sqlException"));
        } catch (Exception e) {
            ResourceBundle rb = ResourceBundle.getBundle("LoginScreen", Locale.getDefault());
            System.out.println(e.getMessage());
            System.out.println(rb.getString("exceptionMessage"));
        }
    }

    /**
     * The appointment alert method.
     */
    private void appointmentAlert() {
        try {
            ResourceBundle rb = ResourceBundle.getBundle("LoginScreen", Locale.getDefault());

            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM appointments WHERE Start BETWEEN NOW() AND "
                            + "ADDDATE(NOW(), INTERVAL 15 MINUTE)");

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle(rb.getString("AppointmentReminderTitle"));
                alert2.setHeaderText(rb.getString("NoUpcomingAppointments"));
                alert2.showAndWait();
                LocalDateTime now = LocalDateTime.now();
                System.out.println(now);
            } else {
                do {
                    int appointmentID = rs.getInt("Appointment_ID");
                    LocalDateTime alertDate = rs.getTimestamp("Start").toLocalDateTime();

                    alertList.add(new Appointment(appointmentID, alertDate));

                    String formatted = alertDate.format(dtFormat);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(rb.getString("AppointmentReminderTitle"));
                    alert.setHeaderText(rb.getString("UpcomingAppointment"));
                    alert.setContentText(rb.getString("AppointmentNumber") + appointmentID
                            + " " + rb.getString("AppointmentNumberContinued") + " " + formatted);
                    alert.showAndWait();
                }
                while (rs.next());
            }
        } catch (SQLException e) {
            ResourceBundle rb = ResourceBundle.getBundle("LoginScreen", Locale.getDefault());
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(rb.getString("sqlException"));
        } catch (Exception e) {
            ResourceBundle rb = ResourceBundle.getBundle("LoginScreen", Locale.getDefault());
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(rb.getString("exceptionMessage"));
        }
    }

    private void loginAttempts(boolean login) {
        try {
            ResourceBundle rb = ResourceBundle.getBundle("LoginScreen", Locale.getDefault());

            FileWriter fileName = new FileWriter("login_activity.txt", true);
            BufferedWriter loginAttempt = new BufferedWriter(fileName);

            loginAttempt.append("Login attempt on ")
                    .append(LocalDateTime.now().format(logFormat))
                    .append(" | User: ")
                    .append(User.getUserName())
                    .append(" | Attempt Successful: ")
                    .append(String.valueOf(login));
            loginAttempt.newLine();
            loginAttempt.close();
            System.out.println("\n" + rb.getString("LoginActivity"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
