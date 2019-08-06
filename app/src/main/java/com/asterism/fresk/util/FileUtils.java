package com.asterism.fresk.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 文件工具类
 *
 * @author Ashinch
 * @email Glaxyinfinite@outlook.com
 * @date on 2019-07-06 17:19
 */
public class FileUtils {

    /**
     * 获取文件格式后缀名
     *
     * @param fileName 文件名
     *
     * @return 返回文件后缀名字符串（不包括点）
     */
    public static String getFileSuffixName(String fileName) {
        //去掉首尾的空格
        fileName = fileName.trim();
        String[] s = fileName.split("\\.");
        if (s.length >= 2) {
            return s[s.length - 1];
        }
        return "";
    }

    /**
     * 获取文件名称
     *
     * @param path 文件路径
     *
     * @return 返回文件名称字符串（不包括后缀名）
     */
    public static String getFileSimpleName(String path) {
        //去掉首尾的空格
        path = path.trim();
        String[] parts = new File(path).getName().split("\\.");
        StringBuilder name = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            if (i != (parts.length - 1)) {
                name.append(parts[i]);
            }
        }
        return name.toString();
    }

    /**
     * 获取文件名称
     *
     * @param path 文件路径
     *
     * @return 返回文件名称字符串（不包括后缀名）
     */
    public static String getFileSimpleName2(String path) {
        //去掉首尾的空格
        path = path.trim();
        String[] parts = new File(path).getName().split("\\.");
        StringBuilder name = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            if (i != (parts.length - 1)) {
                name.append(parts[i]);
            }
        }
        return name.toString();
    }

    /**
     * 写入File目录文件
     *
     * @param context  上下文对象
     * @param fileName 欲写入的完整文件名（不包含路径），不存在则新建
     * @param content  欲写入的内容
     * @param mode     写入模式 Context.MODE_PRIVATE、MODE_APPEND
     */
    public static void writeFile(Context context, String fileName, String content, int mode) {
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(fileName, mode);
            fos.write(content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 读取File目录文件
     *
     * @param context  上下文对象
     * @param fileName 欲读取的完整文件名（不包含路径）
     *
     * @return 返回文件内容
     */
    public static String readFileToString(Context context, String fileName) {
        FileInputStream fis = null;
        StringBuilder buffer = new StringBuilder();
        try {
            fis = context.openFileInput(fileName);
            byte[] b = new byte[4096];
            for (int i; (i = fis.read(b)) != -1; ) {
                buffer.append(new String(b, 0, i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return buffer.toString();
    }

    /**
     * 复制文件
     *
     * @param oldPath 被复制的文件路径
     * @param newPath 复制到的文件路径
     *
     * @return 返回复制结果
     */
    public static boolean copyFile(String oldPath, String newPath) {
        File oldFile = new File(oldPath);
        if (!oldFile.exists() || !oldFile.isFile() || !oldFile.canRead()) {
            return false;
        }
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(oldPath);
            fos = new FileOutputStream(newPath);
            byte[] buffer = new byte[4096];
            int byteRead;
            while ((byteRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, byteRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (fos != null) {
                    fos.flush();
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 写入书籍封面图片文件
     *
     * @param context 上下文对象
     * @param picName 图片文件名称
     * @param data    InputStream流
     */
    public static void writeBookPic(Context context, String picName, InputStream data) {
        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                .getAbsolutePath() + File.separator + picName);
        OutputStream output = null;
        try {
            output = new FileOutputStream(file);
            byte[] buffer = new byte[4096];
            int temp;
            while ((temp = data.read(buffer)) != -1) {
                output.write(buffer, 0, temp);
            }
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
