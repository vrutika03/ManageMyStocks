package com.dalhousie.csci5308.mms.dao;

import com.dalhousie.csci5308.mms.model.IDbModel;
import com.dalhousie.csci5308.mms.model.WatchList;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.io.IOException;
import java.util.*;

public class WatchListDao implements IDao{
    sqlStoredProcedureExecutor proc;
    private static WatchListDao instance;
    
    public static WatchListDao getInstance() // singleton pattern so that only one variable holds the gamestate
    {
        if(instance == null){
            instance = new WatchListDao();
        }
        return instance;
    }
    
    @Override
    public int create(IDbModel modelObject) {
    	WatchList watchlist = (WatchList) modelObject;
    	SqlParameterSource inParams = new MapSqlParameterSource()
    			.addValue("p_user_name", SecurityContextHolder.getContext().getAuthentication().getName())
    			.addValue("p_stock_id", watchlist.getStockId());
    	try {
    		Map<String, Object> result = sqlStoredProcedureExecutor.getInstance().executeProcedure("addToWatchlist",inParams);
    		return getModifiedRowCount(result);
    	}catch (IOException e) {
    		throw new RuntimeException(e);
    	}			
    }
     
    @Override
    public int update(IDbModel modelObject) {
        return 0;
    }
    
    @Override
    public int delete(IDbModel modelObject) {
    	WatchList watchlist = (WatchList) modelObject;
    	SqlParameterSource inParams = new MapSqlParameterSource()
                .addValue("p_user_name",SecurityContextHolder.getContext().getAuthentication().getName())
                .addValue("p_stock_id",watchlist.getStockId());
        try {
            Map<String, Object> result= sqlStoredProcedureExecutor.getInstance().executeProcedure("removeFromWatchlist",inParams);
            return getModifiedRowCount(result);
           } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }   
    
    public int getModifiedRowCount(Map<String, Object> result) // GET NUMBER OF MODIFIED ROWS
    {
        return (Integer)result.get("#update-count-1");
    }
    
 	private List<IDbModel> sqlResultExtractor(Map<String, Object> result) {
		Collection<Object> values=result.values();
        Iterator<Object> itr=values.iterator();
        Collection<Object> dataMap= (Collection<Object>) itr.next();
        List<IDbModel> watchList=new ArrayList<>();
        for(Object e:dataMap)
        {
            WatchList currentWatchList=new WatchList();
            LinkedCaseInsensitiveMap entry= (LinkedCaseInsensitiveMap) e;
            if(entry.containsKey("STOCK_ID")) {
                Object stockId = entry.get("STOCK_ID");
                currentWatchList.setStockId((int) stockId);
            }
            if(entry.containsKey("STOCK_PRICE")) {
                Object stockPrice = entry.get("STOCK_PRICE");
                currentWatchList.setStockPrice((double) stockPrice);
            }
            if(entry.containsKey("STOCK_NAME")) {
                Object stockName = entry.get("STOCK_NAME");
                currentWatchList.setStockName((String) stockName);
            }
            watchList.add(currentWatchList);
        }
        return watchList;
	}
 	
    @Override
    public IDbModel select(IDbModel modelObject) {
        return null;
    }
    
    @Override
    public List<IDbModel> selectAll() throws IOException {
    	 SqlParameterSource inParams = new MapSqlParameterSource()
                 .addValue("p_user_name",SecurityContextHolder.getContext().getAuthentication().getName());
         try {
             Map<String, Object> result= sqlStoredProcedureExecutor.getInstance().executeProcedure("getUserWatchlist",inParams);
             List <IDbModel> watchList = sqlResultExtractor(result);
             return watchList;
            } catch (IOException e) {
             throw new RuntimeException(e);
         }
     }
    
    @Override
    public List<IDbModel> selectAll(IDbModel modelObject) throws IOException {
        return null;
    }
}