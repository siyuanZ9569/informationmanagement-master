package com.example.deliveryapp.App;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deliveryapp.Model.Good;
import com.example.deliveryapp.R;
import com.example.deliveryapp.StorageGoodActivity;

import java.util.ArrayList;
import java.util.List;

public class StorageActivityAdapter extends
        RecyclerView.Adapter<StorageActivityAdapter.ViewHolder> {

    private List<Good> goodList = new ArrayList<>();
    private Context item_context;
    private String item_type, item_origin, item_description;
    private double item_weight;
    private int item_id, user_id;

    public StorageActivityAdapter(List<Good> goods) {
        this.goodList = goods;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView goodType, goodWeight, goodOrigin;
        public Button moreInfo;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            item_context = itemView.getContext();

            goodType = (TextView) itemView.findViewById(R.id.storage_type);
            goodWeight = itemView.findViewById(R.id.storage_weight);
            goodOrigin = itemView.findViewById(R.id.storage_orgin);
            moreInfo = (Button) itemView.findViewById(R.id.storage_button);


        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.activity_storage_helper, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StorageActivityAdapter.ViewHolder viewHolder, int position) {
        TextView good_type = viewHolder.goodType;
        TextView good_weight = viewHolder.goodWeight;
        TextView good_origin = viewHolder.goodOrigin;
        Button more_info = viewHolder.moreInfo;
        Good item_good = goodList.get(viewHolder.getAdapterPosition());

        item_type = item_good.getType();
        item_origin = item_good.getOrigin();
        item_id = item_good.getId();
        item_weight = item_good.getWeight();
        item_description = item_good.getDescription();
        user_id = item_good.getUserId();

        more_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item_clickListener(position);
            }
        });

        good_type.setText(item_type);
        good_origin.setText(item_origin);
        good_weight.setText(String.valueOf(item_weight));
    }

    @Override
    public int getItemCount() {
        return goodList.size();
    }

    private void item_clickListener(int position){
        Good item_good = goodList.get(position);
        System.out.println("adapter"+item_good);

        item_type = item_good.getType();
        item_origin = item_good.getOrigin();
        item_id = item_good.getId();
        item_weight = item_good.getWeight();
        item_description = item_good.getDescription();
        user_id = item_good.getUserId();
        Intent i = new Intent(item_context, StorageGoodActivity.class);
        i.putExtra("userId", user_id);
        i.putExtra("goodType", item_type);
        i.putExtra("goodOrigin", item_origin);
        i.putExtra("goodDescription", item_description);
        i.putExtra("goodId", item_id);
        i.putExtra("goodWeight", item_weight);
        item_context.startActivity(i);
        Activity activity = (Activity)item_context;
        activity.finish();
    }
}
