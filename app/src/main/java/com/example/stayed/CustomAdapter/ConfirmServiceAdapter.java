package com.example.stayed.CustomAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;

import com.example.stayed.Model.RGS;
import com.example.stayed.Model.Services;
import com.example.stayed.R;
import com.example.stayed.databinding.ConfirmListThemeBinding;

import java.util.ArrayList;
import java.util.List;

public class ConfirmServiceAdapter extends BaseAdapter {
    private List<RGS> rgsList = new ArrayList<>();
    private ConfirmListThemeBinding binding;
    private ConfirmServiceInterface anInterface;

    public ConfirmServiceAdapter(List<RGS> rgsList) {
        this.rgsList = rgsList;
    }

    public void setAnInterface(ConfirmServiceInterface anInterface) {
        this.anInterface = anInterface;
    }

    @Override
    public int getCount() {
        return rgsList.size();
    }

    @Override
    public Object getItem(int position) {
        return rgsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = DataBindingUtil.inflate(inflater, R.layout.confirm_list_theme, parent, false);
        int amount = rgsList.get(position).getAmount();
        int price = rgsList.get(position).getPriceService();
        binding.confirmlistnameandpricetxt.setText(rgsList.get(position).getNameService() + "\n" + String.valueOf(price) + " VND / "+rgsList.get(position).getUnitService());
        binding.confirmlistamounttxt.setText(String.valueOf(amount));
        binding.confirmlisttotalcosttxt.setText(String.valueOf(price * amount));

        binding.confirmlistremovebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anInterface.onclickRemove(rgsList.get(position).getId());
            }
        });
        binding.confirmlistbehindlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anInterface.onclickEdit(rgsList.get(position).getId());
            }
        });
        return binding.getRoot();
    }
}
