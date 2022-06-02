package com.example.ballsdemo;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class Ball {

    public double x, y, r, m, vx, vy, tempVx, tempVy, vxf;
    public Circle display;
    Object lock;

    public Ball(double x, double y, double r, double m, Object lock) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.m = m;
        this.lock = lock;

        vx = (int) (3 + Math.random() * 3);
        vy = (int) (3 + Math.random() * 3);

        display = new Circle();
        display.setCenterX(x);
        display.setCenterY(y);
        display.setRadius(r);

        display.setFill(Color.color(Math.random(), Math.random(), Math.random()));
    }

    public void move() {
        x += vx;
        y += vy;
    }

    public void oneDim(Ball otherBall) {
        tempVx = vx;
        vx = ((m - otherBall.m) * vx + 2 * otherBall.m * otherBall.vx) / (m + otherBall.m);
        otherBall.vx = ((otherBall.m - m) * otherBall.vx + 2 * m * tempVx) / (m + otherBall.m);
    }

    public void detectCollision(ArrayList<Ball> balls) {
        for (Ball otherBall : balls) {
            if (otherBall != this) {
                if (collisionDetected(otherBall)) {
                    handleCollision(otherBall);
//                    oneDim(otherBall);
                }
            }
        }
    }

    private boolean collisionDetected(Ball otherBall) {
        // return getBounds().intersects(otherBall.getBounds());
        return (Math.sqrt(Math.pow(Math.abs(x - otherBall.x), 2) + Math.pow(Math.abs(y - otherBall.y), 2)) <= (r + otherBall.r));
    }

    public void checkCollisionWithBorder(Bounds border) {
        if (x + r >= border.getMaxX() || x - r <= border.getMinX()) {
            vx *= -1;
        }
        if (y + r >= border.getMaxY() || y - r <= border.getMinY()) {
            vy *= -1;
        }
    }

    public void handleCollision(Ball otherBall) {

        // synchronized (lock) {
        double angle = Math.atan2(Math.abs(getX() - otherBall.getX()), Math.abs(getY() - otherBall.getY()));

        tempVx = Math.cos(angle) * vx + Math.sin(angle) * vy;
        tempVy = -Math.sin(angle) * vx + Math.cos(angle) * vy;
        otherBall.tempVx = Math.cos(angle) * otherBall.vx + Math.sin(angle) * otherBall.vy;
        otherBall.tempVy = -Math.sin(angle) * otherBall.vx + Math.cos(angle) * otherBall.vy;

        vxf = ((m - otherBall.m) * tempVx + 2 * otherBall.m * otherBall.tempVx) / (m + otherBall.m);
        otherBall.vxf = ((otherBall.m - m) * otherBall.tempVx + 2 * m * tempVx) / (m + otherBall.m);

        vx = (Math.cos(angle) * vxf - Math.sin(angle) * tempVy);
        vy = (Math.sin(angle) * vxf + Math.cos(angle) * tempVy);

        otherBall.vx = (Math.cos(angle) * otherBall.vxf - Math.sin(angle) * otherBall.tempVy);
        otherBall.vy = (Math.sin(angle) * otherBall.vxf + Math.cos(angle) * otherBall.tempVy);
        //}
    }

    public synchronized Bounds getBounds() {
        return display.getBoundsInParent();
    }

    public void compute(ArrayList<Ball> balls, Bounds scene) throws InterruptedException {
        while (true) {
            move();
            detectCollision(balls);
            checkCollisionWithBorder(scene);
            animate();
            Thread.sleep(10);
        }
    }

    public void animate() {
        display.setLayoutX(x);
        display.setLayoutY(y);
    }

    public synchronized double getX() {
        return x;
    }

    public synchronized double getY() {
        return y;
    }

}
