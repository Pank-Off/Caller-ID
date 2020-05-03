package com.example.Caller_ID;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

class FireBaseWorker {
    FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private StorageReference ref;
    private Context context;

    FireBaseWorker(Context context) {
        this.context = context;
    }

    void download() {
        storageReference = FirebaseStorage.getInstance().getReference();
        ref = storageReference.child("phoneTable");
        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();
                downloadFiles(context,
                        "phoneTable",
                        "",
//                        "/data/data/com.example.myapplication/databases/",  // Здесь студия предлагает другой вариант
                        context.getFilesDir().getPath(),
                        url);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void downloadFiles(Context context,
                               String fileName,
                               String fileExtension,
                               String destinationDirectory,
                               String url) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationDirectory, fileName + fileExtension);

        if (downloadManager != null) {
            downloadManager.enqueue(request);
        }
    }

}
