package com.asterism.fresk.ui.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.asterism.fresk.R;
import com.asterism.fresk.contract.IMainContract;
import com.asterism.fresk.presenter.MainPresenter;
import com.asterism.fresk.ui.widget.ScrollViewPager;

import butterknife.BindView;
import butterknife.OnItemSelected;

/**
 * 主活动
 *
 * @author Ashinch
 * @email Glaxyinfinite@outlook.com
 * @date on 2019-07-09 16:48
 */
public class MainActivity extends BaseActivity<IMainContract.Presenter>
        implements IMainContract.View {

    @BindView(R.id.content_main)
    ScrollViewPager viewPager;

    @BindView(R.id.navigation_main)
    BottomNavigationView navigation;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected IMainContract.Presenter setPresenter() {
        return new MainPresenter();
    }

    @Override
    protected void initialize() {

    }

    @OnItemSelected(R.id.navigation_main)
    public void onItemSelected(@NonNull MenuItem item) {

    }
}
