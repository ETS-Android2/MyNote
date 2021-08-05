package com.example.mynote;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

public class MainFragment extends Fragment  {
    RecyclerView recyclerView;
    ArrayList<Note> noteList;
    Context context;
    ForFab forFab;
    TextView notesAppearTextView;
    ImageView notesAppearImageView;
    MainFragment(Context context){
        this.context = context;
    }



    public interface ForFab{
        void setVisibility();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         forFab=(ForFab) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment,container,false);
    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerview);
        notesAppearTextView =view.findViewById(R.id.textViewNotesAppear);
        notesAppearImageView = view.findViewById(R.id.imageViewNotesAppear);
        dataRetrive();
    }
    void dataRetrive(){
        NoteDatabase noteDatase = new NoteDatabase(this.getContext());
        noteDatase.openConnection();
      noteList=  noteDatase.getData();
        Adapters adapter = new Adapters(noteList, context);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        registerForContextMenu(recyclerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        dataRetrive();
        forFab.setVisibility();
    }

}
