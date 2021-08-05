package com.example.mynote;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class OnlineNoteShow extends Fragment {
    ProgressBar progressBar ;
   Note note;
   Button saveButton,cancelButton;
   TextView titleText,descriptionText;
   Context context;
   ForCommunication forCommunication;
    public OnlineNoteShow(Note note,Context context) {
       this.note=note;
       this.context = context;
       forCommunication = (ForCommunication)context;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {
       return inflater.inflate(R.layout.note_edit_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        titleText = view.findViewById(R.id.editTextTitle);
        descriptionText = view.findViewById(R.id.editTextDescription);
        saveButton = view.findViewById(R.id.buttonSave);
        cancelButton = view.findViewById(R.id.buttonCancel);
        progressBar = view.findViewById(R.id.noteEditProgress);
        titleText.setText(note.getTitle());
        descriptionText.setText(note.getDescription());
        saveButton.setOnClickListener(View->{
            progressBar.setVisibility(android.view.View.VISIBLE);
            note.setTitle(titleText.getText().toString());
            note.setDescription(descriptionText.getText().toString());
         CollectionReference docref = FirebaseFirestore.getInstance().collection("Notes").document(FirebaseAuth.getInstance().getCurrentUser()
            .getEmail()).collection("Notes");
         docref.document(note.getFirebaseid()).set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
             @Override
             public void onSuccess(Void unused) {
                 Toast.makeText(context, "Note Updated Succesfully", Toast.LENGTH_SHORT).show();
                 forCommunication.onCancelPressed();
             }
         }).addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {
                 Toast.makeText(context, "Error = "+e.getMessage(), Toast.LENGTH_SHORT).show();
             }
         });

        });
        cancelButton.setOnClickListener(View->{
            forCommunication.onCancelPressed();
        });
    }


}
