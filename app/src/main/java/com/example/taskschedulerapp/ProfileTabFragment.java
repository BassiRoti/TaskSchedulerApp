package com.example.taskschedulerapp;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatDelegate;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileTabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileTabFragment extends Fragment {

EditText name, email;
Switch sw;
View v;
LinearLayout ly;
Button editbtn;
boolean isEditMode=false;

SharedPreferences sp;
private void init(){
    name=v.findViewById(R.id.displayName);
    email=v.findViewById(R.id.displayEmail);
    sw=v.findViewById(R.id.themeToggle);
    editbtn=v.findViewById(R.id.editButton);
    sp= requireContext().getSharedPreferences("pp",MODE_PRIVATE);
    ly=v.findViewById(R.id.ly);
}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment_profile_tab, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();


        String sname = sp.getString("pname", "Default Name");
        String semail = sp.getString("pemail", "Default Email");
//        boolean isDarkMode = sp.getBoolean("ptheme", false);

        name.setText(sname);
        email.setText(semail);
//        sw.setChecked(isDarkMode);
        name.setEnabled(false);
        email.setEnabled(false);
//        sw.setChecked(isDarkMode);

//        if (isDarkMode) {
//            ly.setBackgroundColor(0xFF121212);
//            name.setBackgroundColor(0xFF333333);
//            email.setBackgroundColor(0xFF333333);
//            name.setTextColor(0xFFFFFFFF);
//            email.setTextColor(0xFFFFFFFF);
//            sw.setTextColor(0xFFFFFFFF);
//            editbtn.setBackgroundColor(0xFF6A1B9A);
//        } else {
//            ly.setBackgroundColor(0xFFFFFFFF);
//            name.setBackgroundColor(0xFFFFFFFF);
//            email.setBackgroundColor(0xFFFFFFFF);
//            name.setTextColor(0xFF000000);
//            email.setTextColor(0xFF000000);
//            sw.setTextColor(0xFF000000);
//            editbtn.setBackgroundColor(0xFF6A1B9A);
//        }

        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isEditMode){
                    name.setEnabled(true);
                    email.setEnabled(true);
                    editbtn.setText("Save");
                    isEditMode = true;
                }
                else{
                    String newName = name.getText().toString();
                    String newEmail = email.getText().toString();

                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("pname", newName);
                    editor.putString("pemail", newEmail);
                    editor.apply();

                    name.setEnabled(false);
                    email.setEnabled(false);
                    editbtn.setText("Edit");
                    isEditMode = false;
                }
            }
        });

        sw.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("ptheme", isChecked);
            editor.apply();

            AppCompatDelegate.setDefaultNightMode(isChecked ?
                    AppCompatDelegate.MODE_NIGHT_YES :
                    AppCompatDelegate.MODE_NIGHT_NO);

            requireActivity().recreate();
        });


    }
}