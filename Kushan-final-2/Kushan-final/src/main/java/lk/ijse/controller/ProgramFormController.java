package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.ProgramBO;
import lk.ijse.dto.ProgramDTO;
import lk.ijse.tdm.ProgramTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

//import static sun.security.krb5.internal.crypto.KeyUsage.isValid;


public class ProgramFormController {

    @FXML
    private JFXButton btnProgramDelete;

    @FXML
    private JFXButton btnProgramSave;

    @FXML
    private JFXButton btnProgramUpdate;

    @FXML
    private TableColumn<?, ?> colDuration;

    @FXML
    private TableColumn<?, ?> colFee;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private Label lblProgramId;

    @FXML
    private TableView<ProgramTM> tblProgram;

    @FXML
    private TextField txtProgramDuration;

    @FXML
    private TextField txtProgramFee;

    @FXML
    private TextField txtProgramName;

    ProgramBO programBO  = (ProgramBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PROGRAM);

    public void initialize() {
        generateNewProgramID();
        setCellValueFactory();
        loadAllPrograms();
        showSelectedUserDetails();
//        buttonsDisableAndEnable();
//        clickEnterButtonMoveCursor();
    }

    private void showSelectedUserDetails() {
        ProgramTM selectedUser = tblProgram.getSelectionModel().getSelectedItem();
        tblProgram.setOnMouseClicked(event -> showSelectedUserDetails());
        if (selectedUser != null) {
            lblProgramId.setText(String.valueOf(selectedUser.getId()));
            txtProgramName.setText(selectedUser.getName());
            txtProgramDuration.setText(selectedUser.getDuration());
            txtProgramFee.setText(String.valueOf(selectedUser.getFee()));

            btnProgramUpdate.setDisable(false);
            btnProgramSave.setDisable(true);

            btnProgramDelete.setDisable(false);
        }
    }


    private void generateNewProgramID() {
        try {
            String nextProgramId = programBO.generateNewID();

            lblProgramId.setText(nextProgramId);
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void loadAllPrograms() {
        tblProgram.getItems().clear();
        try {

            ArrayList<ProgramDTO> allPrograms = programBO.getAllPrograms();

            for (ProgramDTO programDTO : allPrograms) {
                tblProgram.getItems().add(new ProgramTM(programDTO.getProgramId(), programDTO.getProgramName(), programDTO.getDuration(), programDTO.getFee()));
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("fee"));


    }



    void clearFields() {

        txtProgramName.clear();
        txtProgramDuration.clear();
        txtProgramFee.clear();

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
         clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
        String id = lblProgramId.getText();
        String name = txtProgramName.getText();
        String fee = (txtProgramFee.getText());
        String duration = txtProgramDuration.getText();



        if (!id.isEmpty() || !name.isEmpty() || !fee.isEmpty() || !duration.isEmpty()) {

           boolean isSaved = programBO.deleteProgram(id);


            initialize();
            clearFields();

        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        String id = lblProgramId.getText();
        String name = txtProgramName.getText();
        String fee = (txtProgramFee.getText());
        String duration = txtProgramDuration.getText();

        if (!id.isEmpty() || !name.isEmpty() || !fee.isEmpty() || !duration.isEmpty()) {
//            if (isValied()) {

                ProgramDTO programDTO = new ProgramDTO(id, name, duration, fee);

//                try {
                    boolean isSaved = programBO.saveProgram(programDTO);
                    if (isSaved) {
                        new Alert(Alert.AlertType.CONFIRMATION, "User saved!").show();
                        clearFields();
                        initialize();
                    }
//                } catch (SQLException | ClassNotFoundException | IOException e) {
//                    throw new RuntimeException(e);
//                }
//            } else {
//                new Alert(Alert.AlertType.ERROR, "Please fill all fields").show();
//            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Please fill all fields").show();
        }
    }

//    private boolean isValied() {
//        boolean nameValid = Regex.setTextColor(lk.ijse.Util.TextField.NAME, txtProgramName);
//        boolean duration = Regex.setTextColor(lk.ijse.Util.TextField.DURATION, txtProgramDuration);
//        boolean fee = Regex.setTextColor(lk.ijse.Util.TextField.PRICEDOT, txtProgramFee);
//
//        return nameValid && duration && fee;
//    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = lblProgramId.getText();
        String name = txtProgramName.getText();
        String fee = (txtProgramFee.getText());
        String duration = txtProgramDuration.getText();

        if (!id.isEmpty() || !name.isEmpty() || !fee.isEmpty() || !duration.isEmpty()) {

                ProgramDTO programDTO = new ProgramDTO(id, name, duration, fee);

                try {
                    boolean isSaved = programBO.updateProgram(programDTO);
                    if (isSaved) {
                        new Alert(Alert.AlertType.CONFIRMATION, "User saved!").show();
                        initialize();
                        clearFields();
                    }
                } catch (SQLException | ClassNotFoundException | IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Please fill all fields").show();
            }

    }

    @FXML
    void durationKeyRelaseAction(KeyEvent event) {

    }

    @FXML
    void feeKeyRelaseAction(KeyEvent event) {

    }

    @FXML
    void nameKeyRelaseAction(KeyEvent event) {

    }

}
