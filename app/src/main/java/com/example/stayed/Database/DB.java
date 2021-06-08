package com.example.stayed.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import com.example.stayed.Model.GuestServices;
import com.example.stayed.Model.Managers;
import com.example.stayed.Model.RGS;
import com.example.stayed.Model.RentGuests;
import com.example.stayed.Model.Rooms;
import com.example.stayed.Model.Services;

import java.util.ArrayList;
import java.util.List;

public class DB extends SQLiteOpenHelper {
    public static final String DB_NAME = "STAYED_DB";
    public static final int VERSION = 1;
    private SQLiteDatabase database;
    private ContentValues contentValues;
    private Cursor cursor;

    // Managers
    public static final String TABLE_MANAGERS = "managers_tbl";
    public static final String ID_MANAGERS = "managers_id";
    public static final String FULLNAME_MANAGERS = "managers_fullname";
    public static final String EMAIL_MANAGERS = "managers_email";
    public static final String USERNAME_MANAGERS = "managers_username";
    public static final String PASSWORD_MANAGERS = "managers_password";
    public static final String ISBOSS_MANAGERS = "managers_isboss";

    //RentGuests
    public static final String TABLE_RENTGUESTS = "rentguests_tbl";
    public static final String ID_RENTGUESTS = "rentguests_id";
    public static final String ROOMID_RENTGUESTS = "rentguests_roomId";
    public static final String NAME_RENTGUESTS = "rentguests_name";
    public static final String SEX_RENTGUESTS = "rentguests_sex";
    public static final String CID_RENTGUESTS = "rentguests_cid";
    public static final String TIMECHECKIN_RENTGUESTS = "rentguests_timecheckin";
    public static final String TIMECHECKOUT_RENTGUESTS = "rentguests_timecheckout";

    //Rooms
    public static final String TABLE_ROOMS = "rooms_tbl";
    public static final String ID_ROOMS = "rooms_id";
    public static final String AVAILABLE_ROOMS = "rooms_available";
    public static final String VIP_ROOMS = "rooms_vip";
    public static final String PRICE_ROOMS = "rooms_price";

    //Services
    public static final String TABLE_SERVICES = "services_tbl";
    public static final String ID_SERVICES = "services_id";
    public static final String NAME_SERVICES = "services_name";
    public static final String PRICE_SERVICES = "services_price";
    public static final String UNIT_SERVICES = "services_unit";

    //GuestServices
    public static final String TABLE_GUESTSERVICES = "guestservices_tbl";
    public static final String ID_GUESTSERVICES = "guestservices_id";
    public static final String GUESTID_GUESTSERVICES = "guestservices_guestid";
    public static final String SERVICEID_GUESTSERVICES = "guestservices_serviceid";
    public static final String AMOUNT_GUESTSERVICES = "guestservices_amount";


