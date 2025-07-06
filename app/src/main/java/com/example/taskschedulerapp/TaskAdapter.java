package com.example.taskschedulerapp;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private final Context context;
    private final ArrayList<Task> tasks;
    private final ScheduleDB db;

    public TaskAdapter(Context context, Cursor cursor, ScheduleDB db) {
        this.context = context;
        this.db = db;
        this.tasks = new ArrayList<>();
        loadFromCursor(cursor);
        Collections.sort(tasks, (t1, t2) -> t1.category.compareToIgnoreCase(t2.category));

    }

    private void loadFromCursor(Cursor c) {
        if (c != null && c.moveToFirst()) {
            int idIndex = c.getColumnIndex(ScheduleDB.ROW_ID);
            int titleIndex = c.getColumnIndex(ScheduleDB.ROW_TITLE);
            int descIndex = c.getColumnIndex(ScheduleDB.ROW_DESC);
            int dateIndex = c.getColumnIndex(ScheduleDB.ROW_DATE);
            int timeIndex = c.getColumnIndex(ScheduleDB.ROW_TIME);
            int statusIndex = c.getColumnIndex(ScheduleDB.ROW_STATUS);
            int catIndex = c.getColumnIndex(ScheduleDB.ROW_CATEGORY);

            do {
                Task t = new Task(
                        c.getInt(idIndex),
                        c.getString(titleIndex),
                        c.getString(descIndex),
                        c.getString(dateIndex),
                        c.getString(timeIndex),
                        c.getString(statusIndex),
                        c.getString(catIndex) != null ? c.getString(catIndex) : "Uncategorized"

                );
                tasks.add(t);
            } while (c.moveToNext());

            c.close();
        }
    }

    public void reload(Cursor newCursor) {
        tasks.clear();
        if (newCursor != null && newCursor.moveToFirst()) {
            int idIndex = newCursor.getColumnIndex(ScheduleDB.ROW_ID);
            int titleIndex = newCursor.getColumnIndex(ScheduleDB.ROW_TITLE);
            int descIndex = newCursor.getColumnIndex(ScheduleDB.ROW_DESC);
            int dateIndex = newCursor.getColumnIndex(ScheduleDB.ROW_DATE);
            int timeIndex = newCursor.getColumnIndex(ScheduleDB.ROW_TIME);
            int statusIndex = newCursor.getColumnIndex(ScheduleDB.ROW_STATUS);
            int catIndex = newCursor.getColumnIndex(ScheduleDB.ROW_CATEGORY);

            do {
                Task t = new Task(
                        newCursor.getInt(idIndex),
                        newCursor.getString(titleIndex),
                        newCursor.getString(descIndex),
                        newCursor.getString(dateIndex),
                        newCursor.getString(timeIndex),
                        newCursor.getString(statusIndex),
                        newCursor.getString(catIndex)
                );
                tasks.add(t);
            } while (newCursor.moveToNext());
        }
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        if (position == 0 || !task.category.equals(tasks.get(position - 1).category)) {
            holder.categoryHeader.setVisibility(View.VISIBLE);
            holder.categoryHeader.setText(task.category);
        } else {
            holder.categoryHeader.setVisibility(View.GONE);
        }
        holder.title.setText("Title: " + task.title);
        holder.desc.setText("Description: " + task.desc);
        holder.datetime.setText("Date: " + task.date + " | Time: " + task.time);
        holder.status.setText("Status: " + task.status);
        holder.category.setText("Category: " + task.category);

        holder.btnDelete.setOnClickListener(v -> {
            db.deleteTask(task.id);
            reload(db.getPastTasksCursor()); // refresh RecyclerView
            Toast.makeText(context, "Task deleted", Toast.LENGTH_SHORT).show();
        });

        holder.btnMarkDone.setOnClickListener(v -> {
            db.markAsCompleted(task.id);
            reload(db.getPastTasksCursor()); // refresh RecyclerView
            Toast.makeText(context, "Marked as completed", Toast.LENGTH_SHORT).show();
        });


        holder.btnUpdate.setOnClickListener(v -> {
            Task taskToEdit = tasks.get(position);

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Update Task");
            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(20, 20, 20, 20);

            EditText etTitle = new EditText(context);
            etTitle.setHint("Title");
            etTitle.setText(taskToEdit.title);
            layout.addView(etTitle);

            EditText etDesc = new EditText(context);
            etDesc.setHint("Description");
            etDesc.setText(taskToEdit.desc);
            layout.addView(etDesc);

            EditText etDate = new EditText(context);
            etDate.setHint("Date (yyyy-MM-dd)");
            etDate.setText(taskToEdit.date);
            layout.addView(etDate);

            EditText etTime = new EditText(context);
            etTime.setHint("Time (HH:mm)");
            etTime.setText(taskToEdit.time);
            layout.addView(etTime);

            EditText etStatus = new EditText(context);
            etStatus.setHint("Status");
            etStatus.setText(taskToEdit.status);
            layout.addView(etStatus);

            EditText etCategory = new EditText(context);
            etCategory.setHint("Category");
            etCategory.setText(taskToEdit.category);
            layout.addView(etCategory);

            builder.setView(layout);

            builder.setPositiveButton("Update", (dialog, which) -> {
                String newTitle = etTitle.getText().toString();
                String newDesc = etDesc.getText().toString();
                String newDate = etDate.getText().toString();
                String newTime = etTime.getText().toString();
                String newStatus = etStatus.getText().toString();
                String newCategory = etCategory.getText().toString();

                db.updateTask(taskToEdit.id, newTitle, newDesc, newDate, newTime, newStatus, newCategory);

                taskToEdit.title = newTitle;
                taskToEdit.desc = newDesc;
                taskToEdit.date = newDate;
                taskToEdit.time = newTime;
                taskToEdit.status = newStatus;
                taskToEdit.category = newCategory;

                notifyItemChanged(position);
                Toast.makeText(context, "Task updated", Toast.LENGTH_SHORT).show();
            });

            builder.setNegativeButton("Cancel", null);
            builder.show();
        });

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {

        TextView title, desc, datetime, status, category;
        Button btnDelete, btnUpdate, btnMarkDone;
        TextView categoryHeader;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryHeader = itemView.findViewById(R.id.itemCategoryHeader);
            title = itemView.findViewById(R.id.itemTitle);
            desc = itemView.findViewById(R.id.itemDesc);
            datetime = itemView.findViewById(R.id.itemDateTime);
            status = itemView.findViewById(R.id.itemStatus);
            category = itemView.findViewById(R.id.itemCategory);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            btnMarkDone = itemView.findViewById(R.id.btnMarkDone);
        }
    }
}
