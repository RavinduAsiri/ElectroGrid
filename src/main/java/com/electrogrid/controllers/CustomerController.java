package com.electrogrid.controllers;

import com.electrogrid.services.BillService;
import com.electrogrid.services.CustomerService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/customer")
public class CustomerController {

    CustomerService customerService = new CustomerService();
    BillService billService = new BillService();

    @Path("/{id}")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getCustomerProfile(@PathParam("id") String id) {
        return customerService.findCustomerById(id);
    }

    @Path("/register")
    @PUT
    @Produces(MediaType.TEXT_HTML)
    public String customerRegister(@FormParam("accountID") String accountId, @FormParam("password") String password) {
        return customerService.updatePasswordByAccountID(accountId, password);
    }

    @Path("viewBill/{id}")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getCustomerBills(@PathParam("id") String customerID) {
        return billService.findCustomerBillsById(customerID);
    }
}
