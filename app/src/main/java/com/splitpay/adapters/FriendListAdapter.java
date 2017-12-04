package com.splitpay.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.splitpay.R;
import com.splitpay.RecyclerItemSelectedListener;
import com.splitpay.User;

import java.util.List;

/**
 * Created by shashank on 30-01-2017.
 */

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.RoomiesViewHolder> {

    private Context mContext;
    private List<User> userList;
    private RecyclerItemSelectedListener mListener;

    public FriendListAdapter(Context mContext, List<User> userList, RecyclerItemSelectedListener listener) {
        this.mContext = mContext;
        this.userList = userList;
        mListener = listener;
    }

    @Override
    public RoomiesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_roomies_list, parent, false);
        return new RoomiesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RoomiesViewHolder holder, int position) {
        final User user = userList.get(position);
        if (user != null) {
            holder.tvName.setText(!TextUtils.isEmpty(user.getFullName()) ? user.getFullName() : "");
            StringBuilder emailPhone = new StringBuilder();
            if (!TextUtils.isEmpty(user.getPhoneNumber())) {
                emailPhone.append(user.getPhoneNumber());
            }
            if (!TextUtils.isEmpty(user.getEmail())) {
                emailPhone.append("/").append(user.getEmail());
            }
            holder.tvEmailPhone.setText(emailPhone);
        }
        holder.tvName.setText(userList.get(position).getEmail());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null)
                    mListener.onItemClick(user, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class RoomiesViewHolder extends RecyclerView.ViewHolder {

        private CheckBox cbNames;
        private TextView tvName, tvEmailPhone;

        RoomiesViewHolder(View itemView) {
            super(itemView);
            cbNames = (CheckBox) itemView.findViewById(R.id.checkBox);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvEmailPhone = (TextView) itemView.findViewById(R.id.tvEmailPhone);
        }
    }
}
