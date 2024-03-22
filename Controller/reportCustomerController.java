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
import project.Utility.Queries;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.ResourceBundle;
/** This class controls the customer report form. */
public class reportCustomerController implements Initializable {
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
    public ArrayList<String> customerNames;
    /** Sets customerNames equal to a list of all customers from the database. */
    public void setCustomerNames() throws SQLException {
        customerNames = Queries.getCustomerList();
    }
    /** Redirects to the report form.
     * @param actionEvent This method is triggered by an action event.
     * */
    public void backClicked(ActionEvent actionEvent) throws IOException {
        stage = (Stage)((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/project/view/reports.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /** Initializes the controller.
     * Sets comboBox to contain all customers names.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setCustomerNames();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        contactBox.getItems().addAll(customerNames);
    }
    /** Populates the tableView with all appointments associated with the selected customer.
     * @param actionEvent This method is associated with an action event.
     * */
    public void viewClicked(ActionEvent actionEvent) {
        if (contactBox.getValue() != null) {
            String customerName = contactBox.getValue();
            ObservableList<Appointment> customerAppointments = FXCollections.observableArrayList();
            try {
                customerAppointments = Queries.selectAppointmentsByCustomer(customerName);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            contactTable.setItems(customerAppointments);
            appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            startCol.setCellValueFactory(new PropertyValueFactory<>("startDT"));
            endCol.setCellValueFactory(new PropertyValueFactory<>("endDT"));
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Customer Selected");
            alert.setContentText("Please select a customer from the dropdown and try again!");
            alert.showAndWait();
        }
    }
}
