package com.example.Caller_ID;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

public class FireBaseWorker {
    FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private String nameDB = "CloudPhoneDB.db";
    private Context context;
    private boolean result;

    public FireBaseWorker(Context context) {
        this.context = context;
    }

    public boolean download() {

        String Path = context.getDatabasePath(nameDB).getParent();
        assert Path != null;
        File rootPath = new File(Path);

        storageReference = FirebaseStorage.getInstance().getReference().child("phoneTable.db");

        if (!rootPath.exists()) {
            rootPath.mkdirs();
        }

        final File localFile = new File(rootPath, nameDB);

        Log.d("Path", localFile.toString());

        storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Log.e("firebase ", ";local tem file created  created " + localFile.toString());
                result = true;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e("firebase ", ";local tem file not created  created " + exception.toString());
                result = false;
            }
        });
        return result;
    }
}
