package com.sapai_4.ai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sapai_4.ai.R;
import com.sapai_4.ai.adapter.MessageRecentAdapter;
import com.sapai_4.ai.data.DBRecentCopyManager;
import com.sapai_4.ai.model.MessageCopyModel;
import com.sapai_4.ai.model.RecentCopyModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Detail_Recent_Activity extends AppCompatActivity {
    DBRecentCopyManager dbManager;
    String timeCreate;
    MessageRecentAdapter messageAdapter;
    ArrayList<MessageCopyModel> messageList;
    RecyclerView messageRcv, messageCopyRcv;
    LinearLayout shareLinear, backLinear, deleteLinear, notyficationLinear;
    NestedScrollView nested;
    ImageView deleteImg;
    private static final int REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_recent);
        mapping();
        Intent intent = getIntent();
        String getTime = intent.getStringExtra("time");
        Integer getTimeDate = intent.getIntExtra("timeDate", 0);
        dbManager = new DBRecentCopyManager(Detail_Recent_Activity.this);
        ArrayList<RecentCopyModel> recentList = dbManager.getAllFavourites();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        for (int i = 0; i < recentList.size(); i++) {
            String dateString = format.format(recentList.get(i).getTimeCreateChat());
            if (dateString.compareTo(getTime) == 0) {
                messageList = recentList.get(i).getListMessage();
                messageAdapter = new MessageRecentAdapter(messageList, Detail_Recent_Activity.this);

                messageRcv.setAdapter(messageAdapter);
                messageCopyRcv.setAdapter(messageAdapter);
                LinearLayoutManager llm = new LinearLayoutManager(this);
                LinearLayoutManager llm1 = new LinearLayoutManager(this);
                llm.setStackFromEnd(true);
                messageRcv.setLayoutManager(llm);
                messageCopyRcv.setLayoutManager(llm1);
                messageRcv.setHasFixedSize(true);
                messageCopyRcv.setHasFixedSize(true);
            }
        }
//        ArrayList<MessageCopyModel> messageList=recentList.;
        shareLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Bitmap bitmap = getRecyclerViewAsBitmap(messageRcv);
//                shareImage(bitmap);
//                shareRecyclerViewAsImage();
//                checkWriteExternalStoragePermission();
                messageRcv.post(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bitmap = getRecyclerViewScreenshot(messageRcv);
                        shareImage(bitmap);
                    }
                });
            }
        });
        backLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // call back
                finish();
            }
        });
        deleteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View customDialogView = LayoutInflater.from(Detail_Recent_Activity.this).inflate(R.layout.custom_alert_clear, null);
                View dialogBackground = LayoutInflater.from(Detail_Recent_Activity.this).inflate(R.layout.dialog_background, null);
                LinearLayout yesLinear = customDialogView.findViewById(R.id.yesLinear);
                LinearLayout noLinear = customDialogView.findViewById(R.id.noLinear);
                ViewGroup rootLayout = findViewById(android.R.id.content);
                rootLayout.addView(dialogBackground);

                dialogBackground.setVisibility(View.VISIBLE);
                dialogBackground.setAlpha(0.0f);
                dialogBackground.animate().alpha(1.0f).setDuration(300).start();

                AlertDialog.Builder builder = new AlertDialog.Builder(Detail_Recent_Activity.this);
                builder.setView(customDialogView);

                final AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                int paddingLeft = (int) getResources().getDimension(R.dimen.popup_margin);
                int paddingRight = (int) getResources().getDimension(R.dimen.popup_margin);
                customDialogView.setPadding(paddingLeft, 0, paddingRight, 0);
                alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                alertDialog.show();

                noLinear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogBackground.animate().alpha(0.0f).setDuration(300).withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                rootLayout.removeView(dialogBackground);
                            }
                        }).start();
                        alertDialog.dismiss();
                    }
                });
                yesLinear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(Detail_Recent_Activity.this, "Deleted Success...", Toast.LENGTH_SHORT).show();
                        dbManager.deleteFavouriteById(getTimeDate);
                        messageList.clear();
                        finish();
                        dialogBackground.animate().alpha(0.0f).setDuration(300).withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                rootLayout.removeView(dialogBackground);
                            }
                        }).start();
                        alertDialog.dismiss();
                    }
                });

                alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        dialogBackground.animate().alpha(0.0f).setDuration(300).withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                rootLayout.removeView(dialogBackground);
                            }
                        }).start();
                        alertDialog.dismiss();
                    }
                });

            }
        });

    }

    private void mapping() {
        messageRcv = findViewById(R.id.messageRcv);
        shareLinear = findViewById(R.id.shareLinear);
        backLinear = findViewById(R.id.backLinear);
        deleteImg = findViewById(R.id.deleteImg);
        nested = findViewById(R.id.nested);
        deleteLinear = findViewById(R.id.deleteLinear);
        notyficationLinear = findViewById(R.id.notyficationLinear);
        messageCopyRcv = findViewById(R.id.messageCopyRcv);
    }

    //    private Bitmap getRecyclerViewAsBitmap(RecyclerView recyclerView) {
//        // Đo chiều rộng và chiều cao của ScrollView
//        RecyclerView.Adapter adapter = recyclerView.getAdapter();
//        Bitmap bitmap = Bitmap.createBitmap(recyclerView.getWidth(),
//                recyclerView.getHeight(),
//                Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//        recyclerView.draw(canvas);
//        return bitmap;
//    }
    private void shareImage(Bitmap bitmap) {
        String title="title"+new Date();
        // Lưu bitmap vào bộ nhớ ngoài
        String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, title, null);
        Uri bitmapUri = Uri.parse(bitmapPath);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
        startActivity(Intent.createChooser(intent, "Share"));
    }

    //    private void checkWriteExternalStoragePermission() {
