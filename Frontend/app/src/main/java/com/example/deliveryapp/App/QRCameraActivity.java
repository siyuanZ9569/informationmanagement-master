package com.example.deliveryapp.App;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.example.deliveryapp.createOrderActivity;
import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRCameraActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView scannerView;
    private int userId, userType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        scannerView.setAutoFocus(true);
        setContentView(scannerView);


        userId = getIntent().getExtras().getInt("userId");
        userType = getIntent().getExtras().getInt("userType");

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public void handleResult(Result rawResult) {
        String url = rawResult.getText();
        Button confirm = QRScannerOutActivity.confirm;
        ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
        tg.startTone(ToneGenerator.TONE_PROP_BEEP);
        tg.release();
//        System.out.println(url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        Toast.makeText(getApplicationContext(), "扫描成功", Toast.LENGTH_SHORT).show();
                        QRScannerOutActivity.tv_scanning_goodWeight.setText(String.valueOf(response.getDouble("weight")));
                        QRScannerOutActivity.tv_scanning_goodType.setText(response.getString("type"));
                        QRScannerOutActivity.tv_scanning_goodOrigin.setText(response.getString("origin"));
                        QRScannerOutActivity.tv_scanning_goodId.setText(String.valueOf(response.getInt("id")));
                        int goodId = response.getInt("id");

                        if (response.getInt("userId") == userId) {
                            confirm.setText("确认出库");
                            confirm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getApplicationContext(), createOrderActivity.class);
                                    intent.putExtra("userId", userId);
                                    intent.putExtra("userType", userType);
                                    intent.putExtra("goodId", goodId);
                                    finish();
                                    startActivity(intent);
                                }
                            });
                        } else {
                            confirm.setText("确认入库");
                            confirm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {


                                    String url = getString(R.string.base_url) + "/orders/receiveOrder?buyerId="+userId+"&goodId="+goodId;

                                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                            response -> {
                                                Toast.makeText(getApplicationContext(), "添加成功", Toast.LENGTH_SHORT).show();
                                                Intent i = new Intent(getApplicationContext(), QRScannerOutActivity.class);
                                                QRScannerOutActivity.ll_scanning_goodInfo.setVisibility(View.INVISIBLE);
                                                i.putExtra("userId", userId);
                                                i.putExtra("userType", userType);
                                                finish();
                                                startActivity(i);
                                            }
                                            , error -> {
                                        Log.d("入库失败", error.toString());
                                        Toast.makeText(getApplicationContext(), "添加失败", Toast.LENGTH_SHORT).show();
                                    });
                                    AppController.getInstance().addToRequestQueue(stringRequest);
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            Toast.makeText(getApplicationContext(), "扫描失败！", Toast.LENGTH_SHORT).show();
            System.out.println(error);
        });
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
        QRScannerOutActivity.ll_scanning_goodInfo.setVisibility(View.VISIBLE);

        onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();

        scannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();

        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent i = new Intent(QRCameraActivity.this, QRScannerOutActivity.class);
            i.putExtra("userId", userId);
            i.putExtra("userType", userType);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}
