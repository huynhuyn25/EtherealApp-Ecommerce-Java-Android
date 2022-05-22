package com.huynhuyn25.etherealapp.Model;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {
    private int idOrder;
    private User user;
    private ArrayList<ProductOrder> listProductOrder;
    private double total;
    private OrderInfor orderInfor;
    private String status;
    public Order() {
    }

    public Order(int idOrder, User user, ArrayList<ProductOrder> listProductOrder, double total, OrderInfor orderInfor, String status) {
        this.idOrder = idOrder;
        this.user = user;
        this.listProductOrder = listProductOrder;
        this.total = total;
        this.orderInfor = orderInfor;
        this.status = status;
    }

    public Order(User user, ArrayList<ProductOrder> listProductOrder, double total, OrderInfor orderInfor, String status) {
        this.user = user;
        this.listProductOrder = listProductOrder;
        this.total = total;
        this.orderInfor = orderInfor;
        this.status = status;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<ProductOrder> getListProductOrder() {
        return listProductOrder;
    }

    public void setListProductOrder(ArrayList<ProductOrder> listProductOrder) {
        this.listProductOrder = listProductOrder;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public OrderInfor getOrderInfor() {
        return orderInfor;
    }

    public void setOrderInfor(OrderInfor orderInfor) {
        this.orderInfor = orderInfor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "idOrder=" + idOrder +
                ", user=" + user +
                ", listProductOrder=" + listProductOrder +
                ", total=" + total +
                ", orderInfor=" + orderInfor +
                ", status='" + status + '\'' +
                '}';
    }
}
