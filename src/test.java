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

        int   sleepTime = 1500;
        MPlot newPlot   = new MPlot();

        /* Figure 0 */
        newPlot.figure();
        newPlot.pause(sleepTime);
        newPlot.plot(x, x, "");
        newPlot.pause(sleepTime);
        /* Figure 7 */
        newPlot.figure(7);
        newPlot.pause(sleepTime);
        newPlot.plot(x, y, "b--.");
        newPlot.pause(sleepTime);

        /* Figure 8 */
        newPlot.figure("Name", "Martins Figure", "NumberTitle", "false", "Position", "[100 100 750 500]");
        newPlot.pause(sleepTime);
        newPlot.plot(x, z, "r-*");
        newPlot.pause(sleepTime);

        /* Figure 9 */
        newPlot.figure();
        newPlot.pause(sleepTime);
        newPlot.plot(x, y, x, z, x, x);
        newPlot.pause(sleepTime);

        /* set figure 0 active */
        newPlot.figure(0);
        newPlot.pause(sleepTime);

        /* figure with id 100 does not exist, output error msg and keep going */
        newPlot.close(100);
        newPlot.pause(sleepTime);

        /* try to clear not existent figure with id 50, output error msg and keep going */
        newPlot.clf(50);
        newPlot.pause(sleepTime);

        /* close active figure */
        newPlot.close();
        newPlot.pause(sleepTime);

        /* clear active figure */
        newPlot.clf("reset");
        newPlot.pause(sleepTime);

        /* close all other figures */
        newPlot.close("all");
        newPlot.pause(sleepTime);
    }
}