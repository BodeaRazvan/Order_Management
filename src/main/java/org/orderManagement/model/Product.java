package org.orderManagement.model;

/**
 * A class that has the same fields as the Product from the Database
 */
public class Product {
    private int id;
    private String productname;
    private int quantity;
    private int price;

    public Product(){

    }

    public Product( String productname, int quantity, int price){
        this.productname=productname;
        this.quantity=quantity;
        this.price=price;
    }

    public Product(int idproduct, String productname, int quantity, int price){
        this.id =idproduct;
        this.productname=productname;
        this.quantity=quantity;
        this.price=price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Method to generate a string representation of a product
     * @return a string representation of the Product
     */
    public String getString(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.id);
        sb.append(", ");
        sb.append(this.productname);
        sb.append(", quantity:");
        sb.append(this.quantity);
        sb.append(", price:");
        sb.append(this.price);
        return sb.toString();
    }
}
