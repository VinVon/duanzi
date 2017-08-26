package com.demo.duanzi;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baseLibrary.ExceptionCrashHandler;
import com.baseLibrary.base.BaseActivity;
import com.baseLibrary.ioc.CheckNet;
import com.baseLibrary.ioc.OnClick;
import com.baseLibrary.ioc.ViewById;
import com.baseLibrary.ioc.ViewUtils;
import com.framelibrary.base.BaseSkinActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

public class MainActivity extends BaseSkinActivity {
    @ViewById(R.id.text_view)
    private TextView textView;

    @Override
    protected void initData() {
//        File crashFile = ExceptionCrashHandler.newInstance().getCrashFile();
//        if (crashFile.exists()){
//            try {
//                InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(crashFile));
//                char[] bytes  = new char[1024];
//                int len = 0;
//                while((len = inputStreamReader.read(bytes))!=-1){
//                    String str = new String(bytes,0,len);
//                    Log.e("----------",str);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        textView.setText("哈哈");
    }

    @Override
    protected void initView() {
        ViewUtils.inject(this);
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
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
