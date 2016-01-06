package com.xuhai.telescopes.httpclient.httpResponseHandle;

import android.util.Log;

import com.xuhai.telescopes.MyApplication;
import com.xuhai.telescopes.MyHelper;
import com.xuhai.telescopes.db.NetDao;
import com.xuhai.telescopes.domain.Net;
import com.xuhai.telescopes.domain.Seaman;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chudong on 2015/12/25.
 */
public class AsyncNetsListFromServer extends BaseJsonHttpResponseHandle {
    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        super.onSuccess(statusCode, headers, response);
        ArrayList<Net> nets = new ArrayList<Net>();

        if (statusCode == 200) {
            try {
                JSONArray netsjson = response.getJSONArray("nets");
                Log.e("AsyncNetsListFromServer",netsjson.toString());
                Net net = new Net();
                for (int i = 0; i < netsjson.length(); i++) {
                   JSONObject netjson = netsjson.getJSONObject(i);
                    net = setNetFromJson(netjson);
                    nets.add(net);
                }
                MyHelper.getInstance().saveNetsList(nets);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            catch (Exception eo){
                eo.printStackTrace();
            }
        }


    }
}
