package me.getline.motionanalysis;

import java.util.ArrayList;

/**
 * Created by Lqs on 15/11/23.
 */
public class MotionCalculator {
    private ArrayList<MotionPoint> pointList = new ArrayList<>();
    private double maxX;
    private double minX;
    private double maxY;
    private double minY;
    private double maxSize;
    private double minSize;
    private double sumX;
    private double sumY;

    public MotionCalculator() {
        maxX = -1.0;
        minX = 10000;
        maxY = -1.0;
        minY = 10000;
        maxSize = -1.0;
        minSize = 10000;
        sumX = 0;
        sumY = 0;
    }

    public double getMeanX() {
        return sumX / pointList.size();
    }

    public double getMeanY() {
        return sumY / pointList.size();
    }

    public double getNormalX(double x) {
        return (x - minX) / (maxX - minX);
    }

    public double getNormalY(double y) {
        return (y - minY) / (maxY - minY);
    }

    public double getNormalSize(double size) {
        return (size - minSize) / (maxSize - minSize);
    }

    public double getXSecondaryMoment() {
        double normalMeanX = getNormalX(getMeanX());
        for (MotionPoint point : pointList) {
        }
        return 0.0;
    }

    public void addPoint(MotionPoint point) {
        maxX = Math.max(maxX, point.getX());
        minX = Math.min(minX, point.getX());
        maxY = Math.max(maxY, point.getY());
        minY = Math.min(minY, point.getY());
        maxSize = Math.max(maxSize, point.getSize());
        minSize = Math.min(minSize, point.getSize());
        sumX += point.getX();
        sumY += point.getY();
        pointList.add(point);
    }
}
