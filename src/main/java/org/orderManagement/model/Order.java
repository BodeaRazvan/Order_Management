package org.orderManagement.model;

/**
 * A class that has the same fields as the Order from the Database
 */
public class Order {
    private int id;
    private int idclient;
    private int idproduct;
    private int quantityordered;

    public Order() {

    }
    public Order (int idclient,int idproduct,int quantityordered){
        this.idclient=idclient;
        this.idproduct=idproduct;
        this.quantityordered=quantityordered;
    }

    public Order (int idorder,int idclient,int idproduct,int quantityordered){
        this.id =idorder;
        this.idclient=idclient;
        this.idproduct=idproduct;
        this.quantityordered=quantityordered;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdclient() {
        return idclient;
    }

    public void setIdclient(int idclient) {
        this.idclient = idclient;
    }

    public int getIdproduct() {
        return idproduct;
    }

    public void setIdproduct(int idproduct) {
        this.idproduct = idproduct;
    }

    public int getQuantityordered() {
        return quantityordered;
    }

    public void setQuantityordered(int quantityordered) {
        this.quantityordered = quantityordered;
    }
}
