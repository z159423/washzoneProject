package com.WashZone.WashZone;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SqlQueryActivity extends AppCompatActivity {

    EditText et_sqlquery;
    Button btn_sqlquery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlquery);

        et_sqlquery = findViewById(R.id.et_sqlquery);
        btn_sqlquery = findViewById(R.id.btn_sqlquery);

        btn_sqlquery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success) {
                                Toast.makeText(getApplicationContext(), "SQL QUERY를 적용하였습니다.", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(), "SQL QUERY 적용을 실패했습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                //서버를 이용해서 요청을 함
                SqlRequest registerRequest = new SqlRequest(et_sqlquery.getText().toString(), responseListener);
                RequestQueue queue = Volley.newRequestQueue(SqlQueryActivity.this);
                queue.add(registerRequest);


            }
        });

    }

    class SqlRequest extends StringRequest {

        //서버 URL 설정 (PHP 파일 연동)
        final static private String URL = "http://washzone.dothome.co.kr/SQLQuery.php";
        private Map<String, String> map;


        public SqlRequest(String QUERY, Response.Listener<String> listener) {
            super(Method.POST, URL, listener, null);

            map = new HashMap<>();
            map.put("QUERY",QUERY + "");
        }


        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            return map;
        }
    }
}


