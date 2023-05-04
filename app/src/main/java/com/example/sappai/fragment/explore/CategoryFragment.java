package com.example.sappai.fragment.explore;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sappai.R;
import com.example.sappai.adapter.FavouritesAdapter;
import com.example.sappai.data.DBManager;
import com.example.sappai.model.Favourites;

import java.util.ArrayList;


public class CategoryFragment extends Fragment {
    RecyclerView favouritesRcv;
    DBManager dbManager;
    FavouritesAdapter favouritesAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_category, container, false);
        favouritesRcv=view.findViewById(R.id.favouriteRcv);
        dbManager= new DBManager(getContext());
        ArrayList<Favourites> favouritesList = dbManager.getAllFavourites();
        favouritesAdapter=new FavouritesAdapter(favouritesList,getContext());
        favouritesRcv.setLayoutManager(new LinearLayoutManager(requireContext()));
        favouritesRcv.setItemAnimator(new DefaultItemAnimator());
        favouritesRcv.setAdapter(favouritesAdapter);



        return view;
    }
}