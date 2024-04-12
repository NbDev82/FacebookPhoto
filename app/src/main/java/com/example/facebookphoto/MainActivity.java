package com.example.facebookphoto;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import com.example.facebookphoto.service.ImageService;
import com.example.facebookphoto.service.ImageServiceImpl;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private GridView mGridView;
    private PhotoAdapter mPhotoAdapter;
    private ArrayList<Image> mPhotos;

    private ImageService imageService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGridView = findViewById(R.id.gridView);
        mPhotos = new ArrayList<>();

        mPhotoAdapter = new PhotoAdapter(this, mPhotos);
        mGridView.setAdapter(mPhotoAdapter);

        imageService = new ImageServiceImpl();
    }

    @Override
    protected void onStart() {
        super.onStart();

        loadImages();
    }

    private void loadImages() {
        String folderPath = "images/";
        imageService.fetchAllImageUris(folderPath,
                imageUris -> {
                    imageService.downloadAllImages(imageUris,
                            downloadedBitmaps -> {
                                for (Bitmap bitmap : downloadedBitmaps) {
                                    mPhotos.add(new Image("asd", bitmap));
                                }
                            },
                            e -> {
                                Log.e("Download Error", "Failed to download images: " + e.getMessage());
                            });
                },
                e -> {
                    Log.e("Fetch Error", "Failed to fetch image URIs: " + e.getMessage());
                });
    }
}