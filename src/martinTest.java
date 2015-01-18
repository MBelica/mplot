//~--- non-JDK imports --------------------------------------------------------

import edu.kit.math.mplot.MPlot;

public class martinTest { // todo catchjava.lang.NullPointerException für leere daten + todo float double convert + linepecs for 3D
    public static void main(String[] args) throws InterruptedException {

        int sleepTime = 3000;
        MPlot newPlot   = new MPlot();


        //** 2D Test **//
        double[] x = new double[100], y = new double[100];
        for (int i = 0; i < x.length; i++) {
            x[i] = 0.1 * i;
            y[i] = Math.pow(x[i], 2);
        }
        newPlot.figure("Name", "Martins Figure", "NumberTitle", "false", "Position", "[100 100 1280 720]");
        newPlot.pause(sleepTime);
        newPlot.plot(x, y);
        newPlot.pause(sleepTime);

        //** 3D Test **//
        double[] x3d = new double[10000], y3d = new double[10000], z3d = new double[10000];
        for(int i=0; i<10000; i++){
            x3d[i] = (float) Math.sin(i);
            y3d[i] = (float) Math.cos(i);
            z3d[i] = (float) i;
        }
        newPlot.figure("Name", "Martins 3d Figure", "NumberTitle", "false", "Position", "[250 250 852 480]");
        newPlot.pause(sleepTime);
        newPlot.plot3(x3d, y3d, z3d);
        newPlot.pause(sleepTime);


        newPlot.close("all");
    }
}