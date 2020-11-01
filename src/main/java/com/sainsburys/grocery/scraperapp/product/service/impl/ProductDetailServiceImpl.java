package com.sainsburys.grocery.scraperapp.product.service.impl;

import com.sainsburys.grocery.scraperapp.product.model.ProductModel;
import com.sainsburys.grocery.scraperapp.product.service.ProductDetailService;
import com.sainsburys.grocery.scraperapp.product.util.ProductDetailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductDetailServiceImpl implements ProductDetailService {

    @Autowired
    private ProductDetailUtil productDetailUtil;


    @Override
    public List<ProductModel> getAllProductDetails(String websiteUrl) {
        return Optional.of(websiteUrl)
                .map(url -> productDetailUtil.getProductListsFromWebsite(url))
                .map(productNameLinks -> productNameLinks.stream()
                        .map(this::getDetailsOfEachProduct)
                        .collect(Collectors.toList()))
                .orElseGet(ArrayList::new);

    }

    public ProductModel getDetailsOfEachProduct(String productNameLink) {
        return productDetailUtil.getDetailsOfEachProduct(productNameLink);
    }
}
