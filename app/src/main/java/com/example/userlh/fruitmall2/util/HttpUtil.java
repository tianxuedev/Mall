package com.example.userlh.fruitmall2.util;
import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;
/**
 * Created by userLH on 2018/1/29.
 */

public class HttpUtil {
    public static void sendOkHttpRequest(String address,okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
