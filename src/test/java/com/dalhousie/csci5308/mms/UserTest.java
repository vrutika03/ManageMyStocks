package com.dalhousie.csci5308.mms;

import com.dalhousie.csci5308.mms.dao.TestUserDao;
import com.dalhousie.csci5308.mms.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {
    @Test
    public void createUserTest_validUser() throws IOException {
        TestUserDao testDao = new TestUserDao();
        User testUser = new User(testDao);
        testUser.setUsername("mockUser");
        assertTrue(testUser.createUser());
    }
    
    @Test
    public void createUserTest_invalidUser() throws IOException {
        TestUserDao testDao = new TestUserDao();
        User testUser = new User(testDao);
        testUser.setUsername("invalidUser");
        assertFalse(testUser.createUser());
    }
    
    @Test
    public void deleteUserTest_validUser() throws IOException {
        TestUserDao testDao = new TestUserDao();
        User testUser = new User(testDao);
        testUser.setUsername("mockUser");
        testUser.setPan("xxxxxxxx1");
        assertTrue(testUser.deleteUser("mockUser"));
    }
    
    @Test
    public void deleteUserTest_invalidUser() throws IOException {
        TestUserDao testDao = new TestUserDao();
        User testUser = new User(testDao);
        testUser.setUsername("invalidUser");
        assertFalse(testUser.deleteUser("invalidUser"));
    }
    
    @Test
    public void updateUserTest_validUser() throws IOException {
        TestUserDao testDao = new TestUserDao();
        User testUser = new User(testDao);
        testUser.setUsername("mockUser");
        testUser.setPan("xxxxxxxx1");
        assertTrue(testUser.deleteUser("mockUser"));
    }
    
    @Test
    public void updateUserTest_invalidUser() throws IOException {
        TestUserDao testDao = new TestUserDao();
        User testUser = new User(testDao);
        testUser.setUsername("invalidUser");
        assertFalse(testUser.deleteUser("invalidUser"));
    }
    
    @Test
    public void getAllUsersTest() throws IOException {
        TestUserDao testDao = new TestUserDao();
        User testUser = new User(testDao);
        testUser.getAllUsers();
        int userCount = testUser.getAllUsers().size();
        assertEquals(userCount,2);
    }
}
