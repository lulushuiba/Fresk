package com.asterism.fresk.ui.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.asterism.fresk.R;
import com.asterism.fresk.contract.IAddBookContract;
import com.asterism.fresk.contract.IMainContract;
import com.asterism.fresk.presenter.AddBookPresenter;
import com.asterism.fresk.presenter.MainPresenter;
import com.asterism.fresk.ui.adapter.PagerAdapter;
import com.asterism.fresk.ui.fragment.BookFragment;
import com.asterism.fresk.ui.fragment.MoreFragment;
import com.asterism.fresk.ui.fragment.NoteFragment;
import com.asterism.fresk.ui.widget.ScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 主要Activity类，继承base基类且泛型为当前模块Presenter类型，并实现当前模块View接口
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
    BottomNavigationBar bottomNavigationBar ;    // 底部导航栏

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
        //初始化底部导航栏
        initBottomNavigationBar();
        // 碎片集合
        List<Fragment> fragmentList = new ArrayList<>();
        // 添加笔记页面碎片
        fragmentList.add(new NoteFragment());
        // 添加书桌页面碎片
        fragmentList.add(new BookFragment());
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
    }

    /**
     * 初始化底部导航栏
     */
    void initBottomNavigationBar(){
        /*2.进行必要的设置*/
        bottomNavigationBar.setBarBackgroundColor(R.color.colorPrimary);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC );
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED );//适应大小
        /*3.添加Tab*/
        bottomNavigationBar
                .addItem(new BottomNavigationItem(
                        R.drawable.nav_write)
                    .setActiveColor(R.color.colorBlack))
                .addItem(new BottomNavigationItem(
                        R.drawable.nav_desk)
                    .setActiveColor(R.color.colorBlack))
                .addItem(new BottomNavigationItem(
                        R.drawable.nav_more)
                    .setActiveColor(R.color.colorBlack))
                .setFirstSelectedPosition(1)//默认显示面板
                .initialise();//初始化

        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                switch (position){
                    case 0:
                        viewPager.setCurrentItem(0);
                        break;
                    case 1:
                        viewPager.setCurrentItem(1);
                        break;
                    case 2:
                        viewPager.setCurrentItem(2);
                        break;
                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position)
            {

            }
        });
    }
}
