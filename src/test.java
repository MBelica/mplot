import edu.kit.math.mplot.*;
import java.lang.InterruptedException;


public class test {


    public static void main(String[] args) throws InterruptedException {

        double[] x = new double[10], y = new double[10];
        for(int i=0; i<x.length; i++) {
            x[i] = 0.1*i;
            y[i] = Math.pow(x[i],2);
        }

        // Zur Demonstration öffne Fenster, plotte gegeben Punkte und schließe aktuellstes Fenster - mit Pausen, damits besser aussieht
        MPlot.figure(); /* Figure 0 */  java.lang.Thread.sleep(1000);
        MPlot.plot(x, y, "b--.");       java.lang.Thread.sleep(1000);
        MPlot.figure(); /* Figure 1 */  java.lang.Thread.sleep(1000);
        MPlot.plot(x, y, "b--.");       java.lang.Thread.sleep(1000);
        MPlot.figure();  /* Figure 2 */ java.lang.Thread.sleep(1000);
        MPlot.plot(x, y, "b--.");       java.lang.Thread.sleep(1000);
        MPlot.figure();  /* Figure 3 */ java.lang.Thread.sleep(1000);
        MPlot.plot(x, y, "b--.");   java.lang.Thread.sleep(1000);
        MPlot.figure();  /* Figure 4 */ java.lang.Thread.sleep(1000);
        MPlot.plot(x, y, "b--.");   java.lang.Thread.sleep(1000);

        MPlot.clf();                java.lang.Thread.sleep(1000);
        MPlot.test(2); /** Delete Figure with nbr 2 */ java.lang.Thread.sleep(1000);
        MPlot.clf();                java.lang.Thread.sleep(1000);
        MPlot.clf();                java.lang.Thread.sleep(1000);
        MPlot.clf();                java.lang.Thread.sleep(1000);
    }
}
