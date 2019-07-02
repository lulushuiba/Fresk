package com.asterism.fresk.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 碎片ViewPager适配器类
 *
 * @author Ashinch
 * @email Glaxyinfinite@outlook.com
 * @date on 2019-07-01 16:14
 */
public class PagerAdapter extends FragmentPagerAdapter {

    private FragmentManager mfragmentManager;
    private List<Fragment> mList;
    private List<String> mTitles;

    public PagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.mList = list;
    }

    public PagerAdapter(FragmentManager fm, List<Fragment> list, List<String> titles) {
        super(fm);
        this.mList = list;
        this.mTitles = titles;
    }

    /**
     * 获取item对应的对象
     *
     * @param i 指定item位置
     *
     * @return 返回碎片集合中指定位置的碎片对象引用
     */
    @Override
    public Fragment getItem(int i) {
        return mList.get(i);
    }

    /**
     * 获取item总数
     *
     * @return 返回集合长度
     */
    @Override
    public int getCount() {
        return mList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
