package com.example.sappai.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sappai.R;
import com.example.sappai.data.DBRecentCopyManager;
import com.example.sappai.model.MessageCopyModel;
import com.example.sappai.model.MessageModel;

import java.util.List;

public class MessageRecentAdapter extends RecyclerView.Adapter<MessageRecentAdapter.MyViewHolder> {

    List<MessageCopyModel> messageList;
    Context context;
    DBRecentCopyManager dbManager;

    public MessageRecentAdapter(List<MessageCopyModel> messageList, Context context) {
        this.messageList = messageList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View chatView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_recyclerview_layout, null);
        MyViewHolder myViewHolder = new MyViewHolder(chatView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MessageCopyModel message = messageList.get(position);
        if (message.getSentBy().equals(MessageModel.SENT_BY_ME)) {
            holder.leftChatView.setVisibility(View.GONE);
            holder.rightChatView.setVisibility(View.VISIBLE);
            holder.rightTextView.setText(message.getMessage());
            holder.copyUserImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClipboardManager clipboard = (ClipboardManager) view.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("text", message.getMessage());
                    clipboard.setPrimaryClip(clip);
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(view.getContext(), "Text copied", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            holder.rightChatView.setVisibility(View.GONE);
            holder.leftChatView.setVisibility(View.VISIBLE);
            holder.leftTextView.setText(message.getMessage());
            holder.copyAIImg.setOnClickListener(new View.OnClickListener() {
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

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout leftChatView, rightChatView;

        TextView leftTextView, rightTextView;
        ImageView copyAIImg, copyUserImg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            leftChatView = itemView.findViewById(R.id.left_chat_view);
            rightChatView = itemView.findViewById(R.id.right_chat_view);
            leftTextView = itemView.findViewById(R.id.left_chat_text_view);
            rightTextView = itemView.findViewById(R.id.right_chat_text_view);
            copyUserImg = itemView.findViewById(R.id.copyUserImg);
            copyAIImg = itemView.findViewById(R.id.copyAIImg);
        }
    }
}
