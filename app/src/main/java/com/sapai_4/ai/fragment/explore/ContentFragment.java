package com.sapai_4.ai.fragment.explore;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sapai_4.ai.Content_Write_Activity;
import com.sapai_4.ai.ImproveActivity;
import com.sapai_4.ai.Summarize_Activity;
import com.sapai_4.ai.TranslateActivity;
import com.sapai_4.ai.R;


public class ContentFragment extends Fragment {
    LinearLayout paragraphLinear,summarizeLinear,improveLinear,translateLinear;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_content, container, false);
        paragraphLinear=view.findViewById(R.id.paragraphLinear);
        summarizeLinear=view.findViewById(R.id.summarizeLinear);
        improveLinear=view.findViewById(R.id.improveLinear);
        translateLinear=view.findViewById(R.id.translateLinear);
        paragraphLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Content_Write_Activity.class);
                startActivity(intent);
            }
        });
        summarizeLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Summarize_Activity.class);
                startActivity(intent);
            }
        });
        improveLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ImproveActivity.class);
                startActivity(intent);
            }
        });
        translateLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), TranslateActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}