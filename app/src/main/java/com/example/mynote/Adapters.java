package com.example.mynote;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapters extends RecyclerView.Adapter<Adapters.ViewHolders>{
   OnItemClickListeners onitemccl;
   ArrayList<Data> noteList;
   public Adapters(OnItemClickListeners onitemccl,ArrayList<Data> noteList){
        this.onitemccl= onitemccl;
        this.noteList= noteList;
    }
    @Override
    public Adapters.ViewHolders onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.notegrid,parent,false);
        return new ViewHolders(view);
    }

    @Override
    public void onBindViewHolder( Adapters.ViewHolders holder, int position) {
       Data data = noteList.get(position);
       holder.titletext.setText(data.getNoteTitle());
       holder.descriptionText.setText(data.getNoteData());
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    class ViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{
       TextView titletext,descriptionText;
       public ViewHolders( View itemView) {
            super(itemView);
            titletext = itemView.findViewById(R.id.titletext);
            descriptionText = itemView.findViewById(R.id.description);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            onitemccl.onItemClick(getAdapterPosition());
        }
    }

    public interface  OnItemClickListeners{
        void onItemClick(int position);
    }
}
