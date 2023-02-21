package com.dalhousie.csci5308.mms.dao;

import com.dalhousie.csci5308.mms.model.IDbModel;
import com.dalhousie.csci5308.mms.model.Stock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MockStockDao implements IDao{
    @Override
    public int create(IDbModel modelObject) {
        Stock dao=(Stock) modelObject;
        if (dao.getStock_name().equals("mockstock"))
        {
            return 1;
        }
        return 0;
    }
    @Override
    public int  update(IDbModel modelObject) {
        Stock dao=(Stock) modelObject;
        if (dao.getStock_name().equals("mockstock"))
        {
            return 1;
        }
        return 0;
    }

    @Override
    public int delete(IDbModel modelObject) {
        Stock dao=(Stock)modelObject;
        if(dao.getStock_name().equals("mockstock"))
            return 1;
        return 0;
    }

    @Override
    public IDbModel select(IDbModel modelObject) throws IOException {
        Stock dao=(Stock)modelObject;
        if (dao.getStock_name().equals("mockstock"))
            return dao;
        return null;
    }

    @Override
    public List<IDbModel> selectAll() throws IOException {
        return null;
    }

    @Override
    public List<IDbModel> selectAll(IDbModel modelObject) {
        Stock mockStock = new Stock();
        mockStock.setStock_name("mockStock");
        Stock invalidStock = new Stock();
        invalidStock.setStock_name("invalidStock");
        List<IDbModel> stockList = new ArrayList<IDbModel>();
        stockList.add(mockStock);
        stockList.add(invalidStock);
        return stockList;
    }
}
