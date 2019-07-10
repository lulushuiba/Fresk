package com.asterism.fresk.ui.fragment;

import com.asterism.fresk.R;
import com.asterism.fresk.contract.IBookContract;
import com.asterism.fresk.presenter.BookPresenter;

/**
 * 更多页面Fragment类，继承base基类且泛型为当前模块Presenter接口类型，并实现当前模块View接口
 *
 * @author Ashinch
 * @email Glaxyinfinite@outlook.com
 * @date on 2019-07-10 16:10
 */
public class MoreFragment extends BaseFragment<IBookContract.Presenter>
        implements IBookContract.View {

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_more;
    }

    @Override
    protected IBookContract.Presenter setPresenter() {
        return new BookPresenter();
    }

    @Override
    protected void initialize() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showRemoving() {

    }

    @Override
    public void hideRemoving() {

    }
}
