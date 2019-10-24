package com.asterism.fresk.contract;

import java.util.List;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.TOCReference;

/**
 * 阅读模块合约接口
 *
 * @author Ashinch
 * @email Glaxyinfinite@outlook.com
 * @date on 2019-08-03 21:18
 */
public interface IReadContract {

    interface View extends IBaseContract.View {
        /**
         * 显示正在加载
         */
        void showLoading();

        /**
         * 隐藏正在加载
         */
        void hideLoading();
    }

    interface Presenter extends IBaseContract.Presenter<IReadContract.View> {
        Book getEpubBook(String path, String encoding);
        /**
         * 获取书籍目录
         *
         * @param book     欲获取目录的Book对象
         * @param listener 监听器
         */
        void getToc(Book book, OnGetTocListener listener);
    }

    interface OnGetEpubBookListener {
        /**
         * 获取epub书籍对象成功事件
         *
         * @param book 回调获取到的epub书籍对象
         */
        void onSuccess(Book book);

        /**
         * 获取epub书籍对象错误事件
         */
        void onError(String message);
    }

    interface OnGetTocListener {
        /**
         * 获取书籍目录成功事件
         *
         * @param tocList 回调获取到的目录字符串集合
         */
        void onSuccess(List<String> tocList);

        /**
         * 获取书籍目录错误事件
         */
        void onError(String message);
    }
}
