package com.servlet.service;

import java.util.HashMap;
import java.util.Map;

public class Inventory<T> {
    private Map<T, Integer> inventory;

    public Inventory() {
        inventory = new HashMap<>();
    }

    public void add(T item) {
        int count = 0;
        if (inventory.containsKey(item)) {
            count = inventory.get(item);
        }
        inventory.put(item, count + 1);
    }

    public void deduct(T item) {
        if (hasItem(item)) {
            int count = inventory.get(item);
            inventory.put(item, count - 1);
        }
    }

    public boolean hasItem(T item) {
        return inventory.containsKey(item) && inventory.get(item) > 0;
    }

    public void clear() {
        inventory.clear();
    }

    public void put(T item, int quantity) {
        inventory.put(item, quantity);
    }
}
