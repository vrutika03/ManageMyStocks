package com.dalhousie.csci5308.mms.dao;

import com.dalhousie.csci5308.mms.model.IDbModel;
import com.dalhousie.csci5308.mms.model.Stock;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.io.IOException;
import java.util.*;

public class StockDao implements IDao{

    private static StockDao instance;
    sqlStoredProcedureExecutor executor;
    
    private StockDao()
    {
        executor=sqlStoredProcedureExecutor.getInstance();
    }

    public static StockDao getInstance() {

        if(instance==null)
        {
            instance=new StockDao();
            return instance;
        }
        else
        {
            return instance;
        }
    }

    int getModifiedRowCount(Map<String, Object> result) // GET NUMBER OF MODIFIED ROWS
    {
        return (Integer)result.get("#update-count-1");
    }
    
    List<IDbModel> sqlResultExtractor(Map<String, Object> result) // gets result data from SELECT QUERIES
    {
        Collection<Object> values=result.values();
        Iterator<Object> itr=values.iterator();
        Collection<Object> dataMap= (Collection<Object>) itr.next();
        List<IDbModel> stockList=new ArrayList<>();
        for(Object e:dataMap)
        {
            Stock currentStock=new Stock();
            LinkedCaseInsensitiveMap entry= (LinkedCaseInsensitiveMap) e;
            if(entry.containsKey("STOCK_NAME")) {
                Object name = entry.get("STOCK_NAME");
                currentStock.setStock_name((String) name);
            }
            if(entry.containsKey("STOCK_ID")) {
                Object stockId = entry.get("STOCK_ID");
                currentStock.setStock_id((int) stockId);
            }
            if(entry.containsKey("DATE")) {
                Object stockDate = entry.get("DATE");
                currentStock.setDate((String)stockDate);
            }
            if(entry.containsKey("STOCK_PRICE")) {
                Object stockPrice = entry.get("STOCK_PRICE");

                currentStock.setStock_price((double) stockPrice);

            }
            stockList.add(currentStock);
        }
        return stockList;
    }
    @Override
    public int create(IDbModel modelObject) {
        Stock currentStock = (Stock) modelObject;
        SqlParameterSource parameterSource=new MapSqlParameterSource()
                .addValue("name",currentStock.getStock_name())
                .addValue("price",currentStock.getStock_price())
                .addValue("date",currentStock.getDate());
        Map result= null;
        try {
            result = executor.executeProcedure("addStock",parameterSource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return getModifiedRowCount(result);
    }
    @Override
    public int update(IDbModel modelObject) {
        Stock currentStock = (Stock) modelObject;
        SqlParameterSource parameterSource=new MapSqlParameterSource()
                .addValue("id",currentStock.getStock_id())
                .addValue("price",currentStock.getStock_price())
                .addValue("date",currentStock.getDate());
        Map result= null;
        try {
            result = executor.executeProcedure("updateStockPrice",parameterSource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return getModifiedRowCount(result);
    }

    @Override
    public int delete(IDbModel modelObject) {
        Stock currentStock = (Stock) modelObject;
        SqlParameterSource parameterSource=new MapSqlParameterSource()
                .addValue("p_stock_id",currentStock.getStock_id());
        Map result= null;
        try {
            result = executor.executeProcedure("removeStock",parameterSource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return getModifiedRowCount(result);
    }
    @Override
    public IDbModel select(IDbModel modelObject) throws IOException {
        return selectAll().stream().filter(stock->((Stock)stock).getStock_id()==((Stock) modelObject).getStock_id()).findAny().get();
    }
    @Override
    public List<IDbModel> selectAll() throws IOException {
        Map result=executor.executeProcedure("showAllStocks");
        return sqlResultExtractor(result);
    }
    @Override
    public List<IDbModel> selectAll(IDbModel modelObject) {
        return null;
    }
}

