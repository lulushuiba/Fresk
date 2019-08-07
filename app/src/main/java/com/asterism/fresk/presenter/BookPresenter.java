package com.asterism.fresk.presenter;

import android.annotation.SuppressLint;
import android.util.Log;

import com.asterism.fresk.contract.IBookContract;
import com.asterism.fresk.dao.BookDao;
import com.asterism.fresk.dao.bean.BookBean;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
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
    public void getAllBooks(final IBookContract.OnBookListListener listener) {
        // 显示正在加载
        mView.showLoading();

        // 创建被观察者，传递List<BookBean>类型事件
        Observable<List<BookBean>> BookObservable
                = Observable.create(new ObservableOnSubscribe<List<BookBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<BookBean>> emitter) throws Exception {
                // 初始化书籍类型表访问器
                BookDao bookDao = new BookDao(getContext());
                emitter.onNext(bookDao.selectALLSortReadDate());
                emitter.onComplete();
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
                        // 隐藏正在加载
                        mView.hideLoading();
                        if (bookBeans != null) {
                            listener.onSuccess(bookBeans);
                        } else {
                            listener.onError();
                        }
                    }
                });
    }

    /**
     * 从数据库内按照阅读日期排序获取指定索引书籍
     *
     * @param index    索引
     * @param listener 监听器
     */
    @SuppressLint("CheckResult")
    @Override
    public void getBookByIndexSortReadDate(final int index,
                                           final IBookContract.OnBookBeanListener listener) {
        // 创建被观察者，传递BookBean类型事件
        Observable<BookBean> BookObservable
                = Observable.create(new ObservableOnSubscribe<BookBean>() {
            @Override
            public void subscribe(ObservableEmitter<BookBean> emitter) throws Exception {
                // 初始化书籍类型表访问器
                BookDao bookDao = new BookDao(getContext());
                BookBean bookBean = bookDao.selectByIndexSortReadDate(index);
                if (bookBean != null) {
                    emitter.onNext(bookBean);
                } else {
                    emitter.onError(new Throwable("bookBean is null!"));
                }
                emitter.onComplete();
            }
        });

        // 处理于IO子线程
        BookObservable.subscribeOn(Schedulers.io())
                // 响应于Android主线程
                .observeOn(AndroidSchedulers.mainThread())
                // 设置订阅的响应事件
                .subscribe(new Observer<BookBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BookBean bookBean) {
                        listener.onSuccess(bookBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

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
                                      final IBookContract.OnBookListListener listener) {
        // 显示正在移除
        mView.showRemoving();

        // 创建被观察者，传递List<BookBean>类型事件
        Observable<List<BookBean>> BookObservable
                = Observable.create(new ObservableOnSubscribe<List<BookBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<BookBean>> emitter) throws Exception {
                // 初始化书籍表访问器
                BookDao bookDao = new BookDao(getContext());
                for (BookBean bookBean : bookList) {
                    // 执行书籍类型表访问器删除操作
                    bookDao.delete(bookBean);
                }
                emitter.onNext(bookList);
                emitter.onComplete();
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
                        // 隐藏正在移除
                        mView.hideRemoving();
                        listener.onSuccess(bookBeans);
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
        Observable<BookBean> BookObservable
                = Observable.create(new ObservableOnSubscribe<BookBean>() {
            @Override
            public void subscribe(ObservableEmitter<BookBean> emitter) throws Exception {
                // 初始化书籍表访问器
                BookDao bookDao = new BookDao(getContext());
                for (BookBean bookBean : bookList) {
                    // 执行书籍类型表访问器添加操作
                    bookDao.insert(bookBean);
                    emitter.onNext(bookBean);
                }
                emitter.onComplete();
            }
        });

        // 处理于IO子线程
        BookObservable.subscribeOn(Schedulers.io())
                // 响应于Android主线程
                .observeOn(AndroidSchedulers.mainThread())
                // 设置订阅的响应事件
                .subscribe(new Observer<BookBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BookBean bookBean) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        // 隐藏正在移除
                        mView.hideRemoving();
                        listener.onError();
                    }

                    @Override
                    public void onComplete() {
                        // 隐藏正在移除
                        mView.hideRemoving();
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
        Observable<BookBean> BookObservable
                = Observable.create(new ObservableOnSubscribe<BookBean>() {
            @Override
            public void subscribe(ObservableEmitter<BookBean> emitter) throws Exception {
                // 初始化书籍表访问器
                BookDao bookDao = new BookDao(getContext());
                if (bookBean != null) {
                    // 执行书籍类型表访问器修改操作
                    bookDao.update(bookBean);
                }
                emitter.onNext(bookBean);
                emitter.onComplete();
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
                        if (bookBean != null) {
                            listener.onSuccess();
                        } else {
                            listener.onError();
                        }
                    }
                });
    }

    /**
     * 实现 修改数据库内书籍书名信息
     *
     * @param bookid 欲更改的书籍编号
     * @param newbookname 欲更改书名
     * @param listener 监听器
     */
    @SuppressLint("CheckResult")
    @Override
    public void alterBookNameInfo(final int bookid, final String newbookname,
                                  final IBookContract.OnNormalListener listener) {

        // 创建被观察者，传递BookBean类型事件
        Observable<BookBean> BookObservable
                = Observable.create(new ObservableOnSubscribe<BookBean>() {
            @Override
            public void subscribe(ObservableEmitter<BookBean> emitter) throws Exception {
                // 初始化书籍表访问器
                BookDao bookDao = new BookDao(getContext());
                BookBean bookBean=null;
                // 执行书籍类型表访问器根据编号查询操作
                bookBean=bookDao.getbookbyid(bookid);
                if (bookBean != null) {
                 bookBean.setName(newbookname);
                 // 执行书籍类型表访问器修改操作
                 bookDao.update(bookBean);
                }
                emitter.onNext(bookBean);
                emitter.onComplete();
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
                        if (bookBean != null) {
                            listener.onSuccess();
                        } else {
                            listener.onError();
                        }
                    }
                });
    }

    /**
     * 实现 修改数据库内书籍图片信息
     *
     * @param bookid 欲更改的书籍编号
     * @param newpic 欲更改图片
     * @param listener 监听器
     */
    @SuppressLint("CheckResult")
    @Override
    public void alterBookPicInfo(final int bookid, final String newpic,
                                 final IBookContract.OnNormalListener listener) {

        // 创建被观察者，传递BookBean类型事件
        Observable<BookBean> BookObservable
                = Observable.create(new ObservableOnSubscribe<BookBean>() {
            @Override
            public void subscribe(ObservableEmitter<BookBean> emitter) throws Exception {
                // 初始化书籍表访问器
                BookDao bookDao = new BookDao(getContext());
                BookBean bookBean=null;
                // 执行书籍类型表访问器根据编号查询操作
                bookBean=bookDao.getbookbyid(bookid);
                if (bookBean != null) {
                    bookBean.setPicName(newpic);
                    // 执行书籍类型表访问器修改操作
                    bookDao.update(bookBean);
                }
                emitter.onNext(bookBean);
                emitter.onComplete();
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
                        if (bookBean != null) {
                            listener.onSuccess();
                        } else {
                            listener.onError();
                        }
                    }
                });
    }
}