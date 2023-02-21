package com.dalhousie.csci5308.mms.model;

import com.dalhousie.csci5308.mms.dao.IDao;
import com.dalhousie.csci5308.mms.dao.UserDao;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
public class User implements IDbModel{
    private int id;
    private String username;
    private String email;
    private String password_hash;
    private String role;
    private String pan;
    private int enabled;

    private IDao userDao;

    public User(){
        userDao=UserDao.getInstance();
    }

    public User(IDao userDao) {
        this.userDao = userDao;
    }
    public User(String username, String email, String password_hash, String role, String pan)
    {
        this.username=username;
        this.password_hash=password_hash;
        this.role=role;
        this.email=email;
        this.pan=pan;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public int getEnabled()
    {
        return enabled;
    }
    public void setEnabled(int code)
    {
        this.enabled=code;
    }
    public boolean createUser() throws IOException {
        Iterator<IDbModel> userIterator = getAllUsers().iterator();
        while (userIterator.hasNext())
        {
            User currentUser = (User)userIterator.next();
            if(this.getUsername().equalsIgnoreCase(currentUser.getUsername()))
                return false;
        }
            if (this.userDao.create(this) > 0) {
                return true;
            }
            return false;
    }
    public boolean deleteUser(String username) throws IOException {
        setUsername(username);
        if(this.userDao.delete(this)>0) {
            return true;
        }
        return false;
    }
    public boolean updateUser(String username,String role ) throws IOException {
        setUsername(username);
        setRole(role);
        if(this.userDao.update(this)>0) {
            return true;
        }
        return false;
    }
    public List<IDbModel> getAllUsers() throws IOException {
        return this.userDao.selectAll();
    }
}