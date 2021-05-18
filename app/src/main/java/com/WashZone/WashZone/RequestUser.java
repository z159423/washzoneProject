package com.WashZone.WashZone;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RequestUser extends AppCompatActivity {

    final static private String URL = "http://washzone.dothome.co.kr/Register.php";

    Button btn_requestuser;
    TextView tv_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_adminmenu);

        //btn_requestuser = findViewById(R.id.bt_request);
        //tv_check = findViewById(R.id.tv_requestuser);

        /*btn_requestuser.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                RequestQueue queue = Volley.newRequestQueue(this);
                // Request를 요청 할 URL
                String url ="http://api.androidhive.info/volley/person_object.json";

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String id = response.getString("name");
                                    String recordDate = response.getString("email");
                                    JSONObject distance = response.getJSONObject("phone");
                                }catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        tv_check.setText("That didn't work!");
                    }
                });
                // queue에 Request를 추가해준다.
                queue.add(jsonObjectRequest);

            }
        });*/

    }
}
