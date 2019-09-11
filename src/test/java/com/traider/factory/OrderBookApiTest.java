package com.traider.factory;

import static org.junit.Assert.*;

import com.tradair.domain.Order;
import com.tradair.factory.OrderBookApi;
import org.junit.Before;
import org.junit.Test;
import java.util.Map;

public class OrderBookApiTest {

    private static final String SUCCESS = "{'status':'OK', 'code':'200'} ";
    private static final String FAILED = "{'status':'FAILED', 'code':'409'} ";
    private static final String NOT_FOUND = "{'status':'FAILED', 'code':'Object not found'} ";
    private static final String ADDED_TO_BOOK = "{'status':'OK', 'code':'Added to book'} ";
    private static final String DELETED_FROM_BOOK = "{'status':'OK', 'code':'Deleted from book'} ";
    private static final String MODIFIED = "{'status':'OK', 'code':'Order has been modified'} ";


    private OrderBookApi orderBookApi;

    @Before
    public void setUp() {
        this.orderBookApi = new OrderBookApi();
        mockBookStorage();
    }

   @Test
   public void processOrderTest(){
       long time = System.currentTimeMillis();
        Order o5 = new Order(1, "MM", "MICKEY", time, 15, 110, false);
        Order o6 = new Order(6, "MM", "MICKEY", time, 10, 90, true);
        Order o7 = new Order(7, "MM", "PIC", time, 1, 20, true);
        o7.setOrdertype(Order.Ordertype.LIMIT);
        assertEquals("Error processing order 5", MODIFIED, orderBookApi.processOrder(o5));
        assertEquals("Error processing order 6", DELETED_FROM_BOOK, orderBookApi.processOrder(o6));
        assertEquals("Error processing order 7", ADDED_TO_BOOK, orderBookApi.processOrder(o7));
   }


    @Test
    public void AddOrderTest() {
        assertEquals("Error in creating add order", 5, orderBookApi.getTotalOrders());
    }


    @Test
    public void modifyOrderTest() {

        long time = System.currentTimeMillis();
        Order o4 = new Order(3, "MM", "DAIS", time, 16, 90, false);
        Order o5 = new Order(1, "MM", "INVALID_CHANGE_HERE", time, 10, 110, false);
        orderBookApi.modifyOrder(o4);
        assertEquals("Error in modifing order o4", 16, orderBookApi.getOrder(3).getQuantity());
        assertEquals("Error in modifing order o5", FAILED + " Venue is not the same", orderBookApi.modifyOrder(o5));
    }

    @Test
    public void deleteOrder(){
        Order o = new Order();
        o.setId(1);
        assertEquals("Error deleting order with id: 1", SUCCESS, orderBookApi.deleteOrder(o));
        assertEquals("Error deleting order with id: 1", NOT_FOUND, orderBookApi.deleteOrder(o));
    }

    @Test
    public void getBestBid(){
        assertEquals("Error getting best bid", "110.0", String.valueOf(orderBookApi.getBestBid()));
    }

    @Test
    public void getBestOffer(){
        assertEquals("Error getting best offer", "90.0", String.valueOf(orderBookApi.getBestOffer()));
    }



    public void mockBookStorage() {

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
