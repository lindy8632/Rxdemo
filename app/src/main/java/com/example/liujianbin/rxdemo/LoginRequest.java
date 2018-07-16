package com.example.liujianbin.rxdemo;

import com.example.liujianbin.rxdemo.request.KKBaseRequest;
import com.example.liujianbin.rxdemo.request.annotation.KKGet;
import com.example.liujianbin.rxdemo.request.annotation.KKGsonList;

import org.json.JSONArray;

import java.util.List;

@KKGsonList(yaozeyulist.class)
@KKGet
public class LoginRequest extends KKBaseRequest<JSONArray, List<yaozeyulist>>
{
    @Override
    protected String getSubUrl()
    {
        return "yaozeyu.json";
    }

//    @Override
//    protected List<yaozeyulist> onReceieve(JSONArray object)
//    {
//        Annotation[] s = this.getClass().getDeclaredAnnotations();
//        for (Annotation annotation : s) {
//            if ( annotation instanceof KKSubUrl)
//            {
//                //Log.e("jimmy", ((KKSubUrl)annotation).value());
//                return JsonUtils.json2List(object.toString(),  ((KKSubUrl)annotation).value());
//            }
//        }
//
//        //return JsonUtils.json2List(object.toString(), yaozeyulist.class);
//        return  null;
//    }
}
