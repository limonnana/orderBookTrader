package com.tradair.factory;

import com.tradair.domain.Order;

import java.util.HashMap;
import java.util.Map;

public class OrderBookStorage {

    private static Map<Integer, Order> mapOrders = new HashMap<>();

    public static Map<Integer, Order> getMapOrders() {
        return mapOrders;
    }


}
