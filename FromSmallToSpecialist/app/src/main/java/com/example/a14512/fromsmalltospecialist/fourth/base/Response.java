package com.example.a14512.fromsmalltospecialist.fourth.base;

/**
 * @author 14512 on 2018/4/7
 */

public class Response extends BasicHttpResponse {
    public byte[] rawData = new byte[0];
    private int mStatusCode;
    private String mMessage;

    public Response(StatusLine statusLine) {
        super(statusLine);
    }

    public Response(ProtocolVersion ver, int code, String reason) {
        super(ver, code, reason);
    }

    @Override
    public void setEntity(HttpEntity entity) {
        super.setEntity(entity);
        rawData = entityToBytes(getEntity());
    }

    private HttpEntity getEntity() {
        return null;
    }

    private byte[] entityToBytes(HttpEntity entity) {
//        try {
//            return EntityUtils.toByteArray(entity);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return new byte[0];
    }


    public void setMessage(String message) {
        this.mMessage = message;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setStatusCode(int statusCode) {
        this.mStatusCode = statusCode;
    }

    public int getStatusCode() {
        return mStatusCode;
    }

    public byte[] getRawData() {
        return rawData;
    }
}
