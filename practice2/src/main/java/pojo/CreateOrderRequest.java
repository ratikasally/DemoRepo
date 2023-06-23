package pojo;

import java.util.List;

public class CreateOrderRequest {
    private List<Orders> orders;

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }

    public List<Orders> getOrders(){
        return orders;
    }
}
