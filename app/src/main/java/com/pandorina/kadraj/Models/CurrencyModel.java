package com.pandorina.kadraj.Models;

public class CurrencyModel {
    private String name, purchasePrice, salePrice, changing, rotation;

    public CurrencyModel(String name, String purchasePrice, String salePrice, String changing, String rotation) {
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.salePrice = salePrice;
        this.changing = changing;
        this.rotation = rotation;

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

    public String getRotation() {
        return rotation;
    }
}
