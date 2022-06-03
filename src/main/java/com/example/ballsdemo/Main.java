package com.example.ballsdemo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

public class Main extends Application {
    final int NUMBER_OF_BALLS = 20;

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
        primaryStage.setResizable(false);

        generateBalls();

//        Thread th1 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    ball1.compute(balls, canvas.getBoundsInParent());
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
//        Thread th2 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    ball2.compute(balls, canvas.getBoundsInParent());
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
//

        for (Ball ball : balls) {
            canvas.getChildren().add(ball.display);
        }

        final Timeline loop = new Timeline(new KeyFrame(Duration.millis(10), t -> {
            draw();
        }));

        primaryStage.show();
        loop.setCycleCount(Timeline.INDEFINITE);
        loop.play();
        draw();
//        th1.start();
//        th2.start();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void draw() {
        for (int i = 0; i < balls.size(); i++) {
            for (int j = 0; j < i; j++) {
                balls.get(i).collide(balls.get(j));
            }
        }
        for (Ball ball : balls) {
            ball.move();
            ball.render();
        }
    }

    public void generateBalls() {
        for (int i = 0; i < NUMBER_OF_BALLS; i++) {
            Random rand = new Random();
            balls.add(new Ball(
                    rand.nextFloat(700 - 100) + 100,
                    rand.nextFloat(500 - 100) + 100,
                    rand.nextFloat(50 - 10) + 10,
                    canvas));
        }
    }
}