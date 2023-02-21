package com.dalhousie.csci5308.mms;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.dalhousie.csci5308.mms.dao.WatchlistDaoTest;
import com.dalhousie.csci5308.mms.model.IDbModel;
import com.dalhousie.csci5308.mms.model.WatchList;

@RunWith(SpringRunner.class)
@SpringBootTest
class WatchListTest {
     WatchList watchlistTest;
     WatchlistDaoTest watchlistDaoTest;
	
	@BeforeEach
	 void setUpBeforeClass() throws Exception {
		watchlistDaoTest = WatchlistDaoTest.getInstance();
		watchlistTest = new WatchList(watchlistDaoTest);
	}
	
	@Test
	void testGetUserWatchList_success() {
		List<IDbModel> list = new ArrayList<IDbModel>();
		int watchlistSize = 0;
		try {
			list = watchlistTest.getUserWatchList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (IDbModel iDbModel : list) {
			WatchList element = (WatchList) iDbModel;
			if(element.getUserName().equals("testUser")) {
				watchlistSize++;
			}
		}
		assertEquals(2,watchlistSize);
	}
	
	@Test
	void testGetUserWatchList_failure() {
		List<IDbModel> list = new ArrayList<IDbModel>();
		int watchlistSize = 0;
		try {
			list = watchlistTest.getUserWatchList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (IDbModel iDbModel : list) {
			WatchList element = (WatchList) iDbModel;
			if(element.getUserName().equals("invalidUser")) {
				watchlistSize++;
			}
		}
		assertEquals(0,watchlistSize);
	}
	@Test
	void testRemoveFromWatchList_success() {
		watchlistTest.setUserName("testUser");
		watchlistTest.setStockId(1);
		assertEquals(1, watchlistTest.removeFromWatchList(watchlistTest));
	}

	@Test
	void testRemoveFromWatchList_failure() {
		watchlistTest.setUserName("invalidUser");
		watchlistTest.setStockId(-999);
		assertEquals(0, watchlistTest.removeFromWatchList(watchlistTest));
	}
	
	@Test
	void testAddToWatchList_success() {
		watchlistTest.setStockId(3);
		watchlistTest.setStockName("META");
		watchlistTest.setStockPrice(156.89);
		watchlistTest.setUserName("testUser");
		try {
			assertEquals(1, watchlistTest.addToWatchList(watchlistTest));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	void testAddToWatchList_failure() {
		watchlistTest.setStockId(1);
		watchlistTest.setStockName("ACN");
		watchlistTest.setStockPrice(123.45);
		watchlistTest.setUserName("testUser");
		try {
			assertEquals(0, watchlistTest.addToWatchList(watchlistTest));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}