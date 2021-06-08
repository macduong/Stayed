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

import com.example.stayed.CustomAdapter.ChooseServiceAdapter;
import com.example.stayed.CustomAdapter.ChooseServiceInterface;
import com.example.stayed.CustomAdapter.ConfirmServiceAdapter;
import com.example.stayed.CustomAdapter.ConfirmServiceInterface;
import com.example.stayed.Database.DB;
import com.example.stayed.Model.GuestServices;
import com.example.stayed.Model.RGS;
import com.example.stayed.Model.Services;
import com.example.stayed.R;
import com.example.stayed.databinding.ActivityUseServicesBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UseServices extends AppCompatActivity {
    private ActivityUseServicesBinding binding;
    private List<Services> servicesList = new ArrayList<>();
    private ChooseServiceAdapter serviceAdapter;
    private ConfirmServiceAdapter confirmServiceAdapter;
    private DB db;
    private Services service = new Services();
    private List<RGS> rgsList = new ArrayList<>();
    private List<GuestServices> guestServicesList = new ArrayList<>();
    private int guestId;
    private boolean isFABOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_use_services);
        db = new DB(getBaseContext());


        serviceAdapter = new ChooseServiceAdapter(servicesList);
        binding.usechoiceslst.setAdapter(serviceAdapter);
        layout1Refresh();

        confirmServiceAdapter = new ConfirmServiceAdapter(rgsList);
        binding.uselst.setAdapter(confirmServiceAdapter);
        layout2Refresh();

        binding.usechoiceslst.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (binding.usechoiceslst.getChildAt(0) != null) {
                    binding.refreshchoices.setEnabled(binding.usechoiceslst.getFirstVisiblePosition() == 0 && binding.usechoiceslst.getChildAt(0).getTop() == 0);
                }
            }
        });

        binding.refreshchoices.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                layout1Refresh();
                binding.refreshchoices.setRefreshing(false);
            }
        });

        binding.usesearchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });

        binding.usesearchtxt.setOnKeyListener(new View.OnKeyListener() {
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

        serviceAdapter.setAnInterface(new ChooseServiceInterface() {
            @Override
            public void onclickEdit(int id) {
                Intent intent = new Intent(getBaseContext(), EditService.class);
                intent.putExtra("serviceId", id);
                startActivity(intent);
            }

            @Override
            public void onclickChoose(int id) {
                service = db.searchOneServicesById(id);
                binding.usename.setText(service.getName());
                warningAndFocus(binding.usesamount, "Insert amount to add");
                binding.useaddbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (binding.usesamount.getText().toString().isEmpty() || binding.usesamount.getText().toString().equalsIgnoreCase("0")) {
                            warningAndFocus(binding.usesamount, "Please insert amount!");
                        } else {
                            binding.usename.setText("Service's name");
                            int amount = Integer.parseInt(binding.usesamount.getText().toString());
                            GuestServices guestService = new GuestServices(guestId, id, amount);
                            int gsId = db.checkExistGuestService(guestService);
                            if (gsId > 0) {
                                guestService.setId(gsId);
                                add(db.searchOneGuestServiceById(gsId), amount);
                            } else {
                                db.insertGuestService(guestService);

                                binding.usesamount.getText().clear();
                                binding.usesamount.clearFocus();
                                layout2Refresh();
                            }
                        }
                    }
                });
            }
        });

        // Nua tren ket thuc, nua giua bat dau


        //Nua giua ket thuc, nua cuoi bat dau

        binding.uselst.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (binding.uselst.getChildAt(0) != null) {
                    binding.refreshuse.setEnabled(binding.uselst.getFirstVisiblePosition() == 0 && binding.uselst.getChildAt(0).getTop() == 0);
                }
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 7) {
                    binding.usecaltotalbtn.setVisibility(View.GONE);
                } else {
                    binding.usecaltotalbtn.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.refreshuse.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                layout2Refresh();
                binding.refreshuse.setRefreshing(false);
            }
        });

        binding.usecaltotalbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFABOpen){
                    showFABMenu();
                }else{
                    closeFABMenu();
                }
            }
        });

        binding.usecaltotalbtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                closeFABMenu();
                binding.usecaltotalbtn.setVisibility(View.GONE);
                return true;
            }
        });

        confirmServiceAdapter.setAnInterface(new ConfirmServiceInterface() {
            @Override
            public void onclickRemove(int id) {
                db.deleteGuestServiceById(id);
                layout2Refresh();
            }

            @Override
            public void onclickEdit(int id) {
                GuestServices guestService = db.searchOneGuestServiceById(id);
                binding.usename.setText(db.getAllInfoRGS(guestService).getNameService()); //lay guestservice qua id, lay rgs qua guestservice, lay ten service qua rgs
                binding.useaddbtn.setText("EDIT");
                warningAndFocus(binding.usesamount, "Insert amount to edit");
                binding.useaddbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(binding.usesamount.getText().toString().equalsIgnoreCase("0")){
                            binding.usesamount.getText().clear();
                            warningAndFocus(binding.usesamount,"Amount must be bigger than 0");
                        }
                        else{
                            edit(guestService);
                        }
                    }
                });
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        guestId = intent.getIntExtra("guestId", 0);
        layout1Refresh();
        layout2Refresh();
    }

    private void layout1Refresh() {
        servicesList.clear();
        servicesList.addAll(db.searchAllServices());
        Collections.sort(servicesList, Services.nameComp);  //sort services by name
        serviceAdapter.notifyDataSetChanged();
    }

    private void layout2Refresh() {
        rgsList.clear();
        guestServicesList.clear();
        guestServicesList.addAll(db.searchAllGuestService(guestId));
        guestServicesList.forEach(guestServices -> {
            rgsList.add(db.getAllInfoRGS(guestServices));
        });
        confirmServiceAdapter.notifyDataSetChanged();
    }

    private void edit(GuestServices guestService) {
        if (binding.usesamount.getText().toString().isEmpty()) {
            warningAndFocus(binding.usesamount, "Please insert amount before click");
        } else {
            guestService.setAmount(Integer.parseInt(binding.usesamount.getText().toString()));
            db.updateGuestService(guestService);
            binding.useaddbtn.setText("ADD");
            binding.usesamount.getText().clear();
            binding.usename.setText("Service's name");
            binding.usesamount.clearFocus();
            layout2Refresh();
        }
    }

    private void add(GuestServices guestService, int amount) {
        if (binding.usesamount.getText().toString().isEmpty()) {
            warningAndFocus(binding.usesamount, "Please insert amount before click");
        } else {
            int amountAdd = guestService.getAmount() + amount;
            guestService.setAmount(amountAdd);
            db.updateGuestService(guestService);
            binding.useaddbtn.setText("ADD");
            binding.usesamount.getText().clear();
            binding.usename.setText("Service's name");
            binding.usesamount.clearFocus();
            layout2Refresh();
        }
    }

    private void search() {
        if (binding.usesearchtxt.getText().toString().isEmpty()) {
            warningAndFocus(binding.usesearchtxt, "Please insert room's name or guest's name");
        } else {
            String search = binding.usesearchtxt.getText().toString();
            servicesList.clear();
            servicesList.addAll(db.searchAllServicesByName(search));
            serviceAdapter.notifyDataSetChanged();
            binding.usesearchtxt.getText().clear();
            binding.usesearchtxt.clearFocus();
        }

    }

    private void showFABMenu() {
        isFABOpen = true;
        binding.usetotaltxt.setVisibility(View.VISIBLE);
        binding.usetotaltxt.setText("Total cost:" + totalSum(rgsList));
    }

    private void closeFABMenu() {
        isFABOpen = false;
        binding.usetotaltxt.setVisibility(View.GONE);
    }

    private long totalSum(List<RGS> rgsList) {
        long total = 0;
        int size = rgsList.size();
        for (int i = 0; i < size; i++) {
            total = total + rgsList.get(i).getAmount() * rgsList.get(i).getPriceService();
        }
        return total;
    }

    public void warningAndFocus(EditText focusText, String warning) {
        Toast.makeText(getBaseContext(), warning, Toast.LENGTH_LONG).show();
        focusText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(focusText, InputMethodManager.SHOW_IMPLICIT);
    }
}