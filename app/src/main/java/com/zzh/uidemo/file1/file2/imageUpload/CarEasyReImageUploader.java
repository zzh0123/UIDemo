package com.zzh.uidemo.file1.file2.imageUpload;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.hmfl.careasy.baselib.CarEasyApplication;
import com.hmfl.careasy.baselib.R;
import com.hmfl.careasy.baselib.constant.CommonStatusType;
import com.hmfl.careasy.baselib.library.cache.StringUtils;
import com.hmfl.careasy.baselib.library.httputils.HttpConnUtils;
import com.hmfl.careasy.baselib.library.imageselector.bean.SingleImage;
import com.hmfl.careasy.baselib.library.utils.ActivityUtils;
import com.hmfl.careasy.baselib.library.utils.LogUtil;
import com.hmfl.careasy.baselib.library.utils.NetworkDetector;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by ccMagic on 2018/3/13.
 * Copyright ：
 * Version ：
 * Reference ：
 * Description ：图片上传
 */
public class CarEasyReImageUploader {

    private static final String TAG = "CarEasyReImageUploader";
    private Activity mContext;
    /**
     * 图片上传的服务器地址（上传到哪里）
     */
    private String mUrl;
    /**
     * 需要上传的所有图片信息
     */
    private ArrayList<SingleImage> mSingleImageArrayList;
    /**
     * 是否需要压缩图片
     */
    private boolean mNeedCompressImage = false;

    /**
     * @param context Activity
     * @param url     图片上传的服务器地址（上传到哪里）  当前HttpConnUtils.interceptUrl(Constant.COMMONUPLOADIMAGE)
     */
    public CarEasyReImageUploader(@NonNull Activity context, @NonNull String url) {
        mContext = context;
        mUrl = url;
    }

    /**
     * @param context           Activity
     * @param url               图片上传的服务器地址（上传到哪里）  当前HttpConnUtils.interceptUrl(Constant.COMMONUPLOADIMAGE)
     * @param needCompressImage 是否需要压缩图片
     */
    public CarEasyReImageUploader(@NonNull Activity context, @NonNull String url, boolean needCompressImage) {
        mContext = context;
        mUrl = url;
        mNeedCompressImage = needCompressImage;
    }

    /**
     * 设置是否需要压缩图片
     *
     * @param needCompressImage 否需要压缩图片
     */
    public void setNeedCompressImage(boolean needCompressImage) {
        mNeedCompressImage = needCompressImage;
    }

    /**
     * 设置上传的数据
     *
     * @param singleImages 需要上传的所有图片（多张图片，单张图片，没有图片三种情况统一处理）
     */
    public void setUpLoadData(ArrayList<SingleImage> singleImages) {
        mSingleImageArrayList = singleImages;
    }

    /**
     * 原来的开始上传接口
     */
    public void start() {
        commonStart(false, -1);
    }

    /**
     * 新的开始上传接口
     */
    public void start(boolean mlyun, int isGiguan) {
        commonStart(mlyun, isGiguan);
    }

    /**
     * 开始上传接口
     *
     * @param mlyun    接口格式是否是美络云规范
     * @param isGiguan
     */
    public void commonStart(boolean mlyun, int isGiguan) {
        boolean net = NetworkDetector.isNetworkConnected(mContext);
        for (SingleImage singleImage : mSingleImageArrayList) {
            if (TextUtils.isEmpty(singleImage.getUploadedPath()) && !TextUtils.isEmpty(singleImage.getPath())) {
                if (net) {
                    //上传单张图片
                    if (mNeedCompressImage) {
                        compressImage(singleImage, mlyun, isGiguan);
                    } else {
                        upLoadPictures(singleImage, mlyun, isGiguan);
                    }
                } else {
                    if (singleImage.getProgressListener() != null) {
                        singleImage.getProgressListener().fail(mContext.getString(R.string.netexception1));
                    }
                }

            }
        }
    }

