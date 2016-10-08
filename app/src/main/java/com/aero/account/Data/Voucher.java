package com.aero.account.Data;

public class Voucher {
    public Double amount;
    public String debit;
    public String credit;
    public String date;
    public String remark;
    public int subject;

    public Voucher(){

    }
    public Voucher(Double amount,String debit,String credit,String date,String remark,int subject){
        this.amount=amount;
        this.debit=debit;
        this.credit=credit;
        this.date=date;
        this.remark=remark;
        this.subject=subject;
    }
}
