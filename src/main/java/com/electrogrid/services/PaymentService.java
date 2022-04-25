package com.electrogrid.services;

import com.electrogrid.ApplicationService;
import com.electrogrid.model.Bill;
import com.electrogrid.model.Customer;
import com.electrogrid.model.Payment;

import java.math.BigDecimal;
import java.sql.*;

public class PaymentService {

    ApplicationService applicationService = new ApplicationService();
    BillService billService = new BillService();

    public String createPayment(String billId, Payment payment) {

        String output = "";
        Connection connection = applicationService.connect();
        try {
            String queryBill = "SELECT * FROM bill WHERE id='" + billId + "'";
            Statement statementCustomer = connection.createStatement();
            ResultSet resultSetCustomer = statementCustomer.executeQuery(queryBill);
            if (!resultSetCustomer.next()){
                return "Invalid Bill ID !";
            }
            resultSetCustomer = statementCustomer.executeQuery(queryBill);
            while (resultSetCustomer.next()){
                if (resultSetCustomer.getString("payment_id") != null){
                    return "The bill has already been paid";
                }

            }

            String queryPayment = "INSERT INTO payment(`method`, `refId`, `amount`, `date`) VALUES (?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(queryPayment, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, payment.getMethod());
            preparedStatement.setString(2, payment.getRefId());
            preparedStatement.setBigDecimal(3, payment.getAmount());
            java.sql.Date sqlDate = new java.sql.Date(payment.getDate().getTime());
            preparedStatement.setDate(4, sqlDate);

            preparedStatement.execute();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    output = billService.updateBil(billId, generatedKeys.getString(1), payment.getAmount());
                }
            }
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return output;
    }

    public String findAllPayments() {
            String output = "";
            Connection connection = applicationService.connect();
            if (connection == null) {
                return "Error while connecting to the database for find all Payments.";
            }

            try {
                output = "<table border='1'><tr><th>Payment ID</th>" + "<th>Payment Method</th><th>Reference ID</th>"+ "<th>Amount</th>" + "<th>Payment Date</th>" + "<th>Customer Name</th>" + "<th>Customer Account ID</th>" + "<th>Bill ID</th>";

                String query = "SELECT p.id AS paymentId, p.method, p.refId, p.amount, p.date AS paymentdate, c.name, c.accountId, b.id AS billId FROM payment p LEFT JOIN bill b ON p.id = b.payment_id INNER JOIN customer c ON b.customer_id = c.id";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                Payment payment = new Payment();
                Bill bill = new Bill();
                Customer customer = new Customer();

                while (resultSet.next()) {
                    payment.setId(resultSet.getInt("paymentId"));
                    payment.setMethod(resultSet.getString("method"));
                    payment.setRefId(resultSet.getString("refId"));
                    payment.setAmount(resultSet.getBigDecimal("amount"));
                    payment.setDate(resultSet.getDate("paymentdate"));
                    customer.setName(resultSet.getString("name"));
                    customer.setAccountId(resultSet.getString("accountId"));
                    bill.setId(resultSet.getInt("billId"));


                    output += "<tr><td>" + payment.getId() + "</td>";
                    output += "<td>" + payment.getMethod() + "</td>";
                    output += "<td>" + payment.getRefId() + "</td>";
                    output += "<td>" + payment.getAmount() + "</td>";
                    output += "<td>" + payment.getDate() + "</td>";
                    output += "<td>" + customer.getName() + "</td>";
                    output += "<td>" + customer.getAccountId() + "</td>";
                    output += "<td>" + bill.getId() + "</td>";
                }

                statement.close();
                connection.close();
                output += "</table>";


            } catch (SQLException e) {
                output = "Error while find all customers.";
                e.printStackTrace();
            }

            return output;
    }

    public String deletePayment(int id) {
        String output = "";
        Connection connection = applicationService.connect();
        if (connection == null) {
            return "Error while connecting to the database for delete Payment";
        }

        try {
            String query = "SELECT b.id, b.currentBalance, p.amount FROM payment p LEFT JOIN bill b ON p.id = b.payment_id INNER JOIN customer c ON b.customer_id = c.id WHERE p.id='"+id+"'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            int billID  = 0;
            BigDecimal currentBalance = null;
            BigDecimal paymentAmount = null;

            while (resultSet.next()) {
                billID = resultSet.getInt("b.id");
                currentBalance = resultSet.getBigDecimal("b.currentBalance");
                paymentAmount = resultSet.getBigDecimal("p.amount");
            }

            String billUpdateQuery = "UPDATE `bill` SET `currentBalance` = ?, `payment_id`= NULL WHERE (`id` = ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(billUpdateQuery);
            preparedStatement.setBigDecimal(1, currentBalance.add(paymentAmount));
            preparedStatement.setInt(2, billID);
            preparedStatement.executeUpdate();

            String queryPayment = "DELETE FROM `payment` WHERE (`id` = ?)";
            PreparedStatement preparedStatementPayment = connection.prepareStatement(queryPayment);
            preparedStatementPayment.setInt(1, id);
            int executeUpdate = preparedStatementPayment.executeUpdate();
            connection.close();
            if (executeUpdate < 1){
                return  "Invalid Payment ID";
            }else {
                output = "Payment Deleted successfully";
            }
            
            output = "Payment deleted and bill update successful";

            connection.close();
        } catch (SQLException e) {
            output = "Update Failed";
            e.printStackTrace();
        }
        return output;
    }
}
