package com.sapai_4.ai.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sapai_4.ai.model.MessageModel;
import com.sapai_4.ai.R;

import java.util.List;

public class GenarateAdapter extends RecyclerView.Adapter<GenarateAdapter.MyViewHolder> {
    List<MessageModel> messageList;
    Context context;

    public GenarateAdapter(List<MessageModel> messageList, Context context) {
        this.messageList = messageList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View chatView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_genarate_recyclerview,null);
        GenarateAdapter.MyViewHolder myViewHolder = new MyViewHolder(chatView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MessageModel message = messageList.get(position);
        if(message.getSentBy().equals(MessageModel.SENT_BY_ME)){

        }else{
            if(message.getMessage().trim().equals("Typing...".trim())){
                holder.copyLinear.setVisibility(View.GONE);
            }else{
                holder.copyLinear.setVisibility(View.VISIBLE);
            }

            holder.leftTextView.setText(message.getMessage());
            holder.copyLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClipboardManager clipboard = (ClipboardManager) view.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("text", message.getMessage());
                    clipboard.setPrimaryClip(clip);
                    clipboard.setPrimaryClip(clip);
                    SharedPreferences sharedPreferences = context.getSharedPreferences("checkCopy",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("check", 1);
                    editor.apply();
                    Intent intent = new Intent("checkCopy");
                    intent.putExtra("data", "1");
                    context.sendBroadcast(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView leftTextView;
        LinearLayout copyLinear;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            leftTextView = itemView.findViewById(R.id.resultTv);
            copyLinear=itemView.findViewById(R.id.copyLinear);
        }
    }
}
