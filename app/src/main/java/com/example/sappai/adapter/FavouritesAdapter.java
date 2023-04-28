package com.example.sappai.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sappai.R;
import com.example.sappai.model.Favourites;

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

        holder.descripTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return favourites.size();
    }

    public class ViewHodel extends RecyclerView.ViewHolder{

        ImageView iconImg;
        TextView titleTv,descripTv;
        public ViewHodel(@NonNull View itemView) {
            super(itemView);
            iconImg=itemView.findViewById(R.id.iconImg);
            titleTv=itemView.findViewById(R.id.titleTv);
            descripTv=itemView.findViewById(R.id.descripTv);
        }
    }
}
