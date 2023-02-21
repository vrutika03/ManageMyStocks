package com.dalhousie.csci5308.mms.model;

public interface ModelFactory {
		public IDbModel createPortfolioModel();
		public IDbModel createWatchlistModel();
		public IDbModel createFundsModel();
		public IDbModel createMarginModel();
		public IDbModel createUserModel();
		public IDbModel createStockModel();
		public IDbModel createPerformanceModel();
		public IDbModel createPurchaseSellModel();
}
