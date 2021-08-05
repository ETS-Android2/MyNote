package com.example.mynote;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class OnlineNotesFragment extends Fragment implements OnlineAdapterClass.AdapterInterface {
    RecyclerView recyclerView;
    FirebaseAuth fauth;
    FirebaseFirestore fstore;
    Context context ;
   ArrayList<Note> noteList;
   ProgressBar progressBar;
   OnlineNotesFragment fargmentContext = this;
    OnlineNotesFragment(Context context){
    this.context= context;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable  Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment,container,false);

    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fstore= FirebaseFirestore.getInstance();

    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerview);
        progressBar = view.findViewById(R.id.progressBar);

        fstore = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String userEmail;
        progressBar.setVisibility(View.VISIBLE);
        if (user == null) {
         Note note = new Note();
         note.setTitle("Error");
         note.setDescription("Please Login First And Then Try Again Later ");
        noteList = new ArrayList<>();
         noteList.add(note);
            progressBar.setVisibility(View.INVISIBLE);
            recyclerView.setAdapter(new OnlineAdapterClass(noteList, this,this.getContext()));
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        }else {
                Log.d("Fetch Data", "loadInBackground: Fethc Data");
                userEmail = user.getEmail();
                CollectionReference notesReference = fstore.collection("Notes").document(userEmail).collection("Notes");

                notesReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        noteList= new ArrayList<>();
                        for (QueryDocumentSnapshot noteSnapshot : queryDocumentSnapshots){
                            Log.d("Fetch Data", "onSuccess: We Are Fetching");
                            Note note =noteSnapshot.toObject(Note.class);
                            note.setFirebaseid(noteSnapshot.getId());
                            Log.d("Fetch Data", "onSuccess: Again We Are Fetching");
                            noteList.add(note);
                        }
                        attachAdapter(noteList);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                  });
        }
    }
     void attachAdapter(ArrayList<Note> noteList){
         progressBar.setVisibility(View.INVISIBLE);
         recyclerView.setAdapter(new OnlineAdapterClass(noteList, this,this.getContext()));
         recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
     }

    @Override
    public void adapterAttach(ArrayList<Note> noteList) {
        attachAdapter(noteList);
    }

}
