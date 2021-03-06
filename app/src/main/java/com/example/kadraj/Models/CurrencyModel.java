package com.example.kadraj.Models;

public class CurrencyModel {
    private String name, purchasePrice, salePrice, changing;

    public CurrencyModel(String name, String purchasePrice, String salePrice, String changing) {
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.salePrice = salePrice;
        this.changing = changing;
    }

    public String getName() {
        return name;
    }

    public String getPurchasePrice() {
        return purchasePrice;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public String getChanging() {
        return changing;
    }
}
