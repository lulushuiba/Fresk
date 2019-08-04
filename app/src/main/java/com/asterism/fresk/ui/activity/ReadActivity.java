package com.asterism.fresk.ui.activity;

import android.widget.TextView;

import com.asterism.fresk.R;
import com.asterism.fresk.contract.IReadContract;
import com.asterism.fresk.presenter.ReadPresenter;

import butterknife.BindView;

/**
 * 阅读Activity类，继承base基类且泛型为当前模块Presenter类型，并实现当前模块View接口
 *
 * @author Ashinch
 * @email Glaxyinfinite@outlook.com
 * @date on 2019-08-03 21:18
 */
public class ReadActivity extends BaseActivity<IReadContract.Presenter>
        implements IReadContract.View {

    @BindView(R.id.text)
    TextView text;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_read;
    }

    @Override
    protected IReadContract.Presenter setPresenter() {
        return new ReadPresenter();
    }

    @Override
    protected void initialize() {
        text.setText(mPresenter.getEpub());
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
