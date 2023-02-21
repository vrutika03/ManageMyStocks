package com.dalhousie.csci5308.mms;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.dalhousie.csci5308.mms.dao.PortfolioDaoTest;
import com.dalhousie.csci5308.mms.model.IDbModel;
import com.dalhousie.csci5308.mms.model.Portfolio;

@RunWith(SpringRunner.class)
@SpringBootTest
class PortfolioTest {
	 PortfolioDaoTest portfolioDaoTest;
	 Portfolio portfolioTest;
	
    @BeforeEach
     void setUp() {
    	 portfolioDaoTest = PortfolioDaoTest.getInstance();
    	 portfolioTest = new Portfolio(portfolioDaoTest);
    }

	@Test
	void testGetUserPortfolio_success() {
		int portfolioSize = 0;
		List<IDbModel> list = new ArrayList<IDbModel>();
		try {
			list = portfolioTest.getUserPortfolio();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (IDbModel iDbModel : list) {
			Portfolio element = (Portfolio) iDbModel;
			if(element.getUserName().equals("testUser")) {
				portfolioSize ++;
			}
		}
		assertEquals(2, portfolioSize);
	}
	
	@Test
	void testGetUserPortfolio_failure() {
		int portfolioSize = 0;
		List<IDbModel> list = new ArrayList<IDbModel>();
		try {
			list = portfolioTest.getUserPortfolio();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (IDbModel iDbModel : list) {
			Portfolio element = (Portfolio) iDbModel;
			if(element.getUserName().equals("invalidUser")) {
				portfolioSize ++;
			}
		}
		assertEquals(0, portfolioSize);
	}
}