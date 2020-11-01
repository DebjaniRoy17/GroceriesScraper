package com.sainsburys.grocery.scraperapp.product.util;

public abstract class ProductDetailConstants {

    private ProductDetailConstants() {
    }

    public static final String WEBSITE_URL = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";
    public static final String PRODUCT_NAME_LINKS_XPATH = "//div[@class='productNameAndPromotions']";
    public static final String ANCHOR_XPATH = ".//a[contains(@href,websiteUrl)]";
    public static final String HTML_PRODUCT_TITLE_XPATH = "//div[@class='productTitleDescriptionContainer']/h1";
    public static final String HTML_PRODUCT_CALORIE_XPATH = "//tr[@class='tableRow0']/td";
    public static final String HTML_PRODUCT_CALORIE_XPATH_ALT = "//table[@class='nutritionTable']/tbody/tr";
    public static final String HTML_PRODUCT_CALORIE_TD_XPATH_ALT = "//table[@class='nutritionTable']/tbody/tr[2]/td[1]";
    public static final String HTML_PRICE_PER_UNIT_XPATH = "//p[@class='pricePerUnit']";
    public static final String HTML_PRODUCT_DESCRIPTION_XPATH = "//div[@class='productText']/p";
    public static final String HTML_PRODUCT_DESCRIPTION_XPATH_ALT = "//div[@class='itemTypeGroupContainer productText']/div[@class='memo']/p";
    public static final String HTML_PRODUCT_DESCRIPTION_XPATH_ALT_2 = "//div[@class='itemTypeGroup']/p[2]";
    public static final String HTML_ITEM_CODE_XPATH = "//p[@class='itemCode']";
}
