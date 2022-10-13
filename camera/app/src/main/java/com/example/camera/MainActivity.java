package com.example.camera;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_WRITE = 223;

    private Button button;
    private ImageView imageView;

    private File photoFile;
    private ActivityResultLauncher<Intent> cameraResultLauncher;

    private String photoFileName;

    private void askWritePermission() {
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            int writePermission = this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (writePermission != PackageManager.PERMISSION_GRANTED) {
                this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        askWritePermission();
        setContentView(R.layout.activity_main);

        this.button = findViewById(R.id.button);
        this.imageView = findViewById(R.id.imageView);


        this.cameraResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Bitmap bm;
                        BitmapFactory.Options options;
                        options = new BitmapFactory.Options();
                        options.inSampleSize = 2;
                        bm = BitmapFactory.decodeFile(photoFileName, options);
                        imageView.setImageBitmap(bm); // Set imageview to image that was
                        Toast.makeText(this, "Data Telah Terload ke ImageView" + photoFileName, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Error taking picture", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        this.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                // Buat nama file menggunakan timestamp
                Date d = new Date();
                CharSequence s = DateFormat.format("yyyyMMdd-hh-mm-ss", d.getTime());

                photoFile = getPhotoFileUri(s.toString() + ".jpg");

                // wrap file menggunakan file provider
                Uri fileProvider = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

                if (intent.resolveActivity(getPackageManager()) != null) {
                    // Start the image capture intent to take photo
                    cameraResultLauncher.launch(intent);
                }
            }
        });
    }

    // Return File untuk menyimpan foto
    public File getPhotoFileUri(String fileName) {
        // Use `getExternalFilesDir` for getting safe storage folder
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "EXAMPLE_CAMERA");

        // Buat folder
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d("EXAMPLE.CAMERA", "failed to create directory");
        }

        //nama file dengan full path
        this.photoFileName = mediaStorageDir.getPath() + File.separator + fileName;
        File file = new File(this.photoFileName);

        return file;
    }
}