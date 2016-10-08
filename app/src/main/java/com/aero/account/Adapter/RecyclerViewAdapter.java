package com.aero.account.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aero.account.Data.Account;
import com.aero.account.Data.DBManager;
import com.aero.account.Data.Voucher;
import com.aero.account.MyFunction;
import com.aero.account.R;

import java.util.List;
import java.util.Objects;

public class RecyclerViewAdapter extends RecyclerView.Adapter implements View.OnClickListener{

    private List<Voucher> vouchers;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public String date;
    //public  MyFunction fun;

    public RecyclerViewAdapter(Context context,String strSql){
        //fun = new MyFunction();
        DBManager dbManager = new DBManager(context);
        vouchers = dbManager.queryVoucher(strSql);//获得所有记录
        dbManager.closeDB();
        date="";
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_voucher, parent,false);
        view.setOnClickListener(this);//将创建的View注册点击事件
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //int picID = fun.getDrawableID(accounts.get(position).icon);

        ViewHolder vh = (ViewHolder) holder;

        if(Objects.equals(date, vouchers.get(position).date.trim())){
            vh.line1.setVisibility(View.GONE);
            vh.tvDate.setText("");
            vh.tvAmount.setText("");
        }else{
            vh.line1.setVisibility(View.GONE);
            vh.tvDate.setText(vouchers.get(position).date);
            vh.tvAmount.setText(vouchers.get(position).amount.toString());
        }
        if(vouchers.get(position).subject==1){
            vh.tvLeft.setText(vouchers.get(position).amount.toString()+"  "+vouchers.get(position).credit);
            vh.tvRight.setText("");
        }else{
            vh.tvRight.setText(vouchers.get(position).credit+"  "+vouchers.get(position).amount.toString());
            vh.tvLeft.setText("");
        }
        //vh.itemView.setTag(vouchers.get(position).credit);
        date = vouchers.get(position).date.trim();
    }

    @Override
    public int getItemCount() {
        return vouchers.size();
    }

    @Override
    public void onClick(View v) {
        //注意这里使用getTag方法获取数据
        if (mOnItemClickListener != null) mOnItemClickListener.onItemClick(v, (String)v.getTag());
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvLeft;
        public TextView tvRight;
        public ImageButton imageButton;
        public TextView tvDate;
        public TextView tvAmount;
        public ImageButton imageButton2;
        public LinearLayout line0;
        public LinearLayout line1;

        public ViewHolder(View rootView) {
            super(rootView);
            tvLeft =  (TextView) rootView.findViewById(R.id.tv_left);
            tvRight = (TextView) rootView.findViewById(R.id.tv_right);
            imageButton=(ImageButton) rootView.findViewById(R.id.imageButton);

            tvDate =  (TextView) rootView.findViewById(R.id.tv_date);
            tvAmount = (TextView) rootView.findViewById(R.id.tv_amount);
            imageButton2=(ImageButton) rootView.findViewById(R.id.imageButton2);

            line0= (LinearLayout) rootView.findViewById(R.id.line0);
            line1= (LinearLayout) rootView.findViewById(R.id.line1);
        }
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view,String data);
    }
}
