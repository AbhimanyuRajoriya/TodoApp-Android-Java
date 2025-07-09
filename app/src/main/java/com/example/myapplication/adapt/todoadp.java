package com.example.myapplication.adapt;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.addnewtask;
import com.example.myapplication.model.todoapp;
import com.example.myapplication.storeddata.data;

import java.util.List;

public class todoadp extends RecyclerView.Adapter<todoadp.myview> {

    private List<todoapp> mlist;
    private final MainActivity activity;

    private final data mydb;

    public todoadp(data db, MainActivity activity){
        this.activity= activity;
        this.mydb = db;
    }

    @NonNull
    @Override
    public myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        return new myview(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myview holder, int position) {
        final todoapp item = mlist.get(position);
        holder.cb.setText(item.getTask());
        holder.cb.setChecked(boo(item.getStatus()));
        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    mydb.satutsupdate(item.getId(),1);
                }
                else
                    mydb.satutsupdate(item.getId(),0);
            }
        });
    }

    public boolean boo(int num){
        return num!=0;
    }

    public Context getcontext(){
        return activity;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void settask(List<todoapp> mlist){
        this.mlist=mlist;
        notifyDataSetChanged();
    }

    public void deteltetask(int position){
        todoapp item = mlist.get(position);
        mydb.deletetask(item.getId());
        mlist.remove(position);
        notifyItemRemoved(position);
    }

    public void edittask(int position){
        todoapp item = mlist.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id",item.getId());
        bundle.putString("task",item.getTask());

        addnewtask task = new addnewtask();
        task.setArguments(bundle);
        task.show(activity.getSupportFragmentManager(), task.getTag());
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public static class myview extends RecyclerView.ViewHolder{
        CheckBox cb;
        public myview(@NonNull View itemView) {
            super(itemView);
            cb = itemView.findViewById(R.id.checkbox);

        }
    }
}
