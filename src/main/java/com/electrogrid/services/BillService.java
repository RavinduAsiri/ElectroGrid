package com.electrogrid.services;

import com.electrogrid.ApplicationService;
import com.electrogrid.Utility;
import com.electrogrid.model.Bill;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Date;

public class BillService {

    ApplicationService applicationService = new ApplicationService();
    Utility utility = new Utility();

    public String findCustomerBillsById(String customerID) {
        String output = "";
        Connection connection = applicationService.connect();

        if (connection == null) {
            return "Error while connecting to the database for inserting.";
        }

        try {
            String queryCustomer = "SELECT * FROM customer WHERE id = '" + customerID + "'";
            Statement statementCustomer = connection.createStatement();
            ResultSet resultSetCustomer = statementCustomer.executeQuery(queryCustomer);
            while (resultSetCustomer.next()) {
                output = "<h1>My Bills<h4/> <P>Name: " + resultSetCustomer.getString("name") + "<P/> <P>Account ID: " + resultSetCustomer.getString("accountId") + "<P/>";
                output += "<table border='1'><tr><th>ID</th>" + "<th>Date</th><th>Previous Balance</th>" + "<th>Charge For the Period</th>" + "<th>Current Balance</th>" + "<th>Units</th>" + "<th>Payment Status/ID</th>" + "<th>Payment Amount</th>";
            }
            String query = "SELECT * FROM bill LEFT JOIN payment ON bill.payment_id = payment.id WHERE customer_id = '" + customerID + "' ORDER BY bill.date DESC";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            Bill bill = new Bill();

            while (resultSet.next()){
                String payment = "";
                bill.setId(resultSet.getInt("id"));
                bill.setDate(resultSet.getDate("date"));
                bill.setPreviousBalance(resultSet.getBigDecimal("previousBalance"));
                bill.setChargeForPeriod(resultSet.getBigDecimal("chargeForPeriod"));
                bill.setCurrentBalance(resultSet.getBigDecimal("currentBalance"));
                bill.setUnits(resultSet.getInt("units"));
                if (resultSet.getString("payment_id") == null){
                    payment = "Unpaid";
                }else {
                    payment = "Paid ("+ resultSet.getString("payment_id") +")";
                }
                String paymentAmount = resultSet.getString("amount");
                output += "<tr><td>" + bill.getId() + "</td>";
                output += "<td>" + bill.getDate().toString() + "</td>";
                output += "<td>" + bill.getPreviousBalance() + "</td>";
                output += "<td>" + bill.getChargeForPeriod() + "</td>";
                output += "<td>" + bill.getCurrentBalance() + "</td>";
                output += "<td>" + bill.getUnits() + "</td>";
                output += "<td>" + payment + "</td>";
                output += "<td>" + paymentAmount + "</td>";
            }

            connection.close();
            output += "</table>";

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return output;
    }

    public String updateBil(String billId, String paymentId, BigDecimal amount) {

        String output = "";
        BigDecimal currentAmount = BigDecimal.valueOf(0);
        Connection connection = applicationService.connect();
        if (connection == null) {
            return "Error while connecting to the database for find all employees.";
        }

        try {

            String queryBill = "SELECT * FROM bill WHERE id='" + billId + "'";
            Statement statementCustomer = connection.createStatement();
            ResultSet resultSetCustomer = statementCustomer.executeQuery(queryBill);
            while (resultSetCustomer.next()){
               currentAmount = resultSetCustomer.getBigDecimal("currentBalance");
            }

            BigDecimal newCurrentAmount = currentAmount.subtract(amount);

            String query = "UPDATE `bill` SET `payment_id` = ?, `currentBalance` = ? WHERE (`id` = ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, paymentId);
            preparedStatement.setBigDecimal(2, newCurrentAmount);
            preparedStatement.setString(3, billId);
            int executeUpdate = preparedStatement.executeUpdate();
            connection.close();

            if (executeUpdate < 1){
                output = "Payment failed";
            }else {
                output = "Payment successful";
            }

        } catch (SQLException e) {
            output = "Error while updating the employee";
            e.printStackTrace();
        }
        return output;
    }

    public String createNewBill(Date date, String customerId, int units, String currentBalance) {
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        Connection connection = applicationService.connect();
        BigDecimal chargeForThePeriod = utility.calculateAmountUsingUnits(units);
        try{
            String queryCustomer = "SELECT * FROM customer WHERE id = '" + customerId + "'";
            Statement statementCustomer = connection.createStatement();
            ResultSet resultSetCustomer = statementCustomer.executeQuery(queryCustomer);

            if (!resultSetCustomer.next()){
                return "Invalid Customer ID";
            }
            String query = "INSERT INTO bill(`date`, `previousBalance`, `chargeForPeriod`, `units`, `customer_id`, `currentBalance`) VALUES (?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1, sqlDate);
            preparedStatement.setBigDecimal(2, BigDecimal.valueOf(Long.parseLong(currentBalance)));
            preparedStatement.setBigDecimal(3, chargeForThePeriod);
            preparedStatement.setInt(4, units);
            preparedStatement.setString(5, customerId);
            preparedStatement.setBigDecimal(6, chargeForThePeriod.add(BigDecimal.valueOf(Long.parseLong(currentBalance))));

            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
            return "The bill was successfully created!";

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Something Went Wrong!";
    }

    public String deleteBill(int id) {

        String output = "";
        Connection connection = applicationService.connect();
        if (connection == null) {
            return "Error while connecting to the database for delete Bills";
        }

        try {

            String query = "DELETE FROM `bill` WHERE (`id` = ?)";
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

    public String updateBill(Bill bill) {
        String output = "";
        Connection connection = applicationService.connect();
        if (connection == null) {
            return "Error while connecting to the database for update customer.";
        }

        try {

            String query = "UPDATE `bill` SET `date` = ?, `previousBalance` = ?, `chargeForPeriod` = ?, `units` = ?  WHERE (`id` = ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            java.sql.Date sqlDate = new java.sql.Date(bill.getDate().getTime());
            preparedStatement.setDate(1, sqlDate);
            preparedStatement.setBigDecimal(2, bill.getPreviousBalance());
            preparedStatement.setBigDecimal(3, bill.getChargeForPeriod());
            preparedStatement.setInt(4, bill.getUnits());
            preparedStatement.setInt(5, bill.getId());

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
}
