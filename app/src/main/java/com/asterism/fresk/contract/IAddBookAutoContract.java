package com.asterism.fresk.contract;

/**
 * 自动查找添加书籍合约接口，同时规定了View基础接口和Presenter基础接口
 *
 * @author lulushuiba
 * @email 1315269930@qq.com
 * @date on 2019-08-04 11:01
 */
public interface IAddBookAutoContract {
    interface View extends IBaseContract.View {}

    interface Presenter extends IBaseContract.Presenter<IAddBookAutoContract.View> {}
}
