package com.WashZone.WashZone;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MyAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<userData> userData;

    String PNumber, USERNAME, USERID;
    int Position;

    private static String TAG = "phpexample";

    private String mJsonString, SMSText, SMSText2;

    public MyAdapter(Context context, ArrayList<userData> data) {
        mContext = context;
        userData = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return userData.size();
    }

    @Override
    public Object getItem(int position) {
        return userData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.listview_user,null);

        TextView userId = (TextView)view.findViewById(R.id.userId);
        TextView userName = (TextView)view.findViewById(R.id.userName);
        final TextView userNumber = (TextView)view.findViewById(R.id.userNumber);
        TextView userBirth = (TextView)view.findViewById(R.id.userBirth);
        TextView userCar = (TextView)view.findViewById(R.id.userCar);
        TextView userCarNumber = (TextView)view.findViewById(R.id.userCarNumber);
        TextView userEventCount = (TextView)view.findViewById(R.id.userEventCount);

        userId.setText("" + userData.get(position).getUSER_ID());
        userName.setText(userData.get(position).getUSER_NAME());
        userNumber.setText(userData.get(position).getUSER_NUMBER());
        userBirth.setText(userData.get(position).getUSER_BIRTH());
        userCar.setText(userData.get(position).getUSER_CAR());
        userCarNumber.setText(userData.get(position).getUSER_CARBUMBER());
        userEventCount.setText("" + userData.get(position).getUSER_EVENTSTACK());

        USERNAME = userData.get(position).getUSER_NAME();
        PNumber = userData.get(position).getUSER_NUMBER();
        Position = position;
        Button button = (Button) view.findViewById(R.id.SMSsend);
        button.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                USERNAME = userData.get(position).getUSER_NAME();
                PNumber = userData.get(position).getUSER_NUMBER();
                USERID = Integer.toString(userData.get(position).getUSER_ID());
                Position = position;

                Log.d("문자보내기 테스트", "" + Position);

