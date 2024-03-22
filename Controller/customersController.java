package project.Controller;

import com.sun.jdi.event.StepEvent;
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
import project.Model.Customer;
import project.Utility.Queries;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
/** This class controls the customers form. */
public class customersController implements Initializable{

    Stage stage;
    Parent scene;

    @FXML
    private TableView<Customer> customersTable;
    @FXML
    private TableColumn<Customer, Integer> customerID;
    @FXML
    private TableColumn<Customer, String> customerName;
    @FXML
    private TableColumn<Customer, String> customerAddress;
    @FXML
    private TableColumn<Customer, String> postalCodeCol;
    @FXML
    private TableColumn<Customer, String> customerPhone;
    @FXML
    private TableColumn<Customer, String> countryCol;
    @FXML
    private TableColumn<Customer, String> customerStateProvince;

    /** Initializes the controller.
     * Calls updateCustomerList.
     * Sets data in customersTable from getAllCustomers.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            updateCustomersList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Customers updated!");

        customersTable.setItems(getAllCustomers());
        customerID.setCellValueFactory(new PropertyValueFactory<Customer,Integer>("ID"));
        customerName.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
        customerAddress.setCellValueFactory(new PropertyValueFactory<Customer, String>("address"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("postalCode"));
        customerPhone.setCellValueFactory(new PropertyValueFactory<Customer, String>("phoneNumber"));
        countryCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("country"));
        customerStateProvince.setCellValueFactory(new PropertyValueFactory<Customer, String>("stateProvince"));
    }
    static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    /** Sets allCustomers to all customers from the customers table in the database. */
    public void updateCustomersList() throws SQLException {
        allCustomers = Queries.selectAllCustomers();
    }
    /** Gets allCustomers. */
    public ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    }
    /** Redirects to appointments from.
     * @param actionEvent This method is triggered by an action event.
     * */
    public void returnAppointmentsClicked(ActionEvent actionEvent) throws IOException {
        stage = (Stage)((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/project/view/appointments.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /** Redirects to the add customer form.
     * @param actionEvent This method is triggered by an action event.
     * */
    public void addCustomerPressed(ActionEvent actionEvent) throws IOException {
        stage = (Stage)((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/project/view/addCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /** Redirects to the update customer from.
     * Sends update form the customer's information.
     * @param actionEvent This method is triggered by an action event.
     * */
    public void updateCustomerClicked(ActionEvent actionEvent) throws IOException {
        if(customersTable.getSelectionModel().getSelectedItem() != null){
            updateCustomerController.receiveCustomer(customersTable.getSelectionModel().getSelectedItem());

            stage = (Stage)((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/project/view/updateCustomer.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else {
            Alert loginFail = new Alert(Alert.AlertType.ERROR);
            loginFail.setTitle("No Customer Selected");
            loginFail.setContentText("Please select the customer you would like to update and try again.");
            loginFail.showAndWait();
        }
    }
    /** Gets confirmation and deletes the selected customer.
     * Shows error if customer is not selected or selected customer has appointments scheduled.
     * Refreshes customer table upon delete.
     * @param actionEvent This method is triggered by an action event.
     * */
    public void deleteCustomerClicked(ActionEvent actionEvent) throws SQLException, IOException {
        Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION, "Selecting 'OK' will delete the selected customer!");

        if(customersTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Customer Selected");
            alert.setContentText("Please select a customer and try again");
            alert.showAndWait();
        }else if(customersTable.getSelectionModel().getSelectedItem().getCustomersAppointments().isEmpty()) {
            Optional<ButtonType> result = deleteAlert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                Alert deleteSuccess = new Alert(Alert.AlertType.INFORMATION, "Delete Successful!");

                if(Queries.deleteCustomer(customersTable.getSelectionModel().getSelectedItem().getID()) != 0){
                    deleteSuccess.showAndWait();

                    stage = (Stage)((Button) actionEvent.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/project/view/customers.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Delete Failed");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Delete Canceled");
                alert.showAndWait();
            }

        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Customer Has Appointments Scheduled");
            alert.setContentText("Please delete the appointment(s) associated with this customer and try again!");
            alert.showAndWait();
        }
    }
}
