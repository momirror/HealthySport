package com.example.msp.healthysport.utils;

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
        strUrl = "http://op.juhe.cn/onebox/weather/query?cityname=%E5%8C%97%E4%BA%AC&key=06ba330de85cf5484fedbcd1c2247e28";
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




























}
