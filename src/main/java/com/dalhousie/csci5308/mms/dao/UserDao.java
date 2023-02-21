package com.dalhousie.csci5308.mms.dao;

import com.dalhousie.csci5308.mms.model.IDbModel;
import com.dalhousie.csci5308.mms.model.User;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.io.IOException;
import java.util.*;

@Component
public class UserDao implements IDao{
   sqlStoredProcedureExecutor proc;
   private static UserDao instance;
    public static UserDao getInstance() // singleton pattern so that only one variable holds the gamestate
    {
        if(instance == null){
            instance = new UserDao();
        }
        return instance;
    }
    private UserDao()
    {
        this.proc = sqlStoredProcedureExecutor.getInstance();
    }
    List<IDbModel> sqlResultExtractor(Map<String, Object> result) // gets result data from SELECT QUERIES
    {
        Collection<Object> values=result.values();
        Iterator<Object>itr=values.iterator();
        Collection<Object> dataMap= (Collection<Object>) itr.next();
        List<IDbModel> userList=new ArrayList<>();
        for(Object e:dataMap)
        {
            User currentUser=new User();
            LinkedCaseInsensitiveMap entry= (LinkedCaseInsensitiveMap) e;
            if(entry.containsKey("USER_NAME")) {
                Object username = entry.get("USER_NAME");
                currentUser.setUsername((String) username);
            }
            if(entry.containsKey("USER_PASSWORD")) {
                Object password = entry.get("USER_PASSWORD");
                currentUser.setPassword_hash((String) password);
            }
            if(entry.containsKey("USER_ROLE")) {
                Object role = entry.get("role");
                currentUser.setRole((String) role);
            }
            if(entry.containsKey("USER_EMAIL")) {
                Object email = entry.get("USER_EMAIL");
                currentUser.setEmail((String) email);
            }
            if(entry.containsKey("enabled")) {
                Object enabled = entry.get("enabled");
                currentUser.setEnabled((int) enabled);
            }
            if(entry.containsKey("USER_PAN")) {
                Object pan = entry.get("USER_PAN");
                currentUser.setPan((String) pan);
            }
            userList.add(currentUser);
       }
        return userList;
    }
    int getModifiedRowCount(Map<String, Object> result) // GET NUMBER OF MODIFIED ROWS
    {
        return (Integer)result.get("#update-count-1");
    }
    @Override
    public int create(IDbModel model) {
        User currentUser = (User) model;

        SqlParameterSource inParams = new MapSqlParameterSource()
                .addValue("p_username",currentUser.getUsername() )
                .addValue("p_user_email",currentUser.getEmail())
                .addValue("p_user_pan",currentUser.getPan() )
                .addValue("p_password",currentUser.getPassword_hash());
        Map result= null;
        try {
            result = proc.executeProcedure("createUser",inParams);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int rowCount=getModifiedRowCount(result);
        return rowCount;
    }
    @Override
    public int update(IDbModel model) {
        User currentUser = (User) model;
        SqlParameterSource inParams = new MapSqlParameterSource()
                .addValue("p_username", currentUser.getUsername()).addValue("p_role",currentUser.getRole());
        Map result= null;
        try {
            result = proc.executeProcedure("updateUser",inParams);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return getModifiedRowCount(result);
    }

    @Override
    public int delete(IDbModel model) {
        User currentUser = (User) model;
        SqlParameterSource inParams = new MapSqlParameterSource()
                .addValue("p_username",currentUser.getUsername() );
        Map result= null;
        try {
            result = proc.executeProcedure("deleteUser",inParams);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return getModifiedRowCount(result);
    }

    @Override
    public IDbModel select(IDbModel model) {
        return null;
    }

    @Override
    public List<IDbModel> selectAll() throws IOException {
        Map result = proc.executeProcedure("showAllUsers");
        return sqlResultExtractor(result);
    }

    @Override
    public List<IDbModel> selectAll(IDbModel modelObject) throws IOException {
        return null;
    }
}
