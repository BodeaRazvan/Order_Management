package org.orderManagement.bll;
import javafx.scene.control.TableView;
import org.orderManagement.dataAccess.OrderDAO;
import org.orderManagement.model.Order;

import java.sql.SQLException;
import java.util.List;

/**
 * A Business Logic Level Class that is used to call the methods from the Abstract Class
 * It makes the tie between our declared objects and the abstract methods in the AbstractDAO Class
 */
public class OrderBLL {
    private final OrderDAO orderDAO;

    public OrderBLL() {
        this.orderDAO = new OrderDAO();
    }
    public void insertOrder(Order order) throws IllegalAccessException {
        orderDAO.insert(order);
    }
    public void deleteOrder(int id){
        orderDAO.delete(id);
    }
    public List<Order> initializeTablesOrder(){
        return orderDAO.getTableData();
    }
    public TableView<Order> getOrderTableHeaders() throws SQLException {
        return orderDAO.getTableHeaders();
    }
    public List<Order> findByIdOrder(String id) {
        return orderDAO.findById(id);
    }

}
