package com.sainsburys.grocery.scraperapp.product.service.impl;

import com.sainsburys.grocery.scraperapp.product.model.ProductModel;
import com.sainsburys.grocery.scraperapp.product.model.TotalModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CalculateTotalServiceImplTest {

    @Autowired
    CalculateTotalServiceImpl calculateTotalService;

    @InjectMocks
    CalculateTotalServiceImpl calculateTotalServiceMock;

    private ProductModel productModel;
    private ProductModel productModel1;
    private ProductModel productModel2;

    @BeforeEach
    public void setup() {
        calculateTotalServiceMock = Mockito.mock(CalculateTotalServiceImpl.class);
        //Given
        productModel = new ProductModel(7555699, "Sainsbury's Strawberries 400g", 1.75, 33, "by Sainsbury's strawberries");
        productModel1 = new ProductModel(7555700, "Sainsbury's Cherries 400g", 1.6500, 49, "by Sainsbury's Cherries");
        productModel2 = new ProductModel(7555701, "Sainsbury's Blueberries 400g", 2.3698, 24, "by Sainsbury's Blueberries");
    }

    @Test
    @DisplayName("Test calculate Vat for 3 Products")
    public void testCalculateVat() {
        //Givne
        List<ProductModel> productModelList = new ArrayList<>();
        productModelList.add(productModel);
        productModelList.add(productModel1);
        productModelList.add(productModel2);
        //When
        double actualVatAmount = calculateTotalService.calculateVat(productModelList);
        double expectedVatAmount = 1.15;
        //Then
        Assertions.assertEquals(expectedVatAmount, actualVatAmount);
    }

    @Test
    @DisplayName("Test calculate Gross for 3 Products")
    public void testCalculateGross() {
        //Given
        List<ProductModel> productModelList = new ArrayList<>();
        productModelList.add(productModel);
        productModelList.add(productModel1);
        productModelList.add(productModel2);
        //When
        double actualGrossAmount = calculateTotalService.calculateGross(productModelList);
        double expectedGrossAmount = 5.77;
        //Then
        Assertions.assertEquals(expectedGrossAmount, actualGrossAmount);
    }

    @Test
    @DisplayName("Test calculate Gross when unit price is incorrect")
    public void testCalculateGross_whenUnitPriceIsIncorrect() {
        //Given
        productModel.setUnitPrice(null);
        List<ProductModel> productModelList = new ArrayList<>();
        productModelList.add(productModel);
        productModelList.add(productModel1);
        productModelList.add(productModel2);
        //When,Then
        Assertions.assertEquals(4.02, calculateTotalService.calculateGross(productModelList));

    }

    @Test
    @DisplayName("Test calculate vat when unit price is incorrect")
    public void testCalculateVat_whenUnitPriceIsIncorrect() {
        //Given
        productModel.setUnitPrice(null);
        List<ProductModel> productModelList = new ArrayList<>();
        productModelList.add(productModel);
        productModelList.add(productModel1);
        productModelList.add(productModel2);
        //When,Then
        Assertions.assertEquals(0.8, calculateTotalService.calculateVat(productModelList));

    }

    @Test
    @DisplayName("Test populate Total Model when Gross and Vat are already calculated")
    public void testCalculateTotalAmount() {
        //Given
        TotalModel expectedTotalModel = new TotalModel(5.77, 1.15);
        List<ProductModel> productModelList = new ArrayList<>();
        productModelList.add(productModel);
        productModelList.add(productModel1);
        productModelList.add(productModel2);
        //When
        when(calculateTotalServiceMock.calculateGross(any())).thenReturn(1.15);
        when(calculateTotalServiceMock.calculateVat(any())).thenReturn(5.77);

        //Then
        TotalModel actualTotalModel = calculateTotalService.calculateTotalAmount(productModelList);
        Assertions.assertEquals(expectedTotalModel.getGross(), actualTotalModel.getGross());
        Assertions.assertEquals(expectedTotalModel.getVat(), actualTotalModel.getVat());

    }


}
