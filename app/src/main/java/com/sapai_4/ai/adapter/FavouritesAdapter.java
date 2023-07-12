package com.sapai_4.ai.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sapai_4.ai.R;
import com.sapai_4.ai.Adverisements_Activity;
import com.sapai_4.ai.Apology_Activity;
import com.sapai_4.ai.Birthday_Activity;
import com.sapai_4.ai.Company_Bio_Activity;
import com.sapai_4.ai.Content_Write_Activity;
import com.sapai_4.ai.ConversationActivity;
import com.sapai_4.ai.Diet_Plan_Activity;
import com.sapai_4.ai.Email_Activity;
import com.sapai_4.ai.Email_Subject_Activity;
import com.sapai_4.ai.Explain_Code_Activity;
import com.sapai_4.ai.Fight_Activity;
import com.sapai_4.ai.ImproveActivity;
import com.sapai_4.ai.Improve_Email_Activity;
import com.sapai_4.ai.Instagram_Activity;
import com.sapai_4.ai.Into_Tweet_Activity;
import com.sapai_4.ai.Invitation_Activity;
import com.sapai_4.ai.Job_Post_Activity;
import com.sapai_4.ai.Linkedin_Post_Activity;
import com.sapai_4.ai.LyricsActivity;
import com.sapai_4.ai.Name_Generator_Activity;
import com.sapai_4.ai.PoemActivity;
import com.sapai_4.ai.Recipe_Activity;
import com.sapai_4.ai.Sentence_Activity;
import com.sapai_4.ai.Short_Movie_Activity;
import com.sapai_4.ai.Slogan_Activity;
import com.sapai_4.ai.Speech_Activity;
import com.sapai_4.ai.Story_Activity;
import com.sapai_4.ai.Summarize_Activity;
import com.sapai_4.ai.Tell_Joke_Activity;
import com.sapai_4.ai.Tiktok_Activity;
import com.sapai_4.ai.To_Emoji_Activity;
import com.sapai_4.ai.TranslateActivity;
import com.sapai_4.ai.Tweet_Activity;
import com.sapai_4.ai.Up_Line_Activity;
import com.sapai_4.ai.Viral_Video_Activity;
import com.sapai_4.ai.Words_Activity;
import com.sapai_4.ai.Write_Code_Activity;
import com.sapai_4.ai.model.Favourites;


