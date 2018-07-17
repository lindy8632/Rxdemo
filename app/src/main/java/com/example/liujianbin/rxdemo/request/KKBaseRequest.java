package com.example.liujianbin.rxdemo.request;

import android.os.Handler;
import android.util.Log;

import com.example.liujianbin.rxdemo.request.Interceptor.KKBaseInterceptor;
import com.example.liujianbin.rxdemo.request.Interceptor.KKBaseInterceptorResult;
import com.example.liujianbin.rxdemo.request.annotation.KKBaseUrl;
import com.example.liujianbin.rxdemo.request.annotation.KKDelete;
import com.example.liujianbin.rxdemo.request.annotation.KKDownload;
import com.example.liujianbin.rxdemo.request.annotation.KKGet;
import com.example.liujianbin.rxdemo.request.annotation.KKGsonList;
import com.example.liujianbin.rxdemo.request.annotation.KKGsonObject;
import com.example.liujianbin.rxdemo.request.annotation.KKPostBody;
import com.example.liujianbin.rxdemo.request.annotation.KKPostQuery;
import com.example.liujianbin.rxdemo.request.annotation.KKPut;
import com.example.liujianbin.rxdemo.request.annotation.KKSubUrl;
import com.example.liujianbin.rxdemo.request.annotation.KKUpload;
import com.example.liujianbin.rxdemo.request.convertor.KKBaseCoveter;
import com.example.liujianbin.rxdemo.request.convertor.KKBaseDecoder;
import com.example.liujianbin.rxdemo.request.enumaration.KKRequestMethod;
import com.example.liujianbin.rxdemo.request.error.KKRequestError;
import com.example.liujianbin.rxdemo.request.jsonHelper.JsonHelper;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.request.BaseRequest;
import com.zhouyou.http.request.GetRequest;
import com.zhouyou.http.request.PostRequest;

import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/* https://github.com/zhou-you/RxEasyHttp?utm_source=gold_browser_extension */
abstract public class KKBaseRequest<U,V>
{
    protected int mRetCode;
    protected String mRetMsg;
    protected HashMap<String, String> mRequestParams;

    protected SuccessClosure mSuccessClosure;
    protected FailedClosure mFailedClosure;
    protected CompleteClosure mCompleteClosure;

    private KKBaseCoveter coveter;

    private String mAnnotationBaseUrl;
    private String mAnnotationSubUrl;
    private Class mGsonListClass;
    private Class mGsonObjectClass;
    private int mRequestMethod = -1;

    public KKBaseRequest<U,V> execute()
    {
        return execute(null);
    }

