package com.dalhousie.csci5308.mms.dao;
import com.dalhousie.csci5308.mms.model.Funds;
import com.dalhousie.csci5308.mms.model.IDbModel;
import com.dalhousie.csci5308.mms.model.Margin;
import com.dalhousie.csci5308.mms.model.ModelFactoryImpl;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.LinkedCaseInsensitiveMap;
import java.io.IOException;
import java.util.*;

public class MarginDao implements IDao{

    private static sqlStoredProcedureExecutor executor;

    private static MarginDao instance;

    private MarginDao()
    {
        executor= sqlStoredProcedureExecutor.getInstance();
    }
    public static MarginDao getInstance(){
        if(instance==null)
        {
            instance=new MarginDao();
            return  instance;
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
    List<IDbModel> sqlResultExtractor(Map<String, Object> result) // result data from select queries
    {
        Collection<Object> values=result.values();
        Iterator<Object> itr=values.iterator();
        Collection<Object> dataMap= (Collection<Object>) itr.next();
        List<IDbModel> marginList=new ArrayList<>();
        for(Object e:dataMap)
        {
            Margin currentMargin=(Margin) ModelFactoryImpl.getInstance().createMarginModel();
            LinkedCaseInsensitiveMap entry= (LinkedCaseInsensitiveMap) e;
            if(entry.containsKey("USER_NAME")) {
                Object name = entry.get("USER_NAME");
                currentMargin.setUserName((String) name);
            }
            if(entry.containsKey("USER_AVAILABLE_MARGIN")) {
                Object amount = entry.get("USER_AVAILABLE_MARGIN");
                currentMargin.setAvailable_margin((double) amount);
            }
            marginList.add(currentMargin);
        }
        return marginList;
    }

    @Override
    public int create(IDbModel modelObject) {
        return 0;
    }

    @Override
    public int update(IDbModel modelObject) {
        Funds funds = (Funds) modelObject;
        SqlParameterSource inParams = new MapSqlParameterSource()
                .addValue("p_user_name",SecurityContextHolder.getContext().getAuthentication().getName())
                .addValue("p_amount",funds.getAmount()*2.5);
        try {
            Map<String, Object> result= sqlStoredProcedureExecutor.getInstance().executeProcedure("updateUserMargin",inParams);
            return getModifiedRowCount(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int delete(IDbModel modelObject) {
        return 0;
    }

    @Override
    public IDbModel select(IDbModel modelObject) throws IOException {
        Margin margin = (Margin) modelObject;
        SqlParameterSource source = new MapSqlParameterSource()
                .addValue("p_user_name", SecurityContextHolder.getContext().getAuthentication().getName());
        Map<String, Object> result= sqlStoredProcedureExecutor.getInstance().executeProcedure("getUserMargin",source);
        List <IDbModel> marginList = sqlResultExtractor(result);
        return marginList.get(0);
    }

    @Override
    public List<IDbModel> selectAll() throws IOException {
        return null;
    }

    @Override
    public List<IDbModel> selectAll(IDbModel modelObject) throws IOException {
        return null;
    }
}
