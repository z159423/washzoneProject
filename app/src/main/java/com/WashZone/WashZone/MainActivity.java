package com.WashZone.WashZone;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    //TextView tv_ph;
    Button btn_ph, btn_adminmenu;
    int userNUMBER;

    static final int SMS_RECEIVE_PERMISSON=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int permissonCheck= ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        int permissonCheck2= ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);

        if(permissonCheck == PackageManager.PERMISSION_GRANTED && permissonCheck2 == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(getApplicationContext(), "SMS 수신권한 있음", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(getApplicationContext(), "SMS 수신권한 없음", Toast.LENGTH_SHORT).show();

            //권한설정 dialog에서 거부를 누르면
            //ActivityCompat.shouldShowRequestPermissionRationale 메소드의 반환값이 true가 된다.
            //단, 사용자가 "Don't ask again"을 체크한 경우
            //거부하더라도 false를 반환하여, 직접 사용자가 권한을 부여하지 않는 이상, 권한을 요청할 수 없게 된다.
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)){
                //이곳에 권한이 왜 필요한지 설명하는 Toast나 dialog를 띄워준 후, 다시 권한을 요청한다.
                Toast.makeText(getApplicationContext(), "SMS권한이 필요합니다", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS}, SMS_RECEIVE_PERMISSON);
            }else{
                ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS}, SMS_RECEIVE_PERMISSON);
            }
        }

        //tv_ph = findViewById(R.id.tv_ph);
        btn_ph = findViewById(R.id.btn_pn);
        btn_adminmenu = findViewById(R.id.btn_adminmenu);

        //final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "Data-db").allowMainThreadQueries().build();

        btn_ph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                //intent.putExtra("str", userNUMBER);
                startActivity(intent);

            /*userNUMBER = Integer.parseInt(tv_ph.getText().toString());

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");
                        if (success) {
                            Toast.makeText(getApplicationContext(), "등록에 성공했습니다.", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                            intent.putExtra("str", userNUMBER);
                            startActivity(intent);

                        } else {
                            Toast.makeText(getApplicationContext(), "등록에 실패했습니다..", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            };
            //서버를 이용해서 요청을 함
            RegisterRequest registerRequest = new RegisterRequest("test", userNUMBER, responseListener);
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(registerRequest);

            //db.DataDao().insert(new Data01(str));*/

            }
        });

        btn_adminmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);

            }
        });

        /*btn_kakaotest.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.setClassName("com.android.mms", "com.android.mms.ui.ComposeMessageActivity");
                sendIntent.putExtra("address", "01041889924");
                sendIntent.putExtra("sms_body", "Test");
                sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(sendIntent);
            }
        });*/




    }

    /*public void Numbering(View v){
        switch (v.getId())
        {
            case R.id.Num0:
                tv_ph.append("0");
                break;
            case R.id.Num1:
                tv_ph.append("1");
                break;
            case R.id.Num2:
                tv_ph.append("2");
                break;
            case R.id.Num3:
                tv_ph.append("3");
                break;
            case R.id.Num4:
                tv_ph.append("4");
                break;
            case R.id.Num5:
                tv_ph.append("5");
                break;
            case R.id.Num6:
                tv_ph.append("6");
                break;
            case R.id.Num7:
                tv_ph.append("7");
                break;
            case R.id.Num8:
                tv_ph.append("8");
                break;
            case R.id.Num9:
                tv_ph.append("9");
                break;
            case R.id.Back:
                if (tv_ph.length() > 0) {
                    String temp = tv_ph.getText().toString();
                    tv_ph.setText(temp.substring(0, temp.length() - 1));
                }
                break;
        }
    }*/




}
