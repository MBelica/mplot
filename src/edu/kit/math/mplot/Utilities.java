package edu.kit.math.mplot;

//~--- JDK imports ------------------------------------------------------------

import java.io.*;

class Utilities {
    static long systemStartTime  = 0;
    static int  infPauseSequence = 250;

    static outputStyle echoOutput  = outputStyle.console;
    static outputStyle debugOutput = outputStyle.file;

    protected enum outputStyle { console, file, none }


    protected static void setStartTime (long time) {
        systemStartTime = time;
    }

    protected static void echo(String ouputText) {
        createOutput(echoOutput, ouputText, systemStartTime + ".echoOutput.txt");
    }

    protected static void debugEcho(String debugText) {
        createOutput(debugOutput, debugText, systemStartTime + ".debugEchoOutput.txt");
    }

    private static void createOutput(outputStyle methode, String text, String filename) {
        switch (methode) {
            case console :
                System.out.println(text);

                break;

            case file :
                try (PrintWriter pw = new PrintWriter( new BufferedWriter( new FileWriter(filename, true)) )) {
                    pw.println(text);
                }catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;

            case none :
                break;

            default :
                break;
        }
    }

    protected static double[] getIndexVs(double[] y) {
        double[] x = new double[y.length];

        for (int i = 0; i < x.length; i++) {
            x[i] = i + 1.0;
        }

        return x;
    }

    protected static double getExecuteDuration() {
        double time = 0;
        long   t    = (System.nanoTime() - systemStartTime);


        time =  Math.round(100000.0 * convertLong(t, "n")) / 100000.0;
        return time;
    }

    protected static double convertLong(long t, String... conv) {
        double factor = 1;
        double[] unitPrefix = new double[2];

        if (conv.length > 0) {
            for (int i = 0; i < conv.length; i++) {
                switch (conv[i]) {
                    case "G":
                        unitPrefix[i] = 1000000000;
                        break;
                    case "M":
                        unitPrefix[i] = 1000000;
                        break;
                    case "k":
                        unitPrefix[i] = 1000;
                        break;
                    case "h":
                        unitPrefix[i] = 100;
                        break;
                    case "da":
                        unitPrefix[i] = 10;
                        break;
                    case "d":
                        unitPrefix[i] = 0.1;
                        break;
                    case "c":
                        unitPrefix[i] = 0.01;
                        break;
                    case "m":
                        unitPrefix[i] = 0.001;
                        break;
                    case "mk":
                        unitPrefix[i] = 0.000001;
                        break;
                    case "n":
                        unitPrefix[i] = 0.000000001;
                        break;
                    case "p":
                        unitPrefix[i] = 0.000000000001;
                        break;
                    default:
                        unitPrefix[i] = 1;
                }
            }
            if (conv.length == 1) unitPrefix[1] = 1;
            factor = unitPrefix[0] / unitPrefix[1];
        }

        return (t * factor);
    }
}