package com.sapai_4.ai.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sapai_4.ai.Detail_Recent_Activity;
import com.sapai_4.ai.R;
import com.sapai_4.ai.model.RecentCopyModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.ViewHolder> {
    private ArrayList<RecentCopyModel> recentList;
    private Context context;

    public RecentAdapter(ArrayList<RecentCopyModel> recentList, Context context) {
        this.recentList = recentList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recent,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecentCopyModel message = recentList.get(position);
        int lastItem=recentList.get(position).getListMessage().size();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String dateString = format.format(message.getTimeCreateChat());
        holder.timeTv.setText(dateString);
        holder.contentTv.setText(message.getListMessage().get(0).getMessage());
        holder.recentLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Detail_Recent_Activity.class);
                intent.putExtra("time", dateString);
                intent.putExtra("timeDate",message.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout recentLinear;
        TextView timeTv,contentTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recentLinear=itemView.findViewById(R.id.recentLinear);
            timeTv=itemView.findViewById(R.id.timeTv);
            contentTv=itemView.findViewById(R.id.contentTv);

        }
    }
}
