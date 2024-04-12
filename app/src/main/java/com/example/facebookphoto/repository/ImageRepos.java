package com.example.facebookphoto.repository;

import android.net.Uri;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

public interface ImageRepos {
    void fetchAllImageUris(String folderPath,
                           final OnSuccessListener<List<Uri>> onSuccessListener,
                           final OnFailureListener onFailureListener);
}
