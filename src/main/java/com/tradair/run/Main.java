package com.tradair.run;

import com.tradair.domain.Order;
import com.tradair.factory.OrderBookApi;
import java.util.List;
import java.util.Map;

public class Main {

    private OrderBookApi orderBookApi = new OrderBookApi();
    private static final String SEPARATOR = " | ";
    private static final String SEPARATOR_EMPTY = "_________________________";

    public static void main(String[] args) {
        Main main = new Main();
        main.displayOrderBook();
    }

    public void displayOrderBook(){
        StringBuilder row = new StringBuilder();
        int loopSize = 0;
        setUp();
        List<Order> bids = orderBookApi.getBids();
        List<Order> offers = orderBookApi.getOffers();
        if(bids.size()>offers.size()){
            loopSize = bids.size();
        }else{
            loopSize = offers.size();
        }
        System.out.println(" ");
        System.out.println("------------ " + bids.get(0).getSymbol().toUpperCase() + " ----------------");

        for(int i=0;i<loopSize;i++){
            if(i < bids.size()) {
                row.append(SEPARATOR);
                 row.append(bids.get(i).getId());
                 row.append(SEPARATOR);
                 row.append(bids.get(i).getVenue());
                row.append(SEPARATOR);
                 row.append(bids.get(i).getQuantity());
                row.append(SEPARATOR);
                 row.append(bids.get(i).getPrice());
                row.append( " || ");
            }else{
                row.append(SEPARATOR_EMPTY);
            }
            if(i < offers.size()){
                row.append(offers.get(i).getPrice());
                row.append(SEPARATOR);
                row.append(offers.get(i).getQuantity());
                row.append(SEPARATOR);
                row.append(offers.get(i).getVenue());
                row.append(SEPARATOR);
                row.append(offers.get(i).getId());
                row.append(SEPARATOR);
            }else{
                row.append(SEPARATOR_EMPTY);
            }
            System.out.println(row.toString());
            row = new StringBuilder();
        }
    }

    private void setUp() {
        this.orderBookApi = new OrderBookApi();
        mockBookStorage();
    }

    private void mockBookStorage() {

        Map<Integer, Order> mapOrders = this.orderBookApi.getM();
        long time = System.currentTimeMillis();
        Order o1 = new Order(2, "MM", "PIC", time, 1, 110, true);
        Order o2 = new Order(4, "MM", "MIN", time, 4, 105, true);
        Order o3 = new Order(5, "MM", "PIV", time, 10, 95, true);
        Order o4 = new Order(3, "MM", "DAIS", time, 4, 90, false);
        Order o5 = new Order(1, "MM", "MICKEY", time, 10, 110, false);

        mapOrders.put(o1.getId(), o1);
        mapOrders.put(o2.getId(), o2);
        mapOrders.put(o3.getId(), o3);
        mapOrders.put(o4.getId(), o4);
        mapOrders.put(o5.getId(), o5);

    }


}
