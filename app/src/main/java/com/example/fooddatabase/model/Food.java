package com.example.fooddatabase.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Food implements Serializable {

    String label;
    String carbs;
    String protien;
    String fat;
    String ingredients;
    String URL;
    ArrayList<String> dietLabel;
    String Tfat;
    String Cusine;
    String Dish;

    public Food(String label, String carbs, String protien, String fat, String ingredients, String URL,ArrayList<String> dietLabel,String Tfat,String Cusine,String Dish) {
        this.label = label;
        this.carbs = carbs;
        this.protien = protien;
        this.fat = fat;
        this.ingredients = ingredients;
        this.URL = URL;
        this.dietLabel = dietLabel;
        this.Tfat = Tfat;
        this.Cusine = Cusine;
        this.Dish = Dish;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCarbs() {
        return carbs;
    }

    public void setCarbs(String carbs) {
        this.carbs = carbs;
    }

    public String getProtien() {
        return protien;
    }

    public void setProtien(String protien) {
        this.protien = protien;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public ArrayList<String> getDietLabel() {
        return dietLabel;
    }

    public void setDietLabel(ArrayList<String> dietLabel) {
        this.dietLabel = dietLabel;
    }

    public String getTfat() {
        return Tfat;
    }

    public void setTfat(String tfat) {
        Tfat = tfat;
    }

    public String getCusine() {
        return Cusine;
    }

    public void setCusine(String cusine) {
        Cusine = cusine;
    }

    public String getDish() {
        return Dish;
    }

    public void setDish(String dish) {
        Dish = dish;
    }
}
