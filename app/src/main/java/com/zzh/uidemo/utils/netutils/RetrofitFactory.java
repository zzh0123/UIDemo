package com.zzh.uidemo.utils.netutils;

import android.content.SharedPreferences;

import com.zzh.uidemo.MyApplication;
import com.zzh.uidemo.constant.HttpApi;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 封装Retrofit配置
 */

public class RetrofitFactory {
    public String TAG = "RetrofitFactory";
    // 缓存文件夹名称
    public static final String CACHE_NAME = "zkml_meeting_room_cache";
//    public static String BASE_URL = IPConfig.IPCONFIG;
    private static final int DEFAULT_CONNECT_TIMEOUT = 5;
    private static final int DEFAULT_WRITE_TIMEOUT = 10;
    private static final int DEFAULT_READ_TIMEOUT = 10;
    private Retrofit retrofit;
    private HttpApi httpApi;
    /**
     * 请求失败重连次数
     */
    private int RETRY_COUNT = 3;
    private OkHttpClient.Builder okHttpBuilder;

    //构造方法私有
    private RetrofitFactory() {
        //手动创建一个OkHttpClient并设置超时时间
        okHttpBuilder = new OkHttpClient.Builder();

//        SharedPreferences sps = ActivityUtils.selSharedPreferencesData(MyApplication.getContext(), MyApplication.USER_INFO_NAME);
//        final String token = sps.getString("token", "");
//        LogUtil.i("zzz1", "rtro token: " + token);
        /**
         * 设置头信息
         */
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request.Builder requestBuilder = originalRequest.newBuilder()
                        .addHeader("Accept-Encoding", "gzip")
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "application/json; charset=utf-8")
                        .method(originalRequest.method(), originalRequest.body());
//                requestBuilder.addHeader("Authorization", token);//添加请求头信息，服务器进行token有效性验证
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };
        okHttpBuilder.addInterceptor(headerInterceptor);

        // 是否开启日志打印
//        if (MyApplication.LOG_SWITCH){
//            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//            //设置 Debug Log 模式
//            okHttpBuilder.addInterceptor(loggingInterceptor);
//        }

        /**
         * 设置超时和重新连接
         */
        okHttpBuilder.connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS);
        okHttpBuilder.readTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS);
        okHttpBuilder.writeTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS);
        //错误重连
        okHttpBuilder.retryOnConnectionFailure(true);

//        retrofit = new Retrofit.Builder()
//                .client(okHttpBuilder.build())
//                .addConverterFactory(GsonConverterFactory.create())//json转换成JavaBean
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .baseUrl(BASE_URL)
//                .build();
//        httpApi = retrofit.create(HttpApi.class);
    }

    /**
     * 设置缓存
     * @param isCache
     */
    public RetrofitFactory setCache(boolean isCache){
        if (isCache){
            File cacheFile = new File(MyApplication.getContext().getExternalCacheDir(), CACHE_NAME);
            Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
            Interceptor cacheInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    if (!NetUtil.isNetworkConnected()) {
                        request = request.newBuilder()
                                .cacheControl(CacheControl.FORCE_CACHE)
                                .build();
                    }
                    Response response = chain.proceed(request);
                    if (!NetUtil.isNetworkConnected()) {
                        int maxAge = 0;
                        // 有网络时 设置缓存超时时间0个小时
                        response.newBuilder()
                                .header("Cache-Control", "public, max-age=" + maxAge)
                                .removeHeader(CACHE_NAME)// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                                .build();
                    } else {
                        // 无网络时，设置超时为4周
                        int maxStale = 60 * 60 * 24 * 28;
                        response.newBuilder()
                                .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                                .removeHeader(CACHE_NAME)
                                .build();
                    }
                    return response;
                }
            };
            okHttpBuilder.cache(cache).addInterceptor(cacheInterceptor);

            retrofit = new Retrofit.Builder()
                    .client(okHttpBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create())//json转换成JavaBean
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                    .baseUrl(BASE_URL)
                    .build();
            httpApi = retrofit.create(HttpApi.class);
        }
        return this;
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final RetrofitFactory INSTANCE = new RetrofitFactory();

    }

    /**
     * 获取单例
     * @return
     */
    public static RetrofitFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 获取retrofit
     * @return
     */
    public Retrofit getRetrofit() {
        return retrofit;
    }

    /**
     * 切换baseurl
     * @return
     */
    public RetrofitFactory defaultBaseUrl(){
        retrofit = new Retrofit.Builder()
                .client(okHttpBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .baseUrl(BASE_URL)
                .build();
        httpApi = retrofit.create(HttpApi.class);
        return this;
    }

    /**
     * 切换baseurl
     * @param baseUrl
     * @return
     */
    public RetrofitFactory changeBaseUrl(String baseUrl) {
        retrofit = new Retrofit.Builder()
                .client(okHttpBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
        httpApi = retrofit.create(HttpApi.class);
        return this;
    }

    /**
     * 获取httpService
     *
     * @return
     */
    public HttpApi getHttpApi() {
        return httpApi;
    }

    /**
     * 设置订阅 和 所在的线程环境
     */
    public <T> void toSubscribe(Observable<T> o, DisposableObserver<T> s) {

        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(RETRY_COUNT)//请求失败重连次数
                .subscribe(s);

    }
}