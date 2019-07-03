package com.asterism.fresk.util;

public class FileUtils {
    public static String getSuffixNameByFileName(String fileName) {
        //去掉首尾的空格
        fileName = fileName.trim();
        String s[] = fileName.split("\\.");
        if (s.length >= 2) {
            return s[s.length - 1];
        }
        return "";
    }

    public static String getFileNameByPath(String path) {
        //去掉首尾的空格
        path = path.trim();
        String s[] = path.split("\\.");
        if (s.length >= 2) {
            return s[s.length - 1];
        }
        return "";
    }
}
