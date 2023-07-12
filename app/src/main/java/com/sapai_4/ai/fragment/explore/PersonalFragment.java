package com.sapai_4.ai.fragment.explore;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sapai_4.ai.Apology_Activity;
import com.sapai_4.ai.Birthday_Activity;
import com.sapai_4.ai.Invitation_Activity;
import com.sapai_4.ai.Speech_Activity;
import com.sapai_4.ai.Up_Line_Activity;
import com.sapai_4.ai.R;


public class PersonalFragment extends Fragment {
    LinearLayout birthDayLinear,apologyLinear,invitationLinear,pickLineLinear,speechLinear;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_personal, container, false);
        birthDayLinear=view.findViewById(R.id.birthdayLinear);
        apologyLinear=view.findViewById(R.id.apologyLinear);
        invitationLinear=view.findViewById(R.id.invitationLinear);
        pickLineLinear=view.findViewById(R.id.pickUpLineLinear);
        speechLinear=view.findViewById(R.id.speechLinear);
        birthDayLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Birthday_Activity.class);
                startActivity(intent);
            }
        });
        apologyLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Apology_Activity.class);
                startActivity(intent);
            }
        });
        invitationLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Invitation_Activity.class);
                startActivity(intent);
            }
        });
        pickLineLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Up_Line_Activity.class);
                startActivity(intent);
            }
        });
        speechLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Speech_Activity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}