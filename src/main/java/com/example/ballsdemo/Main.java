package com.example.ballsdemo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main extends Application {
    private Pane canvas;
    ArrayList<Ball> balls = new ArrayList<>();
    private double vx1 = 3, vx2 = 0;
    private double vy1 = 2, vy2 = 3;

    @Override
    public void start(final Stage primaryStage) throws FileNotFoundException {

        canvas = new Pane();
        final Scene scene = new Scene(canvas, 800, 600);
        scene.setFill(Color.LIGHTGREEN);

        primaryStage.setTitle("Balls");
        primaryStage.setScene(scene);

        Ball ball1 = new Ball(100, 100, 50, 15);
        Ball ball2 = new Ball(300,100,50,10);
        Ball ball3 = new Ball(300,200,50,10);
        Ball ball4 = new Ball(200,300,50,10);
        balls.add(ball1);
        balls.add(ball2);
//        balls.add(ball3);
//        balls.add(ball4);

        //add to stage
        for (Ball ball : balls) {
            canvas.getChildren().add(ball.display);
        }

        final Timeline loop = new Timeline(new KeyFrame(Duration.millis(10), t -> {

            for (Ball ball : balls) {
                ball.move();
                ball.detectCollision(balls);
                ball.checkCollisionWithBorder(canvas.getBoundsInLocal());
            }
        }));

        primaryStage.show();
        loop.setCycleCount(Timeline.INDEFINITE);
        loop.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}