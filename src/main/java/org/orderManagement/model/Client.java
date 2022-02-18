package org.orderManagement.model;

/**
 * A class that has the same fields as the Client from the Database
 */
public class Client {
    private int id;
    private String name;
    private String address;
    private String email;

    public Client(){

    }

    public Client(String name, String address, String email){
        this.name=name;
        this.address=address;
        this.email=email;
    }

    public Client (int idclient, String name, String address, String email){
        this.id =idclient;
        this.name=name;
        this.address=address;
        this.email=email;
    }

    public Client(String toString) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * Method to generate a string representation of a client
     * @return a string representation of the Client
     */
    public String getString(){
        StringBuilder sb =new StringBuilder();
        sb.append(this.id);
        sb.append(", ");
        sb.append(this.name);
        sb.append(", ");
        sb.append(this.address);
        sb.append(", ");
        sb.append(this.email);
        return sb.toString();
    }

}
