package com.education.Vidhyalaya_Faculty_APP.Activites;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.education.Vidhyalaya_Faculty_APP.R;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Random;

import dmax.dialog.SpotsDialog;

public class DownloadImage extends AppCompatActivity {

    ImageView imageView;
    OutputStream outputStream;
    AppCompatButton button;
    String url;
    AlertDialog progress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        imageView = findViewById(R.id.imageview);
        button = findViewById(R.id.button);
        progress = new SpotsDialog.Builder().setContext(DownloadImage.this).build();
        progress.setTitle("Connecting to Server");
        progress.setMessage("Please wait....");
        progress.setCancelable(false);


        Intent intent = getIntent();
        url = intent.getStringExtra("img");
        progress.show();
        Picasso.with(this)
                .load("http://vidhyalaya.co.in/sspanel/" + url)
                .into(imageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        //do something when picture is loaded successfully
                        progress.dismiss();


                    }

                    @Override
                    public void onError() {
                        progress.dismiss();
                        Toast.makeText(DownloadImage.this, "Somthing Problem Try Later", Toast.LENGTH_SHORT).show();
                        button.setVisibility(View.GONE);
                    }
                });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                saveImage();
                downloadFile("http://vidhyalaya.co.in/sspanel/" + url);
            }
        });
    }

    public void saveImage() {
//        BitmapDrawable drawable= (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream((InputStream) new URL("http://www.mac-wallpapers.com/bulkupload/wallpapers/Apple%20Wallpapers/apple-black-logo-wallpaper.jpg").getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }

        File filepath = Environment.getExternalStorageDirectory();
        File dir = new File(filepath.getAbsolutePath() + "/Demo/");
        dir.mkdir();
        File file = new File(dir, System.currentTimeMillis() + ".jpg");
        try {
            outputStream = new FileOutputStream(file);
        } catch (Exception e) {
            Toast.makeText(this, "error " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        Toast.makeText(DownloadImage.this, "Saved Image \n Check in File Directory \n storage/" + R.string.app_name + ".jpg", Toast.LENGTH_SHORT).show();
        try {
            outputStream.flush();
        } catch (IOException e) {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void downloadFile(String uRl) {
        final int random = new Random().nextInt(1000 - 10000) + 1000;
        File direct = new File(Environment.getExternalStorageDirectory()
                + "/AnhsirkDasarp");

        if (!direct.exists()) {
            direct.mkdirs();
        }

        DownloadManager mgr = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

        Uri downloadUri = Uri.parse(uRl);
        DownloadManager.Request request = new DownloadManager.Request(
                downloadUri);

        request.setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_WIFI
                        | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false).setTitle("Downloding")
                .setDescription("Student Pictures")
                .setDestinationInExternalPublicDir("/File", random + ".jpg");

        mgr.enqueue(request);

        // Open Download Manager to view File progress
        Toast.makeText(this, "DownPlease wait.......", Toast.LENGTH_LONG).show();
        startActivity(new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS));

    }
}