package com.example.ballsdemo;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main extends Application {
    private Pane canvas;
    private Object lock;
    ArrayList<Ball> balls = new ArrayList<>();

    @Override
    public void start(final Stage primaryStage) throws FileNotFoundException {

        canvas = new Pane();
        lock = new Object();
        final Scene scene = new Scene(canvas, 800, 600);
        scene.setFill(Color.BLACK);

        primaryStage.setTitle("Balls");
        primaryStage.setScene(scene);

        Ball ball1 = new Ball(0, 0, 60, 20, lock);
        Ball ball2 = new Ball(0, 0, 60, 20, lock);
//        Ball ball3 = new Ball(300, 200, 30, 10, lock);
//        Ball ball4 = new Ball(200, 300, 20, 5, lock);
        balls.add(ball1);
        balls.add(ball2);
//        balls.add(ball3);
//        balls.add(ball4);

        Thread th1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ball1.compute(balls, canvas.getBoundsInParent());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Thread th2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ball2.compute(balls, canvas.getBoundsInLocal());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        AnimationTimer updater = new AnimationTimer() {
            @Override
            public void handle(long l) {
                for (Ball ball : balls) {
                    ball.animate();
                }
            }
        };

        //add to stage
        for (Ball ball : balls) {
            canvas.getChildren().add(ball.display);
        }

        primaryStage.show();
        //updater.start();
        th1.start();
        th2.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}