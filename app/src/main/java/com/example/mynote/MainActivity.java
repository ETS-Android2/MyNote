package com.example.mynote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements Adapters.OnItemClickListeners {
   Toolbar toolbar;
   RecyclerView recyclerView;
   ArrayList<Data> noteList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("My Notes");
        recyclerView= findViewById(R.id.note_container);
        dataRetrive();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataRetrive();
    }

    void dataRetrive(){
        NoteDatabase noteDatabase = new NoteDatabase(this);
        noteDatabase.openConnection();
        noteList = noteDatabase.getData();
        noteDatabase.closeConnection();
        recyclerView.setAdapter(new Adapters(this,noteList));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        int id = item.getItemId();
        if(id==R.id.settings){
            Toast.makeText(this, "You Pressed Setting Icon ", Toast.LENGTH_SHORT).show();
        }
        else if(id==R.id.search){
            Toast.makeText(this, "Tou Pressed Search Icon ", Toast.LENGTH_SHORT).show();
        }else if(id==R.id.addnote){
            Intent intent = new Intent(this,AddNoteActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onItemClick(int position) {
        Data data = noteList.get(position);
       Intent intent = new Intent(this,NoteShow.class);
       intent.putExtra("titleText",data.getNoteTitle());
       intent.putExtra("id",data.getId());
       intent.putExtra("Description",data.getNoteData());
       startActivity(intent);
    }
}