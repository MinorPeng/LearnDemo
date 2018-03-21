package com.example.hp.servicebestpractice;

/**
 * Created by HP on 2017/2/9.
 */

public interface DownloadListener {

    void onProgress(int progress);  //通知当前的下载进度
    void onSuccess();  //通知下载成功事件
    void onFailed();  //通知失败事件
    void onPaused();  //通知暂停事件
    void onCanceled();  //通知取消事件
}
