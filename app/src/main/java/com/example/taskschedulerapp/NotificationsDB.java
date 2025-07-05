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
    public static String ROW_TASK_ID = "_task_id";

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
            String query = "CREATE TABLE " + DATABASE_TABLE + " (" +
                    ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ROW_MESSAGE + " TEXT NOT NULL, " +
                    ROW_DATE + " TEXT NOT NULL, " +
                    ROW_TIME + " TEXT NOT NULL, " +
                    ROW_TASK_ID + " INTEGER, " +
                    "FOREIGN KEY(" + ROW_TASK_ID + ") REFERENCES ScheduleTable(_id)" +
                    ");";
            db.execSQL(query);
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

    public long addNotification(String message, String date, String time, int taskId) {
        ContentValues cv = new ContentValues();
        cv.put(ROW_MESSAGE, message);
        cv.put(ROW_DATE, date);
        cv.put(ROW_TIME, time);
        cv.put(ROW_TASK_ID, taskId);
        return db.insert(DATABASE_TABLE, null, cv);
    }

    public void insertNotification(String message, String date, String time) {
        ContentValues values = new ContentValues();
        values.put(ROW_MESSAGE, message);
        values.put(ROW_DATE, date);
        values.put(ROW_TIME, time);
        db.insert(DATABASE_TABLE, null, values);
    }


    public String getAllNotifications() {
        String[] columns = new String[]{ROW_ID, ROW_MESSAGE, ROW_DATE, ROW_TIME, ROW_TASK_ID};
        Cursor c = db.query(DATABASE_TABLE, columns, null, null, null, null, null);
        if (c != null && c.moveToFirst()) {
            StringBuilder result = new StringBuilder();

            int indexMessage = c.getColumnIndex(ROW_MESSAGE);
            int indexDate = c.getColumnIndex(ROW_DATE);
            int indexTime = c.getColumnIndex(ROW_TIME);

            do {
                String message = c.getString(indexMessage);
                String date = c.getString(indexDate);
                String time = c.getString(indexTime);

                result.append("Message: ").append(message).append("\n")
                        .append("Date: ").append(date).append(" | Time: ").append(time)
                        .append("\n----------------------------\n");
            } while (c.moveToNext());

            c.close();
            return result.toString();
        }

        return "No notifications";
    }




}
