package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.LoginBO;
import lk.ijse.config.SessionFactoryConfiguration;
import lk.ijse.dto.LoginDTO;
import lk.ijse.entity.UserSession;
import org.hibernate.Session;

import java.io.IOException;
import java.sql.SQLException;

public class LoginFormController {

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    private AnchorPane rootNode;



    String tempPassword;

    String tempUserName;

    LoginBO loginBO = (LoginBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.LOGIN);

    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {
        Session session = SessionFactoryConfiguration.getInstance().getSession();

        String userName= txtUserName.getText();
        String password = txtPassword.getText();

        try {
            if (userName.equals(tempUserName)  && password.equals(tempPassword)) {
                /*methana krala thinne wena kenekge pc ekakata me system eka dammothi mulin DB ekk nathi
                  nisa tempory login details tikak dila manual user id ekakui role ekakui dapu eka*/

                // Store userId and role in Session singleton
                UserSession.getInstance().setUser(123, "admin");

                navigateToTheDashboard((Stage) rootNode.getScene().getWindow());

            } else {
                LoginDTO loginDTO = new LoginDTO(userName, password);

                boolean loginResult = loginBO.checkCredential(loginDTO);
                System.out.println(UserSession.getInstance().getUserId() + " " + UserSession.getInstance().getRole());
                if (loginResult) {
                    navigateToTheDashboard((Stage) rootNode.getScene().getWindow());

                } else {
                    // Show alert if credentials are incorrect
                    new Alert(Alert.AlertType.ERROR, "Invalid credentials! Please try again.").show();
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void navigateToTheDashboard(Stage stage) throws IOException {
        // Load the dashboard
        FXMLLoader customerLoader = new FXMLLoader(getClass().getResource("/view/dashboard_form.fxml"));
        Parent customerRoot = customerLoader.load();
        rootNode.getChildren().clear();
        rootNode.getChildren().add(customerRoot);

        stage = (Stage) rootNode.getScene().getWindow();
        stage.setTitle("Dashboard");
    }

    @FXML
    void txtPasswordOnAction(ActionEvent event) {

    }

    @FXML
    void txtUserNameOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtUsernameOnAction(ActionEvent event) {

    }

}