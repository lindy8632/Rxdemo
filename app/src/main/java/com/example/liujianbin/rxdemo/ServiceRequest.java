package com.example.liujianbin.rxdemo;

import com.example.liujianbin.rxdemo.request.KKBaseRequest;
import com.example.liujianbin.rxdemo.request.annotation.KKGet;
import com.example.liujianbin.rxdemo.request.annotation.KKGsonList;
import com.example.liujianbin.rxdemo.request.annotation.KKGsonObject;
import com.example.liujianbin.rxdemo.request.annotation.KKSubUrl;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.annotation.Annotation;
import java.util.List;


@KKGsonObject(ServiceInfo.class)
@KKGet
public class ServiceRequest extends KKBaseRequest<JSONObject, ServiceInfo> {
    @Override
    protected String getSubUrl()
    {
        return "rn/service/android/server.json";
    }

}
