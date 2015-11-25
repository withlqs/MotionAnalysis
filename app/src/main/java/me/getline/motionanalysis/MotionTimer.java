package me.getline.motionanalysis;

/**
 * Created by apple on 2015/11/25.
 */
public class MotionTimer {
    private long startTime;
    private long endTime;

    public MotionTimer(long _startTime, long _endTime) {
        setStartTime(_startTime);
        setEndTime(_endTime);
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getDuration() {
        return getEndTime() - getStartTime();
    }
}
