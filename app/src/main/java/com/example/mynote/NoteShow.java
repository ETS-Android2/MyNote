package com.example.mynote;

import android.os.Bundle;
import android.view.Menu;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NoteShow extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_show);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
