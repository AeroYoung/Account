package com.aero.account.Data;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBManager {
    private DBHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context) {
        helper = new DBHelper(context);
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = helper.getWritableDatabase();
    }

    public void addVoucher(Voucher voucher) {
        db.beginTransaction();  //开始事务
        try {
            db.execSQL("INSERT INTO Voucher VALUES(null,?,?,?,?,?,?)",
                    new Object[]{voucher.amount, voucher.debit,voucher.credit,voucher.date,voucher.remark,voucher.subject});
            //ALTER TABLE Account ADD '2015Feb' DOUBLE  DEFAULT (0); Update Account set '2015Feb' = endingBalance

            db.execSQL("update Account set endingBalance = endingBalance-"+voucher.amount+" where name='"+voucher.credit+"'");
            db.execSQL("update Account set endingBalance = endingBalance+"+voucher.amount+" where name='"+voucher.debit+"'");
            db.setTransactionSuccessful();  //设置事务成功完成
        } finally {
            db.endTransaction();    //结束事务
        }
    }

//    public void addAccount(List<Account> accounts) {
//        db.beginTransaction();  //开始事务
//        try {
//            for (Account person : accounts) {
//                db.execSQL("INSERT INTO Account VALUES(null, ?, ?, ?)", new Object[]{person.name, person.age, person.info});
//            }
//            db.setTransactionSuccessful();  //设置事务成功完成
//        } finally {
//            db.endTransaction();    //结束事务
//        }
//    }

//    /**
//     * update person's age
//     * @param person
//     */
//    public void updateAge(Person person) {
//        ContentValues cv = new ContentValues();
//        cv.put("age", person.age);
//        db.update("person", cv, "name = ?", new String[]{person.name});
//    }

//    /**
//     * delete old person
//     * @param person
//     */
//    public void deleteOldPerson(Person person) {
//        db.delete("person", "age >= ?", new String[]{String.valueOf(person.age)});
//    }

    public double getDoubleValue(String strSql){
        double result=0;
        Cursor c = db.rawQuery(strSql, null);
        while (c.moveToNext()) {
            result = c.getDouble(c.getColumnIndex("Expr1"));
        }
        c.close();
        return result;
    }

    /**
     * query all Account, return list
     * @return List<Account>
     */
    public List<Account> queryAccount(String strSql) {
        ArrayList<Account> accounts = new ArrayList<>();
        Cursor c = db.rawQuery(strSql, null);
        while (c.moveToNext()) {
            Account account = new Account();
            account.code = c.getInt(c.getColumnIndex("code"));
            account.name = c.getString(c.getColumnIndex("name"));
            account.numSubAccount = c.getInt(c.getColumnIndex("numSubAccount"));
            account.openingBalance = c.getDouble(c.getColumnIndex("openingBalance"));
            account.amountOccurred = c.getDouble(c.getColumnIndex("amountOccurred"));
            account.endingBalance = c.getDouble(c.getColumnIndex("endingBalance"));
            account.type = c.getString(c.getColumnIndex("type"));
            account.icon = c.getString(c.getColumnIndex("icon"));
            account.hex = c.getString(c.getColumnIndex("hex"));
            accounts.add(account);
        }
        c.close();
        return accounts;
    }

    public List<Account> queryAccount2(String strSql) {
        ArrayList<Account> accounts = new ArrayList<>();
        Cursor c = db.rawQuery(strSql, null);
        while (c.moveToNext()) {
            Account account = new Account();
            account.name = c.getString(c.getColumnIndex("name"));
            account.endingBalance = c.getDouble(c.getColumnIndex("sum"));
            account.hex="101001";
            accounts.add(account);
        }
        c.close();
        return accounts;
    }

    public List<Voucher> queryVoucher(String strSql) {
        ArrayList<Voucher> vouchers = new ArrayList<>();
        Cursor c = db.rawQuery(strSql, null);
        while (c.moveToNext()) {
            Voucher voucher = new Voucher();
            voucher.amount = c.getDouble(c.getColumnIndex("amount"));
            voucher.debit = c.getString(c.getColumnIndex("debit"));
            voucher.credit = c.getString(c.getColumnIndex("credit"));
            voucher.date = c.getString(c.getColumnIndex("date"));
            voucher.remark = c.getString(c.getColumnIndex("remark"));
            voucher.subject = c.getInt(c.getColumnIndex("subject"));
            vouchers.add(voucher);
        }
        c.close();
        return vouchers;
    }

    public void closeDB() {
        db.close();
    }
}
