package com.dalhousie.csci5308.mms.dao;
import com.dalhousie.csci5308.mms.model.IDbModel;
import com.dalhousie.csci5308.mms.model.Margin;
import java.io.IOException;
import java.util.List;

public class MarginDaoTest implements IDao{

    private Margin margin;
   private  static MarginDaoTest instance;
    public MarginDaoTest(){
        margin = new Margin();
        margin.setAvailable_margin(200.0);
        margin.setUserName("User");
    }

    public static Object getInstance() {
        if (instance==null){
            instance = new MarginDaoTest();
            return instance;
        }return instance;
    }

    @Override
    public int create(IDbModel modelObject) {
        return 0;
    }

    @Override
    public int update(IDbModel modelObject) {
        Margin marginObject = (Margin) modelObject;
        if(margin.getUserName().equals(marginObject.getUserName())) {
            return 1;
        }
        return 0;
    }

    @Override
    public int delete(IDbModel modelObject) {
        return 0;
    }

    @Override
    public IDbModel select(IDbModel modelObject) throws IOException {
       if (margin.getUserName().equals(((Margin)modelObject).getUserName()))
        {
            modelObject=margin;
            return modelObject;
        }
        return null;
    }

    @Override
    public List<IDbModel> selectAll() throws IOException {
        return null;
    }

    @Override
    public List<IDbModel> selectAll(IDbModel modelObject) throws IOException {
        return null;
    }
}
