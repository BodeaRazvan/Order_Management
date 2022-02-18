module org.orderManagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.sql;
    requires mysql.connector.java;
    requires java.desktop;

    opens org.orderManagement to javafx.fxml;
    exports org.orderManagement;
    exports org.orderManagement.businessLayer;
    exports org.orderManagement.model;
    opens org.orderManagement.businessLayer to javafx.fxml;
}