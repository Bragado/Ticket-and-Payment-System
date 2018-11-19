package cmov.miguellucas.com.customerapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import cmov.miguellucas.com.customerapp.Models.Order;
import cmov.miguellucas.com.customerapp.Models.Product;
import cmov.miguellucas.com.customerapp.Models.Ticket;

public class TicketDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ticket_db5";
    public static final int DATABASE_VERSION = 1;


    public static final String CREATE_TABLE_TICKET = "create table " + TicketContract.ContactEntry.TABLE_TICKET_NAME + "("
            + "id text primary key unique, "
            + TicketContract.ContactEntry.TICKET_PLACE + " number,"
            + TicketContract.ContactEntry.TICKET_USED + " number,"
            + TicketContract.ContactEntry.Date + " text,"
            + TicketContract.ContactEntry.TICKET_EVENT_TITLE + " text);";
    public static final String CREATE_TABLE_VOUCHER = "create table " + TicketContract.ContactEntry.TABLE_VOUCHER_NAME + "("
            + "id text primary key unique, "
            + TicketContract.ContactEntry.VOUCHER_DESCRIPTION+ " text,"
            + TicketContract.ContactEntry.VOUCHER_TYPE + " number);";

    public static final String CREATE_TABLE_CAFETERIA_ORDER = "create table " + TicketContract.ContactEntry.TABLE_ORDER + "(id number primary key unique, date text, price real);" ;

    public static final String CREATE_TABLE_Products_ORDER = "create table "+ TicketContract.ContactEntry.TABLE_PRODUCTS +" (amount number, type number, orderID number);";

    public static final String DROP_TABLE_TICKET = "drop table if exists " + TicketContract.ContactEntry.TABLE_TICKET_NAME+ ";";
    public static final String DROP_TABLE_VOUCHER = "drop table if exists " + TicketContract.ContactEntry.TABLE_VOUCHER_NAME+ ";";
    public static final String DROP_TABLE_CAFETERIA = "drop table if exists " + TicketContract.ContactEntry.TABLE_ORDER  + " ;";
    public static final String DROP_TABLE_PRODUCTS = "drop table if exists " + TicketContract.ContactEntry.TABLE_PRODUCTS  + ";";

    public TicketDbHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        Log.d("Database Operations", "Database created....");
    }


    public void AddTicket( SQLiteDatabase db, String ID, int Place, String eventTitle, String date, int used) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TicketContract.ContactEntry.TICKET_ID, ID);
        contentValues.put(TicketContract.ContactEntry.TICKET_PLACE, Place);
        contentValues.put(TicketContract.ContactEntry.TICKET_EVENT_TITLE, eventTitle);
        contentValues.put(TicketContract.ContactEntry.Date, date);
        contentValues.put(TicketContract.ContactEntry.TICKET_USED, used);
        try {
            db.insert(TicketContract.ContactEntry.TABLE_TICKET_NAME, null, contentValues);
            Log.e("DATABASE" , "TICKET ADDED");
        }catch (Exception e) {
            e.printStackTrace();
        }



    }

    public void AddVoucher(SQLiteDatabase db, String description, String ID, int type) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TicketContract.ContactEntry.TICKET_ID, ID);
        contentValues.put(TicketContract.ContactEntry.VOUCHER_DESCRIPTION, description);
        contentValues.put(TicketContract.ContactEntry.VOUCHER_TYPE, type);


        try {
            db.insert(TicketContract.ContactEntry.TABLE_VOUCHER_NAME, null, contentValues);
            Log.e("DATABASE" , "VOUCHER ADDED");
        }catch (Exception e) {
            e.printStackTrace();

        }

    }





    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        try {
            sqLiteDatabase.execSQL(CREATE_TABLE_VOUCHER);
        }catch (Exception e) {
            e.printStackTrace();
        }

        try {
            sqLiteDatabase.execSQL(CREATE_TABLE_TICKET);
        }catch (Exception e) {
            e.printStackTrace();
        }

        try {
            sqLiteDatabase.execSQL(CREATE_TABLE_CAFETERIA_ORDER);
        }catch (Exception e) {
            e.printStackTrace();
        }

        try {
            sqLiteDatabase.execSQL(CREATE_TABLE_Products_ORDER);
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_TABLE_TICKET);
        sqLiteDatabase.execSQL(DROP_TABLE_VOUCHER);
        sqLiteDatabase.execSQL(DROP_TABLE_CAFETERIA);
        sqLiteDatabase.execSQL(DROP_TABLE_PRODUCTS);
        onCreate(sqLiteDatabase);
    }


    public ArrayList<Ticket> readTickets(SQLiteDatabase db, int usedN) {

        ArrayList<Ticket> tickets = new ArrayList<>();


        String[] projections = {TicketContract.ContactEntry.TICKET_ID, TicketContract.ContactEntry.Date, TicketContract.ContactEntry.TICKET_PLACE, TicketContract.ContactEntry.TICKET_EVENT_TITLE, TicketContract.ContactEntry.TICKET_USED
        };

        Cursor cursor = db.query(TicketContract.ContactEntry.TABLE_TICKET_NAME, projections, null, null, null, null, TicketContract.ContactEntry.Date);

        String date, eventTitle, ID;
        int place, used;
        Log.e("GET TICKETS", "Size of tickets array:  " );
        if (cursor.moveToFirst()) {
            do {
                Log.e("GET TICKETS", "Size of tickets array:  " );
                date = cursor.getString(cursor.getColumnIndex(TicketContract.ContactEntry.Date) );
                ID = cursor.getString(0 );
                eventTitle = cursor.getString(cursor.getColumnIndex(TicketContract.ContactEntry.TICKET_EVENT_TITLE) );
                place = cursor.getInt(cursor.getColumnIndex(TicketContract.ContactEntry.TICKET_PLACE) );
                used = cursor.getInt(cursor.getColumnIndex(TicketContract.ContactEntry.TICKET_USED) );
                if(used == usedN)
                tickets.add(new Ticket(ID, place, eventTitle, date));
            } while (cursor.moveToNext());



        }



        return tickets;


    }

    public ArrayList<Product> readProducts(SQLiteDatabase db, int orderID) {
        ArrayList<Product> products = new ArrayList<>();
        String[] projections = {"type", "amount"};

        Cursor cursor = db.query(TicketContract.ContactEntry.TABLE_PRODUCTS, projections, "orderID = " + orderID, null, null, null, null);

        int type, amount;
        if (cursor.moveToFirst()) {
            do {
                Log.e("GET TICKETS", "Size of tickets array:  " );
                type = cursor.getInt(cursor.getColumnIndex("type"));
                amount = cursor.getInt(cursor.getColumnIndex("amount") );
                products.add(new Product(type, amount));
            } while (cursor.moveToNext());



        }



        return products;

    }


    public ArrayList<Order> readOrders(SQLiteDatabase db) {

        ArrayList<Order> orders = new ArrayList<>();


        String[] projections = {"id", "date", "price"};

        Cursor cursor = db.query(TicketContract.ContactEntry.TABLE_ORDER, projections, null, null, null, null, "date");

        String date;
        int  ID;
        double price;
        Log.e("GET TICKETS", "Size of tickets array:  " );
        if (cursor.moveToFirst()) {
            do {
                Log.e("GET TICKETS", "Size of tickets array:  " );
                date = cursor.getString(cursor.getColumnIndex("date") );
                ID = cursor.getInt(0 );
                price = cursor.getDouble(cursor.getColumnIndex("price") );
                orders.add(new Order(ID, price,  date));
            } while (cursor.moveToNext());



        }



        return orders;


    }



    public void addOrder(SQLiteDatabase db, int id, String date, String price) throws  Exception{
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", id);
        contentValues.put("date", date);
        contentValues.put("price", price);
        db.insert(TicketContract.ContactEntry.TABLE_ORDER, null, contentValues);

    }

    public void addProductOrder(SQLiteDatabase db, int id, int quantity, int productType) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("orderID", id);
        contentValues.put("type", productType);
        contentValues.put("amount", quantity);


        try {
            db.insert(TicketContract.ContactEntry.TABLE_PRODUCTS , null, contentValues);

        }catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void setTicketUsed(SQLiteDatabase db, String ticketID) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TicketContract.ContactEntry.TICKET_USED, 1);

        db.update(TicketContract.ContactEntry.TABLE_TICKET_NAME, contentValues, TicketContract.ContactEntry.TICKET_ID + " = " + ticketID, null);

    }

    public int countVouchers( SQLiteDatabase db, int i) {   //+ " where " + TicketContract.ContactEntry.VOUCHER_TYPE + " = " + i + ";"
        String countQuery = "SELECT  * FROM " + TicketContract.ContactEntry.TABLE_VOUCHER_NAME + " where " + TicketContract.ContactEntry.VOUCHER_TYPE + " = " + i + ";";

        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public String getVoucher(SQLiteDatabase db, int v1) {
        String cuponQuery = "SELECT  * FROM " + TicketContract.ContactEntry.TABLE_VOUCHER_NAME + " where " + TicketContract.ContactEntry.VOUCHER_TYPE + " = " + v1 + ";";
        Cursor cursor = db.rawQuery(cuponQuery, null);
        String voucherID = "";
        if (cursor.moveToFirst()) {
            voucherID = cursor.getString(cursor.getColumnIndex("id"));
        }


        if(!voucherID.equals(""))
        db.delete(TicketContract.ContactEntry.TABLE_VOUCHER_NAME, "id  = '" + voucherID + "'", null);

        return voucherID;
    }


}
