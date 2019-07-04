package com.example.deliveryapp;

import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.deliveryapp.App.AppController;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class StorageGoodActivity extends AppCompatActivity {
    private TextView good_type, good_id, good_weight, good_origin, good_description;
    private int user_id, item_id;
    private String item_type, item_origin, item_description;
    private double item_weight;
    private Button editDescript, cancelEdit, postDescription;
    private PopupWindow popupWindow;
    private EditText ev_description;
    private Context mContext;
    private Bitmap bitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_good);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mContext= StorageGoodActivity.this;

        item_type = getIntent().getExtras().getString("goodType");
        item_origin = getIntent().getExtras().getString("goodOrigin");
        item_description = getIntent().getExtras().getString("goodDescription");
        item_weight = getIntent().getExtras().getDouble("goodWeight");
        item_id = getIntent().getExtras().getInt("goodId");
        user_id = getIntent().getExtras().getInt("userId");

        good_description = findViewById(R.id.storage_good_description);
        good_origin = findViewById(R.id.storage_good_origin);
        good_id = findViewById(R.id.storage_good_id);
        good_type = findViewById(R.id.storage_good_type);
        good_weight = findViewById(R.id.storage_good_weight);
        ev_description = findViewById(R.id.storage_good_addDescription);

        editDescript = findViewById(R.id.storage_good_editDescription);
        cancelEdit = findViewById(R.id.storage_good_cancelEdit);
        postDescription = findViewById(R.id.storage_good_postDescription);

        good_description.setText(item_description);
        good_weight.setText(String.valueOf(item_weight));
        good_type.setText(item_type);
        good_id.setText(String.valueOf(item_id));
        good_origin.setText(item_origin);

        editDescript.setOnClickListener(v -> editDescription());

        cancelEdit.setOnClickListener(v -> cancelEditDescription());

        postDescription.setOnClickListener(v -> postClickListener());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            Intent i = new Intent(StorageGoodActivity.this, StorageActivity.class);
            i.putExtra("userId", user_id);
            i.putExtra("userType", 1);
            startActivity(i);
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void editDescription(){
        ev_description.setVisibility(View.VISIBLE);
        cancelEdit.setVisibility(View.VISIBLE);
        good_description.setVisibility(View.INVISIBLE);
        postDescription.setVisibility(View.VISIBLE);
        editDescript.setVisibility(View.INVISIBLE);
    }

    private void cancelEditDescription(){
        ev_description.setVisibility(View.INVISIBLE);
        cancelEdit.setVisibility(View.INVISIBLE);
        good_description.setVisibility(View.VISIBLE);
        postDescription.setVisibility(View.INVISIBLE);
        editDescript.setVisibility(View.VISIBLE);
    }

    private void postClickListener(){
        String str_postDescription = ev_description.getText().toString().trim();
        if(str_postDescription.trim().length()!=0) {
            String url = getString(R.string.base_url) +
                    "/storage/editDescription?id=" + item_id + "&userId=" + user_id + "&description="+str_postDescription;
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    response -> {
                        Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
                        good_description.setText(str_postDescription);
                        ev_description.setVisibility(View.INVISIBLE);
                        cancelEdit.setVisibility(View.INVISIBLE);
                        good_description.setVisibility(View.VISIBLE);
                        postDescription.setVisibility(View.INVISIBLE);
                        editDescript.setVisibility(View.VISIBLE);
                    }, error -> {
                        Toast.makeText(mContext, error.toString(), Toast.LENGTH_SHORT).show();
            });
            AppController.getInstance().addToRequestQueue(stringRequest);
        }
        else{
            Toast.makeText(mContext, "描述不能为空", Toast.LENGTH_SHORT).show();
        }

    }

    private Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    500, 500, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.QRCodeBlackColor):getResources().getColor(R.color.QRCodeWhiteColor);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

    public void generateQRcode(View view){
        ImageView image = new ImageView(this);
        String qrcodeURL = getString(R.string.base_url)+"/storage/getByIdAndUserId?id="+item_id+"&userId="+user_id;
        try {
            bitmap = TextToImageEncode(qrcodeURL);

            image.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }

        AlertDialog.Builder builder =
                new AlertDialog.Builder(this).
                        setMessage("Message above the image").
                        setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).
                        setView(image);
        builder.create().show();
    }
}
