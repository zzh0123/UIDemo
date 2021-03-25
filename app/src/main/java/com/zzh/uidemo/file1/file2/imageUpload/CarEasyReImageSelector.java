package com.zzh.uidemo.file1.file2.imageUpload;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.hmfl.careasy.baselib.R;
import com.hmfl.careasy.baselib.base.GalleryActivity;
import com.hmfl.careasy.baselib.constant.Constant;
import com.hmfl.careasy.baselib.library.cache.StringUtils;
import com.hmfl.careasy.baselib.library.httputils.HttpConnUtils;
import com.hmfl.careasy.baselib.library.imageselector.bean.SingleImage;
import com.hmfl.careasy.baselib.library.imageselector.callback.ImageSelectFinishListener;
import com.hmfl.careasy.baselib.library.imageselector.model.ImageSelectorUtil;
import com.hmfl.careasy.baselib.view.NoScrollGridView;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by ccMagic on 2018/3/12.
 * Copyright ：
 * Version ：
 * Reference ：
 * Description ：图片选择工具类
 */
public class CarEasyReImageSelector {
    private static final String ADD_SIGNAL = "-100";

    private static final String TAG = "CarEasyReImageSelector";
    private SelectFinishListener mSelectFinishListener;
    /**
     * 默认项目需求最多选择5张图片
     */
    private int mMaxSelectCount = 5;
    /**
     * 保证每次重新选择图片的时候，不会频繁新建实例（在不能滥用单例模式的情况下）
     */
    private ImageSelectorUtil mImageSelectorUtil;
    /**
     * 记录选择了的图片
     */
    private ArrayList<SingleImage> mSelectedSingleImageList = new ArrayList<>();
    /**
     * 用于保障选图未点击确认，直接返回时的数据正常
     */
    private ArrayList<SingleImage> mDefaultSelectedSingleImageList = new ArrayList<>();
    /**
     * 显示的内容
     */
    private ArrayList<SingleImage> mImageArrayList = new ArrayList<>();
    /**
     * 默认显示图片信息
     */
    private SingleImage mDefaultImageSelectedBean;
    /**
     * 选择的图片的显示NoScrollGridView
     */
    private NoScrollGridView mNoScrollGridView;
    private WeakReference<Activity> mActivity;
    public ImageSelectedAdapter mImageSelectedAdapter;
    /**
     * 记录上次选择的文件夹的名称，方便重新选择图片时默认还原上次显示的文件夹
     */
    private String mFolderName = "";
    /**
     * 图片上传
     */
    private CarEasyReImageUploader mCarEasyReImageUploader;
    private int mDefaultImageId;
    /**
     * 进度条显示格式
     */
    private DecimalFormat mDecimalFormat = new DecimalFormat("0.00%");
    /**
     * 是否需要压缩图片
     */
    private boolean mNeedCompressImage = true;
    /**
     * 图片上传的服务器地址（上传到哪里）
     */
    private String mUrl;
    /**
     * 接口格式是否是美络云规范
     */
    private boolean mlyun = false;
    /**
     * 接口格式是否是机关事务=1模块文件上传
     */
    private int isGiguan = 1;

    /**
     * 设置是否需要压缩图片
     *
     * @param needCompressImage
     */
    public void setNeedCompressImage(boolean needCompressImage) {
        mNeedCompressImage = needCompressImage;
    }

    /**
     * 设置上传图片的url
     *
     * @param url 上传图片的url
     */
    public void setUpLoadUrl(String url) {
        mUrl = url;
    }

    /**
     * 设置是否是美络云接口规范
     *
     * @param mlyun 上传图片的url
     */
    public void setIsMlyun(boolean mlyun) {
        this.mlyun = mlyun;
    }

    /**
     * 设置是否是机关事务接口规范
     *
     * @param isGiguan
     */
    public void setIsGiguan(int isGiguan) {
        this.isGiguan = isGiguan;
    }

