package com.example.deliveryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.deliveryapp.App.AppController;
import com.example.deliveryapp.Model.User;

import org.json.JSONException;

public class LoginActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private double latitude, longtitude;
    private Button loginButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username=findViewById(R.id.login_username);
        password=findViewById(R.id.login_password);
        loginButton=findViewById(R.id.login_login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryLogin();
            }
        });


    }

    private void tryLogin(){
        String entered_username = username.getText().toString().trim();
        String entered_password = password.getText().toString().trim();
        String url = getString(R.string.base_url)+"/users/login?username="+entered_username+"&password="+entered_password;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                response ->{
            try {
                int userId = response.getInt("id");
                String response_username = response.getString("username");
                String response_company = response.getString("companyName");
                int userType = response.getInt("userType");
                latitude = response.getDouble("latitude");
                longtitude = response.getDouble("longtitude");
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                i.putExtra("userId", userId);
                i.putExtra("username", entered_username);
                i.putExtra("password", entered_password);
                i.putExtra("company", response_company);
                i.putExtra("userType", userType);
                i.putExtra("latitude",latitude);
                i.putExtra("longtitude", longtitude);
                startActivity(i);
                Toast.makeText(LoginActivity.this, "Welcome, "+response_username, Toast.LENGTH_SHORT).show();
            }
            catch (JSONException e){
                e.printStackTrace();
            }

            }, error -> {
            Log.d("Login Failed","Error: " + error
                    );
            Toast.makeText(LoginActivity.this, new String(error.networkResponse.data), Toast.LENGTH_LONG).show();
        });

        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }
}
