package com.example.stayed.CustomAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;

import com.example.stayed.Model.Services;
import com.example.stayed.R;
import com.example.stayed.databinding.ServiceListThemeBinding;

import java.util.List;

public class ServicesListAdapter extends BaseAdapter {
    private List<Services> servicesList;
    private ServiceListThemeBinding binding;
    private ServicesListInterface anInterface;

    public void setAnInterface(ServicesListInterface anInterface) {
        this.anInterface = anInterface;
    }

    public ServicesListAdapter(List<Services> servicesList) {
        this.servicesList = servicesList;
    }

    @Override
    public int getCount() {
        return servicesList.size();
    }

    @Override
    public Object getItem(int position) {
        return servicesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = DataBindingUtil.inflate(inflater, R.layout.service_list_theme, parent, false);
        binding.servicelistnametxt.setText(servicesList.get(position).getName());
        binding.servicelistpricetxt.setText(String.valueOf(servicesList.get(position).getPrice())+"\nVND");
        binding.servicelistunittxt.setText(servicesList.get(position).getUnit());
        binding.servicelistbehindlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anInterface.onclickEdit(servicesList.get(position).getId());
            }
        });
        binding.servicelistdeletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anInterface.onclickRemove(servicesList.get(position).getId());
            }
        });
        return binding.getRoot();
    }
}
