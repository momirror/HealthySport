package com.example.msp.healthysport.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 数据存储于SharePreferences
 */

public class Storage {

    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;


    //初始化
    public static void createSharePrefences(Context context) {
        String name = context.getPackageName();
        sharedPreferences = context.getSharedPreferences(name,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    //判断是否已经创建preference
    public static boolean isUncreate(){
        boolean result = sharedPreferences == null?true:false;
        return result;
    }

    //保存string类型数据
    public static boolean saveStringValues(String key,String value) {
        if(isUncreate()) {
            return false;
        }

        editor.putString(key,value);
        return editor.commit();
    }

    //读取string类型的数据
    public static  String getStringValues(String key,String defaultValue) {
        if(isUncreate()) {
            return null;
        }

        String value = sharedPreferences.getString(key,defaultValue);
        return value;

    }

    /**保存int数据
     * @param key
     * @param value
     * @return
     */
    public static boolean saveIntValues(String key,int value) {
        if(isUncreate()) {
            return false;
        }

        editor.putInt(key,value);
        Boolean result = editor.commit();
        return result;
    }


    /**读取int值
     * @param key
     * @param defValue
     * @return
     */
    public static int getIntValue(String key,int defValue) {
        if(isUncreate()) {
            return 0;
        }

        int value = sharedPreferences.getInt(key,defValue);
        return value;
    }


    /**保存long类型值
     * @param key
     * @param value
     * @return
     */
    public static boolean saveLongValue(String key,long value) {

        if(isUncreate()){
            return false;
        }

        editor.putLong(key,value);
        return editor.commit();
    }

    /**读取long类型值
     * @param key
     * @param defValue
     * @return
     */
    public static long getLongValue(String key,long defValue) {
        if(isUncreate()){
            return 0;
        }

        long value = sharedPreferences.getLong(key,defValue);
        return value;
    }

    /**保存float类型值
     * @param key
     * @param value
     * @return
     */
    public static boolean saveFloatValue(String key,float value){
        if(isUncreate()){
            return false;
        }

        editor.putFloat(key,value);
        return editor.commit();
    }


    /**读取float类型值
     * @param key
     * @param defValue
     * @return
     */
    public static float getFloatValue(String key,float defValue) {
        if(isUncreate()) {
            return  0;
        }

        float value = sharedPreferences.getFloat(key,defValue);
        return value;
    }


    /**保存布尔值
     * @param key
     * @param value
     * @return
     */
    public static boolean saveBooleanValue(String key,boolean value) {
        if(isUncreate()) {
            return  false;
        }

        editor.putBoolean(key,value);
        return editor.commit();
    }

    /**读取boolean值
     * @param key
     * @param defValue
     * @return
     */
    public static boolean getBooleanValue(String key, boolean defValue) {
        if(isUncreate()) {
            return false;
        }

        boolean value = sharedPreferences.getBoolean(key,defValue);
        return value;
    }


    /**清空数据
     * @return
     */
    public static  boolean deleteAllValues() {
        if(isUncreate()) {
            return false;
        }

        editor.clear();
        return editor.commit();
    }


    /**删除某个键值对
     * @param key
     * @return
     */
    public static boolean remove(String key) {
        if(isUncreate()) {
            return false;
        }

        editor.remove(key);

        return editor.commit();
    }


}
