package com.asterism.fresk.presenter;

import android.annotation.SuppressLint;

import com.asterism.fresk.contract.IAddBookContract;
import com.asterism.fresk.dao.BookDao;
import com.asterism.fresk.dao.BookTypeDao;
import com.asterism.fresk.dao.bean.BookBean;
import com.asterism.fresk.dao.bean.BookTypeBean;
import com.asterism.fresk.util.DateUtils;
import com.asterism.fresk.util.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AddBookPresenter extends BasePresenter<IAddBookContract.View>
        implements IAddBookContract.Presenter {

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
        bookBean.setLastChapter("从未阅读");
        bookBean.setName(FileUtils.getFileNameByPath(path));
        bookBean.setReadDate(DateUtils.getNowToString());
        bookBean.setReadProgress(0);
        bookBean.setReadTiming(0);
        bookBean.setType(bookTypeBean);

        String picPath = "";
        // 根据不同的类型处理书籍封面
        switch (bookTypeBean.getType()) {
            case "txt":
                picPath = "txt封面图片路径";
                break;
            case "pdf":
                picPath = "pdf封面图片路径";
                break;
            case "epub":
                picPath = "epub封面图片路径";
                break;
            case "mobi":
                picPath = "mobi封面图片路径";
                break;
        }
        bookBean.setPicName(picPath);

        return bookBean;
    }

    /**
     * 实现 添加书籍
     *
     * @param pathList 选中的书籍文件路径集合
     * @param listener 监听器
     */
    @SuppressLint("CheckResult")
    @Override
    public void addBooks(final List<String> pathList, final IAddBookContract.OnAddBooksListener listener) {
        // 创建被观察者，传递List<String>类型事件
        Observable<List<String>> observable = Observable.create(new ObservableOnSubscribe<List<String>>() {
            @Override
            public void subscribe(ObservableEmitter<List<String>> emitter) {
                List<String> errorPathList = new ArrayList<>(); // 错误路径集合
                BookTypeDao bookTypeDao = new BookTypeDao(mView.getContext());
                BookDao bookDao = new BookDao(mView.getContext());

                // 遍历路径集合
                for (String path : pathList) {
                    // 根据后缀名获取书籍类型
                    List<BookTypeBean> list = bookTypeDao.selectAllByName(FileUtils.getSuffixNameByFileName(path));
                    // 如果文件类型存在于书籍类型表中
                    if (list.size() != 0) {
                        // 将书籍添加到数据库
                        bookDao.insert(getBookBean(path, list.get(0)));
                    } else {
                        // 将路径添加到错误路径集合
                        errorPathList.add(path);
                    }
                }

                emitter.onNext(errorPathList);
                emitter.onComplete();
            }
        });

        // 处理于IO子线程
        observable.subscribeOn(Schedulers.io())
                // 响应于Android主线程
                .observeOn(AndroidSchedulers.mainThread())
                // 设置订阅的响应事件
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> strings) throws Exception {
                        if (strings.size() == 0) {
                            listener.onSuccess();
                        } else {
                            listener.onError(strings);
                        }
                    }
                });
    }

    /**
     * 实现 获取目录下所有文件信息
     *
     * @param currentDir 当前文件夹目录
     * @param listener   监听器
     */
    @Override
    public void getFilesInDir(File currentDir, IAddBookContract.OnGetFilesListener listener) {

    }

    /**
     * 实现 扫描储存设备内所有书籍
     *
     * @param BookTypeSet 欲扫描的文件类型格式后缀名集合
     * @param listener    监听器
     */
    @Override
    public void scanBooks(Set<BookTypeBean> BookTypeSet, IAddBookContract.OnGetFilesListener listener) {

    }
}