    /**
     * @param activity         Activity
     * @param noScrollGridView 显示选择的图片的NoScrollGridView
     * @param maxSelectCount   图片的最大选择数量，小于等于0时不限数量，为1是表示单选。
     */
    private CarEasyReImageSelector(Activity activity, NoScrollGridView noScrollGridView, int maxSelectCount, int defaultImageId) {
        mMaxSelectCount = maxSelectCount;
        mDefaultImageId = defaultImageId;
        //不能滥用单例模式，所以ImageSelectorUtil不适用单例模式
        mImageSelectorUtil = ImageSelectorUtil.newInstance();
        //获取实例，设置回调监听，之所以不在每个pick方法中设置，是为了避免每次重新选择图片是新建过多的ImageSelectFinishListener
        mImageSelectorUtil.setImageSelectFinishListener(new ImageSelectFinishListener() {
            @Override
            public void confirm(ArrayList<SingleImage> singleImageArrayList, String folderName) {
                mFolderName = folderName;
                refresh(singleImageArrayList);
            }

            @Override
            public void finish() {
                //
                mSelectedSingleImageList.clear();
                mSelectedSingleImageList.addAll(mDefaultSelectedSingleImageList);
                mImageSelectedAdapter.notifyDataSetChanged();
                //
                startUpload();
            }
        });
        mActivity = new WeakReference<>(activity);
        mNoScrollGridView = noScrollGridView;
        mNoScrollGridView.setNumColumns(4);
        mNoScrollGridView.setHorizontalSpacing(18);
        mNoScrollGridView.setVerticalSpacing(16);
        init();
    }

    /**
     * @param activity         Activity
     * @param noScrollGridView 显示选择的图片的NoScrollGridView
     * @param maxSelectCount   图片的最大选择数量，小于等于0时不限数量，为1是表示单选。
     */
    private CarEasyReImageSelector(Activity activity,
                                   NoScrollGridView noScrollGridView,
                                   int maxSelectCount,
                                   int defaultImageId,
                                   SelectFinishListener selectFinishListener) {
        mMaxSelectCount = maxSelectCount;
        mDefaultImageId = defaultImageId;
        mSelectFinishListener = selectFinishListener;
        //不能滥用单例模式，所以ImageSelectorUtil不适用单例模式
        mImageSelectorUtil = ImageSelectorUtil.newInstance();
        //获取实例，设置回调监听，之所以不在每个pick方法中设置，是为了避免每次重新选择图片是新建过多的ImageSelectFinishListener
        mImageSelectorUtil.setImageSelectFinishListener(new ImageSelectFinishListener() {
            @Override
            public void confirm(ArrayList<SingleImage> singleImageArrayList, String folderName) {
                mFolderName = folderName;
                refresh(singleImageArrayList);
            }

            @Override
            public void finish() {
                //
                mSelectedSingleImageList.clear();
                mSelectedSingleImageList.addAll(mDefaultSelectedSingleImageList);
                mImageSelectedAdapter.notifyDataSetChanged();
                //
                startUpload();
            }
        });
        //
        mActivity = new WeakReference<>(activity);
        //  mActivity = activity;
        mNoScrollGridView = noScrollGridView;
        mNoScrollGridView.setNumColumns(4);
        mNoScrollGridView.setHorizontalSpacing(18);
        mNoScrollGridView.setVerticalSpacing(16);
        init();
    }

    /**
     * @param activity         Activity
     * @param noScrollGridView 显示选择的图片的NoScrollGridView
     * @param maxSelectCount   图片的最大选择数量，小于等于0时不限数量，为1是表示单选。
     */
    public static CarEasyReImageSelector newInstance(Activity activity, NoScrollGridView noScrollGridView, int maxSelectCount, int defaultImageId) {
        return new CarEasyReImageSelector(activity, noScrollGridView, maxSelectCount, defaultImageId);
    }

    /**
     * @param activity         Activity
     * @param noScrollGridView 显示选择的图片的NoScrollGridView
     * @param maxSelectCount   图片的最大选择数量，小于等于0时不限数量，为1是表示单选。
     */
    public static CarEasyReImageSelector newInstance(Activity activity, NoScrollGridView noScrollGridView, int maxSelectCount, int defaultImageId, SelectFinishListener selectFinishListener) {
        return new CarEasyReImageSelector(activity, noScrollGridView, maxSelectCount, defaultImageId, selectFinishListener);
    }

