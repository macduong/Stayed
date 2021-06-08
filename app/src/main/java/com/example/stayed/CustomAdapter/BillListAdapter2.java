package com.example.stayed.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stayed.Model.RGS;
import com.example.stayed.R;
import com.example.stayed.databinding.BillListThemeBinding;

import java.util.ArrayList;
import java.util.List;

public class BillListAdapter2 extends RecyclerView.Adapter<BillListAdapter2.ViewHolder> {
    private List<RGS> rgsList = new ArrayList<>();
    private Context context;
    private ChooseServiceInterface anInterface;

    public BillListAdapter2(List<RGS> rgsList, Context context) {
        this.rgsList = rgsList;
        this.context = context;
    }

    public void setAnInterface(ChooseServiceInterface anInterface) {
        this.anInterface = anInterface;
    }

    @NonNull

    @Override
    public BillListAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        BillListThemeBinding binding = DataBindingUtil.inflate(inflater, R.layout.bill_list_theme, parent, false);
        BillListAdapter2.ViewHolder viewHolder = new BillListAdapter2.ViewHolder(binding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BillListAdapter2.ViewHolder holder, int position) {
        int amount = rgsList.get(position).getAmount();
        int price = rgsList.get(position).getPriceService();
        holder.binding.confirmlistnameandpricetxt.setText(rgsList.get(position).getNameService().toString() + "\n" + price + " VND / " + rgsList.get(position).getUnitService());
        holder.binding.confirmlistamounttxt.setText(String.valueOf(amount));
        holder.binding.confirmlisttotalcosttxt.setText(String.valueOf(amount * price));

    }

    @Override
    public int getItemCount() {
        return rgsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        BillListThemeBinding binding;
        public ViewHolder(@NonNull BillListThemeBinding binding1) {
            super(binding1.getRoot());
            binding = binding1;
        }
    }
}
