package com.example.liujianbin.rxdemo.request.convertor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class KKGsonDecoder<T> extends KKBaseDecoder<T>
{
    Class<?> mClass;
    boolean mIsList;

    public KKGsonDecoder(Class<T> cls)
    {
        mClass = cls;
    }

    public KKGsonDecoder(Class<?> cls, boolean isList)
    {
        mClass = cls;
        mIsList = true;
    }


    @Override
    public T decode(String value)
    {
        try
        {
            if ( mIsList )
            {
                List list = new ArrayList<>();

                JSONArray jsonArray = new JSONArray(value);
                int length = jsonArray.length();
                for (int i = 0; i < length; i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    GsonBuilder builder = new GsonBuilder();
                    builder.excludeFieldsWithoutExposeAnnotation();
                    Gson gson = builder.create();
                    list.add(gson.fromJson(jsonObject.toString(), mClass));
                }

                return (T)list;
            }
            else
            {
                GsonBuilder builder = new GsonBuilder();
                builder.excludeFieldsWithoutExposeAnnotation();
                Gson gson = builder.create();

                T b = gson.fromJson(value, (Class<T>) mClass);

//                TypeToken t = new TypeToken<TestObject<List<yaozeyulist>>>(){};
//
//                T b = gson.fromJson(value, new TypeToken<TestObject<List<yaozeyulist>>>(){}.getType());

                return b;
            }
       }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
