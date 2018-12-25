/*
 * Logger.java
 *
 * Author: Roger Ngo
 * Copyright: 2018
 *
 * What the name says! It can log:
 *  1) To an internal circular buffer
 *  2) To file
 *  3) To standard out!
 *
 *  Usage: Logger.log("message to be logged")
 */

package com.mygdx.game.logging;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.util.CircularBuffer;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Logger implements Disposable {
    private int bufferLimit = 9;

    private SimpleDateFormat dateFormatterOnlyDate;
    private SimpleDateFormat dateFormatterDateTime;
    private CircularBuffer<LogMessage> logBuffer;
    private List<String> toFlush;

    private Color[] palette;
    private int paletteIdx;
    private int entries;
    private int batchSize;
    private int totalEntries;

    private static Logger theLogger;

    private Logger() {
        this.dateFormatterOnlyDate = new SimpleDateFormat("yyyy-MM-dd");
        this.dateFormatterDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        this.logBuffer = new CircularBuffer<>(this.bufferLimit);
        this.toFlush = new LinkedList<>();
        this.batchSize = 20;
        this.entries = 0;

        this.palette = new Color[] { Color.YELLOW, Color.PURPLE, Color.RED };
        this.paletteIdx = 0;
    }

    public static Logger getInstance() {
        return theLogger;
    }

    @Override
    public void dispose() {
        theLogger.flushToDisk();
    }

    public static void log(String message) {
        if(theLogger == null) {
            theLogger = new Logger();
        }

        if(theLogger.entries == theLogger.batchSize) {
            theLogger.flushToDisk();
        }

        String builtMessage = theLogger.buildMessage(message);

        theLogger.toFlush.add(builtMessage);

        theLogger.logBuffer.add(new LogMessage(
                builtMessage,
                new Date().getTime(),
                theLogger.palette[theLogger.paletteIdx]
            )
        );

        System.out.println(builtMessage);

        theLogger.entries++;
        theLogger.totalEntries++;

        theLogger.paletteIdx = (theLogger.totalEntries / (theLogger.bufferLimit / 3)) % theLogger.palette.length;
    }

    public static List<LogMessage> getBuffer() {
        if(theLogger == null) {
            theLogger = new Logger();
        }
        return theLogger.logBuffer.getBuffer();
    }

    private void flushToDisk() {
        System.out.println("Flushing log messages to disk.");

        try {
            String formattedDate = theLogger.dateFormatterOnlyDate.format(new Date());

            OutputStream os = new FileOutputStream("logs/log-" + formattedDate + ".txt", true);
            OutputStreamWriter out = new OutputStreamWriter(os);

            for(String s : theLogger.toFlush) {
                out.write(s);
            }

            out.close();
        } catch(Exception e) {
            System.out.println(e);
        } finally {
            theLogger.entries = 0;
            theLogger.toFlush = new LinkedList<>();
        }
    }

    private String buildMessage(String message) {
        String formattedDateTime = this.dateFormatterDateTime.format(new Date());

        StringBuilder builtMessage = new StringBuilder();

        builtMessage.append(formattedDateTime);
        builtMessage.append(" - ");
        builtMessage.append(message);
        builtMessage.append(System.lineSeparator());

        return builtMessage.toString();
    }
}
