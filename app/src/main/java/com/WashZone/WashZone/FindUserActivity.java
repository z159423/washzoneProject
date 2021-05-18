package com.WashZone.WashZone;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FindUserActivity extends AppCompatActivity {

    private static String TAG = "phpexample";
    private String mJsonString;
    ArrayList<userData> userDataList;

    EditText et_id,et_name,et_number,et_birth, et_carnumber;
    Button btn_find;

    String ID,NAME,NUMBER,BIRTH,CARNUMBER,SMSDATE, AND1, AND2, AND3, AND4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finduser);

        et_id = findViewById(R.id.find_id);
        et_name = findViewById(R.id.find_name);
        et_number = findViewById(R.id.find_number);
        et_birth = findViewById(R.id.find_birth);
        et_carnumber = findViewById(R.id.find_carnumber);

        btn_find = findViewById(R.id.btn_find);

        GetData task = new GetData();
        task.execute( "http://washzone.dothome.co.kr/FindAllUser.php", "");

        btn_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(et_id.getText().length() == 0)
                {
                    ID = "";
                } else
                {
                    ID = "ID=" + et_id.getText();
                }

                if(et_name.getText().length() == 0)
                {
                    AND1 = "";
                } else
                {
                    AND1 = "&";
                }

                if(et_name.getText().length() == 0)
                {
                    NAME = "";
                } else
                {
                    NAME = "NAME=" + et_name.getText();
                }

                if(et_number.getText().length() == 0)
                {
                    AND2 = "";
                } else
                {
                    AND2 = "&";
                }

                if(et_number.getText().length() == 0)
                {
                    NUMBER = "";
                } else
                {
                    NUMBER = "NUMBER=" + et_number.getText();
                }

                if(et_birth.getText().length() == 0)
                {
                    AND3 = "";
                } else
                {
                    AND3 = "&";
                }

                if(et_birth.getText().length() == 0)
                {
                    BIRTH = "";
                } else
                {
                    BIRTH = "BIRTH=" + et_birth.getText();
                }

                if(et_carnumber.getText().length() == 0)
                {
                    AND4 = "";
                } else
                {
                    AND4 = "&";
                }

                if(et_carnumber.getText().length() == 0)
                {
                    CARNUMBER = "";
                } else
                {
                    CARNUMBER = "CARNUMBER=" + et_carnumber.getText();
                }


                GetData task = new GetData();
                task.execute( "http://washzone.dothome.co.kr/FindUser.php?" + ID + AND1 + NAME + AND2 + NUMBER + AND3 + BIRTH + AND4 + CARNUMBER, "");

                Log.d("FindUserTest : ", "http://washzone.dothome.co.kr/FindUser.php?" + ID + AND1 + NAME + AND2 + NUMBER + AND3 + BIRTH + AND4 + CARNUMBER);

            }
        });

    }

    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(FindUserActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            //mTextViewResult.setText(result);
            Log.d(TAG, "response - " + result);

            if (result == null){

                //mTextViewResult.setText(errorString);
            }
            else {

                mJsonString = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String postParameters = params[1];


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "GetData : Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }

    private void showResult(){

        String TAG_JSON="result";


        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            userDataList = new ArrayList<userData>();

            //Log.d("데이터베이스 불러오기","" + jsonArray.length());

            for(int i=0;i<jsonArray.length();i++){
                //Log.d("데이터베이스 불러오기","" + i);

                JSONObject item = jsonArray.getJSONObject(i);

                String USER_ID = item.getString("USER_ID");
                String USER_NAME = item.getString("USER_NAME");
                String USER_NUMBER = item.getString("USER_NUMBER");
                String USER_BIRTH = item.getString("USER_BIRTH");
                String USER_CAR = item.getString("USER_CAR");
                String USER_CARNUMBER = item.getString("USER_CARNUMBER");
                String USER_EVENTSTACK = item.getString("USER_EVENTSTACK");

                userDataList.add(new userData(Integer.parseInt(USER_ID),USER_NAME,USER_NUMBER, USER_BIRTH,USER_CAR,USER_CARNUMBER,Integer.parseInt(USER_EVENTSTACK)));

            }

            ListView listview = (ListView)findViewById(R.id.listView);
            final MyAdapter myAdapter = new MyAdapter(this,userDataList);

            userDataList = new ArrayList<userData>();

            listview.setAdapter(myAdapter);


        } catch (JSONException e) {


            Log.d(TAG, "showResult : ", e);
        }

    }
}
