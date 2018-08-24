package com.example.msp.healthysport.entity;

public class TodayInfo {
    private String windSpeed;//风速 km/h
    private String windDirect;//风向
    private String windPower;//风力
    private String humidity;//温度
    private String info;//天气
    private String temperature;//温度
    private String date;//日期
    private String city;//城市
    private String week;//周几
    private String moon;//农历



    private String quality;//空气质量


    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getQuality() {
        return quality;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setWindDirect(String windDirect) {
        this.windDirect = windDirect;
    }

    public void setWindPower(String windPower) {
        this.windPower = windPower;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public void setMoon(String moon) {
        this.moon = moon;
    }

    public String getWindSpeed() {
        return windSpeed;

    }

    public String getWindDirect() {
        return windDirect;
    }

    public String getWindPower() {
        return windPower;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getInfo() {
        return info;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getDate() {
        return date;
    }

    public String getCity() {
        return city;
    }

    public String getWeek() {
        return week;
    }

    public String getMoon() {
        return moon;
    }
}
