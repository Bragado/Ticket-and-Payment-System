package cmov.miguellucas.com.customerapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import cmov.miguellucas.com.customerapp.Models.Ticket;

public class TicketDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ticket_db1";
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
    public static final String CREATE_TABLE_CAFETERIA = "";

    public static final String DROP_TABLE_TICKET = "drop table if exists " + TicketContract.ContactEntry.TABLE_TICKET_NAME+ ";";
    public static final String DROP_TABLE_VOUCHER = "drop table if exists " + TicketContract.ContactEntry.TABLE_VOUCHER_NAME+ ";";
    public static final String DROP_TABLE_CAFETERIA = "";

    public TicketDbHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        Log.d("Database Operations", "Database created....");
    }


    public void AddTicket( SQLiteDatabase db, String ID, int Place, String eventTitle, String date) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TicketContract.ContactEntry.TICKET_ID, ID);
        contentValues.put(TicketContract.ContactEntry.TICKET_PLACE, Place);
        contentValues.put(TicketContract.ContactEntry.TICKET_EVENT_TITLE, eventTitle);
        contentValues.put(TicketContract.ContactEntry.Date, date);
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

        }

        try {
            sqLiteDatabase.execSQL(CREATE_TABLE_TICKET);
        }catch (Exception e) {

        }

        try {
            sqLiteDatabase.execSQL(CREATE_TABLE_CAFETERIA);
        }catch (Exception e) {

        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_TABLE_TICKET);
        sqLiteDatabase.execSQL(DROP_TABLE_VOUCHER);
        sqLiteDatabase.execSQL(DROP_TABLE_CAFETERIA);
        onCreate(sqLiteDatabase);
    }


    public ArrayList<Ticket> readTickets(SQLiteDatabase db) {

        ArrayList<Ticket> tickets = new ArrayList<>();


        String[] projections = {TicketContract.ContactEntry.TICKET_ID, TicketContract.ContactEntry.Date, TicketContract.ContactEntry.TICKET_PLACE, TicketContract.ContactEntry.TICKET_EVENT_TITLE
        };

        Cursor cursor = db.query(TicketContract.ContactEntry.TABLE_TICKET_NAME, projections, null, null, null, null, TicketContract.ContactEntry.Date);

        String date, eventTitle, ID;
        int place;
        Log.e("GET TICKETS", "Size of tickets array:  " );
        if (cursor.moveToFirst()) {
            do {
                Log.e("GET TICKETS", "Size of tickets array:  " );
                date = cursor.getString(cursor.getColumnIndex(TicketContract.ContactEntry.Date) );
                ID = cursor.getString(0 );
                eventTitle = cursor.getString(cursor.getColumnIndex(TicketContract.ContactEntry.TICKET_EVENT_TITLE) );
                place = cursor.getInt(cursor.getColumnIndex(TicketContract.ContactEntry.TICKET_PLACE) );
                tickets.add(new Ticket(ID, place, eventTitle, date));
            } while (cursor.moveToNext());



        }



        return tickets;


    }


}
