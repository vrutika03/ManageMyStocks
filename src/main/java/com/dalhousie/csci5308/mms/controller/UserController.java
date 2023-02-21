package com.dalhousie.csci5308.mms.controller;

import com.dalhousie.csci5308.mms.model.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Controller
public class UserController {
    @GetMapping("/register")
    public String registerGET() {
        return "register";
    }

    @PostMapping("/register")
    public String registerPOST(@ModelAttribute("User") User currentUser,RedirectAttributes redirectAttributes) throws IOException {
        //using spring security password hashing algorithm
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        String encoded_pass = encoder.encode(currentUser.getPassword_hash());
        currentUser.setPassword_hash(encoded_pass);
        if(currentUser.createUser()) {
            return "home";
        }
        else {
            redirectAttributes.addFlashAttribute("errorMessage","ERROR: Username already exists");
            return "redirect:/register";
        }
    }

    @GetMapping("/user/Login")
    public String userLoginGET() throws IOException {
        return "user_login";
    }

    @GetMapping("/user/home")
    public ModelAndView userHome() throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        Portfolio portfolioModel = (Portfolio) ModelFactoryImpl.getInstance().createPortfolioModel();
        WatchList watchlistModel = (WatchList) ModelFactoryImpl.getInstance().createWatchlistModel();
        Margin marginModel = (Margin) ModelFactoryImpl.getInstance().createMarginModel();
        Funds fund = (Funds) ModelFactoryImpl.getInstance().createFundsModel();
        modelAndView.setViewName("user_home");
        modelAndView.addObject("user_portfolio", portfolioModel.getUserPortfolio());
        modelAndView.addObject("user_watchlist", watchlistModel.getUserWatchList());
        modelAndView.addObject("amount", fund.getUserFunds(fund));
        modelAndView.addObject("userAvlbMargin",marginModel.getUserMargin(marginModel));
        return modelAndView;
    }

    @GetMapping(value = "/logout")
    public String logoutPageGET(HttpServletRequest request, HttpServletResponse response) {
        CookieClearingLogoutHandler cookieClearingLogoutHandler = new CookieClearingLogoutHandler(AbstractRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY);
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        cookieClearingLogoutHandler.logout(request, response, null);
        securityContextLogoutHandler.logout(request, response, null);
        return "logout";
    }

    @PostMapping(value = "/logout")
    public String logoutPagePOST(HttpServletRequest request, HttpServletResponse response) {
        CookieClearingLogoutHandler cookieClearingLogoutHandler = new CookieClearingLogoutHandler(AbstractRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY);
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        cookieClearingLogoutHandler.logout(request, response, null);
        securityContextLogoutHandler.logout(request, response, null);
        return "logout";
    }

    @GetMapping(value = "/user/removeWatchlist")
    public String removeFromWatchlist(@RequestParam int stockID) {
        WatchList watchlist = (WatchList) ModelFactoryImpl.getInstance().createWatchlistModel();
        watchlist.setStockId(stockID);
        watchlist.removeFromWatchList(watchlist);
        return "redirect:/user/home";
    }

    @GetMapping(value = "/user/createWatchlist")
    public ModelAndView createWatchlist() throws Exception {
        Stock currentStock = (Stock)ModelFactoryImpl.getInstance().createStockModel();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("createWatchlist");
        modelAndView.addObject("list_of_stocks", currentStock.getAllStocks());
        return modelAndView;
    }

    @PostMapping(value = "/user/addToWatchlist")

    public String addToWatchlist(@ModelAttribute("Watchlist") WatchList watchlist, Errors errors, RedirectAttributes redirectAttributes) throws Exception {
        if (watchlist.addToWatchList(watchlist) == 0) {
            redirectAttributes.addFlashAttribute("errorMessage", "ERROR: Already exists in watchlist");
            return "redirect:/user/createWatchlist";
        } else {
            return "redirect:/user/home";
        }
    }

    @GetMapping(value = "/user/createFunds")
    public String createFunds() {
        return "createFunds";
    }

    @PostMapping(value = "/user/addFunds")
    public String addToFunds(@ModelAttribute("Funds") Funds funds, Errors errors, RedirectAttributes redirectAttributes) {
        if (funds.addUserFunds(funds) == 0) {
            redirectAttributes.addFlashAttribute("errorMessage", "ERROR: Amount must be greater than 0");
            return "redirect:/user/createFunds";
        } else {
            Margin marginModel = (Margin) ModelFactoryImpl.getInstance().createMarginModel();
            marginModel.updateUserMargin(funds);
            return "redirect:/user/home";
        }
    }

    @PostMapping("/user/purchase")
    public String purchaseStock(@ModelAttribute("PurchaseStock") PurchaseSellStock purchased_stock, Errors errors, RedirectAttributes redirectAttributes) throws Exception {
        Funds fund = ((Funds) ModelFactoryImpl.getInstance().createFundsModel());
        Stock stock = (Stock)ModelFactoryImpl.getInstance().createStockModel();
        try {
            stock.setStock_id(purchased_stock.getStockId());
            Stock retrieved_stock = (Stock) stock.selectStock();
            purchased_stock.setStockPrice(retrieved_stock.getStock_price());
            stock.setStock_id(purchased_stock.getStockId());
            purchased_stock.setStockPrice(retrieved_stock.getStock_price());
            if (purchased_stock.getStockQuantity() * purchased_stock.getStockPrice() >= fund.getUserFunds(fund)) {
                redirectAttributes.addFlashAttribute("errorMessage", "ERROR: Amount must be added before purchasing");
                return "redirect:/user/purchase";
            } else {
                purchased_stock.purchase();
                return "redirect:/user/home";
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/user/purchase")
    public ModelAndView userPurchaseGET() throws IOException {
        ModelAndView name = new ModelAndView();
        Stock stock=(Stock)ModelFactoryImpl.getInstance().createStockModel();
        name.setViewName("purchase");
        List<Stock> stocks = new ArrayList<>();
        stock.getAllStocks().forEach(object ->
        {
            stocks.add(((Stock) object));
        });
        name.addObject("list_of_stocks", stocks);
        return name;

    }

    @GetMapping("/user/sell")
    public ModelAndView sellStockGET() throws Exception {
        ModelAndView name = new ModelAndView();
        name.setViewName("sell");
        List<Portfolio> stocks = new ArrayList<>();
        Portfolio portfolio= (Portfolio) ModelFactoryImpl.getInstance().createPortfolioModel();
        portfolio.getUserPortfolio().forEach(object ->
        {
            stocks.add(((Portfolio) object));
        });
        name.addObject("list_of_stocks", stocks);
        return name;
    }

    @PostMapping("/user/sell")
    public String sellStockPOST(@ModelAttribute("SellStock") PurchaseSellStock sellStock, Errors errors, RedirectAttributes redirectAttributes) throws Exception {
        Portfolio quantity = (Portfolio) ModelFactoryImpl.getInstance().createPortfolioModel();
        Portfolio selected_stock = null;

        for (IDbModel iDbModel : quantity.getUserPortfolio()) {
            if (sellStock.getStockId() == ((Portfolio) iDbModel).getStockId()) {
                selected_stock = ((Portfolio) iDbModel);
            }
        }
        if (sellStock.getStockQuantity() > selected_stock.getStockQuantity()) {
            redirectAttributes.addFlashAttribute("errorMessage", "ERROR: Please enter valid value ");
            return "redirect:/user/sell";
        } else {
            Stock stock = (Stock)ModelFactoryImpl.getInstance().createStockModel();
            stock.setStock_id(sellStock.getStockId());
            Stock retrieved_stock = (Stock) stock.selectStock();
            sellStock.setStockPrice(retrieved_stock.getStock_price());
            sellStock.sell();
            return "redirect:/user/home";
        }
    }
}