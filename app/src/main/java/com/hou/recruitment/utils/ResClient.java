package com.hou.recruitment.utils;

/**
 * @author Fred Liu(liuxiaokun0410@gmail.com)
 * @version v1.0.0
 * @since 2016-04-12 17:19
 */

import com.hou.recruitment.common.Constant;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


public class ResClient {

    private static AsyncHttpClient client;

    public static void post(RequestParams params, String url,
                            AsyncHttpResponseHandler responseHandler) {
        client = new AsyncHttpClient();
        client.setTimeout(60000);
        client.post(url, params, responseHandler);
    }

    public static void get(RequestParams params, String url,
                           AsyncHttpResponseHandler responseHandler) {
        if (params == null) {
            params = new RequestParams();
        }
        client = new AsyncHttpClient();
        client.setTimeout(60000);
        client.get(url, params, responseHandler);
    }


    public static void login(RequestParams params, String id,
                             AsyncHttpResponseHandler handler) {
        get(params, Constant.LOGIN_URL + id, handler);
    }

    public static void getStudentInfo(RequestParams params, AsyncHttpResponseHandler handler) {
        post(params, Constant.GET_STUDENT_INFO, handler);
    }

    public static void submitScore(RequestParams params, String url, AsyncHttpResponseHandler handler) {

        get(params, url, handler);
    }
}
