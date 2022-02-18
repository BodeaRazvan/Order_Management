package org.orderManagement.businessLayer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.orderManagement.App;
import org.orderManagement.Validator.Validator;
import org.orderManagement.bll.ClientBLL;
import org.orderManagement.bll.OrderBLL;
import org.orderManagement.bll.ProductBLL;
import org.orderManagement.model.Client;
import org.orderManagement.model.Order;
import org.orderManagement.model.Product;

/**
 * A class that handles the user interaction with the user interface
 * The class handles actions that should occur when a specific button is pressed
 */
public class Controller {
    //Client Table data
    @FXML private TableView<Client> client_table;
    @FXML private TextField client_id;
    @FXML private TextField client_name;
    @FXML private TextField client_address;
    @FXML private TextField client_email;
    @FXML private TextField errLogClient;
    //Product Table data
    @FXML private TableView<Product> product_table;
    @FXML private TextField prod_id;
    @FXML private TextField prod_name;
    @FXML private TextField prod_quantity;
    @FXML private TextField prod_price;
    @FXML private TextField errLogProduct;
    //Order Table data
    @FXML private TableView<Order> order_table;
    @FXML private TextField order_id;
    @FXML private TextField errLogOrder;
    @FXML private ChoiceBox<Object> choiceBarClient;
    @FXML private ChoiceBox<Object> choiceBarProduct;
    @FXML private TextField quantityOrdered;

    private final Validator validator=new Validator();

    public Controller() {
    }

    @FXML
    private void switchToOrder() throws IOException{
        App.setRoot("Order");
    }
    @FXML
    private void switchToClient() throws IOException {
        App.setRoot("Client");
    }
    @FXML
    private void switchToProduct() throws IOException {
        App.setRoot("Product");
    }

