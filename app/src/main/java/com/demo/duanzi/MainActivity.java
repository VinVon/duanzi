package com.demo.duanzi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baseLibrary.ioc.CheckNet;
import com.baseLibrary.ioc.OnClick;
import com.baseLibrary.ioc.ViewById;
import com.baseLibrary.ioc.ViewUtils;

public class MainActivity extends AppCompatActivity {
    @ViewById(R.id.text_view)
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
        textView.setText("dasfdafa");
    }

    @OnClick({R.id.text_view,R.id.img})
    @CheckNet(R.id.text_view)
    public void OnClicks(View view){
        switch (view.getId()){
            case R.id.text_view:
                Toast.makeText(this,"haha",Toast.LENGTH_SHORT).show();
                break;
            case R.id.img:
                Toast.makeText(this,"img",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
