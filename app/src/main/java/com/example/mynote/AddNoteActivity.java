package com.example.mynote;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

public class AddNoteActivity extends AppCompatActivity {
    Toolbar toolbar ;
    Button saveButton , cancelButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note_activity);
        toolbar = findViewById(R.id.addnotetoolbar);
        setSupportActionBar(toolbar);
        saveButton = findViewById(R.id.savebutton);
        cancelButton = findViewById(R.id.cancelButton);
    }}