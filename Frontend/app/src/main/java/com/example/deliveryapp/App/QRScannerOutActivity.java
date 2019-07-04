package com.example.deliveryapp.App;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.deliveryapp.MainActivity;
import com.example.deliveryapp.R;
import com.example.deliveryapp.StorageActivity;
import com.example.deliveryapp.StorageInOutActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class QRScannerOutActivity extends AppCompatActivity{
    protected static LinearLayout ll_scanning_goodInfo;
    protected static TextView tv_scanning_goodId, tv_scanning_goodType, tv_scanning_goodWeight, tv_scanning_goodOrigin;
    protected static Button confirm;
    protected static Context mContext;
    private int userId, userType;
    private EditText buyerId, deliveryId;
    private TextView buyerName, buyerAddr, deliveryName, deliveryComp;
    private Button orderConfirm, buyerBtn, deliveryBtn;
    private static int enterCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanning);
        mContext = QRScannerOutActivity.this;

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userId = getIntent().getExtras().getInt("userId");
        userType = getIntent().getExtras().getInt("userType");

        ll_scanning_goodInfo = findViewById(R.id.scanningOut_goodInformation);

        tv_scanning_goodId = findViewById(R.id.scanningOut_good_id);
        tv_scanning_goodOrigin = findViewById(R.id.scanningOut_good_origin);
        tv_scanning_goodType = findViewById(R.id.scanningOut_good_type);
        tv_scanning_goodWeight = findViewById(R.id.scanningOut_good_weight);
        confirm = findViewById(R.id.scanningOut_confirm);




    }

    public void startCamera(View view){
        Intent intent = new Intent(getApplicationContext(), QRCameraActivity.class);
        intent.putExtra("userId", userId);
        intent.putExtra("userType", userType);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            Intent i = new Intent(QRScannerOutActivity.this, MainActivity.class);
            i.putExtra("userId", userId);
            i.putExtra("userType", userType);
            startActivity(i);
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}