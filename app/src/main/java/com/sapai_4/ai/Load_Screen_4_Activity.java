package com.sapai_4.ai;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.AcknowledgePurchaseResponseListener;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.sapai_4.ai.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Load_Screen_4_Activity extends AppCompatActivity {
    LinearLayout freeLinear,payLinear,continueLinear;
    Drawable selectedItem,unSelectItem ;
    TextView freeTv,skipTv,moneyFreeTv,moneyTv,timeFreeTv,timeTv;
    ImageView checkYearImg,checkWeekImg;
    public static final String PREF_FILE = "MyPref";
    private static ArrayList<String> subscribeItemDisplay = new ArrayList<String>();
    ArrayAdapter<String> arrayAdapter;
    ListView listView;
    String respone,des,sku;
    boolean isSuccess=false;
    String option="year";
    private BillingClient billingClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_screen4);
        SharedPreferences sharedPreferencesCheckApp = getSharedPreferences("CheckApp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferencesCheckApp.edit();
        editor.putInt("check", 1);
        editor.apply();
        mapping();
        billingClient = BillingClient.newBuilder(Load_Screen_4_Activity.this)
                .setListener(purchasesUpdatedListener)
                .enablePendingPurchases()
                .build();
        getYear();
        getWeek();
        unSelectItem=getResources().getDrawable(R.drawable.shape_unselecte_package);
        selectedItem = getResources().getDrawable(R.drawable.shape_selected_package);
        freeLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                option="week";
                freeLinear.setBackground(selectedItem);
                payLinear.setBackground(unSelectItem);
                checkWeekImg.setVisibility(View.VISIBLE);
                checkYearImg.setVisibility(View.GONE);
                freeTv.setTextColor(Color.argb(255,255,255,255));
                moneyFreeTv.setTextColor(Color.parseColor("#FFFFFF"));
                timeFreeTv.setTextColor(Color.parseColor("#FFFFFF"));
                moneyTv.setTextColor(Color.parseColor("#969FB0"));
                timeTv.setTextColor(Color.parseColor("#969FB0"));
            }
        });
        payLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                option="year";
                payLinear.setBackground(selectedItem);
                freeLinear.setBackground(unSelectItem);
                checkWeekImg.setVisibility(View.GONE);
                checkYearImg.setVisibility(View.VISIBLE);
                freeTv.setTextColor(Color.parseColor("#0073F7"));
                moneyFreeTv.setTextColor(Color.parseColor("#969FB0"));
                timeFreeTv.setTextColor(Color.parseColor("#969FB0"));
                moneyTv.setTextColor(Color.parseColor("#FFFFFF"));
                timeTv.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });
        skipTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Load_Screen_4_Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        continueLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Load_Screen_4_Activity.this, IAPActivity.class);
