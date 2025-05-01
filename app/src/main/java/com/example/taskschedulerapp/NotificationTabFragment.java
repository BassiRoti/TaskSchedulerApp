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
 * Use the {@link NotificationTabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationTabFragment extends Fragment {

    TextView tvnoti;
    View v;
    private void init(){
        tvnoti=v.findViewById(R.id.notiid);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_notification_tab, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        NotificationsDB db=new NotificationsDB(requireContext());
        db.open();
        tvnoti.setText(db.getallnotifications());
        db.close();
    }
}