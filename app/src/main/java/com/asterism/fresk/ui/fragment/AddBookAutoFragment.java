package com.asterism.fresk.ui.fragment;

import com.asterism.fresk.R;
import com.asterism.fresk.contract.IAddBookAutoContract;
import com.asterism.fresk.presenter.AddBookAutoPresenter;

/**
 * 自动查找添加书籍页面Fragment类，继承base基类且泛型为当前模块Presenter接口类型，并实现当前模块View接口
 *
 * @author lulushuiba
 * @email 1315269930@qq.com
 * @date on 2019-08-04 11:17
 */
public class AddBookAutoFragment extends BaseFragment<IAddBookAutoContract.Presenter>
        implements IAddBookAutoContract.View {

    @Override
    protected int setLayoutId() { return R.layout.fragment_add_book_auto; }

    @Override
    protected IAddBookAutoContract.Presenter setPresenter() { return new AddBookAutoPresenter(); }

    @Override
    protected void initialize() {

    }

}