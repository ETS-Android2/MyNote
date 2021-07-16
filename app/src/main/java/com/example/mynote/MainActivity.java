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
import android.view.View;
import android.widget.Adapter;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements Adapters.OnItemListener {
   Toolbar toolbar;
   NavigationView navView;
   DrawerLayout drawerLayout;
   ActionBarDrawerToggle toggle;
   FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        toolbar = findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navView = findViewById(R.id.navView);
        drawerLayout = findViewById(R.id.mainDrawer);
        toggle= new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        toggle.syncState();
        fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontationer,new NoteEditFragment()).addToBackStack(null).commit();
                fab.setVisibility(View.INVISIBLE);
            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontationer,new MainFragment(this)).commit();
    }

    @Override
    public void onItenListen(Note note) {
          // Start Work From Here
        Bundle bundle = new Bundle();
        bundle.putString("title",note.getTitle());
        bundle.putString("descrip",note.getDescription());
        bundle.putInt("id",note.getId());
        NoteEditFragment ntef= new NoteEditFragment();
        ntef.setArguments(bundle);
        fab.setVisibility(View.INVISIBLE);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontationer,ntef).addToBackStack(null).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fab.setVisibility(View.VISIBLE);
    }

}