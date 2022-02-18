package org.orderManagement.dataAccess;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.orderManagement.connection.ConnectionFactory;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for implementing some reflexive methods
 * The idea of this class is to create methods that can be used for all objects
 * @param <T> -placeholder object (Could be a Client/Product/Order)
 */
public class AbstractDAO<T> {
    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * This method generates a Select all query
     * @return The query as a string
     * @throws IllegalArgumentException -did not get a valid table name
     */
    private String createSelectQuery() throws IllegalArgumentException{
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM `");
        sb.append(type.getSimpleName());
        sb.append("`");
        return sb.toString();
    }
    /**
     * This method generates a Query for selecting a specific item in a table (depending on id)
     * @return The query as a string
     * @throws IllegalArgumentException -did not get a valid table name
     */
    private String createQueryFindById(String id) throws IllegalArgumentException{
        StringBuilder sb =new StringBuilder();
        sb.append("Select * from `");
        sb.append(type.getSimpleName());
        sb.append("`");
        sb.append(" WHERE id LIKE ");
        sb.append(id);
        return sb.toString();
    }
    /**
     * This method generates a Delete item from a table (depending in id)
     * @return The query as a string
     */
    private String createDeleteQuery(String field){
        StringBuilder sb =new StringBuilder();
        sb.append("DELETE FROM `");
        sb.append(type.getSimpleName());
        sb.append("` WHERE ");
        sb.append(field);
        sb.append(" =?");
        return sb.toString();
    }

    /**
     * @param t - placeholder class (could be a Client/Order/Product)
     * @return  - String - query for inserting item in a table
     * @throws IllegalAccessException - invoke encountered an error
     */
    private String createInsertQuery(T t) throws IllegalAccessException {
        StringBuilder sb=new StringBuilder();
        sb.append("INSERT INTO `").append(type.getSimpleName()).append("` (");
        Field[] fieldArr = type.getDeclaredFields();

        ArrayList<String> fields = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();
        int count =-1;
        try{
            for(Field field:fieldArr){
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(),type);
                Object f = field.getName();
                Method method = propertyDescriptor.getReadMethod();
                Object v = method.invoke(t);
                if(!field.getName().equals("id")){
                    count++;
                    fields.add(count,f.toString());
                    values.add(count,v.toString());
                }
            }
        } catch (IntrospectionException | InvocationTargetException e) {
            e.printStackTrace();
        }
        int flag=1;
        for(String strig: fields){
            if(flag==0){
                sb.append(", ");
            }
            sb.append(strig);
            flag=0;
        }
        sb.append(") VALUES (");
        flag=1;
        for(String string: values){
            if(flag==0){
                sb.append(", ");
            }
            sb.append("'").append(string).append("'");
            flag=0;
        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * Creates a query for updating a row in the database
     * @param t  placeholder class (could be a Client/Order/Product)
     * @return query as a string
     */
    private String createUpdateQuery(T t) {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE `").append(type.getSimpleName()).append("` SET ");
        Field[] fieldsArr = type.getDeclaredFields();

        ArrayList<String> fields = new ArrayList();
        ArrayList<String> values = new ArrayList();
        int count = -1;
        int flag = 1;
        String id = "";
        try {
            for (Field field : fieldsArr) {
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), type);
                Object f = field.getName();
                Method method = pd.getReadMethod();
                Object v = method.invoke(t);
                if (flag == 1) {
                    id = f.toString() + "='" + v.toString() + "'";
                    flag = 0;
                }
                if (!field.getName().equals("id")) {
                    count++;
                    fields.add(count, f.toString());
                    values.add(count, v.toString());
                }
            }
        } catch (IntrospectionException | InvocationTargetException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        flag = 1;
        for (int i = 0; i <= count; i++) {
            if (flag == 0) {
                sb.append(", ");
            }
            sb.append(fields.get(i)).append("='").append(values.get(i)).append("'");
            flag = 0;
        }
        sb.append(" WHERE ").append(id);
        return sb.toString();
    }

