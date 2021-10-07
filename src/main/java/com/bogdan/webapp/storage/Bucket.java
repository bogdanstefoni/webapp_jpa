package com.bogdan.webapp.storage;

public class Bucket<E1, E2> {

    private final E1 fist;
    private final E2 second;

    public Bucket(E1 fist, E2 second) {
        this.fist = fist;
        this.second = second;
    }

    public E1 getFist() {
        return fist;
    }

    public E2 getSecond() {
        return second;
    }
}
