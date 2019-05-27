package com.servlet.service;

import com.servlet.dao.MongoProductDao;
import com.servlet.dao.ProductDao;
import com.servlet.entity.ChangeViewModel;
import com.servlet.entity.Coin;
import com.servlet.entity.Product;
import com.servlet.exceptions.ChangeAvailableException;
import com.servlet.exceptions.NoProductException;
import com.servlet.exceptions.NotEnoughMoneyException;
import com.servlet.exceptions.NotSufficientChangeException;

import java.math.BigDecimal;
import java.util.*;

public class MachineService {
    private Inventory<Coin> cashInventory;

    private BigDecimal balance;
    private ProductDao productDao;

    public MachineService() {
        this.cashInventory = new Inventory<>();
        this.balance = BigDecimal.ZERO;
        this.productDao = new MongoProductDao();

        for(Coin c : Coin.values()){
            cashInventory.put(c, 5);
        }
    }

    public String addMoney(String amount) {
        BigDecimal denomination = BigDecimal.ZERO;

        switch (amount) {
            case "dollar":
                balance = balance.add(Coin.DOLLAR.getDenomination());
                cashInventory.add(Coin.DOLLAR);
                denomination = Coin.DOLLAR.getDenomination();
                break;
            case "quarter":
                balance = balance.add(Coin.QUARTER.getDenomination());
                cashInventory.add(Coin.QUARTER);
                denomination = Coin.QUARTER.getDenomination();
                break;
            case "dime":
                balance = balance.add(Coin.DIME.getDenomination());
                cashInventory.add(Coin.DIME);
                denomination = Coin.DIME.getDenomination();
                break;
            case "nickel":
                balance = balance.add(Coin.NICKEL.getDenomination());
                cashInventory.add(Coin.NICKEL);
                denomination = Coin.NICKEL.getDenomination();
                break;
        }

        return denomination.toString();
    }

    public List<Product> getListOfProducts() {
        return this.productDao.getAllProducts();
    }

    private List<ChangeViewModel> convertChangeToViewModelList(List<Coin> change) {
        List<ChangeViewModel> changeViewModelList = new ArrayList<>();

        for (Coin c : new HashSet<>(change)) {
            changeViewModelList.add(new ChangeViewModel(c.toString(),
                    String.valueOf(Collections.frequency(change, c))));
        }

        return changeViewModelList;
    }

    public void returnChangeWithoutPurchase() throws NotSufficientChangeException, ChangeAvailableException {
        List<Coin> change = this.getChange(this.balance);
        balance = BigDecimal.ZERO;

        if (!change.isEmpty()) {
            this.updateCashInventory(change);
            throw new ChangeAvailableException(this.convertChangeToViewModelList(change));
        }
    }

    public void makePurchase(int productNumber) throws NoProductException, NotEnoughMoneyException, NotSufficientChangeException, ChangeAvailableException {
        Product product = this.productDao.getProductById(productNumber);
        if (product == null) {
            throw new NoProductException();
        }

        BigDecimal selectionPrice = product.getPrice();
        if (product.getQuantity() <= 0) {
            throw new NoProductException();
        } else if (balance.compareTo(selectionPrice) < 0) {
            BigDecimal difference = selectionPrice.subtract(balance);
            throw new NotEnoughMoneyException(difference.toString());
        } else {
            BigDecimal newBalance = balance.subtract(selectionPrice);
            List<Coin> change = this.getChange(newBalance);
            balance = BigDecimal.ZERO;
            this.productDao.updateProductQuantity(productNumber, product.getQuantity() - 1);

            if (!change.isEmpty()) {
                this.updateCashInventory(change);
                throw new ChangeAvailableException(this.convertChangeToViewModelList(change));
            }
        }
    }

    private List<Coin> getChange(BigDecimal amount) throws NotSufficientChangeException {
        List<Coin> changes = new ArrayList<>();
        BigDecimal localBalance = new BigDecimal(amount.toString());

        if (localBalance.signum() != 0) {
            while (localBalance.signum() == 1) {
                if (localBalance.compareTo(Coin.DOLLAR.getDenomination()) >= 0
                        && cashInventory.hasItem(Coin.DOLLAR)) {
                    changes.add(Coin.DOLLAR);
                    localBalance = localBalance.subtract(Coin.DOLLAR.getDenomination());
                } else if (localBalance.compareTo(Coin.NICKEL.getDenomination()) >= 0
                        && cashInventory.hasItem(Coin.NICKEL)) {
                    changes.add(Coin.NICKEL);
                    localBalance = localBalance.subtract(Coin.NICKEL.getDenomination());

                } else if (localBalance.compareTo(Coin.QUARTER.getDenomination()) >= 0
                        && cashInventory.hasItem(Coin.QUARTER)) {
                    changes.add(Coin.QUARTER);
                    localBalance = localBalance.subtract(Coin.QUARTER.getDenomination());

                } else if (localBalance.compareTo(Coin.DIME.getDenomination()) >= 0
                        && cashInventory.hasItem(Coin.DIME)) {
                    changes.add(Coin.DIME);
                    localBalance = localBalance.subtract(Coin.DIME.getDenomination());
                } else {
                    throw new NotSufficientChangeException();
                }
            }
        }

        return changes;
    }

    private void updateCashInventory(List<Coin> change) {
        for (Coin c : change) {
            cashInventory.deduct(c);
        }
    }
}
