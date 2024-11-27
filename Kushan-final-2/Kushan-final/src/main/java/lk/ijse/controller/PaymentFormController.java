package lk.ijse.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.PaymentBO;
import lk.ijse.bo.custom.RegistrationBO;
import lk.ijse.bo.custom.StudentBO;
import lk.ijse.dto.PaymentDTO;
import lk.ijse.dto.RegistrationDTO;
import lk.ijse.tdm.PaymentTM;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PaymentFormController {

    @FXML
    private JFXComboBox<String> cmbPaymentMethod;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colPaymentDate;

    @FXML
    private TableColumn<?, ?> colPaymentId;

    @FXML
    private TableColumn<?, ?> colPaymentMethod;

    @FXML
    private TableColumn<?, ?> colRegistrationId;

    @FXML
    private Label lblBalance;

    @FXML
    private Label lblPaidAmount;

    @FXML
    private Label lblPaymentDate;

    @FXML
    private Label lblPaymentId;

    @FXML
    private Label lblProgramFee;

    @FXML
    private Label lblProgramName;

    @FXML
    private Label lblRegId;

    @FXML
    private Label lblStudentId;

    @FXML
    private TableView<PaymentTM> tblPayment;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtRegSearch;

    PaymentBO paymentBO  = (PaymentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PAYMENT);
    StudentBO studentBO  = (StudentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.STUDENT);
    RegistrationBO registrationBO  = (RegistrationBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.REGISTRATION);

    public void initialize() {
        lblPaymentDate.setText(LocalDate.now().toString());
        generateNewPaymentId();
        setPaymentType();
        setCellValueFactory();

        // Ensures the cursor goes to txtRegSearch when the payment UI is opened
        Platform.runLater(() -> txtRegSearch.requestFocus());

    }


    private void setCellValueFactory() {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colRegistrationId.setCellValueFactory(new PropertyValueFactory<>("registrationId"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colPaymentDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        colPaymentMethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));

    }

    private void setPaymentType() {
        ObservableList<String> paymentType = FXCollections.observableArrayList();
        cmbPaymentMethod.setValue("Cash");

        paymentType.add("Cash");
        paymentType.add("Card");

        cmbPaymentMethod.setItems(paymentType);
    }

    private void generateNewPaymentId() {
        try {
            String nextStudentId = paymentBO.generateNewID();

            lblPaymentId.setText(nextStudentId);
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }













    @FXML
    public void btnPayOnAction(ActionEvent actionEvent) {
        try {

            int payId = Integer.parseInt(lblPaymentId.getText());
            double paidAmount = Double.parseDouble(txtAmount.getText());
            Date payDate = Date.valueOf(lblPaymentDate.getText());
            String payMethod = cmbPaymentMethod.getValue();
            int regId = Integer.parseInt(lblRegId.getText());

            // Check if any required field is missing
            if (regId < 1 || paidAmount < 1) {
                new Alert(Alert.AlertType.ERROR, "Please fill all the fields correctly!").show();
                return;
            }

            double a = Double.parseDouble(lblPaidAmount.getText());

            // registration table eke thiyena paidamount ekata aluthin pay karan amount eka ekathu kranawa
            double amount = paidAmount + a;

            RegistrationDTO registrationDTO = new RegistrationDTO(regId, amount);
            PaymentDTO paymentDTO = new PaymentDTO(payId, new RegistrationDTO(regId), paidAmount, payDate, payMethod);

            // Save registration and handle the result
            boolean isSaved = paymentBO.savePayment(paymentDTO, registrationDTO);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Payment Successful!").show();
                refreshPageMethod(amount);
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save the payment").show();
            }

        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid number format! Please check your inputs.").show();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Please check all fields entered correctly!").show();
        }

    }

    void refreshPageMethod(double amount) throws SQLException, IOException, ClassNotFoundException {
        loadPaymentDetails();
        btnSearchOnAction(new ActionEvent());

        lblPaidAmount.setText(String.valueOf(amount));

        double fee = Double.parseDouble(lblProgramFee.getText());
        double paid = Double.parseDouble(lblPaidAmount.getText());
        double balance = fee - paid;

        lblBalance.setText(String.valueOf(balance));;

        txtAmount.setText(null);
        txtAmount.setStyle(null);
    }







    @FXML
    void btnSearchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        int regId = Integer.parseInt(txtRegSearch.getText());
        txtAmount.requestFocus();

        List<Object[]> regDetails = registrationBO.searchForPayment(regId);

        if (regDetails == null || regDetails.isEmpty()) {
            System.out.println("No results found for regId: " + regId);
            return;
        }

        for (Object obj : regDetails) {
            Object[] row = (Object[]) obj;

            lblRegId.setText(String.valueOf(row[0]));
            lblStudentId.setText(String.valueOf(row[1]));
            lblPaidAmount.setText(String.valueOf(row[2]));
            lblProgramName.setText(String.valueOf(row[3]));
            lblProgramFee.setText(String.valueOf(row[4]));

            double balance = ((Double) row[4]) - ((Double) row[2]);
            lblBalance.setText(String.valueOf(balance));
        }

        loadPaymentDetails();
    }

    void loadPaymentDetails() {
        try {

            tblPayment.getItems().clear();
            // Fetch payment details using the registration ID
            ArrayList<PaymentDTO> paymentList = paymentBO.getPaymentDetails(Integer.parseInt(lblRegId.getText()));

            for (PaymentDTO paymentDTO : paymentList) {
                tblPayment.getItems().add(new PaymentTM(
                        paymentDTO.getPaymentId(),
                        paymentDTO.getRegistration().getRegId(),
                        paymentDTO.getAmount(),
                        paymentDTO.getPaymentDate(),
                        paymentDTO.getPaymentMethod()
                ));
            }

        } catch (SQLException | ClassNotFoundException | IOException | NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void btnClearOnAction(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        clearFields();
    }

    void clearFields() throws SQLException, IOException, ClassNotFoundException {
        txtRegSearch.setText(null);
        txtRegSearch.setStyle("");

        txtAmount.setText(null);
        txtAmount.setStyle("");

        tblPayment.getItems().clear();

        lblRegId.setText(null);
        lblStudentId.setText(null);
        lblProgramName.setText(null);
        lblProgramFee.setText(null);
        lblPaidAmount.setText(null);
        lblBalance.setText(null);

        tblPayment.getItems().clear();

        initialize();
    }





    @FXML
    void idKeyReleaseAction(KeyEvent event) {

    }

    @FXML
    void nameKeyRelaseAction(KeyEvent event) {

    }

}
