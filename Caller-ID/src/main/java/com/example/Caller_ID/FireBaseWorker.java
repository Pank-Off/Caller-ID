package com.example.Caller_ID;

import android.app.DownloadManager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

class FireBaseWorker {
    FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    private Context context;

    FireBaseWorker(Context context) {
        this.context = context;
    }

    void download() {
        // так можно получить путь бд которую создате sqlite в databaseHelper
        // String PathDB = mDatabaseHelper.getPath();
        // String Path = PathDB.substring(0,PathDB.length()-14);
        String Path = "/data/data/com.example.myapplication/databases";
        File rootPath = new File(Path);

        storageReference = FirebaseStorage.getInstance().getReference().child("phoneTable.db");

        if (!rootPath.exists()) {
            rootPath.mkdirs();
        }

        final File localFile = new File(rootPath, "CloudPhoneTable");

        Log.d("Path", localFile.toString());

        storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Log.e("firebase ", ";local tem file created  created " + localFile.toString());
                //  updateDb(timestamp,localFile.toString(),position);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e("firebase ", ";local tem file not created  created " + exception.toString());
            }
        });


//        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//
//                Log.d("Path", uri.toString());
//                Environment.getExternalStorageState();
//                String url = uri.toString();
//                downloadFiles(context,
//                        "cloudPhoneTable",
//                        "",
//                        "data/data/com.example.myapplication/databases",
//                        // context.getFilesDir().getPath(),
//                        url);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//            }
//        });
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
