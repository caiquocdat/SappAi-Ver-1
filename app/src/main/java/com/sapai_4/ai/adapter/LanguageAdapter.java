package com.sapai_4.ai.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sapai_4.ai.api.OnItemClickListener;
import com.sapai_4.ai.R;
import com.sapai_4.ai.model.LanguageModel;

import java.util.ArrayList;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.ViewHodel>  {

//    private Context context;
//    int singleData;
    private ArrayList<LanguageModel> languageCode;
    private OnItemClickListener onItemClickListener;
    private int selectedItemIndex = -1;
    private Context context;
    private boolean isChecked;

//    SQLiteDatabase sqLiteDatabase;


    public LanguageAdapter(ArrayList<LanguageModel> languageCode, Context context) {
        this.languageCode = languageCode;
        this.context = context;
        SharedPreferences sharedPreferences = context.getSharedPreferences("Check_click_language", Context.MODE_PRIVATE);SharedPreferences.Editor editor = sharedPreferences.edit();
        int value = sharedPreferences.getInt("value", 0);
        if (!languageCode.isEmpty()) {
            selectedItemIndex = value;
        }
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_language_code_rcv,parent,false);

        return new ViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodel holder, int position) {
        holder.languageTv.setText(languageCode.get(position).getLanguage());
        if (selectedItemIndex == position) {
            holder.checkImg.setImageResource(R.drawable.item_checked);
        } else {
            holder.checkImg.setImageResource(R.drawable.item_uncheck);
        }




////        holder.descripTv.setText(favourites.get(position).getDescrip());
////        String activity=holder.titleTv.getText().toString().trim();
//        holder.languageLinear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(),R.drawable.item_checked);
//                holder.checkImg.setImageBitmap(bitmap);
//            }
//        });


//        byte[] imageBytes = favourites.get(position).getImage();

//        byte[] imageBytes = TEST;
//        Toast.makeText(context, imageBytes+"-"+TEST, Toast.LENGTH_SHORT).show();
//        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
//        Toast.makeText(context, ""+bitmap, Toast.LENGTH_SHORT).show();
//        Toast.makeText(context, imageBytes.length+""+imageBytes, Toast.LENGTH_SHORT).show();
//        if (imageBytes != null) {
//            Glide.with(context).load(imageBytes).into(holder.iconImg);
//        }

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
        return languageCode.size();
    }

    public class ViewHodel extends RecyclerView.ViewHolder{

        ImageView checkImg;
        TextView languageTv;
        LinearLayout languageLinear;
        public ViewHodel(@NonNull View itemView) {
            super(itemView);
            checkImg=itemView.findViewById(R.id.checkImg);
            languageTv=itemView.findViewById(R.id.languageTv);
            languageLinear=itemView.findViewById(R.id.languageLinear);
            languageLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    SharedPreferences sharedPreferences = context.getSharedPreferences("Check_click_language", Context.MODE_PRIVATE);SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("value", position); // Thay "key" và "value" bằng các giá trị tương ứng của bạn
                    editor.apply();
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(position);
                    }
                    if (position != RecyclerView.NO_POSITION) {
                        selectedItemIndex = position;
                        onItemClickListener.onItemClick(position);
                        notifyDataSetChanged();
                    }
                    Intent intent = new Intent("language");
                    intent.putExtra("data", languageTv.getText().toString());
                    intent.putExtra("check", 1);
                    context.sendBroadcast(intent);
//                    isChecked = !isChecked;
//                    if (isChecked) {
//                        checkImg.setImageResource(R.drawable.item_checked);
//                    } else {
//                        checkImg.setImageResource(R.drawable.item_uncheck);
//                    }
                }
            });
        }
    }
}
