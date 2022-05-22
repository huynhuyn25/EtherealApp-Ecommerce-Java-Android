package com.huynhuyn25.etherealapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Manufacturer implements Serializable {
    private int idNCC;

    @SerializedName("tenncc")
    private String tenNCC;
    private Address address;
    @SerializedName("tenctyql")
    private String tenCTYQL;
    private String email;
    private String sdt;

    public Manufacturer() {
    }

    public Manufacturer(int idNCC, String tenNCC, Address address, String tenCTYQL, String email, String sdt) {
        this.idNCC = idNCC;
        this.tenNCC = tenNCC;
        this.address = address;
        this.tenCTYQL = tenCTYQL;
        this.email = email;
        this.sdt = sdt;
    }

    public Manufacturer(String tenNCC, Address address, String tenCTYQL, String email, String sdt) {
        this.tenNCC = tenNCC;
        this.address = address;
        this.tenCTYQL = tenCTYQL;
        this.email = email;
        this.sdt = sdt;
    }

    public int getIdNCC() {
        return idNCC;
    }

    public void setIdNCC(int idNCC) {
        this.idNCC = idNCC;
    }

    public String getTenNCC() {
        return tenNCC;
    }

    public void setTenNCC(String tenNCC) {
        this.tenNCC = tenNCC;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getTenCTYQL() {
        return tenCTYQL;
    }

    public void setTenCTYQL(String tenCTYQL) {
        this.tenCTYQL = tenCTYQL;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    @Override
    public String toString() {
        return
                "tenNCC: " + tenNCC  +
                "\naddress: " + address +
                "\ntenCTYQL: " + tenCTYQL  +
                "\nemail: " + email  +
                "\nsdt: " + sdt ;
    }
}
