package com.example.deliveryapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.deliveryapp.App.AppController;
import com.example.deliveryapp.App.QRScannerOutActivity;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private double latitude, longtitude;
    private TextView accountID, accountUsername, accountPass, accountComp;
    protected static int userId, userType;
    private String getUsername, getPass, getComp;
    private static final int REQUEST_CALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userId = getIntent().getExtras().getInt("userId");
        userType = getIntent().getExtras().getInt("userType");


        accountID = findViewById(R.id.account_ID);
        accountUsername = findViewById(R.id.account_username);
        accountPass = findViewById(R.id.account_password);
        accountComp = findViewById(R.id.account_companyName);

        String url = getString(R.string.base_url)+"/users/"+userId;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response ->{
                    try {
                        getUsername = response.getString("username");
                        getComp = response.getString("companyName");
                        getPass = response.getString("password");
                        latitude = response.getDouble("latitude");
                        longtitude = response.getDouble("longtitude");

                        accountID.setText(String.valueOf(userId));
                        accountUsername.setText(getUsername);
                        accountPass.setText(getPass);
                        accountComp.setText(getComp);
                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }

                }, error -> {
            Log.d("Login Failed","Error: " + error
            );
            Toast.makeText(MainActivity.this, new String(error.toString()), Toast.LENGTH_LONG).show();
        });

        AppController.getInstance().addToRequestQueue(jsonObjectRequest);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(userType ==1)
            navigationView.getMenu().findItem(R.id.nav_setting).setVisible(true);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new StorageFragment()).commit();
                break;

            case R.id.nav_order:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new OrderFragment()).commit();
                break;

            case R.id.nav_setting:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SettingFragment()).commit();
                break;
            case R.id.nav_contact:
                makeCall();
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void goStorage(View view){
        Intent i = new Intent(MainActivity.this, StorageActivity.class);
        i.putExtra("userId", userId);
        i.putExtra("userType", userType);
        startActivity(i);
        finish();
    }

    public void goOrders(View view){
        Intent i = new Intent(MainActivity.this, OrderActivity.class);
        i.putExtra("userId", userId);
        i.putExtra("userType", userType);
        i.putExtra("latitude", latitude);
        i.putExtra("longtitude", longtitude);
        startActivity(i);
        finish();
    }

    public void goStorage_inOut(View view){
        Intent i = new Intent(MainActivity.this, QRScannerOutActivity.class);
        i.putExtra("userId", userId);
        i.putExtra("userType", userType);
        startActivity(i);
        finish();
    }

    public void goCreate(View view){
        Intent i = new Intent(MainActivity.this, SignupActivity.class);
        i.putExtra("userId", userId);
        i.putExtra("userType", userType);
        startActivity(i);
        finish();
    }

    public void goManage(View view){
        Intent i = new Intent(MainActivity.this, ManageActivity.class);
        i.putExtra("userId", userId);
        i.putExtra("userType", userType);
        startActivity(i);
        finish();
    }

    public void testMap(View view){
        Intent i = new Intent(MainActivity.this, StorageInOutActivity.class);
        i.putExtra("userId", userId);
        i.putExtra("userType", userType);
        i.putExtra("trackOrder", false);
        startActivity(i);
        finish();
    }

    private void makeCall(){
        String number = getString(R.string.phone_number);
        if (number.trim().length() > 0) {

            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }

        } else {
            Toast.makeText(MainActivity.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makeCall();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
