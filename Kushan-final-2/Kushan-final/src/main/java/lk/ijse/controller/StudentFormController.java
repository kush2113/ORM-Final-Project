package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.StudentBO;
import lk.ijse.config.SessionFactoryConfiguration;
import lk.ijse.dto.ProgramDTO;
import lk.ijse.dto.StudentDTO;
import lk.ijse.entity.User;
import lk.ijse.entity.UserSession;
import lk.ijse.tdm.ProgramTM;
import lk.ijse.tdm.StudentTm;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class StudentFormController {

    @FXML
    private Label lblDate;
    @FXML
    private JFXButton btnStudentDelete;

    @FXML
    private JFXButton btnStudentSave;

    @FXML
    private JFXButton btnStudentUpdate;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPhoneNumber;

    @FXML
    private TableColumn<?, ?> colRegisterDate;

    @FXML
    private TableColumn<?, ?> colUserId;

    @FXML
    private Label lblId;

    @FXML
    private Label lblUserId;

    @FXML
    private TableView<StudentTm> tblStudent;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhoneNumber;

    int userID = UserSession.getInstance().getUserId();


    StudentBO studentBO =(StudentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.STUDENT);


    public void initialize() {

        lblDate.setText(LocalDate.now().toString());
        loadAllStudent();
        lblUserId.setText(String.valueOf(userID));
        setCellValueFactory();
        generateNewStudentID();
        showSelectedUserDetails();
        clearFields();

    }

    private void showSelectedUserDetails() {
        StudentTm selectedUser = tblStudent.getSelectionModel().getSelectedItem();
        tblStudent.setOnMouseClicked(event -> showSelectedUserDetails());
        if (selectedUser != null) {
            lblId.setText(String.valueOf(selectedUser.getId()));
            txtName.setText(selectedUser.getName());
            lblUserId.setText(String.valueOf(selectedUser.getUserId()));
            txtPhoneNumber.setText(selectedUser.getPhoneNumber());
            txtAddress.setText(selectedUser.getAddress());

            btnStudentUpdate.setDisable(false);
            btnStudentSave.setDisable(true);

            btnStudentDelete.setDisable(false);
        }
    }

    private void generateNewStudentID() {
        try {
            String nextStudentId = studentBO.generateNewID();

            lblId.setText(String.valueOf(nextStudentId));
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }


   private void loadAllStudent(){
       tblStudent.getItems().clear();
       try {

           ArrayList<StudentDTO> allStudent = studentBO.getAllStudent();

           for (StudentDTO studentDTO : allStudent) {
               tblStudent.getItems().add(new StudentTm(studentDTO.getStudentId(), studentDTO.getName(), studentDTO.getAddress(), studentDTO.getPhone(), studentDTO.getRegDate(), studentDTO.getUser()));
           }
       } catch (IOException | ClassNotFoundException | SQLException e) {
           throw new RuntimeException(e);
       }

    }

    private void setCellValueFactory() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colRegisterDate.setCellValueFactory(new PropertyValueFactory<>("registerDate"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
    }

    void clearFields() {
        txtID.clear();
        txtName.clear();
        txtAddress.clear();
        txtPhoneNumber.clear();
    }


    @FXML
    void addressKeyRelaseAction(KeyEvent event) {

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
         clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        int id = Integer.parseInt(lblId.getText());

            try {
                boolean isDelete = studentBO.deleteStudent(id);
                if (isDelete) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Student delete!").show();
                    clearFields();
                    initialize();
                }
            } catch (SQLException | ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {

        int id = Integer.parseInt(lblId.getText());
        String name = txtName.getText();
        String address = txtAddress.getText();
        String tel = txtPhoneNumber.getText();
        Date date = Date.valueOf(lblDate.getText());

        if (!name.isEmpty() || !address.isEmpty() || !tel.isEmpty()) {
             try (Session session = SessionFactoryConfiguration.getInstance().getSession()) {
                    Transaction transaction = session.beginTransaction();

                    int userID = UserSession.getInstance().getUserId();
                    User user = session.get(User.class, userID);
                    System.out.println(userID);

                    if (user == null) {
                        new Alert(Alert.AlertType.ERROR, "User not found in the database").show();
                        return;
                    }
                    StudentDTO studentDTO = new StudentDTO(id, name, address, tel, date, user.getUserId());
                        boolean isSaved = studentBO.saveStudent(studentDTO);
                      if (isSaved) {
                            transaction.commit();
                            new Alert(Alert.AlertType.CONFIRMATION, "Student saved!").show();
                            clearFields();
                            initialize();
                    } else {
                        transaction.rollback();
                        new Alert(Alert.AlertType.ERROR, "Failed to save the student").show();
                    }

                } catch (SQLException | ClassNotFoundException e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
        int id = Integer.parseInt(lblId.getText());
        String name = txtName.getText();
        String address = txtAddress.getText();
        String tel = txtPhoneNumber.getText();
        Date date = Date.valueOf(lblDate.getText());

        StudentDTO studentDTO = new StudentDTO(id, name, address, tel, date, userID);

        if (!name.isEmpty() || !address.isEmpty() || !tel.isEmpty()) {

                try {
                    boolean isUpdated = studentBO.updateStudent(studentDTO);
                    if (isUpdated) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Student updated!").show();
                        clearFields();
                        initialize();
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

        }
    }

    @FXML
    void idKeyReleaseAction(KeyEvent event) {

    }

    @FXML
    void nameKeyRelaseAction(KeyEvent event) {

    }

    @FXML
    void telKeyRelaseAction(KeyEvent event) {

    }

}
