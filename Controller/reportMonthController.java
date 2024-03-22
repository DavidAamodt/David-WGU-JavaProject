package project.Controller;

import javafx.collections.FXCollections;
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
import java.time.LocalDateTime;
import java.util.ResourceBundle;
/** This class controls the month report form. */
public class reportMonthController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    TextArea textArea;
    /** Redirects to the reports from.
     * @param actionEvent This method is triggered by an action event.
     * */
    public void backClicked(ActionEvent actionEvent) throws IOException {
        stage = (Stage)((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/project/view/reports.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /** Initializes the controller.
     * Generates report on how many appointments per month.
     * Defines lambda expression to help output report with less redundancy and risk of typo.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        report message = (s, m) -> "Total appointments scheduled in " + s + "= " + m;
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        try {
            allAppointments = Queries.selectAllAppointments();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int jan = 0;
        int feb = 0;
        int mar = 0;
        int apr = 0;
        int may = 0;
        int jun = 0;
        int jul = 0;
        int aug = 0;
        int sep = 0;
        int oct = 0;
        int nov = 0;
        int dec = 0;
        for (Appointment appointment : allAppointments) {
            LocalDateTime startDT = appointment.getStartDT().toLocalDateTime();
            int month = startDT.getMonthValue();

            if (month == 1){
                jan = jan+1;
            } else if (month == 2){
                feb = feb+1;
            } else if (month == 3){
                mar = mar+1;
            } else if (month == 4){
                apr = apr+1;
            }else if (month == 5){
                may = may+1;
            }else if (month == 6){
                jun = jun+1;
            }else if (month == 7){
                jul = jul+1;
            }else if (month == 8){
                aug = aug+1;
            }else if (month == 9){
                sep = sep+1;
            }else if (month == 10){
                oct = oct+1;
            }else if (month == 11){
                nov = nov+1;
            }else if (month == 12){
                dec = dec+1;
            }
        }
        String output = message.getMessage("January", jan) + "\n" + message.getMessage("February", feb) + "\n" + message.getMessage("March", mar) + "\n" + message.getMessage("April", apr) + "\n" + message.getMessage("May", may) + "\n" + message.getMessage("June", jun) + "\n" + message.getMessage("July", jul) + "\n" + message.getMessage("August", aug) + "\n" + message.getMessage("September", sep) + "\n" + message.getMessage("October", oct) + "\n" + message.getMessage("November", nov) + "\n" + message.getMessage("December", dec);
        textArea.setText(output);
    }
}
