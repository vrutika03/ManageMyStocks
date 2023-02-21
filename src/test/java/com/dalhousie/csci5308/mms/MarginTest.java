package com.dalhousie.csci5308.mms;
import com.dalhousie.csci5308.mms.dao.MarginDaoTest;
import com.dalhousie.csci5308.mms.model.Margin;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MarginTest {
    MarginDaoTest marginDaoTest;
    Margin m;
    @Before
    public void setUp() {
         marginDaoTest = (MarginDaoTest) MarginDaoTest.getInstance();
    }

    @Test
    public void updateUserMarginTest()
    {
        m= new Margin(marginDaoTest);
        m.setUserName("User");
        Assert.assertEquals(1,m.updateUserMargin(m));
    }
    @Test
    public void selectUserMarginTest() throws IOException {
        m = new Margin(marginDaoTest);
        m.setUserName("User");
        Assert.assertEquals(200,m.getUserMargin(m),0.0f);
    }
}
