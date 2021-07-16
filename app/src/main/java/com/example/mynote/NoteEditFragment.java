package com.example.mynote;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class NoteEditFragment extends Fragment {
    Button saveButton,cancelButton;
    EditText editTextTitle,editTextDescription;
    private int id;
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
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String desText= editTextDescription.getText().toString().trim();
               String title = editTextTitle.getText().toString().trim();
               NoteDatabase noteDatabase = new NoteDatabase(getContext());
               noteDatabase.openConnection();
               noteDatabase.doEntery(desText,title);
            }
        });
        if(getArguments()!=null) {
            editTextTitle.setText( getArguments().getString("title"));
            editTextDescription.setText(getArguments().getString("descrip"));
            id = getArguments().getInt("id");
        }
        else{

        }
    }
}
