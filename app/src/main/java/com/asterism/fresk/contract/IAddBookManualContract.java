package com.asterism.fresk.contract;

/**
 * 添加书籍手册合约接口，同时规定了View基础接口和Presenter基础接口
 *
 * @author lulushuiba
 * @email 1315269930@qq.com
 * @date on 2019-08-03 10:10
 */
public interface IAddBookManualContract {
    interface View extends IBaseContract.View {}

    interface Presenter extends IBaseContract.Presenter<IAddBookManualContract.View> {}
}
