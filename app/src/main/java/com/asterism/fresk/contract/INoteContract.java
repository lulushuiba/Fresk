package com.asterism.fresk.contract;

import com.asterism.fresk.dao.bean.NoteBean;

import java.util.List;

/**
 * 笔记模块合约接口
 *
 * @author Ashinch
 * @email Glaxyinfinite@outlook.com
 * @date on 2019-07-01 23:42
 */
public interface INoteContract {

    interface View extends IBaseContract.View {
        /**
         * 显示正在加载
         */
        void showLoading();

        /**
         * 隐藏正在加载
         */
        void hideLoading();
    }

    interface Presenter extends IBaseContract.Presenter<INoteContract.View> {

        /**
         * 获取所有笔记
         *
         * @param listener 监听器
         */
        void getAllNotes(OnNoteBeanListener listener);
    }

    interface OnNoteBeanListener {
        /**
         * NoteBean相关操作成功事件
         *
         * @param noteList 回调操作的NoteBean集合
         */
        void onSuccess(List<NoteBean> noteList);

        /**
         * BookBean相关操作失败事件
         */
        void onError();
    }
}
