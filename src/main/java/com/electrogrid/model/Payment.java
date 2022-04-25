package com.electrogrid.model;

import java.math.BigDecimal;
import java.util.Date;

public class Payment {

    int id;
    String method;
    String refId;
    BigDecimal amount;
    Date date;

    public Payment(int id, String method, String refId, BigDecimal amount, Date date) {
        this.id = id;
        this.method = method;
        this.refId = refId;
        this.amount = amount;
        this.date = date;
    }

    public Payment(String method, String refId, BigDecimal amount, Date date) {
        this.method = method;
        this.refId = refId;
        this.amount = amount;
        this.date = date;
    }

    public Payment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
