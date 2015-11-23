package me.getline.motionanalysis;

import android.view.MotionEvent;

/**
 * Created by Lqs on 15/11/23.
 */
public class MotionPoint {
    private double x;
    private double y;
    private double size;

    public MotionPoint(MotionEvent event) {
        setX(event.getX());
        setY(event.getY());
        setSize(event.getSize());
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

}
