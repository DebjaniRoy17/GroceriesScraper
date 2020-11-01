package com.sainsburys.grocery.scraperapp.product.service;

import com.sainsburys.grocery.scraperapp.product.model.ProductModel;
import com.sainsburys.grocery.scraperapp.product.model.TotalModel;

import java.util.List;

public interface CalculateTotalService {

    TotalModel calculateTotalAmount(List<ProductModel> productModelList);
}
