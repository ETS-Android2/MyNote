package com.example.mynote;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class NoteShowClass extends Fragment {
    Button saveButton,cancelButton;
    EditText editTextTitle,editTextDescription;
    private int id ;
    ForCommunication cancleButton;
    NoteShowClass(Context context){
        cancleButton =(ForCommunication) context;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.note_edit_fragment,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        saveButton = view.findViewById(R.id.buttonSave);
        cancelButton = view.findViewById(R.id.buttonCancel);
        editTextDescription = view.findViewById(R.id.editTextDescription);
        editTextTitle = view.findViewById(R.id.editTextTitle);
        cancelButton.setText("Back");
        if(getArguments()!=null) {
            editTextTitle.setText( getArguments().getString("title"));
            editTextDescription.setText(getArguments().getString("descrip"));
            id = getArguments().getInt("id");
        }
        saveButton.setOnClickListener(View->
        {
            NoteDatabase noteDatabase = new NoteDatabase(getContext());
            noteDatabase.openConnection();
            noteDatabase.updateData(editTextDescription.getText().toString().trim(),editTextTitle.getText().toString().trim(),id);
            Toast.makeText(getContext(), "NoteUpdated Successfully", Toast.LENGTH_SHORT).show();
            noteDatabase.closeConnection();
            cancleButton.onCancelPressed();
        });
        cancelButton.setOnClickListener(View->
        {
            cancleButton.onCancelPressed();
        });
    }
}
