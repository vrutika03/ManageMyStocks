package com.dalhousie.csci5308.mms.controller;

import com.dalhousie.csci5308.mms.model.IDbModel;
import com.dalhousie.csci5308.mms.model.ModelFactoryImpl;
import com.dalhousie.csci5308.mms.model.Performance;
import com.dalhousie.csci5308.mms.model.Stock;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.*;

@Controller
public class HistoricalDataController {
        @GetMapping("/admin/performance")
    public ModelAndView getHistoricalData() throws IOException {
            ModelAndView modelAndView=new ModelAndView();
            Performance performance = (Performance) ModelFactoryImpl.getInstance().createPerformanceModel();
            Stock currentStock = (Stock) ModelFactoryImpl.getInstance().createStockModel();
            List<IDbModel> stockList= currentStock.getAllStocks();
            Iterator<IDbModel> stockIterator = stockList.iterator();
            performance.getPerformanceData(currentStock);
            List<String> listStockNames= new ArrayList<String>();
            modelAndView.setViewName("historical_data");
            while(stockIterator.hasNext())
                listStockNames.add(((Stock)(stockIterator.next())).getStock_name());
            modelAndView.addObject("stocks",listStockNames);
        return modelAndView;
    }
    @PostMapping("/admin/performance")
    public ModelAndView viewHistoricalData(@ModelAttribute("stock_name") String stockName) throws IOException {
        ModelAndView modelAndView=new ModelAndView();
        Map<String, Integer> graphData = new TreeMap<>();
        Performance performance = (Performance) ModelFactoryImpl.getInstance().createPerformanceModel();
        Stock currentStock =  (Stock) ModelFactoryImpl.getInstance().createStockModel();
        currentStock.setStock_name(stockName);
        List<IDbModel> performanceData=performance.getPerformanceData(currentStock);
        Iterator<IDbModel> performanceIterator = performanceData.iterator();
        while(performanceIterator.hasNext())
        {
            Performance currentPerformance=(Performance)(performanceIterator.next());
            graphData.put(currentPerformance.getDate(), (int) currentPerformance.getPrice());
        }
        List<IDbModel> stockList= currentStock.getAllStocks();
        Iterator<IDbModel> stockIterator = stockList.iterator();
        performance.getPerformanceData(currentStock);
        List<String> listStockNames= new ArrayList<String>();
        modelAndView.addObject("chartData", graphData);
        modelAndView.addObject("stockName",currentStock.getStock_name());
        while(stockIterator.hasNext())
            listStockNames.add(((Stock)(stockIterator.next())).getStock_name());
        modelAndView.addObject("stocks",listStockNames);
        modelAndView.setViewName("historical_data");
        return modelAndView;
    }
    @GetMapping("/user/performance")
    public ModelAndView getHistoricalData_user() throws IOException {
        ModelAndView modelAndView=new ModelAndView();
        Performance performance = (Performance) ModelFactoryImpl.getInstance().createPerformanceModel();
        Stock currentStock =  (Stock) ModelFactoryImpl.getInstance().createStockModel();
        List<IDbModel> stockList= currentStock.getAllStocks();
        Iterator<IDbModel> stockIterator = stockList.iterator();
        performance.getPerformanceData(currentStock);
        List<String> listStockNames= new ArrayList<String>();
        modelAndView.setViewName("historical_data_user");
        while(stockIterator.hasNext())
            listStockNames.add(((Stock)(stockIterator.next())).getStock_name());
        modelAndView.addObject("stocks",listStockNames);
        return modelAndView;
    }
    @PostMapping("/user/performance")
    public ModelAndView viewHistoricalData_user(@ModelAttribute("stock_name") String stockName) throws IOException {
        ModelAndView modelAndView=new ModelAndView();
        Map<String, Integer> graphData = new TreeMap<>();
        Performance performance = (Performance) ModelFactoryImpl.getInstance().createPerformanceModel();
        Stock currentStock =  (Stock) ModelFactoryImpl.getInstance().createStockModel();
        currentStock.setStock_name(stockName);
        List<IDbModel> performanceData=performance.getPerformanceData(currentStock);
        Iterator<IDbModel> performanceIterator = performanceData.iterator();
        while(performanceIterator.hasNext())
        {
            Performance currentPerformance=(Performance)(performanceIterator.next());
            graphData.put(currentPerformance.getDate(), (int) currentPerformance.getPrice());
        }
        List<IDbModel> stockList= currentStock.getAllStocks();
        Iterator<IDbModel> stockIterator = stockList.iterator();
        performance.getPerformanceData(currentStock);
        List<String> listStockNames= new ArrayList<String>();
        modelAndView.addObject("chartData", graphData);
        modelAndView.addObject("stockName",currentStock.getStock_name());
        while(stockIterator.hasNext())
            listStockNames.add(((Stock)(stockIterator.next())).getStock_name());
        modelAndView.addObject("stocks",listStockNames);
        modelAndView.setViewName("historical_data_user");
        return modelAndView;
    }
}