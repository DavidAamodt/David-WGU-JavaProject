package project.Utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import project.Model.Appointment;
import project.Model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
/** Contains methods with queries for accessing database information. */
public abstract class Queries {
    /** Checks if username and password input are valid from the Users table in the database.
     * @param username username.
     * @param password password.
     * */
    public static int tryLogin(String username, String password) throws SQLException {
        int passFail = 0;
        String sql = "SELECT * FROM USERS";
        JDBC.makePreparedStatement(sql, JDBC.getConnection());
        PreparedStatement ps = JDBC.getPreparedStatement();
        assert ps != null;
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            String user = rs.getString("User_Name");
            String login = rs.getString("Password");
            if(Objects.equals(username, user)){
                if(Objects.equals(password, login)){
                    passFail=1;
                }
            }
        }
        return passFail;
    }
    /** Inserts an appointment into the appointments table given appointment information.
     * @param title title.
     * @param description description.
     * @param location location.
     * @param type type.
     * @param start start date time.
     * @param end end date time.
     * @param customerID customer id.
     * @param userID user id.
     * */
    public static int insertAppointment(String title, String description, String location, String type, Timestamp start, Timestamp end, int customerID, int userID, int contactID) throws SQLException {
        String sql = "INSERT INTO APPOINTMENTS (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        JDBC.makePreparedStatement(sql, JDBC.getConnection());
        PreparedStatement ps = JDBC.getPreparedStatement();
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, start);
        ps.setTimestamp(6, end);
        ps.setInt(7, customerID);
        ps.setInt(8, userID);
        ps.setInt(9, contactID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
    /** Inserts a customer into the customer table with the given information.
     * @param name name.
     * @param address address.
     * @param postalCode postal code.
     * @param phoneNumber phone number.
     * @param stateProvince state or province.
     * */
    public static int insertCustomer(String name, String address, String postalCode, String phoneNumber, String stateProvince) throws SQLException {


        String sql = "INSERT INTO CUSTOMERS (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES(?, ?, ?, ?, ?)";
        JDBC.makePreparedStatement(sql, JDBC.getConnection());
        PreparedStatement ps = JDBC.getPreparedStatement();
        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phoneNumber);
        ps.setInt(5, getDivId(stateProvince));
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
    /** Updates an appointment with a given id with the information given.
     * @param appointmentID appointment id of appointment being updated.
     * @param title title.
     * @param description description.
     * @param location location.
     * @param type type.
     * @param start start.
     * @param end end.
     * @param customerID customer id.
     * @param userID user id.
     * @param contactID contact id.
     * */
    public static int updateAppointment(int appointmentID, String title, String description, String location, String type, Timestamp start, Timestamp end, int customerID, int userID, int contactID) throws SQLException {
        String sql = "UPDATE APPOINTMENTS SET TITLE = ?, DESCRIPTION = ?, LOCATION = ?, TYPE = ?, START = ?, END = ?, CUSTOMER_ID = ?, USER_ID = ?, CONTACT_ID = ? WHERE APPOINTMENT_ID = ?";
        JDBC.makePreparedStatement(sql, JDBC.getConnection());
        PreparedStatement ps = JDBC.getPreparedStatement();

        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, start);
        ps.setTimestamp(6, end);
        ps.setInt(7, customerID);
        ps.setInt(8, userID);
        ps.setInt(9, contactID);
        ps.setInt(10, appointmentID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
    /** Updates an existing customer with the given customer id with the information given.
     * @param customerID customer id.
     * @param name name.
     * @param address address.
     * @param postalCode postal code.
     * @param phoneNumber phone number.
     * @param stateProvince state or province.
     * */
    public static int updateCustomer(int customerID, String name, String address, String postalCode, String phoneNumber, String stateProvince) throws SQLException {
        String sql = "UPDATE CUSTOMERS SET CUSTOMER_NAME = ?, ADDRESS = ?, POSTAL_CODE = ?, PHONE = ?, DIVISION_ID = ? WHERE CUSTOMER_ID = ?";
        JDBC.makePreparedStatement(sql, JDBC.getConnection());
        PreparedStatement ps = JDBC.getPreparedStatement();
        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phoneNumber);
        ps.setInt(5, getDivId(stateProvince));
        ps.setInt(6, customerID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
    /** deletes an appointment with a given appointment id.
     * @param appointmentID appointment id.
     * */
    public static int deleteAppointment(int appointmentID) throws SQLException {
        String sql = "DELETE FROM APPOINTMENTS WHERE APPOINTMENT_ID = ?";
        JDBC.makePreparedStatement(sql, JDBC.getConnection());
        PreparedStatement ps = JDBC.getPreparedStatement();
        ps.setInt(1, appointmentID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
    /** deletes a customer with a given customer id.
     * @param customerId customer id.
     * */
    public static int deleteCustomer(int customerId) throws SQLException {
        String sql = "DELETE FROM CUSTOMERS WHERE CUSTOMER_ID = ?";
        JDBC.makePreparedStatement(sql, JDBC.getConnection());
        PreparedStatement ps = JDBC.getPreparedStatement();
        ps.setInt(1, customerId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
    /** Selects all customers from the customer table. */
    public static ObservableList<Customer> selectAllCustomers() throws SQLException {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM CUSTOMERS";
        JDBC.makePreparedStatement(sql, JDBC.getConnection());
        PreparedStatement ps = JDBC.getPreparedStatement();
        assert ps != null;
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int id = rs.getInt("Customer_ID");
            String name = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phoneNumber = rs.getString("Phone");
            int divisionID = rs.getInt("Division_ID");
            String stateProvince = getDivision(divisionID);
            String country = getCountry(divisionID);

            Customer customer = new Customer(name, address, postalCode, phoneNumber, country, stateProvince);
            customer.setID(id);
            customer.setCustomersAppointments(getCustomersAppointments(id));
            customerList.add(customer);
        }
        return customerList;
    }
    /** Gets a country given a division id.
     * @param divisionID division id.
     * */
    public static String getCountry(int divisionID) throws SQLException {
        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS WHERE DIVISION_ID = ?";
        JDBC.makePreparedStatement(sql, JDBC.getConnection());
        PreparedStatement ps = JDBC.getPreparedStatement();
        ps.setInt(1, divisionID);
        ResultSet rs = ps.executeQuery();
        int countryID = 0;

        while (rs.next()){
            countryID = rs.getInt("Country_ID");
        }

        String countryName = "null";
        if(countryID == 1) {
            countryName = "United States";
        } else if (countryID == 2) {
            countryName = "United Kingdom";
        } else if (countryID == 3) {
            countryName = "Canada";
        } else if (countryID == 0) {
            countryName = "null";
        }
        return countryName;
    }
    /** Gets division name given division id.
     * @param divisionID division id.
     * */
    public static String getDivision(int divisionID) throws SQLException {
        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS WHERE DIVISION_ID = ?";
        JDBC.makePreparedStatement(sql, JDBC.getConnection());
        PreparedStatement ps = JDBC.getPreparedStatement();
        ps.setInt(1, divisionID);
        ResultSet rs = ps.executeQuery();
        String div = "Null";
        while(rs.next()){
            div = rs.getString("Division");
        }
        return div;
    }
    /** gets division id given a division name.
     * @param divisionName division name.
     * */
    public static int getDivId(String divisionName) throws SQLException {
        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS WHERE DIVISION = ?";
        JDBC.makePreparedStatement(sql, JDBC.getConnection());
        PreparedStatement ps = JDBC.getPreparedStatement();
        ps.setString(1, divisionName);
        ResultSet rs = ps.executeQuery();
        int divId = 0;
        while(rs.next()){
            divId = rs.getInt("Division_ID");
        }
        return divId;
    }
    /** Gets contact id given a contact name.
     * @param contactName contact name.
     * */
    public static int getContactID (String contactName) throws SQLException {
        int contactID = 0;
        String sql = "SELECT CONTACT_ID FROM CONTACTS WHERE CONTACT_NAME = ?";
        JDBC.makePreparedStatement(sql, JDBC.getConnection());
        PreparedStatement ps = JDBC.getPreparedStatement();
        ps.setString(1, contactName);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            contactID = rs.getInt("CONTACT_ID");
        }
        return contactID;
    }
    /** Gets a list of all contact names from contacts table. */
    public static ArrayList<String> getContactList() throws SQLException {
        ArrayList<String> contactsNames = new ArrayList<String>();
        String sql = "SELECT CONTACT_NAME FROM CONTACTS";
        JDBC.makePreparedStatement(sql, JDBC.getConnection());
        PreparedStatement ps = JDBC.getPreparedStatement();
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            String name = rs.getString("CONTACT_NAME");
            contactsNames.add(name);
        }
        return contactsNames;
    }
    /** Gets a customers id given their name.
     * @param customerName customers name.
     * */
    public static int getCustomerID(String customerName) throws SQLException {
        int customerId = 0;
        String sql = "SELECT CUSTOMER_ID FROM CUSTOMERS WHERE CUSTOMER_NAME = ?";
        JDBC.makePreparedStatement(sql, JDBC.getConnection());
        PreparedStatement ps = JDBC.getPreparedStatement();
        ps.setString(1, customerName);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            customerId = rs.getInt("CUSTOMER_ID");
        }
        return customerId;
    }
    /** gets a list of customers names from customers table. */
    public static ArrayList<String> getCustomerList() throws SQLException {
        ArrayList<String> customerNames = new ArrayList<>();
        String sql = "SELECT CUSTOMER_NAME FROM CUSTOMERS";
        JDBC.makePreparedStatement(sql, JDBC.getConnection());
        PreparedStatement ps = JDBC.getPreparedStatement();
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            String name = rs.getString("CUSTOMER_NAME");
            customerNames.add(name);
        }
        return customerNames;
    }
    /** Gets a list of a customers appointments given the customers id.
     * @param customerID customers id.
     * */
    public static ObservableList<Appointment> getCustomersAppointments(int customerID) throws SQLException {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM APPOINTMENTS WHERE CUSTOMER_ID = ?";
        JDBC.makePreparedStatement(sql, JDBC.getConnection());
        PreparedStatement ps = JDBC.getPreparedStatement();
        ps.setInt(1, customerID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            int contactID = rs.getInt("Contact_ID");
            String type = rs.getString("Type");
            Timestamp startDT = rs.getTimestamp("Start");
            Timestamp endDT = rs.getTimestamp("End");
            int customersID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            String contactName = getContactName(contactID);
            Appointment appointment = new Appointment(title, description, location, contactName, type, startDT, endDT, customersID, userID);
            appointment.setAppointmentID(appointmentID);
            appointmentList.add(appointment);
        }
        return appointmentList;
    }
    /** gets all of a customers appointments excluding the one with the appointment ID given.
     * @param customerID customers id.
     * @param appointment_ID appointment id.
     * */
    public static ObservableList<Appointment> getCustomersAppointmentsUpdate(int customerID, int appointment_ID) throws SQLException {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM APPOINTMENTS WHERE CUSTOMER_ID = ? AND APPOINTMENT_ID != ?";
        JDBC.makePreparedStatement(sql, JDBC.getConnection());
        PreparedStatement ps = JDBC.getPreparedStatement();
        ps.setInt(1, customerID);
        ps.setInt(2, appointment_ID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            int contactID = rs.getInt("Contact_ID");
            String type = rs.getString("Type");
            Timestamp startDT = rs.getTimestamp("Start");
            Timestamp endDT = rs.getTimestamp("End");
            int customersID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            String contactName = getContactName(contactID);
            Appointment appointment = new Appointment(title, description, location, contactName, type, startDT, endDT, customersID, userID);
            appointment.setAppointmentID(appointmentID);
            appointmentList.add(appointment);
        }
        return appointmentList;
    }
    /** selects all appointments from the appointments table. */
    public static ObservableList<Appointment> selectAllAppointments() throws SQLException {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM APPOINTMENTS";
        JDBC.makePreparedStatement(sql, JDBC.getConnection());
        PreparedStatement ps = JDBC.getPreparedStatement();
        assert ps != null;
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            int contactID = rs.getInt("Contact_ID");
            String type = rs.getString("Type");
            Timestamp startDT = rs.getTimestamp("Start");
            Timestamp endDT = rs.getTimestamp("End");
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            String contactName = getContactName(contactID);
            Appointment appointment = new Appointment(title, description, location, contactName, type, startDT, endDT, customerID, userID);
            appointment.setAppointmentID(appointmentID);
            appointmentList.add(appointment);
        }
        return appointmentList;
    }
    /** selects all appointments from the table with a given type.
     * @param type1 appointment type.
     * */
    public static ObservableList<Appointment> selectAppointmentsByType(String type1) throws SQLException {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM APPOINTMENTS WHERE TYPE = ?";
        JDBC.makePreparedStatement(sql, JDBC.getConnection());
        PreparedStatement ps = JDBC.getPreparedStatement();
        ps.setString(1, type1);
        assert ps != null;
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            int contactID = rs.getInt("Contact_ID");
            String type = rs.getString("Type");
            Timestamp startDT = rs.getTimestamp("Start");
            Timestamp endDT = rs.getTimestamp("End");
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            String contactName = getContactName(contactID);
            Appointment appointment = new Appointment(title, description, location, contactName, type, startDT, endDT, customerID, userID);
            appointment.setAppointmentID(appointmentID);
            appointmentList.add(appointment);
        }
        return appointmentList;
    }
    /** Selects all appointments assigned to a given contact.
     * @param contact contact name.
     * */
    public static ObservableList<Appointment> selectAppointmentsByContact(String contact) throws SQLException {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        int contact_id = getContactID(contact);
        String sql = "SELECT * FROM APPOINTMENTS WHERE CONTACT_ID = ?";
        JDBC.makePreparedStatement(sql, JDBC.getConnection());
        PreparedStatement ps = JDBC.getPreparedStatement();
        ps.setInt(1, contact_id);
        assert ps != null;
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            int contactID = rs.getInt("Contact_ID");
            String type = rs.getString("Type");
            Timestamp startDT = rs.getTimestamp("Start");
            Timestamp endDT = rs.getTimestamp("End");
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            String contactName = getContactName(contactID);
            Appointment appointment = new Appointment(title, description, location, contactName, type, startDT, endDT, customerID, userID);
            appointment.setAppointmentID(appointmentID);
            appointmentList.add(appointment);
        }
        return appointmentList;
    }
    /** selects all appointments belonging to a given customer.
     * @param customer customer.
     * */
    public static ObservableList<Appointment> selectAppointmentsByCustomer(String customer) throws SQLException {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        int customer_ID = Queries.getCustomerID(customer);
        String sql = "SELECT * FROM APPOINTMENTS WHERE CUSTOMER_ID = ?";
        JDBC.makePreparedStatement(sql, JDBC.getConnection());
        PreparedStatement ps = JDBC.getPreparedStatement();
        ps.setInt(1, customer_ID);
        assert ps != null;
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            int contactID = rs.getInt("Contact_ID");
            String type = rs.getString("Type");
            Timestamp startDT = rs.getTimestamp("Start");
            Timestamp endDT = rs.getTimestamp("End");
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            String contactName = getContactName(contactID);
            Appointment appointment = new Appointment(title, description, location, contactName, type, startDT, endDT, customerID, userID);
            appointment.setAppointmentID(appointmentID);
            appointmentList.add(appointment);
        }
        return appointmentList;
    }
    /** selects all appointments in the next 31 days from a given date..
     * @param date starting at date.
     * */
    public static ObservableList<Appointment> selectMonthAppointments(LocalDate date) throws SQLException {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        LocalDate date1 = date;
        LocalDate date2 = date.plusDays(31);
        Timestamp timestamp1 = Timestamp.valueOf(date1.atStartOfDay());
        Timestamp timestamp2 = Timestamp.valueOf(date2.atStartOfDay());
        String sql = "SELECT * FROM APPOINTMENTS WHERE START BETWEEN ? AND ?";
        JDBC.makePreparedStatement(sql, JDBC.getConnection());
        PreparedStatement ps = JDBC.getPreparedStatement();
        ps.setTimestamp(1, timestamp1);
        ps.setTimestamp(2, timestamp2);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            int contactID = rs.getInt("Contact_ID");
            String type = rs.getString("Type");
            Timestamp startDT = rs.getTimestamp("Start");
            Timestamp endDT = rs.getTimestamp("End");
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            String contactName = getContactName(contactID);
            Appointment appointment = new Appointment(title, description, location, contactName, type, startDT, endDT, customerID, userID);
            appointment.setAppointmentID(appointmentID);
            appointmentList.add(appointment);
        }
        return appointmentList;
    }
    /** selects all appointments in the next 7 days from a given date.
     * @param date date.
     * */
    public static ObservableList<Appointment> selectWeekAppointments(LocalDate date) throws SQLException {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        LocalDate date1 = date;
        LocalDate date2 = date.plusDays(7);
        Timestamp timestamp1 = Timestamp.valueOf(date1.atStartOfDay());
        Timestamp timestamp2 = Timestamp.valueOf(date2.atStartOfDay());
        String sql = "SELECT * FROM APPOINTMENTS WHERE START BETWEEN ? AND ?";
        JDBC.makePreparedStatement(sql, JDBC.getConnection());
        PreparedStatement ps = JDBC.getPreparedStatement();
        ps.setTimestamp(1, timestamp1);
        ps.setTimestamp(2, timestamp2);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            int contactID = rs.getInt("Contact_ID");
            String type = rs.getString("Type");
            Timestamp startDT = rs.getTimestamp("Start");
            Timestamp endDT = rs.getTimestamp("End");
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            String contactName = getContactName(contactID);
            Appointment appointment = new Appointment(title, description, location, contactName, type, startDT, endDT, customerID, userID);
            appointment.setAppointmentID(appointmentID);
            appointmentList.add(appointment);
        }
        return appointmentList;
    }
    /** Gets a contact name given a contact id. */
    public static String getContactName(int contactID) throws SQLException {
        String sql = "SELECT * FROM CONTACTS WHERE CONTACT_ID = ?";
        JDBC.makePreparedStatement(sql, JDBC.getConnection());
        PreparedStatement ps = JDBC.getPreparedStatement();
        ps.setInt(1, contactID);
        ResultSet rs = ps.executeQuery();
        String contactName = null;
        while(rs.next()){
            contactName = rs.getString("Contact_Name");
        }
        return contactName;
    }
}
