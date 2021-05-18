package com.WashZone.WashZone;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LostCardSmsRequest extends StringRequest {

    final static private String URL = "http://washzone.dothome.co.kr/SendLostSmsMent.php";
    private Map<String, String> map;

    public LostCardSmsRequest(String smsment, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("SMSMENT",smsment + "");
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}