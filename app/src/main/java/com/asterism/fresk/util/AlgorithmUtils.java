package com.asterism.fresk.util;

import com.asterism.fresk.dao.bean.BookBean;

import java.util.List;

/**
 * 算法工具类
 *
 * @author Ashinch
 * @email Glaxyinfinite@outlook.com
 * @date on 2019-07-23 22:44
 */
public class AlgorithmUtils {

    /**
     * 根据书籍阅读日期快速排序
     *
     * @param list  排序的集合
     * @param left  集合的前针
     * @param right 集合的后针
     */
//    public static void sortBookListByReadDate(List<BookBean> list, int left, int right) {
//        // 为空则返回
//        if (list == null || list.size() == 0) {
//            return;
//        }
//        //设置最左边的元素为基准值
//        BookBean bean = list.get(left);
//        //数组中比bean小的放在左边，比bean大的放在右边，bean值下标为i
//        int i = left;
//        int j = right;
//        while (i < j) {
//            //j向左移，直到遇到比bean小的值
//            while (DateUtils.compareDateByString(list.get(j).getReadDate(), bean.getReadDate()) >= 0 && i < j) {
//                j--;
//            }
//            //i向右移，直到遇到比bean大的值
//            while (DateUtils.compareDateByString(list.get(i).getReadDate(), bean.getReadDate()) <= 0 && i < j) {
//                i++;
//            }
//            //i和j索引的元素交换
//            if (i < j) {
//                BookBean temp = list.get(i);
//                list.set(i, list.get(j));
//                list.set(j, temp);
//            }
//        }
//
//        list.set(left, list.get(i));
//        list.set(i, bean);
//        sortBookListByReadDate(list, left, i - 1);
//        sortBookListByReadDate(list, i + 1, right);
//    }
}
