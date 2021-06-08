package com.example.stayed.Model;

import java.io.Serializable;

public class Managers implements Serializable  {
    public static final int ID = 0;
    public static final String FULLNAME = "0";
    public static final String MAIL = "0";
    public static final String USERNAME = "0";
    public static final String PASSWORD = "0";
    public static final int IS_BOSS = 0;
    private int id;
    private String fullName, mail, userName, passWord;
    private int isboss;

    public Managers() {
        this(ID, FULLNAME, MAIL, USERNAME, PASSWORD, IS_BOSS);
    }


    public Managers(int id, String fullName, String mail, String userName, String passWord, int isboss) {
        this.id = id;
        this.fullName = fullName;
        this.mail = mail;
        this.userName = userName;
        this.passWord = passWord;
        this.isboss = isboss;
    }
    public Managers( String fullName, String mail, String userName, String passWord, int isboss) {
        this.id = ID;
        this.fullName = fullName;
        this.mail = mail;
        this.userName = userName;
        this.passWord = passWord;
        this.isboss = isboss;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public int getIsboss() {
        return isboss;
    }

    public void setIsboss(int isboss) {
        this.isboss = isboss;
    }
}
