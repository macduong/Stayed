package com.example.stayed.Model;

import java.io.Serializable;

public class Rooms implements Serializable {
    public static final int ID = -1;
    public static final int AVAILABLE = 0;
    public static final int VIP = 0;
    public static final int PRICE = 0;
    private int id, available, vip, price;

    public Rooms() {
        this(ID, AVAILABLE, VIP, PRICE);
    }

    public Rooms(int id, int available, int vip, int price) {
        this.id = id;
        this.available = available;
        this.vip = vip;
        this.price = price;
    }

    public Rooms(int vip){
        this(ID, AVAILABLE, vip, PRICE);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public int getVip() {
        return vip;
    }

    public void setVip(int vip) {
        this.vip = vip;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Rooms{" +
                "id=" + id +
                ", available=" + available +
                ", vip=" + vip +
                ", price=" + price +
                '}';
    }
}
