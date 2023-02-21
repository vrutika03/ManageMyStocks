package com.dalhousie.csci5308.mms.dao;

import com.dalhousie.csci5308.mms.model.IDbModel;

import java.io.IOException;
import java.util.List;

public interface IDao {
    public int create(IDbModel modelObject);
    public int update(IDbModel modelObject);
    public int delete(IDbModel modelObject);
    public IDbModel select(IDbModel modelObject) throws IOException;
    public List<IDbModel> selectAll() throws IOException;
    public List<IDbModel> selectAll(IDbModel modelObject) throws IOException;
}
