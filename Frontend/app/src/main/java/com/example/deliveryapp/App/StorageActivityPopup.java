package com.example.deliveryapp.App;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.deliveryapp.R;
import com.example.deliveryapp.StorageActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class StorageActivityPopup extends Activity {

    private EditText popAdd_type, popAdd_weight, popAdd_origin, popAdd_description;
    private int userId, userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_popup);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        userId = getIntent().getExtras().getInt("userId");

        popAdd_description = findViewById(R.id.storage_pop_description);
        popAdd_origin= findViewById(R.id.storage_pop_origin);
        popAdd_type = findViewById(R.id.storage_pop_type);
        popAdd_weight= findViewById(R.id.storage_pop_weight);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout( (int)(width*0.7), (int)(height*0.3) ) ;
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setElevation(15f);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity= Gravity.CENTER;
        params.x =0;
        params.y = -20;

        getWindow().setAttributes(params);


    }

    public void popCancel(View view){
        finish();
    }

    public void confirmAdd(View view) throws JSONException {
        String post_type = popAdd_type.getText().toString().trim();
        double post_weight = Double.valueOf(popAdd_weight.getText().toString().trim());
        String post_description = popAdd_description.getText().toString().trim();
        String post_orgin = popAdd_origin.getText().toString().trim();

        JSONObject postGood = new JSONObject();
        postGood.put("type", post_type);
        postGood.put("weight", post_weight);
        postGood.put("origin", post_orgin);
        postGood.put("userId", userId);
        final String body_content = postGood.toString();



        if(!post_type.isEmpty() && post_weight != 0 && !post_orgin.isEmpty()){
            String url = getString(R.string.base_url)+"/storage/addGood";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    response -> {
                        Toast.makeText(StorageActivityPopup.this, "添加成功",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(StorageActivityPopup.this, StorageActivity.class);
                        i.putExtra("userId", userId);
                        i.putExtra("userType", userType);
                        finish();
                        startActivity(i);
                    }
                    , error -> {
                        Toast.makeText(StorageActivityPopup.this, "添加失败", Toast.LENGTH_SHORT).show();
            }){
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
            AppController.getInstance().addToRequestQueue(stringRequest);

        }
    }
}
