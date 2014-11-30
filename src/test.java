import edu.kit.math.mplot.*;
import java.lang.InterruptedException;

public class test {


    public static void main(String[] args) throws InterruptedException {

        double[] x = new double[100], y = new double[100], z = new double[100];
        for(int i=0; i<x.length; i++) {
            x[i] = 0.1*i;
            y[i] = Math.pow(x[i],2);
            z[i] = Math.pow(x[i],3);
        }

        int sleepTime = 1500;

        MPlot newPlot = new MPlot();

        /* Figure 0 */
        newPlot.figure();                   java.lang.Thread.sleep(sleepTime);
            newPlot.plot(x, x, "");         java.lang.Thread.sleep(sleepTime);
        /* Figure 7 */
        newPlot.figure(7);                  java.lang.Thread.sleep(sleepTime);
            newPlot.plot(x, y, "b--.");     java.lang.Thread.sleep(sleepTime);
        /* Figure 8 */
        newPlot.figure("Name","Martins Figure","NumberTitle", "false", "Position", "[100 100 750 500]");   java.lang.Thread.sleep(sleepTime);
            newPlot.plot(x, z, "r-*");      java.lang.Thread.sleep(sleepTime);
        /* Figure 9 */
        newPlot.figure();                   java.lang.Thread.sleep(sleepTime);
            newPlot.plot(x, y, x, z, x, x); java.lang.Thread.sleep(sleepTime);

        /* set figure 0 active */
        newPlot.figure(0);                  java.lang.Thread.sleep(sleepTime);

        /* figure with id 100 does not exist, output error msg and keep going */
        newPlot.close(100);                 java.lang.Thread.sleep(sleepTime);
        /* try to clear not existent figure with id 50, output error msg and keep going */
        newPlot.clf(50);                    java.lang.Thread.sleep(sleepTime);

        /* close active figure */
        newPlot.close();                    java.lang.Thread.sleep(sleepTime);
        /* clear active figure */
        newPlot.clf("reset");               java.lang.Thread.sleep(sleepTime);
        /* close all other figures */
        newPlot.close("all");               java.lang.Thread.sleep(sleepTime);
    }
}
