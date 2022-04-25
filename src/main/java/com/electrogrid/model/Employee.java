package com.electrogrid.model;

public class Employee {

    private int id;
    private String name;
    private String type;
    private String email;
    private String telNo;
    private String address;
    private String password;

    public Employee(int id, String name, String type, String email, String telNo, String address, String password) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.email = email;
        this.telNo = telNo;
        this.address = address;
        this.password = password;
    }

    public Employee(String name, String type, String email, String telNo, String address, String password) {
        this.name = name;
        this.type = type;
        this.email = email;
        this.telNo = telNo;
        this.address = address;
        this.password = password;
    }

    public Employee() {}

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