/*
                new AlertDialog.Builder(mContext)
                        .setTitle("문자 발송하기")
                        .setMessage(PNumber + "님께 어떤 문자를 보내시겠습니까?")
                        .setIcon(android.R.drawable.ic_menu_save)
                        .setPositiveButton("생일이벤트", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // 확인시 처리 로직
                                //Toast.makeText(mContext, userNumber.getText().toString() + " 로 문자를 전송하였습니다.", Toast.LENGTH_SHORT).show();


                                GetData task = new GetData();
                                task.execute( "http://washzone.dothome.co.kr/GetSmsMent.php", "");
                            }})
                        .setNegativeButton("카드분실", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                GetlostCardMent task = new GetlostCardMent();
                                task.execute( "http://washzone.dothome.co.kr/GetLostCardSmsMent.php", "");

                            }})
                        .setNeutralButton("취소", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
*/

                final List<String> ListItems = new ArrayList<>();
                ListItems.add("생일이벤트문자보내기");
                ListItems.add("카드분실");
                ListItems.add("포인트지급완료");
                ListItems.add("이벤트사진보내기");
                ListItems.add("취소");

                final CharSequence[] items =  ListItems.toArray(new String[ ListItems.size()]);

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("문자 발송하기");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int pos) {
                        String selectedText = items[pos].toString();
                        //Toast.makeText(mContext, selectedText, Toast.LENGTH_SHORT).show();

                        if(selectedText == "생일이벤트문자보내기")
                        {
                            GetData task = new GetData();
                            task.execute( "http://washzone.dothome.co.kr/GetSmsMent.php", "");
                        }else if(selectedText == "카드분실")
                        {
                            GetlostCardMent task = new GetlostCardMent();
                            task.execute( "http://washzone.dothome.co.kr/GetLostCardSmsMent.php", "");
                        }
                        else if(selectedText == "포인트지급완료")
                        {

                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        boolean success = jsonObject.getBoolean("success");

                                        if (success) {
                                            Toast.makeText(mContext, "이벤트 포인트 지급이 완료되었습니다.", Toast.LENGTH_SHORT).show();

                                        } else {
                                            Toast.makeText(mContext, "실패했습니다.", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            };
                            //서버를 이용해서 요청을 함
                            ChangeEventStackRequest changeEventStackRequest = new ChangeEventStackRequest(USERID, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(mContext);
                            queue.add(changeEventStackRequest);

                        }
                        else if(selectedText == "이벤트사진보내기")
                        {
                            sendImage("워시존 이벤트 광고입니다.",PNumber);
                        }
                        else if(selectedText == "취소"){

                        }
                    }
                });
                builder.show();

            }
        });

        return view;
    }

    private void sendSMS(String subject,String phoneNumber, String message, int position)
    {

        /*SmsManager sms = SmsManager.getDefault();
        Log.d("smsSendTEST", phoneNumber + "  " + position);
        sms.sendTextMessage(phoneNumber, null, message, null, null);*/

        /*String strPhone = phoneNumber;
        String strMessage = message;

        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setClassName("com.android.mms", "com.android.mms.ui.ComposeMessageActivity");
        sendIntent.putExtra("address", strPhone);
        sendIntent.putExtra("sms_body", strMessage);
        sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Log.d(TAG, "sendSMS: " + mContext);
        mContext.startActivity(sendIntent);*/

        sendMMS(subject,phoneNumber, message);

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

        String recordDate = year+month+day;

        //서버를 이용해서 요청을 함
        RecordRequest recordRequest = new RecordRequest("" + userData.get(position).getUSER_ID(),userData.get(position).getUSER_NAME(),userData.get(position).getUSER_NUMBER(),userData.get(position).getUSER_BIRTH(),recordDate, responseListener);
        RequestQueue queue = Volley.newRequestQueue(mContext);
        queue.add(recordRequest);
    }

    private class ChangeEventStackRequest extends StringRequest {

        //서버 URL 설정 (PHP 파일 연동)
        final static private String URL = "http://washzone.dothome.co.kr/ChangeUserStack.php";
        private Map<String, String> map;


        public ChangeEventStackRequest(String USER_ID, Response.Listener<String> listener) {
            super(Method.POST, URL, listener, null);

            map = new HashMap<>();
            map.put("USER_ID", USER_ID);
        }


        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            return map;
        }
    }

    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(mContext,
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
                showResult(PNumber,Position);
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

    private class GetlostCardMent extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(mContext,
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
                getMent(PNumber,Position);
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

    private void showResult(String number, int position){

        String TAG_JSON="result";


        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            Log.d("데이터베이스 불러오기","" + jsonArray.length());

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String SMSMENT = item.getString("SMSMENT");

                SMSText = SMSMENT;
                SMSText = SMSText.replace("USERNAME", USERNAME);

                Log.d("TEST", SMSMENT);

                sendSMS("워시존 생일 이벤트",number, SMSText, position);
            }




        } catch (JSONException e) {


            Log.d(TAG, "showResult : ", e);
        }

    }

    private void getMent(String number, int position){

        String TAG_JSON="result";


        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            Log.d("데이터베이스 불러오기","" + jsonArray.length());

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String SMSMENT2 = item.getString("SMSMENT");

                SMSText = SMSMENT2;

                Log.d("TEST", SMSMENT2);

                sendSMS("워시존 카드 분실",number, SMSText, position);
            }




        } catch (JSONException e) {


            Log.d(TAG, "showResult : ", e);
        }

    }


    public void sendMMS(String subject,String phone, String text2) {

        Log.d(TAG, "sendMMS(Method) : " + "start");

        String Subject = subject;
        String text = text2;

        // 예시 (절대경로) : String imagePath = "/storage/emulated/0/Pictures/Screenshots/Screenshot_20190312-181007.png";
        String imagePath = "R.drawable.eventimage";

        Log.d(TAG, "subject : " + Subject);
        Log.d(TAG, "text : " + text);
        Log.d(TAG, "imagePath : " + imagePath);

        com.klinker.android.send_message.Settings settings = new com.klinker.android.send_message.Settings();
        settings.setUseSystemSending(true);

        // TODO : 이 Transaction 클래스를 위에 링크에서 다운받아서 써야함
        Transaction transaction = new Transaction(mContext, settings);

        // 제목이 있을경우
        com.klinker.android.send_message.Message message = new com.klinker.android.send_message.Message(text, phone,Subject);

        /*if(!(imagePath.equals("") || imagePath == null)) {
            // 예시2 (앱 내부 리소스) :
            Bitmap mBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.eventimage);
            //Bitmap mBitmap = BitmapFactory.decodeFile(imagePath);
            // TODO : image를 여러장 넣고 싶은경우, addImage를 계속호출해서 넣으면됨
            message.addImage(mBitmap);
            //message.setImage(mBitmap);
        }*/

        // 제목이 없을경우
        // Message message = new Message(text, number);

        long id = android.os.Process.getThreadPriority(android.os.Process.myTid());

        transaction.sendNewMessage(message, id);

    }

    public void sendImage(String subject,String phone) {

        Log.d(TAG, "sendMMS(Method) : " + "start");

        String Subject = subject;
        String text = null;

        // 예시 (절대경로) : String imagePath = "/storage/emulated/0/Pictures/Screenshots/Screenshot_20190312-181007.png";
        String imagePath = "R.drawable.eventimage";

        Log.d(TAG, "subject : " + Subject);
        Log.d(TAG, "text : " + text);
        Log.d(TAG, "imagePath : " + imagePath);

        com.klinker.android.send_message.Settings settings = new com.klinker.android.send_message.Settings();
        settings.setUseSystemSending(true);

        // TODO : 이 Transaction 클래스를 위에 링크에서 다운받아서 써야함
        Transaction transaction = new Transaction(mContext, settings);

        // 제목이 있을경우
        com.klinker.android.send_message.Message message = new com.klinker.android.send_message.Message(text, phone,Subject);

        if(!(imagePath.equals("") || imagePath == null)) {
            // 예시2 (앱 내부 리소스) :
            Bitmap mBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.eventimage);
            //Bitmap mBitmap = BitmapFactory.decodeFile(imagePath);
            // TODO : image를 여러장 넣고 싶은경우, addImage를 계속호출해서 넣으면됨
            message.addImage(mBitmap);
            //message.setImage(mBitmap);
        }

        // 제목이 없을경우
        // Message message = new Message(text, number);

        long id = android.os.Process.getThreadPriority(android.os.Process.myTid());

        transaction.sendNewMessage(message, id);

    }

}


