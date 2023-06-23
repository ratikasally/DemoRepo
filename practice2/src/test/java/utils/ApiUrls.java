package utils;

public enum ApiUrls {
    LoginAPI("api/ecom/auth/login"),
    AddProductAPI("api/ecom/product/add-product"),
    DeleteProductAPI("api/ecom/product/delete-product"),
    GetOrderDetailsAPI("api/ecom/order/get-orders-details"),
    CreateOrderAPI("api/ecom/order/create-order");
    String resource;
    ApiUrls(String resource){
        this.resource = resource;
    }

    public String returnUrl(){
        return resource;
    }

}