    public KKBaseRequest<U,V> execute(SuccessClosure<V> closure)
    {
        if ( !willStart() )
        {
            new Handler().post( () -> { sendComplete(false); });

            return this;
        }

        parseAnnotation();

        KKBaseInterceptor interceptor = KKRequestManager.getInstance().getInterceptor();
        if ( interceptor != null )
        {
            if ( !interceptor.willStart() )
            {
                new Handler().post( () -> { sendComplete(false); });

                return this;
            }
        }

        if ( closure != null )
        {
            onSuccess(closure);
        }

        KKRequestManager.getInstance().add(this);

        BaseRequest request = getEasyHttpRequest();
        if ( interceptor != null )
        {
            if ( !interceptor.willExecute(request) )
            {
                new Handler().post( () -> { sendComplete(false); });

                return this;
            }
        }

        if ( getRequestMethod() == KKRequestMethod.GET || getRequestMethod() == KKRequestMethod.POSTQUERY )
        {
            StringBuilder builder = new StringBuilder();
            for (Map.Entry<String, String> entry : this.getParameters().entrySet())
            {
                builder.append(entry.getKey());
                builder.append("=");
                try {
                    builder.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                builder.append("&");
            }

            if (builder.length() > 0)
            {
                builder.deleteCharAt(builder.length() - 1);
            }

//            request.baseUrl(getBaseUrl() + "?" + builder.toString());
            request.baseUrl(getBaseUrl());
        }
        else
        {
            for (Map.Entry<String, String> entry : this.getParameters().entrySet())
            {
                request.params(entry.getKey(), entry.getValue());
            }
            request.baseUrl(getBaseUrl());
        }
        handleExecute(request);

        return this;
    }

    private void handleError(ApiException e)
    {
        KKBaseInterceptor interceptor = KKRequestManager.getInstance().getInterceptor();

        Log.e("jimmy", "请求失败了:");
        if ( interceptor != null )
        {
            KKBaseInterceptorResult result = interceptor.onError(e);
            if ( result != null )
            {
                KKRequestError error = new KKRequestError(result.getCode(), result.getMessage());
                error.setDebugErrorCode(e.getCode());
                sendError(error);
            }
            else
            {
                KKRequestError error = new KKRequestError(e.getCode(), e.getMessage());
                error.setDebugErrorCode(e.getCode());
                sendError(error);
            }
        }

        sendComplete(true );
    }

    private void handleSuccess(String response)
    {
        KKBaseInterceptor interceptor = KKRequestManager.getInstance().getInterceptor();

        Object target = getDecoder().decode(response);
        if ( target == null )
        {
            //不会走这里
            sendError(new KKRequestError(KKRequestError.ConvertError, "内部发生错误"));
        }
        else
        {
            try
            {
                if ( interceptor != null )
                {
                    KKBaseInterceptorResult result = interceptor.onReceieve((U)target);
                    if ( result != null )
                    {
                        if ( result.getCode() != 0 )
                        {
                            sendError(new KKRequestError(result.getCode(), result.getMessage()));
                        }

                        target = result.getData();
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                Log.e("jimmy", "跳过, 类型不匹配");
            }


            //适配类型不匹配的情况
            try
            {
                if ( target!= null )
                {
                    KKBaseCoveter c = getCoveter();

                    if ( c == null )
                    {
                        mSuccessClosure.onSuccess(onReceieve((U)target));
                    }
                    else
                    {
                        mSuccessClosure.onSuccess(c.onReceieve((U)target));
                    }
                }
                else
                {
                    sendError(new KKRequestError(KKRequestError.ConvertError, "内部发生错误"));
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                Log.e("jimmy", "数据结构转换失败");
                sendError(new KKRequestError(KKRequestError.ConvertError, "内部发生错误"));
            }
        }

        KKRequestManager.getInstance().remove( KKBaseRequest.this);
        sendComplete(true);
    }

    private void handleExecute(BaseRequest request)
    {
        int method = getRequestMethod();
        String url = getBaseUrl() + getSubUrl();
        if ( method == KKRequestMethod.GET )
        {
            ((GetRequest)request).execute(new KKSimpleCallBack());
        }
        else if ( method == KKRequestMethod.POSTQUERY || method == KKRequestMethod.POSTBODY )
        {
            ((PostRequest)request).execute(new KKSimpleCallBack());
        }
        else if ( method == KKRequestMethod.DOWNLOAD || method == KKRequestMethod.UPLOAD || method == KKRequestMethod.PUT || method == KKRequestMethod.DELETE )
        {

        }
    }

    class KKSimpleCallBack extends SimpleCallBack<String>
    {
        @Override
        public void onError(ApiException e)
        {
            Log.e("jimmy","------KKSimpleCallBack onError-----");
            handleError(e);
        }

        @Override
        public void onSuccess(String response)
        {
            Log.e("jimmy","------KKSimpleCallBack onSuccess-----");
            handleSuccess(response);
        }
    }

    private void sendError(KKRequestError e)
    {
        if ( mFailedClosure != null )
        {
            mFailedClosure.onFailed(e);
        }
    }

    private void sendComplete(boolean isSend)
    {
        if ( mCompleteClosure != null )
        {
            mCompleteClosure.onComplete(isSend);
        }
    }

    private BaseRequest getEasyHttpRequest()
    {
        int method = getRequestMethod();
        String url = getSubUrl();
        if ( method == KKRequestMethod.GET )
        {
            return EasyHttp.get(url);
        }
        else if ( method == KKRequestMethod.POSTQUERY )
        {
            return EasyHttp.post(url);
        }
        else if ( method == KKRequestMethod.POSTBODY )
        {
            return EasyHttp.post(url);
        }
        else if ( method == KKRequestMethod.PUT )
        {
            return EasyHttp.put(url);
        }
        else if ( method == KKRequestMethod.DELETE )
        {
            return EasyHttp.delete(url);
        }
        else if ( method == KKRequestMethod.DOWNLOAD )
        {
            return EasyHttp.downLoad(url);
        }
        else if ( method == KKRequestMethod.UPLOAD )
        {
            return EasyHttp.post(url);
        }
        return EasyHttp.get(url);
    }

    private void parseAnnotation()
    {
        Annotation[] s = this.getClass().getDeclaredAnnotations();
        for (Annotation annotation : s)
        {
            if ( annotation instanceof KKGsonList)
            {
                mGsonListClass = ((KKGsonList) annotation).value();
            }
            else if ( annotation instanceof KKGsonObject)
            {
                mGsonObjectClass = ((KKGsonObject) annotation).value();
            }
            else if ( annotation instanceof KKSubUrl)
            {
                mAnnotationSubUrl = ((KKSubUrl) annotation).value();
            }
            else if ( annotation instanceof KKBaseUrl)
            {
                mAnnotationBaseUrl = ((KKBaseUrl) annotation).value();
            }
            else if ( annotation instanceof KKGet)
            {
                mRequestMethod = KKRequestMethod.GET;
            }
            else if ( annotation instanceof KKPostQuery)
            {
                mRequestMethod = KKRequestMethod.POSTQUERY;
            }
            else if ( annotation instanceof KKPostBody)
            {
                mRequestMethod = KKRequestMethod.POSTBODY;
            }
            else if ( annotation instanceof KKPut)
            {
                mRequestMethod = KKRequestMethod.PUT;
            }
            else if ( annotation instanceof KKDelete)
            {
                mRequestMethod = KKRequestMethod.DELETE;
            }
            else if ( annotation instanceof KKDownload)
            {
                mRequestMethod = KKRequestMethod.DOWNLOAD;
            }
            else if ( annotation instanceof KKUpload)
            {
                mRequestMethod = KKRequestMethod.UPLOAD;
            }
        }
    }

    protected boolean willStart()
    {
        return true;
    }
    protected void willExecute(BaseRequest request)
    {

    }

    protected String getBaseUrl()
    {
        if ( mAnnotationBaseUrl != null && mAnnotationBaseUrl.length() > 0 )
        {
            return mAnnotationBaseUrl;
        }

        return KKRequestManager.getInstance().getBaseUrl();
    }

    protected String getSubUrl()
    {
        if ( mAnnotationSubUrl != null && mAnnotationSubUrl.length() > 0 )
        {
            return mAnnotationSubUrl;
        }

        return "";
    }

    protected KKBaseDecoder getDecoder()
    {
        return KKRequestManager.getInstance().getDefaultDecoder();
    }

    protected int getRequestMethod()
    {
        if ( mRequestMethod != -1 )
        {
            return mRequestMethod;
        }

        return KKRequestManager.getInstance().getDefaultRequestMethod();
    }

    protected HashMap<String, String> getParameters() { return mRequestParams != null ? mRequestParams : new HashMap<String, String>(); }

    protected V onReceieve(U object)
    {
        if ( mGsonListClass != null )
        {
            return (V) JsonHelper.json2List(object.toString(),  mGsonListClass);
        }

        if ( mGsonObjectClass != null )
        {
            return (V) JsonHelper.json2Object(object.toString(),  mGsonObjectClass);
        }

        return (V)object;
    }

    public KKBaseCoveter getCoveter()
    {
        return coveter;
    }

    public KKBaseRequest<U,V> addCoveter(KKBaseCoveter coveter)
    {
        this.coveter = coveter;

        return this;
    }

    public interface SuccessClosure<T>
    {
        void onSuccess(T a);
    }

    public interface FailedClosure
    {
        void onFailed(KKRequestError error);
    }

    public interface CompleteClosure
    {
        void onComplete(boolean isSend);
    }

    public KKBaseRequest<U,V> onSuccess(SuccessClosure<V> closure)
    {
        mSuccessClosure = closure;
        return this;
    }

    public KKBaseRequest<U,V> onFailed(FailedClosure closure)
    {
        mFailedClosure = closure;
        return this;
    }

    public KKBaseRequest<U,V> onComplete(CompleteClosure closure)
    {
        mCompleteClosure = closure;
        return this;
    }

    public <T> void onSpecialSuccess(SuccessClosure<T> closure)
    {
        mSuccessClosure = closure;
    }
}

