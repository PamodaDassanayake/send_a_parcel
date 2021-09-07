package com.myapp.courier.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myapp.courier.R;
import com.myapp.courier.dto.OrderDetailsDto;

import java.util.List;

public class DriverOrderDetailsAdapter extends RecyclerView.Adapter<DriverOrderDetailsAdapter.OrderDetailsViewHolder> {
    Context mContext;
    List<OrderDetailsDto> mData;
    private OrderDetailsViewHolder.RecyclerViewClickListener clicklistener;

    public DriverOrderDetailsAdapter(Context mContext, List<OrderDetailsDto> mData, OrderDetailsViewHolder.RecyclerViewClickListener listener){
        this.mContext = mContext;
        this.mData = mData;
        this.clicklistener =listener;

    }

    @NonNull
    @Override
    public DriverOrderDetailsAdapter.OrderDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View layout;
        layout = LayoutInflater.from(mContext).inflate(R.layout.driver_order_details_layout,viewGroup,false);
        return new OrderDetailsViewHolder(layout,mContext,mData,clicklistener);
    }

    @Override
    public void onBindViewHolder(DriverOrderDetailsAdapter.OrderDetailsViewHolder holder, int position) {
        holder.tv_name.setText(mData.get(position).getName());
        holder.tv_address.setText(mData.get(position).getAddress());
        holder.tv_phone.setText(mData.get(position).getPhone());
        holder.tv_desc.setText(mData.get(position).getDesc());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class OrderDetailsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_name,tv_address,tv_phone,tv_desc;
        RecyclerViewClickListener mrecyclerViewClickListener;
        public OrderDetailsViewHolder(@NonNull View itemView, Context mContext, List<OrderDetailsDto> mData, RecyclerViewClickListener onClickListener) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_address = itemView.findViewById(R.id.tv_address);
            tv_phone = itemView.findViewById(R.id.tv_phone);
            tv_desc = itemView.findViewById(R.id.tv_desc);
            this.mrecyclerViewClickListener = onClickListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            mrecyclerViewClickListener.onClickListener(getAdapterPosition());

        }
        public interface RecyclerViewClickListener {
            void onClickListener(int position);
        }
    }

}
