package edu.kit.math.mplot;

//~--- JDK imports ------------------------------------------------------------

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Watchdog {
    public static void echo(String ouputText, int reportLevel) {
        createOutput("echo", reportLevel, ouputText);
    }

    public static void debugEcho(String debugText, int reportLevel) {
        createOutput("debug", reportLevel, debugText);
    }

    private static void createOutput(String method, int reportLevel, String text) {
        if (reportLevel <= MPlot.echoReportingLevel.ordinal()) {
            MPlot.outputStyle mode;
            String            filename;

            if (method.equals("echo")) {
                mode     = MPlot.echoOutput;
                filename = MPlot.systemStartTime + ".echoOutput.txt";
            } else {
                mode     = MPlot.debugOutput;
                filename = MPlot.systemStartTime + ".debugEchoOutput.txt";
            }

            switch (mode) {
            case console :
                System.out.println(text);

                break;

            case file :
                try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)))) {
                    pw.println(text);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                break;

            case none :
                break;

            default :
                break;
            }
        }
    }
}