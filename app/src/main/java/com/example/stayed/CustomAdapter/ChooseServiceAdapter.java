package com.example.stayed.CustomAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.example.stayed.Model.Services;
import com.example.stayed.R;
import com.example.stayed.databinding.UseListThemeBinding;

import java.util.ArrayList;
import java.util.List;

public class ChooseServiceAdapter extends BaseAdapter {
    private List<Services> servicesList = new ArrayList<>();
    private UseListThemeBinding binding;
    private ChooseServiceInterface anInterface;

    public ChooseServiceAdapter(List<Services> servicesList) {
        this.servicesList = servicesList;
    }

    public void setAnInterface(ChooseServiceInterface anInterface) {
        this.anInterface = anInterface;
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
        binding = DataBindingUtil.inflate(inflater, R.layout.use_list_theme, parent, false);
        binding.uselistnametxt.setText(servicesList.get(position).getName());
        binding.uselistpricetxt.setText(String.valueOf(servicesList.get(position).getPrice()) + "\nVND");
        binding.uselistunittxt.setText(servicesList.get(position).getUnit());
        binding.uselisteditbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anInterface.onclickEdit(servicesList.get(position).getId());

            }
        });
        binding.uselistbehindlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anInterface.onclickChoose(servicesList.get(position).getId());
            }
        });
        return binding.getRoot();
    }
}
