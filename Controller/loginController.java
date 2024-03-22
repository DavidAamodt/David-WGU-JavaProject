package project.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project.Model.Appointment;
import project.Utility.Queries;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;
/** This class controls the login form. */
public class loginController implements Initializable {

    Stage stage;
    Parent scene;
    @FXML
    Label LoginTitle;
    @FXML
    Label UsernameLabel;
    @FXML
    Label PasswordLabel;
    @FXML
    TextField UsernameField;
    @FXML
    TextField PasswordField;
    @FXML
    Label LocationLabel;
    @FXML
    Button LoginButton;
    @FXML
    Label CurrentLocation;

    /** Initializes the controller.
     * Sets labels and buttons to french based on computer language settings.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        CurrentLocation.setText(TimeZone.getDefault().getID());

        ResourceBundle rb = ResourceBundle.getBundle("project/nat_fr", Locale.getDefault());


        if (Locale.getDefault().getLanguage().equals("fr")) {
            LoginTitle.setText(rb.getString("Login"));
            UsernameLabel.setText(rb.getString("Username") + ":");
            UsernameField.setPromptText(rb.getString("Username"));
            PasswordLabel.setText(rb.getString("Password") + ":");
            PasswordField.setPromptText(rb.getString("Password"));
            LoginButton.setText(rb.getString("Login"));
            LocationLabel.setText(rb.getString("Location") + ":");
        }


    }
    /** Gives alert if there is or is not an appointment within the next 15 min. */
    public void checkUpcomingAppointments() throws SQLException {
        int upcoming = 0;
        LocalDateTime currentDateTime = LocalDateTime.now();
        for (Appointment appointment : Queries.selectAllAppointments()){
            LocalDateTime appointmentTime = appointment.getStartDT().toLocalDateTime();
            if ((currentDateTime.isBefore(appointmentTime)) && ((currentDateTime.plusMinutes(15).isAfter(appointmentTime)) || (currentDateTime.plusMinutes(15).isEqual(appointmentTime)))) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Upcoming Appointment Detected");
                alert.setContentText("The appointment with ID: " + appointment.getAppointmentID() + " is scheduled for " + appointment.getStartDT());
                alert.showAndWait();
                upcoming++;
            }
        }
        if (upcoming == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Upcoming Appointment Not Detected");
            alert.setContentText("There are no appointments scheduled in the next 15 min!");
            alert.showAndWait();
        }
    }
    /** Checks input username and password against Users database table.
     * Redirects to appointments upon correct information entered.
     * Calls checkUpcomingAppointments upon successfully login.
     * @param actionEvent This method is triggered by an action event.
     * */
    public void LoginAttempt(ActionEvent actionEvent) throws SQLException, IOException {
        System.out.println("Login Attempted");
        String username = UsernameField.getText();
        String password = PasswordField.getText();
        if(Queries.tryLogin(username, password) == 1){
            System.out.println("Login Successful");

            String loginInfo = "Login Successful, Timestamp: " + Timestamp.valueOf(LocalDateTime.now()) + " Username Entered: " + username + " Password Entered: " + password + "\n";
            Files.write(Paths.get("login_activity.txt"), loginInfo.getBytes(), StandardOpenOption.APPEND);

            checkUpcomingAppointments();
            stage = (Stage)((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/project/view/appointments.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else {
            System.out.println("Login Failed");

            String loginInfo = "Login Failed, Timestamp: " + Timestamp.valueOf(LocalDateTime.now()) + " Username Entered: " + username + " Password Entered: " + password + "\n";
            Files.write(Paths.get("login_activity.txt"), loginInfo.getBytes(), StandardOpenOption.APPEND);

            if (Locale.getDefault().getLanguage().equals("fr")){
                Alert loginFail = new Alert(Alert.AlertType.ERROR);
                loginFail.setTitle("La connexion a échoué");
                loginFail.setContentText("Veuillez vérifier vos identifiant et mot de passe puis ré-essayer.");
                loginFail.showAndWait();
            }
            else {
                Alert loginFail = new Alert(Alert.AlertType.ERROR);
                loginFail.setTitle("Login Failed");
                loginFail.setContentText("Please check your username and password and try again.");
                loginFail.showAndWait();
            }
        }
    }
}
