package com.example.deliveryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.deliveryapp.App.AppController;
import com.example.deliveryapp.App.StorageActivityAdapter;
import com.example.deliveryapp.App.StorageActivityPopup;
import com.example.deliveryapp.Model.Good;
import com.example.deliveryapp.Model.User;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class StorageActivity extends AppCompatActivity {
    private TextView type, weight, origin;
    private RecyclerView recyclerView;
    private Button moreInfo, btn_addGood;
    private int userId, userType, responseSize;
    private List<Good> goodList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        type=findViewById(R.id.storage_type);
        weight = findViewById(R.id.storage_weight);
        origin = findViewById(R.id.storage_orgin);
        moreInfo=findViewById(R.id.storage_button);
        btn_addGood = findViewById(R.id.storage_add);
        recyclerView = findViewById(R.id.storage_recycleView);
        userId = getIntent().getExtras().getInt("userId");
        userType = getIntent().getExtras().getInt("userType");

        String url = getString(R.string.base_url)+"/storage/getByUserId?userId="+userId;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
            responseSize = response.length();
            try {
                for (int i = 0; i < responseSize; i++) {
                    String goodType = response.getJSONObject(i).getString("type");
                    int goodId = response.getJSONObject(i).getInt("id");
                    String goodOrigin = response.getJSONObject(i).getString("origin");
                    String goodDescription = response.getJSONObject(i).getString("description");
                    double goodWeight = response.getJSONObject(i).getDouble("weight");
                    Good tempGood = new Good(goodId, userId, goodOrigin, goodDescription, goodType, goodWeight);
                    goodList.add(tempGood);
//                    System.out.println(goodList.toString());
                }
            }
            catch (JSONException e){
                e.printStackTrace();
            }
//            System.out.println("storageMain"+goodList.size());
            StorageActivityAdapter adapter = new StorageActivityAdapter(goodList);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(StorageActivity.this));


                }, error -> {
            Log.d("goStorage Failed",error.toString());
        });
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            Intent i = new Intent(StorageActivity.this, MainActivity.class);
            i.putExtra("userId", userId);
            i.putExtra("userType", userType);
            startActivity(i);
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void storage_addGood(View view){
        Intent i = new Intent(StorageActivity.this, StorageActivityPopup.class);
        i.putExtra("userId", userId);
        i.putExtra("userType", userType);
        startActivity(i);
    }
}
