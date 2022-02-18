package org.orderManagement.bll;

import javafx.scene.control.TableView;
import org.orderManagement.dataAccess.ClientDAO;
import org.orderManagement.model.Client;

import java.sql.SQLException;
import java.util.List;

/**
 * A Business Logic Level Class that is used to call the methods from the Abstract Class
 * It makes the tie between our declared objects and the abstract methods in the AbstractDAO Class
 */
public class ClientBLL {
    private final ClientDAO clientDAO;

    public ClientBLL(){
        clientDAO = new ClientDAO();
    }
    public void insertClient(Client client) throws IllegalAccessException {
        clientDAO.insert(client);
    }
    public void deleteClient(int id){
        clientDAO.delete(id);
    }
    public void updateClient(Client client){
        clientDAO.update(client);
    }
    public List<Client> initializeTablesClient(){
        return clientDAO.getTableData();
    }
    public TableView<Client> getClientTableHeaders() throws SQLException {
        return clientDAO.getTableHeaders();
    }
    public List<Client> findByIdClient(String id) {
        return clientDAO.findById(id);
    }

}
