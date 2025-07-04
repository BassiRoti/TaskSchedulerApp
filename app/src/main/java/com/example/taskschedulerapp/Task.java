package com.example.taskschedulerapp;

public class Task {
    int id;
    String title, desc, date, time, status, category;

    public Task(int id, String title, String desc, String date, String time, String status, String category) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.date = date;
        this.time = time;
        this.status = status;
        this.category = category;
    }
}