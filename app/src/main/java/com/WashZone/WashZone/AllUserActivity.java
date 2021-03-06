package com.WashZone.WashZone;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

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

public class AllUserActivity extends AppCompatActivity {

    private static String TAG = "phpexample";
    private String mJsonString;
    ArrayList<userData> userDataList;

    TextView tv_totaluser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alluser);

        tv_totaluser = findViewById(R.id.tv_totaluser);

        GetData task = new GetData();
        task.execute( "http://washzone.dothome.co.kr/FindAllUser.php", "");

    }

    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(AllUserActivity.this,
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

            //Log.d("?????????????????? ????????????","" + jsonArray.length());

            tv_totaluser.setText("??? ?????? ??? : " + jsonArray.length());

            for(int i=0;i<jsonArray.length();i++){
                //Log.d("?????????????????? ????????????","" + i);

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
            MyAdapter myAdapter = new MyAdapter(this,userDataList);

            userDataList = new ArrayList<userData>();

            listview.setAdapter(myAdapter);



        } catch (JSONException e) {


            Log.d(TAG, "showResult : ", e);
        }

    }
}