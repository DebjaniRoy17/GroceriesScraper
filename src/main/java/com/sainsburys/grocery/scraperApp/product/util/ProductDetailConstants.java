package com.sainsburys.grocery.scraperApp.product.util;

public abstract class ProductDetailConstants {

    public static final String websiteUrl = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";
    public static final String productNameLinksXPath = "//div[@class='productNameAndPromotions']";
    public static final String anchorXPath = ".//a[contains(@href,websiteUrl)]";
    public static final String htmlProductTitleXPath = "//div[@class='productTitleDescriptionContainer']/h1";
    public static final String htmlProductCalorieXPath = "//tr[@class='tableRow0']/td";
    public static final String htmlProductCalorieXPathAlt = "//table[@class='nutritionTable']/tbody/tr/td";
    public static final String htmlPricePerUnitXPath = "//p[@class='pricePerUnit']";
    public static final String htmlProductDescriptionXPath = "//div[@class='productText']/p";
    public static final String htmlProductDescriptionXPathAlt = "//div[@class='itemTypeGroupContainer productText']/div[@class='memo']/p";
    public static final String htmlProductDescriptionXPathAlt2 = "//div[@class='itemTypeGroup']/p";
    public static final String htmlItemCodeXPath = "//p[@class='itemCode']";
}
