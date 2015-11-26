package me.getline.motionanalysis;

import java.util.ArrayList;

/**
 * Created by apple on 2015/11/25.
 */
public class MotionTimeMonitor {
    public static double threshold;
    public ArrayList<MotionTimer> motionTimeList = new ArrayList<>();
    private long minDuration;
    private long maxDuration;

    public MotionTimeMonitor() {
        init();
    }

    public static double getThreshold() {
        return threshold;
    }

    public static void setThreshold(double threshold) {
        MotionTimeMonitor.threshold = threshold;
    }

    public static boolean thresholdCompare(double _threshold) {
        if (_threshold < threshold) {
            threshold = threshold * 0.95 + (_threshold + threshold / 2.0) * 0.05;
            return true;
        } else {
            return false;
        }
    }

    public int getMotionTimerSize() {
        return motionTimeList.size();
    }

    public void init() {
        motionTimeList.clear();
        minDuration = Long.MAX_VALUE;
        maxDuration = Long.MIN_VALUE;
        threshold = 1;
    }

    public void addMotionTime(MotionTimer motionTime) {
        motionTimeList.add(motionTime);
        minDuration = Math.min(minDuration, motionTime.getDuration());
        maxDuration = Math.max(maxDuration, motionTime.getDuration());
    }

    public double getNormalDuration(long time) {
        return (1.0 * (time - minDuration)) / (maxDuration - minDuration);
    }

    public double getDistance(MotionTimeMonitor motionTimeMonitor) {
        int size = getMotionTimerSize();
        if (size != motionTimeMonitor.getMotionTimerSize()) {
            return getThreshold() + 1;
        }
        double diff = 0;
        for (int i = 0; i < size; i++) {
            MotionTimer a = motionTimeList.get(i);
            MotionTimer b = motionTimeMonitor.motionTimeList.get(i);
            diff += Math.abs(getNormalDuration(a.getDuration()) - motionTimeMonitor.getNormalDuration(b.getDuration()));
        }
        return diff;
    }

}
