package com.electrogrid.model;

public class Customer {

    private int id;
    private String name;
    private String accountId;
    private String telNo;
    private String email;
    private String address;
    private String nic;
    private String password;

    public Customer(int id, String name, String accountId, String telNo, String email, String address, String nic, String password) {
        this.id = id;
        this.name = name;
        this.accountId = accountId;
        this.telNo = telNo;
        this.email = email;
        this.address = address;
        this.nic = nic;
        this.password = password;
    }

    public Customer(String name, String accountId, String telNo, String email, String address, String nic) {
        this.name = name;
        this.accountId = accountId;
        this.telNo = telNo;
        this.email = email;
        this.address = address;
        this.nic = nic;
    }

    public Customer() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
