package com.example.facebookphoto.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ImageServiceImpl implements ImageService {

    private StorageReference storageReference;

    public ImageServiceImpl() {
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    @Override
    public void fetchAllImageUris(String folderPath,
                                  final OnSuccessListener<List<Uri>> onSuccessListener,
                                  final OnFailureListener onFailureListener) {

        storageReference.listAll()
                .addOnSuccessListener(listResult -> {
                    List<Uri> imageUris = new ArrayList<>();
                    for (StorageReference item : listResult.getItems()) {
                        item.getDownloadUrl().addOnSuccessListener(uri -> {
                            imageUris.add(uri);
                            if (imageUris.size() == listResult.getItems().size()) {
                                onSuccessListener.onSuccess(imageUris);
                            }
                        });
                    }
                })
                .addOnFailureListener(onFailureListener);
    }

    @Override
    public void downloadImage(Uri imageUri,
                              final OnSuccessListener<Bitmap> onSuccessListener,
                              final OnFailureListener onFailureListener) {
        if (imageUri != null) {
            StorageReference storageRef = storageReference.child(imageUri.getPath());

            storageRef.getBytes(Long.MAX_VALUE)
                    .addOnSuccessListener(bytes -> {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        if (onSuccessListener != null) {
                            onSuccessListener.onSuccess(bitmap);
                        }
                    })
                    .addOnFailureListener(e -> {
                        if (onFailureListener != null) {
                            onFailureListener.onFailure(e);
                        }
                    });
        }
    }

    @Override
    public void downloadAllImages(List<Uri> imageUris,
                                  final OnSuccessListener<List<Bitmap>> onSuccessListener,
                                  final OnFailureListener onFailureListener) {

        int totalImages = imageUris.size();
        final int[] downloadedImages = {0};
        final List<Bitmap> downloadedBitmaps = new ArrayList<>();

        for (Uri imageUri : imageUris) {
            downloadImage(imageUri,
                    bitmap -> {
                        downloadedBitmaps.add(bitmap);
                        downloadedImages[0]++;
                        if (downloadedImages[0] == totalImages) {
                            onSuccessListener.onSuccess(downloadedBitmaps);
                        }
                    },
                    e -> {
                        if (onFailureListener != null) {
                            onFailureListener.onFailure(e);
                        }
                    });
        }
    }
}
