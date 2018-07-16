package com.example.liujianbin.rxdemo.request;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

public class RetrofitManager
{
    private volatile static RetrofitManager mInstance;
    private RetrofitApiService mRequestServices;

    public static RetrofitManager getInstance()
    {
        if (mInstance == null)
        {
            synchronized (RetrofitManager.class)
            {
                if ( mInstance == null )
                {
                    mInstance = new RetrofitManager();
                    mInstance.init();
                }
            }
        }

        return mInstance;
    }

    void init()
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://localhost:").build();

        mRequestServices = retrofit.create(RetrofitApiService.class);
    }

    Call<ResponseBody> get(String url, Map<String, String> params)
    {
        return mRequestServices.get(url, params);
    }

    Call<ResponseBody> postWithQuery(String url, Map<String, String> params)
    {
        return mRequestServices.postWithQuery(url, params);
    }

    Call<ResponseBody> postWithBody(String url, Map<String, String> params)
    {
        return mRequestServices.postWithBody(url, params);
    }
}
