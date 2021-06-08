package com.example.stayed.CustomAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;

import com.example.stayed.Model.RGS;
import com.example.stayed.Model.RentGuests;
import com.example.stayed.R;
import com.example.stayed.databinding.RecentListThemeBinding;
import com.sun.mail.imap.Utility;

import java.util.ArrayList;
import java.util.List;

public class RecentListAdapter extends BaseAdapter {
    private List<RentGuests> guestsList = new ArrayList<>();
    private RecentListThemeBinding binding;

    public RecentListAdapter(List<RentGuests> guestsList) {
        this.guestsList = guestsList;
    }

    @Override
    public int getCount() {
        return guestsList.size();
    }

    @Override
    public Object getItem(int position) {
        return guestsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        binding = DataBindingUtil.inflate(inflater, R.layout.recent_list_theme, parent, false);
        binding.recentlistnametxt.setText(guestsList.get(position).getName());
        binding.recentlistroom.setText(  "Room " + guestsList.get(position).getRoomId());
        binding.recentlistdatestxt.setText(guestsList.get(position).getTimeCheckin() + "\n" + guestsList.get(position).getTimeCheckout());


        return binding.getRoot();
    }
}
