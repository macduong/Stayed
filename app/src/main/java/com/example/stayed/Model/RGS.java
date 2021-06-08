package com.example.stayed.Model;

import java.io.Serializable;
import java.util.Comparator;

public class RGS implements Serializable {
    private int id;
    private int idGuest, roomIdGuest;
    private String nameGuest, sexGuest, cidGuest;
    private String timeCheckinGuest, timeCheckoutGuest;

    private int roomIsVipGuest;
    private int roomPriceGuest;

    private int idService;
    private String nameService;
    private int priceService;
    private String unitService;

    private int amount;


    public static final int ID = -1;
    public static final int IDGUEST = 0;
    public static final int ROOMIDGUEST = 0;
    public static final String NAMEGUEST = "0";
    public static final String SEXGUEST = "0";
    public static final String CIDGUEST = "0";
    public static final String TIMECHECKINGUEST = "0";
    public static final String TIMECHECKOUTGUEST = "0";
    public static final int ROOMISVIPGUEST = 0;
    public static final int ROOMPRICEGUEST = 0;
    public static final int IDSERVICE = 0;
    public static final String NAMESERVICE = "0";
    public static final int PRICESERVICE = 0;
    public static final String UNITSERVICE = "0";
    public static final int AMOUNT = 0;


    public RGS(int id, int idGuest, int roomIdGuest, String nameGuest, String sexGuest, String cidGuest, String timeCheckinGuest, String timeCheckoutGuest, int roomIsVipGuest, int roomPriceGuest, int idService, String nameService, int priceService, String unitService, int amount) {
        this.id = id;
        this.idGuest = idGuest;
        this.roomIdGuest = roomIdGuest;
        this.nameGuest = nameGuest;
        this.sexGuest = sexGuest;
        this.cidGuest = cidGuest;
        this.timeCheckinGuest = timeCheckinGuest;
        this.timeCheckoutGuest = timeCheckoutGuest;
        this.roomIsVipGuest = roomIsVipGuest;
        this.roomPriceGuest = roomPriceGuest;
        this.idService = idService;
        this.nameService = nameService;
        this.priceService = priceService;
        this.unitService = unitService;
        this.amount = amount;
    }

    public RGS(int id, int idGuest, int roomIdGuest, String nameGuest, String sexGuest, String cidGuest, String timeCheckinGuest, int roomIsVipGuest, int roomPriceGuest, int idService, String nameService, int priceService, String unitService, int amount) {
        this.id = id;
        this.idGuest = idGuest;
        this.roomIdGuest = roomIdGuest;
        this.nameGuest = nameGuest;
        this.sexGuest = sexGuest;
        this.cidGuest = cidGuest;
        this.timeCheckinGuest = timeCheckinGuest;
        this.timeCheckoutGuest = TIMECHECKOUTGUEST;
        this.roomIsVipGuest = roomIsVipGuest;
        this.roomPriceGuest = roomPriceGuest;
        this.idService = idService;
        this.nameService = nameService;
        this.priceService = priceService;
        this.unitService = unitService;
        this.amount = amount;
    }

    public RGS() {
        this.id = ID;
        this.idGuest = IDGUEST;
        this.roomIdGuest = ROOMIDGUEST;
        this.nameGuest = NAMEGUEST;
        this.sexGuest = SEXGUEST;
        this.cidGuest = CIDGUEST;
        this.timeCheckinGuest = TIMECHECKINGUEST;
        this.timeCheckoutGuest = TIMECHECKOUTGUEST;
        this.roomIsVipGuest = ROOMISVIPGUEST;
        this.roomPriceGuest = ROOMPRICEGUEST;
        this.idService = IDSERVICE;
        this.nameService = NAMESERVICE;
        this.priceService = PRICESERVICE;
        this.unitService = UNITSERVICE;
        this.amount = AMOUNT;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomPriceGuest() {
        return roomPriceGuest;
    }

    public void setRoomPriceGuest(int roomPriceGuest) {
        this.roomPriceGuest = roomPriceGuest;
    }

    public int getIdGuest() {
        return idGuest;
    }

    public void setIdGuest(int idGuest) {
        this.idGuest = idGuest;
    }

    public int getRoomIdGuest() {
        return roomIdGuest;
    }

    public void setRoomIdGuest(int roomIdGuest) {
        this.roomIdGuest = roomIdGuest;
    }

    public int getRoomIsVipGuest() {
        return roomIsVipGuest;
    }

    public void setRoomIsVipGuest(int roomIsVipGuest) {
        this.roomIsVipGuest = roomIsVipGuest;
    }

    public String getNameGuest() {
        return nameGuest;
    }

    public void setNameGuest(String nameGuest) {
        this.nameGuest = nameGuest;
    }

    public String getSexGuest() {
        return sexGuest;
    }

    public void setSexGuest(String sexGuest) {
        this.sexGuest = sexGuest;
    }

    public String getCidGuest() {
        return cidGuest;
    }

    public void setCidGuest(String cidGuest) {
        this.cidGuest = cidGuest;
    }

    public String getTimeCheckinGuest() {
        return timeCheckinGuest;
    }

    public void setTimeCheckinGuest(String timeCheckinGuest) {
        this.timeCheckinGuest = timeCheckinGuest;
    }

    public String getTimeCheckoutGuest() {
        return timeCheckoutGuest;
    }

    public void setTimeCheckoutGuest(String timeCheckoutGuest) {
        this.timeCheckoutGuest = timeCheckoutGuest;
    }

    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }

    public String getNameService() {
        return nameService;
    }

    public void setNameService(String nameService) {
        this.nameService = nameService;
    }

    public int getPriceService() {
        return priceService;
    }

    public void setPriceService(int priceService) {
        this.priceService = priceService;
    }

    public String getUnitService() {
        return unitService;
    }

    public void setUnitService(String unitService) {
        this.unitService = unitService;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public static Comparator<RGS> nameComp = new Comparator<RGS>() {

        public int compare(RGS s1, RGS s2) {
            String name1 = s1.getNameService().toUpperCase();
            String name2 = s2.getNameService().toUpperCase();

            //ascending order
            return name1.compareTo(name2);

            //descending order
            //return StudentName2.compareTo(StudentName1);
        }};
}
