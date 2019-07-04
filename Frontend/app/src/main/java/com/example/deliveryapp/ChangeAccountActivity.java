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

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.deliveryapp.App.AppController;

import org.json.JSONException;

public class ChangeAccountActivity extends AppCompatActivity {
    private int userId, targetId, userType;
    private TextView tv_accountType, tv_username;
    private int postUserType;
    private EditText et_password, et_confirmPassword;
    private Button btn_confirm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        userId = getIntent().getExtras().getInt("userId");
        userType = getIntent().getExtras().getInt("userType");
        targetId = getIntent().getExtras().getInt("targetId");

        tv_accountType = findViewById(R.id.change_accountType);
        tv_username = findViewById(R.id.change_accountUsername);
        et_password = findViewById(R.id.change_accountPassword);
        et_confirmPassword = findViewById(R.id.change_ConfirmPassword);
        btn_confirm = findViewById(R.id.change_post);
        String url = getString(R.string.base_url) + "/users/" + targetId;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        tv_username.setText(response.getString("username"));
                        int typeInteger = response.getInt("userType");
                        if (typeInteger == 1)
                            tv_accountType.setText("管理员");
                        if (typeInteger == 2)
                            tv_accountType.setText("公司账号");
                        if (typeInteger == 3)
                            tv_accountType.setText("运输员");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            Toast.makeText(ChangeAccountActivity.this, "获取用户信息失败", Toast.LENGTH_SHORT).show();
        });
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);

        registerForContextMenu(tv_accountType);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postChange();
            }
        });

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.account_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.account_admin:
                tv_accountType.setText("管理员");
                postUserType = 1;
                return true;

            case R.id.account_company:
                tv_accountType.setText("公司主账号");
                postUserType = 2;
                return true;

            case R.id.account_delivery:
                tv_accountType.setText("运输员");
                postUserType = 3;
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void postChange() {
        String postPassword = et_password.getText().toString().trim();
        String confirmPassword = et_confirmPassword.getText().toString().trim();
        String editUserUrl = getString(R.string.base_url) + "/users/editUser?targetId=" + targetId + "&userType=" + postUserType
                + "&password=" + postPassword;
        if (postPassword.equals(confirmPassword)) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, editUserUrl,
                    response -> {
                        if (response.contains("成功")) {
                            Toast.makeText(ChangeAccountActivity.this, "更改成功", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(ChangeAccountActivity.this, MainActivity.class);
                            i.putExtra("userId", userId);
                            i.putExtra("userType", userType);
                            finish();
                            startActivity(i);
                        } else {
                            Toast.makeText(ChangeAccountActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                    }, error -> {
                Toast.makeText(ChangeAccountActivity.this, "添加失败"+new String(error.networkResponse.data), Toast.LENGTH_SHORT).show();
            });
            AppController.getInstance().addToRequestQueue(stringRequest);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent i = new Intent(ChangeAccountActivity.this, ManageActivity.class);
            i.putExtra("userId", userId);
            i.putExtra("userType", userType);
            startActivity(i);
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
