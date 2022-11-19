module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;



    opens com.example.demo1 to javafx.fxml;
    exports com.example.demo1;
}