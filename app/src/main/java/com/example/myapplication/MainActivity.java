package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapt.todoadp;
import com.example.myapplication.model.todoapp;
import com.example.myapplication.storeddata.data;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements onDialCloseListener{

    RecyclerView recv;
    FloatingActionButton addb;
    data d;
    private List<todoapp> mlist;
    private todoadp adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recv = findViewById(R.id.recycleView);
        addb = findViewById(R.id.addbutton);
        d = new data(MainActivity.this);
        mlist = new ArrayList<>();
        adapter = new todoadp(d, MainActivity.this);

        recv.setHasFixedSize(true);
        recv.setLayoutManager(new LinearLayoutManager(this));
        recv.setAdapter(adapter);

        mlist = d.gettask();
        Collections.reverse(mlist);
        adapter.settask(mlist);

        addb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addnewtask.newinstance().show(getSupportFragmentManager(),addnewtask.tag);
            }
        });

        ItemTouchHelper ith = new ItemTouchHelper(new RecyclerViewTouchHelper(adapter));
        ith.attachToRecyclerView(recv);

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onDialogClose(DialogInterface dlgint) {
        mlist = d.gettask();
        Collections.reverse(mlist);
        adapter.settask(mlist);

        adapter.notifyDataSetChanged();
    }
}