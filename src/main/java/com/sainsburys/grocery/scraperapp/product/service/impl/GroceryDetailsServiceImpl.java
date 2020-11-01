package com.sainsburys.grocery.scraperapp.product.service.impl;

import com.sainsburys.grocery.scraperapp.product.model.GroceryModel;
import com.sainsburys.grocery.scraperapp.product.service.CalculateTotalService;
import com.sainsburys.grocery.scraperapp.product.service.GroceryDetailsService;
import com.sainsburys.grocery.scraperapp.product.service.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class GroceryDetailsServiceImpl implements GroceryDetailsService {


    @Autowired
    private ProductDetailService productDetailService;

    @Autowired
    private CalculateTotalService calculateTotalService;


    @Override
    public GroceryModel getGroceryDetails(String websiteUrl) {
        GroceryModel groceryModel = new GroceryModel();
        Optional.of(websiteUrl)
                .map(url -> productDetailService.getAllProductDetails(url))
                .ifPresent(productModels -> {
                    groceryModel.setProductModelList(productModels);
                    groceryModel.setTotalModel(calculateTotalService.calculateTotalAmount(productModels));
                });
        return groceryModel;

    }


}
