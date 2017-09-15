package com.example;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleAnnotationValueVisitor7;

/**
 * Created by raytine on 2017/9/11.
 */

public class MyVisitor extends SimpleAnnotationValueVisitor7<Void,Void> {
    private String mPackageName;
    private TypeMirror mTypeMirror;
    private Filer mFiler;
    @Override
    public Void visitString(String s, Void aVoid) {
        mPackageName = s;
        return aVoid;
    }

    @Override
    public Void visitType(TypeMirror typeMirror, Void aVoid) {
        mTypeMirror = typeMirror;
        generateWXPayCode();
        return aVoid;
    }

    private void generateWXPayCode() {
        // 生成 一个 Class xxx.wxapi.WXPayEntryActivity extends BaseWXPayActivity
        TypeSpec.Builder classSpecBuilder = TypeSpec.classBuilder("WXPayEntryActivity")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .superclass(TypeName.get(mTypeMirror));
        //fangfa
        // 组装unbind 方法
        MethodSpec.Builder unbindMethodBuilder1 = MethodSpec.methodBuilder("initData")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .returns(TypeName.VOID);
        MethodSpec.Builder unbindMethodBuilder2 = MethodSpec.methodBuilder("initView")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .returns(TypeName.VOID);
        MethodSpec.Builder unbindMethodBuilder3 = MethodSpec.methodBuilder("initTitle")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .returns(TypeName.VOID);
        MethodSpec.Builder unbindMethodBuilder4 = MethodSpec.methodBuilder("setContentView")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .returns(TypeName.VOID);
        classSpecBuilder.addMethod(unbindMethodBuilder1.build());
        classSpecBuilder.addMethod(unbindMethodBuilder2.build());
        classSpecBuilder.addMethod(unbindMethodBuilder3.build());
        classSpecBuilder.addMethod(unbindMethodBuilder4.build());
        try {
            JavaFile.builder(mPackageName+".wxapi",classSpecBuilder.build())
                    .addFileComment("微信支付自动生成")
                    .build().writeTo(mFiler);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("翻车了");
        }
    }

    public void setmFiler(Filer mFiler) {
        this.mFiler = mFiler;
    }
}
