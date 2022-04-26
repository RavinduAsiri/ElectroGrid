package com.electrogrid.model;

import java.math.BigDecimal;
import java.util.Date;

public class Bill {

    int id;
    Date date;
    BigDecimal previousBalance;
    BigDecimal chargeForPeriod;
    BigDecimal currentBalance;
    int units;

    public Bill(int id, Date date, BigDecimal previousBalance, BigDecimal chargeForPeriod, BigDecimal currentBalance) {
        this.id = id;
        this.date = date;
        this.previousBalance = previousBalance;
        this.chargeForPeriod = chargeForPeriod;
        this.currentBalance = currentBalance;
    }

    public Bill() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getPreviousBalance() {
        return previousBalance;
    }

    public void setPreviousBalance(BigDecimal previousBalance) {
        this.previousBalance = previousBalance;
    }

    public BigDecimal getChargeForPeriod() {
        return chargeForPeriod;
    }

    public void setChargeForPeriod(BigDecimal chargeForPeriod) {
        this.chargeForPeriod = chargeForPeriod;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }
}
