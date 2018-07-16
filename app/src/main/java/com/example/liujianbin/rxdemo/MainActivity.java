package com.example.liujianbin.rxdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.liujianbin.rxdemo.request.KKRequestManager;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity
{
    static String TAG = "jimmy";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test();
    }

    void test()
    {
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(3);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        });
        //创建一个下游 Observer
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, "subscribe");
            }

            @Override
            public void onNext(Integer value) {
                Log.e(TAG, "" + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "error");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "complete");
            }
        };
        //        //建立连接
        observable.subscribe(observer);

//        KKRequestManager.getInstance().setBaseUrl("http://10.139.40.171:8080/").setInterceptor(new TestInterceptor()).setTimeOut(30);
        KKRequestManager.getInstance().setBaseUrl("http://d.kkcredit.cn/").setInterceptor(new TestInterceptor()).setTimeOut(30);

//        EasyHttp.getInstance()
//                .setReadTimeOut(30 * 1000)
//                .setWriteTimeOut(30 * 1000)
//                .setConnectTimeout(30 * 1000)
//                .setBaseUrl("http://10.139.11.11:8280/");

//        new LoginRequest().execute().onSuccess((List<yaozeyulist> data) -> {
//            Log.e("jimmy", "最后 请求成功： " + data.get(0).totalBill);
//        }).onFailed( e -> {
//            Log.e("jimmy", "最后 请求失败");
//        }).onComplete( e -> {
//            Log.e("jimmy", "最后 请求结束");
//        });

        new ServiceRequest().execute().onSuccess((ServiceInfo info) ->{
            Log.e("jimmy", "ServiceRequest()最后 请求成功： " + info.getCreditService());
        }).onFailed( e -> {
            Log.e("jimmy", "ServiceRequest()最后 请求失败");
        }).onComplete( e -> {
            Log.e("jimmy", "ServiceRequest()最后 请求结束");
        });

//        LoginRequest s = new LoginRequest();
//        s.execute().addCoveter(new LoginConveter()).onSpecialSuccess( (List<Integer> data) -> {
//            int b= 4;
//            b = 4;
//            Log.e("jimmy", "成功");
//        } );
    }

}
