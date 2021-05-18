package com.WashZone.WashZone;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AutoMMS extends Service {

    private static String TAG = "phpexample";
    private String mJsonString, mJsonString2;
    Context mContext;
    ArrayList<userData> userDataList;
    public String USERDATA, MMSString, USERNAME;

    private SharedPreferences appData;


    Date currentTime = Calendar.getInstance().getTime();
    SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
    SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());

    String month = monthFormat.format(currentTime);
    String day = dayFormat.format(currentTime);

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mContext = getApplicationContext();
        appData = getSharedPreferences("appData", MODE_PRIVATE);

        Calendar nextNotifyTime = Calendar.getInstance();

        nextNotifyTime.add(Calendar.DATE, 1);

        diaryNotification(nextNotifyTime);

        GetSMS smstask = new GetSMS();
        smstask.execute("http://washzone.dothome.co.kr/GetSmsMent.php", "");

        //Toast.makeText(, "AutoMMS: 자동 문자 보내기 : ", Toast.LENGTH_SHORT).show();


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //progressDialog = ProgressDialog.show(mContext,
            //        "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //progressDialog.dismiss();
            //mTextViewResult.setText(result);
            Log.d(TAG, "response - " + result);
            USERDATA = result;
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

    private class GetSMS extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //progressDialog = ProgressDialog.show(mContext,
             //       "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //progressDialog.dismiss();
            //mTextViewResult.setText(result);
            Log.d(TAG, "response - " + result);

            if (result == null){

                //mTextViewResult.setText(errorString);
            }
            else {

                mJsonString2 = result;
                getString(USERDATA);
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

    private void getString(String MMSString){

        String TAG_JSON="result";


        try {
            JSONObject jsonObject = new JSONObject(mJsonString2);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            Log.d("데이터베이스 불러오기","" + jsonArray.length());

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String SMSMENT = item.getString("SMSMENT");

                this.MMSString = SMSMENT;


                Log.d("MMS MENT : ", SMSMENT);

            }

            GetData task = new GetData();
            task.execute( "http://washzone.dothome.co.kr/getuserbybirth.php?Date="+month+"-"+day, "");


        } catch (JSONException e) {


            Log.d(TAG, "showResult : ", e);
        }

    }

    private void showResult(){

        String TAG_JSON="result";


        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            Log.d("데이터베이스 불러오기","" + jsonArray.length());

            userDataList = new ArrayList<userData>();

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
            Log.d(TAG, "onStartCommand: " + month + " " + day + " " + userDataList.size());



            if(appData.getBoolean("Auto_CheckBox", false)) {
                for (int i = 0; i < userDataList.size(); i++) {
                    USERNAME = userDataList.get(i).getUSER_NAME();
                    MMSString = MMSString.replace("USERNAME", USERNAME);
                    sendMMS(userDataList.get(i).getUSER_NUMBER(), MMSString);

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");

                                if (success) {
                                    Toast.makeText(mContext, "문자 발송 기록이 등록되었습니다", Toast.LENGTH_SHORT).show();


                                } else {
                                    Toast.makeText(mContext, "문자 발송 기록이 등록에 실패했습니다.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    };
                    Date currentTime = Calendar.getInstance().getTime();
                    SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
                    SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
                    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());

                    String month = monthFormat.format(currentTime);
                    String day = dayFormat.format(currentTime);
                    String year = yearFormat.format(currentTime);

                    String recordDate = year + month + day;

                    //서버를 이용해서 요청을 함
                    RecordRequest recordRequest = new RecordRequest("" + userDataList.get(i).getUSER_ID(), userDataList.get(i).getUSER_NAME(), userDataList.get(i).getUSER_NUMBER(), userDataList.get(i).getUSER_BIRTH(), recordDate, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(mContext);
                    queue.add(recordRequest);

                    MMSString = MMSString.replace(USERNAME, "USERNAME");

                }
            }
            userDataList = new ArrayList<userData>();



        } catch (JSONException e) {


            Log.d(TAG, "showResult : ", e);
        }

    }



    public void sendMMS(String phone, String text2) {

        Log.d(TAG, "sendMMS(Method) : " + "start");

        String subject = "워시존 생일 이벤트";
        String text = text2;

        // 예시 (절대경로) : String imagePath = "/storage/emulated/0/Pictures/Screenshots/Screenshot_20190312-181007.png";
        String imagePath = "R.drawable.eventimage";

        Log.d(TAG, "subject : " + subject);
        Log.d(TAG, "text : " + text);
        Log.d(TAG, "imagePath : " + imagePath);


        com.klinker.android.send_message.Settings settings = new com.klinker.android.send_message.Settings();
        settings.setUseSystemSending(true);

        // TODO : 이 Transaction 클래스를 위에 링크에서 다운받아서 써야함
        Transaction transaction = new Transaction(mContext, settings);

        // 제목이 있을경우
        com.klinker.android.send_message.Message message = new com.klinker.android.send_message.Message(text, phone,subject);


        // 제목이 없을경우
        // Message message = new Message(text, number);

        /*if(!(imagePath.equals("") || imagePath == null)) {
            // 예시2 (앱 내부 리소스) :
            //Bitmap mBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.eventimage);
            Bitmap mBitmap = BitmapFactory.decodeFile(imagePath);
            // TODO : image를 여러장 넣고 싶은경우, addImage를 계속호출해서 넣으면됨
            message.addImage(mBitmap);
            //message.setImage(mBitmap);
        }*/

        long id = android.os.Process.getThreadPriority(android.os.Process.myTid());

        transaction.sendNewMessage(message, id);



    }

    public void diaryNotification(Calendar calendar)
    {
//        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
//        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
//        Boolean dailyNotify = sharedPref.getBoolean(SettingsActivity.KEY_PREF_DAILY_NOTIFICATION, true);
        Boolean dailyNotify = true; // 무조건 알람을 사용

        PackageManager pm = this.getPackageManager();
        ComponentName receiver = new ComponentName(this, DeviceBootReceiver.class);
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);


        // 사용자가 매일 알람을 허용했다면
        if (dailyNotify) {


            if (alarmManager != null) {

                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, pendingIntent);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                }
            }

            // 부팅 후 실행되는 리시버 사용가능하게 설정
            pm.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP);

        }
//        else { //Disable Daily Notifications
//            if (PendingIntent.getBroadcast(this, 0, alarmIntent, 0) != null && alarmManager != null) {
//                alarmManager.cancel(pendingIntent);
//                //Toast.makeText(this,"Notifications were disabled",Toast.LENGTH_SHORT).show();
//            }
//            pm.setComponentEnabledSetting(receiver,
//                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
//                    PackageManager.DONT_KILL_APP);
//        }
    }
}
