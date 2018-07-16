package com.example.liujianbin.rxdemo.request.Interceptor;

public class KKBaseInterceptorResult<T>
{
    private int code;
    private String message;
    private T data;

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMessage()
    {
        return ( message != null ? message : "" );
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public T getData()
    {
        return data;
    }

    public void setData(T data)
    {
        this.data = data;
    }
}
