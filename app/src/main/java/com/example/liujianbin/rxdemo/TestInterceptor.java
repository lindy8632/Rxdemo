package com.example.liujianbin.rxdemo;

import com.example.liujianbin.rxdemo.request.Interceptor.KKBaseInterceptor;
import com.example.liujianbin.rxdemo.request.Interceptor.KKBaseInterceptorResult;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.request.BaseRequest;

import org.json.JSONObject;

public class TestInterceptor extends KKBaseInterceptor<JSONObject>
{
    public boolean willStart() {
        return true;
    };

    //这里可以改head
    public boolean willExecute(BaseRequest request)
    {
        return true;
    };

//    //增加一些公共的参数
//    public HashMap<String, String> canModifyParams(HashMap<String, String> params)
//    {
//        params.put("key","hello");
//        return params;
//    };

    public KKBaseInterceptorResult onReceieve(JSONObject object)
    {
        KKBaseInterceptorResult r = new KKBaseInterceptorResult();
        r.setCode(0);
        r.setMessage("12");
        try
        {
            r.setData(object.get("creditService"));
        }
        catch (Exception e)
        {

        }

        return r;
    };

    public KKBaseInterceptorResult onError(ApiException e)
    {
        //这里转换成自己需要的错误
        return null;
    }
}
