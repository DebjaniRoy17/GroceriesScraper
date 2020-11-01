package com.sainsburys.grocery.scraperapp.product.util;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.sainsburys.grocery.scraperapp.product.model.ProductBuilderModel;
import com.sainsburys.grocery.scraperapp.product.model.ProductModel;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


@Component
public class ProductDetailUtil {

    private static final Logger logger = Logger.getLogger(ProductDetailUtil.class.getName());

    public HtmlPage getHtmlPageFromWebsiteUrl(String websiteUrl) {
        HtmlPage htmlPage = null;
        if (Optional.ofNullable(websiteUrl).isPresent()) {
            WebClient client = new WebClient();
            client.getOptions().setCssEnabled(false);
            client.getOptions().setJavaScriptEnabled(false);
            try {
                htmlPage = client.getPage(websiteUrl);
            } catch (MalformedURLException e) {
                logger.log(Level.WARNING, "MalformedURLException : ", e);
            } catch (IOException e) {
                logger.log(Level.WARNING, "IOException : ", e);
            } finally {
                client.close();
            }
        }
        return htmlPage;
    }

    public List<String> getProductListsFromWebsite(String websiteUrl) {
        return Optional.of(websiteUrl)
                .map(this::getHtmlPageFromWebsiteUrl)
                .map(htmlPage -> getHtmlElementListByXPath(htmlPage, ProductDetailConstants.PRODUCT_NAME_LINKS_XPATH))
                .map(this::getListOfHtmlAnchors)
                .orElse(null);
    }

    private List<String> getListOfHtmlAnchors(List<HtmlElement> htmlElementList) {
        return htmlElementList.stream()
                .map(htmlElement -> (List<HtmlAnchor>) htmlElement.getByXPath(ProductDetailConstants.ANCHOR_XPATH))
                .flatMap(Collection::stream)
                .map(HtmlAnchor::getHrefAttribute)
                .collect(Collectors.toList());
    }


    private List<HtmlElement> getHtmlElementListByXPath(HtmlPage htmlPage, String htmlProductTitleXPath) {
        return (List<HtmlElement>) htmlPage.getByXPath(htmlProductTitleXPath);
    }

    public String getTitleOfProduct(HtmlPage productContent) {
        return getHtmlElementListByXPath(productContent, ProductDetailConstants.HTML_PRODUCT_TITLE_XPATH).stream()
                .findAny()
                .map(DomNode::getTextContent)
                .orElse(null);

    }

    public Integer getCaloriesOfProduct(HtmlPage productContent) {

        return getHtmlElementListByXPath(productContent, ProductDetailConstants.HTML_PRODUCT_CALORIE_XPATH).stream()
                .map(DomNode::getTextContent)
                .filter(valueString -> valueString.contains("kcal"))
                .findAny()
                .map(kCalValue -> kCalValue.split("kcal")[0])
                .filter(caloriesInt -> caloriesInt.matches("\\d+"))
                .map(Integer::valueOf)
                .orElseGet(() ->
                        getHtmlElementListByXPath(productContent, ProductDetailConstants.HTML_PRODUCT_CALORIE_XPATH_ALT).stream()
                                .map(DomNode::getTextContent)
                                .anyMatch(valueString -> valueString.contains("kcal")) ?
                                getCaloriesOfProductAltXPath(productContent)
                                : null

                );
    }

    private Integer getCaloriesOfProductAltXPath(HtmlPage productContent) {
        return getHtmlElementListByXPath(productContent, ProductDetailConstants.HTML_PRODUCT_CALORIE_TD_XPATH_ALT).stream()
                .map(DomNode::getTextContent)
                .filter(caloriesInt -> caloriesInt.matches("\\d+"))
                .map(Integer::valueOf).findAny().orElse(null);
    }

    public Double getUnitPriceOfProduct(HtmlPage productContent) {
        return getHtmlElementListByXPath(productContent, ProductDetailConstants.HTML_PRICE_PER_UNIT_XPATH).stream()
                .findAny()
                .map(htmlElement -> htmlElement.getTextContent().trim())
                .map(unitPriceWithoutCurrencySign -> unitPriceWithoutCurrencySign.substring(1))
                .map(unitPrice -> unitPrice.substring(0, unitPrice.lastIndexOf("/")))
                .filter(unitPriceDouble -> unitPriceDouble.matches("\\d+(\\.\\d*)?"))
                .map(Double::valueOf)
                .orElse(null);
    }

    public String getDescriptionOfProduct(HtmlPage productContent) {

        return getHtmlElementListByXPath(productContent, ProductDetailConstants.HTML_PRODUCT_DESCRIPTION_XPATH).stream()
                .findFirst()
                .map(DomNode::getTextContent)
                .map(String::trim)
                .orElseGet(() ->
                        getHtmlElementListByXPath(productContent, ProductDetailConstants.HTML_PRODUCT_DESCRIPTION_XPATH_ALT).stream()
                                .findFirst()
                                .map(DomNode::getTextContent)
                                .map(String::trim)
                                .orElseGet(() ->
                                        getHtmlElementListByXPath(productContent, ProductDetailConstants.HTML_PRODUCT_DESCRIPTION_XPATH_ALT_2).stream()
                                                .findFirst()
                                                .map(DomNode::getTextContent)
                                                .map(String::trim)
                                                .orElse(null)
                                )
                );
    }

    public Integer getItemCodeOfProduct(HtmlPage productContent) {
        return getHtmlElementListByXPath(productContent, ProductDetailConstants.HTML_ITEM_CODE_XPATH).stream()
                .findAny()
                .map(DomNode::getTextContent)
                .map(itemCodeStr -> itemCodeStr.split(":")[1])
                .map(String::trim)
                .filter(itemCodeInt -> itemCodeInt.matches("\\d+"))
                .map(Integer::valueOf)
                .orElse(null);
    }


    public ProductModel getDetailsOfEachProduct(String productNameLink) {
        String productLink = ProductDetailConstants.WEBSITE_URL.substring(0, ProductDetailConstants.WEBSITE_URL.lastIndexOf('/')) + "/" + productNameLink;
        HtmlPage productContent = getHtmlPageFromWebsiteUrl(productLink);
        return new ProductBuilderModel(productNameLink)
                .setTitle(getTitleOfProduct(productContent))
                .setCalories(getCaloriesOfProduct(productContent))
                .setUnitPrice(getUnitPriceOfProduct(productContent))
                .setDescription(getDescriptionOfProduct(productContent))
                .setItemCode(getItemCodeOfProduct(productContent))
                .build();
    }
}
