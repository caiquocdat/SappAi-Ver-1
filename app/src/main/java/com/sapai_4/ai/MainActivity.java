package com.sapai_4.ai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sapai_4.ai.R;
import com.sapai_4.ai.fragment.ChatFragment;
import com.sapai_4.ai.fragment.explore.ExploreFragment;
import com.sapai_4.ai.fragment.RecentsFragment;

public class MainActivity extends AppCompatActivity {
    ImageView chatImg,exploreImg,searchImg,profileImg;
    LinearLayout chatBrg,exploreBrg,recentBrg;
    Drawable selectedItem,unSelectItem ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        ChatFragment fragment1= new ChatFragment();
        FragmentTransaction ft1=getSupportFragmentManager().beginTransaction();
        ft1.replace(R.id.content,fragment1,"");
        ft1.commit();
        unSelectItem=getResources().getDrawable(R.drawable.shape_item_bottom_bar);
        selectedItem = getResources().getDrawable(R.drawable.shape_item_bottom_bar_selected);
        mapping();



        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        chatBrg.setOnClickListener(new View.OnClickListener() {
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
        exploreBrg.setOnClickListener(new View.OnClickListener() {
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

        recentBrg.setOnClickListener(new View.OnClickListener() {
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
        profileImg=findViewById(R.id.profileImg);
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        // hoặc
        // finish();
        // System.exit(0);
    }
}