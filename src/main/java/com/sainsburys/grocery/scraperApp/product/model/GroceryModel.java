package com.sainsburys.grocery.scraperApp.product.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GroceryModel {

    @JsonProperty(value = "results")
    private List<ProductModel> productModelList;
    @JsonProperty(value = "total")
    private TotalModel totalModel;

    public GroceryModel() {
    }

    public GroceryModel(List<ProductModel> productModelList, TotalModel totalModel) {
        this.productModelList = productModelList;
        this.totalModel = totalModel;
    }

    public List<ProductModel> getProductModelList() {
        return productModelList;
    }

    public void setProductModelList(List<ProductModel> productModelList) {
        this.productModelList = productModelList;
    }

    public TotalModel getTotalModel() {
        return totalModel;
    }

    public void setTotalModel(TotalModel totalModel) {
        this.totalModel = totalModel;
    }
}

