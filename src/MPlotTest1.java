import java.lang.Math;
import javax.swing.JFrame;
import edu.kit.math.mplot.*;

public class MPlotTest1 {
   public static void main(String[] args) {
      double[] x = new double[10],
               y = new double[10];
      for(int i=0;i<x.length;i++) {
         x[i] = 0.1*i;
         y[i] = Math.pow(x[i],2);
      }
      
      MPlot mplot = new MPlot();
      mplot.figure();
      mplot.plot(x,y);
      
   }
}