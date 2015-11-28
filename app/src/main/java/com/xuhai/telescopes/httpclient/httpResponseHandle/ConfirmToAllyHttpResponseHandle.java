package com.xuhai.telescopes.httpclient.httpResponseHandle;

import android.util.Log;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by chudong on 2015/11/27.
 */
public class ConfirmToAllyHttpResponseHandle extends BaseJsonHttpResponseHandle {
    public ConfirmToAllyHttpResponseHandle() {
        super();
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        super.onSuccess(statusCode, headers, response);
        if(statusCode == 200) {
            Log.e("ConfirmToAlly","加入群成功"+response.toString());

        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        super.onFailure(statusCode, headers, throwable, errorResponse);
    }
}
