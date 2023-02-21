package com.dalhousie.csci5308.mms;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.dalhousie.csci5308.mms.dao.FundsDaoTest;
import com.dalhousie.csci5308.mms.model.Funds;

@RunWith(SpringRunner.class)
@SpringBootTest
class FundsTest {
	private FundsDaoTest fundsDaoTest;
	private Funds funds;

	@BeforeEach
	void setUp() throws Exception {
		fundsDaoTest = FundsDaoTest.getInstance();
		funds = new Funds(fundsDaoTest);
	}

	@Test
	void testGetUserFunds_success() {
		funds.setUserName("testUser");
		try {
			assertEquals(150, funds.getUserFunds(funds));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testAddUserFunds_success() {
		funds.setUserName("testUser");
		funds.setAmount(100);
		assertEquals(1, funds.addUserFunds(funds));
	}
	
	@Test
	void testAddUserFunds_failure() {
		funds.setUserName("invalidUser");
		funds.setAmount(100);
		assertEquals(0, funds.addUserFunds(funds));
	}

	@Test
	void testAddUserFunds_failureAmt() {
		funds.setUserName("invalidUser");
		funds.setAmount(-100);
		assertEquals(0, funds.addUserFunds(funds));
	}

	@Test
	void testRemoveFromUserFunds_success() {
		funds.setUserName("testUser");
		funds.setAmount(10);
		assertEquals(1, funds.removeFromUserFunds(funds));	
	}
	
	@Test
	void testRemoveFromUserFunds_failure() {
		funds.setUserName("invalidUser");
		funds.setAmount(10);
		assertEquals(0, funds.removeFromUserFunds(funds));
	}
	
	@Test
	void testRemoveFromUserFunds_failureAmt() {
		funds.setUserName("invalidUser");
		funds.setAmount(-10);
		assertEquals(0, funds.removeFromUserFunds(funds));
	}
}
