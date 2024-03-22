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
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import project.Model.Appointment;
import project.Model.report;
import project.Utility.Queries;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
/** This class controls the type report form. */
public class reportTypeController implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    TextArea textArea;
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
     * Defines Lambda expression to help output text with less redundancy and risk of typo.
     * Generates Report of appointments per type.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        report message = (s, m) -> "There exists " + m + " appointment(s) of type: " + s + "\n";
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        ObservableList<String> doneType = FXCollections.observableArrayList();
        ObservableList<String> typeList = FXCollections.observableArrayList();
        String output = "null";
        try {
            allAppointments = Queries.selectAllAppointments();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (Appointment appointment : allAppointments) {
            String type = appointment.getType();
            typeList.add(type);
        }
        for (String string : typeList) {
            int appointmentNum = 0;
            if (!doneType.contains(string)) {
                try {
                    appointmentNum = Queries.selectAppointmentsByType(string).size();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                output += message.getMessage(string, appointmentNum);
                doneType.add(string);
            }
        }
        textArea.setText(output);
    }
}
