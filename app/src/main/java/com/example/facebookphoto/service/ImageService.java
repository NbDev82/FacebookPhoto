package com.example.facebookphoto.service;

import android.net.Uri;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

public interface ImageService {
    void fetchAllImageUris(String folderPath,
                           final OnSuccessListener<List<Uri>> onSuccessListener,
                           final OnFailureListener onFailureListener);

    void downloadImage(Uri imageUri,
                       final OnSuccessListener<byte[]> onSuccessListener,
                       final OnFailureListener onFailureListener);

    void downloadAllImages(List<Uri> imageUris,
                           final OnSuccessListener<List<byte[]>> onSuccessListener,
                           final OnFailureListener onFailureListener);
}
