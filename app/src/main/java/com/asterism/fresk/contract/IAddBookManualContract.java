package com.asterism.fresk.contract;

import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * 手动查找添加书籍合约接口，同时规定了View基础接口和Presenter基础接口
 *
 * @author lulushuiba
 * @email 1315269930@qq.com
 * @date on 2019-08-03 10:10
 */
public interface IAddBookManualContract {
    interface View extends IBaseContract.View {}

    interface Presenter extends IBaseContract.Presenter<IAddBookManualContract.View> {

        /**
         * 添加书籍
         *
         * @param pathList 选中的书籍文件路径集合
         * @param listener 监听器
         */
        void addBooks(List<String> pathList, IAddBookManualContract.OnAddBooksListener listener);

    }

    interface OnAddBooksListener {
        /**
         * 添加书籍成功事件
         */
        void onSuccess();

        /**
         * 添加书籍失败事件
         *
         * @param pathList 回调添加失败的书籍文件路径集合
         */
        void onFailed(List<String> pathList);

        /**
         * 添加书籍错误事件
         *
         * @param message 回调错误消息
         */
        void onError(String message);
    }
}
