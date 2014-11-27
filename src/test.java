import edu.kit.math.mplot.*;
import java.lang.InterruptedException;


public class test {


    public static void main(String[] args) throws InterruptedException {

        double[] x = new double[10], y = new double[10];
        for(int i=0; i<x.length; i++) {
            x[i] = 0.1*i;
            y[i] = Math.pow(x[i],2);
        }

        MPlot newPlot = new MPlot();
        /* Figure 0 */
            newPlot.figure();                   java.lang.Thread.sleep(1000);
            newPlot.plot(x, y, "b--.");         java.lang.Thread.sleep(1000);
        /* Figure 2. 1 skipped */
            newPlot.figure(2);                  java.lang.Thread.sleep(1000);
            newPlot.plot(x, x);                 java.lang.Thread.sleep(1000);
        /* Figure 3 */
            newPlot.figure("a","b","c", "d");   java.lang.Thread.sleep(1000);
            newPlot.plot(x, y, "r-*");          java.lang.Thread.sleep(1000);

        /* set figure 0 active */
            newPlot.figure(0);                  java.lang.Thread.sleep(1000);

        /* figure with id 100 does not exist, output error msg and keep going */
            newPlot.close(100);                 java.lang.Thread.sleep(1000);
        /* try to clear not existent figure with id 50, output error msg and keep going */
            newPlot.clf(50);                    java.lang.Thread.sleep(2000);

        /* clear active figure */
            newPlot.clf();                      java.lang.Thread.sleep(2000);
        /* close figure 2 */
            newPlot.close(2);                   java.lang.Thread.sleep(2000);
        /* close all other figures */
            newPlot.close("all");               java.lang.Thread.sleep(1000);
    }
}
