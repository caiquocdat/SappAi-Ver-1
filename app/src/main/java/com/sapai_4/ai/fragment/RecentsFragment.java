package com.sapai_4.ai.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sapai_4.ai.adapter.RecentAdapter;
import com.sapai_4.ai.data.DBRecentCopyManager;
import com.sapai_4.ai.R;
import com.sapai_4.ai.model.RecentCopyModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


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

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        dbManager= new DBRecentCopyManager(getContext());
        recentRcv.setVisibility(View.GONE);
        noHistoryTv.setVisibility(View.GONE);
        ArrayList<RecentCopyModel> recentList = dbManager.getAllFavourites();
        Collections.sort(recentList, new Comparator<RecentCopyModel>() {

            @Override
            public int compare(RecentCopyModel t1, RecentCopyModel t2) {
                return t2.getTimeCreateChat().compareTo(t1.getTimeCreateChat());
            }
        });
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
    }
}