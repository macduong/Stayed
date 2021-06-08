package com.example.stayed.CustomAdapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stayed.Model.Rooms;
import com.example.stayed.R;

import java.util.List;

public class RoomListAdapter extends BaseAdapter {
    private List<Rooms> roomsList;

    public RoomListAdapter(List<Rooms> roomsList) {
        this.roomsList = roomsList;
    }

    @Override
    public int getCount() {
        return roomsList.size();
    }

    @Override
    public Object getItem(int position) {
        return roomsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        convertView = inflater.inflate(R.layout.room_list_theme, parent, false);
        ImageView icon = convertView.findViewById(R.id.roomlisticon);
        TextView roomListNametxt = convertView.findViewById(R.id.roomlistnametxt);
        TextView roomListStatustxt = convertView.findViewById(R.id.roomliststatustxt);
        Rooms room = roomsList.get(position);
        if (room.getVip() == 1) {
            roomListNametxt.setText("Room " + String.valueOf(room.getId()) + " (VIP)");
            roomListNametxt.setTextColor(Color.parseColor("#388be9"));
            if (room.getAvailable() == 1) {
                icon.setImageResource(R.drawable.vip_room_available);
                roomListStatustxt.setText("Room available");
                roomListStatustxt.setTextColor(Color.GREEN);
            } else {
                roomListStatustxt.setText("Room rented");
                icon.setImageResource(R.drawable.vip_room_unavailable);
                roomListNametxt.setTextColor(Color.RED);
            }
        } else {
            roomListNametxt.setText("Room " + String.valueOf(room.getId()));
            roomListNametxt.setTextColor(Color.parseColor("#388be9"));
            if (room.getAvailable() == 1) {
                icon.setImageResource(R.drawable.room_available);
                roomListStatustxt.setText("Room available");
                roomListStatustxt.setTextColor(Color.GREEN);
            } else {
                roomListStatustxt.setText("Room rented");
                roomListStatustxt.setTextColor(Color.RED);
                icon.setImageResource(R.drawable.room_unavailable);
            }

        }
        return convertView;
    }
}
