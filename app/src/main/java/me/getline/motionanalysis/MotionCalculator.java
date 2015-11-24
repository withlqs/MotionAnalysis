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
    private double sumSize;

    public MotionCalculator() {
        init();
    }

    public void init() {
        maxX = -1.0;
        minX = 10000;
        maxY = -1.0;
        minY = 10000;
        maxSize = -1.0;
        minSize = 10000;
        sumX = 0;
        sumY = 0;
        sumSize = 0;
        pointList.clear();
    }

    public double getMeanX() {
        return sumX / pointList.size();
    }

    public double getMeanY() {
        return sumY / pointList.size();
    }

    public double getMeanSize() {
        return sumSize / pointList.size();
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
        double sum = 0.0;
        for (MotionPoint point : pointList) {
            sum += (getNormalX(point.getX()) - normalMeanX) * (getNormalX(point.getX()) - normalMeanX);
        }
        return sum;
    }

    public double getYSecondaryMoment() {
        double normalMeanY = getNormalY(getMeanY());
        double sum = 0.0;
        for (MotionPoint point : pointList) {
            sum += (getNormalY(point.getY()) - normalMeanY) * (getNormalY(point.getY()) - normalMeanY);
        }
        return sum;
    }

    public double getSizeSecondaryMoment() {
        double normalMeanSize = getNormalSize(getMeanSize());
        double sum = 0.0;
        for (MotionPoint point : pointList) {
            sum += (getNormalSize(point.getSize()) - normalMeanSize) * (getNormalSize(point.getSize()) - normalMeanSize);
        }
        return sum;
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
        sumSize += point.getSize();
        pointList.add(point);
    }
}
