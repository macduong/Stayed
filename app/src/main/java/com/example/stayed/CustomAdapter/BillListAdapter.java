package com.example.stayed.CustomAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.databinding.DataBindingUtil;

import com.example.stayed.Model.RGS;
import com.example.stayed.R;
import com.example.stayed.databinding.BillListThemeBinding;

import java.util.ArrayList;
import java.util.List;

public class BillListAdapter extends BaseAdapter {
    private BillListThemeBinding binding;
    private List<RGS> rgsList = new ArrayList<>();

    public BillListAdapter(List<RGS> rgsList) {
        this.rgsList = rgsList;
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
        binding = DataBindingUtil.inflate(inflater, R.layout.bill_list_theme, parent, false);
        int amount = rgsList.get(position).getAmount();
        int price = rgsList.get(position).getPriceService();
        binding.confirmlistnameandpricetxt.setText(rgsList.get(position).getNameService().toString() + "\n" + price + " VND / " + rgsList.get(position).getUnitService());
        binding.confirmlistamounttxt.setText(String.valueOf(amount));
        binding.confirmlisttotalcosttxt.setText(String.valueOf(amount * price));
        return binding.getRoot();
    }
}
