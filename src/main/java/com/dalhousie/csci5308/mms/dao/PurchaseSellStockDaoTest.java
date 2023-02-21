package com.dalhousie.csci5308.mms.dao;
import com.dalhousie.csci5308.mms.model.IDbModel;
import com.dalhousie.csci5308.mms.model.PurchaseSellStock;
import java.io.IOException;
import java.util.List;

public class PurchaseSellStockDaoTest  implements  IDao{
    private PurchaseSellStock pss;
    private  static PurchaseSellStockDaoTest instance;
    public PurchaseSellStockDaoTest(){
        pss = new PurchaseSellStock();
        pss.setStockId(101);
        pss.setStockPrice(200);
    }

    public static IDao getInstance() {
        if (instance==null){
            instance = new PurchaseSellStockDaoTest();
            return instance;
        }return instance;
    }

    @Override
    public int create(IDbModel modelObject) {
        if(pss.getStockId()==((PurchaseSellStock)modelObject).getStockId()){
            return 1;
        }return 0;
    }

    @Override
    public int update(IDbModel modelObject) {
        return 0;
    }

    @Override
    public int delete(IDbModel modelObject) {
        if(pss.getStockPrice()==((PurchaseSellStock)modelObject).getStockPrice()){
            return 1;
        }return 0;
    }

    @Override
    public IDbModel select(IDbModel modelObject) throws IOException {
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
