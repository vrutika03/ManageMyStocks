package com.dalhousie.csci5308.mms.model;


import com.dalhousie.csci5308.mms.dao.IDao;
import com.dalhousie.csci5308.mms.dao.StockDao;

import java.io.IOException;
import java.util.List;


public class Stock implements IDbModel {
    public int stock_id;
    public String stock_name;
    public double stock_price;
    public String date;
    private final IDao stockDao;
    public Stock() {
        this.stockDao = StockDao.getInstance();
    }
    public Stock(IDao stockDao) {
        this.stockDao = stockDao;
    }
    public int getStock_id() {
        return stock_id;
    }

    public void setStock_id(int stock_id) {
        this.stock_id = stock_id;
    }
    public String getStock_name() {
        return stock_name;
    }
    public void setStock_name(String stock_name) {
        this.stock_name = stock_name;
    }
    public double getStock_price() {
        return stock_price;
    }
    public void setStock_price(double stock_price) {
        this.stock_price = stock_price;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public boolean addStock() {
        return stockDao.create(this) > 0;
    }

    public boolean updateStockPrice() {
        return stockDao.update(this) > 0;
    }
    public IDbModel selectStock() throws IOException {
        return stockDao.select(this);
    }
    public boolean deleteStock() {
        return stockDao.delete(this) > 0;
    }
    public List<IDbModel> getAllStocks() throws IOException {
        return stockDao.selectAll();
    }
}
