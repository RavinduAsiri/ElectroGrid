package com.electrogrid;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Path("/testConnection")
public class ApplicationService {
    public Connection connect() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/electrogrid","system", "s1y2s3t4e5m");
            System.out.print("Successfully connected");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @GET
    public String getMsg() {
        Connection connection = connect();
        if (connection == null){
            return "Something went wrong.. Could not connect to the database";
        }
        return "You have successfully connected to the database!";
    }
}
