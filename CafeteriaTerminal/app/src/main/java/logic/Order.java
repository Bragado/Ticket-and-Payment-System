package logic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Order implements Serializable{


    //product - number of items ordered
    private int id;
    private HashMap<Product, Integer> products;
    private double price;
    private String userId;
    private String signedUserId;
    private boolean orderServed;
    private Voucher voucher1;
    private Voucher voucher2;

    public Order(int id){
        this.id = id;
        products = new HashMap<>();
        setOrderServed(false);
        voucher1 = null;
        voucher2 = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isOrderServed() {
        return orderServed;
    }

    public HashMap<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(HashMap<Product, Integer> products) {
        this.products = products;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean getOrderServed() {
        return orderServed;
    }

    public void setOrderServed(boolean orderServed) {
        this.orderServed = orderServed;
    }

    public Voucher getVoucher1() {
        return voucher1;
    }

    public void setVoucher1(Voucher voucher1) {
        this.voucher1 = voucher1;
    }

    public Voucher getVoucher2() {
        return voucher2;
    }

    public void setVoucher2(Voucher voucher2) {
        this.voucher2 = voucher2;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSignedUserId() {
        return signedUserId;
    }

    public void setSignedUserId(String signedUserId) {
        this.signedUserId = signedUserId;
    }

    public void addProduct(Product product) {
        product.setSales(product.getSales() + 1);
        if (products.containsKey(product)) {
            products.put(product, products.get(product) + 1);
        } else {
            products.put(product, 1);
        }

        price += product.getPrice();
    }

    public OrderType getType(){
        String productName = "";
        for (HashMap.Entry<Product, Integer> pair : products.entrySet()) {
            if (productName.equals("")) {
                productName = pair.getKey().getName();
                continue;
            }

            if (!productName.equals(pair.getKey().getName())){
                return OrderType.MIXED;
            }
        }

        switch (productName){
            case "Coffee":
                return OrderType.COFFEE;
            case "Soda":
                return OrderType.SODA;
            case "Sandwich":
                return OrderType.SANDWICH;
            case "Popcorn":
                return OrderType.POPCORN;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        String res = "";

        for (HashMap.Entry<Product, Integer> pair : products.entrySet()) {
            if (!res.equals(""))
                res += ", ";

            res += pair.getValue() + " " + pair.getKey().getName();
        }

        /*res += " / ";
        if (orderServed)
            res += "Served";
        else
            res += "NOT Served";
        */

        return res;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null)
            return false;
        if (other == this)
            return true;
        if (!(other instanceof Order))
            return false;

        Order otherOrder = (Order) other;

        return otherOrder.getId() == this.getId();
    }

    public JSONObject toJson(){
        try {
            JSONObject jo = new JSONObject();
            jo.put("userID", userId);
            jo.put("signedUserID", signedUserId);
            JSONArray products = new JSONArray();
            for (Map.Entry<Product, Integer> entry: this.products.entrySet()) {
                JSONObject newProduct = new JSONObject();
                newProduct.put("type", entry.getKey().getId());
                newProduct.put("quantity", entry.getValue());
                products.put(newProduct);
            }
            JSONArray vouchers = new JSONArray();
            if (voucher1 != null)
                vouchers.put(voucher1.getId());
            if (voucher2 != null)
                vouchers.put(voucher2.getId());
            jo.put("products", products);
            jo.put("vouchers", vouchers);
            return jo;

        } catch (JSONException e){
            e.printStackTrace();
        }

        return null;
    }

    public void checkVouchers(ArrayList<String> vouchers){
        for (String voucher:vouchers) {
            switch (voucher){
                case "Free Coffe":
                    voucher1.checkVoucherStatus(Voucher.FREE_COFFEE);
                    voucher2.checkVoucherStatus(Voucher.FREE_COFFEE);
                    break;
                case "Free Popcorn":
                    voucher1.checkVoucherStatus(Voucher.FREE_POPCORN);
                    voucher2.checkVoucherStatus(Voucher.FREE_POPCORN);
                    break;
            }
        }
    }

    public double getVoucher1Discount(){
        switch (voucher1.getType()){
            case Voucher.FREE_COFFEE:
                return Cafeteria.getInstance().getProducts().get(Product.COFFEE).getPrice();
            case Voucher.FREE_POPCORN:
                return Cafeteria.getInstance().getProducts().get(Product.POPCORN).getPrice();
            case Voucher.DISCOUNT:
                DecimalFormat df = new DecimalFormat("#.##");
                Double discount = Double.valueOf(df.format(price*0.05));
                return discount;
        }

        return 0;
    }

    public double getVoucher2Discount(){
        switch (voucher2.getType()){
            case Voucher.FREE_COFFEE:
                return Cafeteria.getInstance().getProducts().get(Product.COFFEE).getPrice();
            case Voucher.FREE_POPCORN:
                return Cafeteria.getInstance().getProducts().get(Product.POPCORN).getPrice();
            case Voucher.DISCOUNT:
                DecimalFormat df = new DecimalFormat("#.##");
                Double discount = Double.valueOf(df.format(price*0.05));
                return discount;
        }

        return 0;
    }
}
