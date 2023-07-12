package com.sapai_4.ai.fragment.explore;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sapai_4.ai.Explain_Code_Activity;
import com.sapai_4.ai.Write_Code_Activity;
import com.sapai_4.ai.R;


public class CodeFragment extends Fragment {
    LinearLayout writeCodeLinear,explainCodeLinear;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_code, container, false);
        writeCodeLinear=view.findViewById(R.id.writeCodeLinear);
        explainCodeLinear=view.findViewById(R.id.explainCodeLinear);
        writeCodeLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Write_Code_Activity.class);
                startActivity(intent);
            }
        });
        explainCodeLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Explain_Code_Activity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}