    private void init() {
        mDefaultImageSelectedBean = new SingleImage("", true);
        mSelectedSingleImageList.add(mDefaultImageSelectedBean);
        mImageSelectedAdapter = new ImageSelectedAdapter(mActivity.get(), mSelectedSingleImageList, mMaxSelectCount, mDefaultImageId);
        mImageSelectedAdapter.setmHaveDefaultItem(true);
        mImageSelectedAdapter.setSelectFinishListener((com.hmfl.careasy.baselib.library.imageselector.CarEasyReImageSelector.SelectFinishListener) mSelectFinishListener);
        mNoScrollGridView.setAdapter(mImageSelectedAdapter);
        mNoScrollGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mSelectedSingleImageList.get(position).isShowDefault()) {
                    //选图之前，记录好选择了的图片，防止直接返回，不是确定返回时，显示（选择）的还是之前的图片
                    mDefaultSelectedSingleImageList.clear();
                    mDefaultSelectedSingleImageList.addAll(mSelectedSingleImageList);
                    //
                    pickMulti();
                } else {
                    //预览
/*                    for (int i = 0; i < mSelectedSingleImageList.size(); i++) {
                        if (TextUtils.isEmpty(mSelectedSingleImageList.get(i).getPath())) {
                            mSelectedSingleImageList.remove(mSelectedSingleImageList.get(i));
                        }
                    }*/
                    mImageArrayList.clear();
                    mImageArrayList.addAll(mSelectedSingleImageList);
/*                    PreviewActivity.actionStart(mActivity.get(), null, mImageArrayList, mSelectedSingleImageList, position, mMaxSelectCount, new ImageSelectFinishListener() {
                        @Override
                        public void confirm(ArrayList<SingleImage> singleImageArrayList, String folderName) {
                            refresh(singleImageArrayList);
                        }

                        @Override
                        public void finish() {
                            refresh(mSelectedSingleImageList);
                        }
                    }, mFolderName);*/

                    //重写预览页面
                    int size = mImageArrayList.size();
                    for (int i = 0; i < size; i++) {
                        if (TextUtils.isEmpty(mImageArrayList.get(i).getUploadedPath())) {
                            mImageArrayList.remove(mImageArrayList.get(i));
                        }
                    }
                    int size1 = mImageArrayList.size();
                    String[] imageArray = new String[size1];
                    for (int i = 0; i < size1; i++) {
                        imageArray[i] = mImageArrayList.get(i).getUploadedPath();
                    }

                    int[] location = new int[2];
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Rect frame = new Rect();
                        (mActivity.get()).getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
                        int statusBarHeight = frame.top;
                        view.getLocationOnScreen(location);
                        location[1] += statusBarHeight;
                    } else {
                        view.getLocationOnScreen(location);
                    }
                    view.invalidate();
                    int width = view.getWidth();
                    int height = view.getHeight();

