package com.example.liujianbin.rxdemo.request;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface RetrofitApiService
{
    @GET
    Call<ResponseBody> get(@Url String url, @QueryMap Map<String, String> params);

//    @GET
//    Call<ResponseBody> getWithHeader(@Url String url, @QueryMap Map<String, String> params);

    @POST
    Call<ResponseBody> postWithQuery(@Url String url, @QueryMap Map<String, String> params);

    @POST
    Call<ResponseBody> postWithBody(@Url String url, @Body Map<String, String> params);
}
