package com.example.mynote;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends AppCompatActivity implements Adapters.OnItemListener , MainFragment.ForFab , ForCommunication, LoaderManager.LoaderCallbacks<ArrayList<Note>> {
    Toolbar toolbar;
    NavigationView navView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    FloatingActionButton fab;
    int deleteId;bavView
    FirebaseUser user;
    TextView userNameNavigationView;
    Context context = this;
    String usernameDisplay;
    FirebaseAuth fauth;
    ArrayList<Note> noteData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navView = findViewById(R.id.navView);
            View nview    = navView.getHeaderView(0);
        userNameNavigationView=nview.findViewById(R.id.userNameNavigation);
                fauth= FirebaseAuth.getInstance();
        user  = fauth.getCurrentUser();
        if(user!=null) {
            usernameDisplay = user.getDisplayName();
            userNameNavigationView.setText(usernameDisplay);
        }drawerLayout = findViewById(R.id.mainDrawer);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        toggle.syncState();
        fab = findViewById(R.id.floatingActionButton);

        //---------setting the onclicklistener on fab-------------//

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontationer, new NoteEditFragment(context)).addToBackStack(null).commit();
                fab.setVisibility(View.INVISIBLE);
            }
        });

        //-------Setting Main Fragment Here  The Fragment Which Will Come When MainActivity Will Start-----------//

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontationer, new MainFragment(this)).commit();

       //------Setting Navigation Listener-----------//
         navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
          @Override
          public boolean onNavigationItemSelected( MenuItem item) {
              switch (item.getItemId()) {

                  case R.id.aboutus:
                    AboutUsFragment aboutUsFragment = new AboutUsFragment();
                    getSupportFragmentManager().popBackStack();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontationer,aboutUsFragment,null).addToBackStack(null).commit();
                    fab.setVisibility(View.INVISIBLE);
                    drawerLayout.closeDrawer(GravityCompat.START,true);
                    break;
                  case R.id.syncnotes://------Sync Notes To Firebase Data Store -----//
                      FireBaseRegisterClass registerfragment = (FireBaseRegisterClass) getSupportFragmentManager().findFragmentByTag("RegisterFragment");
                      if (registerfragment!=null && registerfragment.isVisible()){
                          drawerLayout.closeDrawer(GravityCompat.START,true);
                      }
                      else {
                          if (fauth.getCurrentUser() == null) {
                              drawerLayout.closeDrawer(GravityCompat.START, true);
                              Toast.makeText(context, "You Are Not Login Please Login Or Register First ", Toast.LENGTH_SHORT).show();
                              getSupportFragmentManager().popBackStack();
                              getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontationer, new FireBaseRegisterClass(MainActivity.this), "RegisterFragment")
                                      .addToBackStack(null)
                                      .commit();
                              fab.setVisibility(View.INVISIBLE);
                          } else {
                              // Toast.makeText(getApplicationContext(), "You Are Already Login ", Toast.LENGTH_SHORT).show();
                              AlertDialog dialogBuilder = new AlertDialog.Builder(context)
                                      .setTitle("Instructions")
                                      .setMessage("If You Select Sync And Delete. All The Notes From Your Will Be Delete " +
                                              "\n And If You Select Only Sync All You Offline Note Will Still be Present In Your Phone")
                                      .setPositiveButton("Sync And Delete", new DialogInterface.OnClickListener() {
                                          @Override
                                          public void onClick(DialogInterface dialog, int which) {
                                              NoteDatabase noteDatabase = new NoteDatabase(context);
                                              noteDatabase.openConnection();
                                              noteData = noteDatabase.getData();
                                              getLoaderManager().initLoader(new Random(5).nextInt(), null, MainActivity.this);
                                              noteDatabase.deleteAll();
                                              noteDatabase.closeConnection();
                                          }
                                      }).setNegativeButton("Sync", new DialogInterface.OnClickListener() {

                                          @Override
                                          public void onClick(DialogInterface dialog, int which) {
                                              NoteDatabase noteDatabase = new NoteDatabase(context);
                                              noteDatabase.openConnection();
                                              noteData = noteDatabase.getData();
                                              getLoaderManager().initLoader(new Random(5).nextInt(), null, MainActivity.this);
                                              noteDatabase.closeConnection();
                                          }
                                      })
                                      .show();
                          }
                      }
                        break;
                  case R.id.signout:
                        if(fauth.getCurrentUser()==null){
                            drawerLayout.closeDrawer(GravityCompat.START,true);
                        Toast.makeText(getApplicationContext(), " You Are Already Sign Out", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            fauth.signOut();
                            userNameNavigationView.setText("");
                            Toast.makeText(getApplicationContext(),"You Are Successfully Sign Out",Toast.LENGTH_SHORT).show();
                            drawerLayout.closeDrawer(GravityCompat.START,true);
                        }
                      break;
                  case R.id.onlineNotes:
                      OnlineNotesFragment onlinenotesFragment= (OnlineNotesFragment) getSupportFragmentManager().findFragmentByTag("OnlineNotes");
                      if(onlinenotesFragment!=null && onlinenotesFragment.isVisible() )
                      {
                          drawerLayout.closeDrawer(GravityCompat.START,true);
                      }
                      else {
                          drawerLayout.closeDrawer(GravityCompat.START, true);
                          getSupportFragmentManager().popBackStack();
                          getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontationer, new OnlineNotesFragment(getApplicationContext()), "OnlineNotes").addToBackStack(null).commit();
                      }
                      break;
                  default:
                      Toast.makeText(context, "Coming Soon", Toast.LENGTH_SHORT).show();
              }
              return  false;
          }
      });
    }

    @Override
    public void alreadyLoginHandle() {                  // For Handling Already Login Calling Her From Fragment Register
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontationer,new LoginClass(MainActivity.this),null)
                .addToBackStack(null).commit();
         fab.setVisibility(View.INVISIBLE);         //For Fan Visisbility ;
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
    public void setImageVisible() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        //fab.setVisibility(View.VISIBLE);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //fab.setVisibility(View.VISIBLE);
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
     //-------For Setting Fab VisiBility Because The Fab Is In Main Activity-------//
    @Override
    public void setVisibility() {
    fab.setVisibility(View.VISIBLE);
    }
    @Override
    //-----For On Cancel Button Pressed From The Note Edit Fragment And From The Note Taking Fragment----------//
    public void onCancelPressed() {
        onBackPressed();
    }

    @Override
    public void forNameSet() {
        user  = fauth.getCurrentUser();
        if(user!=null) {
            usernameDisplay = user.getDisplayName();
            userNameNavigationView.setText(usernameDisplay);
        }
    }

    @Override
    public void onCheckedChangeListener(){

    }
    /// All Loader Methods
    @Override
    public Loader<ArrayList<Note>> onCreateLoader(int id, Bundle args) {
        return new NoteUploadingTask(context,noteData);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Note>> loader, ArrayList<Note> data) {
           Toast.makeText(context,"Noted Uploaded SuccessFully",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Note>> loader) {

    }
     public void replaceFragment(){
        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontationer,new MainFragment(context),null).commit();
    }

    @Override
    public void onLineFragmentReplace(Note note) {
       getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontationer,new OnlineNoteShow(note,context),"OnlineNoteShow").addToBackStack(null)
               .commit();
    }
}
