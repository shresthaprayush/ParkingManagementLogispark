package com.logispark.parkingmanagementlogispark.utilites;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.logispark.parkingmanagementlogispark.models.ModelDriver;
import com.logispark.parkingmanagementlogispark.models.ModelLocations;
import com.logispark.parkingmanagementlogispark.models.ModelParkingData;
import com.logispark.parkingmanagementlogispark.models.ModelPrintTable;
import com.logispark.parkingmanagementlogispark.models.ModelSalesStats;
import com.logispark.parkingmanagementlogispark.models.ModelService;
import com.logispark.parkingmanagementlogispark.models.ModelSlots;
import com.logispark.parkingmanagementlogispark.models.ModelUser;
import com.logispark.parkingmanagementlogispark.models.ModelVehicle;
import com.logispark.parkingmanagementlogispark.models.ModelVehicleRate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DbHandler extends SQLiteOpenHelper {


    public static final String VEHICLE_TABLE = "vehicle_table";
    public static final String VEHICLE_RATE_TABLE = "vehicle_rate_table";
    public static final String USER_TABLE = "user_table";
    public static final String DRIVER_TABLE = "driver_table";
    public static final String SERVICE_TABLE = "service_table";
    public static final String PARKING_TABLE = "parking_table";
    public static final String PRINT_TABLE = "print_table";
    public static final String SLOT_TABLE = "slot_table";
    public static final String AREA_TABLE = "area_table";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CONTACT = "contact";
    public static final String COLUMN_SYNC = "syncStatus";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_AREAId = "areaId";
    public static final String COLUMN_SERVERID = "serverId";
    public static final String COLUMN_RATE = "rate";
    public static final String COLUMN_DISCOUNT = "discount";
    public static final String COLUMN_TICKETCODE = "ticketCode";
    public static final String COLUMN_INTIME = "inTime";
    public static final String COLUMN_OUTTIME = "outTime";
    public static final String COLUMN_CREATEDAT = "createdAt";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_TOTALDURATION = "duration";
    public static final String COLUMN_VECHILENO = "vechileNo";
    public static final String COLUMN_VECHILETYPE = "type";
    public static final String COLUMN_VECHILEID = "vechileID";
    public static final String COLUMN_USERID = "userID";
    public static final String COLUMN_SLOTID = "slotId";
    public static final String COLUMN_SLOT = "slot";
    public static final String COLUMN_TOKENNO = "tokenNo";
    public static final String COLUMN_ACTIVE = "active";
    private static final String COLUMN_LOCATION = "location";
    private static final String COLUMN_PAN = "pan";
    private static final String COLUMN_LICENSE_NUMBER = "licenseNumber";
    private static final String COLUMN_PLACE_ORIGIN = "placeOfOrigin";
    private static final String COLUMN_ACCOMMODATION = "accommodation";
    private static final String COLUMN_WASHING = "washing";
    private static final String COLUMN_NATIONALITY = "nationality";
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "parkingDB";
    private static final String COLUMN_DRIVERID = "driverID";
    private static final String COLUMN_PARKINGTABLEID = "parkingID";
    private static final String COLUMN_INVOICECOUNT = "invoiceCount";
    private static final String COLUMN_SLIPCOUNT = "slipCount";


    public DbHandler(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        //Creating user table
        String createUserTableQuery = "CREATE TABLE " + USER_TABLE + "( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_PASSWORD + " TEXT, " + COLUMN_CONTACT + " TEXT )";
        //Creating area table
        String createAreaTableQuery = "CREATE TABLE " + AREA_TABLE + "( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT, " + COLUMN_SERVERID + " INTEGER )";
        //Creating slot table
        String createSlotTable = "CREATE TABLE " + SLOT_TABLE + "( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT, " + COLUMN_LOCATION + " TEXT, " + COLUMN_SERVERID + " INTEGER )";
        //Creating table vechile table
        String createVehicleRateQuery = "CREATE TABLE " + VEHICLE_RATE_TABLE + "( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_RATE + " INTEGER , "
                + COLUMN_VECHILETYPE + " TEXT, "
                + COLUMN_DISCOUNT + " INTEGER, "
                + COLUMN_SERVERID + " INTEGER, "
                + COLUMN_SYNC + " INTEGER )";

        //Create parking Count Table

        String createPrintTable = "CREATE TABLE " + PRINT_TABLE + "( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_INVOICECOUNT + " INTEGER, "
                + COLUMN_SLIPCOUNT + " INTEGER, "
                + COLUMN_PARKINGTABLEID + " INTEGER, FOREIGN KEY(" + COLUMN_PARKINGTABLEID + ") REFERENCES "
                + PARKING_TABLE + "( " + COLUMN_ID + " ))";

        //Creatig Table for Driver
        String createDriverQuery = "CREATE TABLE " + DRIVER_TABLE + "( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT , " + COLUMN_PAN + " TEXT, "
                + COLUMN_LICENSE_NUMBER + " TEXT, "
                + COLUMN_CONTACT + " TEXT, "
                + COLUMN_PLACE_ORIGIN + " TEXT,"
                + COLUMN_NATIONALITY + " TEXT )";

        //Creating Service Table
        String createServiceTable = "CREATE TABLE " + SERVICE_TABLE + "( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT, " + COLUMN_RATE + " TEXT, " + COLUMN_SERVERID + " INTEGER )";

        //Todo: Change this
        String createParkingTableQuery = "CREATE TABLE " + PARKING_TABLE + "( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TICKETCODE + " TEXT, " + COLUMN_VECHILEID + " INTEGER , " + COLUMN_INTIME + " DATE, " + COLUMN_OUTTIME + "  DATE, " + COLUMN_CREATEDAT + "  DATE, " + COLUMN_TOKENNO + "  INTEGER, " + COLUMN_DRIVERID + "  INTEGER, "
                + COLUMN_ACTIVE + " INTEGER, " + COLUMN_RATE + " INTEGER, " + COLUMN_DISCOUNT + " INTEGER, " + COLUMN_AMOUNT + " INTEGER, " + COLUMN_TOTALDURATION + " INTEGER, " + COLUMN_SYNC + " INTEGER, "
                + COLUMN_SLOTID + " INTEGER, " + COLUMN_VECHILENO + " INTEGER," + COLUMN_SLOT + " TEXT, " + COLUMN_ACCOMMODATION + " INTEGER," + COLUMN_WASHING + " INTEGER, FOREIGN KEY(" + COLUMN_VECHILEID + ") REFERENCES " + VEHICLE_RATE_TABLE + "(" + COLUMN_ID + " ), FOREIGN KEY("
                + COLUMN_DRIVERID + ") REFERENCES " + DRIVER_TABLE + "(" + COLUMN_ID + "))";

        db.execSQL(createUserTableQuery);
        db.execSQL(createAreaTableQuery);
        db.execSQL(createSlotTable);
        db.execSQL(createPrintTable);
        db.execSQL(createVehicleRateQuery);
        db.execSQL(createParkingTableQuery);
        db.execSQL(createServiceTable);
        db.execSQL(createDriverQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    //to add printTable
    public long addPrintTable(ModelPrintTable modelPrintTable) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_INVOICECOUNT, modelPrintTable.getEstimateCount());
        cv.put(COLUMN_SLIPCOUNT, modelPrintTable.getSlipCount());
        cv.put(COLUMN_PARKINGTABLEID, modelPrintTable.getParkingTableID());

        long insert = db.insert(PRINT_TABLE, null, cv);

        return insert;

    }

    /**
     * @param id
     * @param countToIncrease , 0 = estimate, 1 = slip
     * @return
     */
    public long updatePrint(int id, int countToIncrease, int count) {

        Log.d("Update Print", String.valueOf(count));
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();


        if (countToIncrease == 0) {
            cv.put(COLUMN_INVOICECOUNT, count);

        } else if (countToIncrease == 1) {
            cv.put(COLUMN_SLIPCOUNT, count);

        } else {

        }

        long update = db.update(PRINT_TABLE, cv, COLUMN_ID + "= ?", new String[]{String.valueOf(id)});
        return update;


    }

    //Search from parking Id
    public ModelPrintTable searchPrintTableFromId(long ids) {

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + PRINT_TABLE + " WHERE " + COLUMN_PARKINGTABLEID + " = " + ids;
        Cursor c = db.rawQuery(sql, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            int id = c.getInt(0);
            int estimateCount = c.getInt(1);
            int slipCount = c.getInt(2);
            int parkingTableId = c.getInt(3);


            return new ModelPrintTable(parkingTableId, estimateCount, slipCount);


        } else {
            return new ModelPrintTable(-1, -1, -1);
        }


    }


    public long addDriver(ModelDriver modelDriver) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, modelDriver.getName());
        cv.put(COLUMN_CONTACT, modelDriver.getContactNumber());
        cv.put(COLUMN_LICENSE_NUMBER, modelDriver.getLicenceNumber());
        cv.put(COLUMN_PAN, modelDriver.getPanNumber());
        cv.put(COLUMN_PLACE_ORIGIN, modelDriver.getPlaceOfOrigin());
        cv.put(COLUMN_NATIONALITY, modelDriver.getNationality());

        long insert = db.insert(DRIVER_TABLE, null, cv);

        return insert;

    }


    public ModelDriver searchDriver(long idDb) {

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + DRIVER_TABLE + " WHERE " + COLUMN_ID + " = " + idDb;
        Cursor c = db.rawQuery(sql, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            int id = c.getInt(0);
            String name = c.getString(1);
            String pan = c.getString(2);
            String licenseNumberDb = c.getString(3);
            String contact = c.getString(4);
            String placeOfOrigin = c.getString(5);
            String nationality = c.getString(6);

            ModelDriver modelDriver = new ModelDriver(id, name, pan, contact, licenseNumberDb, placeOfOrigin, nationality, "");
            return modelDriver;


        } else {
            return new ModelDriver(-1, "", "", "", "", "", "", "");
        }


    }


    public ModelVehicle searchByVechileNumber(String vechieleNumber) {

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + VEHICLE_TABLE + " WHERE " + COLUMN_VECHILENO + " = \" " + vechieleNumber + "\"";
        Cursor c = db.rawQuery(sql, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            int id = c.getInt(0);
            String vechileNo = c.getString(1);
            int userID = c.getInt(2);
            String type = c.getString(3);

            ModelVehicle modelVehicle = new ModelVehicle(id, vechileNo, type, userID);
            return modelVehicle;


        } else {
            return new ModelVehicle(-1, "", "", 0);
        }


    }

    public ModelVehicleRate searchFromRateName(String rateName) {

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + VEHICLE_RATE_TABLE + " WHERE " + COLUMN_VECHILETYPE + " = \"" + rateName + "\"";
        Cursor c = db.rawQuery(sql, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            int id = c.getInt(0);
            int rate = c.getInt(1);
            String type = c.getString(2);
            int discount = c.getInt(3);
            String serverId = c.getString(4);
            int syncStatus = c.getInt(5);


            ModelVehicleRate modelVehicle = new ModelVehicleRate(id, rate, discount, serverId, syncStatus, type);
            return modelVehicle;


        } else {
            return new ModelVehicleRate(-1, 0, 0, "", 0, "");
        }


    }

    public ModelVehicleRate searchVehicleFromRate(int drate) {

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + VEHICLE_RATE_TABLE + " WHERE " + COLUMN_RATE + " = " + drate;
        Cursor c = db.rawQuery(sql, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            int id = c.getInt(0);
            int rate = c.getInt(1);
            String type = c.getString(2);
            int discount = c.getInt(3);
            String serverId = c.getString(4);
            int syncStatus = c.getInt(5);


            ModelVehicleRate modelVehicle = new ModelVehicleRate(id, rate, discount, serverId, syncStatus, type);
            return modelVehicle;


        } else {
            return new ModelVehicleRate(-1, 0, 0, "", 0, "");
        }


    }

    public ModelVehicleRate searchByVechileId(long vechileId) {

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + VEHICLE_RATE_TABLE + " WHERE " + COLUMN_ID + " = " + vechileId;
        Cursor c = db.rawQuery(sql, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            int id = c.getInt(0);
            int rate = c.getInt(1);
            String type = c.getString(2);
            int discount = c.getInt(3);
            String serverId = c.getString(4);
            int syncStatus = c.getInt(5);


            ModelVehicleRate modelVehicle = new ModelVehicleRate(id, rate, discount, serverId, syncStatus, type);
            return modelVehicle;


        } else {
            return new ModelVehicleRate(-1, 0, 0, "", 0, "");
        }


    }

    //Parking Data

    public long addParkingData(ModelParkingData modelParkingData) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TICKETCODE, modelParkingData.getTicketCode());
        cv.put(COLUMN_VECHILEID, modelParkingData.getVechileId());
        cv.put(COLUMN_INTIME, modelParkingData.getInTime());
        cv.put(COLUMN_OUTTIME, modelParkingData.getOutTime());
        cv.put(COLUMN_ACTIVE, modelParkingData.getActive());
        cv.put(COLUMN_RATE, modelParkingData.getRate());
        cv.put(COLUMN_DISCOUNT, modelParkingData.getDiscount());
        cv.put(COLUMN_AMOUNT, modelParkingData.getAmount());
        cv.put(COLUMN_TOTALDURATION, modelParkingData.getDuration());
        cv.put(COLUMN_SLOTID, modelParkingData.getSlot());
        cv.put(COLUMN_SYNC, modelParkingData.getSync());
        cv.put(COLUMN_VECHILENO, modelParkingData.getVehicleNumber());
        cv.put(COLUMN_CREATEDAT, modelParkingData.createdAt);
        cv.put(COLUMN_TOKENNO, modelParkingData.tokenNo);
        cv.put(COLUMN_SLOT, modelParkingData.slot);
        cv.put(COLUMN_DRIVERID, modelParkingData.driverId);
        cv.put(COLUMN_ACCOMMODATION, modelParkingData.accommodation);
        cv.put(COLUMN_WASHING, modelParkingData.washing);

        return db.insert(PARKING_TABLE, null, cv);

    }


    public ModelParkingData searchParkingDataFromToken(String token) {

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + PARKING_TABLE + " WHERE " + COLUMN_TICKETCODE + " = \"" + token + "\" and " + COLUMN_ACTIVE + " = 1";
        Cursor c = db.rawQuery(sql, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            int id = c.getInt(0);
            String ticketCode = c.getString(1);
            long vehicleId = c.getLong(2);
            String inTime = c.getString(3);
            String outTime = c.getString(4);
            String createdAt = c.getString(5);
            int tokenNo = c.getInt(6);
            int driverId = c.getInt(7);
            int active = c.getInt(8);
            int rate = c.getInt(9);
            int discount = c.getInt(10);
            double amount = c.getDouble(11);
            int duration = c.getInt(12);
            int syncStatus = c.getInt(13);
            int slotId = c.getInt(14);
            String vehiclenumber = c.getString(15);
            String slot = c.getString(16);
            int accommodation = c.getInt(17);
            int washing = c.getInt(18);

            ModelParkingData modelParkingData = new ModelParkingData(id, rate, slotId, active, discount, duration, syncStatus, tokenNo, accommodation, washing, ticketCode, inTime, outTime, vehiclenumber, createdAt, slot, amount, vehicleId, driverId);
            return modelParkingData;


        } else {
            return new ModelParkingData(-1, 0, -1, -1, 0, 0, 0, 0, 0, 0, "", "", "", "", "", "", 0.00, -1, -1);
        }


    }


    public List<ModelParkingData> searchParkingDataFromVehicleNumber(String vehicleNumber) {

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + PARKING_TABLE + " WHERE " + COLUMN_VECHILENO + " = \"" + vehicleNumber + "\" and " + COLUMN_ACTIVE + " = 1";
        Cursor c = db.rawQuery(sql, null);

        List<ModelParkingData> modelParkingDataList = new ArrayList<>();
        while (c.moveToNext()) {
            int id = c.getInt(0);
            String ticketCode = c.getString(1);
            long vehicleId = c.getLong(2);
            String inTime = c.getString(3);
            String outTime = c.getString(4);
            String createdAt = c.getString(5);
            int tokenNo = c.getInt(6);
            int driverId = c.getInt(7);
            int active = c.getInt(8);
            int rate = c.getInt(9);
            int discount = c.getInt(10);
            double amount = c.getDouble(11);
            int duration = c.getInt(12);
            int syncStatus = c.getInt(13);
            int slotId = c.getInt(14);
            String vehiclenumber = c.getString(15);
            String slot = c.getString(16);
            int accommodation = c.getInt(17);
            int washing = c.getInt(18);


            ModelParkingData modelParkingData = new ModelParkingData(id, rate, slotId, active, discount, duration, syncStatus, tokenNo, accommodation, washing, ticketCode, inTime, outTime, vehiclenumber, createdAt, slot, amount, vehicleId, driverId);
            modelParkingDataList.add(modelParkingData);

        }


        c.close();
        db.close();

        return modelParkingDataList;

    }


    public long updateParkingData(ModelParkingData modelParkingData) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ACTIVE, modelParkingData.active);
        cv.put(COLUMN_OUTTIME, modelParkingData.outTime);
        cv.put(COLUMN_AMOUNT, modelParkingData.amount);
        cv.put(COLUMN_TOTALDURATION, modelParkingData.duration);

        long update = db.update(PARKING_TABLE, cv, COLUMN_ID + "= ?", new String[]{String.valueOf(modelParkingData.id)});

        return update;

    }

    public long updateSyncOnParkingData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_SYNC, 1);

        long update = db.update(PARKING_TABLE, cv, COLUMN_ID + "= ?", new String[]{String.valueOf(id)});

        return update;

    }

    public long updateSyncOnParkingDataMultiple(String[] id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_SYNC, 1);

        long update = db.update(PARKING_TABLE, cv, COLUMN_ID + "= ?", id);

        return update;

    }

    public List<ModelParkingData> getUnsyncedData() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        List<ModelParkingData> modelParkingDataList = new ArrayList<>();
        String sql = "SELECT * FROM " + PARKING_TABLE + " WHERE " +
                COLUMN_SYNC + " = 0 AND " + COLUMN_ACTIVE + " = 0";

        Cursor c = sqLiteDatabase.rawQuery(sql, null);

        while (c.moveToNext()) {
            int id = c.getInt(0);
            String ticketCode = c.getString(1);
            long vehicleId = c.getLong(2);
            String inTime = c.getString(3);
            String outTime = c.getString(4);
            String createdAt = c.getString(5);
            int tokenNo = c.getInt(6);
            int driverId = c.getInt(7);
            int active = c.getInt(8);
            int rate = c.getInt(9);
            int discount = c.getInt(10);
            double amount = c.getDouble(11);
            int duration = c.getInt(12);
            int syncStatus = c.getInt(13);
            int slotId = c.getInt(14);
            String vehiclenumber = c.getString(15);
            String slot = c.getString(16);
            int accommodation = c.getInt(17);
            int washing = c.getInt(18);


            ModelParkingData modelParkingData = new ModelParkingData(id, rate, slotId, active, discount, duration, syncStatus, tokenNo, accommodation, washing, ticketCode, inTime, outTime, vehiclenumber, createdAt, slot, amount, vehicleId, driverId);
            modelParkingDataList.add(modelParkingData);

        }


        c.close();
        sqLiteDatabase.close();

        return modelParkingDataList;

    }


    public List<ModelParkingData> getTodaysAllSalesData() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Date date = new Date();
        String startOfDay = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault()).format(atStartOfDay(date));
        String endOfDay = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault()).format(endOfDay(date));


        List<ModelParkingData> modelParkingDataList = new ArrayList<>();


        String sql = "SELECT * FROM " + PARKING_TABLE + " WHERE " +
                COLUMN_CREATEDAT + " BETWEEN \"" + startOfDay + "\" AND \"" + endOfDay +
                "\" AND " + COLUMN_ACTIVE + " = 0";

        Cursor c = sqLiteDatabase.rawQuery(sql, null);

        while (c.moveToNext()) {
            int id = c.getInt(0);
            String ticketCode = c.getString(1);
            long vehicleId = c.getLong(2);
            String inTime = c.getString(3);
            String outTime = c.getString(4);
            String createdAt = c.getString(5);
            int tokenNo = c.getInt(6);
            int driverId = c.getInt(7);
            int active = c.getInt(8);
            int rate = c.getInt(9);
            int discount = c.getInt(10);
            double amount = c.getDouble(11);
            int duration = c.getInt(12);
            int syncStatus = c.getInt(13);
            int slotId = c.getInt(14);
            String vehiclenumber = c.getString(15);
            String slot = c.getString(16);
            int accommodation = c.getInt(17);
            int washing = c.getInt(18);

            ModelParkingData modelParkingData = new ModelParkingData(id, rate, slotId, active, discount, duration, syncStatus, tokenNo, accommodation, washing, ticketCode, inTime, outTime, vehiclenumber, createdAt, slot, amount, vehicleId, driverId);
            modelParkingDataList.add(modelParkingData);
        }


        c.close();
        sqLiteDatabase.close();

        return modelParkingDataList;

    }

    public Cursor getTodaysAllSalesDataCursor() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Date date = new Date();
        String startOfDay = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault()).format(atStartOfDay(date));
        String endOfDay = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault()).format(endOfDay(date));


        List<ModelParkingData> modelParkingDataList = new ArrayList<>();


        String sql = "SELECT * FROM " + PARKING_TABLE + " WHERE " +
                COLUMN_CREATEDAT + " BETWEEN \"" + startOfDay + "\" AND \"" + endOfDay +
                "\" AND " + COLUMN_ACTIVE + " = 0";

        Cursor c = sqLiteDatabase.rawQuery(sql, null);


