package com.aero.account.Widget;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.aero.account.Adapter.FragPagerAdapter;
import com.aero.account.Adapter.WrapContentViewPager;
import com.aero.account.R;
import java.util.ArrayList;
import java.util.List;

public class AtyMain extends AppCompatActivity implements ViewPager.OnPageChangeListener,View.OnClickListener{
    public WrapContentViewPager viewPager;
    public FragPagerAdapter fragPagerAdapter;
    public List<Fragment> fragments = new ArrayList<>();
    public DrawerLayout mDrawerLayout;
    public ActionBarDrawerToggle mDrawerToggle;
    public Toolbar toolbar;
    public TextView[] indicators;
    public TextView tvAll;
    public FragChart fragChartCurrentAssets;
    public FragChart fragChartIncome;
    public FragChart fragChartCost;
    public Spinner spYear;
    public Spinner spMouth;

    public TextView tvAxis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initBar();
        initView();
        initIndicatorAndOthers();
    }

    private void initBar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open,
                R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AtyMain.this, AtyEntry.class);
                startActivity(i);
            }
        });
    }

    private void initView() {
        fragChartCurrentAssets = new FragChart();
        fragChartCurrentAssets.setSubject(0,"All");

        fragChartIncome = new FragChart();
        fragChartIncome.setSubject(1,"All");

        fragChartCost = new FragChart();
        fragChartCost.setSubject(2,"All");

        fragments.add(fragChartCurrentAssets);
        fragments.add(fragChartIncome);
        fragments.add(fragChartCost);

        fragPagerAdapter = new FragPagerAdapter(getSupportFragmentManager(),fragments);
        viewPager = (WrapContentViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(fragPagerAdapter);
        viewPager.addOnPageChangeListener(this);
    }

    private void initIndicatorAndOthers(){
        indicators = new TextView[fragments.size()];
        indicators[0] = (TextView) findViewById(R.id.indicator_current_assets);
        indicators[1] = (TextView) findViewById(R.id.indicator_income);
        indicators[2] = (TextView) findViewById(R.id.indicator_cost);
        indicators[0].setOnClickListener(this);
        indicators[1].setOnClickListener(this);
        indicators[2].setOnClickListener(this);
        indicators[0].setSelected(true);

        tvAll = (TextView) findViewById(R.id.tv_all);
        spYear = (Spinner) findViewById(R.id.sp_year);
        spMouth = (Spinner) findViewById(R.id.sp_mouth);
        tvAll.setOnClickListener(this);

        tvAxis = (TextView) findViewById(R.id.tv_axis);
        tvAxis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AtyMain.this, AtyAxis.class);
                startActivity(i);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for(int i=0;i< indicators.length;i++){
            if(position==i) {
                indicators[i].setSelected(true);
                //indicators[i].setBackgroundColor(Color.parseColor("#DCDCDC"));
                //indicators[i].setTextColor(Color.parseColor("#0097C6"));
            }
            else {
                indicators[i].setSelected(false);
                //indicators[i].setBackgroundColor(Color.parseColor("#0097C6"));
                //indicators[i].setTextColor(Color.parseColor("#FFFFFF"));
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.indicator_current_assets:
                viewPager.setCurrentItem(0,true);
                break;
            case R.id.indicator_income:
                viewPager.setCurrentItem(1,true);
                break;
            case R.id.indicator_cost:
                viewPager.setCurrentItem(2,true);
                break;
            case R.id.tv_all:
                //String date = spYear.getSelectedItem().toString()+"-"+spMouth.getSelectedItem().toString();
//                String date = "All";
//                fragChartCurrentAssets.setSubject(0,"流动资产", date);
//                fragChartIncome.setSubject(1,"营业收入", date);
//                fragChartCost.setSubject(2,"成本", date);
//                fragChartCurrentAssets.setView();
//                fragChartIncome.setView();
//                fragChartCost.setView();
                break;
        }
    }
}
