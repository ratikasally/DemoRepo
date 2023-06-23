package utils;

import pojo.CreateOrderRequest;
import pojo.Orders;

import java.util.*;

public class TestDataBuild {

    public String returnLoginPayload(String userEmail,String userPassword) {
        return "{\"userEmail\":\""+userEmail+"\",\"userPassword\":\""+userPassword+"\"}";
    }

    public HashMap<String,String> returnAddProductBody(String productName, String productCategory, String productSubCategory, String productPrice, String productDescription, String productFor,String productAddedBy) {

        HashMap<String,String> map = new HashMap<>();
        map.put("productName",productName);
        map.put("productCategory",productCategory);
        map.put("productSubCategory",productSubCategory);
        map.put("productPrice",productPrice);
        map.put("productDescription",productDescription);
        map.put("productFor",productFor);
        map.put("productAddedBy",productAddedBy);
        return map;
    }
    public CreateOrderRequest returnCreateOrderRequestPayload(){
        Orders order1 = new Orders();
        Orders order2 = new Orders();
        order1.setCountry("India");
        order1.setProductOrderedId("6262e990e26b7e1a10e89bfa");
        order2.setCountry("USA");
        order2.setProductOrderedId("6262e9d9e26b7e1a10e89c04");
        CreateOrderRequest cor = new CreateOrderRequest();
        List<Orders> list = new ArrayList<>();
        list.add(order1);
        list.add(order2);
        cor.setOrders(list);
        return cor;
    }
}
