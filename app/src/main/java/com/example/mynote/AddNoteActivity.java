package com.example.mynote;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

public class AddNoteActivity extends AppCompatActivity implements View.OnClickListener{
    Toolbar toolbar ;
    Button saveButton , cancelButton;
    EditText title,description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note_activity);
        toolbar = findViewById(R.id.addnotetoolbar);
        setSupportActionBar(toolbar);
        saveButton = findViewById(R.id.savebutton);
        cancelButton = findViewById(R.id.cancelButton);
        saveButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        title = findViewById(R.id.titleenter);
        description= findViewById(R.id.descriptionenter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.savebutton:
                 String titletext = title.getText().toString().trim();
                 String descriptionText = description.getText().toString().trim();
                 NoteDatabase noteDatabase = new NoteDatabase(getApplicationContext());
                 noteDatabase.openConnection();
                 noteDatabase.doentry(titletext,descriptionText);
                Toast.makeText(this, "Note Save Successfully", Toast.LENGTH_SHORT).show();
                 noteDatabase.closeConnection();
                 break;
            case R.id.cancelButton:
                  this.onBackPressed();
                break;
        }
    }
}