module org.example.tictactoefxgl {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.almasb.fxgl.all;

    opens org.example.com to javafx.fxml;
    exports org.example.com;
}
