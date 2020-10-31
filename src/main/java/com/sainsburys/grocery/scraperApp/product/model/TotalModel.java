package com.sainsburys.grocery.scraperApp.product.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TotalModel {

    @JsonProperty(value = "gross")
    private Double gross;
    @JsonProperty(value = "vat")
    private Double vat;

    public TotalModel() {
    }

    public TotalModel(Double gross, Double vat) {
        this.gross = gross;
        this.vat = vat;
    }

    public Double getGross() {
        return Double.parseDouble(String.format("%.2f", gross));
    }

    public void setGross(Double gross) {
        this.gross = gross;
    }

    public Double getVat() {
        return vat;
    }

    public void setVat(Double vat) {
        this.vat = vat;
    }
}
