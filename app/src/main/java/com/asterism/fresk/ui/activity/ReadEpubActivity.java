package com.asterism.fresk.ui.activity;

import android.util.Log;
import android.widget.TextView;

import com.asterism.fresk.R;
import com.asterism.fresk.contract.IReadContract;
import com.asterism.fresk.presenter.ReadPresenter;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.MediaType;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.Spine;
import nl.siegmann.epublib.domain.SpineReference;
import nl.siegmann.epublib.epub.EpubReader;
import nl.siegmann.epublib.service.MediatypeService;

/**
 * 阅读Activity类，继承base基类且泛型为当前模块Presenter类型，并实现当前模块View接口
 *
 * @author Ashinch
 * @email Glaxyinfinite@outlook.com
 * @date on 2019-08-03 21:18
 */
public class ReadEpubActivity extends BaseActivity<IReadContract.Presenter>
        implements IReadContract.View {

    @BindView(R.id.text)
    TextView text;

//    @BindView(R.id.navigation_read)
//    ScrollViewPager viewPager;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_read_epub;
    }

    @Override
    protected IReadContract.Presenter setPresenter() {
        return new ReadPresenter();
    }

    @Override
    protected void initialize() {
        String path = getIntent().getStringExtra("path");
        if (path == null || path.isEmpty()) {
            showErrorToast("未找到文件！");
            finish();
        }

        EpubReader reader = new EpubReader();
        MediaType[] lazyTypes = {
                MediatypeService.CSS,
                MediatypeService.GIF,
                MediatypeService.JPG,
                MediatypeService.PNG,
                MediatypeService.MP3,
                MediatypeService.MP4
        };

        try {
            Book book = reader.readEpubLazy("/storage/emulated/0/Download/三体三部曲_私人典藏版.epub", "UTF-8", Arrays.asList(lazyTypes));
//            List<Resource> chapterList = book.getContents();
//            StringBuffer buffer = new StringBuffer();
//            for (Resource resource : chapterList) {
//                buffer.append(resource.getTitle());
//            }
//            text.setText(buffer.toString());

            Spine spine = book.getSpine();

            List<SpineReference> spineReferences = spine.getSpineReferences();
            if (spineReferences != null && spineReferences.size() > 0) {
                Resource resource = spineReferences.get(3).getResource();
                text.setText(bytes2Hex(resource.getData()));
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    public String bytes2Hex(byte[] bs) {
        if (bs == null || bs.length <= 0) {
            return null;
        }
        Charset charset = Charset.defaultCharset();
        ByteBuffer buf = ByteBuffer.wrap(bs);
        CharBuffer cBuf = charset.decode(buf);

        return cBuf.toString();


    }

//    @OnClick(R.id.navigation_read)
//    public void onClick() {
//    }
}
