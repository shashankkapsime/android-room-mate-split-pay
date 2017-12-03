package com.splitpay.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.splitpay.R;
import com.splitpay.User;

import java.util.List;

/**
 * Created by shashank on 30-01-2017.
 */

public class RoomiesListAdapter extends RecyclerView.Adapter<RoomiesListAdapter.RoomiesViewHolder> {

    private Context mContext;
    private List<User> userList;

    public RoomiesListAdapter(Context mContext, List<User> userList) {
        this.mContext = mContext;
        this.userList = userList;
    }

    @Override
    public RoomiesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_roomies_list, parent, false);
        return new RoomiesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RoomiesViewHolder holder, int position) {
        holder.cbNames.setText(userList.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class RoomiesViewHolder extends RecyclerView.ViewHolder {

        private CheckBox cbNames;

        RoomiesViewHolder(View itemView) {
            super(itemView);
            cbNames = (CheckBox) itemView.findViewById(R.id.cbNames);
        }
    }
}
