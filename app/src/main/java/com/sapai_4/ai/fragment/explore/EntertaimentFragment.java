package com.sapai_4.ai.fragment.explore;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sapai_4.ai.ConversationActivity;
import com.sapai_4.ai.Fight_Activity;
import com.sapai_4.ai.Sentence_Activity;
import com.sapai_4.ai.Tell_Joke_Activity;
import com.sapai_4.ai.To_Emoji_Activity;
import com.sapai_4.ai.Words_Activity;
import com.sapai_4.ai.R;


public class EntertaimentFragment extends Fragment {
    LinearLayout toEmojiLinear,tellJokeLinear,conversationLinear,fightLinear,sentenceLinear,wordsLinear;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_entertaiment, container, false);
        toEmojiLinear=view.findViewById(R.id.toEmojiLinear);
        tellJokeLinear=view.findViewById(R.id.tellJokeLinear);
        conversationLinear=view.findViewById(R.id.conversationLinear);
        fightLinear=view.findViewById(R.id.fightLinear);
        sentenceLinear=view.findViewById(R.id.sentenceLinear);
        wordsLinear=view.findViewById(R.id.wordsLinear);
        toEmojiLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), To_Emoji_Activity.class);
                startActivity(intent);
            }
        });
        tellJokeLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Tell_Joke_Activity.class);
                startActivity(intent);
            }
        });
        sentenceLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Sentence_Activity.class);
                startActivity(intent);
            }
        });
        fightLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Fight_Activity.class);
                startActivity(intent);
            }
        });
        conversationLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ConversationActivity.class);
                startActivity(intent);
            }
        });
        wordsLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Words_Activity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}