    /**
     * 压缩图片的缓存地址
     *
     * @return 压缩图片的缓存地址
     */
    private String getPath() {
        String path = Environment.getExternalStorageDirectory() + "/CarEasyImages/UploadCache/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }

    /**
     * 图片压缩
     *
     * @param singleImage SingleImage
     * @param mlyun       接口格式是否是美络云规范
     * @param isGiguan
     */
    private void compressImage(final SingleImage singleImage, final boolean mlyun, final int isGiguan) {
        Luban.with(mContext)
                .load(singleImage.getPath())
                //小于500K的图片不压缩
                .ignoreBy(500)
                .setTargetDir(getPath())
                .setFocusAlpha(false)
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(File file) {
                        singleImage.setPath(file.getAbsolutePath());
                        upLoadPictures(singleImage, mlyun, isGiguan);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (singleImage.getProgressListener() != null) {
                            singleImage.getProgressListener().fail(mContext.getString(R.string.compressor_fail));
                        }
                    }

                }).launch();
    }


    /**
     * 带进度的单张图片上传
     *
     * @param singleImage 图片信息
     * @param mlyun       接口格式是否是美络云规范
     * @param isGiguan
     */
    private void upLoadPictures(final SingleImage singleImage, boolean mlyun, int isGiguan) {//防止网络异步请求未结束是就点击了其他上传图片的操作，导致修改了pictureType
        if (mlyun || isGiguan == 1) {
            upLoadCommonPictures(true, "file", singleImage, isGiguan);
        } else {
            upLoadCommonPictures(false, "image", singleImage, isGiguan);
        }

    }

    /**
     * 带进度的单张图片上传
     *
     * @param mlyun       是否是阿里云的接口
     * @param bodyKey     Body的key
     * @param singleImage 图片信息
     * @param isGiguan
     */
    private void upLoadCommonPictures(final boolean mlyun, String bodyKey, final SingleImage singleImage, final int isGiguan) {//防止网络异步请求未结束是就点击了其他上传图片的操作，导致修改了pictureType
        final File pictureFile = new File(singleImage.getPath());
        if (pictureFile != null) {
            //
            MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
//             MediaType.parse() 里面是上传的文件类型。
            // 参数分别为， 请求key ，文件名称 ， RequestBody
            multipartBodyBuilder.addFormDataPart(bodyKey, pictureFile.getName(), new RequestBody() {
                @Override
                public MediaType contentType() {
                    return MediaType.parse("image");
                }

                @Override
                public long contentLength() throws IOException {
                    return pictureFile.length();
                }

                @Override
                public void writeTo(final BufferedSink sink) throws IOException {
                    try {
                        Source source;
                        source = Okio.source(pictureFile);
                        Buffer buffer = new Buffer();
                        Long upLoaded = 0L;
                        Long contentLength = contentLength();
                        for (long readCount; (readCount = source.read(buffer, 2048)) > -1; ) {
                            sink.write(buffer, readCount);
                            if (singleImage.getProgressListener() != null) {
                                singleImage.getProgressListener().onProgress(contentLength, upLoaded += readCount, false);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            });
            SharedPreferences sharedPreferences = ActivityUtils.selSharedPreferencesData(mContext, CarEasyApplication.USER_INFO_NAME);
            String token = sharedPreferences.getString("token", "");
            Request request = new Request.Builder().url(mUrl).addHeader("Authorization", token).post(multipartBodyBuilder.build()).tag(this).build();
            HttpConnUtils.getInstance().getOkHttpClient().newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if (singleImage.getProgressListener() != null) {
                        singleImage.getProgressListener().fail(mContext.getString(R.string.shangchuanfail));
                    }

                }

                @Override
                public void onResponse(Call call, final Response response) {
                    try {
                        String json = response.body().string();
                        LogUtil.iJsonFormat("json", json);
                        Map<String, Object> map = StringUtils.transJsonToMap(json);
                        if (isGiguan == 1) {
                            String success = (String) map.get("success");
                            String msg = (String) map.get("message");
                            if (StringUtils.getTypeState(success, CommonStatusType.TRUE)) {
                                //服务器返回成功结果
                                String reqResultStr = (String) map.get("data");
                                Map<String, Object> modelMap = StringUtils.transJsonToMap(reqResultStr);

                                String path = (String) modelMap.get("baseUrl");
                                String size = (String) modelMap.get("size");
                                singleImage.setUploadedPath(path);
                                singleImage.setFileSize(Integer.parseInt(size));
                                if (singleImage.getProgressListener() != null) {
                                    singleImage.getProgressListener().success(msg);
                                }
                            } else {
                                //服务器返回失败结果
                                if (singleImage.getProgressListener() != null) {
                                    singleImage.getProgressListener().fail(msg);
                                }
                            }
                        } else if (mlyun) {
                            String success = (String) map.get("success");
                            String msg = (String) map.get("message");
                            if (StringUtils.getTypeState(success, CommonStatusType.TRUE)) {
                                //服务器返回成功结果
                                String reqResultStr = (String) map.get("data");
                                Map<String, Object> modelMap = StringUtils.transJsonToMap(reqResultStr);
                                String path = (String) modelMap.get("path");
                                String id = (String) modelMap.get("id");
                                singleImage.setUploadedPath(path);
                                singleImage.setId(id);
                                if (singleImage.getProgressListener() != null) {
                                    singleImage.getProgressListener().success(msg);
                                }
                            } else {
                                //服务器返回失败结果
                                if (singleImage.getProgressListener() != null) {
                                    singleImage.getProgressListener().fail(msg);
                                }
                            }

                        } else {
                            String result = (String) map.get("result");
                            String message = (String) map.get("message");
                            if ("success".equals(result)) {
                                //服务器返回success结果
                                String reqResultStr = (String) map.get("model");
                                Map<String, Object> modelMap = StringUtils.transJsonToMap(reqResultStr);
                                String path = (String) modelMap.get("path");
                                singleImage.setUploadedPath(path);
                                if (singleImage.getProgressListener() != null) {
                                    singleImage.getProgressListener().success(message);
                                }
                            } else {
                                //服务器返回fail结果
                                if (singleImage.getProgressListener() != null) {
                                    singleImage.getProgressListener().fail(message);
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (singleImage.getProgressListener() != null) {
                            singleImage.getProgressListener().fail(mContext.getString(R.string.shangchuanfail));
                        }
                    }
                }
            });
        } else {
            Log.e("okhttp", "pictureFile is null!", new NullPointerException());//new NullPointerException()用于标记错误位置
        }
    }
}
