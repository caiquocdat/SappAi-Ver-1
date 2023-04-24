package com.example.sappai.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sappai.ChatActivity;
import com.example.sappai.Content_Write_Activity;
import com.example.sappai.MainActivity;
import com.example.sappai.ProfileActivity;
import com.example.sappai.R;


public class ChatFragment extends Fragment {
    ImageView sendImg;
    EditText chatEdt;
    TextView explainPhysicsTv,explainWormholesTv,writeTweetTv,writePoemTv,writeSongTv,translateKoreanTv,writeEmailTv,recipesPotatoTv,mathProblemTv,actTeacherTv,actInterviewerTv,historySantaTv,helpElectronicsTv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        sendImg=view.findViewById(R.id.sendImg);
        chatEdt=view.findViewById(R.id.chatEdt);
        explainPhysicsTv=view.findViewById(R.id.explainPhysicTv);
        explainWormholesTv=view.findViewById(R.id.explainWormholesTv);
        writeTweetTv=view.findViewById(R.id.writeTweetTv);
        writePoemTv=view.findViewById(R.id.writePoemTv);
        writeSongTv=view.findViewById(R.id.writeSongTv);
        translateKoreanTv=view.findViewById(R.id.translateKoreanTv);
        writeEmailTv=view.findViewById(R.id.writeEmailTv);
        recipesPotatoTv=view.findViewById(R.id.recipesPotatoTv);
        mathProblemTv=view.findViewById(R.id.mathProblemTv);
        actTeacherTv=view.findViewById(R.id.actTeacherTv);
        actInterviewerTv=view.findViewById(R.id.actInterviewerTv);
        historySantaTv=view.findViewById(R.id.historySantaTv);
        helpElectronicsTv=view.findViewById(R.id.helpElectronicsTv);



        explainPhysicsTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ChatActivity.class);
                intent.putExtra("content", explainPhysicsTv.getText().toString().trim());
                startActivity(intent);
            }
        });
        explainWormholesTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ChatActivity.class);
                intent.putExtra("content", explainWormholesTv.getText().toString().trim());
                startActivity(intent);
            }
        });

        writeTweetTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ChatActivity.class);
                intent.putExtra("content", writeTweetTv.getText().toString().trim());
                startActivity(intent);
            }
        });
        writePoemTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ChatActivity.class);
                intent.putExtra("content", writePoemTv.getText().toString().trim());
                startActivity(intent);
            }
        });
        writeSongTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ChatActivity.class);
                intent.putExtra("content", writeSongTv.getText().toString().trim());
                startActivity(intent);
            }
        });
        translateKoreanTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ChatActivity.class);
                intent.putExtra("content", translateKoreanTv.getText().toString().trim());
                startActivity(intent);
            }
        });
        writeEmailTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ChatActivity.class);
                intent.putExtra("content", writeEmailTv.getText().toString().trim());
                startActivity(intent);
            }
        });
        recipesPotatoTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ChatActivity.class);
                intent.putExtra("content", recipesPotatoTv.getText().toString().trim());
                startActivity(intent);
            }
        });
        mathProblemTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ChatActivity.class);
                intent.putExtra("content", mathProblemTv.getText().toString().trim());
                startActivity(intent);
            }
        });
       actTeacherTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ChatActivity.class);
                intent.putExtra("content", actTeacherTv.getText().toString().trim());
                startActivity(intent);
            }
        });
       actInterviewerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ChatActivity.class);
                intent.putExtra("content", actInterviewerTv.getText().toString().trim());
                startActivity(intent);
            }
        });
        historySantaTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ChatActivity.class);
                intent.putExtra("content", historySantaTv.getText().toString().trim());
                startActivity(intent);
            }
        });
        helpElectronicsTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ChatActivity.class);
                intent.putExtra("content", helpElectronicsTv.getText().toString().trim());
                startActivity(intent);
            }
        });
        sendImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(chatEdt.getText().toString().length()>1) {
                    Intent intent = new Intent(getContext(), ChatActivity.class);
                    intent.putExtra("content", chatEdt.getText().toString().trim());
                    startActivity(intent);
                    chatEdt.setText("");
                }else{
                    View customDialogView = inflater.inflate(R.layout.custom_alert_dialog, null);


                    // Tạo đối tượng AlertDialog.Builder và thiết lập thông tin cho AlertDialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                    builder.setView(customDialogView);
                    builder.setCancelable(false);
                    final AlertDialog alertDialog = builder.create();

                    customDialogView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });

                    alertDialog.show();
                }
            }
        });

        return view;

    }
}