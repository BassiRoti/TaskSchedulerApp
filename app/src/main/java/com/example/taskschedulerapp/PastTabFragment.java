package com.example.taskschedulerapp;

import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PastTabFragment extends Fragment {

    RecyclerView pastRecycler;
    View v;

    ScheduleDB db;
    TaskAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_past_tab, container, false);
        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        load(); // refresh tasks every time fragment becomes visible
    }

    private void load() {
        Cursor cursor = db.getPastTasksCursor();
        adapter.reload(cursor);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = new ScheduleDB(requireContext());
        db.open();

        pastRecycler = v.findViewById(R.id.pastRecycler);
        pastRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter = new TaskAdapter(requireContext(), db.getPastTasksCursor(), db);
        pastRecycler.setAdapter(adapter);
    }

}
