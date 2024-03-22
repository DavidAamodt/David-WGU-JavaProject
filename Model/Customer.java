package project.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import project.Utility.Queries;

import java.sql.SQLException;
/** This class contains the constructor and other accessors/modifiers for Customer. */
public class Customer {
    public int ID;
    public String name;
    public String address;
    public String postalCode;
    public String phoneNumber;
    public String country;
    public String stateProvince;
    public ObservableList<Appointment> customersAppointments = FXCollections.observableArrayList();


    /** Gets name. */
    public String getName() {
        return name;
    }
    /** Sets name.
     * @param name name.
     * */
    public void setName(String name) {
        this.name = name;
    }
    /** Gets address. */
    public String getAddress() {
        return address;
    }
    /** Sets address.
     * @param address address.
     * */
    public void setAddress(String address) {
        this.address = address;
    }
    /** Gets postal code. */
    public String getPostalCode() {
        return postalCode;
    }
    /** Sets postal code.
     * @param postalCode postal code.
     * */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    /** Gets phone number. */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /** Sets phone number.
     *
     * @param phoneNumber phone number.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    /** Gets country name. */
    public String getCountry() {
        return country;
    }
    /** Sets country name.
     * @param country country name.
     * */
    public void setCountry(String country) {
        this.country = country;
    }
    /** Gets state or province. */
    public String getStateProvince() {
        return stateProvince;
    }
    /** Sets state or province.
     * @param stateProvince state or province.
     * */
    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    /** Constructor for Customer.
     * @param name name.
     * @param address address.
     * @param postalCode postal code.
     * @param phoneNumber phone number.
     * @param country country.
     * @param stateProvince state or province.
     * */
    public Customer (String name, String address, String postalCode, String phoneNumber, String country, String stateProvince) throws SQLException {
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.country = country;
        this.stateProvince = stateProvince;
    }
    /** Gets customersAppointments. */
    public ObservableList<Appointment> getCustomersAppointments() {
        return customersAppointments;
    }
    /** Sets customersAppointments
     * @param customersAppointments customers appointments.
     * */
    public void setCustomersAppointments(ObservableList<Appointment> customersAppointments) {
        this.customersAppointments = customersAppointments;
    }
    /** Sets ID
     * @param id customer id.
     * */
    public void setID(int id){
        ID = id;
    }
    /** Gets customer id. */
    public int getID(){
        return ID;
    }

}
