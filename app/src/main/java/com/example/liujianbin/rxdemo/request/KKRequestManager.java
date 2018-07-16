package com.example.liujianbin.rxdemo.request;

import android.app.Application;

import com.example.liujianbin.rxdemo.request.Interceptor.KKBaseInterceptor;
import com.example.liujianbin.rxdemo.request.convertor.KKBaseDecoder;
import com.example.liujianbin.rxdemo.request.convertor.KKJsonDecoder;
import com.example.liujianbin.rxdemo.request.enumaration.KKRequestMethod;
import com.zhouyou.http.EasyHttp;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class KKRequestManager
{
    Set<KKBaseRequest> set = new HashSet<KKBaseRequest>();
    private String baseUrl;
    private int timeout;
    private KKBaseDecoder defaultDecoder;

    private volatile static KKRequestManager mInstance;
    private KKBaseInterceptor interceptor;

    private int mRequestMethod = KKRequestMethod.POSTBODY;

    public static KKRequestManager getInstance()
    {
        if (mInstance == null)
        {
            synchronized (KKRequestManager.class)
            {
                if ( mInstance == null )
                {
                    mInstance = new KKRequestManager();
                    mInstance.defaultDecoder = new KKJsonDecoder();
                    EasyHttp.init(mInstance.getApplication());
                }
            }
        }

        return mInstance;
    }

    private Application getApplication()
    {
        Application application = null;
        Class<?> activityThreadClass;
        try {
            activityThreadClass = Class.forName("android.app.ActivityThread");
            Field appField = activityThreadClass
                    .getDeclaredField("mInitialApplication");
            // Object object = activityThreadClass.newInstance();
            final Method method = activityThreadClass.getMethod(
                    "currentActivityThread", new Class[0]);
            // 得到当前的ActivityThread对象
            Object localObject = method.invoke(null, (Object[]) null);
            appField.setAccessible(true);
            application = (Application) appField.get(localObject);

            return application;
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    void add(KKBaseRequest request)
    {
        this.set.add(request);
    }

    void remove(KKBaseRequest request)
    {
        this.set.remove(request);
    }

    public KKBaseDecoder getDefaultDecoder()
    {
        return defaultDecoder;
    }

    public int getDefaultRequestMethod()
    {
        return mRequestMethod;
    }

    public KKRequestManager setDefaultDecoder(KKBaseDecoder defaultDecoder)
    {
        this.defaultDecoder = defaultDecoder;

        return this;
    }

    public String getBaseUrl()
    {
        return baseUrl;
    }

    public KKRequestManager setBaseUrl(String baseUrl)
    {
        this.baseUrl = baseUrl;
        EasyHttp.getInstance().setBaseUrl(baseUrl);
        return this;
    }

    public KKRequestManager setTimeOut(int seconds)
    {
        timeout = seconds;

        EasyHttp.getInstance().setReadTimeOut(timeout * 1000).setWriteTimeOut(timeout * 1000).setConnectTimeout(timeout * 1000);

        return this;
    }

    public KKBaseInterceptor getInterceptor()
    {
        return interceptor;
    }

    public KKRequestManager setInterceptor(KKBaseInterceptor interceptor)
    {
        this.interceptor = interceptor;

        return this;
    }

    public KKRequestManager setMethodGet()
    {
        mRequestMethod = KKRequestMethod.GET;

        return this;
    }

    public KKRequestManager setMethodPostQuery()
    {
        mRequestMethod = KKRequestMethod.POSTQUERY;

        return this;
    }

    public KKRequestManager setMethodPut()
    {
        mRequestMethod = KKRequestMethod.PUT;

        return this;
    }

    public KKRequestManager setMethodDelete()
    {
        mRequestMethod = KKRequestMethod.DELETE;

        return this;
    }

    public KKRequestManager setMethodDownload()
    {
        mRequestMethod = KKRequestMethod.DOWNLOAD;

        return this;
    }

    public KKRequestManager setMethodUpload()
    {
        mRequestMethod = KKRequestMethod.UPLOAD;

        return this;
    }
}
