package com.asterism.fresk.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DirectoryUtils {
    public static ArrayList<Map<String, Object>> DirectorySort(List<Map<String, Object>> list) {
        ArrayList<Map<String, Object>> dir = new ArrayList<>();
        ArrayList<Map<String, Object>> file = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            //当为文件夹类型时
            if ("dir".equals(list.get(i).get("type"))) {
                dir.add(list.get(i));
            //当为书籍与以添加书籍类型时
            } else if ("file".equals(list.get(i).get("type") )  ||  "already_file".equals(list.get(i).get("type"))) {
                file.add(list.get(i));
            }
        }

        for (int i = 0; i < dir.size() - 1; i++) {
            for (int j = 0; j < dir.size() - i - 1; j++) {
                int num = dir.get(j).get("file").toString().compareToIgnoreCase(dir.get(j + 1).get("file").toString());
                if (num > 0) {
                    Collections.swap(dir, j, j + 1);
                }
            }
        }

        for (int i = 0; i < file.size() - 1; i++) {
            for (int j = 0; j < file.size() - i - 1; j++) {
                int num = file.get(j).get("file").toString().compareToIgnoreCase(file.get(j + 1).get("file").toString());
                if (num > 0) {
                    Collections.swap(file, j, j + 1);
                }
            }
        }

        dir.addAll(file);

        return dir;
    }

    public static String getFormatName(String fileName) {
        //去掉首尾的空格
        fileName = fileName.trim();
        String s[] = fileName.split("\\.");
        if (s.length >= 2) {
            return s[s.length - 1];
        }

        return "";
    }
}
