//~--- non-JDK imports --------------------------------------------------------

import edu.kit.math.mplot.MPlot;

public class martinTest {

    // ToDo: Upcoming changes: rewrite GRoot and split it up into core and groot with catching and passing separated. Thread, memory and runnable management + error catching
    // ToDo: Loading image with core this is going to
    // ToDo: Interfaces to close and round up
    public static void main(String[] args) throws InterruptedException {
        int   sleepTime = 1500;
        MPlot newPlot   = new MPlot();

        // ** 2D Test **//
        double[] x = new double[100],
                 y = new double[100];

        for (int i = 0; i < x.length; i++) {
            x[i] = 0.1 * i;
            y[i] = Math.pow(x[i], 2);
        }

        newPlot.figure("Name", "Martins Figure 2D - 1", "NumberTitle", "false", "Position", "[100 100 1280 720]",
                       "Color", "[0.0 1.00 1.0]");
        newPlot.plot(y);
        newPlot.pause(sleepTime);
        newPlot.figure("Name", "Martins Figure 2D - 2", "NumberTitle", "false", "Position", "[100 100 1280 720]",
                       "Color", "[0.7 0.0 0.2]");
        newPlot.plot(x, y, x, x);
        newPlot.pause();

        // ** 3D Test **//
        // 1. 3D Plot
        int      size = 500;
        double[] x3d  = new double[size],
                 y3d  = new double[size],
                 z3d  = new double[size];

        for (int i = 0; i < size; i++) {
            x3d[i] = (float) Math.sin(i);
            y3d[i] = (float) Math.cos(i);
            z3d[i] = (float) i;
        }

        // 2. 3D Plot
        double[] x32d = new double[size],
                 y32d = new double[size],
                 z32d = new double[size];

        for (int i = 0; i < size; i++) {
            x32d[i] = 0f;
            y32d[i] = 0f;
            z32d[i] = (float) i;
        }

        newPlot.figure("Name", "Martins 3d Figure", "NumberTitle", "false", "Position", "[250 250 852 480]");
        newPlot.pause(sleepTime);
        newPlot.plot3(x3d, y3d, z3d);
        newPlot.pause(sleepTime);
        newPlot.pause(sleepTime);
        newPlot.clf();
        newPlot.pause(sleepTime);
        newPlot.close("all");
    }
}