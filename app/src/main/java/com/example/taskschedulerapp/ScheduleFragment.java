package com.example.taskschedulerapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScheduleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScheduleFragment extends Fragment {

    EditText etname,etdesc,etdate,etttime,etstatus,taskcat;
    Button btn;

    View v;

    private void init(){
        etname=v.findViewById(R.id.taskTitle);
        etdesc=v.findViewById(R.id.taskDesc);
        etdate=v.findViewById(R.id.taskDate);
        etttime=v.findViewById(R.id.taskTime);
        etstatus=v.findViewById(R.id.taskStatus);
        taskcat=v.findViewById(R.id.taskcat);
        btn=v.findViewById(R.id.saveButton);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_schedule, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        etdate.setOnClickListener(v2 -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog dp = new DatePickerDialog(
                    getContext(),
                    (view1, year, month, dayOfMonth) -> {
                        String date = year + "-" + (month + 1) + "-" + dayOfMonth;
                        etdate.setText(date);
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            dp.show();
        });

        etttime.setOnClickListener(v3 -> {
            Calendar calendar = Calendar.getInstance();
            TimePickerDialog tp = new TimePickerDialog(
                    getContext(),
                    (view1, hourOfDay, minute) -> {
                        String time = String.format("%02d:%02d", hourOfDay, minute);
                        etttime.setText(time);
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
            );
            tp.show();
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recname = etname.getText().toString().trim();
                String recdesc = etdesc.getText().toString().trim();
                String recdate = etdate.getText().toString().trim();
                String rectime = etttime.getText().toString().trim();
                String recstatus = etstatus.getText().toString().trim();
                String cat = taskcat.getText().toString().trim();

                if (recname.isEmpty() || recdesc.isEmpty() || recdate.isEmpty() ||
                        rectime.isEmpty() || recstatus.isEmpty() || cat.isEmpty()) {
                    Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                ScheduleDB db = new ScheduleDB(requireContext());
                db.open();
                long taskId=db.addschedule(recname, recdesc, recdate, rectime, recstatus, cat);
                db.close();

                Toast.makeText(requireContext(), "Saved successfully", Toast.LENGTH_SHORT).show();

                // Optionally, clear inputs after saving
                etname.setText("");
                etdesc.setText("");
                etdate.setText("");
                etttime.setText("");
                etstatus.setText("");
                taskcat.setText("");

                NotificationsDB notificationsDB = new NotificationsDB(requireContext());
                notificationsDB.open();
                notificationsDB.addNotification("Upcoming: " + recname, recdate, rectime, (int) taskId);
                notificationsDB.close();

                Toast.makeText(requireContext(), "Saved successfully", Toast.LENGTH_SHORT).show();
                db.close();
            }
        });

    }
}