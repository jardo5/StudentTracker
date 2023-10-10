package com.example.studenttracker.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studenttracker.R;
import com.example.studenttracker.entities.Assessment;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder>{
    class AssessmentViewHolder extends RecyclerView.ViewHolder{

        private final TextView assessmentItemView;



        private AssessmentViewHolder(View itemview) {
            super(itemview);
            assessmentItemView = itemview.findViewById(R.id.assessmentNameTextView);
            itemview.setOnClickListener(view -> {
                int position = getAdapterPosition();
                final Assessment currentAssessment = mAssessments.get(position);
                Intent intent = new Intent(context, AssessmentDetails.class);
                intent.putExtra("assessmentID", currentAssessment.getAssessmentID());
                intent.putExtra("assessmentName", currentAssessment.getAssessmentName());
                intent.putExtra("assessmentStartDate", currentAssessment.getAssessmentStartDate());
                intent.putExtra("assessmentEndDate", currentAssessment.getAssessmentEndDate());
                intent.putExtra("assessmentType", currentAssessment.getAssessmentType());
                intent.putExtra("classID", currentAssessment.getCourseID());
                intent.putExtra("assessmentID", currentAssessment.getAssessmentID());
                context.startActivity(intent);
            });
        }
    }



    @NonNull
    @Override
    public AssessmentAdapter.AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View assessmentItemView = mInflater.inflate(R.layout.assessment_list_item, parent, false);
        return new AssessmentViewHolder(assessmentItemView);
    }


    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.AssessmentViewHolder holder, int position) {
        if(mAssessments == null){
            holder.assessmentItemView.setText("No Assessment Available");
        }
        else{
            Assessment currentAssessment = mAssessments.get(position);
            String assessmentName = currentAssessment.getAssessmentName();
            holder.assessmentItemView.setText(assessmentName);
        }
    }

    @Override
    public int getItemCount() {
        return mAssessments.size();
    }

    List<Assessment> mAssessments;
    private Context context;
    private final LayoutInflater mInflater;
    public AssessmentAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context=context;
    }

    public void setAssessments(List<Assessment> assessments){
        mAssessments = assessments;
        notifyDataSetChanged();
    }
}
