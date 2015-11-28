package com.xuhai.telescopes.httpclient.httpResponseHandle;

import com.xuhai.telescopes.MyHelper;
import com.xuhai.telescopes.domain.Ally;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chudong on 2015/11/25.
 */
public class CreateAnAllyJsonHttpResponseHandle extends BaseJsonHttpResponseHandle {

    public CreateAnAllyJsonHttpResponseHandle() {
        super();
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        super.onSuccess(statusCode, headers, response);
        List<Ally> Allies = new ArrayList<Ally>();
        JSONObject allyjson = null;
        if (statusCode ==200){
            try {
                 allyjson = response.getJSONObject("ally");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Ally ally = setAllyFromJson(allyjson);
            Allies.add(ally);
            MyHelper.getInstance().saveAllies(Allies);
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        super.onFailure(statusCode, headers, throwable, errorResponse);
    }
}
