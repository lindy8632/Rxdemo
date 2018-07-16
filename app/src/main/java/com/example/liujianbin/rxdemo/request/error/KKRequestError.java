package com.example.liujianbin.rxdemo.request.error;

public class KKRequestError
{
    public static final int UNKNOWN = 1000;
    public static final int PARSE_ERROR = UNKNOWN + 1;                      //解析错误
    public static final int NETWORD_ERROR = PARSE_ERROR + 1;                //网络错误
    public static final int HTTP_ERROR = NETWORD_ERROR + 1;                 //协议出错
    public static final int SSL_ERROR = HTTP_ERROR + 1;                     //证书出错
    public static final int TIMEOUT_ERROR = SSL_ERROR + 1;                  //链接超时
    public static final int INVOKE_ERROR = TIMEOUT_ERROR + 1;               //调用错误
    public static final int CAST_ERROR = INVOKE_ERROR + 1;                  //类转换错误
    public static final int REQUEST_CANCEL = CAST_ERROR + 1;                //请求取消
    public static final int UNKNOWNHOST_ERROR = REQUEST_CANCEL + 1;         //未知主机错误
    public static final int NULLPOINTER_EXCEPTION = UNKNOWNHOST_ERROR + 1;  //空指针错误

    public static final int ConvertError = 2000;                             //类型转换错误，不应该走这个，调用的代码没写对

    private int debugErrorCode;
    private String message;
    private int code;

    public KKRequestError(){}

    public KKRequestError(int code, String message)
    {
        this.code = code;
        this.message = message;
    }

    public int getDebugErrorCode()
    {
        return debugErrorCode;
    }

    public void setDebugErrorCode(int debugErrorCode)
    {
        this.debugErrorCode = debugErrorCode;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }
}