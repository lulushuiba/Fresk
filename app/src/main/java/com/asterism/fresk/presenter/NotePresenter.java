package com.asterism.fresk.presenter;

import android.annotation.SuppressLint;

import com.asterism.fresk.contract.INoteContract;
import com.asterism.fresk.dao.NoteDao;
import com.asterism.fresk.dao.bean.NoteBean;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 笔记模块Presenter类，继承base基类且泛型为当前模块View接口类型，并实现当前模块Presenter接口
 *
 * @author lulushuiba
 * @email 1315269930@qq.com
 * @date on 2019-07-10 15:40:56
 */
public class NotePresenter extends BasePresenter<INoteContract.View>
        implements INoteContract.Presenter {

    /**
     * 实现 获取所有笔记
     *
     * @param listener 监听器
     */
    @SuppressLint("CheckResult")
    @Override
    public void getAllNotes(final INoteContract.OnNoteBeanListener listener) {
        // 显示正在加载
        mView.showLoading();

        // 创建被观察者，传递查询到的所有笔记实体类集合。
        Observable<List<NoteBean>> NoteObservable
                = Observable.create(new ObservableOnSubscribe<List<NoteBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<NoteBean>> emitter) throws Exception {
                // 初始化笔记类型表访问器
                NoteDao noteDao = new NoteDao(getContext());
                emitter.onNext(noteDao.selectAll());
                emitter.onComplete();
            }
        });

        // 处理于IO子线程
        NoteObservable.subscribeOn(Schedulers.io())
                // 响应于Android主线程
                .observeOn(AndroidSchedulers.mainThread())
                // 设置订阅的响应事件
                .subscribe(new Consumer<List<NoteBean>>() {
                    @Override
                    public void accept(List<NoteBean> noteBeans) throws Exception {
                        // 隐藏正在加载
                        mView.hideLoading();
                        if (noteBeans != null) {
                            listener.onSuccess(noteBeans);
                        } else {
                            listener.onError();
                        }
                    }
                });
    }
}
