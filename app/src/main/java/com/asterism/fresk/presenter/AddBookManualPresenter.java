package com.asterism.fresk.presenter;

import android.annotation.SuppressLint;
import android.os.Environment;

import com.asterism.fresk.contract.IAddBookManualContract;
import com.asterism.fresk.dao.BookDao;
import com.asterism.fresk.dao.BookTypeDao;
import com.asterism.fresk.dao.bean.BookBean;
import com.asterism.fresk.dao.bean.BookTypeBean;
import com.asterism.fresk.util.DateUtils;
import com.asterism.fresk.util.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 手动查找添加书籍Presenter类，继承base基类且泛型为当前模块View接口类型，并实现当前模块Presenter接口
 *
 * @author lulushuiba
 * @email 1315269930@qq.com
 * @date on 2019-08-03 10:20
 */
public class AddBookManualPresenter extends BasePresenter<IAddBookManualContract.View>
        implements IAddBookManualContract.Presenter{

    /**
     * 实现 添加书籍
     *
     * @param pathList 选中的书籍文件路径集合
     * @param listener 监听器
     */
    @SuppressLint("CheckResult")
    @Override
    public void addBooks(final List<String> pathList, final IAddBookManualContract.OnAddBooksListener listener) {
        // 创建被观察者，传递List<String>类型事件
        Observable<List<String>> observable
                = Observable.create(new ObservableOnSubscribe<List<String>>() {
            @Override
            public void subscribe(ObservableEmitter<List<String>> emitter) {
                // 错误路径集合
                List<String> failPathList = new ArrayList<>();
                // 书籍类型表访问器
                BookTypeDao bookTypeDao = new BookTypeDao(getContext());
                // 书籍表访问器
                BookDao bookDao = new BookDao(getContext());

                // 遍历路径集合
                for (String path : pathList) {
                    // 根据后缀名获取书籍类型
                    String suffixName = FileUtils.getFileSuffixName(path);
                    List<BookTypeBean> list = bookTypeDao.selectAllByName(suffixName);
                    // 如果文件类型存在于书籍类型表中
                    if (list.size() > 0) {
                        // 将书籍添加到数据库
                        bookDao.insert(getBookBean(path, list.get(0)));
                    } else {
                        // 将路径添加到错误路径集合
                        failPathList.add(path);
                    }
                }

                emitter.onNext(failPathList);
                emitter.onComplete();
            }
        });

        // 处理于IO子线程
        observable.subscribeOn(Schedulers.io())
                // 响应于Android主线程
                .observeOn(AndroidSchedulers.mainThread())
                // 设置订阅的响应事件
                .subscribe(new Observer<List<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<String> errorPathList) {
                        if (errorPathList.size() == 0) {
                            listener.onSuccess();
                        } else {
                            listener.onFailed(errorPathList);
                        }
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
     * 获取BookBean对象
     *
     * @param path         文件路径
     * @param bookTypeBean 书籍类型
     *
     * @return 返回实例化后的BookBean
     */
    private BookBean getBookBean(String path, BookTypeBean bookTypeBean) {
        BookBean bookBean = new BookBean();

        bookBean.setFilePath(path);
        bookBean.setAddDate(DateUtils.getNowToString());
        // TODO: 2019-07-11 最后章节与书籍名称后面针对书籍类型需要更改写法
        bookBean.setLastChapter("从未阅读");
        bookBean.setName(FileUtils.getFileSimpleName(path));
        bookBean.setReadDate(DateUtils.getNowToString());
        bookBean.setReadProgress(0);
        bookBean.setReadTiming(0);
        bookBean.setType(bookTypeBean);

        String picPath = "";
        // 根据不同的类型处理书籍封面
        switch (bookTypeBean.getType()) {
            case "txt":
                picPath = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                        .getAbsolutePath() + File.separator + "txt.png";
                break;
            case "pdf":
                picPath = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                        .getAbsolutePath() + File.separator + "pdf.png";
                break;
            case "epub":
                picPath = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                        .getAbsolutePath() + File.separator + "epub.png";
                break;
            case "mobi":
                picPath = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                        .getAbsolutePath() + File.separator + "mobi.png";
                break;
        }

        bookBean.setPicName(picPath);
        return bookBean;
    }
}
