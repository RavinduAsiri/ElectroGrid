package com.electrogrid.controllers;

import com.electrogrid.model.Payment;
import com.electrogrid.services.PaymentService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.Date;


@Path("/payment")
public class PaymentController {

    PaymentService paymentService = new PaymentService();

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getAllPayments() {
        return paymentService.findAllPayments();
    }

    @POST()
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public String createPayment(@FormParam("billID") String billId, @FormParam("method") String method, @FormParam("referenceID") String refId, @FormParam("amount") BigDecimal amount) {
        Payment payment = new Payment(method, refId, amount, new Date());
        return paymentService.createPayment(billId, payment);
    }

    @Path("/{id}")
    @DELETE
    public String deletePayment(@PathParam("id") int id){
        return paymentService.deletePayment(id);
    }
}