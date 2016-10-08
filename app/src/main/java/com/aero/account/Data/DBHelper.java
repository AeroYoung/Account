package com.aero.account.Data;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public  class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "account.db";
    private static final int DATABASE_VERSION = 4;//Account初始值为0
    public Context context;

    public void CreateTables(SQLiteDatabase db){
        db.execSQL("CREATE TABLE Account (\n" +
                "    code           INTEGER UNIQUE\n" +
                "                           NOT NULL\n" +
                "                           PRIMARY KEY,\n" +
                "    name           STRING  NOT NULL\n" +
                "                           UNIQUE,\n" +
                "    numSubAccount  INTEGER DEFAULT (0),\n" +
                "    openingBalance DOUBLE  DEFAULT (100),\n" +
                "    amountOccurred DOUBLE  DEFAULT (0),\n" +
                "    endingBalance  DOUBLE  DEFAULT (100),\n" +
                "    type           TEXT    DEFAULT 流动资产,\n" +
                "    icon           TEXT    DEFAULT type_cash,\n" +
                "    hex            STRING\n" +
                ");");

        db.execSQL("CREATE TABLE Voucher (\n" +
                "    _id     INTEGER PRIMARY KEY,\n" +
                "    amount  DOUBLE  DEFAULT (0),\n" +
                "    debit   STRING  DEFAULT 现金,\n" +
                "    credit  STRING  DEFAULT 一日三餐,\n" +
                "    date    STRING  DEFAULT (2016 - 2 - 19),\n" +
                "    remark  STRING,\n" +
                "    subject INTEGER DEFAULT (2) \n" +
                ");");
    }

    public void ImportDefaultData(SQLiteDatabase db){
        List<Account> accounts = getAccountsFromJSON();
        db.beginTransaction();  //开始事务
        try {
            for (Account account : accounts) {
                db.execSQL("INSERT INTO Account VALUES(?,?,?,?,?,?,?,?,?)",
                        new Object[]{account.code, account.name, account.numSubAccount,
                                account.openingBalance, account.amountOccurred, account.endingBalance,
                                account.type, account.icon,account.hex}
                );
            }
            db.setTransactionSuccessful();  //设置事务成功完成
        } finally {
            db.endTransaction();    //结束事务
        }
    }

    public List<Account> getAccountsFromJSON(){
        List<Account> accounts = new ArrayList<>();
        try {
            InputStreamReader isr = new InputStreamReader(context.getAssets().open("account.json"),"UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line;
            StringBuilder builder = new StringBuilder();
            while((line = br.readLine())!=null){
                builder.append(line);
            }
            try {
                JSONObject root = new JSONObject(builder.toString());
                JSONArray rows=root.getJSONArray("account_rows");
                for (int i=0;i<rows.length();i++){
                    JSONArray row = rows.getJSONArray(i);
                    accounts.add(new Account(row.getInt(0),
                                            row.getString(1),
                                            row.getInt(2),
                                            row.getDouble(3),
                                            row.getDouble(4),
                                            row.getDouble(5),
                                            row.getString(6),
                                            row.getString(7),
                                            row.getString(8))
                                );
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (UnsupportedEncodingException e ) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {//创建数据库内部结构
        CreateTables(db);
        ImportDefaultData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion==newVersion) return;
        db.execSQL("DROP TABLE if exists Account");
        db.execSQL("DROP TABLE if exists Voucher");
        CreateTables(db);
        ImportDefaultData(db);
    }

    public DBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context = context;
    }
}
