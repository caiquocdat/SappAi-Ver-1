package com.sapai_4.ai.fragment.explore;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sapai_4.ai.Email_Activity;
import com.sapai_4.ai.Email_Subject_Activity;
import com.sapai_4.ai.Improve_Email_Activity;
import com.sapai_4.ai.R;

public class EmailFragment extends Fragment {
    LinearLayout emailLinear,improveEmailLinear,emailSubjectLinear;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_email, container, false);
        emailLinear=view.findViewById(R.id.emailLinear);
        emailSubjectLinear=view.findViewById(R.id.emailSubjectLinear);
        improveEmailLinear=view.findViewById(R.id.improveEmailLinear);
        emailLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Email_Activity.class);
                startActivity(intent);
            }
        });
        emailSubjectLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Email_Subject_Activity.class);
                startActivity(intent);
            }
        });
        improveEmailLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Improve_Email_Activity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}