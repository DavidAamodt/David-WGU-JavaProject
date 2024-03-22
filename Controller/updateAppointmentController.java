package project.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import project.Model.Appointment;
import project.Utility.Queries;
import project.Utility.timeConversion;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
/** This class controls the update appointment form. */
public class updateAppointmentController implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    Label appointmentID;
    @FXML
    TextField titleText;
    @FXML
    TextField descriptionText;
    @FXML
    TextField locationText;
    @FXML
    ChoiceBox<String> contactBox;
    @FXML
    TextField typeText;
    @FXML
    TextField dateText;
    @FXML
    TextField startText;
    @FXML
    TextField endText;
    @FXML
    TextField customerIdText;
    @FXML
    TextField userIdText;

    public static Appointment tempAppointment;
    /** Checks for overlaps between a customers existing appointments and the times proposed for a new appointment.
     * @param customerID the id of the customer the appointment is being checked for.
     * @param appointment_ID the id of the appointment being updated.
     * @param start the new proposed start time.
     * @param end the new proposed end time.
     * */
    public int checkOverlaps(int customerID, int appointment_ID, LocalDateTime start, LocalDateTime end) throws SQLException {
        int passFail = 0;
        for (Appointment appointment : Queries.getCustomersAppointmentsUpdate(customerID, appointment_ID)) {
            LocalDateTime existingStart = appointment.getStartDT().toLocalDateTime();
            LocalDateTime existingEnd = appointment.getEndDT().toLocalDateTime();
            if(((start.isAfter(existingStart)) || (start.isEqual(existingStart))) && (start.isBefore(existingEnd))) {
                passFail = 1;
            } else if ((end.isAfter(existingStart)) && ((end.isBefore(existingEnd)) || (end.isEqual(existingEnd)))) {
                passFail = 2;
            } else if (((start.isBefore(existingStart)) || (start.isEqual(existingStart))) && ((end.isEqual(existingEnd)) || (end.isAfter(existingEnd)))){
                passFail = 3;
            }
        }
        return passFail;
    }
    /** Sets tempAppointment to the appointment given
     * @param appointment the appointment tempAppointment is set to equal.
     * */
    public static void receiveAppointment(Appointment appointment){
        tempAppointment = appointment;
    }
    public ArrayList<String> contactsNames;
    /** Sets contactsNames to equal a list of all contacts from the database. */
    public void setContactNames() throws SQLException {
        contactsNames = Queries.getContactList();
    }
    /** Initializes the controller.
     * Populates the form with information from the selected appointment.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setContactNames();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        contactBox.getItems().addAll(contactsNames);

        appointmentID.setText(Integer.toString(tempAppointment.getAppointmentID()));
        titleText.setText(tempAppointment.getTitle());
        descriptionText.setText(tempAppointment.getDescription());
        locationText.setText(tempAppointment.getLocation());
        contactBox.setValue(tempAppointment.getContact());
        typeText.setText(tempAppointment.getType());
        dateText.setText(tempAppointment.getStartDT().toLocalDateTime().toLocalDate().toString());
        startText.setText(tempAppointment.getStartDT().toLocalDateTime().toLocalTime().toString());
        endText.setText(tempAppointment.getEndDT().toLocalDateTime().toLocalTime().toString());
        customerIdText.setText(Integer.toString(tempAppointment.getCustomerID()));
        userIdText.setText(Integer.toString(tempAppointment.getUserID()));
    }
    /** Updates customersController.allCustomer to all customers in the database. */
    public void updateCustomersList() throws SQLException {
        customersController.allCustomers = Queries.selectAllCustomers();
        System.out.println("Customers updated!");
    }
    /** Redirects to appointments form.
     * @param actionEvent This method is triggered by an action event.
     * */
    public void cancelClicked(ActionEvent actionEvent) throws IOException {
        stage = (Stage)((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/project/view/appointments.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /** Updates the existing appointment with the values input in the form.
     * Redirects to the appointments form upon update.
     * @param actionEvent This method is triggered by an action event.
     * */
    public void addAppointmentClicked(ActionEvent actionEvent) throws IOException, SQLException {
        try {
            int ID = Integer.valueOf(appointmentID.getText());
            String title = titleText.getText();
            String description = descriptionText.getText();
            String location = locationText.getText();
            String type = typeText.getText();

            LocalDate date = LocalDate.parse(dateText.getText());
            LocalTime startTime = LocalTime.parse(startText.getText());
            LocalTime endTime = LocalTime.parse(endText.getText());

            LocalDateTime startDateTime = LocalDateTime.of(date, startTime);
            LocalDateTime endDateTime = LocalDateTime.of(date, endTime);
            Timestamp startTimestamp = Timestamp.valueOf(startDateTime);
            Timestamp endTimestamp = Timestamp.valueOf(endDateTime);

            String contactName = contactBox.getValue();
            int userID = Integer.valueOf(userIdText.getText());
            int customerID = Integer.valueOf(customerIdText.getText());
            int contactID = Queries.getContactID(contactName);

            LocalTime businessDayStart = LocalTime.of(8, 0, 0);
            LocalTime businessDayEnd = LocalTime.of(22, 0, 0);

            if (timeConversion.localToET(startDateTime).toLocalTime().isBefore(businessDayStart) || timeConversion.localToET(endDateTime).toLocalTime().isAfter(businessDayEnd)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Time(s) Entered");
                alert.setContentText("Please be sure the entered times are between 8:00 and 22:00 ET and try again!");
                alert.showAndWait();
            }else if (endDateTime.isBefore(startDateTime)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Time(s) Entered");
                alert.setContentText("Please be sure the entered start time is before the entered end time and try again!");
                alert.showAndWait();
            } else if (Objects.equals(timeConversion.localToET(startDateTime).toLocalDate().getDayOfWeek().toString(), "SATURDAY") || Objects.equals(timeConversion.localToET(startDateTime).toLocalDate().getDayOfWeek().toString(), "SUNDAY")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Weekend Date Entered");
                alert.setContentText("Please be sure the date entered does not fall on a saturday or sunday and try again!");
                alert.showAndWait();
            } else if (checkOverlaps(customerID, ID, startDateTime, endDateTime) != 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Conflicting Start or End Time");
                alert.setContentText("Please be sure the entered times do not overlap with an existing appointment!");
                alert.showAndWait();
            } else {
                int rowsAffected = Queries.updateAppointment(ID, title, description, location, type, startTimestamp, endTimestamp, customerID, userID, contactID);
                if (rowsAffected > 0) {
                    System.out.println("Update Successful");
                } else {
                    System.out.println("Update Failed");
                }

                updateCustomersList();

                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/project/view/appointments.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        } catch (RuntimeException | SQLIntegrityConstraintViolationException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid data entered");
            alert.setContentText("Please be sure all fields are filled out and in the correct format and try again!");
            alert.showAndWait();
        }
    }
}
