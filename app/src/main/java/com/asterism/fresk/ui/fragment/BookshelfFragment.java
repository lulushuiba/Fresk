package com.asterism.fresk.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.asterism.fresk.R;
import com.asterism.fresk.contract.IBookContract;
import com.asterism.fresk.dao.bean.BookBean;
import com.asterism.fresk.presenter.BookPresenter;
import com.asterism.fresk.ui.adapter.BookshelfGridAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 书架页面Fragment类，继承base基类且泛型为当前模块Presenter接口类型，并实现当前模块View接口
 *
 * @author Ashinch
 * @email Glaxyinfinite@outlook.com
 * @date on 2019-07-11 18:37
 */
public class BookshelfFragment extends BaseFragment<IBookContract.Presenter>
        implements IBookContract.View {

    @BindView(R.id.gv_bookshelf)
    GridView gvBookshelf;
    Unbinder unbinder;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_bookshelf;
    }

    @Override
    protected IBookContract.Presenter setPresenter() {
        return new BookPresenter();
    }

    @Override
    protected void initialize() {
        mPresenter.getAllBooks(new IBookContract.OnBookListListener() {
            @Override
            public void onSuccess(List<BookBean> bookList) {
                // bookList根据阅读时间进行排序
                BookBean temp;
                for(int i = 0; i < bookList.size(); i++) {
                    for (int j = 0; j <bookList.size()-1-i; j++) {
                       int res=bookList.get(j).getReadDate().compareTo(bookList.get(j+1).getReadDate());
                        if(res<=0) {
                            temp = bookList.get(j);
                           bookList.set(j,bookList.get(j + 1));
                           bookList.set(j + 1, temp);
                        }
                    }
                }

                // 书架GridView适配器设置
                BookshelfGridAdapter adapter = new BookshelfGridAdapter(mContext, bookList);
                gvBookshelf.setAdapter(adapter);
            }

            @Override
            public void onError() {

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
