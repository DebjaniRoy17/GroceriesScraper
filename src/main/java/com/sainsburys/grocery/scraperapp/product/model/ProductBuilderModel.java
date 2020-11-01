package com.sainsburys.grocery.scraperapp.product.model;

public class ProductBuilderModel {

    private String productNameLink;
    private Integer itemCode;
    private String title;
    private Double unitPrice;
    private Integer calories;
    private String description;

    public ProductBuilderModel(String productNameLink) {
        this.productNameLink = productNameLink;
    }

    public String getProductNameLink() {
        return productNameLink;
    }

    public void setProductNameLink(String productNameLink) {
        this.productNameLink = productNameLink;
    }

    public ProductBuilderModel setItemCode(Integer itemCode) {
        this.itemCode = itemCode;
        return this;
    }

    public ProductBuilderModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public ProductBuilderModel setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public ProductBuilderModel setCalories(Integer calories) {
        this.calories = calories;
        return this;
    }

    public ProductBuilderModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public ProductModel build() {
        return  new ProductModel(itemCode,title,unitPrice,calories,description);
    }
}
