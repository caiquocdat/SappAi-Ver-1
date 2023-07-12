package com.sapai_4.ai.fragment.explore;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.sapai_4.ai.adapter.FavouritesAdapter;
import com.sapai_4.ai.data.DBManager;
import com.sapai_4.ai.model.Favourites;
import com.sapai_4.ai.R;

import java.util.ArrayList;


public class CategoryFragment extends Fragment {
    RecyclerView favouritesRcv;
    DBManager dbManager;
    FavouritesAdapter favouritesAdapter;
    FrameLayout favouriteFrm;
    TextView noFavouriteTv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_category, container, false);
        favouritesRcv=view.findViewById(R.id.favouriteRcv);
        favouriteFrm=view.findViewById(R.id.favouriteFrm);
        noFavouriteTv=view.findViewById(R.id.noFavouriteTv);
        favouriteFrm.setVisibility(View.GONE);
        noFavouriteTv.setVisibility(View.GONE);
        dbManager= new DBManager(getContext());
        ArrayList<Favourites> favouritesList = dbManager.getAllFavourites();
        if(favouritesList.size()>0) {
            favouriteFrm.setVisibility(View.VISIBLE);
            favouritesAdapter = new FavouritesAdapter(favouritesList, getContext());
            favouritesRcv.setLayoutManager(new LinearLayoutManager(requireContext()));
            favouritesRcv.setItemAnimator(new DefaultItemAnimator());
            favouritesRcv.setAdapter(favouritesAdapter);
        }else {
            noFavouriteTv.setVisibility(View.VISIBLE);
        }


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        favouriteFrm.setVisibility(View.GONE);
        noFavouriteTv.setVisibility(View.GONE);
        dbManager= new DBManager(getContext());
        ArrayList<Favourites> favouritesList = dbManager.getAllFavourites();
        if(favouritesList.size()>0) {
            favouriteFrm.setVisibility(View.VISIBLE);
            favouritesAdapter = new FavouritesAdapter(favouritesList, getContext());
            favouritesRcv.setLayoutManager(new LinearLayoutManager(requireContext()));
            favouritesRcv.setItemAnimator(new DefaultItemAnimator());
            favouritesRcv.setAdapter(favouritesAdapter);
        }else {
            noFavouriteTv.setVisibility(View.VISIBLE);
        }
    }
}