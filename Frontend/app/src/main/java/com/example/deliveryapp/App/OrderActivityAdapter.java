package com.example.deliveryapp.App;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.deliveryapp.Model.Order;
import com.example.deliveryapp.R;
import com.example.deliveryapp.StorageInOutActivity;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class OrderActivityAdapter extends
        RecyclerView.Adapter<OrderActivityAdapter.ViewHolder> {

    private List<Order> orderList = new ArrayList<>();
    private Context item_context;
    private String item_sellOrBuy, item_target, item_status;
    private double latitude, longtitude;
    private int item_id, userId, userType, deliverymanId;

    public OrderActivityAdapter(List<Order> orders, int userId, int userType, int deliverymanId) {
        this.userType = userType;
        this.userId = userId;
        this.orderList = orders;
        this.deliverymanId = deliverymanId;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView tv_sellOrBuy, tv_target, tv_status;
        public Button btn_position;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            item_context = itemView.getContext();

            tv_sellOrBuy = (TextView) itemView.findViewById(R.id.order_buyOrSell);
            tv_target = itemView.findViewById(R.id.order_target);
            tv_status = itemView.findViewById(R.id.order_status);
            btn_position = (Button) itemView.findViewById(R.id.order_checkPosition);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.recycleview_order, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(OrderActivityAdapter.ViewHolder viewHolder, int position) {
        TextView onbind_tv_buyOrSell = viewHolder.tv_sellOrBuy;
        TextView onbind_tv_target = viewHolder.tv_target;
        TextView onbind_tv_status = viewHolder.tv_status;
        Button onbind_btn_position = viewHolder.btn_position;
        Order item_order = orderList.get(viewHolder.getAdapterPosition());




        if (item_order.getBuyerId() == userId) {
            item_sellOrBuy = "买入";
            item_target = String.valueOf(item_order.getSellerId());
        } else {
            item_sellOrBuy = "卖出";
            item_target = String.valueOf(item_order.getBuyerId());
        }
        if (item_order.getStatus() == 1)
            item_status = "运输中";
        else
            item_status = "已完成";
        onbind_tv_buyOrSell.setText(item_sellOrBuy);
        onbind_tv_target.setText(item_target);
        onbind_tv_status.setText(item_status);

        onbind_btn_position.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = item_context.getString(R.string.base_url) + "/users/" + deliverymanId;
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                        response -> {
                            try {
                                String response_username = response.getString("username");
                                latitude = response.getDouble("latitude");
                                longtitude = response.getDouble("longtitude");
                                Intent i = new Intent(item_context, StorageInOutActivity.class);
                                i.putExtra("userId", userId);
                                i.putExtra("userType", userType);
                                i.putExtra("latitude", latitude);
                                i.putExtra("longtitude", longtitude);
                                i.putExtra("trackOrder", true);
                                item_context.startActivity(i);
                                Toast.makeText(item_context, "运输员： " + response_username + " 的位置", Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }, error -> {
                    Log.d("Login Failed", "Error: " + error
                    );
                    Toast.makeText(item_context, new String(error.toString()), Toast.LENGTH_LONG).show();
                });

                AppController.getInstance().addToRequestQueue(jsonObjectRequest);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }


}
