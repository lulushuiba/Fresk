package com.asterism.fresk.contract;

import android.net.Uri;
import android.widget.ImageView;
import android.widget.TextView;

import com.asterism.fresk.dao.bean.BookBean;

import java.util.List;

/**
 * 书籍模块合约接口
 *
 * @author Ashinch
 * @email Glaxyinfinite@outlook.com
 * @date on 2019-07-01 23:42
 */
public interface IBookContract {

    interface View extends IBaseContract.View {
        /**
         * 显示正在加载
         */
        void showLoading();

        /**
         * 隐藏正在加载
         */
        void hideLoading();

        /**
         * 显示正在移除
         */
        void showRemoving();

        /**
         * 隐藏正在移除
         */
        void hideRemoving();
    }

    interface Presenter extends IBaseContract.Presenter<IBookContract.View> {

        /**
         * 从数据库内获取所有书籍
         *
         * @param listener 监听器
         */
        void getAllBooks(OnBookListListener listener);

        /**
         * 从数据库内按照阅读日期排序获取指定索引书籍
         *
         * @param index    索引
         * @param listener 监听器
         */
        void getBookByIndexSortReadDate(int index, OnBookBeanListener listener);

        /**
         * 从数据库内移除书籍
         *
         * @param bookList 欲移除的BookBean集合
         * @param listener 监听器
         */
        void removeBooksInDatabase(List<BookBean> bookList, OnBookListListener listener);

        /**
         * 从储存设备内移除书籍
         *
         * @param bookList 欲移除的BookBean集合
         */
        void removeBooksInStorage(List<BookBean> bookList);

        /**
         * 撤销移除数据库内的书籍
         *
         * @param bookList 被移除的BookBean集合
         * @param listener 监听器
         */
        void restoreBooks(List<BookBean> bookList, OnNormalListener listener);

        /**
         * 修改数据库内书籍信息
         *
         * @param bookBean 欲更改的书籍实体类
         * @param listener 监听器
         */
        void alterBookInfo(BookBean bookBean, OnNormalListener listener);

        /**
         * 修改数据库内书籍书名信息
         *
         * @param bookid 书籍编号
         * @param newbookname 更改后的书名
         * @param listener 监听器
         */
        void alterBookNameInfo(int bookid,String newbookname,OnNormalListener listener);

        /**
         * 修改数据库内书籍图片信息
         *
         * @param bookid 书籍编号
         * @param newpic 更改后的图片
         * @param listener 监听器
         */
        void alterBookPicInfo(int bookid,String newpic,OnNormalListener listener);

        /**
         * 给BookPresenter成员变量传值
         *
         * @param imgBookPic 显示书籍封面的ImageView
         * @param tvBookName 显示书名的TextView
         * @param onNormalListener 监听器
         * @param bookid 书籍的编号
         * @param bookname 书名
         * @param selectedImageUri 相册选择的图片地址
         * @param bookpicUri 书籍图片路径
         */
        void initData(ImageView imgBookPic, TextView tvBookName, OnNormalListener onNormalListener,
                      int bookid, String bookname, String selectedImageUri, Uri bookpicUri);

        /**
         * 实现长按编辑书籍信息
         */
        void LongPressEditor();
    }

    interface OnBookListListener {
        /**
         * BookList相关操作成功事件
         *
         * @param bookList 回调操作的BookBean集合
         */
        void onSuccess(List<BookBean> bookList);

        /**
         * BookList相关操作失败事件
         */
        void onError();
    }

    interface OnBookBeanListener {
        /**
         * BookBean相关操作成功事件
         *
         * @param bookList 回调操作的BookBean对象
         */
        void onSuccess(BookBean bookList);

        /**
         * BookBean相关操作失败事件
         *
         * @param message 回调错误消息
         */
        void onError(String message);
    }

    interface OnNormalListener {
        /**
         * 成功事件
         */
        void onSuccess();

        /**
         * 失败事件
         */
        void onError();
    }
}
