package com.asterism.fresk.ui.fragment;

import com.asterism.fresk.R;
import com.asterism.fresk.contract.INoteContract;
import com.asterism.fresk.presenter.NotePresenter;

/**
 * 笔记页面Fragment类，继承base基类且泛型为当前模块Presenter接口类型，并实现当前模块View接口
 *
 * @author Ashinch
 * @email Glaxyinfinite@outlook.com
 * @date on 2019-07-10 16:06
 */
public class NoteFragment extends BaseFragment<INoteContract.Presenter>
        implements INoteContract.View {

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_note;
    }

    @Override
    protected INoteContract.Presenter setPresenter() {
        return new NotePresenter();
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
}
