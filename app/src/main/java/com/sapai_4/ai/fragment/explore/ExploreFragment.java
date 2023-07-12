package com.sapai_4.ai.fragment.explore;

import android.graphics.drawable.Drawable;
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

import com.sapai_4.ai.R;

import java.util.ArrayList;
import java.util.List;


public class ExploreFragment extends Fragment {

    LinearLayout favourites_Brg,all_item_linear;
    List<TextView> clickedTextViews = new ArrayList<>();
     HorizontalScrollView horizontalScrollView;
     Drawable selectedItem,unSelectItem ;
    final String textViewFavourites = "Favourites";
    final String textViewAll = "All";
    final String textViewContent = "Content";
    final String textViewArtist = "Artist";
    final String textViewBusiness = "Business";
    final String textViewPersonal = "Personal";
    final String textViewEmail = "Email";
    final String textViewSocial = "Social";
    final String textViewCode = "Code";
    final String textViewFood = "Food";
    final String textViewEntertainment = "Entertainment";
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
                        String clickedItem = ((TextView) v).getText().toString();



                        int clickedIndex=viewGroup.indexOfChild(v);
                        if (clickedItem.equals(textViewFavourites)) {
                            //lấy index
                            int itemIndex=viewGroup.indexOfChild(v);
                            View seletedItem= viewGroup.getChildAt(itemIndex);
                            int start=seletedItem.getLeft();
                            Log.d("QuocDat", "onClick: "+itemIndex+"-"+start+"-"+viewGroup.getChildAt(itemIndex));
                            horizontalScrollView.smoothScrollTo(start,0);
                            item_all_Tv.setBackground(unSelectItem);
                            // Xử lý sự kiện click của TextView cần bắt sự kiện ở đây
                            CategoryFragment fragment2=new CategoryFragment();
                            FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.content1,fragment2,"tag");
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        } else if(clickedItem.equals(textViewAll)){
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
                        } else if(clickedItem.equals(textViewContent)){
                            int itemIndex=viewGroup.indexOfChild(v);
                            View seletedItem= viewGroup.getChildAt(itemIndex);
                            int start=seletedItem.getLeft();
                            horizontalScrollView.smoothScrollTo(start,0);
                            item_all_Tv.setBackground(unSelectItem);
                            ContentFragment fragment2=new ContentFragment();
                            FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.content1,fragment2,"tag");
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }else if(clickedItem.equals(textViewArtist)){
                            int itemIndex=viewGroup.indexOfChild(v);
                            View seletedItem= viewGroup.getChildAt(itemIndex);
                            int start=seletedItem.getLeft();
                            horizontalScrollView.smoothScrollTo(start,0);
                            item_all_Tv.setBackground(unSelectItem);
                            ArtistFragment fragment2=new ArtistFragment();
                            FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.content1,fragment2,"tag");
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();

                        }else if(clickedItem.equals(textViewBusiness)){
                            int itemIndex=viewGroup.indexOfChild(v);
                            View seletedItem= viewGroup.getChildAt(itemIndex);
                            int start=seletedItem.getLeft();
                            horizontalScrollView.smoothScrollTo(start,0);
                            item_all_Tv.setBackground(unSelectItem);
                            BusinessFragment fragment2=new BusinessFragment();
                            FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.content1,fragment2,"tag");
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();

                        }else if(clickedItem.equals(textViewPersonal)){
                            int itemIndex=viewGroup.indexOfChild(v);
                            View seletedItem= viewGroup.getChildAt(itemIndex);
                            int start=seletedItem.getLeft();
                            horizontalScrollView.smoothScrollTo(start,0);
                            item_all_Tv.setBackground(unSelectItem);
                            PersonalFragment fragment2=new PersonalFragment();
                            FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.content1,fragment2,"tag");
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }else if(clickedItem.equals(textViewEmail)){
                            int itemIndex=viewGroup.indexOfChild(v);
                            View seletedItem= viewGroup.getChildAt(itemIndex);
                            int start=seletedItem.getLeft();
                            horizontalScrollView.smoothScrollTo(start,0);
                            item_all_Tv.setBackground(unSelectItem);
                            EmailFragment fragment2=new EmailFragment();
                            FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.content1,fragment2,"tag");
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }else if(clickedItem.equals(textViewSocial)){
                            int itemIndex=viewGroup.indexOfChild(v);
                            View seletedItem= viewGroup.getChildAt(itemIndex);
                            int start=seletedItem.getLeft();
                            horizontalScrollView.smoothScrollTo(start,0);
                            item_all_Tv.setBackground(unSelectItem);
                            SocialFragment fragment2=new SocialFragment();
                            FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.content1,fragment2,"tag");
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();

                        }else if(clickedItem.equals(textViewCode)){
                            int itemIndex=viewGroup.indexOfChild(v);
                            View seletedItem= viewGroup.getChildAt(itemIndex);
                            int start=seletedItem.getLeft();
                            horizontalScrollView.smoothScrollTo(start,0);
                            item_all_Tv.setBackground(unSelectItem);
                            CodeFragment fragment2=new CodeFragment();
                            FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.content1,fragment2,"tag");
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();

                        }else if(clickedItem.equals(textViewFood)){
                            int itemIndex=viewGroup.indexOfChild(v);
                            View seletedItem= viewGroup.getChildAt(itemIndex);
                            int start=seletedItem.getLeft();
                            horizontalScrollView.smoothScrollTo(start,0);
                            item_all_Tv.setBackground(unSelectItem);
                            FoodFragment fragment2=new FoodFragment();
                            FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.content1,fragment2,"tag");
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();

                        }else if(clickedItem.equals(textViewEntertainment)){
                            int itemIndex=viewGroup.indexOfChild(v);
                            View seletedItem= viewGroup.getChildAt(itemIndex);
                            int start=seletedItem.getLeft();
                            horizontalScrollView.smoothScrollTo(start,0);
                            item_all_Tv.setBackground(unSelectItem);
                            EntertaimentFragment fragment2=new EntertaimentFragment();
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