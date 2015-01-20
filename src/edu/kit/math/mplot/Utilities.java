package edu.kit.math.mplot;

class Utilities {

    protected static double[] getIndexVs(double[] y) {
        double[] x = new double[y.length];

        for (int i = 0; i < x.length; i++) {
            x[i] = i + 1.0;
        }

        return x;
    }

    protected static double getExecuteDuration() {
        long   t    = (System.nanoTime() - MPlot.systemStartTime);

        return ( Math.round(100000.0 * convertLong(t, "n")) / 100000.0 );
    }

    protected static double convertLong(long t, String... conv) {
        double factor;
        double[] unitPrefix = new double[2];

        double result = t;
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
            if ( !Double.isNaN(factor) ) result *= factor;
        }

        return result;
    }
}