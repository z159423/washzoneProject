package com.WashZone.WashZone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RecordAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<recordData> recordData;


    public RecordAdapter(Context context, ArrayList<recordData> data) {
        mContext = context;
        recordData = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return recordData.size();
    }

    @Override
    public Object getItem(int position) {
        return recordData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.listview_record,null);

        TextView userId = (TextView)view.findViewById(R.id.rd_userId);
        TextView userName = (TextView)view.findViewById(R.id.rd_userName);
        final TextView userNumber = (TextView)view.findViewById(R.id.rd_userNumber);
        TextView userBirth = (TextView)view.findViewById(R.id.rd_userBirth);
        TextView smsRecord = (TextView)view.findViewById(R.id.rd_smssendRecord);

        userId.setText("" + recordData.get(position).getUSER_ID());
        userName.setText(recordData.get(position).getUSER_NAME());
        userNumber.setText(recordData.get(position).getUSER_NUMBER());
        userBirth.setText(recordData.get(position).getUSER_BIRTH());
        smsRecord.setText(recordData.get(position).getUSER_SMSRECORD());

        return view;
    }
}
