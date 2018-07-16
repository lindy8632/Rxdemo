package com.example.liujianbin.rxdemo.request.convertor;

import org.json.JSONException;
import org.json.JSONObject;

public class KKJsonDecoder extends KKBaseDecoder<JSONObject>
{
    @Override
    public JSONObject decode(String value)
    {
        JSONObject jsonObject = null;
        try
        {
            jsonObject = new JSONObject(value);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        setResultData(jsonObject);

        return jsonObject;
    }
}
