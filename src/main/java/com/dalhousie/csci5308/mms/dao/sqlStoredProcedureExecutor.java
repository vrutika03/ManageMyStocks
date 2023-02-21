package com.dalhousie.csci5308.mms.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Map;

@Component
public class sqlStoredProcedureExecutor {
    private static sqlStoredProcedureExecutor instance;
    public static sqlStoredProcedureExecutor getInstance()
    {
        if(instance == null){
            instance = new sqlStoredProcedureExecutor();
        }
        return instance;
    }
    public Map executeProcedure(String procedure_name, SqlParameterSource params) throws IOException {
        DataSource dataSource =JDBCConfiguration.getInstance().getDataSource();
        SimpleJdbcCall procedure= new SimpleJdbcCall(dataSource).withProcedureName(procedure_name);
        Map result = procedure.execute(params);
        return result;
    }
    public Map executeProcedure(String procedure_name) throws IOException {
        DataSource dataSource = JDBCConfiguration.getInstance().getDataSource();
        SimpleJdbcCall procedure= new SimpleJdbcCall(dataSource).withProcedureName(procedure_name);
        Map result = procedure.execute();
        return result;
    }
}
