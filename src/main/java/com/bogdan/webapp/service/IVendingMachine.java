package com.bogdan.webapp.service;

import com.bogdan.webapp.item.Item;
import com.bogdan.webapp.storage.Bucket;
import com.bogdan.webapp.storage.Cash;

import java.util.List;

public interface IVendingMachine {

     long selectItemAndGetPrice(Item item);

     void insertCoin(Cash cash);

     List<Cash> refund();

     Bucket<Item, List<Cash>> collectItemAndChange();

     void reset();

     void printStats();

}
