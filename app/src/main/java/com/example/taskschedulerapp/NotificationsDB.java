package com.example.taskschedulerapp;

import static org.jetbrains.annotations.Nls.Capitalization.Title;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class NotificationsDB {
    private final String DATABASE_NAME="NotificationsDB";
    private final String DATABASE_TABLE="NotificationsTable";
    private final int DATABASE_VERSION=1;
    public static String ROW_ID="_id";
    public static String ROW_MESSAGE="_message";
    public static String ROW_DATE="_date";
    public static String ROW_TIME="_time";

    public Context context;

    public NotificationsDB(Context c){
        context=c;
    }

    public class NotificationsHelper extends SQLiteOpenHelper{

        public NotificationsHelper(@Nullable Context context) {
            super(context,DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String query="CREATE TABLE "+DATABASE_TABLE+" ("+
                    ROW_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ROW_MESSAGE+" TEXT NOT NULL, "
                    +ROW_DATE+" TEXT NOT NULL, "+ROW_TIME+" TEXT NOT NULL " +");";

            db.execSQL(query);
            insertHardcodedData(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
            onCreate(db);
        }
    }

    NotificationsHelper notihelper;
    SQLiteDatabase db;

    public void open(){
        notihelper=new NotificationsHelper(context);
        db=notihelper.getWritableDatabase();
    }

    public void close(){
        notihelper.close();
    }

    private void insertHardcodedData(SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        values.put(ROW_MESSAGE, "Meeting with Team");
        values.put(ROW_DATE, "2025-05-01");
        values.put(ROW_TIME, "10:00 AM");
        db.insert(DATABASE_TABLE, null, values);

        values.put(ROW_MESSAGE, "Submit Project Report");
        values.put(ROW_DATE, "2025-05-02");
        values.put(ROW_TIME, "03:00 PM");
        db.insert(DATABASE_TABLE, null, values);

        values.put(ROW_MESSAGE, "Call with Client");
        values.put(ROW_DATE, "2025-05-03");
        values.put(ROW_TIME, "11:30 AM");
        db.insert(DATABASE_TABLE, null, values);
    }

    public String getallnotifications(){
        String[] columns = new String[]{ROW_ID, ROW_MESSAGE, ROW_DATE, ROW_TIME};
        Cursor c = db.query(DATABASE_TABLE, columns, null, null, null, null, null);
        if (c != null && c.moveToFirst()) {
            StringBuilder result = new StringBuilder();

            int indexID = c.getColumnIndex(ROW_ID);
            int indexMessage = c.getColumnIndex(ROW_MESSAGE);
            int indexDate = c.getColumnIndex(ROW_DATE);
            int indexTime = c.getColumnIndex(ROW_TIME);

            do {
                int id = c.getInt(indexID);
                String message = c.getString(indexMessage);
                String date = c.getString(indexDate);
                double time = c.getDouble(indexTime);

                result.append("\n")
                        .append("Message: ").append(message).append("\n")
                        .append("Date: ").append(date).append("\n")
                        .append("Time: ").append(time).append("\n")
                        .append("\n----------------------------\n");
            } while (c.moveToNext());

            c.close();
            return result.toString();
        }

        return "No data found";
}



}
