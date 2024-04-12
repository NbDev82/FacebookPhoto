package com.example.facebookphoto.repository;

import android.net.Uri;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ImageReposImpl implements ImageRepos {

    private StorageReference storageReference;

    public ImageReposImpl() {
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
}
