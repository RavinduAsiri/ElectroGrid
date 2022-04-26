package com.electrogrid.controllers;
import com.electrogrid.model.Bill;
import com.electrogrid.model.Customer;
import com.electrogrid.services.BillService;
import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Path("/bill")
public class BIllController {

    BillService billService = new BillService();

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public String createBill(@FormParam("date") String  date, @FormParam("customerID") String customerId, @FormParam("currentBalance") String currentBalance, @FormParam("units") int units) {
        return billService.createNewBill(getDateFromString(date), customerId, units, currentBalance);
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String delete(@PathParam("id") int id){
        return billService.deleteBill(id);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String updateCustomer(String customerData) {

        //Convert the input string to a JSON object
        Gson gson = new Gson();
        Bill bill = gson.fromJson(customerData, Bill.class);
        return billService.updateBill(bill);

    }


    private Date getDateFromString(String dateString) {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date date = df.parse(dateString);
            return date;
        } catch (ParseException ignored) {}
        return null;
    }
}