    public DB(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createManagers = "CREATE TABLE " + TABLE_MANAGERS + " (" +
                ID_MANAGERS + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                FULLNAME_MANAGERS + " TEXT, " +
                EMAIL_MANAGERS + " TEXT, " +
                USERNAME_MANAGERS + " TEXT, " +
                PASSWORD_MANAGERS + " TEXT, " +
                ISBOSS_MANAGERS + " INTEGER)";
        db.execSQL(createManagers);

        String createRentGuests = "CREATE TABLE " + TABLE_RENTGUESTS + " (" +
                ID_RENTGUESTS + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                ROOMID_RENTGUESTS + " INTETER, " +
                NAME_RENTGUESTS + " TEXT, " +
                SEX_RENTGUESTS + " TEXT, " +
                CID_RENTGUESTS + " TEXT, " +
                TIMECHECKIN_RENTGUESTS + " TEXT, " +
                TIMECHECKOUT_RENTGUESTS + " TEXT)";
        db.execSQL(createRentGuests);

        String createRooms = "CREATE TABLE " + TABLE_ROOMS + " (" +
                ID_ROOMS + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                AVAILABLE_ROOMS + " INTEGER, " +
                VIP_ROOMS + " INTEGER, " +
                PRICE_ROOMS + " INTEGER)";
        db.execSQL(createRooms);

        String createServices = "CREATE TABLE " + TABLE_SERVICES + " (" +
                ID_SERVICES + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                NAME_SERVICES + " TEXT, " +
                PRICE_SERVICES + " INTEGER, " +
                UNIT_SERVICES + " TEXT)";
        db.execSQL(createServices);

        String createGuestServices = "CREATE TABLE " + TABLE_GUESTSERVICES + " (" +
                ID_GUESTSERVICES + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                GUESTID_GUESTSERVICES + " INTERGER, " +
                SERVICEID_GUESTSERVICES + " INTEGER, " +
                AMOUNT_GUESTSERVICES + " INTEGER)";
        db.execSQL(createGuestServices);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROOMS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_MANAGERS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_SERVICES);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_RENTGUESTS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_GUESTSERVICES);
        }
    }

    public int login(String username, String password) {
        int mId = 0;
        String login = "SELECT * FROM " + TABLE_MANAGERS +
                " WHERE " + USERNAME_MANAGERS + " = '" + username + "' AND " + PASSWORD_MANAGERS + " = '" + password + "'";
        database = getWritableDatabase();
        cursor = database.rawQuery(login, null);
        if (cursor.moveToFirst() && cursor.getCount() > 0) {
            mId = cursor.getInt(0);
        }
        closeDB();
        return mId;
    }

    public boolean checkUsedUsername(String username) {
        String check = "SELECT * FROM " + TABLE_MANAGERS + " WHERE " + USERNAME_MANAGERS + " = '" + username + "'";
        boolean result = false;
        database = getReadableDatabase();
        cursor = database.rawQuery(check, null);
        if (cursor.getCount() > 0) {
            result = true;
        }
        closeDB();
        return result;
    }

    public boolean checkUsedMail(String mail) {
        String check = "SELECT * FROM " + TABLE_MANAGERS + " WHERE " + EMAIL_MANAGERS + " = '" + mail + "'";
        boolean result = false;
        database = getReadableDatabase();
        cursor = database.rawQuery(check, null);
        if (cursor.getCount() > 0) {
            result = true;
        }
        closeDB();
        return result;
    }

    public Managers searchOneManagerByMail(String mail) {
        String check = "SELECT * FROM " + TABLE_MANAGERS + " WHERE " + EMAIL_MANAGERS + " = '" + mail + "'";
        boolean result = false;
        Managers manager = new Managers();
        database = getReadableDatabase();
        cursor = database.rawQuery(check, null);
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            String fullName = cursor.getString(1);
            String email = cursor.getString(2);
            String username = cursor.getString(3);
            String password = cursor.getString(4);
            int isboss = cursor.getInt(5);
            manager = new Managers(id, fullName, email, username, password, isboss);
        }
        closeDB();
        return manager;
    }

    public boolean insertManager(Managers manager) {
        boolean result = true;
        database = getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put(FULLNAME_MANAGERS, manager.getFullName());
        contentValues.put(EMAIL_MANAGERS, manager.getMail());
        contentValues.put(USERNAME_MANAGERS, manager.getUserName());
        contentValues.put(PASSWORD_MANAGERS, manager.getPassWord());
        contentValues.put(ISBOSS_MANAGERS, manager.getIsboss());
        try {
            database.insert(TABLE_MANAGERS, null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        closeDB();
        return result;
    }

    public List<Managers> searchAllManagers() {
        database = getWritableDatabase();
        String search = "SELECT * FROM " + TABLE_MANAGERS;
        List<Managers> managersList = new ArrayList<>();
        cursor = database.rawQuery(search, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String fullName = cursor.getString(1);
                String email = cursor.getString(2);
                String username = cursor.getString(3);
                String password = cursor.getString(4);
                int isboss = cursor.getInt(5);
                managersList.add(new Managers(id, fullName, email, username, password, isboss));
            }
            while (cursor.moveToNext());
        }
        closeDB();
        return managersList;
    }

    public List<Managers> searchAllManagersByName(String name) {
        database = getWritableDatabase();
        String search = "SELECT * FROM " + TABLE_MANAGERS + " WHERE " + FULLNAME_MANAGERS + " LIKE '%" + name + "%' OR " + USERNAME_MANAGERS + " LIKE '%" + name + "%'";
        List<Managers> managersList = new ArrayList<>();
        cursor = database.rawQuery(search, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String fullName = cursor.getString(1);
                String email = cursor.getString(2);
                String username = cursor.getString(3);
                String password = cursor.getString(4);
                int isboss = cursor.getInt(5);
                managersList.add(new Managers(id, fullName, email, username, password, isboss));
            }
            while (cursor.moveToNext());
        }
        closeDB();
        return managersList;
    }

    public Managers searchOneManagerById(int mId) {
        database = getWritableDatabase();
        String search = "SELECT * FROM " + TABLE_MANAGERS + " WHERE " + ID_MANAGERS + " = '" + mId + "'";
        Managers manager = new Managers();
        cursor = database.rawQuery(search, null);
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            String fullName = cursor.getString(1);
            String email = cursor.getString(2);
            String username = cursor.getString(3);
            String password = cursor.getString(4);
            int isboss = cursor.getInt(5);
            manager = new Managers(id, fullName, email, username, password, isboss);
        }
        closeDB();
        return manager;
    }

