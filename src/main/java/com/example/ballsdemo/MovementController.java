package com.example.ballsdemo;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class MovementController implements Initializable {

    private int movementVariable = 2;
    @FXML
    private Circle ball;
    @FXML
    private AnchorPane scene;

    public MovementController(Circle ball, AnchorPane scene) {
        this.ball = ball;
        this.scene = scene;
    }

    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            if (ball.getBoundsInParent().intersects(scene.getLayoutBounds())) {
                movementVariable *= -1;
            }
            ball.setLayoutX((ball.getLayoutX() + movementVariable));
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timer.start();
    }
}
