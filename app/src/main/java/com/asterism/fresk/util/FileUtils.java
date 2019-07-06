package com.asterism.fresk.util;

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
    public static String getSuffixNameByFileName(String fileName) {
        //去掉首尾的空格
        fileName = fileName.trim();
        String s[] = fileName.split("\\.");
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
