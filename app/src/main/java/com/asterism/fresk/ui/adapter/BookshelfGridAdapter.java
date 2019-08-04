package com.asterism.fresk.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.asterism.fresk.R;
import com.asterism.fresk.dao.bean.BookBean;

import java.io.File;
import java.util.List;

/**
 * 书桌GridView适配器类
 */
public class BookshelfGridAdapter extends BaseAdapter {

    private List<BookBean> mList;
    private Context ctx;

    public BookshelfGridAdapter(Context ctx, List<BookBean> list) {
        this.mList = list;
        this.ctx = ctx;
    }

    // 得到 item 的总数
    @Override
    public int getCount() {
        return mList.size();
    }

    // 得到 item 代表的对象
    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    // 得到 item 的 id
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 得到 item 的 View 视图
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 将 list_item.xml 文件找出来并转换成 View 对象
        View view = View.inflate(ctx,
                R.layout.item_grid_book, null);

        ImageView imgBook = view.findViewById(R.id.img_item_book);
        TextView tvBookName = view.findViewById(R.id.tv_item_bookname);
        TextView tvProgress = view.findViewById(R.id.tv_item_progress);

        imgBook.setImageURI(Uri.fromFile(new File(mList.get(position).getPicName())));
        tvBookName.setText(mList.get(position).getName().trim());
        tvProgress.setText(mList.get(position).getReadProgress() + "%");

        return view;
    }
}
