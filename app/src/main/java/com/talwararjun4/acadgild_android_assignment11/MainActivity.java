package com.talwararjun4.acadgild_android_assignment11;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.talwararjun4.acadgild_android_assignment11.ListPOJO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity{

    Map<String, String> details = null;
    CustomListPOJOAdapter customListPOJOAdapter =null;
    List<ListPOJO> listDisplay = null;
    ListView listView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        details = new HashMap<String, String>();
        details.put("Pushpa","998877542");
        details.put("Latha","9927462821");
        details.put("Arjun","9986950002");
        details.put("Kiran","9922643539");
        details.put("Arnav","9876546291");
        setListData();
        listView = (ListView) findViewById(R.id.listView1);
        Resources res = getResources();
        customListPOJOAdapter = new CustomListPOJOAdapter(this,listDisplay,res);
        listView.setAdapter(customListPOJOAdapter);

        registerForContextMenu(listView);
    }

    public void setListData(){
        listDisplay = new ArrayList<ListPOJO>();
        for(Map.Entry<String, String> entry:details.entrySet()){
            ListPOJO listPOJO = new ListPOJO();
            listPOJO.setName(entry.getKey());
            listPOJO.setPhoneNumber(entry.getValue());

            listDisplay.add(listPOJO);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select The Action");
        menu.add(0, v.getId(), 0, "Call");//groupId, itemId, order, title
        menu.add(0, v.getId(), 0, "SMS");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item){

        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position =  menuInfo.position;
        String phoneNumber =""+    listDisplay.get(position).getPhoneNumber();
        if(item.getTitle()=="Call"){

            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"+ phoneNumber));
            int permissionCheck = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CALL_PHONE);
            if(permissionCheck == PackageManager.PERMISSION_GRANTED){
                startActivity(callIntent);
            }
            else{
                askForPermission(Manifest.permission.CALL_PHONE, Integer.parseInt(phoneNumber));
            }

        }
        else if(item.getTitle()=="SMS") {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", phoneNumber, null)));
//            int permissionCheck = ContextCompat.checkSelfPermission(this,
//                    Manifest.permission.CALL_PHONE);
//            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
//                startActivity(callIntent);
//            } else {
//                Toast.makeText(this, "No permissions to send a message !", Toast.LENGTH_LONG).show();
//            }
        }
            return true;
    }
    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
            }
        } else {
            Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED){
            switch (requestCode) {
                default:
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + requestCode));
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        startActivity(callIntent);
                    }
                    break;
                    }
            Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }
}
