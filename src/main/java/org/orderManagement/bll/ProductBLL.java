package org.orderManagement.bll;

import javafx.scene.control.TableView;
import org.orderManagement.dataAccess.ProductDAO;
import org.orderManagement.model.Product;

import java.sql.SQLException;
import java.util.List;

/**
 * A Business Logic Level Class that is used to call the methods from the Abstract Class
 * It makes the tie between our declared objects and the abstract methods in the AbstractDAO Class
 */
public class ProductBLL {
    private final ProductDAO productDAO;

    public ProductBLL(){
        this.productDAO=new ProductDAO();
    }
    public void insertProduct(Product product) throws IllegalAccessException {
        productDAO.insert(product);
    }
    public void deleteProduct(int id){
        productDAO.delete(id);
    }
    public void updateProduct(Product product){
        productDAO.update(product);
    }
    public List<Product> initializeTablesProduct(){
        return productDAO.getTableData();
    }
    public TableView<Product> getProductTableHeaders() throws SQLException {
        return productDAO.getTableHeaders();
    }
    public List<Product> findByIdProduct(String id) {
        return productDAO.findById(id);
    }

}
