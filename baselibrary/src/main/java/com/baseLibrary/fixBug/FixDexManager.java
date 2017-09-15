package com.baseLibrary.fixBug;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import dalvik.system.BaseDexClassLoader;

/**
 * Created by raytine on 2017/8/29.
 */

public class FixDexManager {
    private Context mContext;
    private File mDexDir;
    public FixDexManager(Context context) {
        this.mContext = context;
        this.mDexDir = context.getDir("dex",Context.MODE_PRIVATE);
    }

    /**
     * 修复dex包
     * @param fixDexPath
     */
    public void fixDex(String fixDexPath) throws Exception {
        //1.先获取已经运行的dexElement
        ClassLoader applicationClassLoader = mContext.getClassLoader();
        Object dexElement = getDexElementByClassLoader(applicationClassLoader);
        //2.获取下载好的dexElement
        File srcFile = new File(fixDexPath);
        if (!srcFile.exists()){
            throw  new FileNotFoundException(fixDexPath);
        }
        File tagerFile = new File(mDexDir,srcFile.getName());
        if (tagerFile.exists()){
            return;
        }
        copyFile(srcFile,tagerFile);
        //2.2classloader读取fixdex的路径
        List<File> fixDexFiles = new ArrayList<>();
        fixDexFiles.add(tagerFile);
        File optimizedDirectory =new File(mDexDir,"odex");
        if (!optimizedDirectory.exists()){
            optimizedDirectory.mkdirs();
        }
        for (File fixDexFile : fixDexFiles) {
            ClassLoader classLoader = new BaseDexClassLoader(
                    fixDexFile.getAbsolutePath(),
                    optimizedDirectory.getAbsoluteFile(),//解压路径
                    null,applicationClassLoader
                    );
            Object dexElementByClassLoader = getDexElementByClassLoader(classLoader);
            //3.把补丁的dex插到dexElement的前面
            combineArray(dexElementByClassLoader,dexElement);
        }
        injectDexElements(applicationClassLoader,dexElement);
    }
        //把dexElement注入到已运行classLoader中
    private void injectDexElements(ClassLoader applicationClassLoader, Object dexElement) throws Exception{
        Field pathListField = BaseDexClassLoader.class.getDeclaredField("pathList");
        pathListField.setAccessible(true);
        Object pathList = pathListField.get(applicationClassLoader);
        Field dexElements = pathList.getClass().getDeclaredField("dexElements");
        dexElements.setAccessible(true);
        dexElements.set(pathList,dexElement);
    }
    /**
     * 获取classLoader中的DexElement
     */
    private Object getDexElementByClassLoader(ClassLoader applicationClassLoader) throws Exception {
        Field pathListField = BaseDexClassLoader.class.getDeclaredField("pathList");
        pathListField.setAccessible(true);
        Object pathList = pathListField.get(applicationClassLoader);
        Field dexElements = pathList.getClass().getDeclaredField("dexElements");
        dexElements.setAccessible(true);
        return dexElements.get(pathList);
    }
    /**
     *
     * copy file
     *
     * @param src
     *            source file
     * @param dest
     *            target file
     * @throws IOException
     */
    public static void copyFile(File src, File dest) throws IOException {
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            if (!dest.exists()) {
                dest.createNewFile();
            }
            inChannel = new FileInputStream(src).getChannel();
            outChannel = new FileOutputStream(dest).getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } finally {
            if (inChannel != null) {
                inChannel.close();
            }
            if (outChannel != null) {
                outChannel.close();
            }
        }
    }
    /**
     * 合并两个dexElements数组
     *
     * @param arrayLhs
     * @param arrayRhs
     * @return
     */
    private static Object combineArray(Object arrayLhs, Object arrayRhs) {
        Class<?> localClass = arrayLhs.getClass().getComponentType();
        int i = Array.getLength(arrayLhs);
        int j = i + Array.getLength(arrayRhs);
        Object result = Array.newInstance(localClass, j);
        for (int k = 0; k < j; ++k) {
            if (k < i) {
                Array.set(result, k, Array.get(arrayLhs, k));
            } else {
                Array.set(result, k, Array.get(arrayRhs, k - i));
            }
        }
        return result;
    }

}