    /**
     * Reflexive method for obtaining the headers of a table in the database
     * The method crates a new Tableview and columns to mimic the database table
     * Calls CreateSelectQuery
     * @return A TableView of objects
     * @throws SQLException - Sql error
     */
    public TableView<T> getTableHeaders() throws SQLException
    {
        Connection con= ConnectionFactory.getConnection();
        String query = createSelectQuery();
        ResultSet resultSet = con.createStatement().executeQuery(query);
        ResultSetMetaData rsmd = resultSet.getMetaData();

        TableView<T> table = new TableView<>();
        table.setEditable(true);
        table.getSelectionModel().setCellSelectionEnabled(true);

        int i=1;
        while(i<=rsmd.getColumnCount()) {
            TableColumn<T,?> column = new TableColumn<>();
            column.setCellValueFactory(new PropertyValueFactory<>(rsmd.getColumnName(i)));
            column.setText(rsmd.getColumnName(i));
            table.getColumns().add(column);
            i++;
        }
        ConnectionFactory.close(resultSet);
        ConnectionFactory.close(con);
        return table;
    }

    /**
     * Reflexive method for obtaining the data in a table in the database
     * Calls createSelectQuery
     * We create a new object for each row in the table
     * @return List of objects
     * @throws IllegalArgumentException - error
     */
    public List<T> getTableData() throws IllegalArgumentException {
        Connection connection = null;
        PreparedStatement  statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            System.out.println(query);
            resultSet = statement.executeQuery();
            return createObjects(resultSet);
        } catch(SQLException e) {
            e.printStackTrace();
        }
        ConnectionFactory.close(resultSet);
        ConnectionFactory.close(statement);
        ConnectionFactory.close(connection);

        return null;
    }

    /**
     * Method for creating objects for the ResultSet
     * @param resultSet -contains the items in the database table that we want to create
     * @return -list of objects
     */
    public List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        try {
            while (resultSet.next()) {
                T instance = type.getDeclaredConstructor().newInstance();
                for (Field field : type.getDeclaredFields()) {
                    Object value = resultSet.getObject(field.getName());
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException | InvocationTargetException | SQLException | IntrospectionException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * A reflexive method for inserting object in the database
     * Calls createInsertQuery
     * @param t object we want to insert
     * @return the row where we inserted or zero
     * @throws IllegalAccessException -error
     */
    public int insert(T t) throws IllegalAccessException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        String query = createInsertQuery(t);
        System.out.println(query);
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if(resultSet.next()){
                System.out.println("Insert operation was successful");
                return resultSet.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        ConnectionFactory.close(resultSet);
        ConnectionFactory.close(statement);
        ConnectionFactory.close(connection);
        return 0;
    }

    /**
     * A reflexive method for deleting item from the database
     * Calls createDeleteQuery
     * @param id - the id(identifier) for the object we want to delete
     */
    public void delete(int id){
        Connection connection =null;
        PreparedStatement preparedStatement=null;
        String query = createDeleteQuery(type.getDeclaredFields()[0].getName());
        System.out.println(query);
        try{
            connection=ConnectionFactory.getConnection();
            preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
        System.out.println("Delete operation was successful");
        ConnectionFactory.close(preparedStatement);
        ConnectionFactory.close(connection);
    }

    /**
     * Method for finding a specific item in the database
     * Calls findByIdQuery
     * @param id - identifier for the object we want to find
     * @return A list of items (for this method the output is a list of 1 item)
     */
    public List<T> findById(String id) {
        Connection connection = null;
        PreparedStatement  statement = null;
        ResultSet resultSet = null;
        String query = createQueryFindById(id);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            System.out.println(query);
            resultSet = statement.executeQuery();
            return createObjects(resultSet);
        } catch(SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Find by id operation was successful");
        ConnectionFactory.close(resultSet);
        ConnectionFactory.close(statement);
        ConnectionFactory.close(connection);

        return null;
    }

    /**a
     * A method for updating a object in the database
     * Calls createUpdateQuery
     * @param t -the object we want to update
     */
    public void update(T t){
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createUpdateQuery(t);
        System.out.println(query);
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
        System.out.println("Update operation was successful");
        ConnectionFactory.close(statement);
        ConnectionFactory.close(connection);
    }

}
