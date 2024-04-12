package com.example.facebookphoto.service;

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

        StorageReference folderReference = storageReference.child(folderPath);

        folderReference.listAll()
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
                              final OnSuccessListener<byte[]> onSuccessListener,
                              final OnFailureListener onFailureListener) {
        if (imageUri != null) {
            StorageReference storageRef = storageReference.child(imageUri.getPath());

            storageRef.getBytes(Long.MAX_VALUE)
                    .addOnSuccessListener(onSuccessListener)
                    .addOnFailureListener(onFailureListener);
        }
    }

    @Override
    public void downloadAllImages(List<Uri> imageUris,
                                  final OnSuccessListener<List<byte[]>> onSuccessListener,
                                  final OnFailureListener onFailureListener) {

        int totalImages = imageUris.size();
        final int[] downloadedImages = {0};
        final List<byte[]> downloadedImageBytes = new ArrayList<>();

        for (Uri imageUri : imageUris) {
            downloadImage(imageUri,
                    bytes -> {
                        downloadedImageBytes.add(bytes);
                        downloadedImages[0]++;
                        if (downloadedImages[0] == totalImages) {
                            onSuccessListener.onSuccess(downloadedImageBytes);
                        }
                    },
                    exception -> {
                        if (onFailureListener != null) {
                            onFailureListener.onFailure(exception);
                        }
                    });
        }
    }
}
