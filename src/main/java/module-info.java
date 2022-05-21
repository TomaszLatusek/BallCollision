module com.example.ballsdemo {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens com.example.ballsdemo to javafx.fxml;
    exports com.example.ballsdemo;
}