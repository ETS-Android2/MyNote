package com.example.mynote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapters extends RecyclerView.Adapter<Adapters.ViewHolders> {
    ArrayList<Note> noteList;
    OnItemListener onItemListener;
    Context context;
    Adapters(ArrayList<Note> noteList, Context context){
        this.noteList  = noteList;
        this.onItemListener=(OnItemListener)context;
        this.context=context;
    }
    @Override
    public ViewHolders onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.adapter_layout,parent,false);
        return new ViewHolders(view);
    }

    @Override
    public void onBindViewHolder(Adapters.ViewHolders holder, int position) {
            holder.titleTextView.setText(noteList.get(noteList.size() - position - 1).getTitle());
            holder.descriptionTextView.setText(noteList.get(noteList.size() - position - 1).getDescription());

    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class ViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener , View.OnLongClickListener{
        TextView titleTextView,descriptionTextView;

        public ViewHolders(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleText);
            descriptionTextView = itemView.findViewById(R.id.descriptionText);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }


        @Override
        public void onClick(View v) {
               onItemListener.onItenListen(noteList.get(noteList.size()-getAdapterPosition()-1));

        }

        @Override
        public boolean onLongClick(View v) {
            onItemListener.longClick(noteList.get(noteList.size()-getAdapterPosition()-1).getId());
            return false;
        }
    }
    public interface OnItemListener{
        void onItenListen(Note note);
        void longClick(int id );
        void setImageVisible();
    }
}
