package com.example.sappai.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sappai.R;
import com.example.sappai.model.MessageModel;

import java.util.List;

public class GenarateAdapter extends RecyclerView.Adapter<GenarateAdapter.MyViewHolder> {
    List<MessageModel> messageList;
    public GenarateAdapter(List<MessageModel> messageList) {
        this.messageList = messageList;
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
            holder.leftTextView.setText(message.getMessage());
            holder.copyLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClipboardManager clipboard = (ClipboardManager) view.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("text", message.getMessage());
                    clipboard.setPrimaryClip(clip);
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(view.getContext(), "Text copied", Toast.LENGTH_SHORT).show();
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
