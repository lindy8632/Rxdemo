package com.example.liujianbin.rxdemo.request.convertor;

public interface KKBaseCoveterInterface<U,V>
{
    V onReceieve(U object);
}
