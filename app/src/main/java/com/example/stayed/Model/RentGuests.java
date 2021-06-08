package com.example.stayed.Model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class RentGuests implements Serializable {
    public static final int ID = 0;
    public static final int ROOMID = 0;
    public static final String NAME = "0";
    public static final String SEX = "0";
    public static final String CID = "0";
    public static final String TIME_CHECKIN = "0";
    public static final String TIME_CHECKOUT = "0";
    private int id, roomId;
    private String name, sex, cid;
    private String timeCheckin, timeCheckout;

    public RentGuests() {
        this(ID, ROOMID, NAME, SEX, CID, TIME_CHECKIN, TIME_CHECKOUT);
    }
    public RentGuests(String guestname, String timecheckin) {
        this(ID, ROOMID, guestname, SEX, CID, timecheckin, TIME_CHECKOUT);
    }



    public RentGuests(int id, int roomId, String name, String sex, String cid, String timeCheckin) {
        this.id = id;
        this.roomId = roomId;
        this.name = name;
        this.sex = sex;
        this.cid = cid;
        this.timeCheckin = timeCheckin;
        this.timeCheckout = TIME_CHECKOUT;
    }

    public RentGuests(int id, int roomId, String name, String sex, String cid, String timeCheckin, String timeCheckout) {
        this.id = id;
        this.roomId = roomId;
        this.name = name;
        this.sex = sex;
        this.cid = cid;
        this.timeCheckin = timeCheckin;
        this.timeCheckout = timeCheckout;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getTimeCheckin() {
        return timeCheckin;
    }

    public void setTimeCheckin(String timeCheckin) {
        this.timeCheckin = timeCheckin;
    }

    public String getTimeCheckout() {
        return timeCheckout;
    }

    public void setTimeCheckout(String timeCheckout) {
        this.timeCheckout = timeCheckout;
    }

    @Override
    public String toString() {
        return "RentGuests{" +
                "id=" + id +
                ", roomId=" + roomId +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", cid='" + cid + '\'' +
                ", timeCheckin='" + timeCheckin + '\'' +
                ", timeCheckout='" + timeCheckout + '\'' +
                '}';
    }
}
