package com.dalhousie.csci5308.mms.dao;

import com.dalhousie.csci5308.mms.model.IDbModel;
import com.dalhousie.csci5308.mms.model.Performance;
import com.dalhousie.csci5308.mms.model.Stock;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.io.IOException;
import java.util.*;

public class PerformanceDao implements  IDao{

    private sqlStoredProcedureExecutor executor;

    private static PerformanceDao instance;
    public static PerformanceDao getInstance() {

        if(instance==null)
        {
            instance=new PerformanceDao();

        }
        return instance;
    }
    public PerformanceDao() {
        executor=sqlStoredProcedureExecutor.getInstance();
    }

    public List<IDbModel> sqlResultExtractor(Map<String,Object> result)
    {
        Collection<Object> values=result.values();
        Iterator<Object> itr=values.iterator();
        Collection<Object> dataMap= (Collection<Object>) itr.next();
        List<IDbModel> list=new ArrayList<>();
        for(Object e:dataMap) {
            Performance performance = new Performance();
            LinkedCaseInsensitiveMap entry = (LinkedCaseInsensitiveMap) e;
            if (entry.containsKey("STOCK_NAME")) {
                String name = (String) entry.get("STOCK_NAME");
                performance.setStock_name(name);
            }
            if (entry.containsKey("DATE")) {
                String date = (String) entry.get("DATE");
                performance.setDate(date);
            }
            if (entry.containsKey("STOCK_PRICE")) {
                Double price = (Double) entry.get("STOCK_PRICE");
                performance.setPrice(price);
            }
            list.add(performance);
        }
        return list;
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
    public IDbModel select(IDbModel modelObject) throws IOException {
        return null;
    }

    @Override
    public List<IDbModel> selectAll() throws IOException {
        return null;
    }

    @Override
    public List<IDbModel> selectAll(IDbModel modelObject) throws IOException {
        MapSqlParameterSource source=new MapSqlParameterSource()
                .addValue("p_stock_name",((Stock)modelObject).getStock_name());
        Map result=executor.executeProcedure("getPerfomanceData",source);
        return sqlResultExtractor(result);
    }
}
