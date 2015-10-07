package me.getline.motionanalysis;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogWriter {
    private File logFile;
    private FileWriter writer;
    private boolean isAppend = true;
    private SimpleDateFormat dateFormatter;

    public LogWriter(String fileName) {
        setFileName(fileName);
    }

    public LogWriter(String fileName, boolean appendMode) {
        isAppend = appendMode;
        setFileName(fileName);
    }

    public void setFileName(String fileName) {
        logFile = new File(Environment.getExternalStorageDirectory(), fileName);
        Log.i("ExternalPath", logFile.toString());
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
        try {
            writer = new FileWriter(logFile, isAppend);
        } catch (IOException e) {
            Log.i("LogWriter", e.toString());
        }
    }

    public void write(String string) {
        String writeString = dateFormatter.format(new Date()) + " " + string;
        try {
            writer.write(writeString);
        } catch (IOException e) {
            Log.i("LogWriter", "Write Error");
        }
    }

    protected void finalize() {
        try {
            super.finalize();
        } catch (Throwable t) {
            //do nothing
        }
        try {
            writer.close();
        } catch (IOException e) {
            Log.i("LogWriter", "Finalize Error");
        }
    }
}
