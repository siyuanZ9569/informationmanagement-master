package com.example.deliveryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.deliveryapp.App.AppController;
import com.example.deliveryapp.App.OrderActivityAdapter;
import com.example.deliveryapp.Model.Order;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {
    private TextView buyOrSell, target, status;
    private Button checkPoistion;
    private RecyclerView recyclerView;
    private int userId, userType, responseSize, deliverymanId;
    private List<Order> orderList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userId = getIntent().getExtras().getInt("userId");
        userType = getIntent().getExtras().getInt("userType");

        buyOrSell = findViewById(R.id.order_buyOrSell);
        target = findViewById(R.id.order_target);
        status = findViewById(R.id.order_status);

        checkPoistion = findViewById(R.id.order_checkPosition);
        recyclerView = findViewById(R.id.order_recycleView);

        String url = getString(R.string.base_url)+"/orders/findAllMyOrders?userId="+userId+"&userType="+userType;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
            responseSize = response.length();
            try {
                for (int i = 0; i < responseSize; i++) {
                    Order order = new Order(
                            response.getJSONObject(i).getInt("id"),
                            response.getJSONObject(i).getInt("buyerId"),
                            response.getJSONObject(i).getInt("deliverymanId"),
                            response.getJSONObject(i).getInt("sellerId"),
                            response.getJSONObject(i).getInt("status"),
                            response.getJSONObject(i).getString("position")
                            );
                    orderList.add(order);
                    deliverymanId = response.getJSONObject(i).getInt("deliverymanId");
                }
            }catch (JSONException e){
                e.printStackTrace();
            }

            OrderActivityAdapter orderActivityAdapter = new OrderActivityAdapter(orderList, userId, userType, deliverymanId);
            recyclerView.setAdapter(orderActivityAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(OrderActivity.this));
                }, error ->{
            Log.d("show orders", error.toString());
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            Intent i = new Intent(OrderActivity.this, MainActivity.class);
            i.putExtra("userId", userId);
            i.putExtra("userType", userType);
            startActivity(i);
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
