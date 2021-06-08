package com.example.stayed.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.stayed.CustomAdapter.ServicesListAdapter;
import com.example.stayed.CustomAdapter.ServicesListInterface;
import com.example.stayed.Database.DB;
import com.example.stayed.Model.Services;
import com.example.stayed.R;
import com.example.stayed.databinding.ActivityManageServicesBinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ManageServicesActivity extends AppCompatActivity {
    private ActivityManageServicesBinding binding;
    private List<Services> servicesList = new ArrayList<>();
    private ServicesListAdapter adapter;
    private DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_manage_services);
        db = new DB(getBaseContext());

        //Test

//        db.insertService(new Services("Sữa tươi trân châu đường đen",12000,"Cốc"));
//        db.insertService(new Services("Trà phô mai kem sữa",12000,"Cốc"));
//        db.insertService(new Services("Trà hoa quả",12000,"Cốc"));
//        db.insertService(new Services("Matcha đá xay",12000,"Cốc"));
//        db.insertService(new Services("Trà đào chanh sả",12000,"Cốc"));
//        db.insertService(new Services("Trà hoa quả nhiệt đới",12000,"Cốc"));
//        db.insertService(new Services("Trà sữa gạo rang Hàn Quốc",12000,"Cốc"));
//        db.insertService(new Services("Trà sữa Hokkaido",12000,"Cốc"));
//        db.insertService(new Services("Cafe latte",12000,"Cốc"));
//        db.insertService(new Services("Cacao",12000,"Cốc"));
//        db.insertService(new Services("Socola đá xay",12000,"Cốc"));
//        db.insertService(new Services("Sinh tố",12000,"Cốc"));
//        db.insertService(new Services("Soda việt quất",12000,"Chai"));
//        db.insertService(new Services("Pepsi",12000,"Chai"));
//        db.insertService(new Services("Trà xanh không độ ",12000,"Chai"));
//        db.insertService(new Services("Coca cola",12000,"Chai"));
//        db.insertService(new Services("7up",12000,"Chai"));
//        db.insertService(new Services("Number one",12000,"Chai"));
//        db.insertService(new Services("Nước cam Twister",12000,"Chai"));
//        db.insertService(new Services("Mirinda",12000,"Chai"));

        //Endtest


        adapter = new ServicesListAdapter(servicesList);
        binding.serviceslst.setAdapter(adapter);
        layoutRefresh();

        binding.serviceslst.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (binding.serviceslst.getChildAt(0) != null) {
                    binding.refresh.setEnabled(binding.serviceslst.getFirstVisiblePosition() == 0 && binding.serviceslst.getChildAt(0).getTop() == 0);
                }
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 13) {
                    binding.servicesaddbtn.setVisibility(View.GONE);
                } else {
                    binding.servicesaddbtn.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                layoutRefresh();
                binding.refresh.setRefreshing(false);
            }
        });

        adapter.setAnInterface(new ServicesListInterface() {
            @Override
            public void onclickRemove(int id) {
                db.deleteServiceById(id);
                layoutRefresh();
            }

            @Override
            public void onclickEdit(int id) {
                Intent intent = new Intent(getBaseContext(), EditService.class);
                intent.putExtra("serviceId", id);
                startActivity(intent);
            }
        });

        binding.servicessearchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.servicessearchtxt.getText().toString().isEmpty()) {
                    warningAndFocus(binding.servicessearchtxt, "Please input before search");
                } else {
                    search();
                }
            }
        });

        binding.servicessearchtxt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    search();
                    return true;
                } else {
                    return false;
                }
            }
        });

        binding.servicesaddbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), AddService.class);
                startActivity(intent);
            }
        });

        binding.servicesaddbtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                binding.servicesaddbtn.setVisibility(View.GONE);
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        layoutRefresh();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void layoutRefresh() {
        servicesList.clear();
        servicesList.addAll(db.searchAllServices());
//        Collections.sort(servicesList, Services.nameComp);  //sort services by name
        adapter.notifyDataSetChanged();
    }

    private void search() {
        if (binding.servicessearchtxt.getText().toString().isEmpty()) {
            warningAndFocus(binding.servicessearchtxt, "Please insert service's name");
        } else {
            String search = binding.servicessearchtxt.getText().toString();
            servicesList.clear();
            servicesList.addAll(db.searchAllServicesByName(search));
            adapter.notifyDataSetChanged();
            binding.servicessearchtxt.getText().clear();
            binding.servicessearchtxt.clearFocus();
        }

    }

    public void warningAndFocus(EditText focusText, String warning) {
        Toast.makeText(getBaseContext(), warning, Toast.LENGTH_LONG).show();
        focusText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(focusText, InputMethodManager.SHOW_IMPLICIT);
    }
}