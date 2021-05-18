package com.WashZone.WashZone;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RecordRequest extends StringRequest {

    final static private String URL = "http://washzone.dothome.co.kr/AddSMSRecord.php";
    private Map<String, String> map;

    public RecordRequest(String USER_ID,String USER_NAME, String USER_NUMBER, String USER_BIRTH, String RECORD_DATE, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("USER_ID", USER_ID);
        map.put("USER_NAME", USER_NAME);
        map.put("USER_NUMBER",USER_NUMBER + "");
        map.put("USER_BIRTH",USER_BIRTH);
        map.put("RECORD_DATE",RECORD_DATE);

        Log.d("smsrecordtest", USER_ID + USER_NAME + USER_NUMBER + USER_BIRTH + RECORD_DATE);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