//                startActivity(intent);

                billingClient.startConnection(new BillingClientStateListener() {
                    @Override
                    public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                        if(option.equals("week")){
                        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
//                    Purchase.PurchasesResult queryPurchase = billingClient.queryPurchases(SUBS);
//                    List<Purchase> queryPurchases = queryPurchase.getPurchasesList();
//                    if(queryPurchases!=null && queryPurchases.size()>0){
//                        handlePurchases(queryPurchases);
//                    }
//
//                    //check which items are in purchase list and which are not in purchase list
//                    //if items that are found add them to purchaseFound
//                    //check status of found items and save values to preference
//                    //item which are not found simply save false values to their preference
//                    //indexOf return index of item in purchase list from 0-2 (because we have 3 items) else returns -1 if not found
//                    ArrayList<Integer> purchaseFound =new ArrayList<Integer> ();
//                    if(queryPurchases!=null && queryPurchases.size()>0){
//                        //check item in purchase list
//                        for(Purchase p:queryPurchases){
//                            int index=subcribeItemIDs.indexOf(p.getSku());
//                            //if purchase found
//                            if(index>-1)
//                            {
//                                purchaseFound.add(index);
//                                if(p.getPurchaseState() == Purchase.PurchaseState.PURCHASED)
//                                {
//                                    saveSubscribeItemValueToPref(subcribeItemIDs.get(index),true);
//                                }
//                                else{
//                                    saveSubscribeItemValueToPref(subcribeItemIDs.get(index),false);
//                                }
//                            }
//                        }
//                        //items that are not found in purchase list mark false
//                        //indexOf returns -1 when item is not in foundlist
//                        for(int i=0;i < subcribeItemIDs.size(); i++){
//                            if(purchaseFound.indexOf(i)==-1){
//                                saveSubscribeItemValueToPref(subcribeItemIDs.get(i),false);
//                            }
//                        }
//                    }
//                    //if purchase list is empty that means no item is not purchased/Subscribed
//                    //Or purchase is refunded or canceled
//                    //so mark them all false
//                    else{
//                        for( String purchaseItem: subcribeItemIDs ){
//                            saveSubscribeItemValueToPref(purchaseItem,false);
//                        }
//                    }
                            List<QueryProductDetailsParams.Product> productList = new ArrayList<>();
                            productList.add(QueryProductDetailsParams.Product.newBuilder()
                                    .setProductId("com.sappai.weekly")
                                    .setProductType(BillingClient.ProductType.SUBS)
                                    .build()
                            );
                            Log.d("Test1", "onBillingSetupFinished: "+productList.size());
                            QueryProductDetailsParams params = QueryProductDetailsParams.newBuilder()
                                    .setProductList(productList)
                                    .build();
                            billingClient.queryProductDetailsAsync(params, (billingResult1, productDetailsList) -> {
                                for (ProductDetails productDetails : productDetailsList) {
                                    String offerToken = productDetails.getSubscriptionOfferDetails()
                                            .get(0).getOfferToken();
                                    List<BillingFlowParams.ProductDetailsParams> productDetailsParams = new ArrayList<>();
                                    productDetailsParams.add(BillingFlowParams.ProductDetailsParams.newBuilder()
                                            .setProductDetails(productDetails)
                                            .setOfferToken(offerToken)
                                            .build()
                                    );
                                    BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                                            .setProductDetailsParamsList(productDetailsParams)
                                            .build();
                                    billingClient.launchBillingFlow(Load_Screen_4_Activity.this, billingFlowParams);

                                }

                            });
                        }}
                        else{
                        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
//                    Purchase.PurchasesResult queryPurchase = billingClient.queryPurchases(SUBS);
//                    List<Purchase> queryPurchases = queryPurchase.getPurchasesList();
//                    if(queryPurchases!=null && queryPurchases.size()>0){
//                        handlePurchases(queryPurchases);
//                    }
//
//                    //check which items are in purchase list and which are not in purchase list
//                    //if items that are found add them to purchaseFound
//                    //check status of found items and save values to preference
//                    //item which are not found simply save false values to their preference
//                    //indexOf return index of item in purchase list from 0-2 (because we have 3 items) else returns -1 if not found
//                    ArrayList<Integer> purchaseFound =new ArrayList<Integer> ();
//                    if(queryPurchases!=null && queryPurchases.size()>0){
//                        //check item in purchase list
//                        for(Purchase p:queryPurchases){
//                            int index=subcribeItemIDs.indexOf(p.getSku());
//                            //if purchase found
//                            if(index>-1)
//                            {
//                                purchaseFound.add(index);
//                                if(p.getPurchaseState() == Purchase.PurchaseState.PURCHASED)
//                                {
//                                    saveSubscribeItemValueToPref(subcribeItemIDs.get(index),true);
//                                }
//                                else{
//                                    saveSubscribeItemValueToPref(subcribeItemIDs.get(index),false);
//                                }
//                            }
//                        }
//                        //items that are not found in purchase list mark false
//                        //indexOf returns -1 when item is not in foundlist
//                        for(int i=0;i < subcribeItemIDs.size(); i++){
//                            if(purchaseFound.indexOf(i)==-1){
//                                saveSubscribeItemValueToPref(subcribeItemIDs.get(i),false);
//                            }
//                        }
//                    }
//                    //if purchase list is empty that means no item is not purchased/Subscribed
//                    //Or purchase is refunded or canceled
//                    //so mark them all false
//                    else{
//                        for( String purchaseItem: subcribeItemIDs ){
//                            saveSubscribeItemValueToPref(purchaseItem,false);
//                        }
//                    }
                            List<QueryProductDetailsParams.Product> productList = new ArrayList<>();
                            productList.add(QueryProductDetailsParams.Product.newBuilder()
                                    .setProductId("com.sappai.yearly")
                                    .setProductType(BillingClient.ProductType.SUBS)
                                    .build()
                            );
                            Log.d("Test1", "onBillingSetupFinished: "+productList.size());
                            QueryProductDetailsParams params = QueryProductDetailsParams.newBuilder()
                                    .setProductList(productList)
                                    .build();
                            billingClient.queryProductDetailsAsync(params, (billingResult1, productDetailsList) -> {
                                for (ProductDetails productDetails : productDetailsList) {
                                    String offerToken = productDetails.getSubscriptionOfferDetails()
                                            .get(0).getOfferToken();
                                    List<BillingFlowParams.ProductDetailsParams> productDetailsParams = new ArrayList<>();
                                    productDetailsParams.add(BillingFlowParams.ProductDetailsParams.newBuilder()
                                            .setProductDetails(productDetails)
                                            .setOfferToken(offerToken)
                                            .build()
                                    );
                                    BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                                            .setProductDetailsParamsList(productDetailsParams)
                                            .build();
                                    billingClient.launchBillingFlow(Load_Screen_4_Activity.this, billingFlowParams);

                                }

                            });
                        }}

                    }

                    @Override
                    public void onBillingServiceDisconnected() {
                    }
                });
            }
        });
    }
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
                    Intent intent = new Intent(Load_Screen_4_Activity.this,MainActivity.class);
                    startActivity(intent);

                }
            }
        };

        billingClient.consumeAsync(consumeParams, listener);
        if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED)
        {
            if (!verifyValidSignature(purchase.getOriginalJson(), purchase.getSignature())) {
                // Invalid purchase
                // show error to user
//                Toast.makeText(getApplicationContext(), "Error : Invalid Purchase", Toast.LENGTH_SHORT).show();
                return;//skip current iteration only because other items in purchase list must be checked if present
            }
            if (!purchase.isAcknowledged()){
                AcknowledgePurchaseParams acknowledgePurchaseParams =
                        AcknowledgePurchaseParams.newBuilder()
                                .setPurchaseToken(purchase.getPurchaseToken())
                                .build();
                billingClient.acknowledgePurchase(acknowledgePurchaseParams,acknowledgePurchaseResponseListener);
                isSuccess=true;
                Intent intent = new Intent(Load_Screen_4_Activity.this,MainActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(this, "Already_Subscribed", Toast.LENGTH_SHORT).show();
            }
        }
        //else item is purchased and also acknowledged
        else if(purchase.getPurchaseState()==Purchase.PurchaseState.PENDING){
            Toast.makeText(this, "Subscription PENDING", Toast.LENGTH_SHORT).show();
        }
        else if(purchase.getPurchaseState()==Purchase.PurchaseState.UNSPECIFIED_STATE){
            Toast.makeText(this, "UNSPECIFIED_STATE", Toast.LENGTH_SHORT).show();
        }
    }
    AcknowledgePurchaseResponseListener acknowledgePurchaseResponseListener= new AcknowledgePurchaseResponseListener() {
        @Override
        public void onAcknowledgePurchaseResponse(@NonNull BillingResult billingResult) {
            if (billingResult.getResponseCode()==BillingClient.BillingResponseCode.OK){
                isSuccess=true;
                Toast.makeText(Load_Screen_4_Activity.this, "Subscribed", Toast.LENGTH_SHORT).show();
            }
        }
    };
    private boolean verifyValidSignature(String signedData, String signature) {
        try {
            //for old playconsole
            // To get key go to Developer Console > Select your app > Development Tools > Services & APIs.
            //for new play console
            //To get key go to Developer Console > Select your app > Monetize > Monetization setup

            String base64Key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjxy8qMksbufOSGKfvmcVc/M5uHOew3KL/gWZElhaV4TLSWVBxmtssI6WQ2hShCtvPtYhnlzCqQSvKQiN0JwVXAYj2vLLUcPSVViTnG17FnttGHg1kU9zJy1+0ptBo1J/dwR6aiXPfTOzHLJppCNp3dymHvFl2iRsWTHe3RMDndgadJoMj337qpRQ2wZllC5+eadIsYf6wIz135PwWuOW75jzrCP2J73lkr4Y5apWTaCMcz6vrJDrtK7eh6tF3YGrq9obhbE/wNW63/xwBuQxjjMIMRvBRVx/2VHYGnkUb2qmDR2Dlpd8q9qAlvpNyKLLbaZxLmdPZNx6T3psbpyA/wIDAQAB";
            return Security.verifyPurchase(base64Key, signedData, signature);
        } catch (IOException e) {
            return false;
        }
    }
    private PurchasesUpdatedListener purchasesUpdatedListener = new PurchasesUpdatedListener() {
        @Override
        public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> Purchase) {
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK &&
                    Purchase != null) {
                for (Purchase purchase : Purchase) {
                    handlePurchases(purchase);
                    Intent intent = new Intent(Load_Screen_4_Activity.this,MainActivity.class);
                    startActivity(intent);

                }
            } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED) {
                Toast.makeText(Load_Screen_4_Activity.this, "Already Subcribed", Toast.LENGTH_SHORT).show();
            } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.FEATURE_NOT_SUPPORTED) {
                Toast.makeText(Load_Screen_4_Activity.this, "FEATURE_NOT_SUPPORTED", Toast.LENGTH_SHORT).show();
            } else {
//                Toast.makeText(Load_Screen_4_Activity.this, "Error" + billingResult.getDebugMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    };

    private void mapping() {
        freeLinear=findViewById(R.id.freeLinear);
        payLinear=findViewById(R.id.payLinear);
        freeTv=findViewById(R.id.freeTv);
        skipTv=findViewById(R.id.skipTv);
        continueLinear=findViewById(R.id.continueLinear);
        moneyFreeTv=findViewById(R.id.moneyFreeTv);
        moneyTv=findViewById(R.id.moneyTv);
        timeFreeTv=findViewById(R.id.timeFreeTv);
        timeTv=findViewById(R.id.timeTv);
        checkWeekImg=findViewById(R.id.checkWeekImg);
        checkYearImg=findViewById(R.id.checkYearImg);
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences sharedPreferences = getSharedPreferences("CheckPermission", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("check", 1);
        editor.apply();
    }
    public void onBackPressed() {
        // Không làm gì, để ngăn người dùng quay lại màn hình trước đó
    }
    private void getWeek(){
        ExecutorService executorService= Executors.newSingleThreadExecutor();
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
                                respone=productDetails.getSubscriptionOfferDetails().get(0).getPricingPhases()
                                        .getPricingPhaseList().get(0).getFormattedPrice();
                                sku=productDetails.getName();
                                String ds=productDetails.getDescription();
                                des=sku+" "+ds+" "+ " Price "+respone;
                                Log.d("test1", "onBillingSetupFinished: "+des);


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
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(100);
                                }catch (InterruptedException e){
                                    e.printStackTrace();
                                }
                                if (des!=null){
                                    Intent intent = new Intent(Load_Screen_4_Activity.this,MainActivity.class);
                                    startActivity(intent);
                                }


                            }
                        });
                    }
                });
            }
        });
    }
    private void getYear(){
        ExecutorService executorService= Executors.newSingleThreadExecutor();
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
                                respone=productDetails.getSubscriptionOfferDetails().get(0).getPricingPhases()
                                        .getPricingPhaseList().get(0).getFormattedPrice();
                                sku=productDetails.getName();
                                String ds=productDetails.getDescription();
                                des=sku+" "+ds+" "+ " Price "+respone;
                                Log.d("test1", "onBillingSetupFinished: "+des);

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
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(100);
                                }catch (InterruptedException e){
                                    e.printStackTrace();
                                }
                                if (des!=null){
                                    Intent intent = new Intent(Load_Screen_4_Activity.this,MainActivity.class);
                                    startActivity(intent);
                                }


                            }
                        });
                    }
                });
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (billingClient != null) {
            billingClient.endConnection();
        }
    }
}