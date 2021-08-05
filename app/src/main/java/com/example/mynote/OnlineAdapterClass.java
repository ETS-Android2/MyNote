package com.example.mynote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class OnlineAdapterClass extends RecyclerView.Adapter<OnlineAdapterClass.ViewHolderClass>{
    ArrayList<Note> noteList;
    FirebaseFirestore fstore;
    AdapterInterface adapterInterface;
  ForCommunication forCommunication;
    OnlineAdapterClass(ArrayList<Note> noteList , OnlineNotesFragment context,Context appContext){
        this.noteList = noteList;
        adapterInterface = (AdapterInterface) context;
        forCommunication = (ForCommunication) appContext;
    }
    @Override
    public ViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.adapter_layout,parent,false);
        return new ViewHolderClass(v);
    }

    @Override
    public void onBindViewHolder(@NonNull  ViewHolderClass holder, int position) {
        holder.titleText.setText(noteList.get(position).getTitle());
        holder.desriptionText.setText(noteList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder implements View.OnLongClickListener ,View.OnClickListener{
        TextView desriptionText,titleText;
        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            desriptionText = itemView.findViewById(R.id.descriptionText);
            titleText = itemView.findViewById(R.id.titleText);
            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);
        }
        @Override
        public boolean onLongClick(View v) {
            PopupMenu menu = new PopupMenu(v.getContext(), v);
            menu.getMenu().add("Delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Note note =noteList.get(getAdapterPosition());
                    CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Notes").document(FirebaseAuth.getInstance()
                            .getCurrentUser().getEmail()).collection("Notes");
                    collectionReference.document(note.getFirebaseid()).delete();
                    noteList.remove(note);
                    Toast.makeText(v.getContext(), "Note Deleted SucessFully", Toast.LENGTH_SHORT).show();
                     adapterInterface.adapterAttach(noteList);
                    return false;
                }
            });
            menu.show();
            return false;
        }

        @Override
        public void onClick(View v) {
            forCommunication.onLineFragmentReplace(noteList.get(getAdapterPosition()));
        }
    }
    public interface AdapterInterface{
          void adapterAttach(ArrayList<Note> noteList);
    }
}
