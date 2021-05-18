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

public class SMSRecordActivity extends AppCompatActivity {

    private static String TAG = "phpexample";
    private String mJsonString;
    ArrayList<recordData> recordDataList;

    EditText et_rdid,et_rdname,et_rdnumber,et_rdbirth, et_rdsms;
    Button btn_rdfind;

    String ID,NAME,NUMBER,BIRTH,SMSDATE, AND1, AND2, AND3, AND4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        GetData task = new GetData();
        task.execute( "http://washzone.dothome.co.kr/FindAllRecord.php", "");

        et_rdid = findViewById(R.id.find_rdid);
        et_rdname = findViewById(R.id.find_rdname);
        et_rdnumber = findViewById(R.id.find_rdnumber);
        et_rdbirth = findViewById(R.id.find_rdbirth);
        et_rdsms = findViewById(R.id.find_rdsms);

        btn_rdfind = findViewById(R.id.btn_rdfind);

        btn_rdfind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(et_rdid.getText().length() == 0)
                {
                    ID = "";
                } else
                {
                    ID = "ID=" + et_rdid.getText();
                }

                if(et_rdname.getText().length() == 0)
                {
                    AND1 = "";
                } else
                {
                    AND1 = "&";
                }

                if(et_rdname.getText().length() == 0)
                {
                    NAME = "";
                } else
                {
                    NAME = "NAME=" + et_rdname.getText();
                }

                if(et_rdnumber.getText().length() == 0)
                {
                    AND2 = "";
                } else
                {
                    AND2 = "&";
                }

                if(et_rdnumber.getText().length() == 0)
                {
                    NUMBER = "";
                } else
                {
                    NUMBER = "NUMBER=" + et_rdnumber.getText();
                }
                if(et_rdbirth.getText().length() == 0)
                {
                    AND3 = "";
                } else
                {
                    AND3 = "&";
                }

                if(et_rdbirth.getText().length() == 0)
                {
                    BIRTH = "";
                } else
                {
                    BIRTH = "BIRTH=" + et_rdbirth.getText();
                }

                if(et_rdsms.getText().length() == 0)
                {
                    AND4 = "";
                } else
                {
                    AND4 = "&";
                }

                if(et_rdsms.getText().length() == 0)
                {
                    SMSDATE = "";
                } else
                {
                    SMSDATE = "RDATE=" + et_rdsms.getText();
                }

            FindRecord task2 = new FindRecord();
            task2.execute( "http://washzone.dothome.co.kr/FindRecord.php?" + ID + AND1 + NAME + AND2 + NUMBER + AND3 + BIRTH  + AND4 + SMSDATE, "");
            }
        });

    }

    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(SMSRecordActivity.this,
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

            recordDataList = new ArrayList<recordData>();

            Log.d("데이터베이스 불러오기","" + jsonArray.length());

            for(int i=0;i<jsonArray.length();i++){


                JSONObject item = jsonArray.getJSONObject(i);

                String USER_ID = item.getString("USER_ID");
                String USER_NAME = item.getString("USER_NAME");
                String USER_NUMBER = item.getString("USER_NUMBER");
                String USER_BIRTH = item.getString("USER_BIRTH");
                String USER_SMSRECORD = item.getString("RECORD_DATE");

                Log.d("TESTTEST",USER_ID + USER_NAME + USER_NUMBER + USER_BIRTH + USER_SMSRECORD);

                recordDataList.add(new recordData(Integer.parseInt(USER_ID),USER_NAME,USER_NUMBER, USER_BIRTH,USER_SMSRECORD));

            }

            ListView listview = (ListView)findViewById(R.id.record_listview);
            final RecordAdapter myAdapter = new RecordAdapter(this, recordDataList);

            recordDataList = new ArrayList<recordData>();

            listview.setAdapter(myAdapter);



        } catch (JSONException e) {


            Log.d(TAG, "showResult : ", e);
        }

    }

    private class FindRecord extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(SMSRecordActivity.this,
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
                showRecord();
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

    private void showRecord(){

        String TAG_JSON="result";


        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            recordDataList = new ArrayList<recordData>();

            Log.d("데이터베이스 불러오기","" + jsonArray.length());

            for(int i=0;i<jsonArray.length();i++){


                JSONObject item = jsonArray.getJSONObject(i);

                String USER_ID = item.getString("USER_ID");
                String USER_NAME = item.getString("USER_NAME");
                String USER_NUMBER = item.getString("USER_NUMBER");
                String USER_BIRTH = item.getString("USER_BIRTH");
                String USER_SMSRECORD = item.getString("RECORD_DATE");

                Log.d("TESTTEST",USER_ID + USER_NAME + USER_NUMBER + USER_BIRTH + USER_SMSRECORD);

                recordDataList.add(new recordData(Integer.parseInt(USER_ID),USER_NAME,USER_NUMBER, USER_BIRTH,USER_SMSRECORD));

            }

            ListView listview = (ListView)findViewById(R.id.record_listview);
            final RecordAdapter myAdapter = new RecordAdapter(this, recordDataList);

            recordDataList = new ArrayList<recordData>();

            listview.setAdapter(myAdapter);



        } catch (JSONException e) {


            Log.d(TAG, "showResult : ", e);
        }

    }
}
