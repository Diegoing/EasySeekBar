package com.diegoing.view.easyseekbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.diegoing.view.seekbar.EasySeekBar;


public class VerticalActivity extends AppCompatActivity {
   private int value=50;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical);
        final EasySeekBar esb_v1 = findViewById(R.id.esb_v1);
        final EasySeekBar esb_v2 = findViewById(R.id.esb_v2);
        final EasySeekBar esb_v3 = findViewById(R.id.esb_v3);
        Button bt = findViewById(R.id.bt);
        esb_v1.setEasySeekBarLister(new EasySeekBar.EasySeekBarLister() {
            @Override
            public void onProgress(int pro) {
                Toast.makeText(VerticalActivity.this,pro+"",Toast.LENGTH_SHORT).show();
            }
        });
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                esb_v1.setValue(value++);
                esb_v2.setValue(value++);
                esb_v3.setValue(value++);
            }
        });
    }
}
