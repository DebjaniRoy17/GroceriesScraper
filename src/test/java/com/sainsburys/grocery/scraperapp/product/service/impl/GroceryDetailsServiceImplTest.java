package com.sainsburys.grocery.scraperapp.product.service.impl;

import com.sainsburys.grocery.scraperapp.product.model.GroceryModel;
import com.sainsburys.grocery.scraperapp.product.model.ProductModel;
import com.sainsburys.grocery.scraperapp.product.model.TotalModel;
import com.sainsburys.grocery.scraperapp.product.service.CalculateTotalService;
import com.sainsburys.grocery.scraperapp.product.service.ProductDetailService;
import com.sainsburys.grocery.scraperapp.product.util.ProductDetailConstants;
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
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GroceryDetailsServiceImplTest {

    @Autowired
    GroceryDetailsServiceImpl groceryDetailsService;

    @MockBean
    private ProductDetailUtil productDetailUtil;
    @MockBean
    private ProductDetailService productDetailService;
    @MockBean
    private CalculateTotalService calculateTotalService;

    private ProductModel productModel;
    private ProductModel productModel1;
    private ProductModel productModel2;

    private TotalModel totalModel;

    private GroceryModel groceryModel;

    private List<String> productNameList;

    @BeforeEach
    public void setup() {
        //Given
        productModel = new ProductModel(7555699, "Sainsbury's Strawberries 400g", 1.75, 33, "by Sainsbury's strawberries");
        productModel1 = new ProductModel(7555700, "Sainsbury's Cherries 400g", 1.6500, 49, "by Sainsbury's Cherries");
        productModel2 = new ProductModel(7555701, "Sainsbury's Blueberries 400g", 2.3698, 24, "by Sainsbury's Blueberries");

        totalModel = new TotalModel(5.77, 1.15);

        List<ProductModel> productModelList = new ArrayList<>();
        productModelList.add(new ProductModel(7555702, "Sainsbury's Strawberries 400g", 1.65, 33, "by Sainsbury's strawberries"));
        productModelList.add(new ProductModel(7555703, "Sainsbury's Strawberries 400g", 1.75, 33, "by Sainsbury's strawberries"));
        productModelList.add(new ProductModel(7555699, "Sainsbury's Strawberries 400g", 2.37, 33, "by Sainsbury's strawberries"));
        groceryModel = new GroceryModel(productModelList, totalModel);

        productNameList = new ArrayList<>();
        productNameList.add("Product1Link");
        productNameList.add("Product2Link");
        productNameList.add("Product3Link");
    }

    @Test
    @DisplayName("Test GroceryDetails with the given Website Url")
    public void testGetGroceryDetails() {

        //Given
        List<ProductModel> productModelList = new ArrayList<>();
        productModelList.add(productModel);
        productModelList.add(productModel1);
        productModelList.add(productModel2);

        //When
        when(productDetailService.getAllProductDetails(any())).thenReturn(productModelList);
        when(calculateTotalService.calculateTotalAmount(any())).thenReturn(totalModel);

        GroceryModel actualGroceryModel = groceryDetailsService.getGroceryDetails(ProductDetailConstants.WEBSITE_URL);

        //Then
        Assertions.assertEquals(groceryModel.getTotalModel().getGross(), actualGroceryModel.getTotalModel().getGross());
        Assertions.assertEquals(groceryModel.getProductModelList().get(0).getCalories(), actualGroceryModel.getProductModelList().get(0).getCalories());
    }


}
