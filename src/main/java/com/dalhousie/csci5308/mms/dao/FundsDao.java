package com.dalhousie.csci5308.mms.dao;

import com.dalhousie.csci5308.mms.model.Funds;
import com.dalhousie.csci5308.mms.model.IDbModel;
import com.dalhousie.csci5308.mms.model.ModelFactoryImpl;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.LinkedCaseInsensitiveMap;
import java.io.IOException;
import java.util.*;


public class FundsDao implements IDao {
	private static FundsDao instance = null;
	
	private FundsDao() {
		
	}
	
	public static FundsDao getInstance() {
		if(instance == null) {
			instance = new FundsDao();
		}
		return instance;
	}
	
	@Override
	public int create(IDbModel modelObject) {
    	Funds funds = (Funds) modelObject;
    	SqlParameterSource inParams = new MapSqlParameterSource()
    			.addValue("p_user_name", SecurityContextHolder.getContext().getAuthentication().getName())
    			.addValue("p_amount",funds.getAmount());
    	try {
    		Map<String, Object> result = sqlStoredProcedureExecutor.getInstance().executeProcedure("addFunds",inParams);
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
		Funds funds = (Funds) modelObject;
    	SqlParameterSource inParams = new MapSqlParameterSource()
    			.addValue("p_user_name", SecurityContextHolder.getContext().getAuthentication().getName())
    			.addValue("p_amount",funds.getAmount());
    	try {
    		Map<String, Object> result = sqlStoredProcedureExecutor.getInstance().executeProcedure("removeFunds",inParams);
    		return getModifiedRowCount(result);
    	}catch (IOException e) {
    		throw new RuntimeException(e);
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
        List<IDbModel> fundsList=new ArrayList<>();
        for(Object e:dataMap)
        {
            Funds currentFunds=(Funds) ModelFactoryImpl.getInstance().createFundsModel();
            LinkedCaseInsensitiveMap entry= (LinkedCaseInsensitiveMap) e;
            if(entry.containsKey("USER_NAME")) {
                Object name = entry.get("USER_NAME");
                currentFunds.setUserName((String) name);
            }
            if(entry.containsKey("USER_AVAILABLE_FUNDS")) {
            	Object amount = entry.get("USER_AVAILABLE_FUNDS");
            	currentFunds.setAmount((double) amount);
            }
            fundsList.add(currentFunds);
        }
        return fundsList;
    }
	
	@Override
	public IDbModel select(IDbModel modelObject) throws IOException {
		SqlParameterSource inParams = new MapSqlParameterSource()
                .addValue("p_user_name",SecurityContextHolder.getContext().getAuthentication().getName());
        try {
            Map<String, Object> result= sqlStoredProcedureExecutor.getInstance().executeProcedure("getUserFunds",inParams);
            List <IDbModel> fundsList = sqlResultExtractor(result);
            return fundsList.get(0);
           } catch (IOException e) {
            throw new RuntimeException(e);
        }
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