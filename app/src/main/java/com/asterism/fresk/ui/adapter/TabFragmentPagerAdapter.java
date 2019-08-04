package com.asterism.fresk.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class TabFragmentPagerAdapter extends FragmentPagerAdapter {

    private FragmentManager mfragmentManager;
    private List<Fragment> mList;
    private List<String> mTitles;

    public TabFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.mList = list;
    }

    public TabFragmentPagerAdapter(FragmentManager fm, List<Fragment> list, List<String> titles) {
        super(fm);
        this.mList = list;
        this.mTitles = titles;
    }

    //  得到 Item 代表的对象
    @Override
    public Fragment getItem(int i) {
        return mList.get(i);
    }

    //  得到 item 的总数
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
