package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.DBConnection;

import java.util.Objects;

/**
 * The Main class.
 *
 * @author Christian Lopez
 * Software Development Capstone – C868
 */
public class Main extends Application {

    /**
     * The JavaFX start method.
     *
     * @param primaryStage the primary stage
     * @throws Exception exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/LoginScreen.fxml")));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * The entry point of the scheduling application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        DBConnection.startConnection();
        launch(args);
        DBConnection.closeConnection();
    }
}
