package com.bogdan.webapp.service;

import com.bogdan.webapp.exception.NotFullPaidException;
import com.bogdan.webapp.exception.NotSufficientChangeException;
import com.bogdan.webapp.exception.SoldOutException;
import com.bogdan.webapp.item.Item;
import com.bogdan.webapp.storage.Bucket;
import com.bogdan.webapp.storage.Cash;
import com.bogdan.webapp.storage.Inventory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VendingMachineImpl implements IVendingMachine {

    private final Inventory<Cash> bankStorage = new Inventory<>();
    private final Inventory<Item> vendingStorage = new Inventory<>();
    private long totalSales;
    private Item currentItem;
    private long currentBalance;

    public VendingMachineImpl() {
        initialize();
    }

    public void initialize() {

        for (Cash c : Cash.values()) {
            bankStorage.put(c, 5);
        }
        for (Item i : Item.values()) {
            vendingStorage.put(i, 5);
        }
    }

    @Override
    public long selectItemAndGetPrice(Item item) {
        if (vendingStorage.hasItem(item)) {
            currentItem = item;

            return currentItem.getPrice();
        }

        throw new SoldOutException("Sold out, please buy another item");
    }

    @Override
    public void insertCoin(Cash cash) {
        currentBalance = currentBalance + cash.getValue();
        bankStorage.addItem(cash);

    }

    @Override
    public List<Cash> refund() {
        List<Cash> refund = getChange(currentBalance);
        updateCashInventory(refund);
        currentBalance = 0;
        currentItem = null;

        return refund;
    }

    @Override
    public Bucket<Item, List<Cash>> collectItemAndChange() {
        Item item = collectItem();
        totalSales = totalSales + currentItem.getPrice();

        List<Cash> change = collectChange();

        return new Bucket<Item, List<Cash>>(item, change);
    }

    private Item collectItem() throws NotSufficientChangeException, NotFullPaidException {
        if (isFullPaid()) {
            if (hasSufficientChange()) {
                vendingStorage.deductItem(currentItem);
                return currentItem;
            }
            throw new NotSufficientChangeException("Not sufficient change to return");
        }
        long remainingBalance = currentItem.getPrice() - currentBalance;

        throw new NotFullPaidException("Price is not full paid, remaining: ", remainingBalance);

    }

    private List<Cash> collectChange() {
        long changeAmount = currentBalance - currentItem.getPrice();
        List<Cash> change = getChange(changeAmount);
        updateCashInventory(change);
        currentBalance = 0;
        currentItem = null;
        return change;
    }

    private boolean isFullPaid() {
        return currentBalance >= currentItem.getPrice();
    }

    private List<Cash> getChange(long amount) throws NotSufficientChangeException {
        List<Cash> changes = Collections.emptyList();

        if (amount > 0) {
            changes = new ArrayList<Cash>();
            long balance = amount;
            while (balance > 0) {
                if (balance >= Cash.COIN.getValue() && bankStorage.hasItem(Cash.COIN)) {
                    changes.add(Cash.COIN);
                    balance = balance - Cash.COIN.getValue();
                    continue;

                } else if (balance >= Cash.ONE_NOE.getValue() && bankStorage.hasItem(
                        Cash.ONE_NOE)) {
                    changes.add(Cash.ONE_NOE);
                    balance = balance - Cash.ONE_NOE.getValue();
                    continue;

                } else if (balance >= Cash.TWO_NOTE.getValue() && bankStorage.hasItem(
                        Cash.TWO_NOTE)) {
                    changes.add(Cash.TWO_NOTE);
                    balance = balance - Cash.TWO_NOTE.getValue();
                    continue;
                } else {
                    throw new NotSufficientChangeException(
                            "Not sufficient change," + "Please try another product");
                }

            }

        }
        return changes;
    }

    @Override
    public void reset() {
        bankStorage.clear();
        vendingStorage.clear();
        totalSales = 0;
        currentItem = null;
        currentBalance = 0;
    }

    @Override
    public void printStats() {
        System.out.println("Total sales: " + totalSales);
        System.out.println("Current vending storage : " + vendingStorage);
        System.out.println("Current bank storage: " + bankStorage);
    }

    private boolean hasSufficientChange() {
        return hasSufficientChangeForAmount(currentBalance - currentItem.getPrice());
    }

    private boolean hasSufficientChangeForAmount(long amount) {
        boolean hasChange = true;
        try {
            getChange(amount);
        } catch (NotSufficientChangeException ex) {
            return hasChange = false;
        }
        return hasChange;
    }

    private void updateCashInventory(List<Cash> change) {
        for (Cash c : change) {
            bankStorage.deductItem(c);
        }
    }

    public long getTotalSales() {
        return totalSales;
    }
}
