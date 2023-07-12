package com.sapai_4.ai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sapai_4.ai.model.ToneOfVoiceModel;
import com.sapai_4.ai.R;

import java.util.List;

public class ToneOfVoiceAdapter extends RecyclerView.Adapter<ToneOfVoiceAdapter.MyViewHolder> {
    List<ToneOfVoiceModel> listToneOfView;
    Context context;

    public ToneOfVoiceAdapter(List<ToneOfVoiceModel> listToneOfView, Context context) {
        this.listToneOfView = listToneOfView;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View toneOfVoiceView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tone_of_voice_rcv,null);
        return new MyViewHolder(toneOfVoiceView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ToneOfVoiceModel toneOfVoiceModel=listToneOfView.get(position);
        holder.toneOfVoiceTv.setText(toneOfVoiceModel.getToneOfVoice());
    }

    @Override
    public int getItemCount() {
        return listToneOfView.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView toneOfVoiceTv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            toneOfVoiceTv=itemView.findViewById(R.id.toneOfVoiceTv);
        }
    }
}
