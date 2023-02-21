package com.dalhousie.csci5308.mms.model;

import com.dalhousie.csci5308.mms.dao.IDao;
import com.dalhousie.csci5308.mms.dao.PerformanceDao;

import java.io.IOException;
import java.util.List;

public class Performance implements IDbModel {

    String stock_name;
    String date;
    double price;
    private final IDao perfomanceDao;
    public Performance() {
        this.perfomanceDao = PerformanceDao.getInstance();
    }

    public Performance(IDao dao) {
        perfomanceDao = dao;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStock_name() {
        return stock_name;
    }

    public void setStock_name(String stock_name) {
        this.stock_name = stock_name;
    }

    public List<IDbModel> getPerformanceData(IDbModel targetStock) {
        try {
            return perfomanceDao.selectAll(targetStock);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
