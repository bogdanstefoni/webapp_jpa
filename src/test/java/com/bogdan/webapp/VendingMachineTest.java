package com.bogdan.webapp;

import com.bogdan.webapp.exception.NotSufficientChangeException;
import com.bogdan.webapp.exception.SoldOutException;
import com.bogdan.webapp.factory.VendingMachineFactory;
import com.bogdan.webapp.item.Item;
import com.bogdan.webapp.service.IVendingMachine;
import com.bogdan.webapp.service.VendingMachineImpl;
import com.bogdan.webapp.storage.Bucket;
import com.bogdan.webapp.storage.Cash;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class VendingMachineTest {
    private static IVendingMachine vendingMachine;

    private final Logger logger = LoggerFactory.getLogger(VendingMachineTest.class);

    @BeforeClass
    public static void setUp() {
        vendingMachine = VendingMachineFactory.createVendingMachine();
    }

    @AfterClass
    public static void tearDown() {
        vendingMachine = null;
    }

    @Test
    public void testBuyItemWithExactPrice() {
        long price = vendingMachine.selectItemAndGetPrice(Item.PEPSI);

        assertEquals(Item.PEPSI.getPrice(),price);

        vendingMachine.insertCoin(Cash.TWO_NOTE);
        vendingMachine.insertCoin(Cash.ONE_NOTE);
        vendingMachine.insertCoin(Cash.COIN);

        Bucket<Item, List<Cash>> bucket = vendingMachine.collectItemAndChange();
        Item item = bucket.getFist();
        List<Cash> change = bucket.getSecond();

        assertEquals(Item.PEPSI, item);
        vendingMachine.printStats();
        assertTrue(change.isEmpty());

        logger.info("I did it!!");

    }

    @Test
    public void testBuyItemWithMorePrice() {
        long price = vendingMachine.selectItemAndGetPrice(Item.FANTA);
        assertEquals(Item.FANTA.getPrice(), price);

        vendingMachine.insertCoin(Cash.TWO_NOTE);
        vendingMachine.insertCoin(Cash.TWO_NOTE);
        vendingMachine.insertCoin(Cash.TWO_NOTE);

        Bucket<Item, List<Cash>> bucket = vendingMachine.collectItemAndChange();
        Item item = bucket.getFist();
        List<Cash> change = bucket.getSecond();

        assertEquals(Item.FANTA, item);
        assertTrue(!change.isEmpty());
        assertEquals(60 - Item.FANTA.getPrice(), getTotal(change));


    }

    @Test
    public void testRefund(){
        long price = vendingMachine.selectItemAndGetPrice(Item.PEPSI);
        assertEquals(Item.PEPSI.getPrice(), price);
        vendingMachine.insertCoin(Cash.COIN);
        vendingMachine.insertCoin(Cash.ONE_NOTE);
        vendingMachine.insertCoin(Cash.TWO_NOTE);
        vendingMachine.insertCoin(Cash.TWO_NOTE);

        assertEquals(55, getTotal(vendingMachine.refund()));

    }

    @Test(expected = SoldOutException.class)
    public void testSoldOut() {
        for(int i = 0; i <= 5 ; i++){
            vendingMachine.selectItemAndGetPrice(Item.SODA);
            vendingMachine.insertCoin(Cash.TWO_NOTE);
            vendingMachine.insertCoin(Cash.TWO_NOTE);
            vendingMachine.insertCoin(Cash.ONE_NOTE);
            vendingMachine.printStats();
            vendingMachine.collectItemAndChange();
        }
    }

    @Test(expected = NotSufficientChangeException.class)
    public void testNotSufficientChangeException(){
        for(int i = 0 ; i <= 5; i++){
            vendingMachine.selectItemAndGetPrice(Item.FANTA);
            vendingMachine.insertCoin(Cash.TWO_NOTE);
            vendingMachine.insertCoin(Cash.TWO_NOTE);
            vendingMachine.insertCoin(Cash.ONE_NOTE);
            vendingMachine.collectItemAndChange();

            vendingMachine.selectItemAndGetPrice(Item.PEPSI);
            vendingMachine.insertCoin(Cash.TWO_NOTE);
            vendingMachine.insertCoin(Cash.TWO_NOTE);
            vendingMachine.collectItemAndChange();
            vendingMachine.printStats();
        }
    }

    @Test(expected = SoldOutException.class)
    public void testReset() {
        IVendingMachine vendingMachine = VendingMachineFactory.createVendingMachine();
        vendingMachine.reset();
        vendingMachine.printStats();

        vendingMachine.selectItemAndGetPrice(Item.PEPSI);
    }

    @Ignore
    public void testVendingMachineImpl() {
        VendingMachineImpl vm = new VendingMachineImpl();

    }

    private long getTotal(List<Cash> change) {
        long total = 0;
        for(Cash c: change) {
            total = total + c.getValue();
        }
        return total;
    }

}
