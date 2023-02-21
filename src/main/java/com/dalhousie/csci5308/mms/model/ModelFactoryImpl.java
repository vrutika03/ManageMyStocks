package com.dalhousie.csci5308.mms.model;

public class ModelFactoryImpl implements ModelFactory{
    private static ModelFactoryImpl instance = null;
	private ModelFactoryImpl() {

    }
    public static ModelFactoryImpl getInstance() {
    	if(instance == null) {
    		instance = new ModelFactoryImpl();
    	}
		return instance;
	}
	@Override
	public IDbModel createPortfolioModel() {
		return new Portfolio();
	}
	@Override
	public IDbModel createWatchlistModel() {
		return new WatchList();
	}
	@Override 
	public IDbModel createFundsModel() {
	     	return new Funds();
	}
	public IDbModel createMarginModel(){
		return new Margin();
	}

	@Override
	public IDbModel createUserModel() {
		return new User();
	}

	@Override
	public IDbModel createStockModel() {
		return new Stock();
	}

	@Override
	public IDbModel createPerformanceModel() {
		return new Performance();
	}

	@Override
	public IDbModel createPurchaseSellModel() {
		return new PurchaseSellStock();
	}
}