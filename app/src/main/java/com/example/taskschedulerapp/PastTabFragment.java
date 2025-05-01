package com.example.taskschedulerapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PastTabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PastTabFragment extends Fragment {


    TextView pastactivities;
    View v;
    private void init(){
       pastactivities=v.findViewById(R.id.pastactivities);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         v=inflater.inflate(R.layout.fragment_past_tab, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        ScheduleDB db=new ScheduleDB(requireContext());
        db.open();
        pastactivities.setText(db.pasttasks());
        db.close();
    }
}