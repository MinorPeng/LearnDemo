package com.example.a14512.mvvmdemo.net;

/**
 * @author 14512 on 2018/8/20
 */
public class PreDealException extends RuntimeException {

    private String errorMsg;

    public PreDealException(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String getMessage() {
        return this.errorMsg;
    }
}
