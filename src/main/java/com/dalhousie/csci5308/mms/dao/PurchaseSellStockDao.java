package com.dalhousie.csci5308.mms.dao;
import com.dalhousie.csci5308.mms.model.*;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.core.context.SecurityContextHolder;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class PurchaseSellStockDao implements IDao  {
    private static sqlStoredProcedureExecutor executor;
    private static PurchaseSellStockDao instance;

    PurchaseSellStockDao()
    {
        executor=sqlStoredProcedureExecutor.getInstance();
    }

    public static PurchaseSellStockDao getInstance()
    {
        if(instance==null)
        {
            instance=new PurchaseSellStockDao();
            return  instance;
        }
        else
        {
            return instance;
        }
    }

    int getModifiedRowCount(Map<String, Object> result) // GET NUMBER OF MODIFIED ROWS
    {
        return (Integer)result.get("#update-count-1");
    }

    //this function works as a purchase functionality

    @Override
    public int create(IDbModel purchased_stock) {
        SqlParameterSource source=new MapSqlParameterSource()
                .addValue("username", SecurityContextHolder.getContext().getAuthentication().getName())
                .addValue("purchasePrice",((PurchaseSellStock)purchased_stock).getStockPrice())
                .addValue("qty",((PurchaseSellStock)purchased_stock).getStockQuantity())
                .addValue("stockid",((PurchaseSellStock)purchased_stock).getStockId());
        Map result=null;
        try {
            result=executor.executeProcedure("stockPurchase",source);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if(getModifiedRowCount(result)==1)
        {
            Funds fund=((Funds)ModelFactoryImpl.getInstance().createFundsModel());
            fund.setAmount(((PurchaseSellStock)purchased_stock).getStockPrice()*((PurchaseSellStock)purchased_stock).getStockQuantity());
            fund.setUserName(SecurityContextHolder.getContext().getAuthentication().getName());
            fund.removeFromUserFunds(fund);
            double tempAmt = fund.getAmount();
            fund.setAmount(-tempAmt);
            Margin margin = (Margin) ModelFactoryImpl.getInstance().createMarginModel();
            margin.updateUserMargin(fund);
            return 1;
        }
        return 0;
    }
    @Override
    public int update(IDbModel modelObject) {
        return 0;
    }

    // this works as selling functionality

    @Override
    public int delete(IDbModel sellstock) {
        SqlParameterSource source=new MapSqlParameterSource()
                .addValue("username", SecurityContextHolder.getContext().getAuthentication().getName())
                .addValue("qty",((PurchaseSellStock)sellstock).getStockQuantity())
                .addValue("stockid",((PurchaseSellStock)sellstock).getStockId());
        Map result=null;
        try {
            result=executor.executeProcedure("stockSell",source);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(getModifiedRowCount(result)>1)
        {
            Funds fund=((Funds)ModelFactoryImpl.getInstance().createFundsModel());
            fund.setAmount(((PurchaseSellStock)sellstock).getStockPrice()*((PurchaseSellStock)sellstock).getStockQuantity());
            fund.setUserName(SecurityContextHolder.getContext().getAuthentication().getName());
            fund.addUserFunds(fund);
            Margin margin = (Margin) ModelFactoryImpl.getInstance().createMarginModel();
            margin.updateUserMargin(fund);
            return 1;
        }
        return 0;
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
