package project.Model;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
/** This class contains the constructor and other accessors/modifiers for Appointment. */
public class Appointment {
    int appointmentID;
    String title;
    String description;
    String location;
    String contact;
    String type;
    Timestamp startDT;
    Timestamp endDT;
    int customerID;
    int userID;
    /** Constructor for Appointment.
     * @param title title of appointment.
     * @param description description of appointment.
     * @param location location of appointment.
     * @param contact contact of appointment.
     * @param type type of appointment.
     * @param startDT start date time of appointment.
     * @param endDT end date time of appointment.
     * @param customerID customer id of appointment.
     * @param userID user id of appointment.
     * */
    public Appointment(String title, String description, String location, String contact, String type, Timestamp startDT, Timestamp endDT, int customerID, int userID){
        setTitle(title);
        setDescription(description);
        setLocation(location);
        setContact(contact);
        setType(type);
        setStartDT(startDT);
        setEndDT(endDT);
        setCustomerID(customerID);
        setUserID(userID);
    }
    /** Gets appointmentID. */
    public int getAppointmentID() {
        return appointmentID;
    }
    /** Sets appointmentID.
     * @param appointmentID appointment id.
     * */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }
    /** Gets title. */
    public String getTitle() {
        return title;
    }
    /** Sets title.
     * @param title title.
     * */
    public void setTitle(String title) {
        this.title = title;
    }
    /** Gets description. */
    public String getDescription() {
        return description;
    }
    /** Sets description
     * @param description description.
     * */
    public void setDescription(String description) {
        this.description = description;
    }
    /** Gets location. */
    public String getLocation() {
        return location;
    }
    /** Sets location
     * @param location location.
     * */
    public void setLocation(String location) {
        this.location = location;
    }
    /** Gets contact name. */
    public String getContact() {
        return contact;
    }
    /** Sets contact name.
     * @param contact contact.
     * */
    public void setContact(String contact) {
        this.contact = contact;
    }
    /** Gets type. */
    public String getType() {
        return type;
    }
    /** Sets type.
     * @param type type.
     * */
    public void setType(String type) {
        this.type = type;
    }
    /** Gets customer id. */
    public int getCustomerID() {
        return customerID;
    }

    /** Sets customer id.
     *
     * @param customerID customer id.
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
    /** Gets user id */
    public int getUserID() {
        return userID;
    }
    /** Sets user id.
     * @param userID user id.
     * */
    public void setUserID(int userID) {
        this.userID = userID;
    }
    /** Gets start date time. */
    public Timestamp getStartDT() {
        return startDT;
    }
    /** Sets start date time.
     * @param startDT start date time.
     * */
    public void setStartDT(Timestamp startDT) {
        this.startDT = startDT;
    }
    /** Gets end date time. */
    public Timestamp getEndDT() {
        return endDT;
    }
    /** Sets end date time.
     * @param endDT end date time.
     * */
    public void setEndDT(Timestamp endDT) {
        this.endDT = endDT;
    }
}
