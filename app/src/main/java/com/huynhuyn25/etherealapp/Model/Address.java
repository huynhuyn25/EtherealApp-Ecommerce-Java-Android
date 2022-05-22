package com.huynhuyn25.etherealapp.Model;

import java.io.Serializable;

public class Address implements Serializable {
    private int idAdress;
    private String city;
    private String district;
    private String ward;
    private String strest;
    private String building;
    private String numberHome;

    public Address(int idAdress, String city, String district, String ward, String strest, String building, String numberHome) {
        this.idAdress = idAdress;
        this.city = city;
        this.district = district;
        this.ward = ward;
        this.strest = strest;
        this.building = building;
        this.numberHome = numberHome;
    }

    public Address(String city, String district, String ward, String strest, String building, String numberHome) {
        this.city = city;
        this.district = district;
        this.ward = ward;
        this.strest = strest;
        this.building = building;
        this.numberHome = numberHome;
    }

    public Address() {
    }

    public int getIdAdress() {
        return idAdress;
    }

    public void setIdAdress(int idAdress) {
        this.idAdress = idAdress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getStrest() {
        return strest;
    }

    public void setStrest(String strest) {
        this.strest = strest;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getNumberHome() {
        return numberHome;
    }

    public void setNumberHome(String numberHome) {
        this.numberHome = numberHome;
    }

    @Override
    public String toString() {
        return  city +
                ", "+district+
                ", " + ward  +
                ", " + strest  +
                ", " + building +
                ", " + numberHome;
    }
}
