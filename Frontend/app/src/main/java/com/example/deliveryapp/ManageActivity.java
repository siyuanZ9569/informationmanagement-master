package com.example.deliveryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.deliveryapp.App.AppController;
import com.example.deliveryapp.App.ManageAdapter;
import com.example.deliveryapp.Model.User;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ManageActivity extends AppCompatActivity {
    private int userId, userType;
    private RecyclerView manageRecycleView;
    private List<User> users = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userId = getIntent().getExtras().getInt("userId");
        userType = getIntent().getExtras().getInt("userType");

        manageRecycleView = findViewById(R.id.manage_recycleView);

        String url = getString(R.string.base_url)+"/users";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
            try {
                for (int i = 0; i < response.length(); i++) {
                    User thisUser = new User(
                            response.getJSONObject(i).getInt("id"),
                            response.getJSONObject(i).getInt("userType"),
                            response.getJSONObject(i).getString("username"),
                            response.getJSONObject(i).getString("password"),
                            response.getJSONObject(i).getString("nickname"),
                            response.getJSONObject(i).getString("companyName")
                            );
                    users.add(thisUser);
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
                    ManageAdapter adapter = new ManageAdapter(users, userId);
                    manageRecycleView.setAdapter(adapter);
                    manageRecycleView.setLayoutManager(new LinearLayoutManager(ManageActivity.this));
                }, error -> {
            Log.d("Load users failed",error.toString());
        });
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent i = new Intent(ManageActivity.this, MainActivity.class);
            i.putExtra("userId", userId);
            i.putExtra("userType", userType);
            startActivity(i);
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
