package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class DashboardFormController {

    @FXML
    private JFXButton btnDashboard;

    @FXML
    private JFXButton btnPrograms;

    @FXML
    private JFXButton btnUser;

    @FXML
    private AnchorPane nodePane;

    @FXML
    private AnchorPane rootNode;

    public void initialize() {
        // Load mainDashboard_form.fxml after a 1-second delay
        PauseTransition delay = new PauseTransition(Duration.seconds(0.0001));
        delay.setOnFinished(event -> loadMainDashboard());
        delay.play();
    }

    // Method to load the main dashboard
    private void loadMainDashboard() {
        try {
            // Load mainDashboard_form.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/show_dashboard.fxml"));
            AnchorPane mainDashboard = loader.load();

            // Replace the children of rootNode with the loaded content
            rootNode.getChildren().setAll(mainDashboard);
            Stage stage = (Stage) rootNode.getScene().getWindow();
            stage.setTitle("Dashboard");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnDashboardOnAction(ActionEvent event) throws IOException {
        FXMLLoader productLoader = new FXMLLoader(getClass().getResource("/view/show_dashboard.fxml"));
        Parent productRoot = productLoader.load();
        rootNode.getChildren().clear();
        rootNode.getChildren().add(productRoot);

        Stage stage = (Stage) rootNode.getScene().getWindow();
        stage.setTitle("");
    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) throws IOException {
        // Load the login form
        Parent loginForm = FXMLLoader.load(getClass().getResource("/view/login_form.fxml"));
        Scene scene = new Scene(loginForm);
        Stage stage = (Stage) rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("The Culinary Academy");
    }

    @FXML
    void btnProgramsOnAction(ActionEvent event) throws IOException {
        FXMLLoader productLoader = new FXMLLoader(getClass().getResource("/view/program_form.fxml"));
        Parent productRoot = productLoader.load();
        rootNode.getChildren().clear();
        rootNode.getChildren().add(productRoot);

        Stage stage = (Stage) rootNode.getScene().getWindow();
        stage.setTitle("");
    }

    @FXML
    void btnRegistrationOnAction(ActionEvent event) throws IOException {
        FXMLLoader productLoader = new FXMLLoader(getClass().getResource("/view/registration_from.fxml"));
        Parent productRoot = productLoader.load();
        rootNode.getChildren().clear();
        rootNode.getChildren().add(productRoot);

        Stage stage = (Stage) rootNode.getScene().getWindow();
        stage.setTitle("");

    }

    @FXML
    void btnStudentsOnAction(ActionEvent event) throws IOException {
        FXMLLoader productLoader = new FXMLLoader(getClass().getResource("/view/student_from.fxml"));
        Parent productRoot = productLoader.load();
        rootNode.getChildren().clear();
        rootNode.getChildren().add(productRoot);

        Stage stage = (Stage) rootNode.getScene().getWindow();
        stage.setTitle("");

    }

    @FXML
    void btnUser(ActionEvent event) throws IOException {
        FXMLLoader productLoader = new FXMLLoader(getClass().getResource("/view/user_form.fxml"));
        Parent productRoot = productLoader.load();
        rootNode.getChildren().clear();
        rootNode.getChildren().add(productRoot);

        Stage stage = (Stage) rootNode.getScene().getWindow();
        stage.setTitle("");
    }

    public void btnPaymentsOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader productLoader = new FXMLLoader(getClass().getResource("/view/payment_form.fxml"));
        Parent productRoot = productLoader.load();
        rootNode.getChildren().clear();
        rootNode.getChildren().add(productRoot);

        Stage stage = (Stage) rootNode.getScene().getWindow();
        stage.setTitle("");
    }
}
