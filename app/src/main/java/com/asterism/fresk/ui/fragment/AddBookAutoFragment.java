package com.asterism.fresk.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.asterism.fresk.R;
import com.asterism.fresk.contract.IAddBookContract;
import com.asterism.fresk.dao.BookDao;
import com.asterism.fresk.dao.BookTypeDao;
import com.asterism.fresk.dao.bean.BookTypeBean;
import com.asterism.fresk.presenter.AddBookPresenter;
import com.asterism.fresk.ui.activity.MainActivity;
import com.asterism.fresk.ui.adapter.DirectoryListAdapter;
import com.asterism.fresk.util.FileSizeUtil;
import com.asterism.fresk.util.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 自动添加书籍页面Fragment类，继承base基类且泛型为当前模块Presenter接口类型，并实现当前模块View接口
 *
 * @author lulushuiba
 * @email 1315269930@qq.com
 * @date on 2019-08-04 11:17
 */
public class AddBookAutoFragment extends BaseFragment<IAddBookContract.Presenter>
        implements IAddBookContract.View {

    @BindView(R.id.lv_manual_files)
    ListView filesListView;                 // 目录容器

    @BindView(R.id.btn_import_select)
    Button btnImportSelect;                 // 导入选中 按钮

    private DirectoryListAdapter adapter;   // 目录列表适配器
    PopupWindow popupWindow =  null;
    final List<Map<String, Object>> fileList = new ArrayList<>();


    @Override
    protected int setLayoutId() {
        return R.layout.fragment_add_book_auto;
    }

    @Override
    protected IAddBookContract.Presenter setPresenter() {
        return new AddBookPresenter();
    }

    @Override
    protected void initialize() {



        //显示正在加载窗口
        ShowNowLoadWindow();

        BookTypeDao b =  new BookTypeDao(mContext);
        Set<String> set = new HashSet<>();
        for(BookTypeBean s : b.selectAll()){
            set.add(s.getType());
        }
        mPresenter.scanBooks(set, new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onNext(String path) {

                Map<String, Object> itemMap = new HashMap<>();
                //加载图标
                itemMap.put("icon", R.drawable.icon_file);
                itemMap.put("path", path);

                // 记录文件名称，例：123.txt
                itemMap.put("name", FileUtils.getFileSimpleName(path) + "." + FileUtils.getFileSuffixName(path));
                File file = new File(path);
                BookDao dao = new BookDao(getContext());
                String Type = "file";
                if( dao.queryIsExistByPath(path) ){
                    Type = "already_file";
                }
                // 记录文件类型
                itemMap.put("type", Type);
                //记录大小
                itemMap.put("size",  FileSizeUtil.getAutoFileOrFilesSize(file.getPath()));
                //记录时间
                itemMap.put("time", FileUtils.GetShowFileTime(path));

                fileList.add(itemMap);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                //填充item
                inflateListView(fileList);
                //关闭加载框
                dismissNowLoadWindow();
            }
        });

        // 绑定点击事件
        filesListView.setOnItemClickListener(listViewItemOnClick);
    }

    //
    private void dismissNowLoadWindow(){
        popupWindow.dismiss();
    }

    /**
     * 显示正在加载窗口
     */
    private void ShowNowLoadWindow(){

        View contentView = LayoutInflater.from(mContext).inflate(R.layout.poput_now_search, null);
        Button btCancel = contentView.findViewById(R.id.btCancel);

        if(popupWindow == null){
             popupWindow = new PopupWindow(contentView,
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        popupWindow.setFocusable(false);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(false);

        //设置透明度
        backgroundAlpha((float) 0.5);
        //绑定点击事件
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissNowLoadWindow();
            }
        });
        //显示PopupWindow
        View rootview = LayoutInflater.from(mContext).inflate(R.layout.fragment_add_book_auto, null);
        popupWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);
        //设置Dismiss事件
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1);
            }
        });
    }

    /**
     * Item的点击事件
     */
    private ListView.OnItemClickListener listViewItemOnClick = new ListView.OnItemClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

           if(fileList.get(position).get("type").equals("file")){

                if (((DirectoryListAdapter.ViewHolder) view.getTag()).cbOption.isChecked()) {
                    adapter.setBook(position);
                    updateProgressPartly(position, false);
                } else {
                    adapter.setBook(position);
                    updateProgressPartly(position, true);
                }

                List<String > li = new ArrayList<>(adapter.book.keySet());

                // 更新UI数据
                btnImportSelect.setText(getResources().getString(R.string.importSelect) + "(" + adapter.book.size() + ")");

                //当点击的为already_file()类型时
            }else if(fileList.get(position).get("type").equals("already_file")){
                //无需操作
            }
            else {
                showSuccessToast("错误，未知的类型+ ");
            }
        }
    };

    /**
     * 当更新书籍选中与被选中时调用
     *
     * @param position 被点击的位置
     * @param b        要设置的值
     */
    private void updateProgressPartly(int position, boolean b) {
        int firstVisiblePosition = filesListView.getFirstVisiblePosition();
        int lastVisiblePosition = filesListView.getLastVisiblePosition();
        if (position >= firstVisiblePosition && position <= lastVisiblePosition) {
            View v = filesListView.getChildAt(position - firstVisiblePosition);
            if (v.getTag() instanceof DirectoryListAdapter.ViewHolder) {
                DirectoryListAdapter.ViewHolder vh = (DirectoryListAdapter.ViewHolder) v.getTag();
                vh.cbOption.setChecked(b);
            }
        }
    }

    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }

    /**
     * 填充ListView
     *
     * @param fileList 文件数组
     */
    private void inflateListView(List<Map<String, Object>> fileList) {

        // 创建Adapter
        if (adapter == null) {
            adapter = new DirectoryListAdapter(getContext(), fileList);
        } else {
            adapter.setData(fileList);
        }
        // 为ListView设置Adapter
        filesListView.setAdapter(adapter);
    }

    /**
     * 按钮点击事件
     *
     * @param view 导入加载按钮
     */
    @OnClick({R.id.btn_import_select, R.id.btn_title_filter})
    public void onClick(View view) {
        switch (view.getId()) {
            // 点击导入选中
            case R.id.btn_import_select:
                if (adapter.getCount() >= 0) {
                    // 临时存储要导入的书籍路径
                    List<String> preBookPathList = new ArrayList<>(adapter.book.keySet());
                    if (preBookPathList.size() < 1) {
                        break;
                    }
                    mPresenter.addBooks(preBookPathList, new IAddBookContract.OnAddBooksListener() {
                        @Override
                        public void onSuccess() {
                            showSuccessToast("添加成功");
                            // 查询加载MainActivity, 并清空栈中所有Activity
                            Intent intent = new Intent(mContext, MainActivity.class)
                                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailed(List<String> pathList) {
                            StringBuilder buffer = new StringBuilder("导入失败: ");
                            for (String s : pathList) {
                                buffer.append(FileUtils.getFileSimpleName(s)).append(", ");
                            }
                            showWarningToast(buffer.toString());
                        }

                        @Override
                        public void onError(String message) {
                            showErrorToast("导入书籍错误: " + message);
                        }
                    });
                }
                break;
        }
    }

    @Override
    public Context GetContext() { return super.getContext(); }

    @Override
    public void showAdding() { }

    @Override
    public void hideAdding() { }

    @Override
    public void showScanning() { }

    @Override
    public void hideScanning() { }
}