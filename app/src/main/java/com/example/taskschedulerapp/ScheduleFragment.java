package com.example.taskschedulerapp;

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
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recname=etname.getText().toString().trim();
                String recdesc=etdesc.getText().toString().trim();
                String recdate=etdate.getText().toString().trim();
                String rectime=etttime.getText().toString().trim();
                String recstatus=etstatus.getText().toString().trim();
                String cat=taskcat.getText().toString().trim();

                ScheduleDB db=new ScheduleDB(requireContext());
                db.open();
                db.addschedule(recname,recdesc,recdate,rectime,recstatus,cat);
                Toast.makeText(requireContext(), "Saved successfully", Toast.LENGTH_SHORT).show();
                db.close();
            }
        });
    }
}