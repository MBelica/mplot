//~--- non-JDK imports --------------------------------------------------------

import edu.kit.math.mplot.MPlot;

public class markusTest {
    public static void main(String[] args) throws InterruptedException {
        MPlot    m = new MPlot();
        double[] x = new double[100],
                 y = new double[100];

        for (int i = 0; i < x.length; i++) {
            x[i] = 0.1 * i;
        }

        int f1 = m.figure(23);

        for (int i = 0; i < x.length; i++) {
            y[i] = Math.sin(x[i]);
        }

        m.plot(x, y);

        int f2 = m.figure(10);

        for (int i = 0; i < x.length; i++) {
            y[i] = x[i] * x[i];
        }

        m.plot(x, y);
        java.lang.Thread.sleep(5000);
        m.figure(f1);

        for (int i = 0; i < x.length; i++) {
            y[i] = Math.pow(x[i], 0.5);
        }

        m.plot(x, y);
        java.lang.Thread.sleep(5000);
        m.figure(f1);
        m.clf();
        java.lang.Thread.sleep(5000);
        m.close("all");
    }
}