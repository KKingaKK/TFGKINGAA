package com.example.notespro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.notespro.Adapter.ToDoAdapter;
import com.example.notespro.Model.ToDoModel;
import com.example.notespro.Utils.DataBaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ToDoMainActivity extends AppCompatActivity implements OnDialogCloseListner{


        private RecyclerView mRecyclerview;
        private FloatingActionButton fab;
    private FloatingActionButton back;
        private DataBaseHelper myDB;
        private List<ToDoModel> mList;
        private ToDoAdapter adapter;


        @SuppressLint("MissingInflatedId")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_to_do_main);

            mRecyclerview = findViewById(R.id.recyclerview);
            fab = findViewById(R.id.fab);
            back=findViewById(R.id.back);
            myDB = new DataBaseHelper(ToDoMainActivity.this);
            mList = new ArrayList<>();
            adapter = new ToDoAdapter(myDB ,ToDoMainActivity.this);


            mRecyclerview.setHasFixedSize(true);
            mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerview.setAdapter(adapter);


            mList = myDB.getAllTasks();
            Collections.reverse(mList);
            adapter.setTasks(mList);

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ToDoMainActivity.this,MainActivity.class));

                }
            });



            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddNewTask.newInstance().show(getSupportFragmentManager() , AddNewTask.TAG);
                }
            });

            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerViewTouchHelper(adapter));
            itemTouchHelper.attachToRecyclerView(mRecyclerview);

        }

        @Override
        public void onDialogClose(DialogInterface dialogInterface) {
            mList = myDB.getAllTasks();
            Collections.reverse(mList);
            adapter.setTasks(mList);
            adapter.notifyDataSetChanged();
        }

    }

