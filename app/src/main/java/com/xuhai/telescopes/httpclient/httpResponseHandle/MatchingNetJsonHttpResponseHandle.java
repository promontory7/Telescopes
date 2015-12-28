package com.xuhai.telescopes.httpclient.httpResponseHandle;

import android.util.Log;

import com.xuhai.telescopes.domain.MatchingData;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chudong on 2015/12/26.
 */
public class MatchingNetJsonHttpResponseHandle extends BaseJsonHttpResponseHandle {
    public MatchingNetJsonHttpResponseHandle() {
        super();
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        super.onFailure(statusCode, headers, throwable, errorResponse);
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        super.onSuccess(statusCode, headers, response);
        if(statusCode==200){
            try {
                JSONObject data = response.getJSONObject("data");
                MatchingData matchingData =setMatchingNetFromJson(data);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
