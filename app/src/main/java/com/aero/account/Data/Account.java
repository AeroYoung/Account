package com.aero.account.Data;

public class Account {
    public int code;
    public String name;
    public int numSubAccount;
    public double openingBalance;
    public double amountOccurred;
    public double endingBalance;
    public String type;
    public String icon;
    public String hex;

    public Account(){

    }

    public Account(int code,String name,int numSubAccount,
                   double openingBalance,double amountOccurred,double endingBalance,
                   String type,String icon,String hex) {
        this.code=code;
        this.name=name;
        this.numSubAccount=numSubAccount;
        this.openingBalance=openingBalance;
        this.amountOccurred=amountOccurred;
        this.endingBalance=endingBalance;
        this.type=type;
        this.icon=icon;
        this.hex=hex;
    }
}
