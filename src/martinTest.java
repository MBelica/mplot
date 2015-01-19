//~--- non-JDK imports --------------------------------------------------------

import edu.kit.math.mplot.MPlot;

public class martinTest { // todo catchjava.lang.NullPointerException für leere daten + todo LEISTUNG!!!! + todo float, long, int double für plots + 3d lineplot and not scatter + linepecs for 3D + resize of axes + interface as connector between modules
    public static void main(String[] args) throws InterruptedException {

        int sleepTime = 3000;
        MPlot newPlot   = new MPlot();


        //** 2D Test **//
        double[] x = new double[100], y = new double[100];
        for (int i = 0; i < x.length; i++) {
            x[i] = 0.1 * i;
            y[i] = Math.pow(x[i], 2);
        }
        newPlot.figure("Name", "Martins Figure", "NumberTitle", "false", "Position", "[100 100 1280 720]", "Color", "[0 1 0.8]");
        newPlot.pause(sleepTime);
        newPlot.plot(x, y, "+ ");
        newPlot.pause(sleepTime);

        //** 3D Test **//
        // 1. 3D Plot
        double[] x3d = new double[10000], y3d = new double[10000], z3d = new double[10000];
        for(int i=0; i<10000; i++){
            x3d[i] = (float) Math.sin(i);
            y3d[i] = (float) Math.cos(i);
            z3d[i] = (float) i;
        }
        // 2. 3D Plot
        double[] x32d = new double[10000], y32d = new double[10000], z32d = new double[10000];
        for(int i=0; i<10000; i++){
            x32d[i] = 0f;
            y32d[i] = 0f;
            z32d[i] = (float) i;
        }
        newPlot.figure("Name", "Martins 3d Figure", "NumberTitle", "false", "Position", "[250 250 852 480]");
        newPlot.pause(sleepTime);
        newPlot.plot3(x3d, y3d, z3d, x32d, y32d, z32d);
        newPlot.pause(sleepTime);

        newPlot.close("all");
    }
}