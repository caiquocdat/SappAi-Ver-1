package com.sapai_4.ai.fragment.explore;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sapai_4.ai.Adverisements_Activity;
import com.sapai_4.ai.Company_Bio_Activity;
import com.sapai_4.ai.Job_Post_Activity;
import com.sapai_4.ai.Name_Generator_Activity;
import com.sapai_4.ai.Slogan_Activity;
import com.sapai_4.ai.R;


public class BusinessFragment extends Fragment {
    LinearLayout companyBioLinear,nameGeneratorLinear,sloganLinear,advertisementsLinear,jobPostLinear;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_business, container, false);
        companyBioLinear=view.findViewById(R.id.companyBioLinear);
        nameGeneratorLinear=view.findViewById(R.id.nameGeneratorLinear);
        sloganLinear=view.findViewById(R.id.sloganLinear);
        advertisementsLinear=view.findViewById(R.id.advertisementsLinear);
        jobPostLinear=view.findViewById(R.id.jobPostLinear);
        companyBioLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Company_Bio_Activity.class);
                startActivity(intent);
            }
        });
        nameGeneratorLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Name_Generator_Activity.class);
                startActivity(intent);
            }
        });
        sloganLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Slogan_Activity.class);
                startActivity(intent);
            }
        });
        advertisementsLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Adverisements_Activity.class);
                startActivity(intent);
            }
        });
        jobPostLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Job_Post_Activity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}