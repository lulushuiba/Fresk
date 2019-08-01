package com.asterism.fresk.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageButton;

import com.asterism.fresk.R;
import com.asterism.fresk.contract.IAddBookLocalContract;
import com.asterism.fresk.presenter.AddBookLocalPresenter;
import com.asterism.fresk.ui.adapter.TabFragmentPagerAdapter;
import com.asterism.fresk.ui.fragment.AddBookAutoFragment;
import com.asterism.fresk.ui.fragment.AddBookManualFragment;
import com.asterism.fresk.ui.widget.ScrollViewPager;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AddBookLocalActivity  extends BaseActivity<IAddBookLocalContract.Presenter>
        implements IAddBookLocalContract.View {



   @BindView(R.id.navigation)
    protected TabLayout tabLayout;

    @BindView(R.id.content)
    protected ScrollViewPager viewPager;

    @BindView(R.id.btn_title_return)
    protected ImageButton btnTitleReturn;

    private TabFragmentPagerAdapter adapter;

    @Override
    protected int setLayoutId() { return  R.layout.activity_add_book_local; }

    @Override
    protected IAddBookLocalContract.Presenter setPresenter() { return new AddBookLocalPresenter(); }

    @Override
    protected void initialize() {
        tabLayout.setSelectedTabIndicatorColor(0xff666666);//设置选中时的指示器的颜色

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new AddBookAutoFragment());
        fragments.add(new AddBookManualFragment());

        List<String> titles = new ArrayList<>();
        titles.add("自动扫描");
        titles.add("手动选择");

        tabLayout.setupWithViewPager(viewPager);

        adapter = new TabFragmentPagerAdapter(getSupportFragmentManager(), fragments, titles);

        viewPager.setCanScroll(true);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(2);
    }


   @OnClick({R.id.btn_title_return})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_title_return:
                finish();
                break;
        } }
}
