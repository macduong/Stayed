package com.example.stayed.CustomAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.stayed.Fragments.FragmentRecent;
import com.example.stayed.Fragments.FragmentRooms;

public class RoomViewPagerAdapter extends FragmentStatePagerAdapter {
    private int page = 2;

    public RoomViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentRooms();
            case 1:
                return new FragmentRecent();
            default:
                return new FragmentRooms();
        }
    }

    @Override
    public int getCount() {
        return page;
    }
}
