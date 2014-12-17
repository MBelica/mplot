//~--- non-JDK imports --------------------------------------------------------

import edu.kit.math.mplot.MPlot;

public class test {
    public static void main(String[] args) throws InterruptedException { // ToDo: ich fände es gut wenn man throws weglassen könnte, aber ich weis noch nicht wie
        double[] x = new double[100],
                 y = new double[100],
                 z = new double[100];

        for (int i = 0; i < x.length; i++) {
            x[i] = 0.1 * i;
            y[i] = Math.pow(x[i], 2);
            z[i] = Math.pow(x[i], 3);
        }

        int   sleepTime = 3000;
        MPlot newPlot   = new MPlot();

        newPlot.pause("off");
        /* Figure 0 */
        newPlot.figure("Name", "Martins Figure", "NumberTitle", "false", "Position", "[100 100 750 500]");
        newPlot.pause(sleepTime);
        newPlot.plot(x, x, "");
        newPlot.pause("newstate");
        newPlot.pause(sleepTime);
        newPlot.plot(x, y, "b--.");
        newPlot.pause(sleepTime);
        newPlot.hold("on");
        newPlot.plot(x, z, "r-*");
        newPlot.pause(sleepTime);
        newPlot.hold();
        newPlot.pause(sleepTime);
        newPlot.plot(x, x, "");
        newPlot.pause(sleepTime);

        /* close all other figures */
        newPlot.close("all");
        newPlot.pause(sleepTime);
    }
}