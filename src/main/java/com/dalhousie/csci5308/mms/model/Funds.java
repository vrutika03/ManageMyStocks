package com.dalhousie.csci5308.mms.model;

import com.dalhousie.csci5308.mms.dao.FundsDao;
import com.dalhousie.csci5308.mms.dao.IDao;

public class Funds implements IDbModel {
		private String userName;
		private double amount;
		private IDao fundsDao;
		
		public Funds() {
			this.fundsDao = FundsDao.getInstance();
		}
		
		public Funds(IDao fundsDao) {
			this.fundsDao = fundsDao;
		}
		
		public String getUserName() {
			return userName;
		}
		
		public void setUserName(String userName) {
			this.userName = userName;
		}
		
		public double getAmount() {
			return amount;
		}
		
		public void setAmount(double amount) {
			this.amount = amount;
		}

		public IDao getFundsDao() {
			return fundsDao;
		}
		
		public double getUserFunds(IDbModel fundsObject) throws Exception {
			Funds fund = (Funds)fundsDao.select(fundsObject);
			return fund.getAmount();
		}

		public int addUserFunds(IDbModel fundsObject) {
			Funds funds = (Funds) fundsObject;
			if(funds.getAmount() > 0) {
				return fundsDao.create(funds);
			}
			return 0;
		}
		
		public int removeFromUserFunds(IDbModel fundsObject) {
			Funds funds = (Funds) fundsObject;
			if(funds.getAmount() > 0) {
				return fundsDao.delete(funds);
			}
			return 0;
		}
}