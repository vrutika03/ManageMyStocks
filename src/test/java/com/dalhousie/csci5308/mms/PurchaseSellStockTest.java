package com.dalhousie.csci5308.mms;
import com.dalhousie.csci5308.mms.dao.IDao;
import com.dalhousie.csci5308.mms.dao.PurchaseSellStockDaoTest;
import com.dalhousie.csci5308.mms.model.PurchaseSellStock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
@RunWith(SpringRunner.class)
@SpringBootTest
public class PurchaseSellStockTest {
    IDao dao;
    @Before
    public void setUp() {
        dao = PurchaseSellStockDaoTest.getInstance();
    }

    @Test
    public void purchaseTest(){
        PurchaseSellStock stock= new PurchaseSellStock(dao);
        stock.setStockId(101);
        stock.setStockPrice(200);
        Assert.assertEquals(1,stock.purchase());
    }
    @Test
    public void sellTest()
    {
        PurchaseSellStock stock= new PurchaseSellStock(dao);
        stock.setStockId(101);
        stock.setStockPrice(200);
        Assert.assertEquals(1,stock.sell());
    }

}
