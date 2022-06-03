package com.example.ballsdemo;

public class Vector {

    public double x;
    public double y;

    public Vector() { }

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getLength() {
        return Math.sqrt(x * x + y * y);
    }

    public void normalize() {
        double magnitude = getLength();
        x /= magnitude;
        y /= magnitude;
    }

    public Vector getNormalized() {
        double magnitude = getLength();
        return new Vector(x / magnitude, y / magnitude);
    }

    public void add(Vector v) {
        this.x += v.x;
        this.y += v.y;
    }

    public void subtract(Vector v) {
        this.x -= v.x;
        this.y -= v.y;
    }

    public static Vector subtract(Vector v1, Vector v2) {
        return new Vector(v1.x - v2.x, v1.y - v2.y);
    }

    public void multiply(double scalar) {
        x *= scalar;
        y *= scalar;
    }

    public double dot(Vector v) {
        return (this.x * v.x + this.y * v.y);
    }

    public void setMag(double len) {
        this.normalize();
        this.multiply(len);
    }

    @Override
    public Vector clone() {
        return new Vector(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Vector) {
            Vector v = (Vector) obj;
            return (x == v.x) && (y == v.y);
        }
        return false;
    }

    @Override
    public String toString() {
        return "Vector[" + x + ", " + y + "]";
    }
}
