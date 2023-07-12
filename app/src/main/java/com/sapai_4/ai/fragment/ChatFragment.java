package com.sapai_4.ai.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.AcknowledgePurchaseResponseListener;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.sapai_4.ai.ChatActivity;
import com.sapai_4.ai.Load_Screen_4_Activity;
import com.sapai_4.ai.R;
import com.sapai_4.ai.Security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ChatFragment extends Fragment {
    ImageView sendImg;
    EditText chatEdt;
    LinearLayout explainTextLinear, listQuestionLinear;
    ScrollView listQuestionScr;

    String respone, desWeek, desYear, sku;
    boolean isSuccess = false;
    String option = "year";
    private BillingClient billingClient;

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
        billingClient = BillingClient.newBuilder(getContext())
                .setListener(purchasesUpdatedListener)
                .enablePendingPurchases()
                .build();
        getWeek();
        getYear();


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

                } else {
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

                    if (desYear == null && desWeek == null) {
                        Intent intent = new Intent(getContext(), Load_Screen_4_Activity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getContext(), ChatActivity.class);
                        intent.putExtra("content", chatEdt.getText().toString().trim());
                        startActivity(intent);
                        chatEdt.setText("");
                    }
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

    private PurchasesUpdatedListener purchasesUpdatedListener = new PurchasesUpdatedListener() {
        @Override
        public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> Purchase) {

        }
    };

    void handlePurchases(Purchase purchase) {
//        for(Purchase purchase:purchases) {
//
//            final int index=subcribeItemIDs.indexOf(purchase.getSku());
//            //purchase found
//            if(index>-1) {
//
//                //if item is purchased
//                if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED)
//                {
//                    if (!verifyValidSignature(purchase.getOriginalJson(), purchase.getSignature())) {
//                        // Invalid purchase
//                        // show error to user
//                        Toast.makeText(getApplicationContext(), "Error : Invalid Purchase", Toast.LENGTH_SHORT).show();
//                        continue;//skip current iteration only because other items in purchase list must be checked if present
//                    }
//                    // else purchase is valid
//                    //if item is purchased/subscribed and not Acknowledged
//                    if (!purchase.isAcknowledged()) {
//                        AcknowledgePurchaseParams acknowledgePurchaseParams =
//                                AcknowledgePurchaseParams.newBuilder()
//                                        .setPurchaseToken(purchase.getPurchaseToken())
//                                        .build();
//
//                        billingClient.acknowledgePurchase(acknowledgePurchaseParams,
//                                new AcknowledgePurchaseResponseListener() {
//                                    @Override
//                                    public void onAcknowledgePurchaseResponse(BillingResult billingResult) {
//                                        if(billingResult.getResponseCode()==BillingClient.BillingResponseCode.OK){
//                                            //if purchase is acknowledged
//                                            //then saved value in preference
//                                            saveSubscribeItemValueToPref(subcribeItemIDs.get(index),true);
//                                            Toast.makeText(getApplicationContext(), subcribeItemIDs.get(index)+" Item Subscribed", Toast.LENGTH_SHORT).show();
//                                            notifyList();
//                                        }
//                                    }
//                                });
//
//                    }
//                    //else item is purchased and also acknowledged
//                    else {
//                        // Grant entitlement to the user on item purchase
//                        if(!getSubscribeItemValueFromPref(subcribeItemIDs.get(index))){
//                            saveSubscribeItemValueToPref(subcribeItemIDs.get(index),true);
//                            Toast.makeText(getApplicationContext(), subcribeItemIDs.get(index)+" Item Subscribed.", Toast.LENGTH_SHORT).show();
//                            notifyList();
//                        }
//                    }
//                }
//                //if purchase is pending
//                else if(  purchase.getPurchaseState() == Purchase.PurchaseState.PENDING)
//                {
//                    Toast.makeText(getApplicationContext(),
//                            subcribeItemIDs.get(index)+" Purchase is Pending. Please complete Transaction", Toast.LENGTH_SHORT).show();
//                }
//                //if purchase is refunded or unknown
//                else if( purchase.getPurchaseState() == Purchase.PurchaseState.UNSPECIFIED_STATE)
//                {
//                    //mark purchase false in case of UNSPECIFIED_STATE
//                    saveSubscribeItemValueToPref(subcribeItemIDs.get(index),false);
//                    Toast.makeText(getApplicationContext(), subcribeItemIDs.get(index)+" Purchase Status Unknown", Toast.LENGTH_SHORT).show();
//                    notifyList();
//                }
//            }
//
//        }
        // Verify the purchase.
        // Ensure entitlement was not already granted for this purchaseToken.
        // Grant entitlement to the user.

        ConsumeParams consumeParams =
                ConsumeParams.newBuilder()
                        .setPurchaseToken(purchase.getPurchaseToken())
                        .build();

        ConsumeResponseListener listener = new ConsumeResponseListener() {
            @Override
            public void onConsumeResponse(BillingResult billingResult, String purchaseToken) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    // Handle the success of the consume operation.
                }
            }
        };

        billingClient.consumeAsync(consumeParams, listener);
        if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
            if (!verifyValidSignature(purchase.getOriginalJson(), purchase.getSignature())) {
                // Invalid purchase
                // show error to user
//                Toast.makeText(getApplicationContext(), "Error : Invalid Purchase", Toast.LENGTH_SHORT).show();
                return;//skip current iteration only because other items in purchase list must be checked if present
            }
            if (!purchase.isAcknowledged()) {
                AcknowledgePurchaseParams acknowledgePurchaseParams =
                        AcknowledgePurchaseParams.newBuilder()
                                .setPurchaseToken(purchase.getPurchaseToken())
                                .build();
                billingClient.acknowledgePurchase(acknowledgePurchaseParams, acknowledgePurchaseResponseListener);
                isSuccess = true;
            } else {
                Toast.makeText(getContext(), "Already_Subscribed", Toast.LENGTH_SHORT).show();
            }
        }
        //else item is purchased and also acknowledged
        else if (purchase.getPurchaseState() == Purchase.PurchaseState.PENDING) {
            Toast.makeText(getContext(), "Subscription PENDING", Toast.LENGTH_SHORT).show();
        } else if (purchase.getPurchaseState() == Purchase.PurchaseState.UNSPECIFIED_STATE) {
            Toast.makeText(getContext(), "UNSPECIFIED_STATE", Toast.LENGTH_SHORT).show();
        }
    }

    AcknowledgePurchaseResponseListener acknowledgePurchaseResponseListener = new AcknowledgePurchaseResponseListener() {
        @Override
        public void onAcknowledgePurchaseResponse(@NonNull BillingResult billingResult) {
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                isSuccess = true;
                Toast.makeText(getContext(), "Subscribed", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private boolean verifyValidSignature(String signedData, String signature) {
        try {
            //for old playconsole
            // To get key go to Developer Console > Select your app > Development Tools > Services & APIs.
            //for new play console
            //To get key go to Developer Console > Select your app > Monetize > Monetization setup

            String base64Key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAi8bOfBgwB9A/lMS1Z+0dWDMcrHy/HYj5+OcXPH51UU+o/B0usbi3X3VnLp2EcP3rSXWQfPQuOuM1UqrZVzZtXVGtbcCaD2miXSgv0gxwuzOX3ocGbv3q9XLYCVa8xy2GbeuKfwGwbpoq//LwcgaGMWFFjx+HaABzPv8GkQkreLz8mrhymym4RhRXV2ygki6OHmgn+h54sC4tAwLQyfnWGBXj8fFyr0u/cX3ip6FYlfdRNYtAhFSUeM4Em76kK57I3WkRdbkDK4lh1473zhICeBqXHOs4RR2zTusPyfv3o+b+kuT0/WLEWuuBf6PrZcokusCYfs7rvHFs4Bw1lZqsdwIDAQAB";
            return Security.verifyPurchase(base64Key, signedData, signature);
        } catch (IOException e) {
            return false;
        }
    }

    private void getWeek() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                billingClient.startConnection(new BillingClientStateListener() {
                    @Override
                    public void onBillingServiceDisconnected() {

                    }

                    @Override
                    public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                        List<QueryProductDetailsParams.Product> productList = new ArrayList<>();
                        productList.add(QueryProductDetailsParams.Product.newBuilder()
                                .setProductId("com.sappai.weekly")
                                .setProductType(BillingClient.ProductType.SUBS)
                                .build()
                        );
                        QueryProductDetailsParams params = QueryProductDetailsParams.newBuilder()
                                .setProductList(productList)
                                .build();
                        billingClient.queryProductDetailsAsync(params, (billingResult1, productDetailsList) -> {
                            for (ProductDetails productDetails : productDetailsList) {
                                respone = productDetails.getSubscriptionOfferDetails().get(0).getPricingPhases()
                                        .getPricingPhaseList().get(0).getFormattedPrice();
                                sku = productDetails.getName();
                                String ds = productDetails.getDescription();
                                desWeek = sku + " " + ds + " " + " Price " + respone;
                                Log.d("test1", "onBillingSetupFinished: " + desWeek);

//                                String offerToken = productDetails.getSubscriptionOfferDetails()
//                                        .get(0).getOfferToken();
//                                List<BillingFlowParams.ProductDetailsParams> productDetailsParams = new ArrayList<>();
//                                productDetailsParams.add(BillingFlowParams.ProductDetailsParams.newBuilder()
//                                        .setProductDetails(productDetails)
//                                        .setOfferToken(offerToken)
//                                        .build()
//                                );
//                                BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
//                                        .setProductDetailsParamsList(productDetailsParams)
//                                        .build();
//                                billingClient.launchBillingFlow(IAPActivity.this, billingFlowParams);

                            }

                        });
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }


                            }
                        });
                    }
                });
            }
        });
    }

    private void getYear() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                billingClient.startConnection(new BillingClientStateListener() {
                    @Override
                    public void onBillingServiceDisconnected() {

                    }

                    @Override
                    public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                        List<QueryProductDetailsParams.Product> productList = new ArrayList<>();
                        productList.add(QueryProductDetailsParams.Product.newBuilder()
                                .setProductId("com.sappai.yearly")
                                .setProductType(BillingClient.ProductType.SUBS)
                                .build()
                        );
                        QueryProductDetailsParams params = QueryProductDetailsParams.newBuilder()
                                .setProductList(productList)
                                .build();
                        billingClient.queryProductDetailsAsync(params, (billingResult1, productDetailsList) -> {
                            for (ProductDetails productDetails : productDetailsList) {
                                respone = productDetails.getSubscriptionOfferDetails().get(0).getPricingPhases()
                                        .getPricingPhaseList().get(0).getFormattedPrice();
                                sku = productDetails.getName();
                                String ds = productDetails.getDescription();
                                desYear = sku + " " + ds + " " + " Price " + respone;
                                Log.d("test1", "onBillingSetupFinished: " + desYear);
//                                String offerToken = productDetails.getSubscriptionOfferDetails()
//                                        .get(0).getOfferToken();
//                                List<BillingFlowParams.ProductDetailsParams> productDetailsParams = new ArrayList<>();
//                                productDetailsParams.add(BillingFlowParams.ProductDetailsParams.newBuilder()
//                                        .setProductDetails(productDetails)
//                                        .setOfferToken(offerToken)
//                                        .build()
//                                );
//                                BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
//                                        .setProductDetailsParamsList(productDetailsParams)
//                                        .build();
//                                billingClient.launchBillingFlow(IAPActivity.this, billingFlowParams);

                            }

                        });
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }


                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (billingClient != null) {
            billingClient.endConnection();
        }
    }
}