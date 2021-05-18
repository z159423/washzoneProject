package com.WashZone.WashZone;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    Button bt_register, bt_back;
    EditText et_name, et_number, et_birth, et_car, et_carnumber;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        bt_register = findViewById(R.id.bt_register);

        et_name = findViewById(R.id.et_name);
        et_number = findViewById(R.id.et_number);
        et_birth = findViewById(R.id.et_birth);
        et_car = findViewById(R.id.et_car);
        et_carnumber = findViewById(R.id.et_carnumber);

        InputMethodManager inm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(et_name.getText().length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "성함을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(et_number.getText().length() != 11)
                {
                    Toast.makeText(getApplicationContext(), "전화번호를 잘못 입력하셨습니다.", Toast.LENGTH_SHORT).show();
                    et_number.setText(null);
                    return;
                }

                if(et_birth.getText().length() != 8)
                {
                    Toast.makeText(getApplicationContext(), "생년월일을 잘못 입력하셨습니다.", Toast.LENGTH_SHORT).show();
                    et_birth.setText(null);
                    return;
                }

                if(et_car.getText().length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "차종을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(et_carnumber.getText().length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "차량번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success) {
                                Toast.makeText(getApplicationContext(), "등록에 성공했습니다.", Toast.LENGTH_LONG).show();

                                et_name.setText(null);
                                et_number.setText(null);
                                et_birth.setText(null);
                                et_car.setText(null);
                                et_carnumber.setText(null);

                                inm.hideSoftInputFromWindow(et_name.getWindowToken(),0);        //키보드 숨기기
                            } else {
                                Toast.makeText(getApplicationContext(), "등록에 실패했습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                //서버를 이용해서 요청을 함
                RegisterRequest registerRequest = new RegisterRequest(et_name.getText().toString(), et_number.getText().toString(),et_birth.getText().toString(),et_car.getText().toString(),et_carnumber.getText().toString(), responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);

            }

        });


    }

}
