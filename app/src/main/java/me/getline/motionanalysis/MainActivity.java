package me.getline.motionanalysis;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.TextView;

import static java.lang.Math.sqrt;

public class MainActivity extends AppCompatActivity {
    private TextView textview;
    private VelocityTracker vTracker = null;
    private LogWriter logWriter;
    private long eventCount;
    private double meanVelocity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textview = (TextView)findViewById(R.id.textview);
        logWriter = new LogWriter("MotionLog.log", true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        String logString;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (vTracker == null) {
                    vTracker = VelocityTracker.obtain();
                } else {
                    vTracker.clear();
                }
                eventCount = 0;
                meanVelocity = 0.0;
                vTracker.addMovement(event);
                break;
            case MotionEvent.ACTION_MOVE:
                vTracker.addMovement(event);
                vTracker.computeCurrentVelocity(1000);
                double xVelocity = vTracker.getXVelocity();
                double yVelocity = vTracker.getYVelocity();
                double nowVelocity = sqrt(xVelocity*xVelocity+yVelocity*yVelocity);
                meanVelocity += (nowVelocity-meanVelocity)/(eventCount+1);
                eventCount++;
                logString = "Now Event Count is " + eventCount + " X is " + event.getX() + " Y is " + event.getY();
                logString += "\nNow Size is " + event.getSize();
                textview.setText(logString);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                //vTracker.recycle();
                textview.setText("Now Event Count is "+eventCount+"\nMean Velocity is "+meanVelocity);
                logString = "" + meanVelocity;
                logWriter.write(logString);
                break;
        }
        //event.recycle();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
