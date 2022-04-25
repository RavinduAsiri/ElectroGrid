package com.electrogrid.services;

import com.electrogrid.ApplicationService;
import com.electrogrid.model.Employee;

import java.sql.*;

public class EmployeeService {

    ApplicationService applicationService = new ApplicationService();

    public String insertEmployee(Employee employee) {
        String output = "";
        Connection connection = applicationService.connect();

        if (connection == null) {
            return "Error while connecting to the database for inserting.";
        }

        String query = "INSERT INTO employee(`name`, `type`, `email`, `telNo`, `address`, `password`) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getType());
            preparedStatement.setString(3, employee.getEmail());
            preparedStatement.setString(4, employee.getTelNo());
            preparedStatement.setString(5, employee.getAddress());
            preparedStatement.setString(6, employee.getPassword());

            preparedStatement.execute();
            preparedStatement.close();

            output= "Inserted successfully";

        } catch (SQLException e) {

            output = "Error while inserting the item.";
            e.printStackTrace();
        }
        return output;
    }

    public String findAllEmployees() {
        String output = "";
        Connection connection = applicationService.connect();
        if (connection == null) {
            return "Error while connecting to the database for find all employees.";
        }

        try {
            output = "<table border='1'><tr><th>ID</th>" +"<th>Name</th><th>Type</th>" + "<th>Email</th>" + "<th>Telephone</th>" + "<th>Address</th>" +  "<th>Update</th><th>Remove</th></tr>";

            String query = "SELECT * FROM employee;";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            Employee employee = new Employee();

            while (resultSet.next()){
                employee.setId(resultSet.getInt("id"));
                employee.setName(resultSet.getString("name"));
                employee.setType(resultSet.getString("type"));
                employee.setEmail(resultSet.getString("email"));
                employee.setTelNo(resultSet.getString("telNo"));
                employee.setAddress(resultSet.getString("address"));

                output += "<tr><td>" + employee.getId() + "</td>";
                output += "<td>" + employee.getName() + "</td>";
                output += "<td>" + employee.getType() + "</td>";
                output += "<td>" + employee.getEmail() + "</td>";
                output += "<td>" + employee.getTelNo() + "</td>";
                output += "<td>" + employee.getAddress() + "</td>";

                output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"+
                        "<td><form method='post' action='emp.jsp'>"+
                        "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"+ "<input name='empID' type='hidden' value='" + employee.getId() + "'>" + "</form></td></tr>";
            }

            connection.close();
            output += "</table>";


        } catch (SQLException e) {
            output = "Error while find all employees.";
            e.printStackTrace();
        }

        return output;
    }

    public String deleteEmployee(int id) {

        String output = "";
        Connection connection = applicationService.connect();
        if (connection == null) {
            return "Error while connecting to the database for find all employees.";
        }

        try {

            String query = "DELETE FROM `employee` WHERE (`id` = ?)";
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

    public String updateEmployee(Employee employee) {
        String output = "";
        Connection connection = applicationService.connect();
        if (connection == null) {
            return "Error while connecting to the database for find all employees.";
        }

        try {

            String query = "UPDATE `employee` SET `name` = ?, `type` = ?, `email` = ?, `telNo` = ?, `address` = ?, `password` = ?  WHERE (`id` = ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getType());
            preparedStatement.setString(3, employee.getEmail());
            preparedStatement.setString(4, employee.getTelNo());
            preparedStatement.setString(5, employee.getAddress());
            preparedStatement.setString(6, employee.getPassword());
            preparedStatement.setInt(7, employee.getId());

            int executeUpdate = preparedStatement.executeUpdate();
            connection.close();
            if (executeUpdate < 1){
                output = "Invalid ID";
            }else {
                output = "Updated successfully";
            }

        } catch (SQLException e) {
            output = "Error while updating the employee";
            e.printStackTrace();
        }
        return output;
    }
}
