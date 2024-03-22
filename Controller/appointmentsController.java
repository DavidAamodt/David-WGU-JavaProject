package project.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import project.Model.Appointment;
import project.Model.Customer;
import project.Utility.Queries;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
/** This class controls the appointments form. */
public class appointmentsController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    Label dateLabel;
    @FXML
    RadioButton monthRadio;
    @FXML
    RadioButton weekRadio;
    @FXML
    TableView<Appointment> appointmentsTable;
    @FXML
    TableColumn<Appointment, Integer> idCol;
    @FXML
    TableColumn<Appointment, String> titleCol;
    @FXML
    TableColumn<Appointment, String> descriptionCol;
    @FXML
    TableColumn<Appointment, String> locationCol;
    @FXML
    TableColumn<Appointment, String> contactCol;
    @FXML
    TableColumn<Appointment, String> typeCol;
    @FXML
    TableColumn<Appointment, Timestamp> startCol;
    @FXML
    TableColumn<Appointment, Timestamp> endCol;
    @FXML
    TableColumn<Appointment, Integer> customerIdCol;
    @FXML
    TableColumn<Appointment, Integer> userIdCol;
    public LocalDate displayedDate;
    /** Redirects to customers form.
     * @param actionEvent This method is triggered by an action event.
     * */
    public void viewCustomersClicked(ActionEvent actionEvent) throws IOException {
        stage = (Stage)((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/project/view/customers.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /** Redirects to login form.
     * @param actionEvent This method is triggered by an action event.
     * */
    public void logOutClicked(ActionEvent actionEvent) throws IOException {
        stage = (Stage)((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/project/view/login.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    private ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    /** Sets allAppointments to a list of all appointments in the next 31 days. */
    public void updateAppointmentsMonth() throws SQLException {
        displayedDate = LocalDate.now();
        allAppointments = Queries.selectMonthAppointments(displayedDate);
        dateLabel.setText(displayedDate + " - " + displayedDate.plusDays(30));
    }
    /** Sets allAppointments to a lost of all appointments in the next 7 days. */
    public void updateAppointmentsWeek() throws SQLException {
        displayedDate = LocalDate.now();
        allAppointments = Queries.selectWeekAppointments(displayedDate);
        dateLabel.setText(displayedDate + " - " + displayedDate.plusDays(6));
    }
    /** Sets allAppointments to the next seven-day period after what is currently set and displays it in the appointmentsTable. */
    public void nextWeekAppointments() throws SQLException {
        displayedDate = displayedDate.plusDays(7);
        allAppointments = Queries.selectWeekAppointments(displayedDate);
        dateLabel.setText(displayedDate + " - " + displayedDate.plusDays(6));

        appointmentsTable.setItems(allAppointments);
        idCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startDT"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endDT"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
    }
    /** Sets allAppointments to the previous seven-day period before what is currently set and displays it in the appointmentsTable. */
    public void previousWeekAppointments() throws SQLException {
        displayedDate = displayedDate.minusDays(7);
        allAppointments = Queries.selectWeekAppointments(displayedDate);
        dateLabel.setText(displayedDate + " - " + displayedDate.plusDays(6));

        appointmentsTable.setItems(allAppointments);
        idCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startDT"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endDT"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
    }
    /** Sets allAppointments to the next thirty-one-day period after what is currently set and displays it in the appointmentsTable. */
    public void nextMonthAppointments() throws SQLException {
        displayedDate = displayedDate.plusDays(31);
        allAppointments = Queries.selectMonthAppointments(displayedDate);
        dateLabel.setText(displayedDate + " - " + displayedDate.plusDays(30));

        appointmentsTable.setItems(allAppointments);
        idCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startDT"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endDT"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
    }
    /** Sets allAppointments to the previous thirty-one-day period before what is currently set and displays it in the appointmentsTable. */
    public void previousMonthAppointments() throws SQLException {
        displayedDate = displayedDate.minusDays(31);
        allAppointments = Queries.selectMonthAppointments(displayedDate);
        dateLabel.setText(displayedDate + " - " + displayedDate.plusDays(30));

        appointmentsTable.setItems(allAppointments);
        idCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startDT"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endDT"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
    }
    /** Initializes the controller and fills appointmentsTable with the next month of appointments. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            updateAppointmentsMonth();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Appointments updated!");
        System.out.println("Appointments set to month view");

        appointmentsTable.setItems(allAppointments);
        idCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startDT"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endDT"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
    }
    /** Redirects to the add appointment form.
     * @param actionEvent This method is triggered by an action event.
     * */
    public void addAppointmentClicked(ActionEvent actionEvent) throws IOException {
        stage = (Stage)((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/project/view/addAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /** Redirects to the update appointment form.
     * Sends the appointment information to the update form.
     * Shows error if appointment is not selected in the table.
     * @param actionEvent This method is triggered by an action event.
     * */
    public void updateAppointmentClicked(ActionEvent actionEvent) throws IOException {
        if(appointmentsTable.getSelectionModel().getSelectedItem() != null) {
            updateAppointmentController.receiveAppointment(appointmentsTable.getSelectionModel().getSelectedItem());

            stage = (Stage)((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/project/view/updateAppointment.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Appointment Selected");
            alert.setContentText("Please select an appointment and try again");
            alert.showAndWait();
        }
    }
    /** Gets confirmation and deletes selected appointment.
     * Shows error if an appointment is not selected in the table.
     * Shows message upon delete.
     * Refreshes appointmentTable upon delete.
     * @param actionEvent This method is triggered by an action event.
     * */
    public void deleteClicked(ActionEvent actionEvent) throws SQLException, IOException {
        if(appointmentsTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Appointment Selected");
            alert.setContentText("Please select an appointment and try again");
            alert.showAndWait();
        }else {
            Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION, "Selecting 'OK' will delete the selected appointment!");
            Optional<ButtonType> result = deleteAlert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {

                if(Queries.deleteAppointment(appointmentsTable.getSelectionModel().getSelectedItem().getAppointmentID()) != 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Delete Successful!");
                    alert.setContentText("Appointment deleted with ID: " + appointmentsTable.getSelectionModel().getSelectedItem().getAppointmentID() + " and Type: " + appointmentsTable.getSelectionModel().getSelectedItem().getType());
                    alert.showAndWait();

                    stage = (Stage)((Button) actionEvent.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/project/view/appointments.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Delete Failed");
                    alert.showAndWait();
                }
            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Delete Canceled");
                alert.showAndWait();
            }
        }
    }
    /** Displays appointments in appointmentsTable with start date in the next 31 days.
     * @param actionEvent This method is triggered by an action event.
     * */
    public void monthSelected(ActionEvent actionEvent) {
        try {
            updateAppointmentsMonth();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Appointments set to month view");

        appointmentsTable.setItems(allAppointments);
        idCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startDT"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endDT"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
    }
    /** Displays appointments in appointmentsTable with start date in the next 7 days.
     * @param actionEvent This method is triggered by an action event.
     * */
    public void weekSelected(ActionEvent actionEvent) {
        try {
            updateAppointmentsWeek();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Appointments set to week view");

        appointmentsTable.setItems(allAppointments);
        idCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startDT"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endDT"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
    }
    /** Calls previousMonthAppointments or previousWeekAppointments based on radio button selection.
     * @param actionEvent This method is triggered by an action event.
     * */
    public void previousGroupClicked(ActionEvent actionEvent) throws SQLException {
        if(monthRadio.isSelected()){
            previousMonthAppointments();
        } else if (weekRadio.isSelected()) {
            previousWeekAppointments();
        }
    }
    /** Calls nextMonthAppointments or nextWeekAppointments based on radio button selection.
     * @param actionEvent This method is triggered by an action event.
     * */
    public void nextGroupClicked(ActionEvent actionEvent) throws SQLException {
        if(monthRadio.isSelected()){
            nextMonthAppointments();
        } else if (weekRadio.isSelected()) {
            nextWeekAppointments();
        }
    }
    /** Redirects to reports form.
     * @param actionEvent This method is triggered by an action event.
     * */
    public void onReportsClicked(ActionEvent actionEvent) throws IOException {
        stage = (Stage)((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/project/view/reports.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
