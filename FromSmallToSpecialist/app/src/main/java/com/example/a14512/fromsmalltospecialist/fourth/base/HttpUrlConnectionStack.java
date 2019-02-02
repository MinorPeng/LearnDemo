package com.example.a14512.fromsmalltospecialist.fourth.base;

import com.example.a14512.fromsmalltospecialist.fourth.base.e_num.HttpMethod;
import com.example.a14512.fromsmalltospecialist.fourth.imp.HttpStack;
import com.example.a14512.fromsmalltospecialist.fourth.config.Config;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 14512 on 2018/4/7
 */

public class HttpUrlConnectionStack implements HttpStack {

    @Override
    public Response performRequest(Request<?> request) {
        HttpURLConnection urlConnection = null;
        try {
            //构建HttpURLConnection
            urlConnection = createUrlConnection(request.getUrl());
            //设置headers
            setRequestHeaders(urlConnection, request);
            //设置Body参数
            setRequestParams(urlConnection, request);
            return fetchResponse(urlConnection);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }

    private Response fetchResponse(HttpURLConnection connection) throws IOException {
        ProtocolVersion protocolVersion = new ProtocolVersion("HTTP", 1, 1);
        int responseCode = connection.getResponseCode();
        if (responseCode == -1) {
            throw new IOException("Could not retrieve response code from HttpUrlConnection");
        }
        //状态行数据
        StatusLine statusLine = (StatusLine) new BasicStatusLine(protocolVersion, connection.getResponseCode(),
                connection.getResponseMessage());
        //构建Response
        Response response = new Response(statusLine);
        //设置Response数据
        response.setEntity(entityFromURLConnwction(connection));
        addHeadersToResponse(response, connection);
        return response;
    }

    private void addHeadersToResponse(BasicHttpResponse response, HttpURLConnection connection) {
        for (Map.Entry<String, List<String>> header : connection.getHeaderFields().entrySet()) {
            if (header.getKey() != null) {
                Header h = (Header) new BasicHeader(header.getKey(), header.getValue().get(0));
                response.addHeader(h);
            }
        }
    }

    private HttpEntity entityFromURLConnwction(HttpURLConnection connection) {
        BasicHttpEntity entity = new BasicHttpEntity();
        InputStream inputStream = null;
        try {
            inputStream = connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            inputStream = connection.getErrorStream();
        }
        entity.setContent(inputStream);
        entity.setContentLength(connection.getContentLength());
        entity.setContentEncoding(connection.getContentEncoding());
        entity.setContentType(connection.getContentType());
        return (HttpEntity) entity;
    }

    private void setRequestParams(HttpURLConnection connection, Request<?> request) throws IOException {
        HttpMethod method = request.getHttpMethod();
        connection.setRequestMethod(method.toString());
        byte[] body = request.getBody();
        if (body != null) {
            connection.setDoOutput(true);
            connection.addRequestProperty(Request.HEADER_CONTENT_TYPE, request.getBodyContentType());
            DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
            dataOutputStream.write(body);
            dataOutputStream.close();
        }
    }

    private void setRequestHeaders(HttpURLConnection connection, Request<?> request) {
        Set<String> headersKeys = request.getnHeaders().keySet();
        for (String headerName : headersKeys) {
            connection.addRequestProperty(headerName, request.getnHeaders().get(headerName));
        }
    }

    private HttpURLConnection createUrlConnection(String url) throws IOException {
        URL newURL = new URL(url);
        URLConnection urlConnection = newURL.openConnection();
        urlConnection.setConnectTimeout(Config.CONN_TIME_OUT);
        urlConnection.setReadTimeout(Config.SO_TIME_OUT);
        urlConnection.setDoInput(true);
        urlConnection.setUseCaches(false);
        return (HttpURLConnection) urlConnection;
    }
}
