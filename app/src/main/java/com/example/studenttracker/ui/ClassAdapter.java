package com.example.studenttracker.ui;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studenttracker.R;
import com.example.studenttracker.entities.Classes;

import java.util.List;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClassViewHolder> {

    class ClassViewHolder extends RecyclerView.ViewHolder {
        private final TextView classItemView;

        private ClassViewHolder(View itemView) {
            super(itemView);
            classItemView = itemView.findViewById(R.id.classNameTextView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO DELETE THIS
                    Log.d("DEBUG", "ClassAdapter: onClick: " + getAdapterPosition());
                    int classPosition = getAdapterPosition();
                    final Classes currentClass = mClasses.get(classPosition);
                    Intent intent = new Intent(context, ClassDetails.class);
                    intent.putExtra("classID", currentClass.getClassID());
                    intent.putExtra("classTitle", currentClass.getClassTitle());
                    intent.putExtra("classStart", currentClass.getClassStart());
                    intent.putExtra("classEnd", currentClass.getClassEnd());
                    intent.putExtra("classProgress", currentClass.getClassProgress());
                    intent.putExtra("classProfName", currentClass.getClassInstructorName());
                    intent.putExtra("classProfPhone", currentClass.getClassInstructorPhone());
                    intent.putExtra("classProfEmail", currentClass.getClassInstructorEmail());
                    intent.putExtra("notes", currentClass.getClassNotes());

                    //Send termID to ClassDetails
                    //TODO DELETE THIS
                    int termIdToSend = currentClass.getTermID();
                    Log.d("DEBUG", "Sending termID: " + termIdToSend);
                    intent.putExtra("termID", termIdToSend);

                    //TODO REVERT THIS
                    //intent.putExtra("termID", currentClass.getTermID());
                    intent.putExtra("classStatus", currentClass.getClassProgress());
                    context.startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public ClassAdapter.ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View classItemView = mInflater.inflate(R.layout.class_list_item, parent, false);
        return new ClassViewHolder(classItemView);
    }

    private List<Classes> mClasses;
    private final Context context;

    private final LayoutInflater mInflater;

    public ClassAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull ClassAdapter.ClassViewHolder holder, int position) {
        if (mClasses != null) {
            Classes currentClass = mClasses.get(position);
            String title = currentClass.getClassTitle();
            holder.classItemView.setText(title);
        } else {
            holder.classItemView.setText("No Classes");
        }
    }

    public void setClasses(List<Classes> classes) {
        mClasses = classes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        //NullPointerException error if empty fix
        return mClasses != null ? mClasses.size() : 0;
    }
}
