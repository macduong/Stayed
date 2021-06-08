package com.example.stayed.Model;

import java.io.Serializable;

public class GuestServices implements Serializable {
    private int id;
    private int guestid, serviceid;
    private int amount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGuestid() {
        return guestid;
    }

    public void setGuestid(int guestid) {
        this.guestid = guestid;
    }

    public int getServiceid() {
        return serviceid;
    }

    public void setServiceid(int serviceid) {
        this.serviceid = serviceid;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public GuestServices(int id, int guestid, int serviceid, int amount) {
        this.id = id;
        this.guestid = guestid;
        this.serviceid = serviceid;
        this.amount = amount;
    }

    public GuestServices(int guestid, int serviceid, int amount) {
        this.guestid = guestid;
        this.serviceid = serviceid;
        this.amount = amount;
    }

    public GuestServices() {
    }
}