//        c.close();
//        sqLiteDatabase.close();

        return c;

    }


    public ModelSalesStats getSalesData() {
        Date date = new Date();
        String startOfDay = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault()).format(atStartOfDay(date));
        String endOfDay = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault()).format(endOfDay(date));

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT Sum(" + COLUMN_AMOUNT + ") as 'totalAmount', COUNT(" +
                COLUMN_TOKENNO + ") as 'tokens' FROM " +
                PARKING_TABLE + " WHERE " +
                COLUMN_CREATEDAT + " BETWEEN \"" + startOfDay + "\" AND \"" + endOfDay +
                "\" AND " + COLUMN_ACTIVE + " = 0";

        Cursor c = db.rawQuery(query, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            int totalAmount = c.getInt(0);
            int tokens = c.getInt(1);


            ModelSalesStats modelSalesStats = new ModelSalesStats(totalAmount, tokens);
            return modelSalesStats;


        } else {
            return new ModelSalesStats(0, 0);
        }

    }

    /**
     * Add Locations
     *
     * @param locations
     * @return
     */
    public long addLocation(ModelLocations locations) {
        Log.d("DB ", "Add Location");

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, locations.getName());
        cv.put(COLUMN_SERVERID, locations.getId());

        long insert = db.insert(AREA_TABLE, null, cv);

        return insert;
    }

    /**
     * Add Slots
     *
     * @param modelSlots
     * @return
     */
    public long addSlots(ModelSlots modelSlots) {

        Log.d("DB ", "Add SLot");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, modelSlots.getName());
        cv.put(COLUMN_SERVERID, modelSlots.getId());
        cv.put(COLUMN_LOCATION, modelSlots.getLocation());

        long insert = db.insert(SLOT_TABLE, null, cv);

        return insert;
    }

    /**
     * Add Service
     *
     * @param modelService
     * @return
     */
    public long addService(ModelService modelService) {

        Log.d("DB ", "Add SLot");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, modelService.getService());
        cv.put(COLUMN_SERVERID, modelService.getServerId());
        cv.put(COLUMN_RATE, modelService.getRate());

        long insert = db.insert(SERVICE_TABLE, null, cv);

        return insert;
    }


    /**
     * To add vehicle rate
     *
     * @param modelVehicleRate
     * @return
     */
    public boolean addVehicleRate(ModelVehicleRate modelVehicleRate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_RATE, modelVehicleRate.getRate());
        cv.put(COLUMN_VECHILETYPE, modelVehicleRate.getVehicleType());
        cv.put(COLUMN_DISCOUNT, modelVehicleRate.getDiscount());
        cv.put(COLUMN_SERVERID, modelVehicleRate.getId());
        cv.put(COLUMN_SYNC, 1);

        long insert = db.insert(VEHICLE_RATE_TABLE, null, cv);

        return insert == -1;
    }

    /**
     * Delete all data of vehicle rate database for syncing
     */
    public void deleteAllVehicleRate() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + VEHICLE_RATE_TABLE;

        db.execSQL(query);
    }

    /**
     * Delete all data of vehicle slot database for syncing
     */
    public void dellAllVehicleSlot() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + SLOT_TABLE;

        db.execSQL(query);
    }


    /**
     * Delete all data of service in database for syncing
     */
    public void deleteAllServices() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + SERVICE_TABLE;

        db.execSQL(query);
    }


    /**
     * Delete all data of vehicle area database for syncing
     */
    public void deleteAllVehicleArea() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + AREA_TABLE;

        db.execSQL(query);
    }

    /**
     * Get All Vehicle Rates
     *
     * @return List of vehicle rates
     */
    public List<ModelVehicleRate> getAllVehicleRate() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<ModelVehicleRate> modelVehicleRateList = new ArrayList<>();

        String query = "SELECT * FROM " + VEHICLE_RATE_TABLE;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {

            do {
                int id = cursor.getInt(0);
                int rate = cursor.getInt(1);
                String type = cursor.getString(2);
                int discount = cursor.getInt(3);
                String serverId = cursor.getString(4);
                int syncStatus = cursor.getInt(5);


                ModelVehicleRate modelVehicleRate = new ModelVehicleRate(id, rate, discount, serverId, syncStatus, type);
                modelVehicleRateList.add(modelVehicleRate);
            }
            while (cursor.moveToNext());
        } else {
            //error
        }

        cursor.close();
        db.close();
        return modelVehicleRateList;

    }


    /**
     * Get All Service Rates
     *
     * @return List of service rates
     */
    public List<ModelService> getAllServiceRate() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<ModelService> modelServiceList = new ArrayList<>();

        String query = "SELECT * FROM " + SERVICE_TABLE;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {

            do {
                int id = cursor.getInt(0);
                String service = cursor.getString(1);
                int serverId = cursor.getInt(3);
                String rate = cursor.getString(2);


                ModelService modelService = new ModelService(serverId, id, rate, service);
                modelServiceList.add(modelService);
            }
            while (cursor.moveToNext());
        } else {
            //error
        }

        cursor.close();
        db.close();
        return modelServiceList;

    }


    /**
     * Get All Slots
     *
     * @return List of slots
     */
    public List<ModelSlots> getAllSlots() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<ModelSlots> modelSlotsList = new ArrayList<>();

        String query = "SELECT * FROM " + SLOT_TABLE;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {

            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String location = cursor.getString(2);
                int serverId = cursor.getInt(3);


                ModelSlots modelSlots = new ModelSlots(id, 0, serverId, name, location);
                modelSlotsList.add(modelSlots);
            }
            while (cursor.moveToNext());
        } else {
            //error
        }

        cursor.close();
        db.close();
        return modelSlotsList;

    }


    /**
     * @param location
     * @return
     */
    public List<ModelSlots> getSlotsByLocation(String location) {

        SQLiteDatabase db = this.getReadableDatabase();
        List<ModelSlots> modelSlotsList = new ArrayList<>();

        String query = "SELECT * FROM " + SLOT_TABLE + " WHERE " + COLUMN_LOCATION + " like " + "'" + location + "'";
        Log.d("Query", query);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {

            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String locationDb = cursor.getString(2);
                int serverId = cursor.getInt(3);


                ModelSlots modelSlots = new ModelSlots(id, 0, serverId, name, location);
                modelSlotsList.add(modelSlots);
            }
            while (cursor.moveToNext());
        } else {
            //error
            Log.d("Query", "Error");
        }

        cursor.close();
        db.close();
        return modelSlotsList;


    }


    /**
     * Get All Vehicle area
     *
     * @return List of area
     */

    public List<ModelLocations> getAllArea() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<ModelLocations> modelLocationsList = new ArrayList<>();

        String query = "SELECT * FROM " + AREA_TABLE;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {

            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int serverId = cursor.getInt(2);


                ModelLocations modelLocations = new ModelLocations(id, serverId, name, null);
                modelLocationsList.add(modelLocations);
            }
            while (cursor.moveToNext());
        } else {
            //error
        }

        cursor.close();
        db.close();
        return modelLocationsList;

    }


    public Date endOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }


    public Date atStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }


    public ModelVehicleRate getVehicleRate(long vechileId) {

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        String query = "SELECT * FROM " + VEHICLE_RATE_TABLE + " WHERE " + COLUMN_SERVERID + " = " + vechileId;

        Cursor c = sqLiteDatabase.rawQuery(query, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            int id = c.getInt(0);
            int rate = c.getInt(1);
            String type = c.getString(2);
            int discount = c.getInt(3);
            int serverId = c.getInt(4);
            int syncStatus = c.getInt(5);

            ModelVehicleRate modelVehicleRate = new ModelVehicleRate(id, rate, discount, String.valueOf(serverId), syncStatus, type);
            return modelVehicleRate;


        } else {
            return new ModelVehicleRate(-1, 0, 0, "", 0, "");
        }


    }
}
