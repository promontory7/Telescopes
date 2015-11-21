package com.xuhai.telescopes.httpclient.httpResponseHandle;


import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by chudong on 2015/11/20.
 */
public class LogoutJsonHttpResponseHandle extends BaseJsonHttpResponseHandle {
    public LogoutJsonHttpResponseHandle() {
        super();
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        super.onSuccess(statusCode, headers, response);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        super.onFailure(statusCode, headers, throwable, errorResponse);
    }

}
