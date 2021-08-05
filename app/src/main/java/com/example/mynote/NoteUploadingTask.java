package com.example.mynote;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NoteUploadingTask extends AsyncTaskLoader {
   ArrayList<Note> noteList;
   Context context;
    public NoteUploadingTask(Context context,ArrayList<Note> noteList) {
        super(context);
       this.noteList = noteList;
       this.context=context;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public Object loadInBackground() {
        FirebaseAuth fauth = FirebaseAuth.getInstance();
        FirebaseUser user = fauth.getCurrentUser();
        String email = user.getEmail();
        FirebaseFirestore fstore = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = fstore.collection("Notes").document(email).collection("Notes");
        for(int i =0 ;i<noteList.size();i++) {
            Note note = noteList.get(i);


            collectionReference.add(note).addOnSuccessListener(
                    new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("Add Data ", "onSuccess: Note Uploading Success");
                        }
                    }
            ).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("Add Data", "onFailure:  Fail To Upload Data");
                }
            });
        }
        return null;
    }
}
