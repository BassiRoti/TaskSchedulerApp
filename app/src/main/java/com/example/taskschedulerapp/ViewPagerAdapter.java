package com.example.taskschedulerapp;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {


    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:return new ScheduleFragment();
            case 1:return new PastTabFragment();
            case 2: return new NotificationTabFragment();
            case 3: return new ProfileTabFragment();
            default:return new ProfileTabFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
