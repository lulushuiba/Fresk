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
import com.asterism.fresk.ui.fragment.MoreFragment;
import com.asterism.fresk.ui.fragment.NoteFragment;
import com.asterism.fresk.ui.widget.ScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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
        // 碎片集合
        List<Fragment> fragmentList = new ArrayList<>();
        // 添加笔记页面碎片
        fragmentList.add(new NoteFragment());
        // 添加书桌页面碎片
        fragmentList.add(new DeskFragment());
        // 添加更多页面碎片
        fragmentList.add(new MoreFragment());

        // 设置滚动视图适配器
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragmentList);

        // 为滚动视图容器设置不可滚动
        viewPager.setCanScroll(false);
        // 为滚动视图容器设置适配器
        viewPager.setAdapter(pagerAdapter);
        // 为滚动视图容器设置默认当前页面为第二页（书桌页面）
        viewPager.setCurrentItem(1);
        // 为滚动视图容器设置屏幕外最大页面数量为3页
        viewPager.setOffscreenPageLimit(3);

        // 为底部导航栏设置默认选中书桌页面按钮
        navigation.setSelectedItemId(R.id.nav_desk);
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
    }

    /**
     * 底部导航栏 item选中事件监听器
     *
     * @param menuItem 被选中的item
     * @return
     */
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                // 切换为笔记页面
                case R.id.nav_note:
                    viewPager.setCurrentItem(0);
                    return true;
                // 切换为书桌页面
                case R.id.nav_desk:
                    viewPager.setCurrentItem(1);
                    return true;
                // 切换为更多页面
                case R.id.nav_more:
                    viewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };
}
