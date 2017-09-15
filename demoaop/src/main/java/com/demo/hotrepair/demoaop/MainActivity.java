package com.demo.hotrepair.demoaop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * aop 网络监测
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @CheckNet
    public void click(View v){

        Toast.makeText(this,":haha",Toast.LENGTH_SHORT).show();
    }
}
