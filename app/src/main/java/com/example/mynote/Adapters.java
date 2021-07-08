package com.example.mynote;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adapters extends RecyclerView.Adapter<Adapters.ViewHolders>{
   OnItemClickListeners onitemccl;
   public Adapters(OnItemClickListeners onitemccl){
        this.onitemccl= onitemccl;
    }
    @Override
    public Adapters.ViewHolders onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.notegrid,parent,false);
        return new ViewHolders(view);
    }

    @Override
    public void onBindViewHolder( Adapters.ViewHolders holder, int position) {
       holder.titletext.setText("Hello ");
       holder.descriptionText.setText("This Is Description");
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    class ViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{
       TextView titletext,descriptionText;
       public ViewHolders( View itemView) {
            super(itemView);
            titletext = itemView.findViewById(R.id.titletext);
            descriptionText = itemView.findViewById(R.id.description);
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
