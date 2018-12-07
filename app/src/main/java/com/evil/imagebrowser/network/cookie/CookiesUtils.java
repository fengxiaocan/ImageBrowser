package com.evil.imagebrowser.network.cookie;

import android.content.Context;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * The type Save utils.
 *
 * @name： BaseApp
 * @package： com.dgtle.baselib.util
 * @author: Noah.冯 QQ:1066537317
 * @time: 17 :18
 * @version: 1.1
 * @desc： 保存工具类
 */
@Deprecated
public final class CookiesUtils {

    /**
     * Save.
     *
     * @param file the file
     * @param value the value
     */
    public static void save(File file,Serializable value) {
        if (file.exists()){
            file.delete();
        }
        FileOutputStream fos = null;
        ObjectOutputStream os = null;
        try {
            fos = new FileOutputStream(file);
            os = new ObjectOutputStream(fos);
            os.writeObject(value);
            os.close();
        } catch (Exception e) {
        } finally {
            closeIO(fos);
            closeIO(os);
        }
    }

    /**
     * Close io.
     *
     * @param is the is
     */
    public static void closeIO(Closeable is) {
        if (is != null) {
            try {
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Save.
     *
     * @param context the context
     * @param fileName the file name
     * @param value the value
     */
    public static void save(Context context,String fileName,Serializable value) {
        save(getFile(context,fileName),value);
    }

    /**
     * Save.
     *
     * @param <T> the type parameter
     * @param file the file
     * @return the t
     */
    public static <T extends Serializable> T get(File file) {
        FileInputStream fis = null;
        ObjectInputStream os = null;
        T t = null;
        try {
            fis = new FileInputStream(file);
            os = new ObjectInputStream(fis);
            t = (T)os.readObject();
            os.close();
        } catch (Exception e) {
        } finally {
            closeIO(fis);
            closeIO(os);
        }
        return t;
    }

    /**
     * Save.
     *
     * @param <T> the type parameter
     * @param context the context
     * @param fileName the file path name
     * @return the t
     */
    public static <T extends Serializable> T get(Context context,String fileName) {
        return get(getFile(context,fileName));
    }

    /**
     * 创建一个存放在 filesDir目录下的文件
     *
     * @param context the context
     * @param name 名字
     * @return file
     */
    public static File getFile(Context context,String name) {
        return new File(context.getFilesDir(),name);
    }

    /**
     * List file array list.
     *
     * @param dir the dir
     * @return the array list
     */
    public static ArrayList<File> listFile(File dir) {
        ArrayList<File> files = new ArrayList<>();
        if (dir == null) {
            return files;
        }
        if (dir.isFile()) {
            files.add(dir);
            return files;
        } else {
            File[] files1 = dir.listFiles();
            if (files1 == null) {
                return files;
            } else {
                for (File file : files1) {
                    if (file.isFile()) {
                        files.add(file);
                    } else {
                        files.addAll(listFile(file));
                    }
                }
                return files;
            }
        }
    }

    /**
     * Delete dir.
     * 删除文件夹
     *
     * @param file the file
     */
    public static void deleteDir(File file) {
        if (file == null || !file.exists()) {
            return;
        }
        ArrayList<File> files = listFile(file);
        for (File file1 : files) {
            file1.delete();
        }
    }
}
