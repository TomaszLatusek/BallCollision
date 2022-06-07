package com.example.ballsdemo;

import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Random;

public class Ball {

    public Vector pos, vel;
    public double r;
    public Circle display;
    public Bounds bounds;
    private boolean noLoss;

    public Ball(double x, double y, double r, Pane canvas, boolean noLoss) {
        Random rand = new Random();

        this.r = r;
        pos = new Vector(x, y);
        vel = new Vector(rand.nextDouble(1 - (-1) + (-1)), rand.nextDouble(1 - (-1) + (-1)));

        display = new Circle();
        display.relocate(x, y);
        display.setRadius(r);
        Color color = Color.color(Math.random(), Math.random(), Math.random());
        display.setFill(color);

        this.bounds = canvas.getBoundsInLocal();
        this.noLoss = noLoss;
    }

    public void move() {
        if (!noLoss) {
            vel.y += 0.1;
        }
        pos.add(vel);
        if (pos.x < r) {
            pos.x = r;
            vel.x = -vel.x;
        }
        if (pos.x > bounds.getMaxX() - r) {
            pos.x = bounds.getMaxX() - r;
            vel.x = -vel.x;
        }
        if (pos.y < r) {
            pos.y = r;
            vel.y = -vel.y;
        }
        if (pos.y > bounds.getMaxY() - r) {
            pos.y = bounds.getMaxY() - r;
            vel.y = noLoss ? -vel.y : -vel.y * 0.9;
        }
    }

    public void collide(Ball other) {
        if (other == this) {
            return;
        }
        Vector relative = Vector.subtract(other.pos, pos);
        double dist = relative.getLength() - (r + other.r);
        if (dist < 0) {
            Vector movement = relative.clone();
            movement.setMag(Math.abs(dist / 2));
            pos.subtract(movement);
            other.pos.add(movement);

            Vector thisToOtherNormal = relative.clone().getNormalized();
            double approachSpeed = vel.dot(thisToOtherNormal) + -other.vel.dot(thisToOtherNormal);
            Vector approachVector = thisToOtherNormal.clone();
            approachVector.setMag(noLoss ? approachSpeed : approachSpeed * 0.99);
            vel.subtract(approachVector);
            other.vel.add(approachVector);
        }
    }

    public void render() {
        display.setLayoutX(pos.x);
        display.setLayoutY(pos.y);
    }
}
