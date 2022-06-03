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
    final int NUMBER_OF_BALLS = 90;
    final int NUMBER_OF_THREADS = 3;
    final boolean NO_LOSS = false;

    private Pane canvas;
    private Object lock;
    ArrayList<Ball> balls = new ArrayList<>();
    ArrayList<Thread> threads = new ArrayList<>();

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

        for (Ball ball : balls) {
            canvas.getChildren().add(ball.display);
        }

        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            int finalI = i;
            threads.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        draw(finalI);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }));
        }


        final Timeline loop = new Timeline(new KeyFrame(Duration.millis(10), t -> {
            for (Ball ball : balls) {
                ball.move();
                try {
                    ball.render();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }));

        primaryStage.show();

        for (Thread th : threads) {
            th.start();
        }

        loop.setCycleCount(Timeline.INDEFINITE);
        loop.play();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void draw(int thread) throws InterruptedException {
        int threshold = NUMBER_OF_BALLS / NUMBER_OF_THREADS;
        while (true) {
            if (thread == NUMBER_OF_THREADS - 1) {
                for (int i = thread * threshold; i < NUMBER_OF_BALLS; i++) {
                    for (int j = 0; j < i; j++) {
                        balls.get(i).collide(balls.get(j));
                    }
                }
            } else {
                for (int i = thread * threshold; i < (thread + 1) * threshold; i++) {
                    for (int j = 0; j < i; j++) {
                        balls.get(i).collide(balls.get(j));
                    }
                }
            }
        }
    }

    public void generateBalls() {
        for (int i = 0; i < NUMBER_OF_BALLS; i++) {
            Random rand = new Random();
            balls.add(new Ball(
                    rand.nextFloat(700 - 100) + 100,
                    rand.nextFloat(500 - 100) + 100,
                    rand.nextFloat(20 - 5) + 5,
                    canvas,
                    NO_LOSS));
        }
    }
}