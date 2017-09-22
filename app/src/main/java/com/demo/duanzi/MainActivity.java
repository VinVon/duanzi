package com.demo.duanzi;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baseLibrary.ExceptionCrashHandler;
import com.baseLibrary.base.BaseActivity;
import com.baseLibrary.dialog.AlertDialog;
import com.baseLibrary.fixBug.FixDexManager;
import com.baseLibrary.ioc.CheckNet;
import com.baseLibrary.ioc.OnClick;
import com.baseLibrary.ioc.ViewById;
import com.baseLibrary.ioc.ViewUtils;
import com.example.BindView;
import com.example.ViewByIds;
import com.framelibrary.base.BaseSkinActivity;
import com.framelibrary.base.DefaultNavigationBar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends BaseSkinActivity {
    @ViewById(R.id.text_view)
    private Button textView;

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


    }

    @Override
    protected void initView() {
        ViewUtils.inject(this);

    }

    @Override
    protected void initTitle() {
        ViewGroup viewById = (ViewGroup)findViewById(R.id.group);
        DefaultNavigationBar.Builder builder = (DefaultNavigationBar.Builder) new DefaultNavigationBar.Builder(MainActivity.this)
                .setTitle("haha")
                .setRightIcon(R.mipmap.back, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this,"hehe",Toast.LENGTH_SHORT).show();

                    }
                });
        builder.builder();
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @OnClick({R.id.text_view})
    @CheckNet(R.id.text_view)
    public void OnClicks(View view){
        switch (view.getId()){
            case R.id.text_view:
                Log.e("TAG","点击事件");
                final AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setContentView(R.layout.dialog)
                        .setText(R.id.dialog_textview,"嗯嗯哒")
                        .fromButtom(true)
                     .show();
                final EditText view1 = alertDialog.getView(R.id.et_1);
                alertDialog.setClick(R.id.dialog_textview, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, view1.getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }

}
