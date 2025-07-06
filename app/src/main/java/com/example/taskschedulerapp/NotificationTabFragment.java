package com.example.taskschedulerapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NotificationTabFragment extends Fragment {

    TextView tvnoti;
    View v;
    NotificationsDB db;

    private void init() {
        tvnoti = v.findViewById(R.id.notiid);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_notification_tab, container, false);
        return v;
    }

    private void load() {
        if (db != null) {
            tvnoti.setText(db.getAllNotifications());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        load();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();

        db = new NotificationsDB(requireContext());
        db.open();

        load();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (db != null) db.close();
    }
}
