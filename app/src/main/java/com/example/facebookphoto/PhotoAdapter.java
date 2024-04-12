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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class PhotoAdapter extends ArrayAdapter<Image> {

    public PhotoAdapter(@NonNull Context context, ArrayList<Image> imgList) {
        super(context, 0, imgList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.card_item, parent, false);
        }

        Image img = getItem(position);
        TextView courseTV = listitemView.findViewById(R.id.idTVName);
        ImageView courseIV = listitemView.findViewById(R.id.idIVImg);

        courseTV.setText(img.getImg_name());
        courseIV.setImageBitmap(img.getImg());
        return listitemView;
    }
}

