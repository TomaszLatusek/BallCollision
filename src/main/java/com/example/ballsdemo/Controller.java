package com.example.ballsdemo;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Circle ball;
    @FXML
    private AnchorPane scene;

    AnimationTimer collisionTimer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            checkCollision(ball);
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MovementController movementController = new MovementController(ball, scene);
        collisionTimer.start();
    }

    public void checkCollision(Circle ball) {
        if (ball.getBoundsInParent().intersects(scene.getLayoutBounds())) {
            System.out.println("Collision");
        }
    }
}