package com.aero.account.Widget;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.aero.account.Chart.MyTextView;
import com.aero.account.Data.DBManager;
import com.aero.account.Data.Voucher;
import com.aero.account.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class AtyEntry extends Activity implements View.OnClickListener{

    public MyTextView tvSum;
    public DBManager dbManager;
    public Spinner spLeft,spRight;
    public String left_account_name,right_account_name;
    public ImageView ivCenter;
    public TextView tvIncome,tvOutlay;

    public String string;
    public int subject=2; /* =1:收入 from Voucher |=2:成本 from Voucher 3 转账*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_aty_entry, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        ActionBar actionBar = getActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        left_account_name = "现金";
        right_account_name= "一日三餐";
        string = "0";
        dbManager = new DBManager(this);

        initView();
        initSpinner();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void initView(){
        tvIncome = (TextView) findViewById(R.id.tv_income);
        tvOutlay = (TextView) findViewById(R.id.tv_outlay);
        tvIncome.setOnClickListener(this);
        tvOutlay.setOnClickListener(this);
        ivCenter = (ImageView) findViewById(R.id.iv_center);

        tvSum = (MyTextView) findViewById(R.id.tv_sum);
        string = "0";

        findViewById(R.id.num_0).setOnClickListener(this);
        findViewById(R.id.num_1).setOnClickListener(this);
        findViewById(R.id.num_2).setOnClickListener(this);
        findViewById(R.id.num_3).setOnClickListener(this);
        findViewById(R.id.num_4).setOnClickListener(this);
        findViewById(R.id.num_5).setOnClickListener(this);
        findViewById(R.id.num_6).setOnClickListener(this);
        findViewById(R.id.num_7).setOnClickListener(this);
        findViewById(R.id.num_8).setOnClickListener(this);
        findViewById(R.id.num_9).setOnClickListener(this);
        findViewById(R.id.num_dot).setOnClickListener(this);
        findViewById(R.id.num_CE).setOnClickListener(this);
        findViewById(R.id.num_back).setOnClickListener(this);
        findViewById(R.id.num_equal).setOnClickListener(this);
        findViewById(R.id.num_ok).setOnClickListener(this);
    }

    public void initSpinner(){
        spLeft  = (Spinner) findViewById(R.id.sp_left);
        spRight = (Spinner) findViewById(R.id.sp_right);

        // 建立数据源
        String[] itemsAssets = getResources().getStringArray(R.array.assets);
        String[] itemsOutlay = getResources().getStringArray(R.array.outlay);
        // 建立Adapter并且绑定数据源
        ArrayAdapter<String> adapterLeft=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, itemsAssets);
        adapterLeft.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> adapterRight=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, itemsOutlay);
        adapterRight.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //绑定 Adapter到控件
        spLeft.setAdapter(adapterLeft);
        spRight.setAdapter(adapterRight);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_income:
                onSwitchIncomeOutlay(1);
                break;
            case R.id.tv_outlay:
                onSwitchIncomeOutlay(2);
                break;

            case R.id.num_0:
                onNumClick(0);
                break;
            case R.id.num_1:
                onNumClick(1);
                break;
            case R.id.num_2:
                onNumClick(2);
                break;
            case R.id.num_3:
                onNumClick(3);
                break;
            case R.id.num_4:
                onNumClick(4);
                break;
            case R.id.num_5:
                onNumClick(5);
                break;
            case R.id.num_6:
                onNumClick(6);
                break;
            case R.id.num_7:
                onNumClick(7);
                break;
            case R.id.num_8:
                onNumClick(8);
                break;
            case R.id.num_9:
                onNumClick(9);
                break;

            case R.id.num_dot:
                if(!string.contains(".")) string += ".";
                break;

            case R.id.num_CE:
                string = "0";
                break;
            case R.id.num_back:
                if(string.length()==1) string="0";
                else string  = string.substring(0,string.length()-1);
                break;
            case R.id.num_equal:
                break;

            case R.id.num_ok:
                onOKClick();
                break;

            default:
                break;
        }
        tvSum.setText("￥" + string);
    }

    public void onNumClick(int num){
        if(Objects.equals(string, "0")) string = String.valueOf(num);
        else string += num;
    }

    public void onOKClick(){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        String date = formatter.format(new Date(System.currentTimeMillis()));
        double amount = Double.parseDouble(string);
        String credit="";
        String debit="";
        if(subject==2){
            credit = spLeft.getSelectedItem().toString();//贷方
            debit  = spRight.getSelectedItem().toString();//借方
        }else if(subject==1){
            credit = spRight.getSelectedItem().toString();//贷方
            debit  = spLeft.getSelectedItem().toString();//借方
        }

        Voucher voucher = new Voucher(amount,debit,credit,date,"",subject);

        if(amount>0) dbManager.addVoucher(voucher);
        finish();
    }

    public void onSwitchIncomeOutlay(int target_subject){
        subject=target_subject;
        if(target_subject==1){
            tvIncome.setTextColor(Color.parseColor("#FFFFFF"));
            tvIncome.setTextSize(22);
            tvOutlay.setTextColor(Color.parseColor("#6AC5E1"));
            tvOutlay.setTextSize(20);
            ivCenter.setImageResource(R.drawable.btn_gain);

            String[] itemsIncome = getResources().getStringArray(R.array.income);
            ArrayAdapter<String> adapterRight=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,itemsIncome);
            adapterRight.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spRight.setAdapter(adapterRight);

        } else if (target_subject==2){
            tvOutlay.setTextColor(Color.parseColor("#FFFFFF"));
            tvOutlay.setTextSize(22);
            tvIncome.setTextColor(Color.parseColor("#6AC5E1"));
            tvIncome.setTextSize(20);
            ivCenter.setImageResource(R.drawable.btn_pay);

            String[] itemsOutlay = getResources().getStringArray(R.array.outlay);
            ArrayAdapter<String> adapterRight=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,itemsOutlay);
            adapterRight.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spRight.setAdapter(adapterRight);
        }
    }

    @Override
    public void onDestroy() {
        dbManager.closeDB();
        super.onDestroy();
    }
}
