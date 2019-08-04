package com.asterism.fresk.contract;

/**
 * 本地数据添加合约接口，同时规定了View基础接口和Presenter基础接口
 *
 * @author lulushuiba
 * @email 1315269930@qq.com
 * @date on 2019-08-02 02:10
 */
public interface IAddBookLocalContract {
    interface View extends IBaseContract.View {
    }

    interface Presenter extends IBaseContract.Presenter<IAddBookLocalContract.View> {
    }
}
