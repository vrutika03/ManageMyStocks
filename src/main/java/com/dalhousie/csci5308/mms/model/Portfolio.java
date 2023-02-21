package com.dalhousie.csci5308.mms.model;

import java.util.List;
import com.dalhousie.csci5308.mms.dao.IDao;
import com.dalhousie.csci5308.mms.dao.PortfolioDao;

public class Portfolio implements IDbModel{
    private String userName;
    private int stockId;
    private int purchasePrice;
    private int stockQuantity;
    private double stockPrice;
    private String stockName;
    private IDao portfolioDao;

	public Portfolio() {
    	this.portfolioDao = PortfolioDao.getInstance();
    }
	
	public Portfolio(IDao portfolioDao) {
		this.portfolioDao = portfolioDao;
	}
    
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
	
    public String getStockName() {
		return stockName;
	}
    
    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }
    
    public int getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(int purchasePrice) {
        this.purchasePrice = purchasePrice;
    }
    
    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
    
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

    public double getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(double stockPrice) {
        this.stockPrice = stockPrice;
    }
    
    public List<IDbModel> getUserPortfolio() throws Exception {
    	return portfolioDao.selectAll();
    }
}