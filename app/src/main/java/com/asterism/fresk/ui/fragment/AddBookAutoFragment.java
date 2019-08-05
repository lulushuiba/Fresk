package com.asterism.fresk.ui.fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

import com.asterism.fresk.R;
import com.asterism.fresk.contract.IAddBookContract;
import com.asterism.fresk.presenter.AddBookPresenter;

/**
 * 自动添加书籍页面Fragment类，继承base基类且泛型为当前模块Presenter接口类型，并实现当前模块View接口
 *
 * @author lulushuiba
 * @email 1315269930@qq.com
 * @date on 2019-08-04 11:17
 */
public class AddBookAutoFragment extends BaseFragment<IAddBookContract.Presenter>
        implements IAddBookContract.View {

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

        View contentView = LayoutInflater.from(mContext).inflate(R.layout.poput_now_search, null);

        Button btCancel = contentView.findViewById(R.id.btCancel);

        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(false);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(false);

        backgroundAlpha((float) 0.5);
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        //显示PopupWindow
        View rootview = LayoutInflater.from(mContext).inflate(R.layout.fragment_add_book_auto, null);
        popupWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1);
            }
        });
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