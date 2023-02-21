package com.dalhousie.csci5308.mms.dao;

import com.dalhousie.csci5308.mms.model.IDbModel;
import com.dalhousie.csci5308.mms.model.Performance;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestPerformanceDao implements IDao{
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
        Performance mockPerformance = new Performance();
        mockPerformance.setDate("12-12-2022");
        mockPerformance.setPrice(12.0);
        mockPerformance.setStock_name("TEST");
        Performance mockPerformance2 = new Performance();
        mockPerformance.setDate("12-12-2022");
        mockPerformance.setPrice(11.0);
        mockPerformance.setStock_name("TEST2");
        List<IDbModel> performanceList = new ArrayList<IDbModel>();
        performanceList.add(mockPerformance);
        performanceList.add(mockPerformance2);
        return performanceList;
    }
}
