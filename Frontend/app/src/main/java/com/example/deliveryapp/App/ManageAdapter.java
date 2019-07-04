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

import com.example.deliveryapp.ChangeAccountActivity;
import com.example.deliveryapp.ManageActivity;
import com.example.deliveryapp.Model.Good;
import com.example.deliveryapp.Model.User;
import com.example.deliveryapp.R;
import com.example.deliveryapp.StorageGoodActivity;

import java.util.ArrayList;
import java.util.List;

public class ManageAdapter extends
        RecyclerView.Adapter<ManageAdapter.ViewHolder> {

    private List<User> users = new ArrayList<>();
    private Context item_context;
    private String item_userType, item_userName;
    private int item_userId, userId;

    public ManageAdapter(List<User> users, int userId) {
        this.userId = userId;
        this.users =users;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView tv_userId, tv_username, tv_userType;
        public Button btn_change;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            item_context = itemView.getContext();

            tv_userId = (TextView) itemView.findViewById(R.id.manage_userId);
            tv_username = itemView.findViewById(R.id.manage_username);
            tv_userType = itemView.findViewById(R.id.manage_userType);
            btn_change = (Button) itemView.findViewById(R.id.manage_userBtn);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.recycleview_manage, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ManageAdapter.ViewHolder viewHolder, int position) {
        TextView obtv_userId = viewHolder.tv_userId;
        TextView obtv_username = viewHolder.tv_username;
        TextView obtv_userType = viewHolder.tv_userType;
        Button obbtn_change = viewHolder.btn_change;

        User itemUser = users.get(viewHolder.getAdapterPosition());

        item_userId = itemUser.getId();
        item_userName = itemUser.getUsername();
        if(itemUser.getUserType() ==1)
            item_userType = "管理员";
        else if(itemUser.getUserType() ==2)
            item_userType = "公司账号";
        else if (itemUser.getUserType() ==3){
            item_userType = "运输员";
        }

//        more_info.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                item_clickListener(position);
//            }
//        });

        obtv_userId.setText(String.valueOf(item_userId));
        obtv_username.setText(item_userName);
        obtv_userType.setText(item_userType);

        obbtn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item_clickListener(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    private void item_clickListener(int position){
        User item_user = users.get(position);
        System.out.println("adapter"+item_user);

        int target_id = item_user.getId();
        Intent i = new Intent(item_context, ChangeAccountActivity.class);
        i.putExtra("userId", userId);
        i.putExtra("userType", 1);
        i.putExtra("targetId", target_id);
        item_context.startActivity(i);
        Activity activity = (Activity)item_context;
        activity.finish();
    }
}
