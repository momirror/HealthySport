package com.example.msp.healthysport.utils;

import android.text.TextUtils;

import com.example.msp.healthysport.entity.TodayInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUtils {
    private static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
    private static final int DEF_CONNE_TIMEOUT = 30000;
    private static final int DEF_READ_TIMEOUT = 30000;
    private static final String DEF_CHATSET = "UTF-8";
    private static final String HUMIDITY = "%rh";//温度单位
    private static final String TEMPERATURE = "°C";// 温度单位

    /*get*/
    public static String getData(String strUrl) {

        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String rs = null;

        try {
            StringBuffer sb = new StringBuffer();
            URL url = new URL(strUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-agent",userAgent);
            connection.setUseCaches(false);
            connection.setConnectTimeout(DEF_CONNE_TIMEOUT);
            connection.setReadTimeout(DEF_READ_TIMEOUT);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("GET");
            connection.connect();

            if(connection.getResponseCode() == 200){
                InputStream is = connection.getInputStream();
                reader  = new BufferedReader(new InputStreamReader(is,DEF_CHATSET));
                String strReead = null;
                while ((strReead = reader.readLine()) != null) {
                    sb.append(strReead);
                }

                rs = sb.toString();
                return rs;
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(connection != null) {
                connection.disconnect();
            }
        }

        return null;
    }

    public static TodayInfo parseTodayInfo(String str) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            String reason = jsonObject.getString("reason");
            if(TextUtils.equals("查询成功!",reason)) {
                JSONObject result = jsonObject.getJSONObject("result");
                JSONObject data = result.getJSONObject("data");
                JSONObject realtime = data.getJSONObject("realtime");
                JSONObject wind = realtime.getJSONObject("wind");
                JSONObject pm25 = data.getJSONObject("pm25");

                //风
                String windSpeed = wind.getString("windspeed");
                String direct = wind.getString("direct");
                String power = wind.getString("power");

                //天气
                JSONObject weather = realtime.getJSONObject("weather");
                String humidity = weather.getString("humidity");
                String info = weather.getString("info");
                String temperature = weather.getString("temperature");

                //日期，城市，农历
                String date = realtime.getString("date");
                String city = realtime.getString("city_name");
                String week = realtime.getString("week");
                String moon = realtime.getString("moon");

                //空气
                JSONObject subpm25 = pm25.getJSONObject("pm25");
                String quality = subpm25.getString("quality");

                TodayInfo todayInfo = new TodayInfo();
                todayInfo.setWindSpeed(windSpeed);
                todayInfo.setCity(city);
                todayInfo.setDate(date);
                todayInfo.setWindDirect(direct);
                todayInfo.setHumidity(humidity);
                todayInfo.setInfo(info);
                todayInfo.setMoon(moon);
                todayInfo.setWindPower(power);
                todayInfo.setTemperature(temperature);
                todayInfo.setWeek(week);
                todayInfo.setQuality(quality);

                return todayInfo;
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }































}
