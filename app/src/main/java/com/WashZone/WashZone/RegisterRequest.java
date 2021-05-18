package com.WashZone.WashZone;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    //서버 URL 설정 (PHP 파일 연동)
     final static private String URL = "http://washzone.dothome.co.kr/Register.php";
     private Map<String, String> map;


     public RegisterRequest(String USER_NAME, String USER_NUMBER, String USER_BIRTH, String USER_CAR, String USER_CARNUMBER, Response.Listener<String> listener) {
         super(Method.POST, URL, listener, null);

         map = new HashMap<>();
         map.put("USER_NAME", USER_NAME);
         map.put("USER_NUMBER",USER_NUMBER + "");
         map.put("USER_BIRTH",USER_BIRTH);
         map.put("USER_CAR",USER_CAR);
         map.put("USER_CARNUMBER",USER_CARNUMBER + "");
     }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
