package com.aero.account;

import android.graphics.Color;
import android.util.Log;

import java.lang.reflect.Field;

public class MyFunction {
    public MyFunction(){

    }

    /**
     * 获得资源的ID
     * @param fileName 资源名称,eg:type_cash
     * @return Resource ID
     */
    public int getDrawableID(String fileName){
        try{
            Field field=R.drawable.class.getField(fileName);
            int i= field.getInt(new R.drawable());
            Log.d("user-getDrawableID", i + "-getDrawableID");
            return i;
        }catch(Exception e){
            Log.d("user-getDrawableID", e.toString() + "-getDrawableID");
            return R.drawable.type_default;
        }
    }

}
