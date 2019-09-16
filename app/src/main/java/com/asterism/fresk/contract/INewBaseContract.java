package com.asterism.fresk.contract;

import android.content.Context;

public interface INewBaseContract {
    interface Model {

    }
    interface View {
        /**
         * 显示错误消息
         *
         * @param massage 欲显示的消息内容
         */
        void showErrorToast(String massage);

        /**
         * 显示成功消息
         *
         * @param massage 欲显示的消息内容
         */
        void showSuccessToast(String massage);

        /**
         * 显示信息消息
         *
         * @param massage 欲显示的消息内容
         */
        void showInfoToast(String massage);

        /**
         * 显示警告消息
         *
         * @param massage 欲显示的消息内容
         */
        void showWarningToast(String massage);

        /**
         * 显示普通消息
         *
         * @param massage 欲显示的消息内容
         */
        void showNormalToast(String massage);

        /**
         * 获取上下文对象
         *
         * @return 返回当前View的上下文对象
         */
        Context getContext();
    }

    interface Presenter<V extends IBaseContract.View, M extends IBaseContract.Model> {

        /**
         * 绑定View and Model
         *
         * @param view 欲绑定的视图，当前合约内的View
         */
        void attachView(V view);

        /**
         * 解绑View
         */
        void detachView();

        /**
         * 判断是否已经绑定View
         *
         * @return 返回绑定状态
         */
        boolean isAttached();
    }
}
