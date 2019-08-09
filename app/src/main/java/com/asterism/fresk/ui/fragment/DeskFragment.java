package com.asterism.fresk.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.asterism.fresk.R;
import com.asterism.fresk.contract.IBookContract;
import com.asterism.fresk.dao.BookDao;
import com.asterism.fresk.dao.bean.BookBean;
import com.asterism.fresk.presenter.BookPresenter;
import com.asterism.fresk.ui.activity.ReadEpubActivity;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 书桌页面Fragment类，继承base基类且泛型为当前模块Presenter接口类型，并实现当前模块View接口
 *
 * @author Ashinch
 * @email Glaxyinfinite@outlook.com
 * @date on 2019-07-10 16:10
 */
public class DeskFragment extends BaseFragment<IBookContract.Presenter>
        implements IBookContract.View {

    @BindView(R.id.img_book_Pic)
    ImageView imgBookPic;

    @BindView(R.id.tv_book_name)
    TextView tvBookName;

    @BindView(R.id.tv_last_chapter)
    TextView tvLastChapter;

    @BindView(R.id.tv_read_progress)
    TextView tvReadProgress;

    private int pos = 0;
    private BookBean mBookBean;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_desk;
    }

    @Override
    protected IBookContract.Presenter setPresenter() {
        return new BookPresenter();
    }

    @Override
    protected void initialize() {
        if (getArguments() != null) {
            pos = getArguments().getInt("pos", 0);
        }
        mPresenter.getBookByIndexSortReadDate(pos, new IBookContract.OnBookBeanListener() {
            @Override
            public void onSuccess(BookBean bookBean) {
                mBookBean = bookBean;
                imgBookPic.setImageURI(Uri.fromFile(new File(bookBean.getPicName())));
                tvBookName.setText(bookBean.getName());
                tvLastChapter.setText(bookBean.getLastChapter());
                tvReadProgress.setText(bookBean.getReadProgress() + "%");
            }

            @Override
            public void onError(String message) {
                showErrorToast("获取书桌书籍错误: " + message);
            }
        });
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showRemoving() {

    }

    @Override
    public void hideRemoving() {

    }

    @OnClick(R.id.img_book_Pic)
    public void onClick() {
        if (mBookBean != null) {
            startActivity(new Intent(mContext, ReadEpubActivity.class).putExtra("path",mBookBean.getFilePath()));
        }
    }
}
