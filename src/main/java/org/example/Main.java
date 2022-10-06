package org.example;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        OrderFileProcessor orderFileProcessor = new OrderFileProcessor(args[0], args[1]);
        List<Order> orderList = orderFileProcessor.loadOrderFromSource();
        orderFileProcessor.updateNewOrders(orderList);
    }
}