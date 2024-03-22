package project.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
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
import project.Utility.Queries;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.ResourceBundle;
/** This class controls the contact report form. */
public class reportContactController implements Initializable {
    Stage stage;
    Parent scene;
    @FXML
    ChoiceBox<String> contactBox;
    @FXML
    TableView<Appointment> contactTable;
    @FXML
    TableColumn<Integer, Appointment> appointmentIdCol;
    @FXML
    TableColumn<String, Appointment> titleCol;
    @FXML
    TableColumn<String, Appointment> typeCol;
    @FXML
    TableColumn<String, Appointment> descriptionCol;
    @FXML
    TableColumn<Timestamp, Appointment> startCol;
    @FXML
    TableColumn<Timestamp, Appointment> endCol;
    @FXML
    TableColumn<Integer, Appointment> customerIdCol;

    public ArrayList<String> contactsNames;
    /** Sets contactNames to equal a list of all contacts from the database. */
    public void setContactNames() throws SQLException {
        contactsNames = Queries.getContactList();
    }
    /** Initializes the controller.
     * Fills the contact box with the names of all contacts.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setContactNames();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        contactBox.getItems().addAll(contactsNames);
    }
    /** Redirects to the reports form.
     * @param actionEvent This method is triggered by an action event.
     * */
    public void backClicked(ActionEvent actionEvent) throws IOException {
        stage = (Stage)((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/project/view/reports.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /** Populates the tableView with all appointments associated with the selected contact.
     * @param actionEvent This method is triggered by an action event.
     * */
    public void viewClicked(ActionEvent actionEvent) throws SQLException {
        if (contactBox.getValue() != null) {
            String contactName = contactBox.getValue();
            ObservableList<Appointment> contactAppointments = FXCollections.observableArrayList();
            contactAppointments = Queries.selectAppointmentsByContact(contactName);

            contactTable.setItems(contactAppointments);
            appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            startCol.setCellValueFactory(new PropertyValueFactory<>("startDT"));
            endCol.setCellValueFactory(new PropertyValueFactory<>("endDT"));
            customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Contact Selected");
            alert.setContentText("Please select a contact from the dropdown and try again!");
            alert.showAndWait();
        }
    }
}
