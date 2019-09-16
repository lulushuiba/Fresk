package com.asterism.fresk.presenter;

import com.asterism.fresk.contract.IBaseContract;
import com.asterism.fresk.contract.INewBaseContract;

public abstract class NewBasePresenter<V extends IBaseContract.View, M extends IBaseContract.Model>
        implements INewBaseContract.Presenter<V, M>  {


    protected V mView; // 绑定的视图，子类可用
    protected M mModel; // 绑定的模型， 子类可用

    NewBasePresenter()
    {
        if(mModel == null) {
            mModel =  setModel();
        }
    }

    /**
     * 实例化该模块的Model对象
     *
     * @return 返回引用
     */
    protected abstract M setModel();

    /**
     * 实现 绑定视图
     *
     * @param view 欲绑定的视图，当前合约内的View
     */
    @Override
    public void attachView(V view) {
        this.mView = view;
    }

    /**
     * 实现 解绑视图
     */
    @Override
    public void detachView() {
        this.mView = null;
    }

    /**
     * 判断是否已经绑定View
     *
     * @return 返回绑定状态
     */
    @Override
    public boolean isAttached() {
        if(mView != null ){ return true; }
        return false;
    }
}
