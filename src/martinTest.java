//~--- non-JDK imports --------------------------------------------------------

import edu.kit.math.mplot.MPlot;

public class martinTest {
    public static void main(String[] args) throws InterruptedException {
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

        newPlot.plot(x, y, x, x);


        System.out.println("test");


    }
}