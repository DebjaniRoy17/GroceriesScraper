package com.sainsburys.grocery.scraperApp.product.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductModel {

    @JsonIgnore
    private Integer itemCode;

    @JsonProperty(value = "title")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String title;

    @JsonProperty(value = "kcal_per_100g")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer calories;

    @JsonProperty(value = "unit_price")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double unitPrice;

    @JsonProperty(value = "description")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;

    @JsonIgnore
    private double vat;

    public ProductModel() {
    }

    public ProductModel(Integer itemCode, String title, double unitPrice, Integer calories, String description) {
        this.itemCode = itemCode;
        this.title = title;
        this.unitPrice = unitPrice;
        this.calories = calories;
        this.description = description;
    }

    public Integer getItemCode() {
        return itemCode;
    }

    public void setItemCode(Integer itemCode) {
        this.itemCode = itemCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getUnitPrice() {
        return Double.parseDouble(String.format("%.2f", unitPrice));
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getVat() {
        return Double.parseDouble(String.format("%.2f", this.unitPrice*0.2));
    }

}
