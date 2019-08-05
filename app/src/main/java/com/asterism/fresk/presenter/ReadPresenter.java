package com.asterism.fresk.presenter;

import com.asterism.fresk.contract.IReadContract;

import java.io.IOException;
import java.io.InputStream;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Metadata;
import nl.siegmann.epublib.epub.EpubReader;

/**
 * 阅读模块Presenter类，继承base基类且泛型为当前模块View接口类型，并实现当前模块Presenter接口
 *
 * @author Ashinch
 * @email Glaxyinfinite@outlook.com
 * @date on 2019-08-03 21:19
 */
public class ReadPresenter extends BasePresenter<IReadContract.View>
        implements IReadContract.Presenter {

    @Override
    public String getEpub() {
        try {
            EpubReader reader = new EpubReader();
            InputStream is = null;
            is = getContext().getAssets().open("books/santi.epub");
            Book book = reader.readEpub(is);

            Metadata metadata = book.getMetadata();
            String bookInfo = "作者：" + metadata.getAuthors().get(0) +
                    "\n出版社：" + metadata.getPublishers().get(0) +
//                    "\n出版时间：" + metadata.getDates() +
                    "\n书名：" + metadata.getTitles().get(0) +
                    "\n简介：" + metadata.getDescriptions().get(0) +
                    "\n语言：" + metadata.getLanguage() +
                    "\n\n封面图：";
            return bookInfo;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
