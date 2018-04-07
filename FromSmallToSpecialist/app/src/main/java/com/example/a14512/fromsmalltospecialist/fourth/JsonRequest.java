package com.example.a14512.fromsmalltospecialist.fourth;

import com.example.a14512.fromsmalltospecialist.fourth.base.HttpMethod;
import com.example.a14512.fromsmalltospecialist.fourth.base.Request;
import com.example.a14512.fromsmalltospecialist.fourth.base.Response;
import com.example.a14512.fromsmalltospecialist.fourth.imp.RequestListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author 14512 on 2018/4/7
 */

public class JsonRequest extends Request<JSONObject> {


    /**
     * @param method   请求方式
     * @param url      目标URL
     * @param listener 请求回调
     */
    public JsonRequest(HttpMethod method, String url, RequestListener<JSONObject> listener) {
        super(method, url, listener);
    }

    @Override
    public JSONObject parseResponse(Response response) {
        String jsonStr = new String(response.getRawData());
        try {
            return new JSONObject(jsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