                    Intent intent = new Intent(mActivity.get(), GalleryActivity.class);
                    Bundle b = new Bundle();
                    b.putStringArray(GalleryActivity.PHOTO_SOURCE_ID, imageArray);
                    intent.putExtras(b);
                    intent.putExtra(GalleryActivity.PHOTO_SELECT_POSITION, position);
                    intent.putExtra(GalleryActivity.PHOTO_SELECT_X_TAG, location[0]);
                    intent.putExtra(GalleryActivity.PHOTO_SELECT_Y_TAG, location[1]);
                    intent.putExtra(GalleryActivity.PHOTO_SELECT_W_TAG, width);
                    intent.putExtra(GalleryActivity.PHOTO_SELECT_H_TAG, height);
                    mActivity.get().startActivity(intent);
                    (mActivity.get()).overridePendingTransition(0, 0);
                }
            }
        });
    }

    /***
     * 刷新选择了的图片的数据
     * */
    private void refresh(ArrayList<SingleImage> singleImageArrayList) {
//        mImageSingleImageList.clear();
//        for (SingleImage singleImage : mImageSingleImageList) {
//            SingleImage imageSelectedBean = new SingleImage(singleImage.getPath(), false);
//            mImageSingleImageList.add(imageSelectedBean);
//        }
        mSelectedSingleImageList = singleImageArrayList;
        if (mMaxSelectCount == 0 || mSelectedSingleImageList.size() < mMaxSelectCount) {
            //保证将默认显示的图片信息显示在最后
            mSelectedSingleImageList.add(mDefaultImageSelectedBean);
            mImageSelectedAdapter.setmHaveDefaultItem(true);
        } else {
            mImageSelectedAdapter.setmHaveDefaultItem(false);
        }
//        mImageSelectedAdapter = new ImageSelectedAdapter(mActivity, mSelectedSingleImageList, mMaxSelectCount);
//        mNoScrollGridView.setAdapter(mImageSelectedAdapter);
        mImageSelectedAdapter.notifyDataSetChanged();
        //

        startUpload();
    }

    /**
     * 开始上传
     */
    private void startUpload() {
        mNoScrollGridView.setFocusable(true);
        mNoScrollGridView.setFocusableInTouchMode(true);
        mNoScrollGridView.requestFocus();

        //
        mNoScrollGridView.postDelayed(new Runnable() {
            @Override
            public void run() {
                //没有设置上传的地址用默认的图片上传地址
                if (StringUtils.isStrEmpty(mUrl)) {
                    mUrl = HttpConnUtils.interceptUrl(Constant.COMMONUPLOADIMAGE);
                }
                if (mSelectFinishListener != null)
                    mSelectFinishListener.selectFinish();
                managerProgress();
                if (mCarEasyReImageUploader == null) {
                    //机关事务服务走网关截取url
                    if (isGiguan == 1) {
                        mUrl = HttpConnUtils.interceptUrl(mUrl);
                    }
                    mCarEasyReImageUploader = new CarEasyReImageUploader(mActivity.get(), mUrl, mNeedCompressImage);
                }
                mCarEasyReImageUploader.setUpLoadData(mSelectedSingleImageList);
                mCarEasyReImageUploader.start(mlyun, isGiguan);
            }
        }, 1000);
    }

    /**
     * 进度条管理，之所以这么写，为不是再adapter中写，
     * 主要是解决NoScrollGridView高度不确定，
     * 多次调用getView测量高度，导致第一个item永远与保存的TextView不一致的问题。
     * NoScrollGridView.getChildAt(i)调用一定是获取到显示的View的
     */
    private void managerProgress() {
        int first = mNoScrollGridView.getFirstVisiblePosition();
        for (int i = 0, size = mSelectedSingleImageList.size(); i < size; i++) {
            View view = mNoScrollGridView.getChildAt(i - first);
            if (view == null) {
                continue;
            }
            final TextView textView = (TextView) view.findViewById(R.id.progressbar);
//            Log.i(TAG, "textView.hashCode---->: " + textView.hashCode() + "< position:>" + i);
//            mSelectedSingleImageList.get(i).setProgressTextView(textView);
            mSelectedSingleImageList.get(i).setProgressListener(new SingleImage.ProgressListener() {
                @Override
                public void onProgress(final long totalBytes, final long readBytes, boolean done) {
                    mActivity.get().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //防止在上传过程中删除图片，导致adapter数据不匹配，出错
                            if (textView != null) {
                                textView.setVisibility(View.VISIBLE);
                                //上传进度展示
                                textView.setText(mDecimalFormat.format(((double) readBytes / (double) totalBytes)));
                            }
                        }
                    });
                }

                @Override
                public void success(String message) {
                    mActivity.get().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (textView != null) {
                                //防止在上传过程中删除图片，导致adapter数据不匹配，出错
                                textView.setVisibility(View.GONE);
                            }
                        }
                    });
                }

                @Override
                public void fail(final String message) {
                    mActivity.get().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (textView != null) {
                                //防止在上传过程中删除图片，导致adapter数据不匹配，出错
                                textView.setText(message);
                                textView.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                }
            });
        }
    }

    public ArrayList<SingleImage> getDefaultSelectedSingleImageList() {
        return mSelectedSingleImageList;

    }

    /**
     * 获取选择的图片的信息
     */
    public ArrayList<SingleImage> getSlectedImageList() {
        ArrayList<SingleImage> arrayList = new ArrayList<>();
        for (SingleImage singleImage : mSelectedSingleImageList) {
            if (!TextUtils.isEmpty(singleImage.getUploadedPath()) && !singleImage.isShowDefault()) {
                arrayList.add(singleImage);
            }
        }
        return arrayList;
    }

    public void setSlectedImageList(ArrayList<SingleImage> singleImages) {
        mSelectedSingleImageList.clear();

        if (singleImages.size() < mMaxSelectCount) {
            mSelectedSingleImageList.addAll(singleImages);
            mSelectedSingleImageList.add(mDefaultImageSelectedBean);
            mImageSelectedAdapter.setmHaveDefaultItem(true);
        } else {
            mImageSelectedAdapter.setmHaveDefaultItem(false);
            mSelectedSingleImageList.addAll(singleImages);
        }
        mImageSelectedAdapter.notifyDataSetChanged();
    }

    /**
     * 图片是否上传成功
     */
    public boolean isUpLoadFail() {
        boolean isSuccess = true;
        for (SingleImage singleImage : mSelectedSingleImageList) {
            if (!TextUtils.isEmpty(singleImage.getPath()) && TextUtils.isEmpty(singleImage.getUploadedPath())) {
                isSuccess = false;
                break;
            }
        }
        return !isSuccess;
    }

    /**
     * 选择图片
     */
    private void pickMulti() {
        mImageSelectorUtil.openPhoto(mActivity.get(), false, mMaxSelectCount, mSelectedSingleImageList, mFolderName);
    }

    /**
     * 单选并裁剪
     */
    private void pickSingleAndCrop() {
        mImageSelectorUtil.openPhotoAndClip(mActivity.get());
    }


    public interface SelectFinishListener {
        void selectFinish();
    }
}
