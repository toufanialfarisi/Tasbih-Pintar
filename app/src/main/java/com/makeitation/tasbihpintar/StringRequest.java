package com.makeitation.tasbihpintar;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import java.net.HttpURLConnection;
import androidx.annotation.Nullable;

public class StringRequest extends com.android.volley.toolbox.StringRequest {
    public StringRequest(String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    @Override
    public void deliverError(VolleyError error) {
        if (error instanceof NoConnectionError) {
            Cache.Entry entry = this.getCacheEntry();
            if(entry != null) {
                Response<String> response = parseNetworkResponse(new NetworkResponse(HttpURLConnection.HTTP_OK,
                        entry.data, false, 0, entry.allResponseHeaders));
                deliverResponse(response.result);
                return;
            }
        }
        super.deliverError(error);
    }
    }
