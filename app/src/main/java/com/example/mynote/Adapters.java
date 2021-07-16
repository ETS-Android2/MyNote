package com.example.mynote;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapters extends RecyclerView.Adapter<Adapters.ViewHolders> {
    ArrayList<Note> noteList;
    OnItemListener onItemListener;
    Adapters(ArrayList<Note> noteList, Context context){
        this.noteList  = noteList;
        this.onItemListener=(OnItemListener)context;
    }
    @Override
    public ViewHolders onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.adapter_layout,parent,false);
        return new ViewHolders(view);
    }

    @Override
    public void onBindViewHolder(Adapters.ViewHolders holder, int position) {
        holder.titleTextView.setText(noteList.get(position).getTitle());
        holder.descriptionTextView.setText(noteList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class ViewHolders extends RecyclerView.ViewHolder implements AdapterView.OnItemClickListener{
        TextView titleTextView,descriptionTextView;
        public ViewHolders(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleText);
            descriptionTextView = itemView.findViewById(R.id.descriptionText);
        }
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              onItemListener.onItenListen(noteList.get(position));
        }


    }
    public interface OnItemListener{
        void onItenListen(Note note);
    }
}
