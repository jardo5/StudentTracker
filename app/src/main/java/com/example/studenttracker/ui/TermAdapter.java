package com.example.studenttracker.ui;
import com.example.studenttracker.R;
import com.example.studenttracker.entities.Term;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {
    class TermViewHolder extends RecyclerView.ViewHolder {
        private final TextView termItemView;

        private TermViewHolder(View itemview){
            super(itemview);
            termItemView=itemview.findViewById(R.id.termNameTextView);
            itemview.setOnClickListener(v -> {
                int position=getAdapterPosition();
                final Term currentTerm=mTerm.get(position);
                Intent intent = new Intent(context, TermDetails.class);
                intent.putExtra("id",currentTerm.getTermID());
                //TODO: Remove this line debugging only
                Log.d("TermAdapter", "Passing termID: " + currentTerm.getTermID() + " to TermDetails.");
                intent.putExtra("title", currentTerm.getTermTitle());
                intent.putExtra("start date", currentTerm.getTermStart());
                intent.putExtra("end date", currentTerm.getTermEnd());
                context.startActivity(intent);
            });
        }
    }

    private List<Term> mTerm;
    private final Context context;

    private final LayoutInflater mInflater;

    public TermAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }


    @NonNull
    @Override
    public TermAdapter.TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflater.inflate(R.layout.term_list_item,parent,false);
        return new TermViewHolder((itemView));
    }

    @Override
    public void onBindViewHolder(@NonNull TermAdapter.TermViewHolder holder, int position) {
        if(mTerm != null){
            Term currentTerm = mTerm.get(position);
            String title = currentTerm.getTermTitle();
            holder.termItemView.setText(title);
        }else{
            holder.termItemView.setText("No Term");
        }
    }

    @Override
    public int getItemCount() {
        return mTerm.size();
    }

    public void setTerm(List<Term> term){
        mTerm = term;
        notifyDataSetChanged();
    }
}
