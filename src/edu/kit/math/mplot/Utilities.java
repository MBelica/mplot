package edu.kit.math.mplot;

class Utilities {
    static final outputStyle output      = outputStyle.console;
    static final outputStyle debugOutput = outputStyle.console;

    protected enum outputStyle { console, file, none }

    protected static void echo(String ouputText) {
        switch (output) {
        case console :
            System.out.println(ouputText);

            break;

        case file :    // ToDo
            break;

        case none :
            break;

        default :
            break;
        }
    }

    protected static void debugEcho(String debugText) {
        switch (output) {
        case console :
            System.out.println(debugText);

            break;

        case file :    // ToDo
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
}