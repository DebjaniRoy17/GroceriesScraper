package com.sainsburys.grocery.scraperapp.product.util;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.sainsburys.grocery.scraperapp.product.model.ProductModel;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductDetailUtilTest {

    @Autowired
    ProductDetailUtil productDetailUtil;

    @InjectMocks
    private ProductDetailUtil productDetailUtilMock;

    private String mainPageUrl;
    private HtmlPage mainPage;

    private HtmlPage productPage;
    private String productPageUrl;

    private String blankPageUrl;
    private HtmlPage blankPage;

    private String altKCalPageUrl;
    private HtmlPage altKCalPage;

    private String altDescPageUrl;
    private HtmlPage altDescPage;

    private String altDescTwoPageUrl;
    private HtmlPage altDescTwoPage;

    @BeforeAll
    public void setUp() {
        //Given
        productDetailUtilMock = Mockito.mock(ProductDetailUtil.class);

        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        try {
            mainPageUrl = this.getClass().getResource("/htmlPage.html").toString();
            mainPage = client.getPage(mainPageUrl);

            productPageUrl = this.getClass().getResource("/productPage.html").toString();
            productPage = client.getPage(productPageUrl);

            blankPageUrl = this.getClass().getResource("/blankPage.html").toString();
            blankPage = client.getPage(blankPageUrl);

            altKCalPageUrl = this.getClass().getResource("/altKCalPage.html").toString();
            altKCalPage = client.getPage(altKCalPageUrl);

            altDescPageUrl = this.getClass().getResource("/altDescPage.html").toString();
            altDescPage = client.getPage(altDescPageUrl);

            altDescTwoPageUrl = this.getClass().getResource("/altDescTwoPage.html").toString();
            altDescTwoPage = client.getPage(altDescTwoPageUrl);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            client.close();
        }
    }

    @Test
    @DisplayName("Test Get Page when Website url is null")
    public void testGetPageFromWebsite_whenUrlIsNull_thenReturnsNull() {
        Assertions.assertNull(productDetailUtil.getHtmlPageFromWebsiteUrl(null));
    }

    @Test
    @DisplayName("Test Get Page when Website url is proper")
    public void testGetPageFromWebsite_whenUrlIsProper_thenReturnsHtmlPage() {
        Assertions.assertNotNull(productDetailUtil.getHtmlPageFromWebsiteUrl(mainPageUrl));
    }

    @Test
    @DisplayName("Test Get Product List with product link xpath")
    public void testGetProductListsFromWebsite_whenLinkXpathIsMentioned_thenReturnProductList() {
        //When
        doReturn(mainPage).when(productDetailUtilMock).getHtmlPageFromWebsiteUrl(any());
        List<String> productListsFromWebsite = productDetailUtil.getProductListsFromWebsite(mainPageUrl);
        //Then
        Assertions.assertNotNull(productListsFromWebsite);
        Assertions.assertEquals("../../../../../../shop/gb/groceries/berries-cherries-currants/sainsburys-british-strawberries-400g.html", productListsFromWebsite.get(0));
        Assertions.assertEquals(17, productListsFromWebsite.size());
    }

    @Test
    @DisplayName("Test Get Title of Product when title is not present")
    public void testGetTitle_whenProductPageIsIncorrect_thenReturnNull() {
        assertNull(productDetailUtil.getTitleOfProduct(blankPage));
    }

    @Test
    @DisplayName("Test Get Title of Product when product page is correct")
    public void testGetTitle_whenProductPageGiven_thenReturnTitle() {
        String actualTitle = productDetailUtil.getTitleOfProduct(productPage);
        assertNotNull(actualTitle);
        assertEquals("Sainsbury's Blueberries 200g", actualTitle);

    }

    @Test
    @DisplayName("Test Get kcal of Product when kCal value is not present")
    public void testGetCalories_whenkcalIsNotPresent_thenReturnNull() {
        assertNull(productDetailUtil.getCaloriesOfProduct(blankPage));
    }

    @Test
    @DisplayName("Test Get kcal of Product when page contains basic Xpath")
    public void testGetCalories_whenProductPageGiven_thenReturnKCal() {
        Integer actualCalories = productDetailUtil.getCaloriesOfProduct(productPage);
        assertNotNull(actualCalories);
        assertEquals(45, (int) actualCalories);

    }

    @Test
    @DisplayName("Test Get kcal of Product when Product page contains Alternate xpath")
    public void testGetCalories_whenAltXPath_thenReturnKCal() {
        Integer actualCalories = productDetailUtil.getCaloriesOfProduct(altKCalPage);
        assertNotNull(actualCalories);
        assertEquals(52, (int) actualCalories);

    }

    @Test
    @DisplayName("Test Get Unit Price of Product when unit price is not present")
    public void testGetUnitPrice_whenProductPageIsIncorrect_thenReturnNull() {
        assertNull(productDetailUtil.getUnitPriceOfProduct(blankPage));
    }

    @Test
    @DisplayName("Test Get Unit Price of Product when page contains basic Xpath")
    public void testGetUnitPrice_whenProductPageGiven_thenReturnUnitPrice() {
        Double actualUnitPrice = productDetailUtil.getUnitPriceOfProduct(productPage);
        assertNotNull(actualUnitPrice);
        assertEquals(1.75, actualUnitPrice, 0.0);
    }

    @Test
    @DisplayName("Test Get Description of Product when description is not present")
    public void testGetDescription_whenProductPageIsIncorrect_thenReturnNull() {
        assertNull(productDetailUtil.getDescriptionOfProduct(blankPage));
    }

    @Test
    @DisplayName("Test Get Description of Product when page contains basic Xpath")
    public void testGetDescription_whenProductPageGiven_thenReturnDescription() {
        String actualDescription = productDetailUtil.getDescriptionOfProduct(productPage);
        assertNotNull(actualDescription);
        assertTrue("by Sainsbury's blueberries".equalsIgnoreCase(actualDescription));
    }

    @Test
    @DisplayName("Test Get Description of Product when page contains Alternate 1 Xpath")
    public void testGetDescription_whenAltXPath_thenReturnDescription() {
        String actualDescription = productDetailUtil.getDescriptionOfProduct(altDescPage);
        assertNotNull(actualDescription);
        assertTrue("British Cherry & Strawberry Mixed Pack".equalsIgnoreCase(actualDescription));
    }

    @Test
    @DisplayName("Test Get Description of Product when page contains Alternate 2 Xpath")
    public void testGetDescription_whenAltTwoXPath_thenReturnDescription() {
        String actualDescription = productDetailUtil.getDescriptionOfProduct(altDescTwoPage);
        assertNotNull(actualDescription);
        assertTrue("Union Flag".equalsIgnoreCase(actualDescription));
    }

    @Test
    @DisplayName("Test Get Item Code of Product when description is not present")
    public void testGetItemCode_whenProductPageIsIncorrect_thenReturnNull() {
        assertNull(productDetailUtil.getItemCodeOfProduct(blankPage));
    }

    @Test
    @DisplayName("Test Get Item Code of Product when page contains basic Xpath")
    public void testGetItemCode_whenProductPageGiven_thenReturnItemCode() {
        Integer actualItemCode = productDetailUtil.getItemCodeOfProduct(productPage);
        assertNotNull(actualItemCode);
        assertEquals(7555404, (int) actualItemCode);
    }

    @Test
    @DisplayName("Test Get Details Of each Product Content from Product link")
    public void testGetProductDetails_whenProductLinkIsGiven_thenReturnProductModel() {
        doReturn(productPage).when(productDetailUtilMock).getHtmlPageFromWebsiteUrl(any());

        when(productDetailUtilMock.getTitleOfProduct(any())).thenReturn("Sainsbury's Blueberries 200g");
        when(productDetailUtilMock.getCaloriesOfProduct(any())).thenReturn(45);
        when(productDetailUtilMock.getUnitPriceOfProduct(any())).thenReturn(1.75);
        when(productDetailUtilMock.getDescriptionOfProduct(any())).thenReturn("by Sainsbury's blueberries");

        ProductModel expectedProductModel = new ProductModel();
        expectedProductModel.setItemCode(7555404);
        expectedProductModel.setTitle("Sainsbury's Blueberries 200g");
        expectedProductModel.setCalories(45);
        expectedProductModel.setUnitPrice(1.75);
        expectedProductModel.setDescription("by Sainsbury's blueberries");

        ProductModel actualProductModel = productDetailUtil.getDetailsOfEachProduct("../../../../../../shop/gb/groceries/berries-cherries-currants/sainsburys-blueberries-200g.html");
        assertEquals(expectedProductModel.getItemCode(), actualProductModel.getItemCode());
        assertEquals(expectedProductModel.getTitle(), actualProductModel.getTitle());
        assertEquals(expectedProductModel.getCalories(), actualProductModel.getCalories());
        assertEquals(expectedProductModel.getUnitPrice(), actualProductModel.getUnitPrice());
        assertEquals(expectedProductModel.getDescription(), actualProductModel.getDescription());

    }

}