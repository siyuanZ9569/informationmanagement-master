package com.example.deliveryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.deliveryapp.App.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    private EditText et_username, et_password, et_confirmPassword, et_nickname, et_company;
    private TextView tv_userType;
    private Button btn_signup;
    private int userId, userType, postUserType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userId = getIntent().getExtras().getInt("userId");
        userType = getIntent().getExtras().getInt("userType");

        tv_userType = findViewById(R.id.account_accountType);
        et_company = findViewById(R.id.account_accountCompany);
        et_username = findViewById(R.id.account_accountUsername);
        et_password = findViewById(R.id.account_accountPassword);
        et_confirmPassword = findViewById(R.id.account_accountConfirmPassword);
        et_nickname = findViewById(R.id.account_accountNickname);
        btn_signup = findViewById(R.id.account_signup);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    confirmSignup();
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });

        registerForContextMenu(tv_userType);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.account_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.account_admin:
                tv_userType.setText("管理员");
                postUserType =1;
                return true;

            case R.id.account_company:
                tv_userType.setText("公司主账号");
                postUserType=2;
                return true;

            case R.id.account_delivery:
                tv_userType.setText("运输员");
                postUserType=3;
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void confirmSignup() throws JSONException {
        String accountType = tv_userType.getText().toString().trim();
        String accountUsername = et_username.getText().toString().trim();
        String accountPassword = et_password.getText().toString().trim();
        String accountConfirmPassword = et_confirmPassword.getText().toString().trim();
        String accountNickname = et_nickname.getText().toString().trim();
        String accountCompany = et_company.getText().toString().trim();

        if(!accountType.isEmpty() && !accountPassword.isEmpty() && !accountConfirmPassword.isEmpty()
                && !accountUsername.isEmpty() && !accountCompany.isEmpty() && !accountNickname.isEmpty()){
            if( !accountPassword.equals(accountConfirmPassword)){
                Toast.makeText(SignupActivity.this, "密码不一致，请重新输入", Toast.LENGTH_SHORT).show();
            }
            else{
                JSONObject postUser = new JSONObject();
                postUser.put("username", accountUsername);
                postUser.put("password", accountPassword);
                postUser.put("userType", postUserType);
                postUser.put("nickname",accountNickname);
                postUser.put("companyName",accountCompany);
                postUser.put("latitude", 38.512468);
                postUser.put("longtitude",106.145248);
                final String body_content = postUser.toString();

                String url = getString(R.string.base_url)+"/users/createUser";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        response -> {
                            if(response.contains("created")) {
                                Toast.makeText(SignupActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(SignupActivity.this, MainActivity.class);
                                i.putExtra("userId", userId);
                                i.putExtra("userType", userType);
                                finish();
                                startActivity(i);
                            }else{
                                Toast.makeText(SignupActivity.this, response, Toast.LENGTH_SHORT).show();
                            }
                        }
                        , error -> {
                    Toast.makeText(SignupActivity.this, "添加失败"+new String(error.networkResponse.data), Toast.LENGTH_SHORT).show();
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
        else {
            Toast.makeText(SignupActivity.this, "请完整填写以上信息", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            Intent i = new Intent(SignupActivity.this, MainActivity.class);
            i.putExtra("userId", userId);
            i.putExtra("userType", userType);
            startActivity(i);
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }


}
