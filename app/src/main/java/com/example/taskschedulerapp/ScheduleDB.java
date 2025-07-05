package com.example.taskschedulerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.DatePicker;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ScheduleDB  {

    private final String DATABASE_NAME="ScheduleDB";
    private final String DATABASE_TABLE="ScheduleTable";
    private final int DATABASE_VERSION=1;
    public static String ROW_ID="_id";
    public static String ROW_TITLE="_title";
    public static String ROW_DESC="_desc";
    public static String ROW_DATE="_date";
    public static String ROW_TIME="_time";
    public static String ROW_STATUS="_status";
    public static String ROW_CATEGORY = "_category";


    public Context context;

    public ScheduleDB(Context c){
        context=c;
    }

    public class ScheduleHelper extends SQLiteOpenHelper{

        public ScheduleHelper(@Nullable Context context) {
            super(context, DATABASE_NAME,null, DATABASE_VERSION,null);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE " + DATABASE_TABLE + " (" +
                    ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ROW_TITLE + " TEXT NOT NULL, " +
                    ROW_DESC + " TEXT NOT NULL, " +
                    ROW_DATE + " TEXT NOT NULL, " +
                    ROW_TIME + " TEXT NOT NULL, " +
                    ROW_STATUS + " TEXT NOT NULL, " +
                    ROW_CATEGORY + " TEXT NOT NULL);";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("ALTER TABLE " + DATABASE_TABLE + " ADD COLUMN " + ROW_CATEGORY + " TEXT");
        }
    }

    ScheduleHelper scheduleHelper;
    SQLiteDatabase db;

    public void open(){
        scheduleHelper=new ScheduleHelper(context);
        db=scheduleHelper.getWritableDatabase();
    }

    public void close(){
        scheduleHelper.close();
    }

    public long addschedule(String title, String desc, String date, String time, String status ){
        ContentValues cv=new ContentValues();
        cv.put(ROW_TITLE,title);
        cv.put(ROW_DESC,desc);
        cv.put(ROW_DATE,date);
        cv.put(ROW_TIME,time);
        cv.put(ROW_STATUS,status);
        return db.insert(DATABASE_TABLE,null,cv);
    }
    public long addschedule(String title, String desc, String date, String time, String status, String category){
        ContentValues cv=new ContentValues();
        cv.put(ROW_TITLE,title);
        cv.put(ROW_DESC,desc);
        cv.put(ROW_DATE,date);
        cv.put(ROW_TIME,time);
        cv.put(ROW_STATUS,status);
        cv.put(ROW_CATEGORY, category);
        return db.insert(DATABASE_TABLE,null,cv);
    }

    public Cursor getFilteredTasks(String category, String beforeDate) {
        String selection = "";
        ArrayList<String> argsList = new ArrayList<>();

        if (!category.equalsIgnoreCase("All")) {
            selection += ROW_CATEGORY + " = ?";
            argsList.add(category);
        }

        if (!beforeDate.isEmpty()) {
            if (!selection.isEmpty()) selection += " AND ";
            selection += ROW_DATE + " <= ?";
            argsList.add(beforeDate);
        }

        String[] selectionArgs = argsList.toArray(new String[0]);
        return db.query(DATABASE_TABLE, null, selection, selectionArgs, null, null, ROW_DATE + " ASC");
    }


    public String pasttasks() {
        String[] columns = new String[]{ROW_ID, ROW_TITLE, ROW_DESC, ROW_DATE, ROW_TIME, ROW_STATUS};
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        String currentDateTime = dateFormat.format(new Date());

        // Corrected column reference for SQLite query
        String selection = "datetime(" + ROW_DATE + " || ' ' || " + ROW_TIME + ") < datetime(?)";
        String[] selectionArgs = new String[]{currentDateTime};

        Cursor c = db.query(DATABASE_TABLE, columns, selection, selectionArgs, null, null, null);
        if (c != null && c.moveToFirst()) {
            StringBuilder result = new StringBuilder();

            int indexTitle = c.getColumnIndex(ROW_TITLE);
            int indexDesc = c.getColumnIndex(ROW_DESC);
            int indexDate = c.getColumnIndex(ROW_DATE);
            int indexTime = c.getColumnIndex(ROW_TIME);
            int indexStatus = c.getColumnIndex(ROW_STATUS);

            do {
                String title = c.getString(indexTitle);
                String desc = c.getString(indexDesc);
                String date = c.getString(indexDate);
                String time = c.getString(indexTime);
                String status = c.getString(indexStatus);

                result.append("\n")
                        .append("Title: ").append(title).append("\n")
                        .append("Description: ").append(desc).append("\n")
                        .append("Date: ").append(date).append("\n")
                        .append("Time: ").append(time).append("\n")
                        .append("Status: ").append(status)
                        .append("\n----------------------------\n");
            } while (c.moveToNext());

            c.close();
            return result.toString();
        }

        return "No data found";
    }

//    public void insertSampleData(SQLiteDatabase db) {
//        ContentValues values = new ContentValues();
//
//        values.put(ROW_ID, 1);
//        values.put(ROW_TITLE, "Task 1");
//        values.put(ROW_DESC, "Description 1");
//        values.put(ROW_DATE, "2025-04-28");
//        values.put(ROW_TIME, "10:00");
//        values.put(ROW_STATUS, "Pending");
//        db.insert(DATABASE_TABLE, null, values);
//
//        values.put(ROW_ID, 2);
//        values.put(ROW_TITLE, "Task 2");
//        values.put(ROW_DESC, "Description 2");
//        values.put(ROW_DATE, "2025-05-01");
//        values.put(ROW_TIME, "09:00");
//        values.put(ROW_STATUS, "Completed");
//        db.insert(DATABASE_TABLE, null, values);
//
//        values.put(ROW_ID, 3);
//        values.put(ROW_TITLE, "Task 3");
//        values.put(ROW_DESC, "Description 3");
//        values.put(ROW_DATE, "2025-04-30");
//        values.put(ROW_TIME, "15:30");
//        values.put(ROW_STATUS, "In Progress");
//        db.insert(DATABASE_TABLE, null, values);
//    }

    public int updateTask(int id, String title, String desc, String date, String time, String status, String category) {
        ContentValues cv = new ContentValues();
        cv.put(ROW_TITLE, title);
        cv.put(ROW_DESC, desc);
        cv.put(ROW_DATE, date);
        cv.put(ROW_TIME, time);
        cv.put(ROW_STATUS, status);
        cv.put(ROW_CATEGORY, category);
        return db.update(DATABASE_TABLE, cv, ROW_ID + "=?", new String[]{String.valueOf(id)});
    }

    public void deleteTask(int id) {
        db.delete(DATABASE_TABLE, ROW_ID + "=?", new String[]{String.valueOf(id)});
    }

    public void markAsCompleted(int id) {
        ContentValues cv = new ContentValues();
        cv.put(ROW_STATUS, "Completed");
        db.update(DATABASE_TABLE, cv, ROW_ID + "=?", new String[]{String.valueOf(id)});
    }


    public Cursor getPastTasksCursor() {
        return db.query(DATABASE_TABLE, null, null, null, null, null, ROW_DATE + " DESC");
    }







}
