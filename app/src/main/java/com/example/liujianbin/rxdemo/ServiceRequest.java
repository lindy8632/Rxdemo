package com.example.liujianbin.rxdemo;

import com.example.liujianbin.rxdemo.request.KKBaseRequest;
import com.example.liujianbin.rxdemo.request.annotation.KKGet;
import com.example.liujianbin.rxdemo.request.annotation.KKGsonList;

import org.json.JSONObject;


@KKGsonList(ServiceInfo.class)
@KKGet
public class ServiceRequest extends KKBaseRequest<JSONObject, ServiceInfo> {
    @Override
    protected String getSubUrl()
    {
        return "rn/service/android/server.json";
    }
}
