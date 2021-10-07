package com.bogdan.webapp.item;

public enum Item {
    PEPSI("Pepsi", 35), FANTA("Fanta", 45), SODA("Soda", 50);

    private final String name;
    private final int price;

    Item(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }
}
