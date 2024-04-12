package com.example.facebookphoto;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private GridView mGridView;
    private PhotoAdapter mPhotoAdapter;
    private ArrayList<Bitmap> mPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bitmap bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);


        mGridView = findViewById(R.id.gridView);
        mPhotos = new ArrayList<>();
        mPhotos.add(bitmap);

        mPhotoAdapter = new PhotoAdapter(this, mPhotos);
        mGridView.setAdapter(mPhotoAdapter);

//        Bitmap b = BitmapUtils.decodeSampledBitmapFromPath(
//                "D:\\Android\\FacebookPhoto\\app\\src\\main\\res\\drawable\\ic_add.png",
//                200,200
//        );
//        addPhoto(bitmap);
    }

    private void addPhoto(Bitmap photo) {
//        mPhotos.add(photo);
        mPhotoAdapter.addPhoto(photo);
    }

    private void deletePhoto(int position) {
        mPhotos.remove(position);
        mPhotoAdapter.notifyDataSetChanged();
    }
}