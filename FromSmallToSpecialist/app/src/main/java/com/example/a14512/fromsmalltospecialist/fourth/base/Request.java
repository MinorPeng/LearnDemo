package com.example.a14512.fromsmalltospecialist.fourth.base;

import android.support.annotation.NonNull;

import com.example.a14512.fromsmalltospecialist.fourth.imp.RequestListener;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 14512 on 2018/4/7
 */

public abstract class Request<T> implements Comparable<Request<T>> {

    /**
     * 默认的编码方式
     */
    private static final String DEFAULT_PARAMS_ENCODING = "UTF-8";

    public static final String HEADER_CONTENT_TYPE = "";

    /**
     * 请求序列号
     */
    protected int mSerialNum = 0;

    /**
     * 设置默认优先级为NORMAL
     */
    protected Priority mPriority = Priority.NORMAL;

    /**
     * 是否取消该请求
     */
    protected boolean isCancel = false;

    /**
     * 请求是否缓存
     */
    private boolean mShouldCache = true;

    /**
     * 请求listener
     */
    protected RequestListener<T> mRequestListener;

    /**
     * 请求的Url
     */
    private String mUrl = "";

    /**
     * 默认请求方法
     */
    HttpMethod mHttpMethod = HttpMethod.GET;

    /**
     * 请求的header
     */
    private Map<String, String> nHeaders = new HashMap<>();

    /**
     * 请求参数
     */
    private Map<String, String> mBodyParams = new HashMap<>();

    /**
     * @param method 请求方式
     * @param url 目标URL
     * @param listener 请求回调
     */
    public Request(HttpMethod method, String url, RequestListener<T> listener) {
        this.mHttpMethod = method;
        this.mUrl = url;
        this.mRequestListener = listener;
    }

    /**
     * 原生的网络请求中解析结果
     * @param response
     * @return
     */
    public abstract T parseResponse(Response response);

    /**
     * 处理Response，运行在UI线程
     * @param response
     */
    public final void deliveryResponse(Response response) {
        T result = parseResponse(response);
        if (mRequestListener != null) {
            int stCode = response != null ? response.getStatusCode() : -1;
            String msg = response != null ? response.getMessage() : "unkown error";
            mRequestListener.onComplete(stCode, result, msg);
        }
    }

    protected String getParamsEncoding() {
        return DEFAULT_PARAMS_ENCODING;
    }

    public String getBodyContentType() {
        return "application/x-www-form-urlencoded; charset=" + getParamsEncoding();
    }

    /**
     * @return POST或PUT请求时的Body参数字节数组
     */
    public byte[] getBody() {
        Map<String, String> params = getParams();
        if (params != null && params.size() > 0) {
            return encodeParameters(params, getParamsEncoding());
        }
        return null;
    }

    /**
     * 将参数转换为URL编码的参数串，key1=value1&key2=value2
     * @param params
     * @param paramsEncoding
     * @return
     */
    private byte[] encodeParameters(Map<String, String> params, String paramsEncoding) {
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                encodedParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode(entry.getValue(), paramsEncoding));
                encodedParams.append('&');
            }
            return encodedParams.toString().getBytes(paramsEncoding);
        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
            throw new RuntimeException("Encoding not supported: " + paramsEncoding, e);
        }
    }

    @Override
    public int compareTo(@NonNull Request<T> o) {
        Priority myPriority = this.getPriority();
        Priority another = o.getPriority();
        //如果优先级相等就按照添加到队列的序列号顺序执行
        return myPriority.equals(o) ? this.getSerialNumber() - o.getSerialNumber() :
                myPriority.ordinal() - another.ordinal();
    }

    public void setSerialNum(int serialNum) {
        this.mSerialNum = serialNum;
    }

    public int getSerialNumber() {
        return mSerialNum;
    }

    public void setPriority(Priority priority) {
        this.mPriority = priority;
    }

    public Priority getPriority() {
        return mPriority;
    }

    public void setBodyParams(Map<String, String> bodyParams) {
        this.mBodyParams = bodyParams;
    }

    public Map<String, String> getParams() {
        return mBodyParams;
    }

    public boolean isCancel() {
        return isCancel;
    }

    public void setCancel(boolean cancel) {
        this.isCancel = cancel;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }

    public String getUrl() {
        return mUrl;
    }

    public boolean isShouldCache() {
        return mShouldCache;
    }

    public void setShouldCache(boolean shouldCache) {
        this.mShouldCache = shouldCache;
    }

    public Map<String, String> getnHeaders() {
        return nHeaders;
    }

    public HttpMethod getHttpMethod() {
        return mHttpMethod;
    }
}
