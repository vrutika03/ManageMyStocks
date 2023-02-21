package com.dalhousie.csci5308.mms.dao;

import com.dalhousie.csci5308.mms.model.IDbModel;
import com.dalhousie.csci5308.mms.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestUserDao implements IDao{
    @Override
    public int create(IDbModel modelObject) {
        User mockUser =(User) modelObject;
        if(mockUser.getUsername().equals("mockUser"))
            return 1;
        return 0;
    }
    @Override
    public int update(IDbModel modelObject) {
        User mockUser =(User) modelObject;
        if(mockUser.getUsername().equals("mockUser")&&mockUser.getPan()!=null)
            return 1;
        return 0;
    }
    @Override
    public int delete(IDbModel modelObject) {
        User mockUser =(User) modelObject;
        if(mockUser.getUsername().equals("mockUser")&&mockUser.getPan()!=null)
            return 1;
        return 0;
    }
    @Override
    public IDbModel select(IDbModel modelObject) {
        User mockUser =(User) modelObject;
        if(mockUser.getUsername().equals("mockUser")&&mockUser.getPan().length()==0)
            return modelObject;
        return null;
    }
    @Override
    public List<IDbModel> selectAll() throws IOException {
        User mockUser = new User();
        mockUser.setUsername("mockUser123");
        User invalidUser = new User();
        invalidUser.setUsername("invalidUser");
        List<IDbModel> userList = new ArrayList<IDbModel>();
        userList.add(mockUser);
        userList.add(invalidUser);
        return userList;
        }
    @Override
    public List<IDbModel> selectAll(IDbModel modelObject) throws IOException {
        return null;
    }
}

