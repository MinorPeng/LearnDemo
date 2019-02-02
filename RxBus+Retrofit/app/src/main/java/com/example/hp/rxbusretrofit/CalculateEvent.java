package com.example.hp.rxbusretrofit;

/**
 * Created by HP on 2017/5/6.
 */

public class CalculateEvent {
    String result;    //计算的结果

    public CalculateEvent(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


    @Override
    public String toString() {
        return "CalculateEvent{" +
                "result='" + result + '\'' +
                '}';
    }
}
