package com.bogdan.webapp.storage;

public enum Cash {

    COIN(5), ONE_NOE(10), TWO_NOTE(20);

    private int value;

    Cash(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
