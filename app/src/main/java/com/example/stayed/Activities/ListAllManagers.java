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
import android.widget.EditText;
import android.widget.Toast;

import com.example.stayed.CustomAdapter.ManagersListAdapter;
import com.example.stayed.CustomAdapter.ServicesListInterface;
import com.example.stayed.Database.DB;
import com.example.stayed.Model.Managers;
import com.example.stayed.R;
import com.example.stayed.databinding.ActivityListAllManagersBinding;

import java.util.ArrayList;
import java.util.List;

public class ListAllManagers extends AppCompatActivity {
    private ActivityListAllManagersBinding binding;
    private DB db;
    private List<Managers> managersList = new ArrayList<>();
    private ManagersListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list_all_managers);
        db = new DB(getBaseContext());

        managersList = db.searchAllManagers();
        adapter = new ManagersListAdapter(managersList);
        binding.managerslst.setAdapter(adapter);
        layoutRefresh();

        binding.managerssearchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });

        binding.managerssearchtxt.setOnKeyListener(new View.OnKeyListener() {
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

        binding.managerslst.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (binding.managerslst.getChildAt(0) != null) {
                    binding.refresh.setEnabled(binding.managerslst.getFirstVisiblePosition() == 0 && binding.managerslst.getChildAt(0).getTop() == 0);
                }
            }
        });

        binding.refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                layoutRefresh();
            }
        });

        adapter.setAnInterface(new ServicesListInterface() {
            @Override
            public void onclickRemove(int id) {
                db.deleteManagerById(id);
                layoutRefresh();
            }

            @Override
            public void onclickEdit(int id) {
                layoutRefresh();
            }
        });
    }

    private void layoutRefresh() {
        managersList.clear();
        managersList.addAll(db.searchAllManagers());
        adapter.notifyDataSetChanged();
        binding.refresh.setRefreshing(false);
    }

    private void search() {
        if (binding.managerssearchtxt.getText().toString().isEmpty()) {
            warningAndFocus(binding.managerssearchtxt, "Please insert room's name or guest's name");
        } else {
            String search = binding.managerssearchtxt.getText().toString();
            managersList.clear();
            managersList.addAll(db.searchAllManagersByName(search));
            adapter.notifyDataSetChanged();
            binding.managerssearchtxt.getText().clear();
            binding.managerssearchtxt.clearFocus();
        }

    }

    public void warningAndFocus(EditText focusText, String warning) {
        Toast.makeText(getBaseContext(), warning, Toast.LENGTH_LONG).show();
        focusText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(focusText, InputMethodManager.SHOW_IMPLICIT);
    }
}