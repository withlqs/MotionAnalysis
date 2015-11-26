package me.getline.motionanalysis;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static java.lang.Math.sqrt;

public class MainActivity extends AppCompatActivity {
    MotionCalculator calculator = new MotionCalculator();
    MotionTimeMonitor monitor = new MotionTimeMonitor();
    MotionTimeMonitor standard;
    private TextView textview;
    private VelocityTracker vTracker = null;
    private LogWriter logWriter;
    private long eventCount;
    private double meanVelocity;

    private Button define;
    private Button compare;
    private Button newMonitor;
    private long startTime, endTime;

    public void defineStandard() {
        standard = monitor;
        monitor = new MotionTimeMonitor();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textview = (TextView)findViewById(R.id.textview);
        logWriter = new LogWriter("MotionLog.log", true);
        define = (Button) findViewById(R.id.define);
        compare = (Button) findViewById(R.id.compare);
        newMonitor = (Button) findViewById(R.id.new_monitor);

        newMonitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monitor = new MotionTimeMonitor();
            }
        });
        define.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                defineStandard();
            }
        });
        compare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double diff = monitor.getDistance(standard);
                textview.setText("" + diff + " " + MotionTimeMonitor.getThreshold());
                if (MotionTimeMonitor.thresholdCompare(diff)) {
                    textview.setText(textview.getText() + "\nYes");
                } else {
                    textview.setText(textview.getText() + "\nNo");
                }
                monitor = new MotionTimeMonitor();
            }
        });
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
                calculator.init();
                eventCount = 0;
                meanVelocity = 0.0;
                vTracker.addMovement(event);
                startTime = event.getEventTime();
                break;
            case MotionEvent.ACTION_MOVE:
                calculator.addPoint(new MotionPoint(event));
                vTracker.addMovement(event);
                vTracker.computeCurrentVelocity(1000);
                double xVelocity = vTracker.getXVelocity();
                double yVelocity = vTracker.getYVelocity();
                double nowVelocity = sqrt(xVelocity*xVelocity+yVelocity*yVelocity);
                meanVelocity += (nowVelocity-meanVelocity)/(eventCount+1);
                eventCount++;
//                logString = "Now Event Count is " + eventCount + " X is " + event.getX() + " Y is " + event.getY();
//                logString += "\nNow Size is " + event.getSize();
//                textview.setText(logString);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                //vTracker.recycle();
                //textview.setText(textview.getText() + "\nX:" + calculator.getXSecondaryMoment() + " Y:" + calculator.getYSecondaryMoment() + " Size:" + calculator.getSizeSecondaryMoment());
                //textview.setText(textview.getText() + "\n" + event.getEventTime() / (1000));
                endTime = event.getEventTime();
                monitor.addMotionTime(new MotionTimer(startTime, endTime));
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
