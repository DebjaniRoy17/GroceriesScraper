package com.sainsburys.grocery.scraperapp.product.service.impl;

import com.sainsburys.grocery.scraperapp.product.model.ProductModel;
import com.sainsburys.grocery.scraperapp.product.model.TotalModel;
import com.sainsburys.grocery.scraperapp.product.service.CalculateTotalService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalculateTotalServiceImpl implements CalculateTotalService {


    public double calculateGross(List<ProductModel> productModels) {
        return productModels.stream()
                .mapToDouble(ProductModel::getUnitPrice)
                .sum();
    }

    public double calculateVat(List<ProductModel> productModels) {
        return productModels.stream()
                .mapToDouble(ProductModel::getVat)
                .sum();
    }

    public TotalModel calculateTotalAmount(List<ProductModel> productModelList) {
        TotalModel totalModel = new TotalModel();
        totalModel.setGross(calculateGross(productModelList));
        totalModel.setVat(calculateVat(productModelList));
        return totalModel;
    }
}
