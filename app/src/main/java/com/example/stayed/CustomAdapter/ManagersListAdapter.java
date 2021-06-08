package com.example.stayed.CustomAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;

import com.example.stayed.Model.Managers;
import com.example.stayed.R;
import com.example.stayed.databinding.ManagersListThemeBinding;

import java.util.ArrayList;
import java.util.List;

public class ManagersListAdapter extends BaseAdapter {
    private ServicesListInterface anInterface;
    private ManagersListThemeBinding binding;
    private List<Managers> managersList = new ArrayList<>();

    public void setAnInterface(ServicesListInterface anInterface) {
        this.anInterface = anInterface;
    }

    public ManagersListAdapter(List<Managers> managersList) {
        this.managersList = managersList;
    }

    @Override
    public int getCount() {
        return managersList.size();
    }

    @Override
    public Object getItem(int position) {
        return managersList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = DataBindingUtil.inflate(inflater, R.layout.managers_list_theme,parent,false);
        binding.manageridtxt.setText(String.valueOf(managersList.get(position).getId()));
        binding.managernametxt.setText(managersList.get(position).getUserName()+"\n"+managersList.get(position).getFullName());

        if (managersList.get(position).getIsboss()==1){
            binding.managerisbosstxt.setText("Boss");
            binding.servicelistdeletebtn.setVisibility(View.GONE);
        }else{
            binding.managerisbosstxt.setText("Employee");
        }
        binding.servicelistdeletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anInterface.onclickRemove(managersList.get(position).getId());
            }
        });
        binding.servicelistbehindlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anInterface.onclickEdit(managersList.get(position).getId());
            }
        });
        return binding.getRoot();
    }
}