//    public boolean updatePassword(String mail, String passWord) {
//        boolean result = false;
//        String getmng = "SELECT * FROM " + TABLE_MANAGERS + " WHERE " + EMAIL_MANAGERS + " = '" + mail + "'";
//        database = getWritableDatabase();
//        cursor = database.rawQuery(getmng, null);
//        if (cursor.moveToFirst()) {
//            int id = cursor.getInt(0);
//            String fullName = cursor.getString(1);
//            String email = cursor.getString(2);
//            String username = cursor.getString(3);
//            String password = cursor.getString(4);
//            int isboss = cursor.getInt(5);
//            Managers manager = new Managers(id, fullName, email, username, password, isboss);
//            contentValues = new ContentValues();
//            contentValues.put(ID_MANAGERS, manager.getId());
//            contentValues.put(FULLNAME_MANAGERS, manager.getFullName());
//            contentValues.put(EMAIL_MANAGERS, manager.getMail());
//            contentValues.put(USERNAME_MANAGERS, manager.getUserName());
//            contentValues.put(PASSWORD_MANAGERS, passWord);
//            contentValues.put(ISBOSS_MANAGERS, manager.getIsboss());
//            database.update(TABLE_MANAGERS, contentValues, EMAIL_MANAGERS + " =?", new String[]{mail});
//            result = true;
//        }
//        closeDB();
//        return result;
//    }

    public void deleteManagerById(int id) {
        database = getWritableDatabase();
        database.delete(TABLE_MANAGERS, ID_MANAGERS + " =?", new String[]{String.valueOf(id)});
        closeDB();
    }

    public boolean updateManager(Managers manager) {
        boolean result = false;
        String getmng = "SELECT * FROM " + TABLE_MANAGERS + " WHERE " + ID_MANAGERS + " = '" + manager.getId() + "'";
        database = getWritableDatabase();
        cursor = database.rawQuery(getmng, null);
        if (cursor.moveToFirst()) {
            contentValues = new ContentValues();
            contentValues.put(ID_MANAGERS, manager.getId());
            contentValues.put(FULLNAME_MANAGERS, manager.getFullName());
            contentValues.put(EMAIL_MANAGERS, manager.getMail());
            contentValues.put(USERNAME_MANAGERS, manager.getUserName());
            contentValues.put(PASSWORD_MANAGERS, manager.getPassWord());
            contentValues.put(ISBOSS_MANAGERS, manager.getIsboss());
            database.update(TABLE_MANAGERS, contentValues, ID_MANAGERS + " =?", new String[]{String.valueOf(manager.getId())});
            result = true;
        }
        closeDB();
        return result;
    }

    public void deleteAllRooms() {
        database = getWritableDatabase();
//        database.delete(TABLE_ROOMS, null, null);
        String drop = "DROP TABLE IF EXISTS " + TABLE_ROOMS;
        database.execSQL(drop);
        String createRooms = "CREATE TABLE " + TABLE_ROOMS + " (" +
                ID_ROOMS + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                AVAILABLE_ROOMS + " INTEGER, " +
                VIP_ROOMS + " INTEGER, " +
                PRICE_ROOMS + " INTEGER)";
        database.execSQL(createRooms);
        closeDB();
    }

    public boolean roomDataExists() {
        boolean result = false;
        database = getReadableDatabase();
        String search = "SELECT * FROM " + TABLE_ROOMS;
        cursor = database.rawQuery(search, null);
        if (cursor.moveToFirst() && cursor.getCount() > 0) {
            result = true;
        }
        closeDB();
        return result;
    }

    public void insertRooms(int num, int price, int vipnum, int vipprice) {
        database = getWritableDatabase();
        int a = num - vipnum;
        contentValues = new ContentValues();
        contentValues.put(AVAILABLE_ROOMS, 1);
        contentValues.put(VIP_ROOMS, 1);
        contentValues.put(PRICE_ROOMS, vipprice);
        for (int i = 0; i < vipnum; i++) {
            database.insert(TABLE_ROOMS, null, contentValues);
        }
        contentValues = new ContentValues();
        contentValues.put(AVAILABLE_ROOMS, 1);
        contentValues.put(VIP_ROOMS, 0);
        contentValues.put(PRICE_ROOMS, price);
        for (int i = 0; i < a; i++) {
            database.insert(TABLE_ROOMS, null, contentValues);
        }
        closeDB();
    }

    public List<Rooms> searchAllRooms() {
        database = getWritableDatabase();
        String search = "SELECT * FROM " + TABLE_ROOMS;
        List<Rooms> roomsList = new ArrayList<>();
        cursor = database.rawQuery(search, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                int isAvailable = cursor.getInt(1);
                int isVip = cursor.getInt(2);
                int price = cursor.getInt(3);
                roomsList.add(new Rooms(id, isAvailable, isVip, price));
            }
            while (cursor.moveToNext());
        }
        closeDB();
        return roomsList;
    }

    public List<Rooms> searchAllRoomsById(String idOrName) {
        database = getWritableDatabase();
        String search = "SELECT * FROM " + TABLE_ROOMS + " WHERE " + ID_ROOMS + " LIKE '%" + idOrName + "%'";
        List<Rooms> roomsList = new ArrayList<>();
        cursor = database.rawQuery(search, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                int isAvailable = cursor.getInt(1);
                int isVip = cursor.getInt(2);
                int price = cursor.getInt(3);
                roomsList.add(new Rooms(id, isAvailable, isVip, price));
            }
            while (cursor.moveToNext());
        }
        closeDB();
        return roomsList;
    }

    public Rooms searchOneRoomsById(int idRoom) {
        database = getReadableDatabase();
        String search = "SELECT * FROM " + TABLE_ROOMS + " WHERE " + ID_ROOMS + " = '" + idRoom + "'";
        Rooms room = new Rooms();
        cursor = database.rawQuery(search, null);
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            int isAvailable = cursor.getInt(1);
            int isVip = cursor.getInt(2);
            int price = cursor.getInt(3);
            room = new Rooms(id, isAvailable, isVip, price);
        }
        closeDB();
        return room;
    }

    public boolean updateRoom(Rooms room) {
        boolean result = true;
        database = getWritableDatabase();

        contentValues = new ContentValues();
        contentValues.put(ID_ROOMS, room.getId());
        contentValues.put(AVAILABLE_ROOMS, room.getAvailable());
        contentValues.put(VIP_ROOMS, room.getVip());
        contentValues.put(PRICE_ROOMS, room.getPrice());

        try {
            database.update(TABLE_ROOMS, contentValues, ID_ROOMS + " =?", new String[]{String.valueOf(room.getId())});
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }

        closeDB();
        return result;
    }

    public boolean insertService(Services service) {
        boolean result = true;
        database = getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put(NAME_SERVICES, service.getName());
        contentValues.put(PRICE_SERVICES, service.getPrice());
        contentValues.put(UNIT_SERVICES, service.getUnit());

        try {
            database.insert(TABLE_SERVICES, null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        closeDB();
        return result;
    }

    public void deleteAllServices() {
        database = getWritableDatabase();
//        database.delete(TABLE_SERVICES, null, null);
        String drop = "DROP TABLE IF EXISTS " + TABLE_SERVICES;
        database.execSQL(drop);
        String createServices = "CREATE TABLE " + TABLE_SERVICES + " (" +
                ID_SERVICES + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                NAME_SERVICES + " TEXT, " +
                PRICE_SERVICES + " INTEGER, " +
                UNIT_SERVICES + " TEXT)";
        database.execSQL(createServices);
        closeDB();
    }

    public List<Services> searchAllServices() {
        database = getWritableDatabase();
        String search = "SELECT * FROM " + TABLE_SERVICES + " ORDER BY " + NAME_SERVICES + " ASC";
        List<Services> servicesList = new ArrayList<>();
        cursor = database.rawQuery(search, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int price = cursor.getInt(2);
                String unit = cursor.getString(3);
                servicesList.add(new Services(id, name, price, unit));
            }
            while (cursor.moveToNext());
        }
        closeDB();
        return servicesList;
    }

    public List<Services> searchAllServicesByName(String sName) {
        database = getWritableDatabase();
        String search = "SELECT * FROM " + TABLE_SERVICES + " WHERE " + NAME_SERVICES + " LIKE '%" + sName + "%'";
        List<Services> servicesList = new ArrayList<>();
        cursor = database.rawQuery(search, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int price = cursor.getInt(2);
                String unit = cursor.getString(3);
                servicesList.add(new Services(id, name, price, unit));
            }
            while (cursor.moveToNext());
        }
        closeDB();
        return servicesList;
    }

    public Services searchOneServicesById(int sId) {
        database = getWritableDatabase();
        Services service = new Services();
        String search = "SELECT * FROM " + TABLE_SERVICES + " WHERE " + ID_SERVICES + " = '" + sId + "'";
        cursor = database.rawQuery(search, null);
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int price = cursor.getInt(2);
            String unit = cursor.getString(3);
            service = new Services(id, name, price, unit);
        }
        closeDB();
        return service;
    }

    public boolean updateService(Services service) {
        boolean result = true;
        database = getWritableDatabase();

        contentValues = new ContentValues();
        contentValues.put(ID_SERVICES, service.getId());
        contentValues.put(NAME_SERVICES, service.getName());
        contentValues.put(PRICE_SERVICES, service.getPrice());
        contentValues.put(UNIT_SERVICES, service.getUnit());
        try {
            database.update(TABLE_SERVICES, contentValues, ID_SERVICES + " =?", new String[]{String.valueOf(service.getId())});
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }

        closeDB();
        return result;
    }

    public void deleteServiceById(int id) {
        database = getWritableDatabase();
        database.delete(TABLE_SERVICES, ID_SERVICES + " =?", new String[]{String.valueOf(id)});
        database.delete(TABLE_GUESTSERVICES, SERVICEID_GUESTSERVICES + " =?", new String[]{String.valueOf(id)});
        closeDB();
    }

    public void deleteAllRentGuest() {
        database = getWritableDatabase();
        String drop = "DROP TABLE IF EXISTS " + TABLE_RENTGUESTS;
        database.execSQL(drop);
        String createRentGuests = "CREATE TABLE " + TABLE_RENTGUESTS + " (" +
                ID_RENTGUESTS + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                ROOMID_RENTGUESTS + " INTETER, " +
                NAME_RENTGUESTS + " TEXT, " +
                SEX_RENTGUESTS + " TEXT, " +
                CID_RENTGUESTS + " TEXT, " +
                TIMECHECKIN_RENTGUESTS + " TEXT, " +
                TIMECHECKOUT_RENTGUESTS + " TEXT)";
        database.execSQL(createRentGuests);
        closeDB();
    }

    public long insertRentGuest(RentGuests guests) {

        database = getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put(ROOMID_RENTGUESTS, guests.getRoomId());
        contentValues.put(NAME_RENTGUESTS, guests.getName());
        contentValues.put(SEX_RENTGUESTS, guests.getSex());
        contentValues.put(CID_RENTGUESTS, guests.getCid());
        contentValues.put(TIMECHECKIN_RENTGUESTS, guests.getTimeCheckin());
        contentValues.put(TIMECHECKOUT_RENTGUESTS, guests.getTimeCheckout());
        long id = database.insert(TABLE_RENTGUESTS, null, contentValues);
        closeDB();
        return id;
    }

    public List<RentGuests> searchAllRecentRenguest() {
        database = getWritableDatabase();
        List<RentGuests> rentGuestsList = new ArrayList<>();
        String search = "SELECT * FROM " + TABLE_RENTGUESTS + " WHERE " + TIMECHECKOUT_RENTGUESTS + " != 0";
        cursor = database.rawQuery(search, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                int roomId = cursor.getInt(1);
                String name = cursor.getString(2);
                String sex = cursor.getString(3);
                String cid = cursor.getString(4);
                String checkin = cursor.getString(5);
                String checkout = cursor.getString(6);
                rentGuestsList.add(new RentGuests(id, roomId, name, sex, cid, checkin, checkout));
            }
            while (cursor.moveToNext());
        }
        closeDB();
        return rentGuestsList;
    }

    public List<RentGuests> searchAllRecentRenguestByName(String gsName) {
        database = getWritableDatabase();
        List<RentGuests> rentGuestsList = new ArrayList<>();
        String search = "SELECT * FROM " + TABLE_RENTGUESTS + " WHERE " + TIMECHECKOUT_RENTGUESTS + " != 0 AND " + NAME_RENTGUESTS + " LIKE '%" + gsName + "%' OR " + ROOMID_RENTGUESTS + " = '" + gsName + "'";
        cursor = database.rawQuery(search, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                int roomId = cursor.getInt(1);
                String name = cursor.getString(2);
                String sex = cursor.getString(3);
                String cid = cursor.getString(4);
                String checkin = cursor.getString(5);
                String checkout = cursor.getString(6);
                rentGuestsList.add(new RentGuests(id, roomId, name, sex, cid, checkin, checkout));
            }
            while (cursor.moveToNext());
        }
        closeDB();
        return rentGuestsList;
    }

    public RentGuests searchOneRentGuestByRoomId(long rId) {
        database = getWritableDatabase();
        RentGuests guest = new RentGuests();
        String search = "SELECT * FROM " + TABLE_RENTGUESTS + " WHERE " + ROOMID_RENTGUESTS + " = '" + rId + "' AND " + TIMECHECKOUT_RENTGUESTS + " = 0";
        cursor = database.rawQuery(search, null);
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            int roomId = cursor.getInt(1);
            String name = cursor.getString(2);
            String sex = cursor.getString(3);
            String cid = cursor.getString(4);
            String checkin = cursor.getString(5);
            guest = new RentGuests(id, roomId, name, sex, cid, checkin);
        }
        closeDB();
        return guest;
    }

    public boolean updateRentGuest(RentGuests guests) {
        boolean result = true;
        database = getWritableDatabase();

        contentValues = new ContentValues();
        contentValues.put(ROOMID_RENTGUESTS, guests.getRoomId());
        contentValues.put(NAME_RENTGUESTS, guests.getName());
        contentValues.put(SEX_RENTGUESTS, guests.getSex());
        contentValues.put(CID_RENTGUESTS, guests.getCid());
        contentValues.put(TIMECHECKIN_RENTGUESTS, guests.getTimeCheckin());
        contentValues.put(TIMECHECKOUT_RENTGUESTS, guests.getTimeCheckout());

        try {
            database.update(TABLE_RENTGUESTS, contentValues, ID_RENTGUESTS + " =?", new String[]{String.valueOf(guests.getId())});
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }

        closeDB();
        return result;
    }


    public void deleteAllGuestServices() {
        database = getWritableDatabase();
        String drop = "DROP TABLE IF EXISTS " + TABLE_GUESTSERVICES;
        database.execSQL(drop);
        String createGuestServices = "CREATE TABLE " + TABLE_GUESTSERVICES + " (" +
                ID_GUESTSERVICES + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                GUESTID_GUESTSERVICES + " INTERGER, " +
                SERVICEID_GUESTSERVICES + " INTEGER, " +
                AMOUNT_GUESTSERVICES + " INTEGER)";
        database.execSQL(createGuestServices);
        closeDB();
    }

    public long insertGuestService(GuestServices guestService) {
        database = getWritableDatabase();
        contentValues = new ContentValues();

        contentValues.put(GUESTID_GUESTSERVICES, guestService.getGuestid());
        contentValues.put(SERVICEID_GUESTSERVICES, guestService.getServiceid());
        contentValues.put(AMOUNT_GUESTSERVICES, guestService.getAmount());

        long id = database.insert(TABLE_GUESTSERVICES, null, contentValues);
        closeDB();
        return id;
    }

    public GuestServices searchOneGuestServiceById(int gsId) {
        database = getWritableDatabase();
        GuestServices guestService = new GuestServices();
        String search = "SELECT * FROM " + TABLE_GUESTSERVICES + " WHERE " + ID_GUESTSERVICES + " = '" + gsId + "'";
        cursor = database.rawQuery(search, null);
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            int guestId = cursor.getInt(1);
            int serviceId = cursor.getInt(2);
            int amount = cursor.getInt(3);

            guestService = new GuestServices(id, guestId, serviceId, amount);
        }
        closeDB();
        return guestService;
    }

    public boolean updateGuestService(GuestServices guestService) {
        boolean result = true;
        database = getWritableDatabase();

        contentValues = new ContentValues();
        contentValues.put(ID_GUESTSERVICES, guestService.getId());
        contentValues.put(GUESTID_GUESTSERVICES, guestService.getGuestid());
        contentValues.put(SERVICEID_GUESTSERVICES, guestService.getServiceid());
        contentValues.put(AMOUNT_GUESTSERVICES, guestService.getAmount());

        try {
            database.update(TABLE_GUESTSERVICES, contentValues, ID_GUESTSERVICES + " =?", new String[]{String.valueOf(guestService.getId())});
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }

        closeDB();
        return result;
    }

    public int checkExistGuestService(GuestServices guestService) {
        int result = 0;
        database = getReadableDatabase();
        String check = "SELECT * FROM " + TABLE_GUESTSERVICES + " WHERE "
                + GUESTID_GUESTSERVICES + " = '" + guestService.getGuestid()
                + "' AND " + SERVICEID_GUESTSERVICES + " = '" + guestService.getServiceid() + "'";
        cursor = database.rawQuery(check, null);
        if (cursor.moveToFirst() && cursor.getCount() > 0) {
            result = cursor.getInt(0);
        }
        closeDB();
        return result;
    }

    public void deleteGuestServiceById(int id) {
        database = getWritableDatabase();
        database.delete(TABLE_GUESTSERVICES, ID_GUESTSERVICES + " =?", new String[]{String.valueOf(id)});
        closeDB();
    }

    public RGS getAllInfoRGS(GuestServices guestService) {
        int gId = guestService.getGuestid();
        int sId = guestService.getServiceid();

        RGS rgs = new RGS();
        database = getWritableDatabase();
        String search = "SELECT * FROM " + TABLE_RENTGUESTS + " WHERE " + ID_RENTGUESTS + " = '" + gId + "' AND " + TIMECHECKOUT_RENTGUESTS + " = 0";
        String search2 = "SELECT * FROM " + TABLE_SERVICES + " WHERE " + ID_SERVICES + " = '" + sId + "'";
        cursor = database.rawQuery(search, null);
        Cursor cursor2 = database.rawQuery(search2, null);

        if (cursor.moveToFirst() && cursor2.moveToFirst()) {
            int roomId = cursor.getInt(1);
            String guestName = cursor.getString(2);
            String guestSex = cursor.getString(3);
            String guestCid = cursor.getString(4);
            String guestCheckin = cursor.getString(5);
            String guestCheckout = cursor.getString(6);

            String search3 = "SELECT * FROM " + TABLE_ROOMS + " WHERE " + ID_ROOMS + " = '" + roomId + "'";
            Cursor cursor3 = database.rawQuery(search3, null);
            int roomIsVip = 0;
            int roomPrice = 0;
            if (cursor3.moveToFirst()) {
                roomIsVip = cursor3.getInt(2);
                roomPrice = cursor3.getInt(3);
            }

            String serviceName = cursor2.getString(1);
            int servicePrice = cursor2.getInt(2);
            String serviceUnit = cursor2.getString(3);

            rgs = new RGS(guestService.getId(), gId, roomId, guestName, guestSex, guestCid, guestCheckin, guestCheckout, roomIsVip, roomPrice, sId, serviceName, servicePrice, serviceUnit, guestService.getAmount());
        }
        closeDB();
        return rgs;
    }

    public List<GuestServices> searchAllGuestService(int gId) {
        database = getWritableDatabase();
        String search = "SELECT * FROM " + TABLE_GUESTSERVICES + " WHERE " + GUESTID_GUESTSERVICES + " = '" + gId + "'";
        List<GuestServices> guestService = new ArrayList<>();
        cursor = database.rawQuery(search, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                int guestId = cursor.getInt(1);
                int serviceId = cursor.getInt(2);
                int amount = cursor.getInt(3);

                guestService.add(new GuestServices(id, guestId, serviceId, amount));
            }
            while (cursor.moveToNext());
        }
        closeDB();
        return guestService;
    }


    private void closeDB() {
        if (database != null) {
            database.close();
        }
        if (contentValues != null) {
            contentValues.clear();
        }
        if (cursor != null) {
            cursor.close();
        }
    }

}
