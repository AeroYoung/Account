package com.aero.account.Chart;

public class Item {

    public double value;
    public int type = 0;//01234 +-*/=  -1 num

    public Item(double value,int type){
        this.value=value;
        this.type=type;
    }

}
