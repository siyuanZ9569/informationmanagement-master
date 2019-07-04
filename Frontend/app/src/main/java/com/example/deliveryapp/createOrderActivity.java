package com.example.deliveryapp;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.example.deliveryapp.App.AppController;
import com.example.deliveryapp.App.QRScannerOutActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class createOrderActivity extends AppCompatActivity {
    private EditText buyerId, deliveryId;
    private TextView buyerName, buyerAddr, deliveryName, deliveryComp;
    private Button orderConfirm, buyerBtn, deliveryBtn;
    private LinearLayout ll_buyName, ll_buyerAddr, ll_deliveryName, ll_deliveryComp;
    private int userId, userType, goodId, targetId;
    private static int enterCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_in_dialog);

        buyerId = findViewById(R.id.buyerId);
        buyerName = findViewById(R.id.buyerName);
        buyerAddr = findViewById(R.id.buyerAddr);
        deliveryId = findViewById(R.id.deliveryId);
        deliveryName = findViewById(R.id.deliveryName);
        deliveryComp = findViewById(R.id.deliveryComp);

        buyerBtn = findViewById(R.id.buyerBtn);
        deliveryBtn = findViewById(R.id.deliveryBtn);
        orderConfirm = findViewById(R.id.orderConfirm);

        ll_buyerAddr = findViewById(R.id.order_ll_buyerAddr);
        ll_buyName = findViewById(R.id.order_ll_buyerName);
        ll_deliveryName = findViewById(R.id.order_ll_deliveryName);
        ll_deliveryComp = findViewById(R.id.order_ll_deliveryComp);

        userId = getIntent().getExtras().getInt("userId");
        userType = getIntent().getExtras().getInt("userType");
        goodId = getIntent().getExtras().getInt("goodId");

        buyerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = Integer.valueOf(buyerId.getText().toString());
                String url = getString(R.string.base_url)+"/users/"+i;
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                        response -> {
                            try {
                                buyerName.setText(response.getString("nickname"));
                                buyerAddr.setText(response.getString("companyName"));
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                            enterCount++;
                        }, error -> {
                    Log.d("getBuyer", error.toString());
                });
                AppController.getInstance().addToRequestQueue(jsonObjectRequest);
                ll_buyerAddr.setVisibility(View.VISIBLE);
                ll_buyName.setVisibility(View.VISIBLE);
            }
        });

        deliveryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = Integer.valueOf(deliveryId.getText().toString());
                targetId = i;
                String url = getString(R.string.base_url)+"/users/"+i;
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                        response -> {
                            try {
                                deliveryName.setText(response.getString("nickname"));
                                deliveryComp.setText(response.getString("companyName"));
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                            enterCount++;
                        }, error -> {
                    Log.d("getDelivery", error.toString());
                });
                AppController.getInstance().addToRequestQueue(jsonObjectRequest);
                ll_deliveryName.setVisibility(View.VISIBLE);
                ll_deliveryComp.setVisibility(View.VISIBLE);
            }
        });

        orderConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (enterCount >= 2) {
                    try {
                        JSONObject postGood = new JSONObject();
                        postGood.put("buyerId", Integer.valueOf(buyerId.getText().toString()));
                        postGood.put("sellerId", userId);
                        postGood.put("deliverymanId", Integer.valueOf(deliveryId.getText().toString()));
                        postGood.put("status", 1);
                        postGood.put("goodId", goodId);
                        final String body_content = postGood.toString();

                        String url_create = getString(R.string.base_url) + "/orders/createOrder?id="+goodId+"&userId="+userId+"&targetId="+targetId;

                        StringRequest stringRequest_create = new StringRequest(Request.Method.POST, url_create,
                                response -> {
                                    Toast.makeText(createOrderActivity.this, "订单创建成功", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(createOrderActivity.this, MainActivity.class);
                                    i.putExtra("userId", userId);
                                    i.putExtra("userType", userType);
                                    finish();
                                    startActivity(i);
                                }
                                , error -> {
                            Toast.makeText(createOrderActivity.this, "添加订单失败", Toast.LENGTH_SHORT).show();
                        }) {
                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8";
                            }

                            @Override
                            public byte[] getBody() throws AuthFailureError {
                                try {
                                    return body_content == null ? null : body_content.getBytes("utf-8");
                                } catch (UnsupportedEncodingException uee) {
                                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", body_content, "utf-8");
                                    return null;
                                }
                            }

                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                HashMap<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-Type", "application/json; charset=utf-8");

                                return headers;
                            }
                        };
                        AppController.getInstance().addToRequestQueue(stringRequest_create);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(createOrderActivity.this, "请输入信息", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
