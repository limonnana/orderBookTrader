package com.tradair.domain;



public class Order implements Comparable{

    public enum Ordertype {LIMIT, MARKET};

    private int id;
    private String symbol;
    private String venue;
    private long time;
    private int quantity;
    private float price;
    private boolean bid;
    private Ordertype ordertype;

    public Order(){}

    public Order(int id, String symbol, String venue, long time, int quantity, float price, boolean bid){
        this.id = id;
        this.symbol = symbol;
        this.venue = venue;
        this.time = time;
        this.quantity = quantity;
        this.price = price;
        this.bid = bid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isBid() {
        return bid;
    }

    public void setBid(boolean bid) {
        this.bid = bid;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Ordertype getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(Ordertype ordertype) {
        this.ordertype = ordertype;
    }


    @Override
    public int compareTo(Object o) {
        int result = 0;
        Order otherOrder = (Order)o;
        if(this.getPrice() == otherOrder.getPrice()){
            return 0;
        }
        else if(this.getPrice() > otherOrder.getPrice()){
            return 1;
        }else{
            return -1;
        }
    }
}