//        // Kiểm tra quyền WRITE_EXTERNAL_STORAGE
//        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                == PackageManager.PERMISSION_GRANTED) {
//            // Nếu đã có quyền, thực hiện chia sẻ ảnh
//            shareRecyclerViewAsImage();
//        } else {
//            // Nếu chưa có quyền, yêu cầu người dùng cấp quyền
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                    REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE);
//        }
//    }
    private Bitmap getRecyclerViewScreenshot(RecyclerView recyclerView) {

//        int totalHeight = getTotalItemHeight(messageRcv);
//        int widthSpec = View.MeasureSpec.makeMeasureSpec(messageRcv.getWidth(), View.MeasureSpec.EXACTLY);
//        int heightSpec = View.MeasureSpec.makeMeasureSpec(messageRcv.getHeight(), View.MeasureSpec.EXACTLY);
//
//
//        messageRcv.getAdapter().notifyDataSetChanged();
//        // Tạo Bitmap và Canvas với kích thước tổng cộng của các phần tử
//        RecyclerView.LayoutManager layoutManager = messageRcv.getLayoutManager();
//        messageRcv.setHasFixedSize(true);
//        int itemCount = layoutManager.getItemCount();
//        int totalHeight = 0;
//
//
//        // Vẽ tất cả các phần tử trong RecyclerView lên Canvas
//        RecyclerView.Adapter adapter = messageRcv.getAdapter();
//        for (int i = 0; i < adapter.getItemCount(); i++) {
//            View itemView = messageRcv.getLayoutManager().findViewByPosition(i);
//            if (itemView != null) {
//                totalHeight += itemView.getHeight()+200;
//            }
//        }
//        Bitmap bitmap = Bitmap.createBitmap(messageRcv.getMeasuredWidth(), totalHeight, Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//        messageRcv.draw(canvas);
        RecyclerView.LayoutManager layoutManager = messageRcv.getLayoutManager();
        int itemCount = layoutManager.getItemCount();
        int totalHeight = 0;
        for (int i = 0; i < itemCount; i++) {
            View itemView = layoutManager.getChildAt(i);
            int itemHeight = itemView.getHeight();
            totalHeight += itemHeight;
        }
//        messageRcv.getAdapter();
//        Bitmap bitmap = Bitmap.createBitmap(messageRcv.getWidth(), messageRcv.computeVerticalScrollRange(), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//        messageRcv.draw(canvas);
        int height_1=messageRcv.getHeight();
        int height = 0;
        double divide=height_1*1.0/totalHeight;

        for (int i = 0; i < recyclerView.getChildCount(); i++) {
            height += recyclerView.getChildAt(i).getHeight();
        }
        int height_main=(int)(height*divide);
        if (totalHeight >= height_1) {
            Bitmap bitmap = Bitmap.createBitmap(recyclerView.getWidth(), height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            Drawable bgDrawable = recyclerView.getBackground();
            if (bgDrawable != null) {
                bgDrawable.draw(canvas);
            } else {
                canvas.drawColor(Color.WHITE);
            }
            recyclerView.draw(canvas);
            return bitmap;
        } else {
            Bitmap bitmap = Bitmap.createBitmap(recyclerView.getWidth(), height_main, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            Drawable bgDrawable = recyclerView.getBackground();
            if (bgDrawable != null) {
                bgDrawable.draw(canvas);
            } else {
                canvas.drawColor(Color.WHITE);
            }
            recyclerView.draw(canvas);
            return bitmap;
        }
    }

    private int getTotalItemHeight(RecyclerView recyclerView) {
        int totalHeight = 0;
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        for (int i = 0; i < adapter.getItemCount(); i++) {
            View itemView = recyclerView.getLayoutManager().findViewByPosition(i);
            if (itemView != null) {
                totalHeight += itemView.getHeight();
            }
        }
        return totalHeight;
    }

//    private void shareRecyclerViewAsImage() {
//        Bitmap screenshot = getRecyclerViewScreenshot();
//        Uri contentUri = saveBitmapToExternalStorage(screenshot);
//        shareImage(contentUri);
//    }
//    private void shareImage(Uri contentUri) {
//        Intent shareIntent = new Intent(Intent.ACTION_SEND);
//        shareIntent.setType("image/jpeg");
//        shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
//        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        startActivity(Intent.createChooser(shareIntent, "Chia sẻ ảnh"));
//    }
//    private Uri saveBitmapToExternalStorage(Bitmap bitmap) {
//        String fileName = "recyclerview_screenshot.jpg";
//        File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "RecyclerViewScreenshots");
//
//        if (!directory.exists()) {
//            directory.mkdirs();
//        }
//
//        File file = new File(directory, fileName);
//        String imagePath = file.getAbsolutePath();
//
//        try (FileOutputStream fos = new FileOutputStream(file)) {
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//            fos.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return  FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", file);
//    }


    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String data = intent.getStringExtra("data");
            if (data.equals("1")) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        notyficationLinear.setVisibility(View.GONE);
                        backLinear.setVisibility(View.VISIBLE);
                        deleteLinear.setVisibility(View.VISIBLE);
                        Intent intent = new Intent("checkCopy");
                        intent.putExtra("data", "0");
                        context.sendBroadcast(intent);
                    }
                }, 1000);
                notyficationLinear.setVisibility(View.VISIBLE);
                backLinear.setVisibility(View.GONE);
                deleteLinear.setVisibility(View.GONE);
            }

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver, new IntentFilter("checkCopy"));
    }
}