package com.asterism.fresk.ui.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.asterism.fresk.R;
import com.asterism.fresk.contract.IAddBookContract;
import com.asterism.fresk.presenter.AddBookPresenter;
import com.asterism.fresk.ui.activity.MainActivity;
import com.asterism.fresk.ui.adapter.DirectoryListAdapter;
import com.asterism.fresk.util.DirectoryUtils;
import com.asterism.fresk.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 手动添加书籍页面Fragment类，继承base基类且泛型为当前模块Presenter接口类型，并实现当前模块View接口
 *
 * @author lulushuiba
 * @email 1315269930@qq.com
 * @date on 2019-08-03 22:48
 */
public class AddBookManualFragment extends BaseFragment<IAddBookContract.Presenter>
        implements IAddBookContract.View {

    @BindView(R.id.lv_manual_files)
    ListView filesListView;                 // 目录容器

    @BindView(R.id.tv_manual_path)
    TextView tvPath;                        // 当前路径 文本框

    @BindView(R.id.btn_loadSelect)
    Button btnLoadSelect;                   // 导入选中 按钮

    private File parent;                    // 当前父文件夹
    private File[] files = null;            // 当前路径下所有文件
    private DirectoryListAdapter adapter;   // 目录列表适配器

    /**
     * 文件信息集合
     * Map：名称、图标、路径、类型
     * key：name、icon、path、type
     * value：String、Drawable_Id、String、（dir、file）
     */
    private List<Map<String, Object>> listItems;
    /**
     * Item的点击事件
     */
    private ListView.OnItemClickListener listViewItemOnClick = new ListView.OnItemClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (listItems.get(position).get("type").equals("dir")) {
                try {
                    // 获取系统SD卡目录
                    File root = new File(AddBookManualFragment.this.parent.getCanonicalPath() + "/" + listItems.get(position).get("file"));
                    if (root.exists()) {
                        AddBookManualFragment.this.parent = root;
                        files = root.listFiles();
                        // 使用当前目录下的全部文件，来填充ListView
                        inflateListView(files);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                adapter.setBook(position);

                if (((DirectoryListAdapter.ViewHolder) view.getTag()).cbOption.isChecked()) {
                    adapter.setBook(position);
                    updateProgressPartly(position, false);
                } else {
                    adapter.setBook(position);
                    updateProgressPartly(position, true);
                }
                // 更新UI数据
                btnLoadSelect.setText(getResources().getString(R.string.LoadSelect) + "(" + adapter.book.size() + ")");
            }
        }
    };

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_add_book_manual;
    }

    @Override
    protected IAddBookContract.Presenter setPresenter() {
        return new AddBookPresenter();
    }

    @Override
    protected void initialize() {
        // 设置为可滑动
        tvPath.setMovementMethod(ScrollingMovementMethod.getInstance());
        // 获取系统SD卡目录
        File root = new File(Environment.getExternalStorageDirectory().getPath());
        if (root.exists()) {
            parent = root;
            files = root.listFiles();
            // 使用当前目录下的全部文件，来填充ListView
            inflateListView(files);
            // 绑定点击事件
            filesListView.setOnItemClickListener(listViewItemOnClick);
        }
        // 设置初始值
        btnLoadSelect.setText(getResources().getString(R.string.LoadSelect) + "(" + adapter.book.size() + ")");
    }

    /**
     * 填充ListView
     *
     * @param files 文件数组
     */
    private void inflateListView(File[] files) {
        // 创建List集合，元素是Map
        listItems = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            Map<String, Object> listItem = new HashMap<>();
            String type;
            // 如果当前File是文件夹，使用文件夹图标，其它使用文件图标
            if (files[i].isDirectory()) {
                listItem.put("icon", R.drawable.icon_folder);
                type = "dir";
            } else if (DirectoryUtils.getFormatName(files[i].getName()).equals("txt") ||
                    DirectoryUtils.getFormatName(files[i].getName()).equals("epub") ||
                    DirectoryUtils.getFormatName(files[i].getName()).equals("pdf") ||
                    DirectoryUtils.getFormatName(files[i].getName()).equals("mobi")) {

                listItem.put("icon", R.drawable.icon_file);
                type = "file";
            } else {
                continue;
            }

            try {
                listItem.put("path", parent.getCanonicalPath() + "/");
            } catch (IOException e) {
                e.printStackTrace();
            }

            listItem.put("icon", files[i]);
            listItem.put("file", files[i].getName());
            listItem.put("type", type);
            // 添加List项
            listItems.add(listItem);
        }

        listItems = DirectoryUtils.DirectorySort(listItems);
        try {
            if (!parent.getCanonicalPath().equals("/storage/emulated/0")) {
                Map<String, Object> returnUp = new HashMap<String, Object>();
                returnUp.put("icon", R.drawable.icon_folder);
                returnUp.put("file", "/..");
                returnUp.put("type", "dir");
                listItems.add(0, returnUp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 创建Adapter
        if (adapter == null) {
            adapter = new DirectoryListAdapter(getContext(), listItems);
        } else {
            adapter.setData(listItems);
        }

        // 为ListView设置Adapter
        filesListView.setAdapter(adapter);
        try {
            tvPath.setText(parent.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
     * 按钮点击事件
     *
     * @param view 导入加载按钮
     */
    @OnClick({R.id.btn_loadSelect})
    public void onClick(View view) {
        switch (view.getId()) {
            // 点击导入选中
            case R.id.btn_loadSelect:
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
                            showErrorToast(message);
                        }
                    });
                }
        }
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
}