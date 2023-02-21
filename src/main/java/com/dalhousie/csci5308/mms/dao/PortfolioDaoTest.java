package com.dalhousie.csci5308.mms.dao;

import com.dalhousie.csci5308.mms.model.IDbModel;
import com.dalhousie.csci5308.mms.model.Portfolio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PortfolioDaoTest implements IDao {
	Portfolio portfolio1;
	Portfolio portfolio2;
	List<IDbModel> userPortfolio = new ArrayList<IDbModel>();
	
	public PortfolioDaoTest() {
		portfolio1 = new Portfolio();
		portfolio2 = new Portfolio();
		
		portfolio1.setUserName("testUser");
		portfolio1.setStockId(1);
		portfolio1.setStockName("ACN");
		portfolio1.setStockPrice(123.45);
		portfolio1.setStockQuantity(14);
		
		portfolio2.setUserName("testUser");
		portfolio2.setStockId(2);
		portfolio2.setStockName("CGI");
		portfolio2.setStockPrice(432.12);
		portfolio2.setStockQuantity(3);
		
	    userPortfolio.add(portfolio1);
	    userPortfolio.add(portfolio2);	
	}
	
	public static PortfolioDaoTest getInstance() {
			 return new PortfolioDaoTest();
	}
		
	@Override
	public int create(IDbModel modelObject) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(IDbModel modelObject) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(IDbModel modelObject) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public IDbModel select(IDbModel modelObject) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IDbModel> selectAll() throws IOException {
		return userPortfolio;
	}

	@Override
	public List<IDbModel> selectAll(IDbModel modelObject) {
			return null;
	}
}