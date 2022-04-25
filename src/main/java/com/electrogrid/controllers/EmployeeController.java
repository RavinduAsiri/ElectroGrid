package com.electrogrid.controllers;

import com.electrogrid.model.Customer;
import com.electrogrid.model.Employee;
import com.electrogrid.services.CustomerService;
import com.electrogrid.services.EmployeeService;
import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/employee")
public class EmployeeController {

    EmployeeService employeeService = new EmployeeService();
    CustomerService customerService = new CustomerService();

    //Employee

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getAllEmployees() {
        return employeeService.findAllEmployees();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public String insert(@FormParam("name") String name, @FormParam("type") String type, @FormParam("email") String email, @FormParam("telNO") String telNo, @FormParam("address") String address, @FormParam("password") String password) {
        return employeeService.insertEmployee(new Employee(name, type, email, telNo, address, password));
    }

    @Path("{id}")
    @DELETE
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String delete(@PathParam("id") int id){
        return employeeService.deleteEmployee(id);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String updateEmployee(String employeeData) {

        //Convert the input string to a JSON object
        Gson gson = new Gson();
        Employee employee = gson.fromJson(employeeData, Employee.class);
        return employeeService.updateEmployee(employee);

    }

    //Admin-access -Customer

    @Path("admin/customers")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public String insertCustomer(@FormParam("name") String name, @FormParam("accountId") String accountId, @FormParam("email") String email, @FormParam("nic") String nic, @FormParam("telNO") String telNo, @FormParam("address") String address) {
        return customerService.insertCustomer(new Customer(name, accountId, telNo, email, address, nic));
    }

    @Path("admin/customers")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getAllCustomers() {
        return customerService.findAllCustomers();
    }

    @Path("/admin/customers")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String updateCustomer(String customerData) {

        //Convert the input string to a JSON object
        Gson gson = new Gson();
        Customer customer = gson.fromJson(customerData, Customer.class);
        return customerService.updateCustomer(customer);

    }

    @Path("/admin/customers/{id}")
    @DELETE
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String deleteCustomer(@PathParam("id") int id){
        return customerService.deleteCustomer(id);
    }

}
