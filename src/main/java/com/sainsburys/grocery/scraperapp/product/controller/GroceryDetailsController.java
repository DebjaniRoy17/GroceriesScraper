package com.sainsburys.grocery.scraperapp.product.controller;

import com.sainsburys.grocery.scraperapp.product.model.GroceryModel;
import com.sainsburys.grocery.scraperapp.product.service.GroceryDetailsService;
import com.sainsburys.grocery.scraperapp.product.util.ProductDetailConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
public class GroceryDetailsController {

    @Autowired
    private GroceryDetailsService groceryDetailsService;

    @GetMapping(value = "/getGroceryDetails", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GroceryModel> getGroceryDetails() throws URISyntaxException {


        Optional<GroceryModel> groceryDetails = Optional.of(groceryDetailsService.getGroceryDetails(ProductDetailConstants.WEBSITE_URL));

        return groceryDetails.map(GroceryModel::getProductModelList).isPresent() ?
                ResponseEntity
                        .ok()
                        .location(new URI("/getGroceryDetails"))
                        .body(groceryDetails.get())
                : ResponseEntity.notFound().build();


    }
}
