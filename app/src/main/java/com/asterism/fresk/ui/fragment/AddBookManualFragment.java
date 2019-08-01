package com.asterism.fresk.ui.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


import com.asterism.fresk.R;
import com.asterism.fresk.ui.adapter.DirectoryListAdapter;
import com.asterism.fresk.util.DirectoryUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddBookManualFragment extends Fragment {
    private Context ctx;

    private ListView listView;
    private TextView textView;
    //记录当前父文件夹
    private File currentParent;
    //记录当前路经下的所有文件
    private File[] currentFiles = null;
    private List<Map<String, Object>> listItems;
    private DirectoryListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_book_manual, container, false);
        ctx = getContext();
        initView(view);
        return view;
    }


    private void initView(View view) {
        listView = view.findViewById(R.id.lv_manual_files);
        textView = view.findViewById(R.id.tv_manual_path);
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());
        //获取系统SD卡目录
        File root = new File(Environment.getExternalStorageDirectory().getPath());
        if(root.exists()){
            currentParent = root;
            currentFiles = root.listFiles();
            //使用当前目录下的全部文件，来填充ListView
            inflateListView(currentFiles);

        listView.setOnItemClickListener(listViewItemOnClick);
    }

}

    private void inflateListView(File[] files) {
        //创建List集合，元素是Map
        listItems = new ArrayList<>();
        for (int i = 0; i < files.length; i++ ) {
            Map<String, Object> listItem = new HashMap<>();
            String type;
            //如果当前File是文件夹，使用文件夹图标，其它使用文件图标
            if (files[i].isDirectory()) {
                listItem.put("icon", R.drawable.icon_folder);
                type = "dir";
            }
            else if (DirectoryUtils.getFormatName(files[i].getName()).equals("txt") ||
                    DirectoryUtils.getFormatName(files[i].getName()).equals("epub") ||
                    DirectoryUtils.getFormatName(files[i].getName()).equals("pdf") ||
                    DirectoryUtils.getFormatName(files[i].getName()).equals("mobi")) {

                listItem.put("icon", R.drawable.icon_file);
                type = "file";
            }
            else {
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
                listItems.add(0,returnUp);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }




        //创建
        if (adapter == null){
            adapter = new DirectoryListAdapter(ctx, listItems);
        }else {
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


    private ListView.OnItemClickListener listViewItemOnClick = new ListView.OnItemClickListener() {
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
                }catch(Exception e){
                    e.printStackTrace();
                }
            }else{
                adapter.setBook(position);

                if(((DirectoryListAdapter.ViewHolder) view.getTag()).cbOption.isChecked()) {
                    adapter.setBook(position);
                    updateProgressPartly(position,false);

                }else {
                    adapter.setBook(position);
                    updateProgressPartly(position,true);
                }

            }

        }
    };

    private void updateProgressPartly(int position, boolean b){
        int firstVisiblePosition = listView.getFirstVisiblePosition();
        int lastVisiblePosition = listView.getLastVisiblePosition();
        if(position>=firstVisiblePosition && position<=lastVisiblePosition){
            View v = listView.getChildAt(position - firstVisiblePosition);
            if(v.getTag() instanceof DirectoryListAdapter.ViewHolder){
                DirectoryListAdapter.ViewHolder vh = (DirectoryListAdapter.ViewHolder)v.getTag();
                vh.cbOption.setChecked(b);
            }
        }
    }


}