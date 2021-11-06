package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

import static utils.DBConnection.closeConnection;

/**
 * The Main Menu controller class.
 *
 * @author Christian Lopez
 * Software Development Capstone – C868
 */
public class MainMenuController implements Initializable
{
    /**
     * The Stage.
     */
    Stage stage;
    /**
     * The Scene.
     */
    Parent scene;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // We call this method as it's required because we are implementing the Initializable interface
        // but we actually don't do anything with it.
    }

    /**
     * On action appointments.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    public void onActionAppointments(ActionEvent event) throws IOException
    {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AppointmentsScreen.fxml")));
            stage.setScene(new Scene(scene));
            stage.centerOnScreen();
            stage.show();
    }

    /**
     * The on action customer method.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    public void onActionCustomer(ActionEvent event) throws IOException
    {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/CustomersScreen.fxml")));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * The on action reports method.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    public void onActionReports(ActionEvent event) throws IOException
    {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ReportsScreen.fxml")));
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
