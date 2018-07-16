package com.example.liujianbin.rxdemo;

import com.example.liujianbin.rxdemo.request.convertor.KKBaseCoveter;

import java.util.ArrayList;
import java.util.List;

public class LoginConveter extends KKBaseCoveter<String, List<Integer>>
{
    @Override
    public List<Integer> onReceieve(String object)
    {
        List b  = new ArrayList();
        b.add(1);
        b.add(3);
        b.add(5);
        return b;
    }
}
