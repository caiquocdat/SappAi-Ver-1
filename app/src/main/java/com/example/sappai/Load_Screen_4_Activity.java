package com.example.sappai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Load_Screen_4_Activity extends AppCompatActivity {
    LinearLayout freeLinear,payLinear;
    Drawable selectedItem,unSelectItem ;
    TextView freeTv,skipTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_screen4);
        mapping();
        unSelectItem=getResources().getDrawable(R.drawable.shape_unselecte_package);
        selectedItem = getResources().getDrawable(R.drawable.shape_selected_package);
        freeLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                freeLinear.setBackground(selectedItem);
                payLinear.setBackground(unSelectItem);
                freeTv.setTextColor(Color.argb(255,255,255,255));
            }
        });
        payLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payLinear.setBackground(selectedItem);
                freeLinear.setBackground(unSelectItem);
                freeTv.setTextColor(Color.parseColor("#0073F7"));
            }
        });
        skipTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Load_Screen_4_Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void mapping() {
        freeLinear=findViewById(R.id.freeLinear);
        payLinear=findViewById(R.id.payLinear);
        freeTv=findViewById(R.id.freeTv);
        skipTv=findViewById(R.id.skipTv);
    }

}