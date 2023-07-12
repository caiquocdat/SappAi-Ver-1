package com.sapai_4.ai.fragment.explore;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sapai_4.ai.LyricsActivity;
import com.sapai_4.ai.PoemActivity;
import com.sapai_4.ai.Short_Movie_Activity;
import com.sapai_4.ai.Story_Activity;
import com.sapai_4.ai.R;


public class ArtistFragment extends Fragment {
    LinearLayout lyricsLinear,poemLinear,storyLinear,shortMovieLinear;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_artist, container, false);
        lyricsLinear=view.findViewById(R.id.lyricsLinear);
        poemLinear=view.findViewById(R.id.poemLinear);
        storyLinear=view.findViewById(R.id.storyLinear);
        shortMovieLinear=view.findViewById(R.id.shortMovieLinear);
        lyricsLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), LyricsActivity.class);
                startActivity(intent);
            }
        });
        poemLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PoemActivity.class);
                startActivity(intent);
            }
        });
        storyLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Story_Activity.class);
                startActivity(intent);
            }
        });
        shortMovieLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Short_Movie_Activity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}