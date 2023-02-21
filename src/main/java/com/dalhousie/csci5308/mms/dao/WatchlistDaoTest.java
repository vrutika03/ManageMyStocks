package com.dalhousie.csci5308.mms.dao;

import com.dalhousie.csci5308.mms.model.IDbModel;
import com.dalhousie.csci5308.mms.model.WatchList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WatchlistDaoTest implements IDao {
	WatchList watchlist1;
	WatchList watchList2;
	List<IDbModel> userWatchlist = new ArrayList<IDbModel>();
	
	//mock data
	public WatchlistDaoTest () {
		watchlist1 =  new WatchList();
		watchList2 =  new WatchList();
		
		watchlist1.setUserName("testUser");
		watchlist1.setStockId(1);
		watchlist1.setStockName("ACN");
		watchlist1.setStockPrice(123.45);
		
		watchList2.setUserName("testUser");
		watchList2.setStockId(2);
		watchList2.setStockName("CGI");
		watchList2.setStockPrice(432.12);
		
		userWatchlist.add(watchlist1);
		userWatchlist.add(watchList2);
	}
	
	public static WatchlistDaoTest getInstance() {
		return new WatchlistDaoTest();
	}
	
	@Override
	public int create(IDbModel modelObject) {
		WatchList watchlistObject = (WatchList)modelObject;
		if(userWatchlist.add(watchlistObject)) {
			return 1;
		}
		return 0;
	}

	@Override
	public int update(IDbModel modelObject) {
		return 0;
	}

	@Override
	public int delete(IDbModel modelObject) {
		WatchList watchlistObject = (WatchList) modelObject;
		List<IDbModel> copyUserWatchlist = userWatchlist;
		for (IDbModel iDbModel : copyUserWatchlist) {
			WatchList element = (WatchList) iDbModel;
			if(element.getUserName().equals(watchlistObject.getUserName())) {
				if(element.getStockId() == watchlistObject.getStockId()) {
					if(userWatchlist.remove(element)) {
						return 1;
					}
				}
			}
		}
		return 0;
	}

	@Override
	public IDbModel select(IDbModel modelObject) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IDbModel> selectAll() throws IOException {
		return userWatchlist;
	}

	@Override
	public List<IDbModel> selectAll(IDbModel modelObject) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
}