package com.asterism.fresk.contract;

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
        String getEpub();
    }
}
