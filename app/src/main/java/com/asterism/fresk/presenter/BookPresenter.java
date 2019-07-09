package com.asterism.fresk.presenter;


import android.annotation.SuppressLint;

import com.asterism.fresk.contract.IBookContract;
import com.asterism.fresk.dao.BookDao;
import com.asterism.fresk.dao.bean.BookBean;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 书籍模块Presenter类，继承base基类且泛型为当前模块View接口类型，并实现当前模块Presenter接口
 *
 * @author Rainydays
 * @email 2036361118@qq.com
 * @date on 2019-07-09 19:14:56
 */
public class BookPresenter extends BasePresenter<IBookContract.View>
        implements IBookContract.Presenter {

    /**
     * 实现 从数据库内获取所有书籍
     *
     * @param listener 监听器
     */
    @SuppressLint("CheckResult")
    @Override
    public void getAllBooks(final IBookContract.OnBookBeanListener listener) {


        // 创建被观察者，传递List<BookBean>类型事件
        Observable<List<BookBean>> BookObservable
                = Observable.create(new ObservableOnSubscribe<List<BookBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<BookBean>> emitter) throws Exception {
                // 初始化书籍类型表访问器
                BookDao bookDao = new BookDao(mView.getContext());
                emitter.onNext(bookDao.selectAll());
            }
        });
        // 处理于IO子线程
        BookObservable.subscribeOn(Schedulers.io())
                // 响应于Android主线程
                .observeOn(AndroidSchedulers.mainThread())
                // 设置订阅的响应事件
                .subscribe(new Consumer<List<BookBean>>() {
                    @Override
                    public void accept(List<BookBean> bookBeans) throws Exception {
                        if (bookBeans != null) {
                            listener.onSuccess(bookBeans);
                        } else {
                            listener.onError();
                        }
                    }
                });
    }

    /**
     * 实现 从数据库内移除书籍
     *
     * @param bookList 欲移除的BookBean集合
     * @param listener 监听器
     */
    @SuppressLint("CheckResult")
    @Override
    public void removeBooksInDatabase(final List<BookBean> bookList,
                                      final IBookContract.OnBookBeanListener listener) {
        // 创建被观察者，传递List<BookBean>类型事件
        Observable<List<BookBean>> BookObservable
                = Observable.create(new ObservableOnSubscribe<List<BookBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<BookBean>> emitter) throws Exception {
                // 初始化书籍表访问器
                BookDao bookDao = new BookDao(mView.getContext());

                if (bookList != null) {
                    for (int i = 0; i < bookList.size(); i++) {
                        BookBean bookBean = bookList.get(i);
                        // 执行书籍类型表访问器删除操作
                        bookDao.delete(bookBean);
                    }
                    emitter.onNext(bookList);
                }

            }
        });

        // 处理于IO子线程
        BookObservable.subscribeOn(Schedulers.io())
                // 响应于Android主线程
                .observeOn(AndroidSchedulers.mainThread())
                // 设置订阅的响应事件
                .subscribe(new Consumer<List<BookBean>>() {
                    @Override
                    public void accept(List<BookBean> bookBeans) throws Exception {
                        listener.onSuccess(bookList);
                    }
                });
    }

    /**
     * 实现 从储存设备内移除书籍
     *
     * @param bookList 欲移除的BookBean集合
     */
    @Override
    public void removeBooksInStorage(List<BookBean> bookList) {
        // TODO: 2019-07-09 待完成 实现 从储存设备内移除书籍
    }

    /**
     * 实现 撤销移除数据库内的书籍
     *
     * @param bookList 被移除的BookBean集合
     * @param listener 监听器
     */
    @SuppressLint("CheckResult")
    @Override
    public void restoreBooks(final List<BookBean> bookList,
                             final IBookContract.OnNormalListener listener) {
        // 创建被观察者，传递List<BookBean>类型事件
        Observable<List<BookBean>> BookObservable = Observable.create(new ObservableOnSubscribe<List<BookBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<BookBean>> emitter) throws Exception {
                if (bookList != null) {
                    for (int i = 0; i < bookList.size(); i++) {
                        // 初始化书籍表访问器
                        BookDao bookDao = new BookDao(mView.getContext());
                        BookBean bookBean = bookList.get(i);
                        // 执行书籍类型表访问器添加操作
                        bookDao.insert(bookBean);
                    }
                    emitter.onNext(bookList);
                }
            }
        });
        // 处理于IO子线程
        BookObservable.subscribeOn(Schedulers.io())
                // 响应于Android主线程
                .observeOn(AndroidSchedulers.mainThread())
                // 设置订阅的响应事件
                .subscribe(new Consumer<List<BookBean>>() {
                    @Override
                    public void accept(List<BookBean> bookBeans) throws Exception {
                        listener.onSuccess();
                    }
                });
    }

    /**
     * 实现 修改数据库内书籍信息
     *
     * @param bookBean 欲更改的书籍实体类
     * @param listener 监听器
     */
    @SuppressLint("CheckResult")
    @Override
    public void alterBookInfo(final BookBean bookBean,
                              final IBookContract.OnNormalListener listener) {
        // 创建被观察者，传递BookBean类型事件
        Observable<BookBean> BookObservable = Observable.create(new ObservableOnSubscribe<BookBean>() {
            @Override
            public void subscribe(ObservableEmitter<BookBean> emitter) throws Exception {
                // 初始化书籍表访问器
                BookDao bookDao = new BookDao(mView.getContext());
                if (bookBean != null) {
                    // 执行书籍类型表访问器修改操作
                    bookDao.update(bookBean);
                    emitter.onNext(bookBean);
                }
            }
        });

        // 处理于IO子线程
        BookObservable.subscribeOn(Schedulers.io())
                // 响应于Android主线程
                .observeOn(AndroidSchedulers.mainThread())
                // 设置订阅的响应事件
                .subscribe(new Consumer<BookBean>() {
                    @Override
                    public void accept(BookBean bookBean) throws Exception {
                        listener.onSuccess();
                    }
                });
    }
}