    /**
     * Create clients according tot he ones in the database and display them in the TableView
     * @throws SQLException - error
     */
    @FXML
    private void refreshClient() throws SQLException {
        errLogClient.clear();
        System.out.println("Refreshing");
        ClientBLL clientBll = new ClientBLL();
        ObservableList<Client> oblist = FXCollections.observableArrayList();
        oblist.addAll(clientBll.initializeTablesClient());
        client_table.getColumns().setAll(clientBll.getClientTableHeaders().getColumns());
        client_table.getItems().setAll(oblist);
        client_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
    /**
     * Create products according tot he ones in the database and display them in the TableView
     * @throws SQLException - error
     */
    @FXML
    private void refreshProduct() throws SQLException{
        errLogProduct.clear();
        System.out.println("Refreshing");
        ProductBLL productBLL = new ProductBLL();
        ObservableList<Product> oblist = FXCollections.observableArrayList();
        oblist.addAll(productBLL.initializeTablesProduct());
        product_table.getColumns().setAll(productBLL.getProductTableHeaders().getColumns());
        product_table.getItems().setAll(oblist);
        product_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
    /**
     * Create Orders according tot he ones in the database and display them in the TableView
     * @throws SQLException - error
     */
    @FXML
    private void refreshOrder() throws SQLException{
        errLogOrder.clear();
        System.out.println("Refreshing");
        OrderBLL orderBLL = new OrderBLL();
        ObservableList<Order> oblist = FXCollections.observableArrayList();
        oblist.addAll(orderBLL.initializeTablesOrder());
        order_table.getColumns().setAll(orderBLL.getOrderTableHeaders().getColumns());
        order_table.getItems().setAll(oblist);
        order_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        choiceBarClient.getSelectionModel().clearSelection();
        choiceBarClient.getItems().clear();
        ClientBLL clientBll = new ClientBLL();
        ObservableList<Client> oblistClient = FXCollections.observableArrayList();
        oblistClient.addAll(clientBll.initializeTablesClient());
        for(Client client:oblistClient){
            choiceBarClient.getItems().add(client.getString());
        }

        choiceBarProduct.getSelectionModel().clearSelection();
        choiceBarProduct.getItems().clear();
        ProductBLL productBLL = new ProductBLL();
        ObservableList<Product> oblistProduct = FXCollections.observableArrayList();
        oblistProduct.addAll(productBLL.initializeTablesProduct());
        for(Product product:oblistProduct){
            choiceBarProduct.getItems().add(product.getString());
        }
    }
    @FXML
    private void addClientSelected() throws IllegalAccessException {
        errLogClient.clear();
        if(validator.isValidClient(client_name,client_address,client_email)==1){
        ClientBLL clientBll = new ClientBLL();
        Client client = new Client(client_name.getText(),client_address.getText(),client_email.getText());
        clientBll.insertClient(client);
        client_id.clear();client_name.clear();client_address.clear();client_email.clear();
        }else {
            errLogClient.setText("Name, Address, Email must be completed");
        }
    }
    @FXML
    private void addProductSelected() throws IllegalAccessException {
        errLogProduct.clear();
        if(validator.isValidProduct(prod_name,prod_quantity,prod_price)==1){
        ProductBLL productBLL = new ProductBLL();
        Product product = new Product(prod_name.getText(),Integer.parseInt(prod_quantity.getText()),
                                      Integer.parseInt(prod_price.getText()));
        productBLL.insertProduct(product);
        prod_id.clear();prod_name.clear();prod_quantity.clear();prod_price.clear();
        }else {
            errLogProduct.setText("Quantity and Price must be numbers >0. Name must be completed");
        }
    }
    @FXML
    private void deleteClientSelected(){
        errLogClient.clear();
        if (validator.isValidId(client_id)==1){
        ClientBLL clientBll = new ClientBLL();
        clientBll.deleteClient(Integer.parseInt(client_id.getText()));
        client_id.clear();client_name.clear();client_address.clear();client_email.clear();
        }else {
            errLogClient.setText("Id must be a number >0");
        }
    }
    @FXML
    private void deleteProductSelected(){
        errLogProduct.clear();
        if(validator.isValidId(prod_id)==1){
        ProductBLL productBLL = new ProductBLL();
        productBLL.deleteProduct(Integer.parseInt(prod_id.getText()));
        prod_id.clear();prod_name.clear();prod_quantity.clear();prod_price.clear();
        }else {
            errLogProduct.setText("Id must be a number >0");
        }
    }
    @FXML
    private void deleteOrderSelected(){
        errLogOrder.clear();
        if(validator.isValidId(order_id)==1){
        OrderBLL orderBLL = new OrderBLL();
        orderBLL.deleteOrder(Integer.parseInt(order_id.getText()));
        order_id.clear();
        }else {
            errLogOrder.setText("Id must be a number >0");
        }
    }
    @FXML
    private void updateClientSelected(){
        errLogClient.clear();
        if(validator.isValidId(client_id)==1 && validator.isValidClient(client_name,client_address,client_email)==1){
        ClientBLL clientBll = new ClientBLL();
        Client client = new Client(Integer.parseInt(client_id.getText()),client_name.getText(),
                                   client_address.getText(),client_email.getText());
        clientBll.updateClient(client);
        client_id.clear();client_name.clear();client_address.clear();client_email.clear();
        }else {
            errLogClient.setText("Id must be a number >0. All fields must be completed");
        }
    }
    @FXML
    private void updateProductSelected(){
        errLogProduct.clear();
        if(validator.isValidId(prod_id)==1 && validator.isValidProduct(prod_name,prod_quantity,prod_price)==1){
        ProductBLL productBLL = new ProductBLL();
        Product product = new Product(Integer.parseInt(prod_id.getText()),prod_name.getText(),
                                      Integer.parseInt(prod_quantity.getText()),Integer.parseInt(prod_price.getText()));
        productBLL.updateProduct(product);
        prod_id.clear();prod_name.clear();prod_quantity.clear();prod_price.clear();
        }else {
           errLogProduct.setText("Id,Quantity,Price must be numbers >0. All fields must be completed");
        }
    }
    @FXML
    private void findClientSelected() throws SQLException {
        errLogClient.clear();
        if(validator.isValidId(client_id)==1){
        ClientBLL clientBLL = new ClientBLL();
        List<Client> client = clientBLL.findByIdClient(client_id.getText());
        client_table.getItems().clear();
        client_table.getItems().setAll(client);
        client_id.clear();client_name.clear();client_address.clear();client_email.clear();
        }else {
            errLogClient.setText("Id must be a number >0");
        }
    }
    @FXML
    private void findOrderSelected() throws SQLException {
        errLogOrder.clear();
        if(validator.isValidId(order_id)==1){
        OrderBLL orderBLL = new OrderBLL();
        List<Order> orders = orderBLL.findByIdOrder(order_id.getText());
        order_table.getItems().clear();
        order_table.getItems().setAll(orders);
        order_id.clear();
        }else {
            errLogOrder.setText("Id must be a number >0");
        }
    }
    @FXML
    private void findProductSelected() throws SQLException {
        errLogProduct.clear();
        if(validator.isValidId(prod_id)==1){
        ProductBLL productBLL = new ProductBLL();
        List<Product> products = productBLL.findByIdProduct(prod_id.getText());
        product_table.getItems().clear();
        product_table.getItems().setAll(products);
        prod_id.clear();prod_name.clear();prod_quantity.clear();prod_price.clear();
        }else {
            errLogProduct.setText("Id must be a number >0");
        }
    }

    /**
     * Method that takes the options selected in the choiceBox and creates a order based on their information
     * We have to check if the ordered quantity is a valid number/ is in stock
     * We have to update the quantity left in stock after we make an order
     * @throws IllegalAccessException - error
     * @throws SQLException - error
     */
    @FXML
    private void addOrderSelected() throws IllegalAccessException, SQLException {
        errLogOrder.clear();
        try{
        if(choiceBarClient.getValue()!=null && choiceBarProduct.getValue()!=null && Integer.parseInt(quantityOrdered.getText())>0) {
            String[] partsClient = choiceBarClient.getValue().toString().split(", ");
            String[] partsProduct = choiceBarProduct.getValue().toString().split(", ");

            partsProduct[2] = partsProduct[2].replace("quantity:", "");
            partsProduct[3] = partsProduct[3].replace("price:", "");

            ClientBLL clientBLL = new ClientBLL();
            List<Client> client = clientBLL.findByIdClient(partsClient[0]);
            ProductBLL productBLL = new ProductBLL();
            List<Product> product = productBLL.findByIdProduct(partsProduct[0]);

            if (product.get(0).getQuantity() < Integer.parseInt(quantityOrdered.getText())) {
                errLogOrder.setText("Not enough items in stock");
            }
            if (product.get(0).getQuantity() >= Integer.parseInt(quantityOrdered.getText())) {
                int newQuantity = product.get(0).getQuantity() - Integer.parseInt(quantityOrdered.getText());
                OrderBLL orderBLL = new OrderBLL();
                Order newOrder = new Order(client.get(0).getId(), product.get(0).getId(), Integer.parseInt(quantityOrdered.getText()));
                orderBLL.insertOrder(newOrder);

                Product updatedProduct = new Product(product.get(0).getId(), product.get(0).getProductname(),
                        newQuantity, product.get(0).getPrice());
                productBLL.updateProduct(updatedProduct);
                quantityOrdered.clear();
            }
        }else{
            errLogOrder.setText("Both Client and Product must be selected. Quantity ordered must be >0");
        }
        }catch (NumberFormatException exception){
            errLogOrder.setText("Quantity ordered must be a number >0");
        }
    }

    /**
     * A method that calculates all the bills/ or a specific bill if an id is specified
     * @throws SQLException -error
     * @throws IOException -error
     */
    @FXML
    private void calculateBills() throws SQLException, IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("Bills.txt"));
        errLogOrder.clear();
        OrderBLL orderBLL = new OrderBLL();
        ClientBLL clientBLL = new ClientBLL();
        ProductBLL productBLL = new ProductBLL();

        if(order_id.getText().equals("")){
            List<Order> orders = orderBLL.initializeTablesOrder();
            int i=1;
            for(Order order: orders){
                writer.write("Bill " +i + " Information:\n");
                Client client = clientBLL.findByIdClient(String.valueOf(order.getIdclient())).get(0);
                Product product = productBLL.findByIdProduct(String.valueOf(order.getIdproduct())).get(0);
                displayBillInformation(writer,order, client, product);
                i++;
            }
        }

        if(!order_id.getText().equals("")){
            if(validator.isValidId(order_id)==1){
                try {
                    Order order = orderBLL.findByIdOrder(order_id.getText()).get(0);
                    Client client = clientBLL.findByIdClient(String.valueOf(order.getIdclient())).get(0);
                    Product product = productBLL.findByIdProduct(String.valueOf(order.getIdproduct())).get(0);
                    writer.write("Bill Information: \n");
                    displayBillInformation(writer,order, client, product);
                }catch (IndexOutOfBoundsException | IOException exception){
                    errLogOrder.setText("Please enter a valid order Id");
                }
            }else {
                errLogOrder.setText("Id must be a number >0");
            }
        }
        writer.close();
    }

    /**
     * Write to the Bill file a string representation of the needed information for a bill
     * @param writer - FileWriter for our file
     * @param order - Order object
     * @param client - Client object
     * @param product -Product object
     * @throws IOException -error
     */
    private void displayBillInformation(BufferedWriter writer,Order order, Client client, Product product) throws IOException {
        writer.write("Name: " + client.getName() + "\n");
        writer.write("Address: " + client.getAddress()+ "\n");
        writer.write("Email: " + client.getEmail() + "\n");
        writer.write("Ordered Product: " + product.getProductname()+ "\n");
        writer.write("Ordered Quantity: " + order.getQuantityordered()+ "\n");
        writer.write("Product price: " + product.getPrice() + "\n");
        writer.write("Total Price: productPrice * orderedQuantity = " + order.getQuantityordered() * product.getPrice()+ "\n");
        writer.write("\n\n");
    }

}
