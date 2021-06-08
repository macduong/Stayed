package com.example.stayed.Model;

import java.io.Serializable;
import java.util.Comparator;

public class Services implements Serializable{

    public static final int ID = -1;
    public static final String NAME = "0";
    public static final int PRICE = 0;
    public static final String UNIT = "0";

    private int id;
    private String name;
    private int price;
    private String unit;


    public static Comparator<Services> nameComp = new Comparator<Services>() {

        public int compare(Services s1, Services s2) {
            String name1 = s1.getName().toUpperCase();
            String name2 = s2.getName().toUpperCase();

            //ascending order
            return name1.compareTo(name2);

            //descending order
            //return StudentName2.compareTo(StudentName1);
        }};


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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Services(int id, String name, int price, String unit) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.unit = unit;
    }

    public Services(String name, int price, String unit) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.unit = unit;
    }

    public Services() {
        this.id = ID;
        this.name = NAME;
        this.price = PRICE;
        this.unit = UNIT;
    }



}
