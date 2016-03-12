package com.example.phuon.googlemapsearchdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by phuon on 3/10/2016.
 */
public class DataContext extends SQLiteOpenHelper {

    public DataContext(Context context) {
        super(context, "MadDiscovery", null, 2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Events");
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Events(ID Integer Primary Key, Name text, Date text, Time text, " +
                "Organizer text, Lat text, Lng text);");
    }

    public boolean insertEvent (Event event)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", event.getName());
        contentValues.put("Date", event.getDate());
        contentValues.put("Time", event.getTime());
        contentValues.put("Organizer", event.getOrganizer());
        contentValues.put("Lat", event.getLat());
        contentValues.put("Lng", event.getLng());
        db.insert("Events", null, contentValues);
        return true;
    }

    public boolean clearData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Events", null, null) > 0;
    }

    public ArrayList<Event> getAllEvents()
    {
        ArrayList<Event> listEvent = new ArrayList<Event>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM Events", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            String name = res.getString(res.getColumnIndex("Name"));
            String date = res.getString(res.getColumnIndex("Date"));
            String time = res.getString(res.getColumnIndex("Time"));
            String organizer = res.getString(res.getColumnIndex("Organizer"));
            String lat = res.getString(res.getColumnIndex("Lat"));
            String lng = res.getString(res.getColumnIndex("Lng"));
            listEvent.add(new Event(name, date, time, organizer, lat, lng));
            res.moveToNext();
        }
        return listEvent;
    }

}
