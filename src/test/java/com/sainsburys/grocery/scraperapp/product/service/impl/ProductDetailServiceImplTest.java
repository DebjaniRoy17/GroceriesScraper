package com.sainsburys.grocery.scraperapp.product.service.impl;

import com.sainsburys.grocery.scraperapp.product.model.ProductModel;
import com.sainsburys.grocery.scraperapp.product.util.ProductDetailUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductDetailServiceImplTest {

    @Autowired
    ProductDetailServiceImpl productDetailService;

    @MockBean
    ProductDetailUtil productDetailUtil;

    private ProductModel productModel;
    private List<String> productNameList;

    private String mainPageUrl;

    @BeforeEach
    public void setup() {
        mainPageUrl = this.getClass().getResource("/htmlPage.html").toString();
        //Given
        productModel = new ProductModel(7555699, "Sainsbury's Strawberries 400g", 1.75, 33, "by Sainsbury's strawberries");

        productNameList = new ArrayList<>();
        productNameList.add("Product1Link");
        productNameList.add("Product2Link");
        productNameList.add("Product3Link");
    }

    @Test
    @DisplayName("Test Get Details of Each product when product link is given")
    public void testGetDetailsOfEachProduct_whenProductLinkIsGiven() {
        //When
        when(productDetailUtil.getDetailsOfEachProduct(any())).thenReturn(productModel);
        //Then
        ProductModel actualProductModel = productDetailService.getDetailsOfEachProduct("<<productNameLink>>");
        Assertions.assertEquals(productModel.getCalories(), actualProductModel.getCalories());
        Assertions.assertEquals(productModel.getTitle(), actualProductModel.getTitle());
        Assertions.assertEquals(productModel.getUnitPrice(), actualProductModel.getUnitPrice());
        Assertions.assertEquals(productModel.getDescription(), actualProductModel.getDescription());

    }

    @Test
    @DisplayName("Test Get List of all Products & populate the Product Model from website Url")
    public void testGetAllProduct_whenWebsiteUrlIsGiven() {

        //When
        when(productDetailUtil.getProductListsFromWebsite(anyString())).thenReturn(productNameList);
        when(productDetailUtil.getDetailsOfEachProduct(anyString())).thenReturn(productModel);

        //Then
        List<ProductModel> actualProductModelList = productDetailService.getAllProductDetails(mainPageUrl);
        List<ProductModel> expectedProductModelList = new ArrayList<>();
        expectedProductModelList.add(productModel);
        Assertions.assertEquals(expectedProductModelList.get(0).getTitle(), actualProductModelList.get(0).getTitle());

    }
}
