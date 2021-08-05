package com.example.mynote;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class NoteEditFragment extends Fragment {
    Button saveButton,cancelButton;
    EditText editTextTitle,editTextDescription;
     ForCommunication cancleButton;
     FirebaseFirestore firestore;
    private int id;
    Context context;
      NoteEditFragment(Context context){
      cancleButton=(ForCommunication) context;
      this.context=context;
      }
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.note_edit_fragment,container,false);
    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        saveButton = view.findViewById(R.id.buttonSave);
        cancelButton = view.findViewById(R.id.buttonCancel);
        editTextDescription = view.findViewById(R.id.editTextDescription);
        editTextTitle = view.findViewById(R.id.editTextTitle);
        firestore = FirebaseFirestore.getInstance();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String desText = editTextDescription.getText().toString();
                String title = editTextTitle.getText().toString();
                if (title.isEmpty() || desText.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("All The Field Are Required To Fill").setNeutralButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).setIcon(R.drawable.warning_sign_dialog).show();

                } else {
                    NoteDatabase noteDatabase = new NoteDatabase(getContext());
                    noteDatabase.openConnection();
                    noteDatabase.doEntery(desText, title);
                    noteDatabase.closeConnection();
                    cancleButton.onCancelPressed();
                }
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancleButton.onCancelPressed();
            }
        });

    }
 /* DocumentReference docref = firestore.collection("Notes").document();
                    Map<String, String> note = new HashMap<>();
                    note.put("title", title);
                    note.put("description", desText);
                    docref.set(note).addOnSuccessListener(new OnSuccessListener<Void>(){
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(context, "Note Added To FireBase Store", Toast.LENGTH_SHORT).show();
                            cancleButton.onCancelPressed();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "Message: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });*/
}
