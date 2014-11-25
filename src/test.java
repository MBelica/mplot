import edu.kit.math.mplot.*;
import java.lang.InterruptedException;


public class test {


    public static void main(String[] args) throws InterruptedException {

        double[] x = new double[10], y = new double[10];
        for(int i=0; i<x.length; i++) {
            x[i] = 0.1*i;
            y[i] = Math.pow(x[i],2);
        }


        // open 2 figures and plot (for better identification plotted once (x,x) )
        MPlot.figure(); /* Figure 0 */              java.lang.Thread.sleep(1000);
        MPlot.plot(x, y, "b--.");                   java.lang.Thread.sleep(1000);
        MPlot.figure(); /* Figure 1 */              java.lang.Thread.sleep(1000);
        MPlot.plot(x, x); /* without linespecs */   java.lang.Thread.sleep(1000);
        /* set figure 0 active */
        MPlot.figure(0);                            java.lang.Thread.sleep(1000);
        /* create a new figure with index 5 */
        MPlot.figure(5);                            java.lang.Thread.sleep(1000);
        MPlot.plot(x, y, "c-.q");                   java.lang.Thread.sleep(1000);
        /* figure with id 100 does not exist, output error msg and keep going */
        MPlot.clf(100);                             java.lang.Thread.sleep(1000);
        /* close figure with id 5 */
        MPlot.clf(5);                               java.lang.Thread.sleep(1000);
        /* and close the rest of them again */
        MPlot.clf();                                java.lang.Thread.sleep(1000);
        MPlot.clf();                                java.lang.Thread.sleep(1000);
    }
}
