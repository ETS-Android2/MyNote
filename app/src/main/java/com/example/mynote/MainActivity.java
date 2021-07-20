package com.example.mynote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity implements Adapters.OnItemListener , MainFragment.ForFab , ForCommunication {
    Toolbar toolbar;
    NavigationView navView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    FloatingActionButton fab;
    int deleteId;
    Context appContext;
    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navView = findViewById(R.id.navView);
        drawerLayout = findViewById(R.id.mainDrawer);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        toggle.syncState();
        fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontationer, new NoteEditFragment(context)).addToBackStack(null).commit();
                fab.setVisibility(View.INVISIBLE);
            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontationer, new MainFragment(this)).commit();
      navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
          @Override
          public boolean onNavigationItemSelected( MenuItem item) {
              switch (item.getItemId()) {
                  case R.id.settings:
                      SettingsFragments settingsFragments = new SettingsFragments(context);
                      getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontationer, settingsFragments, null).addToBackStack(null).commit();
                     drawerLayout.closeDrawer(GravityCompat.START,true);
                      fab.setVisibility(View.INVISIBLE);
                     break;
                  case R.id.aboutus:
                    AboutUsFragment aboutUsFragment = new AboutUsFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontationer,aboutUsFragment,null).addToBackStack(null).commit();
                    fab.setVisibility(View.INVISIBLE);
                    drawerLayout.closeDrawer(GravityCompat.START,true);
                    break;
                  default:
                      Toast.makeText(context, "You Selected Settings", Toast.LENGTH_SHORT).show();
              }
              return  false;
          }
      });
    }

    @Override
    public void onItenListen(Note note) {
        // Start Work From Here
        Bundle bundle = new Bundle();
        bundle.putString("title", note.getTitle());
        bundle.putString("descrip", note.getDescription());
        bundle.putInt("id", note.getId());
        NoteShowClass nsc = new NoteShowClass(context);
        nsc.setArguments(bundle);
        fab.setVisibility(View.INVISIBLE);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontationer, nsc).addToBackStack(null).commit();
    }

    @Override
    public void longClick(int id) {
     deleteId =id;
    }

    @Override
    protected void onResume() {
        super.onResume();
        fab.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        fab.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.context_menu,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_delete:
               NoteDatabase noteDatabase = new NoteDatabase(context);
               noteDatabase.openConnection();
               noteDatabase.deleteData(deleteId);
                Toast.makeText(context, "Note Deleted SuccessFully", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontationer, new MainFragment(this)).commit();
               noteDatabase.closeConnection();
            default:
            return super.onContextItemSelected(item);
        }
    }

    @Override
    public void setVisibility() {
    fab.setVisibility(View.VISIBLE);
    }
    @Override
    public void onCancelPressed() {
        onBackPressed();
    }

    @Override
    public void onCheckedChangeListener() {
    }
}