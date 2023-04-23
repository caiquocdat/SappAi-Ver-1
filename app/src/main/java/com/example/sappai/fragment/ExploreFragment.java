package com.example.sappai.fragment;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sappai.R;

import java.util.ArrayList;
import java.util.List;


public class ExploreFragment extends Fragment {

    LinearLayout favourites_Brg,all_item_linear;
    List<TextView> clickedTextViews = new ArrayList<>();
     HorizontalScrollView horizontalScrollView;
     Drawable selectedItem,unSelectItem ;
    final String textViewFavourites = "Favourites";
    final String textViewAll = "All";
    TextView item_favourites_Tv,item_all_Tv,item_content_Tv,item_business_Tv,item_personal_Tv,item_email_Tv,item_social_Tv,item_code_Tv,item_food_Tv,item_entertainment_Tv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_explore, container, false);



        //đổi màu item khi click
        unSelectItem=getResources().getDrawable(R.drawable.shape_explore_item_top);
        selectedItem = getResources().getDrawable(R.drawable.shape_explore_item_top_selected);
        item_all_Tv=view.findViewById(R.id.item_All_Tv);
        item_content_Tv=view.findViewById(R.id.item_content_Tv);
        item_code_Tv=view.findViewById(R.id.item_content_Tv);
        item_business_Tv=view.findViewById(R.id.item_business_Tv);
        item_personal_Tv=view.findViewById(R.id.item_personal_Tv);
        item_email_Tv=view.findViewById(R.id.item_social_Tv);
        item_code_Tv=view.findViewById(R.id.item_code_Tv);
        item_social_Tv=view.findViewById(R.id.item_social_Tv);
        item_food_Tv=view.findViewById(R.id.item_food_Tv);
        item_entertainment_Tv=view.findViewById(R.id.item_entertainment_Tv);
        item_favourites_Tv=view.findViewById(R.id.item_favourites_Tv);
        all_item_linear=view.findViewById(R.id.all_item_linear);
        horizontalScrollView=view.findViewById(R.id.hr_scroll_view);

        //set fragment mặc định
        AllFragment fragment2=new AllFragment();
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content1,fragment2,"tag");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();




        //thay đổi màu cho horizont
        LinearLayout linearLayout = (LinearLayout) horizontalScrollView.getChildAt(0);
//        for (int i = 0; i < linearLayout.getChildCount(); i++) {
//            View childView = linearLayout.getChildAt(i);
//            if (childView instanceof TextView) {
//                childView.setBackground(unSelectItem);
//            }
//        }
        setOnClickListenerForTextViews(linearLayout);
        return view;
    }
    public void setOnClickListenerForTextViews(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childView = viewGroup.getChildAt(i);

            if (childView instanceof TextView) {
                final TextView textView = (TextView) childView;
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String clickedFavouritesTv = ((TextView) v).getText().toString();
                        String clickedAllTv = ((TextView) v).getText().toString();
                        if (clickedFavouritesTv.equals(textViewFavourites)) {
                            //lấy index
                            int itemIndex=viewGroup.indexOfChild(v);
                            View seletedItem= viewGroup.getChildAt(itemIndex);
                            int start=seletedItem.getLeft();
                            horizontalScrollView.smoothScrollTo(start,0);
                            item_all_Tv.setBackground(unSelectItem);
                            // Xử lý sự kiện click của TextView cần bắt sự kiện ở đây
                            CategoryFragment fragment2=new CategoryFragment();
                            FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.content1,fragment2,"tag");
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        } else if(clickedAllTv.equals(textViewAll)){
                            //lấy index
                            int itemIndex=viewGroup.indexOfChild(v);
                            View seletedItem= viewGroup.getChildAt(itemIndex);
                            int start=seletedItem.getLeft();
                            horizontalScrollView.smoothScrollTo(start,0);


                            item_all_Tv.setBackground(unSelectItem);
                            // Xử lý sự kiện click của các TextView khác ở đây
                            AllFragment fragment2=new AllFragment();
                            FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.content1,fragment2,"tag");
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                        for (TextView clickedTextView : clickedTextViews) {
                            clickedTextView.setBackground(unSelectItem);
                        }
                        // Thay đổi màu nền của TextView được click
                        v.setBackground(getResources().getDrawable(R.drawable.shape_explore_item_top_selected));

                        // Xử lý sự kiện click tại đây
                        clickedTextViews.add(textView);

//                        Toast.makeText(getContext(), ""+childView, Toast.LENGTH_SHORT).show();




                    }
                });
            } else if (childView instanceof ViewGroup) {
                setOnClickListenerForTextViews((ViewGroup) childView);
            }
        }
    }
}