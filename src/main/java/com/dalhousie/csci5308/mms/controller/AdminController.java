package com.dalhousie.csci5308.mms.controller;

import com.dalhousie.csci5308.mms.model.IDbModel;
import com.dalhousie.csci5308.mms.model.ModelFactoryImpl;
import com.dalhousie.csci5308.mms.model.Stock;
import com.dalhousie.csci5308.mms.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
@Controller
public class AdminController { // Controller class uses the Get-Post-Redirect Get Pattern so that elements can be done in the same page.
    String  userList;
    @GetMapping("/admin/home")
    public ModelAndView adminPage()
    {
        ModelAndView modelAndView=new ModelAndView();
        Stock currentStock = (Stock)ModelFactoryImpl.getInstance().createStockModel();

        try {
            List<IDbModel> stocks=currentStock.getAllStocks();
            List<Stock> stockList=new ArrayList<>();
            stocks.stream().forEach(stock->stockList.add((Stock)stock));
            modelAndView.setViewName("admin_home");
            modelAndView.addObject("available_stocks",stocks);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return modelAndView;
    }
    @PostMapping("/admin/listUsers")
    public String listUsers()
    {
        User currentUser = (User)ModelFactoryImpl.getInstance().createUserModel();
        try {
            userList=currentUser.getAllUsers().toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/admin/listUsers";
    }
    @GetMapping("/admin/listUsers")
    public String getListUsers(Model model) {
        model.addAttribute("users", userList);
        return "redirect:/admin/home";
   }
@GetMapping("/admin/modifyUsers")
    public ModelAndView getModifyUsers(Model model) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
    User currentUser = (User)ModelFactoryImpl.getInstance().createUserModel();
    List<IDbModel> userList= null;
    userList = currentUser.getAllUsers();
    Iterator<IDbModel> userIterator = userList.iterator();
    List<String> listUserNames = new ArrayList<String>();
    List<String> roleList=new ArrayList<String>();
    roleList.add("USER");
    roleList.add("ADMIN");
    while(userIterator.hasNext())
     listUserNames.add(((User)(userIterator.next())).getUsername());
    modelAndView.setViewName("modifyUsers");
    modelAndView.addObject("usersList",listUserNames);
    modelAndView.addObject("rolesList",roleList);
    return modelAndView;
}
@PostMapping("/admin/updateUser")
    public String modifyUsers(@ModelAttribute("users") String username,@ModelAttribute("roles") String role)
{
    User currentUser = (User)ModelFactoryImpl.getInstance().createUserModel();
    try {
        currentUser.updateUser(username,role);
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
    return "redirect:/admin/home";
}
@GetMapping("/admin/deleteUser")
    public String getDeleteUsers(Model model) throws IOException {
        User currentUser = (User)ModelFactoryImpl.getInstance().createUserModel();
        List<IDbModel> userList= null;
        userList = currentUser.getAllUsers();
        Iterator<IDbModel> userIterator = userList.iterator();
        List<String> listUserNames = new ArrayList<String>();
         while(userIterator.hasNext())
            listUserNames.add(((User)userIterator.next()).getUsername());
        model.addAttribute("usersList",listUserNames);
        return "deleteUser";
    }
    @PostMapping("/admin/deleteUser")
    public String deleteUsers(@ModelAttribute("users") String username)
    {
        User currentUser = (User)ModelFactoryImpl.getInstance().createUserModel();
        try {
            currentUser.deleteUser(username);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/admin/home";
    }
    @GetMapping("/admin/Login")
    public String adminLoginGET() throws IOException {
        return "admin_login";
    }
    @PostMapping("/admin/addStock")
    public String addStock(@ModelAttribute("Stock") Stock newstock)
    {
        newstock.addStock();
        return "redirect:/admin/home";
    }
    @GetMapping("/admin/removeStock")
    public String removeStock(@RequestParam int stockID) throws IOException {
        Stock currentStock = (Stock)ModelFactoryImpl.getInstance().createStockModel();
        currentStock.setStock_id(stockID);
        Stock deletedStock =  (Stock)currentStock.selectStock();
        deletedStock.deleteStock();
        return "redirect:/admin/home";
    }
    @GetMapping("/admin/updateStock")
    public ModelAndView updateStock(@RequestParam int stockID)
    {
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("updateStock");
        try
        {
            Stock currentStock = (Stock)ModelFactoryImpl.getInstance().createStockModel();
            currentStock.setStock_id(stockID);
            IDbModel updating_stock= currentStock.selectStock();
            modelAndView.addObject("updating_stock",(Stock)updating_stock);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return modelAndView;
    }
    @PostMapping("/admin/updateStock")
    public String updateStock(@ModelAttribute("Stock") Stock updated_stock) {
        updated_stock.updateStockPrice();
        return "redirect:/admin/home";
    }
}
