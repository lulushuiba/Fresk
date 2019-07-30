package com.asterism.fresk.contract;

import android.content.Context;

/**
 * 基础合约接口，同时规定了View基础接口和Presenter基础接口
 *
 * @author Ashinch
 * @email Glaxyinfinite@outlook.com
 * @date on 2019-07-01 15:49
 */
public interface IBaseContract {

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
    }

    interface Presenter<V extends IBaseContract.View> {

        /**
         * 绑定View
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

        /**
         * 获取上下文对象
         *
         * @return 返回上下文对象
         */
        Context getContext();
    }
}
