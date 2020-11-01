package com.sainsburys.grocery.scraperapp.product.service;

import com.sainsburys.grocery.scraperapp.product.model.GroceryModel;

public interface GroceryDetailsService {

    GroceryModel getGroceryDetails(String url);
}
