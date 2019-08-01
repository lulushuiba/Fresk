package com.asterism.fresk.ui.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.asterism.fresk.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DirectoryListAdapter extends BaseAdapter {
    private Context ctx;
    private List<Map<String, Object>> list;
    private HashMap<String, Integer> book;


    //MyAdapter需要一个Context，通过Context获得Layout.inflater，然后通过inflater加载item的布局
    public DirectoryListAdapter(Context context, List<Map<String, Object>> data) {
        this.ctx = context;
        for (int i=0;i<data.size();i++) {
            data.get(i).put("check", false);
        }
        this.list = data;
        this.book = new HashMap<>();

    }

    public void setData(List<Map<String, Object>> data){
        this.list = data;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setBook(int position) {
        String path = list.get(position).get("path").toString() + "" + list.get(position).get("file").toString();

        if (book.getOrDefault(path,(-1)) == position) {
            book.remove(path);
        }else {
            book.put(path, position);
        }

    }

    @Override
    public int getCount() {
        //  返回 ListView Item 条目的总数
        return list.size();
    }

    //  得到 Item 代表的对象
    @Override
    public Map<String, Object> getItem(int position) {
        //  返回 ListView Item 条目代表的对象
        return list.get(position);
    }

    //  得到 Item 的 id
    @Override
    public long getItemId(int position) {
        //  返回 ListView Item 的 id
        return position;
    }

    //  得到 Item 的 View 视图
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;
        ViewHolder holder;
        if (convertView == null) {
            //  将 list_item.xml 文件找出来并转换成 View 对象
            view = View.inflate(ctx, R.layout.item_list_file, null);
            holder = new ViewHolder();
            holder.ivIcon = view.findViewById(R.id.img_item_list_icon);
            holder.tvName = view.findViewById(R.id.tv_item_list_text);
            holder.ivOption = view.findViewById(R.id.img_item_list_folder_forward);
            holder.cbOption = view.findViewById(R.id.cb_item_list_book);

            //把ViewHolder对象封装到View对象中
            view.setTag(holder);
        }else{
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }

        holder.cbOption.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String path = list.get(position).get("path").toString() + "" + list.get(position).get("file").toString();

                if (isChecked) {
                    if (book.getOrDefault(path,(-1)) != position) {

                        book.put(path,position);
                    }
                }else {
                    if (book.getOrDefault(path,(-1)) == position) {
                        book.remove(path);
                    }
                }

            }
        });

        if ("dir".equals(list.get(position).get("type"))) {
            holder.ivIcon.setImageResource(R.drawable.icon_folder);
            holder.ivOption.setVisibility(View.VISIBLE);
            holder.cbOption.setVisibility(View.GONE);

        }else {
            holder.ivIcon.setImageResource(R.drawable.icon_file);
            holder.ivOption.setVisibility(View.GONE);
            holder.cbOption.setVisibility(View.VISIBLE);

            String path = list.get(position).get("path").toString() + "" + list.get(position).get("file").toString();

            if (book.getOrDefault(path,(-1)) == position) {
//                Toast.makeText(ctx, path, Toast.LENGTH_SHORT).show();
                holder.cbOption.setChecked(true);
            }else {
                holder.cbOption.setChecked(false);
            }

        }

        holder.tvName.setText(list.get(position).get("file").toString());

        return view;
    }

    public static class ViewHolder{
        //条目的布局文件中有什么组件，这里就定义有什么属性
        public ImageView ivIcon;
        public TextView tvName;
        public ImageView ivOption;
        public CheckBox cbOption;

    }


}