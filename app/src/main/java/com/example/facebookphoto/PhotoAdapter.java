package com.example.facebookphoto;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class PhotoAdapter extends ArrayAdapter<Bitmap> {
    private Context mContext;
    private ArrayList<Bitmap> mPhotos;

    public void addPhoto(Bitmap photo) {
        mPhotos.add(photo);
        notifyDataSetChanged();
    }


    public PhotoAdapter(@NonNull Context context, ArrayList<Bitmap> photos) {
        super(context, 0, photos);
        mContext = context;
        mPhotos = photos;
    }

    @Override
    public int getCount() {
        return mPhotos.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView = (ImageView) convertView;
        if (imageView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        Bitmap photo = mPhotos.get(position);
        imageView.setImageBitmap(photo);
        return imageView;
    }
}

