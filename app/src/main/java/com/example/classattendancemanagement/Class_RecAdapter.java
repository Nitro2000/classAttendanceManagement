package com.example.classattendancemanagement;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Class_RecAdapter extends RecyclerView.Adapter<Class_RecAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtClassName, txtSubName, txtTakeAttendance;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtClassName = itemView.findViewById(R.id.txtClassName);
            txtSubName = itemView.findViewById(R.id.txtSubName);
            txtTakeAttendance = itemView.findViewById(R.id.txtTakeAttendance);

        }
    }


    private ArrayList<ModelClass> ar = new ArrayList<>();
    private Context mContext;
    private String parentActivity;



    public Class_RecAdapter(Context mContext, ArrayList<ModelClass> ar, String parentActivity) {
        this.mContext = mContext;
        this.ar = ar;
        this.parentActivity = parentActivity;
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Class_RecAdapter.ViewHolder holder, int position) {
        holder.txtClassName.setText(ar.get(position).getClassName());
        holder.txtSubName.setText(ar.get(position).getSubjName());

        if (parentActivity.equals("Attender")) {
            holder.txtTakeAttendance.setVisibility(View.VISIBLE);
            holder.txtTakeAttendance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, TakeAttendance.class);
                    intent.putExtra("className", ar.get(position).getClassName());
                    intent.putExtra("subName", ar.get(position).getSubjName());
                    intent.putExtra("positon", position);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return ar.size();
    }

}
