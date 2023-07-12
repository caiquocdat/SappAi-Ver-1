package com.sapai_4.ai.fragment.explore;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sapai_4.ai.Diet_Plan_Activity;
import com.sapai_4.ai.Recipe_Activity;
import com.sapai_4.ai.R;


public class FoodFragment extends Fragment {
    LinearLayout recipeLinear,dietPlanLinear;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_food, container, false);
        recipeLinear=view.findViewById(R.id.recipeLinear);
        dietPlanLinear=view.findViewById(R.id.dietPlanLinear);
        recipeLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Recipe_Activity.class);
                startActivity(intent);
            }
        });
        dietPlanLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Diet_Plan_Activity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}