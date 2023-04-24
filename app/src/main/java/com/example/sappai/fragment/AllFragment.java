package com.example.sappai.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.sappai.Content_Write_Activity;
import com.example.sappai.MainActivity;
import com.example.sappai.ProfileActivity;
import com.example.sappai.R;


public class AllFragment extends Fragment {
    LinearLayout paragraphLinear;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_all, container, false);
        paragraphLinear=view.findViewById(R.id.paragraphLinear);
        paragraphLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Content_Write_Activity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}