package com.tradair.factory;

import com.tradair.domain.Order;

import java.util.*;

public class OrderBookApi {

    private static final String SUCCESS = "{'status':'OK', 'code':'200'} ";
    private static final String FAILED = "{'status':'FAILED', 'code':'409'} ";
    private static final String NOT_FOUND = "{'status':'FAILED', 'code':'Object not found'} ";
    private static final String ADDED_TO_BOOK = "{'status':'OK', 'code':'Added to book'} ";
    private static final String DELETED_FROM_BOOK = "{'status':'OK', 'code':'Deleted from book'} ";
    private static final String MODIFIED = "{'status':'OK', 'code':'Order has been modified'} ";

    private  static OrderBookStorage orderBookStorage = new OrderBookStorage();

    private  Map<Integer, Order> m = getOrderBookStorage().getMapOrders();

    public String processOrder(Order o){

        if(getOrder(o.getId()) != null){  //order exist change quantity
            modifyOrder(o);
            return MODIFIED;
        }

        Order bestOffer = getOffers().get(0);
        Order bestBid = getBids().get(0);
        String result = FAILED;

        if(o.isBid()){

            if(o.getPrice()>= bestOffer.getPrice()) {  //  buy right away)
                deleteOrder(bestOffer);  //buy
                result = DELETED_FROM_BOOK;
            }else
             if (o.getPrice()< bestOffer.getPrice() && o.getOrdertype() == Order.Ordertype.LIMIT) // Wait
             {
                 addOrder(o);
                 result = ADDED_TO_BOOK;
             }
        }else{  // is offer

             if(o.getPrice()<= bestBid.getPrice()){
                //Sell
                deleteOrder(o);
                result = DELETED_FROM_BOOK;
            }else{

                if(o.getPrice() > bestBid.getPrice() && o.getOrdertype() == Order.Ordertype.LIMIT){ //Wait
                    addOrder(o);
                    result = ADDED_TO_BOOK;
                }
            }
        }
        return result;
    }

    public String addOrder(Order o){
        String result = FAILED;
        getM().put(o.getId(), o);
        result = SUCCESS;
        return result;
    }

    public String modifyOrder(Order o){
        String result = SUCCESS;
        Order fromDb = getOrder(o.getId());

        try{
            checkOrderPropertiesAreEqual(o);
        }catch(Exception e){
           result = FAILED  + e.getMessage();
        }

        getM().put(fromDb.getId(), o);
        return result;
    }

    public String deleteOrder(Order o){
        String result = SUCCESS;
        if(getM().remove(o.getId()) == null){
           result = NOT_FOUND;
        }
        return result;
    }

    public float getBestBid(){
        List<Order> bids = getBids();
        return bids.get(0).getPrice();
    }

    public float getBestOffer(){
        List<Order> offers = getOffers();
        return offers.get(0).getPrice();
    }

    public List<Order> getBids(){
        List<Order> bids = getBidsFromStorage();
        Collections.sort(bids, Collections.reverseOrder());
        return bids;
    }

    public List<Order> getOffers(){
        List<Order> offers = getOffersFromStorage();
        Collections.sort(offers);
        return offers;
    }

    public Order getOrder(int id){
        return this.getM().get(id);
    }

    public int getTotalOrders(){
        return this.getM().keySet().size();
    }

    private List<Order> getBidsFromStorage(){
        List<Order> bids = new ArrayList<>();
        List<Order> allOrders = new ArrayList<>(this.getM().values());
        for(Order o : allOrders){
            if(o.isBid()){
                bids.add(o);
            }
        }
        return bids;
    }

    private List<Order> getOffersFromStorage(){
        List<Order> offers = new ArrayList<>();
        List<Order> allOrders = new ArrayList<>(this.getM().values());
        for(Order o : allOrders){
            if(!o.isBid()){
                offers.add(o);
            }
        }
        return offers;
    }


    private void checkOrderPropertiesAreEqual(Order o) throws Exception {

        Order orderStored = this.getM().get(o.getId());

        if(orderStored.getPrice() != o.getPrice()){
            throw new Exception(" Price is not the same");
        }
        if(orderStored.isBid() != o.isBid()){
            throw new Exception(" Bid is not the same");
        }
        if(!orderStored.getSymbol().equals(o.getSymbol())){
            throw new Exception(" Symbol is not the same");
        }
        if(!orderStored.getVenue().equals(o.getVenue())){
            throw new Exception(" Venue is not the same");
        }

    }

    public OrderBookStorage getOrderBookStorage() {
        return orderBookStorage;
    }

    public void setOrderBookStorage(OrderBookStorage orderBookStorage) {
        this.orderBookStorage = orderBookStorage;
    }

    public Map<Integer, Order> getM() {
        return m;
    }

    public void setM(Map<Integer, Order> m) {
        this.m = m;
    }
}
