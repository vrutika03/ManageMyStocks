package com.dalhousie.csci5308.mms.dao;

import com.dalhousie.csci5308.mms.model.IDbModel;
import com.dalhousie.csci5308.mms.model.ModelFactoryImpl;
import com.dalhousie.csci5308.mms.model.Portfolio;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.io.IOException;
import java.util.*;

public class PortfolioDao implements IDao{

    sqlStoredProcedureExecutor proc;
    private static PortfolioDao instance;
    
    private PortfolioDao()
    {
        proc=sqlStoredProcedureExecutor.getInstance();
    }
    
    public static PortfolioDao getInstance() // singleton pattern so that only one variable holds the gamestate
    {
        if(instance == null){
            instance = new PortfolioDao();
        }
        return instance;
    }
    int getModifiedRowCount(Map<String, Object> result) // GET NUMBER OF MODIFIED ROWS
    {
        return (Integer)result.get("#update-count-1");
    }
    @Override
    public int create(IDbModel modelObject) {
        return 0;
    }
    @Override
    public int update(IDbModel modelObject) {
        return 0;
    }
    @Override
    public int delete(IDbModel modelObject) {
        return 0;
    }
    @Override
    public IDbModel select(IDbModel modelObject) {
        return null;
    }
    List<IDbModel> sqlResultExtractor(Map<String, Object> result) // gets result data from SELECT QUERIES
    {
        Collection<Object> values=result.values();
        Iterator<Object> itr=values.iterator();
        Collection<Object> dataMap= (Collection<Object>) itr.next();
        List<IDbModel> portfolioList=new ArrayList<>();
        for(Object e:dataMap)
        {
            Portfolio currentPortfolio= (Portfolio) ModelFactoryImpl.getInstance().createPortfolioModel();
            LinkedCaseInsensitiveMap entry= (LinkedCaseInsensitiveMap) e;
            if(entry.containsKey("USER_NAME")) {
                Object name = entry.get("USER_NAME");
                currentPortfolio.setUserName((String) name);
            }
            if(entry.containsKey("STOCK_ID")) {
                Object stockId = entry.get("STOCK_ID");
                currentPortfolio.setStockId((int) stockId);
            }
            if(entry.containsKey("STOCK_PURCHASE_PRICE")) {
                Object stockPurchasePrice = entry.get("STOCK_PURCHASE_PRICE");
                currentPortfolio.setPurchasePrice((int)stockPurchasePrice);
            }
            if(entry.containsKey("STOCK_QTY")) {
                Object stockQty = entry.get("STOCK_QTY");
                currentPortfolio.setStockQuantity((int) stockQty);
            }
            if(entry.containsKey("STOCK_PRICE")) {
                Object stockPrice = entry.get("STOCK_PRICE");
                currentPortfolio.setStockPrice((double) stockPrice);
            }
            if(entry.containsKey("STOCK_NAME")) {
            	Object stockName = entry.get("STOCK_NAME");
            	currentPortfolio.setStockName((String) stockName);
            }
            portfolioList.add(currentPortfolio);
        }
        return portfolioList;
    }
    @Override
    public List<IDbModel> selectAll() {
        SqlParameterSource inParams = new MapSqlParameterSource()
                .addValue("p_user_name",SecurityContextHolder.getContext().getAuthentication().getName());
        try {
            Map<String, Object> result= sqlStoredProcedureExecutor.getInstance().executeProcedure("getUserPortfolio",inParams);
            List <IDbModel> portfolioList = sqlResultExtractor(result);
            return portfolioList;
           } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
	@Override
	public List<IDbModel> selectAll(IDbModel modelObject) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
}