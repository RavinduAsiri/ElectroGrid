package com.electrogrid.services;

import com.electrogrid.ApplicationService;

import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginService {

    ApplicationService applicationService = new ApplicationService();

    public Response loginEmployee(String telPhoneNumber, String password) {
        Connection connection = applicationService.connect();
        Response output = null;
        if (connection == null) {
            output = Response.status(Response.Status.BAD_GATEWAY).entity("Error while connecting to the database for Login.").build();
        }

        String query = "SELECT * FROM employee WHERE telNo ='"+ telPhoneNumber + "' AND password ='" + password +"'";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (!resultSet.isBeforeFirst()){
                output = Response.status(Response.Status.UNAUTHORIZED).entity("Invalid credentials Please try again!").build();
            }else {
                output = Response.ok("Login successful").build();
            }

        } catch (SQLException e) {
            output = Response.status(Response.Status.BAD_GATEWAY).entity("Error while Login....").build();
            e.printStackTrace();
        }

        return output;
    }

    public Response loginCustomer(String accountID, String password) {
        Connection connection = applicationService.connect();
        Response output = null;
        if (connection == null) {
            output = Response.status(Response.Status.BAD_GATEWAY).entity("Error while connecting to the database for Login.").build();
        }

        String query = "SELECT * FROM customer WHERE accountId ='"+ accountID + "' AND password ='" + password +"'";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (!resultSet.isBeforeFirst()){
                output = Response.status(Response.Status.UNAUTHORIZED).entity("Invalid credentials Please try again!").build();
            }else {
                output = Response.ok("Login successful").build();
            }

        } catch (SQLException e) {
            output = Response.status(Response.Status.BAD_GATEWAY).entity("Error while Login....").build();
            e.printStackTrace();
        }

        return output;
    }
}
