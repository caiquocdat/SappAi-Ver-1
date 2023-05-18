package com.example.sappai.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sappai.ChatActivity;
import com.example.sappai.Content_Write_Activity;
import com.example.sappai.MainActivity;
import com.example.sappai.ProfileActivity;
import com.example.sappai.R;

import java.util.Date;


public class ChatFragment extends Fragment {
    ImageView sendImg;
    EditText chatEdt;
    LinearLayout explainTextLinear, listQuestionLinear;
    ScrollView listQuestionScr;

    TextView explainPhysicsTv, explainWormholesTv, writeTweetTv, writePoemTv, writeSongTv, translateKoreanTv, writeEmailTv, recipesPotatoTv, mathProblemTv, actTeacherTv, actInterviewerTv, historySantaTv, helpElectronicsTv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        sendImg = view.findViewById(R.id.sendImg);
        chatEdt = view.findViewById(R.id.chatEdt);
        explainPhysicsTv = view.findViewById(R.id.explainPhysicTv);
        explainWormholesTv = view.findViewById(R.id.explainWormholesTv);
        writeTweetTv = view.findViewById(R.id.writeTweetTv);
        writePoemTv = view.findViewById(R.id.writePoemTv);
        writeSongTv = view.findViewById(R.id.writeSongTv);
        translateKoreanTv = view.findViewById(R.id.translateKoreanTv);
        writeEmailTv = view.findViewById(R.id.writeEmailTv);
        recipesPotatoTv = view.findViewById(R.id.recipesPotatoTv);
        mathProblemTv = view.findViewById(R.id.mathProblemTv);
        actTeacherTv = view.findViewById(R.id.actTeacherTv);
        actInterviewerTv = view.findViewById(R.id.actInterviewerTv);
        historySantaTv = view.findViewById(R.id.historySantaTv);
        helpElectronicsTv = view.findViewById(R.id.helpElectronicsTv);
        explainTextLinear = view.findViewById(R.id.explainText);
        listQuestionLinear = view.findViewById(R.id.listQuestionLinear);
        listQuestionScr = view.findViewById(R.id.listQuestionScr);


        chatEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (chatEdt.getText().length() > 0) {
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_chatscreen_send);
                    sendImg.setImageBitmap(bitmap);
                } else {
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_chatscreen_send_gray);
                    sendImg.setImageBitmap(bitmap);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        chatEdt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {

                }else{
                    if (chatEdt.getText().length() > 0) {
                        chatEdt.clearFocus();
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_chatscreen_send);
                        sendImg.setImageBitmap(bitmap);
                    } else {
                        chatEdt.clearFocus();
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_chatscreen_send_gray);
                        sendImg.setImageBitmap(bitmap);
                    }
                }

            }
        });
        listQuestionScr.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                chatEdt.clearFocus();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(chatEdt.getWindowToken(), 0);
                return false;
            }
        });
        explainTextLinear.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                chatEdt.clearFocus();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(chatEdt.getWindowToken(), 0);
                return false;
            }
        });


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
                intent.putExtra("date", "" + new Date());
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
                if (chatEdt.getText().toString().length() > 0) {
                    Intent intent = new Intent(getContext(), ChatActivity.class);
                    intent.putExtra("content", chatEdt.getText().toString().trim());
                    startActivity(intent);
                    chatEdt.setText("");
                } else {
//                    View customDialogView = inflater.inflate(R.layout.custom_alert_dialog, null);
//                    View dialogBackground = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_background, null);
//
//                    ViewGroup rootLayout = requireActivity().findViewById(android.R.id.content);
//                    rootLayout.addView(dialogBackground);
//
//                    dialogBackground.setVisibility(View.VISIBLE);
//                    dialogBackground.setAlpha(0.0f);
//                    dialogBackground.animate().alpha(1.0f).setDuration(300).start();
//
//
//                    // Tạo đối tượng AlertDialog.Builder và thiết lập thông tin cho AlertDialog
//                    AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
//                    builder.setView(customDialogView);
//
//                    final AlertDialog alertDialog = builder.create();
//                    alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//
//                    customDialogView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            dialogBackground.animate().alpha(0.0f).setDuration(300).withEndAction(new Runnable() {
//                                @Override
//                                public void run() {
//                                    rootLayout.removeView(dialogBackground);
//                                }
//                            }).start();
//                            alertDialog.dismiss();
//                        }
//                    });
//                    alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                        @Override
//                        public void onCancel(DialogInterface dialogInterface) {
//                            dialogBackground.animate().alpha(0.0f).setDuration(300).withEndAction(new Runnable() {
//                                @Override
//                                public void run() {
//                                    rootLayout.removeView(dialogBackground);
//                                }
//                            }).start();
//                            alertDialog.dismiss();
//                        }
//                    });
//
//                    alertDialog.show();

                }
            }
        });


        return view;

    }


}