package com.example.classattendancemanagement;

import android.content.Context;
import android.graphics.Color;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Student_RecAdapter extends RecyclerView.Adapter<Student_RecAdapter.ViewHolder> {

    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

        private TextView txtStudentName, txtRollNo, txtStudentStatus;
        private CardView cardViewStudent;

        public ViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            txtStudentName = itemView.findViewById(R.id.txtStudentName);
            txtRollNo = itemView.findViewById(R.id.txtDep);
            txtStudentStatus = itemView.findViewById(R.id.txtStudentStatus);
            cardViewStudent = itemView.findViewById(R.id.cardViewStudent);
            itemView.setOnClickListener(v -> onItemClickListener.onClick(getAdapterPosition()));
            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add(getAdapterPosition(), 1, 0, "Delete");
        }
    }


    private ArrayList<ModelStudent> arStudent = new ArrayList<>();
    private Context mContext;
    private String parentActivity;




    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public Student_RecAdapter(Context mContext, ArrayList<ModelStudent> ar, String parentActivity) {
        this.mContext = mContext;
        this.arStudent = ar;
        this.parentActivity = parentActivity;
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item, parent, false);
        return new ViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull Student_RecAdapter.ViewHolder holder, int position) {
        holder.txtStudentName.setText(arStudent.get(position).getStudentName());
        holder.txtRollNo.setText(arStudent.get(position).getRollNo());
        holder.txtStudentStatus.setText(arStudent.get(position).getStatus());
        holder.cardViewStudent.setCardBackgroundColor(changeColour(position));

    }

    private int changeColour(int position) {
        String status = Utils.getTakeAttendance().get(position).getStatus();
        if (status.equals("P"))
            return Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(mContext, R.color.Present)));
        else if (status.equals("A"))
            return Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(mContext, R.color.Absent)));
        return Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(mContext, R.color.white)));
    }

    @Override
    public int getItemCount() {
        return arStudent.size();
    }

}
