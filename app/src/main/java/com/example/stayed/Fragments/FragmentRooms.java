package com.example.stayed.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.stayed.Activities.ManageRentActivity;
import com.example.stayed.Activities.ManageRoomsActivity;
import com.example.stayed.CustomAdapter.RoomListAdapter;
import com.example.stayed.Database.DB;
import com.example.stayed.Model.Rooms;
import com.example.stayed.R;
import com.example.stayed.databinding.FragmentRoomsBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentRooms#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentRooms extends Fragment {
    private FragmentRoomsBinding binding;
    private List<Rooms> roomsList = new ArrayList<>();
    private RoomListAdapter adapter;
    private DB database;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentRooms() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentRooms.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentRooms newInstance(String param1, String param2) {
        FragmentRooms fragment = new FragmentRooms();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_rooms, container, false);
        database = new DB(getContext());
        roomsList.addAll(database.searchAllRooms());
        adapter = new RoomListAdapter(roomsList);
        binding.mngroomlst.setAdapter(adapter);


        binding.mngroomrefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                layoutRefresh();
            }
        });

        binding.mngroomlst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), ManageRentActivity.class);
                intent.putExtra("openroomid", roomsList.get(position).getId()); // gui sang managerent de danh dau phong ban
                startActivity(intent);
            }
        });

        binding.mngroomsearchtxt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == 66) {
                    search();
                    return true;
                }
                else {
                    return false;
                }
            }
        });
        binding.mngroomsearchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        layoutRefresh();
    }

    private void search() {
        if (binding.mngroomsearchtxt.getText().toString().isEmpty()) {
            warningAndFocus(binding.mngroomsearchtxt, "Please insert room's name or guest's name");
        } else {
            String search = binding.mngroomsearchtxt.getText().toString();
            roomsList.clear();
            roomsList.addAll(database.searchAllRoomsById(search));
            adapter.notifyDataSetChanged();
            binding.mngroomsearchtxt.getText().clear();
            binding.mngroomsearchtxt.clearFocus();
        }

    }

    private void layoutRefresh(){
        roomsList.clear();
        roomsList.addAll(database.searchAllRooms());
        adapter.notifyDataSetChanged();
        binding.mngroomrefresh.setRefreshing(false);
    }

    private void warningAndFocus(EditText focusText, String warning) {
        Toast.makeText(getContext(), warning, Toast.LENGTH_LONG).show();
        focusText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(focusText, InputMethodManager.SHOW_IMPLICIT);
    }
}