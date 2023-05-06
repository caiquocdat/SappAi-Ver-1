package com.example.sappai.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sappai.MainActivity;
import com.example.sappai.ProfileActivity;
import com.example.sappai.R;
import com.example.sappai.RecentsOpenActivity;
import com.example.sappai.adapter.FavouritesAdapter;
import com.example.sappai.adapter.RecentAdapter;
import com.example.sappai.data.DBManager;
import com.example.sappai.data.DBRecentCopyManager;
import com.example.sappai.data.DBRecentManager;
import com.example.sappai.model.Favourites;
import com.example.sappai.model.RecentCopyModel;
import com.example.sappai.model.RecentModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class RecentsFragment extends Fragment {
    RecyclerView recentRcv;
    DBRecentCopyManager dbManager;
    RecentAdapter recentAdapter;
    TextView noHistoryTv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_recents, container, false);

//        tellLinear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {k
//                Intent intent = new Intent(getActivity(), RecentsOpenActivity.class);
//                startActivity(intent);
//            }
//        });
        recentRcv=view.findViewById(R.id.recentRcv);
        noHistoryTv=view.findViewById(R.id.noHistoryTv);
        dbManager= new DBRecentCopyManager(getContext());
        recentRcv.setVisibility(View.GONE);
        noHistoryTv.setVisibility(View.GONE);
        ArrayList<RecentCopyModel> recentList = dbManager.getAllFavourites();
        if (recentList.size()>0) {
            recentRcv.setVisibility(View.VISIBLE);
            for (int i = 0; i < recentList.size(); i++) {
                Log.d("QuocDat", "onCreateView: " + recentList.get(i).getTimeCreateChat());
                Log.d("QuocDat", "onCreateView: " + recentList.get(i).getListMessage().size());
            }
            recentAdapter = new RecentAdapter(recentList, getContext());
            recentRcv.setLayoutManager(new LinearLayoutManager(requireContext()));
            recentRcv.setItemAnimator(new DefaultItemAnimator());
            recentRcv.setAdapter(recentAdapter);
        }else{
            noHistoryTv.setVisibility(View.VISIBLE);
        }
        return view;
    }

}