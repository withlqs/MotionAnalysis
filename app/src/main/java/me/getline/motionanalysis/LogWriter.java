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
    private boolean appendMode;
    private SimpleDateFormat dateFormatter;

    public LogWriter(String fileName, boolean isAppend) {
        appendMode = isAppend;
        setFileName(fileName);
    }

    private void setFileName(String fileName) {
        logFile = new File(Environment.getExternalStorageDirectory(), fileName);
        Log.i("ExternalPath", logFile.toString());
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
        if (appendMode) {
            write("Init log file", true);
        } else {
            write("Init log file", false);
        }
    }

    private String getFormattedDate() {
        return dateFormatter.format(new Date());
    }

    private void write(String string, boolean isAppend) {
        try {
            writer = new FileWriter(logFile, isAppend);
        } catch (IOException e) {
            Log.i("LogWriter", e.toString());
        }
        String writeString = getFormattedDate() + "," + string + "\n";
        try {
            writer.write(writeString);
        } catch (IOException e) {
            Log.i("LogWriter", "Write Error");
        }
        try {
            writer.close();
        } catch (IOException e) {
            Log.i("LogWriter:write", "close error");
        }

    }

    public void write(String string) {
        write(string, true);
    }

    /*protected void finalize() {
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
    }*/
}
