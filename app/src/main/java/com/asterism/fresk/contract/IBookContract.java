package com.asterism.fresk.contract;

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
        void getAllBooks(OnBookBeanListener listener);

        /**
         * 从数据库内移除书籍
         *
         * @param bookList 欲移除的BookBean集合
         * @param listener 监听器
         */
        void removeBooksInDatabase(List<BookBean> bookList, OnBookBeanListener listener);

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
        boolean restoreBooks(List<BookBean> bookList, OnNormalListener listener);

        /**
         * 修改数据库内书籍信息
         *
         * @param bookBean 欲更改的书籍实体类
         * @param listener 监听器
         */
        boolean alterBookInfo(BookBean bookBean, OnNormalListener listener);
    }

    interface OnBookBeanListener {
        /**
         * BookBean相关操作成功事件
         *
         * @param bookList 回调操作的BookBean集合
         */
        void onSuccess(List<BookBean> bookList);

        /**
         * BookBean相关操作失败事件
         */
        void onError();
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
