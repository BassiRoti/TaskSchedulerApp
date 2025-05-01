package com.example.taskschedulerapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {
    TabLayout tabid;
    ViewPager2 vpid;
    ViewPagerAdapter adapter;
    private void init(){
        tabid=findViewById(R.id.tabid);
        vpid=findViewById(R.id.viewpagerid);
        adapter=new ViewPagerAdapter(this);
        vpid.setAdapter(adapter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();

        new TabLayoutMediator(tabid, vpid, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setIcon(R.drawable.calenderasset);
                        break;
                    case 1: tab.setIcon(R.drawable.timeasset);
                    break;
                    case 2: tab.setIcon(R.drawable.notificationasset);
                    break;
                    case 3: tab.setIcon(R.drawable.profileasset);
                    break;
                    default:tab.setIcon(R.drawable.profileasset);
                    break;

                }
            }
        }).attach();

    }
}