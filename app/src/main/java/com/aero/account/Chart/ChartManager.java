package com.aero.account.Chart;

import android.content.Context;
import android.graphics.Color;
import android.provider.CalendarContract;
import android.util.DisplayMetrics;
import android.util.Log;

import com.aero.account.Data.Account;
import com.aero.account.Data.DBManager;
import com.aero.account.MyFunction;
import com.aero.account.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;
import java.util.List;

public class ChartManager {

    public DBManager dbManager;
    public Context context;

    public ArrayList<Integer> origin_colors = new ArrayList<>();

    public ChartManager(Context context, DBManager dbManager){
        this.context = context;
        this.dbManager = dbManager;
        origin_colors.add(Color.parseColor("#66C3E3"));
        origin_colors.add(Color.parseColor("#39B8E3"));
        origin_colors.add(Color.parseColor("#0095C6"));
        origin_colors.add(Color.parseColor("#257995"));
        origin_colors.add(Color.parseColor("#006181"));

        origin_colors.add(Color.parseColor("#59EA3A"));
        origin_colors.add(Color.parseColor("#FFFA40"));
        origin_colors.add(Color.parseColor("#E238A7"));

        origin_colors.add(Color.parseColor("#8DB42D"));
        origin_colors.add(Color.parseColor("#3DA028"));
        origin_colors.add(Color.parseColor("#BFBC30"));
        origin_colors.add(Color.parseColor("#94256D"));
    }

    public void showPieChart_Account(PieChart pieChart,int subject,String date){
        String strWhere="";
//        if(date=="All") strWhere="";
//        else strWhere=" and strftime('%Y-%m',datetime(date))='"+date+"'";
        //获得数据
        //String strSql="select * from Account where type=='"+type+"'";
        String strSql="";
        String type="";
        List<Account> accounts = new ArrayList<>();
        switch (subject){
            case 0://流动资产
                strSql="select * from Account where type=='流动资产' order by endingBalance desc";
                accounts = dbManager.queryAccount(strSql);
                type="流动资产";
                break;
            case 1://营业收入
                strSql="select sum(amount) as sum,credit as name from Voucher  where subject=1 group by credit order by sum desc";
                accounts = dbManager.queryAccount2(strSql);
                type="营业收入";
                break;
            case 2://成本
                strSql="select sum(amount) as sum,debit as name from Voucher  where subject=2 group by debit order by sum desc";
                accounts = dbManager.queryAccount2(strSql);
                type="成本";
                break;
        }
        String strSql2="select sum(abs(endingBalance)) as Expr1 from Account where type=='"+type+"'";
        int count = accounts.size();

        ArrayList<String> xValues = new ArrayList<>(); //每个饼块上的内容
        ArrayList<Entry>  yValues = new ArrayList<>(); //每个饼块的实际数据
        ArrayList<Integer> colors = new ArrayList<>(); // 饼图颜色

        for (int i = 0; i < count; i++) {
            xValues.add(accounts.get(i).name);
            yValues.add(new Entry((float) accounts.get(i).endingBalance, i, accounts.get(i).name));
            //colors.add(Color.parseColor("#"+accounts.get(i).hex));
            colors.add(origin_colors.get(i));
        }

        PieDataSet pieDataSet = new PieDataSet(yValues, "账户余额"/*显示在比例图上*/);//y轴的集合
        pieDataSet.setSliceSpace(0f); //设置个饼状图之间的距离
        pieDataSet.setColors(colors);

        //设置数据
        PieData pieData = new PieData(xValues, pieDataSet);
        pieData.setValueTextSize(14f);
        pieData.setHighlightEnabled(true);
        pieChart.setData(pieData);

        //饼图显示样式
        pieChart.setBackgroundColor(Color.parseColor("#FFFFFF"));
        pieChart.setHoleColorTransparent(true);
        pieChart.setHoleRadius(50f);              //内圆半径，等于0则为实心圆
        pieChart.setTransparentCircleRadius(60f); // 半透明圈
        pieChart.setDescription("");              //描述

        pieChart.setDrawCenterText(true);  //饼状图中间可以添加文字
        pieChart.setCenterText("￥"+dbManager.getDoubleValue(strSql2));//饼状图中间的文字
        pieChart.setCenterTextSize(22f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setRotationAngle(-90);        // 初始旋转角度
        pieChart.setUsePercentValues(true);  //显示成百分比
        pieChart.setRotationEnabled(false);  // 不可以手动旋转

        //设置比例图 不显示
        Legend mLegend = pieChart.getLegend();
        mLegend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);  //最右边显示
        mLegend.setForm(Legend.LegendForm.LINE);  //设置比例图的形状，默认是方形
        mLegend.setXEntrySpace(7f);
        mLegend.setYEntrySpace(5f);
        mLegend.setEnabled(false);//不显示比例图

        //设置动画
        pieChart.animateXY(1000, 1000);
        //pieChart.spin(2000, 0, 360, Easing.EasingOption.EaseInElastic);

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float px = 5 * (metrics.densityDpi / 160f);
        pieDataSet.setSelectionShift(px); // 选中态多出的长度
    }
}
