package com.sainsburys.grocery.scraperapp.product.service;

import com.sainsburys.grocery.scraperapp.product.model.ProductModel;

import java.util.List;

public interface ProductDetailService {

    List<ProductModel> getAllProductDetails(String websiteUrl);

}
