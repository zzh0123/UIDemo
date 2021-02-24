package com.zzh.uidemo.constant;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

;

/**
 * 存放所有的Api
 */

public interface HttpApi {

    // 登录
    @POST("mobileTerminal/loginV2")
    @FormUrlEncoded
    Observable<ResponseBody> login(@FieldMap Map<String, String> map);

    // 获取会议列表信息（大屏）
    @GET("terminalConfig/bigScreenList/{terminalNo}")
    Observable<ResponseBody> getMeetingListBig(@Path("terminalNo") String terminalNo);

    // 获取会议列表信息（小屏）
    @GET("terminalConfig/hangingScreenList/{terminalNo}")
    Observable<ResponseBody> getMeetingListSmall(@Path("terminalNo") String terminalNo);

    // 获取天气信息（大屏）
    @GET("v1/weather/weatherInfo")
    Observable<ResponseBody> getWeather(@Query("location") String location, @Query("ak") String ak);

    // 更新APP（大小屏）
    @GET("v1/weather/weatherInfo")
    Observable<ResponseBody> update(@Query("type") String type);
}