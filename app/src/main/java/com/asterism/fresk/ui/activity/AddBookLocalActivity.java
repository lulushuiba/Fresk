package com.asterism.fresk.ui.activity;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.asterism.fresk.R;
import com.asterism.fresk.contract.IAddBookContract;
import com.asterism.fresk.dao.BookTypeDao;
import com.asterism.fresk.dao.bean.BookTypeBean;
import com.asterism.fresk.presenter.AddBookPresenter;
import com.asterism.fresk.ui.adapter.PagerAdapter;
import com.asterism.fresk.ui.fragment.AddBookAutoFragment;
import com.asterism.fresk.ui.fragment.AddBookManualFragment;
import com.asterism.fresk.ui.widget.ScrollViewPager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @BindView(R.id.btn_title_filter)
    ImageButton btnTitleFilter;         // 筛选 按钮

    //得到书籍类型dao
    BookTypeDao typeDao = null;

    PopupWindow popupFilterWindow = null;  //筛选窗口 弹出窗口

    Set<String> hsFilterShowType = new HashSet<>();  // 要显示的文件类型

    //筛选的最小文件大小输入框
    EditText etMinFileSize = null;
    //筛选的最大文件大小数据库
    EditText etMaxFileSize = null;

    Button btFilterConfirm;   //筛选窗 确认按钮
    Button btFilterAnew;      //筛选窗 重置按钮

    LinearLayout checkLine;   //筛选窗 复选框的容器

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

        //得到书籍类型dao
        if(null == typeDao) { typeDao = new BookTypeDao(this);}

        //得到所有书籍的类型
        List<BookTypeBean> listTypeBean =  typeDao.selectAll();
        //传入到set中
        for(BookTypeBean bean : listTypeBean){
            hsFilterShowType.add(bean.getType());
        }

        //初始化筛选窗口
        initFilterWindow();
    }

    @Override
    public Context GetContext() { return this; }

    @Override
    public void showAdding() { }

    @Override
    public void hideAdding() { }

    @Override
    public void showScanning() { }

    @Override
    public void hideScanning() {

    }

    @OnClick({R.id.btn_title_return, R.id.btn_title_filter})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_title_return:
                finish();
                break;
            case R.id.btn_title_filter:
                //显示筛选窗口
                ShowFilterWindow();
                break;
        }
    }

    /**
     * 初始化筛选窗口
     */
    private void initFilterWindow(){
        View contentView = LayoutInflater.from(this).inflate(R.layout.poput_filter, null);

        //得到确定按钮
        btFilterConfirm = contentView.findViewById(R.id.btConfirm);
        //得到重置按钮
        btFilterAnew = contentView.findViewById(R.id.btAnew);
        //得到复选框的容器 （LinearLayout）
        checkLine = contentView.findViewById(R.id.ll_type_check_list);
        //得到最小文件大小输入框
        etMinFileSize = contentView.findViewById(R.id.etMinFileSize);
        //得到最大文件大小输入框
        etMaxFileSize = contentView.findViewById(R.id.etMaxFileSize);
        //得到书籍类型dao
        if(null == typeDao) { typeDao = new BookTypeDao(this);}
        //添加复习框到弹出按钮
        for(BookTypeBean bean : typeDao.selectAll()){
            CheckBox temCheckBox = new CheckBox(this);
            temCheckBox.setText(bean.getType());
            temCheckBox.setChecked(true);
            checkLine.addView(temCheckBox);
        }

        btFilterConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterConfirm();
            }
        });

        popupFilterWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //触摸窗口外消失
        popupFilterWindow.setOutsideTouchable(true);
        //让触控聚焦于弹出窗口. 主要解决，弹出窗口未消失时点击外部会触发其他控件
        popupFilterWindow.setFocusable(true);

        //设置Dismiss事件
        popupFilterWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1);
            }
        });
    }

    /**
     * 筛选确认
     */
    private void FilterConfirm(){
        //临时CheckBox
        CheckBox temCheckBox = null;
        //清空
        hsFilterShowType.clear();
        //查看所有的复习框
        for(int i = 0; i < checkLine.getChildCount(); i++){
            temCheckBox = (CheckBox)checkLine.getChildAt(i);
            //假若勾选了，则添加到Set中
            if(temCheckBox.isChecked()){
                hsFilterShowType.add(temCheckBox.getText().toString());
            }
        }
        //关闭窗口
        if(null != popupFilterWindow){ popupFilterWindow.dismiss();}
    }

    /**
     * 显示筛选窗口
     */
    private void ShowFilterWindow(){
        backgroundAlpha((float) 0.5);
        //显示PopupWindow
        View rootview = LayoutInflater.from(this).inflate(R.layout.activity_add_book_local, null);
        //居中显示PopupWindow
        popupFilterWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);

    }

    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }
}
