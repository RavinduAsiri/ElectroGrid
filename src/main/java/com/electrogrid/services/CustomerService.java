package com.electrogrid.services;

import com.electrogrid.ApplicationService;
import com.electrogrid.model.Customer;

import java.sql.*;

public class CustomerService {

    ApplicationService applicationService = new ApplicationService();

    public String insertCustomer(Customer customer) {
        String output = "";
        Connection connection = applicationService.connect();

        if (connection == null) {
            return "Error while connecting to the database for inserting.";
        }

        String query = "INSERT INTO customer(`name`, `accountId`, `telNo`, `email`, `address`, `nic`) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getAccountId());
            preparedStatement.setString(3, customer.getTelNo());
            preparedStatement.setString(4, customer.getEmail());
            preparedStatement.setString(5, customer.getAddress());
            preparedStatement.setString(6, customer.getNic());

            preparedStatement.execute();
            preparedStatement.close();

            output = "Inserted successfully";

        } catch (SQLException e) {

            output = "Error while inserting the item.";
            e.printStackTrace();
        }
        return output;
    }

    public String findAllCustomers() {
        String output = "";
        Connection connection = applicationService.connect();
        if (connection == null) {
            return "Error while connecting to the database for find all customers.";
        }

        try {
            output = "<table border='1'><tr><th>ID</th>" + "<th>Name</th><th>AccountID</th>"+ "<th>Telephone</th>" + "<th>Email</th>" + "<th>Address</th>" + "<th>NIC</th>" + "<th>Update</th><th>Remove</th></tr>";

            String query = "SELECT * FROM customer;";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            Customer customer = new Customer();

            while (resultSet.next()) {
                customer.setId(resultSet.getInt("id"));
                customer.setName(resultSet.getString("name"));
                customer.setAccountId(resultSet.getString("accountId"));
                customer.setTelNo(resultSet.getString("telNo"));
                customer.setEmail(resultSet.getString("email"));
                customer.setAddress(resultSet.getString("address"));
                customer.setNic(resultSet.getString("nic"));

                output += "<tr><td>" + customer.getId() + "</td>";
                output += "<td>" + customer.getName() + "</td>";
                output += "<td>" + customer.getAccountId() + "</td>";
                output += "<td>" + customer.getTelNo() + "</td>";
                output += "<td>" + customer.getEmail() + "</td>";
                output += "<td>" + customer.getAddress() + "</td>";
                output += "<td>" + customer.getNic() + "</td>";

                output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>" +
                        "<td><form method='post' action='emp.jsp'>" +
                        "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>" + "<input name='cusID' type='hidden' value='" + customer.getId() + "'>" + "</form></td></tr>";
            }

            connection.close();
            output += "</table>";


        } catch (SQLException e) {
            output = "Error while find all customers.";
            e.printStackTrace();
        }

        return output;
    }

    public String findCustomerById(String id) {
        String output = "";
        Connection connection = applicationService.connect();
        if (connection == null) {
            return "Error while connecting to the database for find all customers.";
        }

        try {
            output = "<h3>My Profile</h3></br><table border='1'><tr><th>ID</th>" + "<th>Name</th><th>AccountID</th>"+ "<th>Telephone</th>" + "<th>Email</th>" + "<th>Address</th>" + "<th>NIC</th>" + "<th>Password</th>";

            String query = "SELECT * FROM customer WHERE id ='" +id + "'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            Customer customer = new Customer();

            while (resultSet.next()) {
                customer.setId(resultSet.getInt("id"));
                customer.setName(resultSet.getString("name"));
                customer.setAccountId(resultSet.getString("accountId"));
                customer.setTelNo(resultSet.getString("telNo"));
                customer.setEmail(resultSet.getString("email"));
                customer.setAddress(resultSet.getString("address"));
                customer.setNic(resultSet.getString("nic"));
                customer.setPassword(resultSet.getString("password"));

                output += "<tr><td>" + customer.getId() + "</td>";
                output += "<td>" + customer.getName() + "</td>";
                output += "<td>" + customer.getAccountId() + "</td>";
                output += "<td>" + customer.getTelNo() + "</td>";
                output += "<td>" + customer.getEmail() + "</td>";
                output += "<td>" + customer.getAddress() + "</td>";
                output += "<td>" + customer.getNic() + "</td>";
                output += "<td>" + customer.getPassword() + "</td>";

            }

            connection.close();
            output += "</table>";


        } catch (SQLException e) {
            output = "Error while find all customers.";
            e.printStackTrace();
        }

        return output;
    }

    public String updateCustomer(Customer customer) {
        String output = "";
        Connection connection = applicationService.connect();
        if (connection == null) {
            return "Error while connecting to the database for update customer.";
        }

        try {

            String query = "UPDATE `customer` SET `name` = ?, `accountId` = ?, `telNo` = ?, `email` = ?, `address` = ?, `nic` = ?  WHERE (`id` = ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getAccountId());
            preparedStatement.setString(3, customer.getTelNo());
            preparedStatement.setString(4, customer.getEmail());
            preparedStatement.setString(5, customer.getAddress());
            preparedStatement.setString(6, customer.getNic());
            preparedStatement.setInt(7, customer.getId());

            int executeUpdate = preparedStatement.executeUpdate();
            connection.close();
            if (executeUpdate < 1){
                output = "Invalid ID";
            }else {
                output = "Updated successfully";
            }

        } catch (SQLException e) {
            output = "Error while updating the customer";
            e.printStackTrace();
        }
        return output;
    }

    public String updatePasswordByAccountID(String accountID, String password) {
        String output = "";
        Connection connection = applicationService.connect();
        if (connection == null) {
            return "Error while connecting to the database for update customer.";
        }

        try {

            String query = "UPDATE `customer` SET `password` = ?  WHERE (`accountId` = ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, accountID);

            int executeUpdate = preparedStatement.executeUpdate();
            connection.close();
            if (executeUpdate < 1){
                output = "Invalid Account ID";
            }else {
                output = "Successfully registered!";
            }

        } catch (SQLException e) {
            output = "Error while updating the customer";
            e.printStackTrace();
        }
        return output;
    }

    public String deleteCustomer(int id) {

        String output = "";
        Connection connection = applicationService.connect();
        if (connection == null) {
            return "Error while connecting to the database for delete customer";
        }

        try {

            String query = "DELETE FROM `customer` WHERE (`id` = ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            int executeUpdate = preparedStatement.executeUpdate();
            connection.close();
            if (executeUpdate < 1){
                output = "Invalid ID";
            }else {
                output = "Deleted successfully";
            }

        } catch (SQLException e) {

            output = "Error while deleting the employee.";
            e.printStackTrace();
        }

        return output;
    }
}
