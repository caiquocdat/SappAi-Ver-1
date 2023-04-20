package com.example.sappai.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.sappai.MainActivity;
import com.example.sappai.ProfileActivity;
import com.example.sappai.R;
import com.example.sappai.RecentsOpenActivity;


public class RecentsFragment extends Fragment {

    LinearLayout explainLinear;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_recents, container, false);
        explainLinear=view.findViewById(R.id.explainLinear);
        explainLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RecentsOpenActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}