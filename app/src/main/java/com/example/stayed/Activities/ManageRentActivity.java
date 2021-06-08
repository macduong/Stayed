package com.example.stayed.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stayed.CustomAdapter.BillListAdapter;
import com.example.stayed.CustomAdapter.BillListAdapter2;
import com.example.stayed.Database.DB;
import com.example.stayed.Model.GuestServices;
import com.example.stayed.Model.RGS;
import com.example.stayed.Model.RentGuests;
import com.example.stayed.Model.Rooms;
import com.example.stayed.R;
import com.example.stayed.databinding.ManageRentBinding;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ManageRentActivity extends AppCompatActivity {
    private ManageRentBinding binding;
    private RentGuests guest = new RentGuests();
    private Rooms openingRoom = new Rooms();
    private LocalDateTime checkin, checkout;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm  dd/MM/yyyy");
    private int openRoomId;
    private long currentRentguestId;
    private DB db;
    private List<RGS> rgsList = new ArrayList<>();
    private List<GuestServices> guestServices = new ArrayList<>();
    private BillListAdapter2 billListAdapter2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.manage_rent);
        db = new DB(getBaseContext());


        Intent intent = getIntent();
        openRoomId = intent.getIntExtra("openroomid", 0);
        openingRoom = db.searchOneRoomsById(openRoomId);
        if (openingRoom.getAvailable() == 1) {
            firstAppearance();
        } else {
            secondAppearance();
        }
        binding.rentroomnametxt.setText("Room " + openingRoom.getId());
        if (openingRoom.getVip() == 1) {
            binding.vipicon1.setVisibility(View.VISIBLE);
            binding.vipicon2.setVisibility(View.VISIBLE);
        }


        binding.rentconfirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.rentinputnametxt.getText().toString().equalsIgnoreCase("")) {
                    warningAndFocus(binding.rentinputnametxt, "Please enter guest's name");
                } else if (binding.rentinputidtxt.getText().toString().equalsIgnoreCase("")) {
                    warningAndFocus(binding.rentinputidtxt, "Please enter guest's ID");
                } else {
                    checkin = LocalDateTime.now();
                    guest.setTimeCheckin(checkin.format(formatter));
                    guest.setRoomId(openRoomId);
                    guest.setCid(binding.rentinputidtxt.getText().toString());
                    guest.setName(binding.rentinputnametxt.getText().toString());
                    if (binding.rentmalebtn.isChecked()) {
                        guest.setSex("Male");
                    } else if (binding.rentfemalebtn.isChecked()) {
                        guest.setSex("Female");
                    } else {
                        guest.setSex("Not mentioned");
                    }
                    currentRentguestId = db.insertRentGuest(guest);
                    openingRoom.setAvailable(0);
                    db.updateRoom(openingRoom);
                    secondAppearance();
                }
            }
        });


        binding.rentuseservicebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), UseServices.class);
                intent.putExtra("guestId", guest.getId());
                startActivity(intent);
            }
        });

        binding.rentcheckoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.rentuseservicebtn.setVisibility(View.GONE);
                binding.rentcheckouttimelayout.setVisibility(View.VISIBLE);
                checkout = LocalDateTime.now();
                checkin = LocalDateTime.parse(guest.getTimeCheckin(), formatter);
                binding.rentcheckouttimetxt.setText(checkout.format(formatter));
                binding.totalcosttxt.setVisibility(View.VISIBLE);
                binding.rentcheckoutbtn.setVisibility(View.GONE);
                Duration total = Duration.between(checkin, checkout);

                String time = "";
                long days = total.toDays();
                if (days > 0) {
                    time += days + "d ";
                }
                total = total.minusDays(days);
                long hours = total.toHours();
                if (hours > 0) {
                    time += hours + "h ";
                }
                total = total.minusHours(hours);
                long minutes = total.toMinutes();
                if (minutes > 0) {
                    time += minutes + "m ";
                }
                if (time.isEmpty()) {
                    time = "Guest hasn't rent yet";
                }

                guest.setTimeCheckout(checkout.format(formatter));
                db.updateRentGuest(guest);
                openingRoom.setAvailable(1);
                db.updateRoom(openingRoom);

                long totalcost = days * 24 * openingRoom.getPrice() + hours * openingRoom.getPrice() + openingRoom.getPrice() * minutes / 60 + totalSum(rgsList);
                binding.totalcosttxt.setText(String.valueOf(totalcost) + " VND");
                binding.renttotaltimetxt.setText(time);
                binding.renttotaltimelayout.setVisibility(View.VISIBLE);
                binding.rentcheckouttimelayout.setVisibility(View.VISIBLE);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (openingRoom.getAvailable() == 1) {
            firstAppearance();
        } else {
            secondAppearance();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void firstAppearance() {
        binding.rentconfirmednametxt.setVisibility(View.GONE);
        binding.rentconfirmedidtxt.setVisibility(View.GONE);
        binding.rentconfirmedsextxt.setVisibility(View.GONE);
        binding.rentcheckintimelayout.setVisibility(View.GONE);
        binding.rentcheckouttimelayout.setVisibility(View.GONE);
        binding.renttotaltimelayout.setVisibility(View.GONE);
        binding.rentcheckoutbtn.setVisibility(View.GONE);
        binding.rentuseservicebtn.setVisibility(View.GONE);
    }

    private void secondAppearance() {

        //lay idrentguest, lay rentguest tu id, gan vao guest
        guest = db.searchOneRentGuestByRoomId(openingRoom.getId());


        binding.rentconfirmednametxt.setText(guest.getName());
        binding.rentconfirmedidtxt.setText(String.valueOf(guest.getCid()));
        binding.rentconfirmedsextxt.setText(guest.getSex());
        binding.rentcheckintimetxt.setText(guest.getTimeCheckin());

        binding.rentinputnametxt.setVisibility(View.GONE);
        binding.rentinputidtxt.setVisibility(View.GONE);
        binding.rentinputsexgr.setVisibility(View.GONE);
        binding.rentcheckouttimelayout.setVisibility(View.GONE);
        binding.renttotaltimelayout.setVisibility(View.GONE);
        binding.rentconfirmbtn.setVisibility(View.GONE);

        binding.rentconfirmednametxt.setVisibility(View.VISIBLE);
        binding.rentconfirmedidtxt.setVisibility(View.VISIBLE);
        binding.rentconfirmedsextxt.setVisibility(View.VISIBLE);
        binding.rentcheckintimelayout.setVisibility(View.VISIBLE);
        binding.rentuseservicebtn.setVisibility(View.VISIBLE);
        binding.rentcheckoutbtn.setVisibility(View.VISIBLE);

        guestServices.clear();
        rgsList.clear();
        guestServices = db.searchAllGuestService(guest.getId());
        guestServices.forEach(guestServices1 -> {
            rgsList.add(db.getAllInfoRGS(guestServices1));
        });


        billListAdapter2 = new BillListAdapter2(rgsList, getBaseContext());
        binding.recycle.setAdapter(billListAdapter2);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getBaseContext(), 1,
                RecyclerView.VERTICAL, false);
        binding.recycle.setLayoutManager(layoutManager);
    }

    private long totalSum(List<RGS> rgsList) {
        long total = 0;
        int size = rgsList.size();
        for (int i = 0; i < size; i++) {
            total = total + rgsList.get(i).getAmount() * rgsList.get(i).getPriceService();
        }
        return total;
    }

    private void warningAndFocus(EditText focusText, String warning) {
        Toast.makeText(getBaseContext(), warning, Toast.LENGTH_LONG).show();
        focusText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(focusText, InputMethodManager.SHOW_IMPLICIT);
    }


}