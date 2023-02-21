package com.dalhousie.csci5308.mms.dao;

import com.dalhousie.csci5308.mms.model.Funds;
import com.dalhousie.csci5308.mms.model.IDbModel;

import java.io.IOException;
import java.util.List;

public class FundsDaoTest implements IDao {
	private Funds funds;
	
	//mock data
	public FundsDaoTest() {
         funds = new Funds();
         funds.setUserName("testUser");
         funds.setAmount(150);
	}
	
	public static FundsDaoTest getInstance() {
		return new FundsDaoTest();
	}
	
	@Override
	public int create(IDbModel modelObject) {
		Funds fundsObject = (Funds) modelObject;
		if(funds.getUserName().equals(fundsObject.getUserName())) {
			double updatedFundValue = funds.getAmount() + fundsObject.getAmount();
			funds.setAmount(updatedFundValue);
			return 1;
		}
		return 0;
	}

	@Override
	public int update(IDbModel modelObject) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(IDbModel modelObject) {
		Funds fundsObject = (Funds) modelObject;
		if(funds.getUserName().equals(fundsObject.getUserName())) {
			double updatedFundValue = funds.getAmount() - fundsObject.getAmount();
			funds.setAmount(updatedFundValue);
			return 1;
		}
		return 0;
	}

	@Override
	public IDbModel select(IDbModel modelObject) throws IOException {
		Funds fundsObject = (Funds) modelObject;
		if(fundsObject.getUserName().equals(funds.getUserName())){
			return funds;
		}
		return null;
	}

	@Override
	public List<IDbModel> selectAll() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IDbModel> selectAll(IDbModel modelObject) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
}