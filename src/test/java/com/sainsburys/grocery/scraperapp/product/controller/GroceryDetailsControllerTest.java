package com.sainsburys.grocery.scraperapp.product.controller;

import com.sainsburys.grocery.scraperapp.product.model.GroceryModel;
import com.sainsburys.grocery.scraperapp.product.model.ProductModel;
import com.sainsburys.grocery.scraperapp.product.model.TotalModel;
import com.sainsburys.grocery.scraperapp.product.service.GroceryDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GroceryDetailsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private GroceryDetailsService groceryDetailsService;

    private ProductModel productModel;
    private ProductModel productModel1;
    private ProductModel productModel2;

    private TotalModel totalModel;

    private GroceryModel groceryModel;

    @BeforeEach
    public void setup() {
        // Given
        totalModel = new TotalModel(5.77, 1.15);

        productModel = new ProductModel(7555699, "Sainsbury's Strawberries 400g", 1.75, 33, "by Sainsbury's strawberries");
        productModel1 = new ProductModel(7555700, "Sainsbury's Cherries 400g", 1.6500, 49, "by Sainsbury's Cherries");
        productModel2 = new ProductModel(7555701, "Sainsbury's Blueberries 400g", 2.3698, 24, "by Sainsbury's Blueberries");

        List<ProductModel> productModelList = new ArrayList<>();
        productModelList.add(productModel);
        productModelList.add(productModel1);
        productModelList.add(productModel2);
        groceryModel = new GroceryModel(productModelList, totalModel);
    }

    @Test
    @DisplayName("Test Get Mapping request to fetch Grocery details")
    public void testGetGroceryDetails() throws Exception {
        //When
        when(groceryDetailsService.getGroceryDetails(any())).thenReturn(groceryModel);
        //Then
        mockMvc.perform(MockMvcRequestBuilders.get("/getGroceryDetails", 0))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(header().string(HttpHeaders.LOCATION, "/getGroceryDetails"))
                .andExpect(jsonPath("$['total']['gross']", is(5.77)))
                .andExpect(jsonPath("$['results'][0]['unit_price']", is(1.75)));

    }

    @Test
    @DisplayName("Test GetMapping when data from website not found")
    public void testGetGroceryDetails_whenDataNotFound() throws Exception {
        //When
        when(groceryDetailsService.getGroceryDetails(any())).thenReturn(new GroceryModel());
        //Then
        mockMvc.perform(MockMvcRequestBuilders.get("/getGroceryDetails", 0))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test GetMapping when kcal_per_100g is NOT present")
    public void testGroceryDetails_whenCalorieIsNull() throws Exception {
        //Given
        productModel.setCalories(null);
        //When
        when(groceryDetailsService.getGroceryDetails(any())).thenReturn(groceryModel);
        //Then
        mockMvc.perform(MockMvcRequestBuilders.get("/getGroceryDetails", 0))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(header().string(HttpHeaders.LOCATION, "/getGroceryDetails"))
                .andExpect(jsonPath("$['total']['gross']", is(5.77)))
                .andExpect(jsonPath("$['results'][0]['kcal_per_100g']").doesNotExist())
                .andExpect(jsonPath("$['results'][1]['kcal_per_100g']").exists())
                .andExpect(jsonPath("$['results'][2]['kcal_per_100g']").exists());


    }

}
