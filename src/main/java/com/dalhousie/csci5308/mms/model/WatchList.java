package com.dalhousie.csci5308.mms.model;

import com.dalhousie.csci5308.mms.dao.IDao;
import com.dalhousie.csci5308.mms.dao.WatchListDao;
import java.util.List;
import java.util.ListIterator;

public class WatchList implements IDbModel {
	private String userName;
	private int stockId;
	private String stockName;
	private double stockPrice;
	private IDao watchlistDao;

	public WatchList() {
		this.watchlistDao = WatchListDao.getInstance();
	}
	
	public WatchList(IDao watchlistDao) {
		this.watchlistDao = watchlistDao;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getStockId() {
		return stockId;
	}

	public void setStockId(int stockId) {
		this.stockId = stockId;
	}

	public String getStockName() {
		return stockName;
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

	public List<IDbModel> getUserWatchList() throws Exception {
		return watchlistDao.selectAll();
	}

	public int removeFromWatchList(IDbModel watchlist) {
		if (watchlistDao.delete(watchlist) == 0) {
			return 0;
		} else {
			return 1;
		}
	}

	public int addToWatchList(IDbModel watchlist) throws Exception {
		boolean exists = false;
		WatchList list = (WatchList) watchlist;
		ListIterator<IDbModel> itr = getUserWatchList().listIterator();
		while (itr.hasNext()) {
			WatchList element = (WatchList) itr.next();
			if (element.getStockId() == list.getStockId()) {
				exists = true;
				break;
			}
		}
		if (!exists) {
			return watchlistDao.create(watchlist);
		}
		return 0;
	}
}