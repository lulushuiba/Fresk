package com.asterism.fresk.presenter;

import android.util.Log;

import com.asterism.fresk.contract.IBookContract;
import com.asterism.fresk.dao.BookDao;
import com.asterism.fresk.dao.bean.BookBean;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * 书籍模块Presenter类，继承base基类且泛型为当前模块View接口类型，并实现当前模块Presenter接口
 *
 * @author Rainydays
 * @email Glaxyinfinite@outlook.com
 * @date on 2019-7-2 16：57：32
 */
public class BookPresenter extends BasePresenter<IBookContract.View>
        implements IBookContract.Presenter {


    /**
     * 实现 从数据库内获取所有书籍
     *
     * @param listener 监听器
     */
    @Override
    public void getAllBooks(final IBookContract.OnBookBeanListener listener) {
        final BookDao bookDao = new BookDao(mView.getContext());
        Observable<List<BookBean>>BookObservable=Observable.create(new ObservableOnSubscribe<List<BookBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<BookBean>> emitter) throws Exception {
                emitter.onNext(bookDao.selectAll());
            }
        });
        Observer<List<BookBean>>BookObserver=new Observer<List<BookBean>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<BookBean> bookBeans) {
                if (bookBeans != null) {
                    listener.onSuccess(bookBeans);
                } else
                {
                    listener.onError();
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        BookObservable.subscribeOn(Schedulers.io())
                .delay(2000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(BookObserver);
    }

    /**
     * 实现 从数据库内移除书籍
     *
     * @param bookList 欲移除的BookBean集合
     * @param listener 监听器
     */
    @Override
    public void removeBooksInDatabase(final List<BookBean> bookList, final IBookContract.OnBookBeanListener listener) {
        final BookDao bookDao = new BookDao(mView.getContext());
        Observable<List<BookBean>>BookObservable=Observable.create(new ObservableOnSubscribe<List<BookBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<BookBean>> emitter) throws Exception {
                emitter.onNext(bookList);
            }
        });
        Observer<List<BookBean>>BookObserver=new Observer<List<BookBean>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<BookBean> bookBeans) {
                if (bookBeans!=null){
                    for (int i=0;i<bookBeans.size();i++)
                    {
                        BookBean bookBean=bookBeans.get(i);
                        bookDao.delete(bookBean);
                    }
                    listener.onSuccess(bookList);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        BookObservable.subscribeOn(Schedulers.io())
                .delay(2000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(BookObserver);
    }

    /**
     * 实现 从储存设备内移除书籍
     *
     * @param bookList 欲移除的BookBean集合
     */
    @Override
    public void removeBooksInStorage(List<BookBean> bookList) {

    }

    /**
     * 实现 撤销移除数据库内的书籍
     *
     * @param bookList 被移除的BookBean集合
     * @param listener 监听器
     */
    @Override
    public void restoreBooks(final List<BookBean> bookList, final IBookContract.OnNormalListener listener) {
        final BookDao bookDao = new BookDao(mView.getContext());
        Observable<List<BookBean>>BookObservable=Observable.create(new ObservableOnSubscribe<List<BookBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<BookBean>> emitter) throws Exception {
                emitter.onNext(bookList);
            }
        });
        Observer<List<BookBean>>BookObserver=new Observer<List<BookBean>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<BookBean> bookBeans) {
                 if (bookBeans!=null){
                     for (int i=0;i<bookBeans.size();i++)
                     {
                         BookBean bookBean=bookBeans.get(i);
                         bookDao.insert(bookBean);
                     }
                     listener.onSuccess();
                 }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        BookObservable.subscribeOn(Schedulers.io())
                .delay(2000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(BookObserver);
    }

    /**
     * 实现 修改数据库内书籍信息
     *
     * @param bookBean 欲更改的书籍实体类
     * @param listener 监听器
     */
    @Override
    public void alterBookInfo(final BookBean bookBean, final IBookContract.OnNormalListener listener) {
        final BookDao bookDao = new BookDao(mView.getContext());
            Observable<BookBean> BookObservable=Observable.create(new ObservableOnSubscribe<BookBean>() {
                @Override
                public void subscribe(ObservableEmitter<BookBean> emitter) throws Exception {
                    emitter.onNext(bookBean);
                }
            });
            Observer<BookBean>BookObserver=new Observer<BookBean>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(BookBean bookBean) {
                    if (bookBean!=null){
                        bookDao.update(bookBean);
                        listener.onSuccess();
                    }
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            };
        BookObservable.subscribeOn(Schedulers.io())
                     .delay(2000, TimeUnit.MILLISECONDS)
                     .observeOn(AndroidSchedulers.mainThread())
                     .subscribe(BookObserver);

    }
}
