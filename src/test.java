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
        MPlot.figure();             java.lang.Thread.sleep(1000);
        MPlot.plot(x, y, "b--.");   java.lang.Thread.sleep(1000);
        MPlot.clf();                java.lang.Thread.sleep(1000);
    }
}
