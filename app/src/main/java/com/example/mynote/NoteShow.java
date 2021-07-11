package com.example.mynote;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NoteShow extends AppCompatActivity {
    TextView titletv, descriptiontv;
    Button deleteButton;
    int id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_show);
      deleteButton = findViewById(R.id.deleteButton);
      titletv = findViewById(R.id.showtitle);
      descriptiontv = findViewById(R.id.showdescription);
      Intent intent = getIntent();
      titletv.setText(intent.getStringExtra("titleText"));
      descriptiontv.setText(intent.getStringExtra("Description"));
      id =intent.getIntExtra("id",0);
   deleteButton.setOnClickListener(new View.OnClickListener() {

       @Override
       public void onClick(View v) {
           NoteDatabase nb = new NoteDatabase(getApplicationContext());
           nb.openConnection();
           nb.delete(id);
           Toast.makeText(NoteShow.this, "Note Deleted Successfully", Toast.LENGTH_SHORT).show();
           onBackPressed();
       }
   });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
