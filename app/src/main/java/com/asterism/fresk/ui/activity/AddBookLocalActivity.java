package com.asterism.fresk.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageButton;

import com.asterism.fresk.R;
import com.asterism.fresk.contract.IAddBookContract;
import com.asterism.fresk.presenter.AddBookPresenter;
import com.asterism.fresk.ui.adapter.PagerAdapter;
import com.asterism.fresk.ui.fragment.AddBookAutoFragment;
import com.asterism.fresk.ui.fragment.AddBookManualFragment;
import com.asterism.fresk.ui.widget.ScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AddBookLocalActivity extends BaseActivity<IAddBookContract.Presenter>
        implements IAddBookContract.View {

    @BindView(R.id.navigation)
    TabLayout tabLayout;                //

    @BindView(R.id.content)
    ScrollViewPager viewPager;          // 滚动视图容器

    @BindView(R.id.btn_title_return)
    ImageButton btnTitleReturn;         // 返回 按钮

    @Override
    protected int setLayoutId() {
        return R.layout.activity_add_book_local;
    }

    @Override
    protected IAddBookContract.Presenter setPresenter() {
        return new AddBookPresenter();
    }

    @Override
    protected void initialize() {
        // 碎片集合
        List<Fragment> fragments = new ArrayList<>();
        // 自动添加书籍页面碎片
        fragments.add(new AddBookAutoFragment());
        // 手动添加书籍页面碎片
        fragments.add(new AddBookManualFragment());

        // 设置页面标题
        List<String> titles = new ArrayList<>();
        titles.add(getString(R.string.addBookAuto));
        titles.add(getString(R.string.addBookManual));

        // 设置滚动视图适配器
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager.setCanScroll(true);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(1);

        // 设置选中时的指示器的颜色
        tabLayout.setSelectedTabIndicatorColor(0xff666666);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void showAdding() {

    }

    @Override
    public void hideAdding() {

    }

    @Override
    public void showScanning() {

    }

    @Override
    public void hideScanning() {

    }

    @OnClick({R.id.btn_title_return})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_title_return:
                finish();
                break;
        }
    }
}
