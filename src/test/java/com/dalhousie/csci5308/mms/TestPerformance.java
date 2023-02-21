package com.dalhousie.csci5308.mms;


import com.dalhousie.csci5308.mms.dao.IDao;
import com.dalhousie.csci5308.mms.dao.TestPerformanceDao;
import com.dalhousie.csci5308.mms.dao.TestUserDao;
import com.dalhousie.csci5308.mms.model.IDbModel;
import com.dalhousie.csci5308.mms.model.Performance;
import com.dalhousie.csci5308.mms.model.Stock;
import com.dalhousie.csci5308.mms.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.parameters.P;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestPerformance {
    @Test
    public void getPerformanceData() throws IOException {
        TestPerformanceDao testDao = new TestPerformanceDao();
        Performance performance = new Performance(testDao);
        Stock targetStock = new Stock();
        List< IDbModel> perfdata = performance.getPerformanceData(targetStock);
        assertTrue(perfdata.size()>0);
    }
}
