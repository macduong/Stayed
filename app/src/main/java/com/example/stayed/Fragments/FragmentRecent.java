package com.example.stayed.Fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.example.stayed.CustomAdapter.RecentListAdapter;
import com.example.stayed.Database.DB;
import com.example.stayed.Model.GuestServices;
import com.example.stayed.Model.RentGuests;
import com.example.stayed.R;
import com.example.stayed.databinding.FragmentRecentBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentRecent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentRecent extends Fragment {
    private List<RentGuests> guestsList = new ArrayList<>();
    private RecentListAdapter adapter;
    private FragmentRecentBinding binding;
    private DB db;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentRecent() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentRecent.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentRecent newInstance(String param1, String param2) {
        FragmentRecent fragment = new FragmentRecent();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recent, container, false);
        db = new DB(getContext());
        guestsList.clear();
        guestsList.addAll(db.searchAllRecentRenguest());
        adapter = new RecentListAdapter(guestsList);
        binding.recentlst.setAdapter(adapter);

        binding.recentsearchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });

        binding.recentsearchtxt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    search();
                }
                return true;
            }
        });

        binding.recentlst.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (binding.recentlst.getChildAt(0) != null) {
                    binding.refresh.setEnabled(binding.recentlst.getFirstVisiblePosition() == 0 && binding.recentlst.getChildAt(0).getTop() == 0);
                }
            }
        });

        binding.refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    private void search() {
        String name = binding.recentsearchtxt.getText().toString();
        if (name.isEmpty()) {
            binding.recentsearchtxt.setError("Please insert name");
        } else {
            guestsList.clear();
            guestsList.addAll(db.searchAllRecentRenguestByName(name));
            adapter.notifyDataSetChanged();
        }
    }

    private void refresh() {
        guestsList.clear();
        guestsList.addAll(db.searchAllRecentRenguest());
        adapter.notifyDataSetChanged();
        binding.refresh.setRefreshing(false);
    }
}