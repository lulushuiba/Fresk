package com.asterism.fresk.ui.fragment;

import com.asterism.fresk.R;
import com.asterism.fresk.contract.IAddBookContract;
import com.asterism.fresk.presenter.AddBookPresenter;

/**
 * 自动查找添加书籍页面Fragment类，继承base基类且泛型为当前模块Presenter接口类型，并实现当前模块View接口
 *
 * @author lulushuiba
 * @email 1315269930@qq.com
 * @date on 2019-08-04 11:17
 */
public class AddBookAutoFragment extends BaseFragment<IAddBookContract.Presenter>
        implements IAddBookContract.View {

    @Override
    protected int setLayoutId() { return R.layout.fragment_add_book_auto; }

    @Override
    protected IAddBookContract.Presenter setPresenter() { return new AddBookPresenter(); }

    @Override
    protected void initialize() {

    }

    @Override
    public void showAdding() {

    }

    @Override
    public void hideAdding() {

    }

    @Override
    public void showScanning() {

    }

    @Override
    public void hideScanning() {

    }
}