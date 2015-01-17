//~--- non-JDK imports --------------------------------------------------------

import edu.kit.math.mplot.MPlot;

public class martinTest {
    public static void main(String[] args) throws InterruptedException { // ToDo: multiple plots result in error with just last plot been resizeable
        double[] x = new double[100],
                y = new double[100],
                z = new double[100];

        for (int i = 0; i < x.length; i++) {
            x[i] = 0.1 * i;
            y[i] = Math.pow(x[i], 2);
            z[i] = Math.pow(x[i], 3);
        }


        int sleepTime = 3000;

        MPlot newPlot   = new MPlot();

        newPlot.figure("Name", "Martins Figure", "NumberTitle", "false", "Position", "[100 100 1280 720]");
        newPlot.pause(sleepTime);

        newPlot.plot(x, y);

        newPlot.pause(sleepTime);

        newPlot.clf("Martins Figure");
        newPlot.pause(sleepTime);

        newPlot.close("all");
    }
}