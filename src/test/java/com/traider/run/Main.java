package com.traider.run;

import com.tradair.domain.Order;
import com.tradair.factory.OrderBookApi;
import com.traider.factory.OrderBookApiTest;
import java.util.List;

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
        OrderBookApiTest orderBookApiTest = new OrderBookApiTest();
        orderBookApiTest.setUp();
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

}
