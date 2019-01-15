package com.diegoing.view.easyseekbar;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private int value = 50;
    private ArrayList<String> strings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        strings = new ArrayList<>();
        strings.add("horizontal");
        strings.add("vertical");
        strings.add("circle");
        strings.add("semicircle");
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter());
    }
    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder>{


        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new MyHolder(LayoutInflater.from(MainActivity.this).inflate(R.layout.item_recycler,viewGroup,false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder myHolder, final int i) {
             final String tvName = strings.get(i);
              myHolder.textView.setText(strings.get(i));
            final Intent intent = new Intent();
              myHolder.textView.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      switch (tvName){
                          case "horizontal":
                             intent.setClass(MainActivity.this,HorizontalActivity.class);

                              break;
                          case "vertical":
                              intent.setClass(MainActivity.this,VerticalActivity.class);
                              break;
                          case "circle":
                              intent.setClass(MainActivity.this,CircleActivity.class);
                              break;
                          case "semicircle":
                              intent.setClass(MainActivity.this,SemicActivity.class);
                              break;
                      }
                      startActivity(intent);
                  }
              });
        }

        @Override
        public int getItemCount() {
            return strings == null?0:strings.size();
        }

        class MyHolder extends RecyclerView.ViewHolder{
            TextView textView;
            public MyHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.tv_name);
            }
        }
    }
}
