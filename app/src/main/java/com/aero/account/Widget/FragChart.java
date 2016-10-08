package com.aero.account.Widget;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aero.account.Chart.ChartManager;
import com.aero.account.Data.DBManager;
import com.aero.account.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

public class FragChart extends Fragment implements OnChartValueSelectedListener {

    public ChartManager chartManager;
    public DBManager dbManager;
    public View rootView;
    public PieChart pieChart;
    public TextView tvSelectedName;
    public TextView tvSelectedAmount;

    public String date;
    public int subject=0; /* =0:资产 from Account|=1:收入 from Voucher |=2:成本 from Voucher */

    public void setSubject(int subject,String date) {
        this.subject = subject;
        this.date = date;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_chart, container, false);
        dbManager = new DBManager(this.getContext());
        chartManager = new ChartManager(this.getContext(),dbManager);
        pieChart = (PieChart) rootView.findViewById(R.id.pie_chart);
        tvSelectedName = (TextView) rootView.findViewById(R.id.tv_selected_name);
        tvSelectedAmount = (TextView) rootView.findViewById(R.id.tv_selected_amount);

        return rootView;
    }

    @Override
    public void onStart() {

        super.onStart();
    }

    @Override
    public void onResume() {
        setView();
        super.onResume();
    }

    public void setView() {
        pieChart.setOnChartValueSelectedListener(this);
        chartManager.showPieChart_Account(pieChart,subject,date);
        pieChart.setOnChartValueSelectedListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbManager.closeDB();
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        tvSelectedName.setText(e.getData().toString());
        tvSelectedAmount.setText("￥"+e.getVal());
    }

    @Override
    public void onNothingSelected() {

    }
}
