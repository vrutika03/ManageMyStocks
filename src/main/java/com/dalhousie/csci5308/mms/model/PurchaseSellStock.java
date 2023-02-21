package com.dalhousie.csci5308.mms.model;
import com.dalhousie.csci5308.mms.dao.IDao;
import com.dalhousie.csci5308.mms.dao.PurchaseSellStockDao;

public class PurchaseSellStock implements IDbModel {
    private static PurchaseSellStock instance;
    private int stockId;
    private int stockQuantity;
    private double stockPrice;

    private IDao purchaseSellStock;

    public PurchaseSellStock(IDao dao)
    {
        purchaseSellStock=dao;
    }

    public PurchaseSellStock()
    {
        purchaseSellStock=PurchaseSellStockDao.getInstance();
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public void setStockPrice(double stockPrice) {
        this.stockPrice = stockPrice;
    }

    public int getStockId() {
        return stockId;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public double getStockPrice() {
        return stockPrice;
    }

    public PurchaseSellStock(int stockId, int stockQuantity, int stockPrice) {
        this.stockId = stockId;
        this.stockQuantity = stockQuantity;
        this.stockPrice = stockPrice;

    }
    public int purchase(){
        return purchaseSellStock.create(this);
    }

    public int sell()
    {
        return purchaseSellStock.delete(this);
    }
}


