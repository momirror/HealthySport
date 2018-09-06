package com.example.msp.healthysport.model;

import java.util.List;

public class FoodType {


    private String foodType;
    private List<FoodMessage> foodList;

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public void setFoodList(List<FoodMessage> foodList) {
        this.foodList = foodList;
    }

    public List<FoodMessage> getFoodList() {
        return foodList;
    }



}
