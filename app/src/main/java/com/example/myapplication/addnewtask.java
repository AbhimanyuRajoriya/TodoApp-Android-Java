package com.example.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.model.todoapp;
import com.example.myapplication.storeddata.data;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class addnewtask extends BottomSheetDialogFragment {
    public static final String tag = "Add New Task";
    private EditText edtxt;
    private Button btn;
    private data mydb;

    public static addnewtask newinstance(){
        return new addnewtask();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_task_layout, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtxt = view.findViewById(R.id.edittext);
        btn = view.findViewById(R.id.addbutton);
        mydb = new data(getActivity());
        boolean isupdate = false;

        Bundle bundle = getArguments();
        if(bundle !=null){
            isupdate = true;
            String task = bundle.getString("task");
            edtxt.setText(task);
            if (task.length() > 0) {
                btn.setEnabled(true);
            }
        }

        edtxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(s.toString().equals("")){
                    btn.setEnabled(false);
                    btn.setBackgroundColor(Color.GRAY);
                }
                else{
                    btn.setEnabled(true);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });

        boolean finalisupdate = isupdate;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = edtxt.getText().toString();
                if(finalisupdate){
                    mydb.taskupdate(bundle.getInt("id"),txt);
                }
                else{
                    todoapp item = new todoapp();
                    item.setTask(txt);
                    item.setStatus(0);
                    mydb.taskinsert(item);
                }
                dismiss();
            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if(activity instanceof onDialCloseListener){
            ((onDialCloseListener)activity).onDialogClose(dialog);
        }
    }
}