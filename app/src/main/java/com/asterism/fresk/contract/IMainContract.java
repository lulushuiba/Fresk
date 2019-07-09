package com.asterism.fresk.contract;

/**
 * 主模块合约接口
 *
 * @author Ashinch
 * @email Glaxyinfinite@outlook.com
 * @date on 2019-07-09 16:31
 */
public interface IMainContract {

    interface View extends IBaseContract.View {

    }

    interface Presenter extends IBaseContract.Presenter<IMainContract.View> {

    }
}
