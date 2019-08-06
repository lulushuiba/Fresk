package com.asterism.fresk.contract;

import android.content.Context;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.Observer;

/**
 * 添加书籍模块合约接口
 *
 * @author Ashinch
 * @email Glaxyinfinite@outlook.com
 * @date on 2019-07-01 23:42
 */
public interface IAddBookContract {

    interface View extends IBaseContract.View {

        /**
         * 得到上下文
         *
         *
         * @return 返回上下文
         */
        Context GetContext();

        /**
         * 显示正在添加
         */
        void showAdding();

        /**
         * 隐藏正在添加
         */
        void hideAdding();

        /**
         * 显示正在扫描
         */
        void showScanning();

        /**
         * 隐藏正在扫描
         */
        void hideScanning();
    }

    interface Presenter extends IBaseContract.Presenter<IAddBookContract.View> {

        /**
         * 添加书籍
         *
         * @param pathList 选中的书籍文件路径集合
         * @param listener 监听器
         */
        void addBooks(List<String> pathList, OnAddBooksListener listener);

        /**
         * 获取目录下所有文件信息
         *
         * @param currentDir 当前文件夹目录
         * @param listener   监听器
         */
        void getFilesInDir(File currentDir, OnGetFilesListener listener);

        /**
         * 扫描储存设备内所有书籍
         *
         * @param typeNameSet 欲扫描的文件类型格式后缀名集合
         * @param observer    订阅观察者
         */
        void scanBooks(Set<String> typeNameSet, Observer<String> observer);
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

    interface OnGetFilesListener {
        /**
         * 获取文件信息成功事件
         *
         * @param fileList 回调文件信息集合
         *                 Map：名称、图标、路径、类型
         *                 key：name、icon、path、type
         *                 value：String、Drawable_Id、String、（dir、file）
         */
        void onSuccess(List<Map<String, Object>> fileList);

        /**
         * 获取文件信息错误事件
         */
        void onError(String message);
    }
}
