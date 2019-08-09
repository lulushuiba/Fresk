package com.asterism.fresk.ui.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.asterism.fresk.R;
import com.asterism.fresk.contract.IBookContract;
import com.asterism.fresk.dao.bean.BookBean;
import com.asterism.fresk.presenter.BookPresenter;
import com.tencent.smtt.sdk.QbSdk;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 书桌页面Fragment类，继承base基类且泛型为当前模块Presenter接口类型，并实现当前模块View接口
 *
 * @author Ashinch
 * @email Glaxyinfinite@outlook.com
 * @date on 2019-07-10 16:10
 */
public class DeskFragment extends BaseFragment<IBookContract.Presenter>
        implements IBookContract.View {

    private int bookid; // 书籍的编号
    private Uri bookpicUri; // 书籍图片路径
    private String selectedImageUri;
    private String longclickUri;
    private Bitmap longclickbitmap;
    private String bookname;

    @BindView(R.id.img_book_Pic)
    ImageView imgBookPic; // 书籍封面

    @BindView(R.id.tv_book_name)
    TextView tvBookName; // 书名

    @BindView(R.id.tv_last_chapter)
    TextView tvLastChapter; // 最近时间读到章节

    @BindView(R.id.tv_read_progress)
    TextView tvReadProgress; // 阅读进程

    private int pos = 0;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_desk;
    }

    @Override
    protected IBookContract.Presenter setPresenter() {
        return new BookPresenter();
    }

    @Override
    protected void initialize() {
        if (getArguments() != null) {
            pos = getArguments().getInt("pos", 0);
        }
        mPresenter.getBookByIndexSortReadDate(pos, new IBookContract.OnBookBeanListener() {
            @Override
            public void onSuccess(BookBean bookBean) {
                bookid = bookBean.getId();
                bookname = bookBean.getName();
                selectedImageUri = bookBean.getPicName();
                imgBookPic.setImageURI(Uri.fromFile(new File(bookBean.getPicName())));
                bookpicUri = Uri.fromFile(new File(bookBean.getPicName()));
                tvBookName.setText(bookBean.getName());
                tvLastChapter.setText(bookBean.getLastChapter());
                tvReadProgress.setText(bookBean.getReadProgress() + "%");
                imgBookPic.setOnLongClickListener(longClickListener);
            }

            @Override
            public void onError(String message) {
                showErrorToast("获取书桌书籍错误: " + message);
            }
        });
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showRemoving() {

    }

    @Override
    public void hideRemoving() {

    }

    @OnClick(R.id.img_book_Pic)
    public void onClick() {

    }

    // 长按响应事件
    private ImageView.OnLongClickListener longClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            // 对话框构建器
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // 设置项的来源和监听器
            builder.setItems(getResources().getStringArray(R.array.edit_menu), dialogOnclickListener);
            // 显示对话框
            builder.show();
            return true;
        }
    };

    // 长按编辑菜单点击事件
    private DialogInterface.OnClickListener dialogOnclickListener =
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(final DialogInterface dialog, int items) {
                    final AlertDialog.Builder dialogupdate = new AlertDialog.Builder(getActivity());
                    switch (items) {
                        case 0:
                            // 设置对话框标题
                            dialogupdate.setTitle("编辑封面");
                            // 设置对话框显示视图
                            final View alter_pic_view =
                                    getLayoutInflater().inflate(R.layout.dialog_alter_upload_bookpic, null);
                            // 对话框预览书籍封面
                            final ImageView dialog_imgbookpic = alter_pic_view.findViewById
                                    (R.id.dl_img_book_pic);
                            // 本地上传按钮
                            Button btn_local = alter_pic_view.findViewById
                                    (R.id.dl_btn_local_upload);
                            //  下载按钮
                            Button btn_download = alter_pic_view.findViewById(R.id.dl_btn_download);
                            // 设置本地上传按钮点击事件
                            btn_local.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alterBookPicByLocal(dialog_imgbookpic);
                                }
                            });

                            // 设置下载按钮点击事件
                            btn_download.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alterBookPicBydownload(dialog_imgbookpic);
                                }
                            });
                            // 设置对话框显示封面图片
                            dialog_imgbookpic.setImageURI(bookpicUri);
                            dialogupdate.setPositiveButton("确定",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (selectedImageUri != null) {
                                                mPresenter.alterBookPicInfo(bookid, selectedImageUri, onNormalListener);
                                                imgBookPic.setImageURI(Uri.fromFile(new File(selectedImageUri)));
                                            }
                                        }
                                    });
                            dialogupdate.setNegativeButton("取消", null);
                            dialogupdate.setView(alter_pic_view);
                            dialogupdate.show();
                            break;
                        case 1:
                            // 设置对话框标题
                            dialogupdate.setTitle("输入想要更改名称");
                            // 设置对话框显示视图
                            final View alter_name_view =
                                    getLayoutInflater().inflate(R.layout.dialog_alter_bookname,
                                            null);
                            // 设置对话框确定按钮和监听事件
                            dialogupdate.setPositiveButton("确定",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            EditText et_name = alter_name_view.findViewById(
                                                    R.id.edit_bookname);
                                            mPresenter.alterBookNameInfo(bookid,
                                                    et_name.getText().toString().trim(),
                                                    onNormalListener);
                                            tvBookName.setText(et_name.getText().toString().trim());
                                        }
                                    });
                            // 设置对话框取消按钮和监听事件
                            dialogupdate.setNegativeButton("取消", null);
                            // 对话框绑定视图
                            dialogupdate.setView(alter_name_view);
                            // 显示对话框
                            dialogupdate.show();
                            break;
                        case 2:
                            // TODO: 2019-08-09 待完成 实现 修改书籍作者功能
                            break;
                        default:
                            break;
                    }
                }
            };

    // 修改书名监听事件
    private IBookContract.OnNormalListener onNormalListener = new IBookContract.OnNormalListener() {
        @Override
        public void onSuccess() {
            showSuccessToast("修改书籍信息成功!");
        }

        @Override
        public void onError() {
            showSuccessToast("修改书籍信息失败!");
        }
    };

    /**
     * 当长按WebView图片时调用
     *
     * @param v WebView所在视图
     * @return 返回长按图片的Url
     */
    private String responseWebLongClick(View v) {
        if (v instanceof WebView) {
            WebView.HitTestResult result = ((WebView) v).getHitTestResult();
            //获得了点击处数据
            if (result != null) {
                int type = result.getType();
                //如果长按的是图片
                if (type == WebView.HitTestResult.IMAGE_TYPE || type == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
                    String longClickUrl = result.getExtra();
                    return longClickUrl;
                }
            }
        }
        return null;
    }

    /**
     * Url转换为BitMap时调用
     *
     * @param url 图片的路径
     * @return 图片Url转换为BitMap的值
     */
    public Bitmap returnBitMap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 当点击下载长按图片时调用
     *
     * @param bitmap 想要保存图片的bitmap
     * @param picUrl 想要保存图片的Url地址
     */
    private void save2Album(Bitmap bitmap, String picUrl) {
        File appDir = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".png");
        if (!appDir.exists()) appDir.mkdir();
        String[] str = picUrl.split("/");
        String fileName = str[str.length - 1];
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            onSaveSuccess(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 当save2Album执行时调用
     *
     * @param file 保存下来的图片文件
     */
    private void onSaveSuccess(final File file) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 扫描指定目录
                mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
                bookpicUri = Uri.fromFile(file);
            }
        }).start();
    }

    /**
     * 当点击本地上传图片时调用
     *
     * @param imageView 对话框视图预览图片
     */
    private void alterBookPicByLocal(ImageView imageView) {
        final ImageView dialog_imagbookpic = imageView;
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        getActivity().startActivityForResult(intent, 1);
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.obj != null) {
                    dialog_imagbookpic.setImageURI(Uri.fromFile(new File(
                            msg.obj.toString())));
                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        SharedPreferences sp = getActivity().getSharedPreferences
                                ("data", getContext().MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        String selectedImage = sp.getString("selectedImage"
                                , null);
                        if (selectedImage == null) {
                            Thread.sleep(1000);
                        } else if (selectedImage.equals("没选择")) {
                            Thread.interrupted();
                            editor.remove("selectedImage");
                            editor.commit();
                        } else if (selectedImage != null) {
                            editor.remove("selectedImage");
                            editor.commit();
                            Message message = new Message();
                            message.obj = selectedImage;
                            handler.sendMessage(message);
                            selectedImageUri = selectedImage;
                            Thread.interrupted();
                        }
                    }
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 创建下载图片对话框
     *
     * @param imageView 对话框视图预览图片
     */
    private void alterBookPicBydownload(ImageView imageView) {
        final ImageView dialog_imgbookpic = imageView;
        final Dialog dialog_download = new Dialog(getActivity());
        dialog_download.setTitle("下载图片");
        // 设置对话框显示下载视图
        final View alter_download_pic_view = getLayoutInflater().inflate(
                R.layout.dialog_alter_download_bookpic, null);
        // 允许没有Wifi网络时使用X5内核
        QbSdk.setDownloadWithoutWifi(true);
        //x5内核初始化接口
        final com.tencent.smtt.sdk.WebView wv_alter_pic = alter_download_pic_view.findViewById(
                R.id.wv_alter_pic);
        final ImageView dialog_download_imgpic = alter_download_pic_view.
                findViewById(R.id.dl_img_book_pic);
        Button dl_btn_commit = alter_download_pic_view.findViewById(
                R.id.dl_btn_commit);
        Button dl_btn_cancle = alter_download_pic_view.findViewById(
                R.id.dl_btn_cancle);
        dialog_download_imgpic.setImageURI(bookpicUri);
        wv_alter_pic.loadUrl("https://m.baidu.com/sf/vsearch?pd=" +
                "image_content&word=" + bookname + "&tn=vsearch&atn=page&sa=" +
                "vs_img_indexhot&fr=index&from=415a");
        wv_alter_pic.getSettings().setJavaScriptEnabled(true);
        wv_alter_pic.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final Handler handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        if (msg.obj != null) {
                            dialog_download_imgpic.setImageBitmap((Bitmap) msg.obj);
                        }
                    }
                };
                longclickUri = responseWebLongClick(v);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        longclickbitmap = returnBitMap(longclickUri);
                        message.obj = longclickbitmap;
                        handler.sendMessage(message);
                    }
                }).start();
                return true;
            }
        });
        dl_btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_download.cancel();
            }
        });
        dl_btn_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save2Album(longclickbitmap, longclickUri);
                dialog_download.cancel();
                dialog_imgbookpic.setImageURI(bookpicUri);
                selectedImageUri = bookpicUri.getPath();
            }
        });
        dialog_download.setContentView(alter_download_pic_view);
        dialog_download.show();
    }
}
