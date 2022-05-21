package com.example.ballsdemo;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class Ball {

    public double x, y, r, m, vx, vy, tempVx, vxf;
    public Circle display;

    public Ball(double x, double y, double r, double m) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.m = m;

        vx = (int) (-5 + Math.random() * 10);
        vy = (int) (-5 + Math.random() * 10);

        display = new Circle();
        display.setCenterX(x);
        display.setCenterY(y);
        display.setRadius(r);
    }

    public void move() {
        x += vx;
        y += vy;
        display.setLayoutX(display.getLayoutX() + vx);
        display.setLayoutY(display.getLayoutY() + vy);
    }

    public void detectCollision(ArrayList<Ball> balls) {
        for (Ball otherBall : balls) {
            if (otherBall != this) {
                if (collisionDetected(otherBall)) {
                    handleCollision(otherBall, false);
                }
            }
        }
    }

    private boolean collisionDetected(Ball otherBall) {
        return display.getBoundsInParent().intersects(otherBall.getBounds());
       // return Math.sqrt(Math.pow(Math.abs(x - otherBall.x), 2) + Math.pow(Math.abs(y - otherBall.y), 2)) <= r + otherBall.r;
    }

    public void checkCollisionWithBorder(Bounds border) {
        if (getBounds().getMaxX() >= border.getMaxX() || getBounds().getMinX() <= border.getMinX()) {
            vx *= -1;
        }
        if (getBounds().getMaxY() >= border.getMaxY() || getBounds().getMinY() <= border.getMinY()) {
            vy *= -1;
        }
    }

    public void handleCollision(Ball otherBall, boolean secondCall) {
        if (!secondCall) {
            double angle = Math.atan2(Math.abs(getX() - otherBall.getX()), Math.abs(getY() - otherBall.getY()));
            tempVx = Math.cos(angle) * vx + Math.sin(angle) * vy;
            vxf = ((m - otherBall.m) * tempVx + 2 * otherBall.m * otherBall.tempVx) / (m + otherBall.m);

            vx = (Math.cos(angle) * vxf - Math.sin(angle) * vy);
            vy = (Math.sin(angle) * vx + Math.cos(angle) * vy);

            otherBall.handleCollision(this, true);
        }

    }

    public Bounds getBounds() {
        return display.getBoundsInParent();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
