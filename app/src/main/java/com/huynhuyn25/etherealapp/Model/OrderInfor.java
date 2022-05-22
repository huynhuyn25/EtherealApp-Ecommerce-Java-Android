package com.huynhuyn25.etherealapp.Model;

import java.io.Serializable;

public class OrderInfor implements Serializable {
    private int idOrderInfor;
    private Address address;
    private String sdt;
    private String tenNguoiNhan;

    public OrderInfor() {
    }

    public OrderInfor(Address address, String sdt, String tenNguoiNhan) {
        this.address = address;
        this.sdt = sdt;
        this.tenNguoiNhan = tenNguoiNhan;
    }

    public int getIdOrderInfor() {
        return idOrderInfor;
    }

    public void setIdOrderInfor(int idOrderInfor) {
        this.idOrderInfor = idOrderInfor;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getTenNguoiNhan() {
        return tenNguoiNhan;
    }

    public void setTenNguoiNhan(String tenNguoiNhan) {
        this.tenNguoiNhan = tenNguoiNhan;
    }
}
