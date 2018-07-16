package com.example.liujianbin.rxdemo;

public class TestClass<T>
{
    public interface SuccessClosure<K> {
        void onSuccess(K a);
    }

    public void onSuccess(SuccessClosure<T> closure)
    {
    }
}
