package com.example.sappai.fragment.explore;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.sappai.Instagram_Activity;
import com.example.sappai.Into_Tweet_Activity;
import com.example.sappai.Linkedin_Post_Activity;
import com.example.sappai.R;
import com.example.sappai.Tiktok_Activity;
import com.example.sappai.Tweet_Activity;
import com.example.sappai.Viral_Video_Activity;


public class SocialFragment extends Fragment {
    LinearLayout tweetLinear,intoTweetLinear,linkedinPostLinear,instagramLinear,tiktokLinear,viralVideoLinear;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_social, container, false);
        tweetLinear=view.findViewById(R.id.tweetLinear);
        intoTweetLinear=view.findViewById(R.id.intoTweetLinear);
        linkedinPostLinear=view.findViewById(R.id.linkedinPostLinear);
        instagramLinear=view.findViewById(R.id.instagramLinear);
        tiktokLinear=view.findViewById(R.id.titokLinear);
        viralVideoLinear=view.findViewById(R.id.viralVideoLinear);
        tweetLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Tweet_Activity.class);
                startActivity(intent);
            }
        });
        intoTweetLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Into_Tweet_Activity.class);
                startActivity(intent);
            }
        });
        linkedinPostLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Linkedin_Post_Activity.class);
                startActivity(intent);
            }
        });
        instagramLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Instagram_Activity.class);
                startActivity(intent);
            }
        });
        tiktokLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Tiktok_Activity.class);
                startActivity(intent);
            }
        });
        viralVideoLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Viral_Video_Activity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}