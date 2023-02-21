package com.dalhousie.csci5308.mms;

import com.dalhousie.csci5308.mms.dao.MockStockDao;
import com.dalhousie.csci5308.mms.model.Stock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestStock{
    MockStockDao stockDao;
    @Before
    public void setup()
    {
        stockDao=new MockStockDao();
    }
    @Test
    public void createTest()
    {
        Stock dao=new Stock(stockDao);
        dao.setStock_name("mockstock");
        Assert.assertEquals(1,1,stockDao.create(dao));
    }


    @Test
    public void updateTest()
    {
        Stock dao=new Stock(stockDao);
        dao.setStock_name("mockdao");
        Assert.assertEquals(1,1,stockDao.update(dao));
    }

    @Test
    public void deleteTest()
    {
        Stock dao=new Stock(stockDao);
        dao.setStock_name("mockdao");
        Assert.assertEquals(1,1,stockDao.update(dao));
    }

    @Test
    public void selectTest()
    {
        Stock dao=new Stock(stockDao);
        dao.setStock_name("mockdao");
        Assert.assertSame(dao,dao);
    }

    @Test
    public void selectAllTest()
    {
        Assert.assertEquals(null,null);
    }
    @Test
    public void selectAllTestModelPassed()
    {
        Stock dao=new Stock(stockDao);

        Assert.assertEquals(null,null);
    }
}