import java.util.ArrayList;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.ViewHodel>  {

//    private Context context;
//    int singleData;
    private ArrayList<Favourites> favourites;
    private Context context;

//    SQLiteDatabase sqLiteDatabase;


    public FavouritesAdapter(ArrayList<Favourites> favourites, Context context) {
        this.favourites = favourites;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favourite_rcv,parent,false);
        return new ViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodel holder, int position) {
        holder.titleTv.setText(favourites.get(position).getTitle());
        holder.descripTv.setText(favourites.get(position).getDescrip());
        String activity=holder.titleTv.getText().toString().trim();
        holder.itemLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activity.equalsIgnoreCase("Write a paragraph")) {
                    Intent intent = new Intent(context, Content_Write_Activity.class);
                    context.startActivity(intent);
                } else if (activity.equalsIgnoreCase("Summarize (TL;DR)")) {
                    Intent intent = new Intent(context, Summarize_Activity.class);
                    context.startActivity(intent);
                } else if (activity.equalsIgnoreCase("Improve")) {
                    Intent intent = new Intent(context, ImproveActivity.class);
                    context.startActivity(intent);
                } else if (activity.equalsIgnoreCase("Translate")) {
                    Intent intent = new Intent(context, TranslateActivity.class);
                    context.startActivity(intent);
                } else if (activity.equalsIgnoreCase("Lyrics")) {
                    Intent intent = new Intent(context, LyricsActivity.class);
                    context.startActivity(intent);
                } else if (activity.equalsIgnoreCase("Poem")) {
                    Intent intent = new Intent(context, PoemActivity.class);
                    context.startActivity(intent);
                } else if (activity.equalsIgnoreCase("Story")) {
                    Intent intent = new Intent(context, Story_Activity.class);
                    context.startActivity(intent);
                } else if (activity.equalsIgnoreCase("Short Movie")) {
                    Intent intent = new Intent(context, Short_Movie_Activity.class);
                    context.startActivity(intent);
                } else if (activity.equalsIgnoreCase("Company Bio")) {
                    Intent intent = new Intent(context, Company_Bio_Activity.class);
                    context.startActivity(intent);
                } else if (activity.equalsIgnoreCase("Name Generator")) {
                    Intent intent = new Intent(context, Name_Generator_Activity.class);
                    context.startActivity(intent);
                } else if (activity.equalsIgnoreCase("Slogan")) {
                    Intent intent = new Intent(context, Slogan_Activity.class);
                    context.startActivity(intent);
                } else if (activity.equalsIgnoreCase("Advertisements")) {
                    Intent intent = new Intent(context, Adverisements_Activity.class);
                    context.startActivity(intent);
                } else if (activity.equalsIgnoreCase("Job Post")) {
                    Intent intent = new Intent(context, Job_Post_Activity.class);
                    context.startActivity(intent);
                } else if (activity.equalsIgnoreCase("Birthday")) {
                    Intent intent = new Intent(context, Birthday_Activity.class);
                    context.startActivity(intent);
                } else if (activity.equalsIgnoreCase("Apology")) {
                    Intent intent = new Intent(context, Apology_Activity.class);
                    context.startActivity(intent);
                } else if (activity.equalsIgnoreCase("Invitation")) {
                    Intent intent = new Intent(context, Invitation_Activity.class);
                    context.startActivity(intent);
                } else if (activity.equalsIgnoreCase("Pick Up Line")) {
                    Intent intent = new Intent(context, Up_Line_Activity.class);
                    context.startActivity(intent);
                } else if (activity.equalsIgnoreCase("Speech")) {
                    Intent intent = new Intent(context, Speech_Activity.class);
                    context.startActivity(intent);
                } else if (activity.equalsIgnoreCase("Email")) {
                    Intent intent = new Intent(context, Email_Activity.class);
                    context.startActivity(intent);
                } else if (activity.equalsIgnoreCase("Write Email Subject")) {
                    Intent intent = new Intent(context, Email_Subject_Activity.class);
                    context.startActivity(intent);
                } else if (activity.equalsIgnoreCase("Improve Email")) {
                    Intent intent = new Intent(context, Improve_Email_Activity.class);
                    context.startActivity(intent);
                } else if (activity.equalsIgnoreCase("Tweet")) {
                    Intent intent = new Intent(context, Tweet_Activity.class);
                    context.startActivity(intent);
                } else if (activity.equalsIgnoreCase("Turn into Tweet")) {
                    Intent intent = new Intent(context, Into_Tweet_Activity.class);
                    context.startActivity(intent);
                } else if (activity.equalsIgnoreCase("Linkedin Post")) {
                    Intent intent = new Intent(context, Linkedin_Post_Activity.class);
                    context.startActivity(intent);
                } else if (activity.equalsIgnoreCase("Instagram Caption")) {
                    Intent intent = new Intent(context, Instagram_Activity.class);
                    context.startActivity(intent);
                } else if (activity.equalsIgnoreCase("Tiktok Captions")) {
                    Intent intent = new Intent(context, Tiktok_Activity.class);
                    context.startActivity(intent);
                } else if (activity.equalsIgnoreCase("Viral Video Ideas")) {
                    Intent intent = new Intent(context, Viral_Video_Activity.class);
                    context.startActivity(intent);
                } else if (activity.equalsIgnoreCase("Write Code")) {
                    Intent intent = new Intent(context, Write_Code_Activity.class);
                    context.startActivity(intent);
                } else if (activity.equalsIgnoreCase("Explain Code")) {
                    Intent intent = new Intent(context, Explain_Code_Activity.class);
                    context.startActivity(intent);
                } else if (activity.equalsIgnoreCase("Recipe")) {
                    Intent intent = new Intent(context, Recipe_Activity.class);
                    context.startActivity(intent);
                } else if (activity.equalsIgnoreCase("Diet Plan")) {
                    Intent intent = new Intent(context, Diet_Plan_Activity.class);
                    context.startActivity(intent);
                } else if (activity.equalsIgnoreCase("Movies to Emoji")) {
                    Intent intent = new Intent(context, To_Emoji_Activity.class);
                    context.startActivity(intent);
                } else if (activity.equalsIgnoreCase("Tell Joke")) {
                    Intent intent = new Intent(context, Tell_Joke_Activity.class);
                    context.startActivity(intent);
                } else if (activity.equalsIgnoreCase("Complete Sentence")) {
                    Intent intent = new Intent(context, Sentence_Activity.class);
                    context.startActivity(intent);
                } else if (activity.equalsIgnoreCase("Make Them Fight")) {
                    Intent intent = new Intent(context, Fight_Activity.class);
                    context.startActivity(intent);
                } else if (activity.equalsIgnoreCase("Create Conversation")) {
                    Intent intent = new Intent(context, ConversationActivity.class);
                    context.startActivity(intent);
                } else if (activity.equalsIgnoreCase("Made Up Words")) {
                    Intent intent = new Intent(context, Words_Activity.class);
                    context.startActivity(intent);
                }
            }
        });

        byte[] imageBytes = favourites.get(position).getImage();

//        byte[] imageBytes = TEST;
//        Toast.makeText(context, imageBytes+"-"+TEST, Toast.LENGTH_SHORT).show();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
//        Toast.makeText(context, ""+bitmap, Toast.LENGTH_SHORT).show();
//        Toast.makeText(context, imageBytes.length+""+imageBytes, Toast.LENGTH_SHORT).show();
        if (imageBytes != null) {
            Glide.with(context).load(imageBytes).into(holder.iconImg);
        }

//        if (imageBytes != null && imageBytes.length > 0) {
//            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
//            holder.iconImg.setImageBitmap(bitmap);
//            // Thực hiện các thao tác khác với bitmap ở đây.
////            Toast.makeText(context, ""+bitmap, Toast.LENGTH_SHORT).show();
//
//
//
//        } else {
//            // Hiển thị thông báo lỗi hoặc xử lý vấn đề khác ở đây.
//
//        }



    }

    @Override
    public int getItemCount() {
        return favourites.size();
    }

    public class ViewHodel extends RecyclerView.ViewHolder{

        ImageView iconImg;
        TextView titleTv,descripTv;
        LinearLayout itemLinear;
        public ViewHodel(@NonNull View itemView) {
            super(itemView);
            iconImg=itemView.findViewById(R.id.iconImg);
            titleTv=itemView.findViewById(R.id.titleTv);
            descripTv=itemView.findViewById(R.id.descripTv);
            itemLinear=itemView.findViewById(R.id.itemLinear);
        }
    }
}
