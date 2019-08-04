package com.asterism.fresk.ui.fragment;

import android.annotation.SuppressLint;
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 手动查找添加书籍页面Fragment类，继承base基类且泛型为当前模块Presenter接口类型，并实现当前模块View接口
 *
 * @author lulushuiba
 * @email 1315269930@qq.com
 * @date on 2019-08-03 22:48
 */
public class AddBookManualFragment extends BaseFragment<IAddBookContract.Presenter>
        implements IAddBookContract.View {
    @BindView(R.id.lv_manual_files)
    ListView listView;

    //所在目录路径
    @BindView(R.id.tv_manual_path)
    TextView textView;

    //导入选中按钮
    @BindView(R.id.bt_loadSelect)
    Button btloadSelect;

    //记录当前父文件夹
    private File currentParent;

    //记录当前路经下的所有文件
    private File[] currentFiles = null;
    private List<Map<String, Object>> listItems;
    private DirectoryListAdapter adapter;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_add_book_manual;
    }

    @Override
    protected IAddBookContract.Presenter setPresenter() { return new AddBookPresenter(); }

    @Override
    protected void initialize() {
        //设置为可滑动
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());
        //获取系统SD卡目录
        File root = new File(Environment.getExternalStorageDirectory().getPath());
        if (root.exists()) {
            currentParent = root;
            currentFiles = root.listFiles();
            //使用当前目录下的全部文件，来填充ListView
            inflateListView(currentFiles);
            //绑定点击事件
            listView.setOnItemClickListener(listViewItemOnClick);
        }
        //设置初始值
        btloadSelect.setText( getResources().getString(R.string.LoadSelect)+"(" + adapter.book.size()+")");
    }

    /**
     * 填充ListView
     *
     * @param files
     */
    private void inflateListView(File[] files) {
        //创建List集合，元素是Map
        listItems = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            Map<String, Object> listItem = new HashMap<>();
            String type;
            //如果当前File是文件夹，使用文件夹图标，其它使用文件图标
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
                listItem.put("path", currentParent.getCanonicalPath() + "/");
            } catch (IOException e) {
                e.printStackTrace();
            }

            listItem.put("icon", files[i]);
            listItem.put("file", files[i].getName());
            listItem.put("type", type);
            //添加List项
            listItems.add(listItem);
        }

        listItems = DirectoryUtils.DirectorySort(listItems);
        try {
            if (!currentParent.getCanonicalPath().equals("/storage/emulated/0")) {
                Map<String, Object> returnUp = new HashMap<String, Object>();
                returnUp.put("icon", R.drawable.icon_folder);
                returnUp.put("file", "/..");
                returnUp.put("type", "dir");
                listItems.add(0, returnUp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //创建
        if (adapter == null) {
            adapter = new DirectoryListAdapter(getContext(), listItems);
        } else {
            adapter.setData(listItems);
        }

        //为ListView设置Adapter
        listView.setAdapter(adapter);
        try {
            textView.setText(currentParent.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Item的点击事件
     */
    private ListView.OnItemClickListener listViewItemOnClick = new ListView.OnItemClickListener() {
        @SuppressLint("SetTextI18n")
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (listItems.get(position).get("type").equals("dir")) {
                try {
                    //获取系统SD卡目录
                    File root = new File(currentParent.getCanonicalPath() + "/" + listItems.get(position).get("file"));
                    if (root.exists()) {
                        currentParent = root;
                        currentFiles = root.listFiles();
                        //使用当前目录下的全部文件，来填充ListView
                        inflateListView(currentFiles);
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
                //更新UI数据
                btloadSelect.setText( getResources().getString(R.string.LoadSelect)+"(" + adapter.book.size()+")");
            }
        }
    };

    /**
     * 当更新书籍选中与被选中时调用
     * @param position 被点击的位置
     * @param b 要设置的值
     */
    private void updateProgressPartly(int position, boolean b) {

        int firstVisiblePosition = listView.getFirstVisiblePosition();
        int lastVisiblePosition = listView.getLastVisiblePosition();
        if (position >= firstVisiblePosition && position <= lastVisiblePosition) {
            View v = listView.getChildAt(position - firstVisiblePosition);
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
    @OnClick({R.id.bt_loadSelect})
    public void onClick(View view) {
        switch (view.getId()) {
            // 点击导入选中
            case R.id.bt_loadSelect:
                if(adapter.getCount() >= 0) {
                    //临时存储要导入的书籍路径
                    List<String> preBookPathList = new ArrayList<>();
                    for (String key : adapter.book.keySet()){
                        preBookPathList.add(key);
                    }

                    mPresenter.addBooks(preBookPathList, new IAddBookContract.OnAddBooksListener() {
                        @Override
                        public void onSuccess() {
                            showSuccessToast("添加成功");
                            //查询加载MainActivity, 并清空栈中所有Activity
                            Intent intent = new Intent(mContext, MainActivity.class)
                                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailed(List<String> pathList) {
                            String temString = "导入失败：";
                            for(String s : pathList) {
                                s += "\n" + s;
                            }
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