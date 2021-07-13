package com.example.mynote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements Adapters.OnItemClickListeners , NavigationView.OnNavigationItemSelectedListener{
   Toolbar toolbar;
   RecyclerView recyclerView;
   ArrayList<Data> noteList;
   DrawerLayout drawerLayout;
   ActionBarDrawerToggle toggle;
   NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("My Notes");
        recyclerView= findViewById(R.id.note_container);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navHeader);
        navigationView.setNavigationItemSelectedListener(this);
        toggle= new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
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
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
         switch (item.getItemId()){
             case R.id.addNote:
                 Toast.makeText(this, "You Pressed The Add Note Button", Toast.LENGTH_SHORT).show();
                 break;
             case R.id.notes:
                 Toast.makeText(this, "You Pressed The Notes From Menu", Toast.LENGTH_SHORT).show();
                 break;
             case R.id.rateus:
                 Toast.makeText(this, "You Pressed The Rate Us Button ", Toast.LENGTH_SHORT).show();
                 break;
             case R.id.shareapp:
                 Toast.makeText(this, "You Pressed The Share App Button ", Toast.LENGTH_SHORT).show();
                 break;
             case R.id.logout:
                 Toast.makeText(this, "You Pressed The Logout Button", Toast.LENGTH_SHORT).show();
                 break;
         }
            return true;
    }
}