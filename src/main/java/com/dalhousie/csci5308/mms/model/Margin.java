package com.dalhousie.csci5308.mms.model;
import com.dalhousie.csci5308.mms.dao.IDao;
import com.dalhousie.csci5308.mms.dao.MarginDao;
import java.io.IOException;

public class Margin implements IDbModel{
    private IDao marginDao;
    private String userName;
    private double available_margin;

    public Margin(IDao marginDao) {
        this.marginDao = marginDao;
    }

    public Margin() {
        this.marginDao = MarginDao.getInstance();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getAvailable_margin() {
        return available_margin;
    }

    public void setAvailable_margin(double available_margin) {
        this.available_margin = available_margin;
    }

    public double getUserMargin(IDbModel margin) throws IOException {
       Margin userMargin = (Margin) marginDao.select(margin);
       return userMargin.getAvailable_margin();
    }

    public int updateUserMargin(IDbModel fundsModel) {
        if(marginDao.update(fundsModel)==0){
            return 0;
        }
        return 1;
    }

}

