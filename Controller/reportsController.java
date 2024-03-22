package project.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
/** This class controls the reports form. */
public class reportsController {
    Stage stage;
    Parent scene;
    /** Redirects to appointments form.
     * @param actionEvent This method is triggered by an action event.
     * */
    public void returnClicked(ActionEvent actionEvent) throws IOException {
        stage = (Stage)((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/project/view/appointments.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /** Redirects to the month report form.
     * @param actionEvent This method is triggered by an action event.
     * */
    public void byMonthClicked(ActionEvent actionEvent) throws IOException {
        stage = (Stage)((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/project/view/reportMonth.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /** Redirects to the type report from.
     * @param actionEvent This method is triggered by an action event.
     * */
    public void typeClicked(ActionEvent actionEvent) throws IOException {
        stage = (Stage)((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/project/view/reportType.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /** Redirects to the contact report form.
     * @param actionEvent This method is triggered by an action event.
     * */
    public void contactClicked(ActionEvent actionEvent) throws IOException {
        stage = (Stage)((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/project/view/reportContact.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /** Redirects to the customer report form.
     * @param actionEvent This method is triggered by an action event.
     * */
    public void customerClicked(ActionEvent actionEvent) throws IOException {
        stage = (Stage)((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/project/view/reportCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
