package com.example.liujianbin.rxdemo;

import com.google.gson.annotations.Expose;

public class TestObject<T>
{
    @Expose
    int code;
    @Expose
    String message;
    @Expose
    public T content;
}
