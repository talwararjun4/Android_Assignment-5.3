package com.talwararjun4.acadgild_android_assignment11;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by artalwar on 12/16/2016.
 */

public class CustomListPOJOAdapter extends BaseAdapter {
    private Activity activity;
    private List data;
    private static LayoutInflater inflater=null;
    public Resources res;
    ListPOJO tempValues=null;
    int i=0;

    /*************  CustomAdapter Constructor *****************/
    public CustomListPOJOAdapter(Activity a, List d, Resources resLocal) {

        activity = a;
        data=d;
        res = resLocal;

       inflater = ( LayoutInflater )activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        if(data.size()<1){
            return 1;
        }
        else{
            return data.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder{

        public TextView name;
        public TextView phoneNumber;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder =null;
        View view = convertView;

        if(convertView == null){
            view = inflater.inflate(R.layout.custom_list_view,null);
            viewHolder = new ViewHolder();
            viewHolder.name =(TextView) view.findViewById(R.id.textV1);
            viewHolder.phoneNumber = (TextView)view.findViewById(R.id.textV2);
            view.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)view.getTag();
        }
        if(data.size()<0){
            viewHolder.name.setText("No Data!");
        }
        else{
            tempValues = null;
            tempValues = (ListPOJO)data.get(position);
            viewHolder.name.setText(tempValues.getName());
            viewHolder.phoneNumber.setText(tempValues.getPhoneNumber());

        }
        return view;

    }
}
