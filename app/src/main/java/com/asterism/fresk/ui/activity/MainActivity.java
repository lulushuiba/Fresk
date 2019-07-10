package com.asterism.fresk.ui.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.asterism.fresk.R;
import com.asterism.fresk.contract.IMainContract;
import com.asterism.fresk.presenter.MainPresenter;
import com.asterism.fresk.ui.adapter.PagerAdapter;
import com.asterism.fresk.ui.fragment.DeskFragment;
import com.asterism.fresk.ui.widget.ScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnItemSelected;

/**
 * 主要Activity类，继承base基类且泛型为当前模块Presenter接口类型，并实现当前模块View接口
 *
 * @author Ashinch
 * @email Glaxyinfinite@outlook.com
 * @date on 2019-07-09 16:48
 */
public class MainActivity extends BaseActivity<IMainContract.Presenter>
        implements IMainContract.View {

    @BindView(R.id.content_main)
    ScrollViewPager viewPager;          // 滚动视图容器

    @BindView(R.id.navigation_main)
    BottomNavigationView navigation;    // 底部导航栏

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
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new DeskFragment());


        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(),fragmentList);

        viewPager.setCanScroll(false);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(1);
        viewPager.setOffscreenPageLimit(3);

        navigation.setSelectedItemId(R.id.nav_desk);
    }

    /**
     * 底部导航栏 item选中事件
     * @param item 被选中的item
     */
    @OnItemSelected(R.id.navigation_main)
    public void onItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_write:
                viewPager.setCurrentItem(0);
                break;
            case R.id.nav_desk:
                viewPager.setCurrentItem(1);
                break;
            case R.id.nav_more:
                viewPager.setCurrentItem(2);
                break;
        }
    }
}
