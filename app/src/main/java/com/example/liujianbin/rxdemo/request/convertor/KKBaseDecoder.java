package com.example.liujianbin.rxdemo.request.convertor;

import java.lang.reflect.Type;

public abstract class KKBaseDecoder<T> implements KKDecoderInterface<T>
{
    private T resultData;
    private Type type;

    public T getResultData()
    {
        return resultData;
    }
    public void setResultData(T resultData)
    {
        this.resultData = resultData;
    }

    public void setType(Type type) { this.type = type; }
}
