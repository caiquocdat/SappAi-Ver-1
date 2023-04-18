package com.example.sappai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.sappai.fragment.ChatFragment;
import com.example.sappai.fragment.ExploreFragment;
import com.example.sappai.fragment.RecentsFragment;

public class MainActivity extends AppCompatActivity {
    ImageView chatImg,exploreImg,searchImg;
    LinearLayout chatBrg,exploreBrg,recentBrg;
    Drawable selectedItem,unSelectItem ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ChatFragment fragment1= new ChatFragment();
        FragmentTransaction ft1=getSupportFragmentManager().beginTransaction();
        ft1.replace(R.id.content,fragment1,"");
        ft1.commit();
        unSelectItem=getResources().getDrawable(R.drawable.shape_item_bottom_bar);
        selectedItem = getResources().getDrawable(R.drawable.shape_item_bottom_bar_selected);
        mapping();
        chatImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatBrg.setBackground(selectedItem);
                exploreBrg.setBackground(unSelectItem);
                recentBrg.setBackground(unSelectItem);
                ChatFragment fragment1= new ChatFragment();
                FragmentTransaction ft1=getSupportFragmentManager().beginTransaction();
                ft1.replace(R.id.content,fragment1,"");
                ft1.commit();
            }
        });
        exploreImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatBrg.setBackground(unSelectItem);
                exploreBrg.setBackground(selectedItem);
                recentBrg.setBackground(unSelectItem);
                ExploreFragment fragment1= new ExploreFragment();
                FragmentTransaction ft1=getSupportFragmentManager().beginTransaction();
                ft1.replace(R.id.content,fragment1,"");
                ft1.commit();
            }
        });

        searchImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatBrg.setBackground(unSelectItem);
                exploreBrg.setBackground(unSelectItem);
                recentBrg.setBackground(selectedItem);
                RecentsFragment fragment1= new RecentsFragment();
                FragmentTransaction ft1=getSupportFragmentManager().beginTransaction();
                ft1.replace(R.id.content,fragment1,"");
                ft1.commit();
            }
        });
    }

    private void mapping() {
        chatImg=findViewById(R.id.chatImg);
        exploreImg=findViewById(R.id.exploreImg);
        searchImg=findViewById(R.id.searchImg);
        chatBrg=findViewById(R.id.chatBrg);
        exploreBrg=findViewById(R.id.exploreBrg);
        recentBrg=findViewById(R.id.recentBrg);
    }
}