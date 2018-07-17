package com.example.liujianbin.rxdemo.request.Interceptor;

import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.request.BaseRequest;

abstract public class KKBaseInterceptor<T>
{
    public boolean willStart() {return true;}
    public boolean willExecute(BaseRequest request) { return true; };
    //public HashMap<String, String> canModifyParams(HashMap<String, String> params) { return params; };
    public KKBaseInterceptorResult onReceieve(T object) { return null; };
    public KKBaseInterceptorResult onError(ApiException e) { return null; };
}
