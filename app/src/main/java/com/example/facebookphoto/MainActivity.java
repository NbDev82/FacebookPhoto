package com.example.facebookphoto;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private GridView mGridView;
    private PhotoAdapter mPhotoAdapter;
    private ArrayList<Image> mPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGridView = findViewById(R.id.gridView);
        mPhotos = new ArrayList<>();

        mPhotoAdapter = new PhotoAdapter(this, mPhotos);
        mGridView.setAdapter(mPhotoAdapter);
    }
}