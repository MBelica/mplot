//~--- non-JDK imports --------------------------------------------------------

import org.jzy3d.chart.Chart;

import org.jzy3d.chart.controllers.keyboard.camera.AWTCameraKeyController;
import org.jzy3d.chart.controllers.mouse.camera.AWTCameraMouseController;
import org.jzy3d.chart.factories.AWTChartComponentFactory;
import org.jzy3d.chart.factories.IChartComponentFactory;

import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.Polygon;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.rendering.canvas.Quality;


//~--- JDK imports ------------------------------------------------------------

import java.awt.*;
import java.util.ArrayList;

//~--- JDK imports ------------------------------------------------------------

import java.util.List;

import javax.swing.*;


public class martin3DTest {

   public static void main(String[] args) throws InterruptedException {

       ArrayList<Coord3d> dataTable = new ArrayList<>();    // contains every figure, data and plot

       Double[] z = new Double[100];
       Double[]  y  = new Double[100];
       Double[]  x  = new Double[100];

       org.jzy3d.plot3d.primitives.Polygon pol = new org.jzy3d.plot3d.primitives.Polygon();
       List<Polygon> polygons = new ArrayList<>();
       for (int i = 0; i < 100; i++) {
         for (int j = 0; j < 100; j++) {
           x[i] = (double) i;
           y[i] = (double) j;
           z[i] = Math.sin(x[i] * y[i]);
           Coord3d cr =new Coord3d(x[i], y[i], z[i]);
           org.jzy3d.colors.Color cl = new org.jzy3d.colors.Color(x[i].floatValue(), y[i].floatValue(), z[i].floatValue(), 0.1f);

           org.jzy3d.plot3d.primitives.Point po = new org.jzy3d.plot3d.primitives.Point(cr, cl);
           pol.add(po);
           dataTable.add(new Coord3d(x[i], y[i], z[i]));
         }
       }

       polygons.add(pol);
        Shape surface = new Shape(polygons);
        org.jzy3d.plot3d.rendering.canvas.Quality                rendererQuality = Quality.Advanced;
        org.jzy3d.chart.factories.IChartComponentFactory.Toolkit rendererToolkit = IChartComponentFactory.Toolkit.awt;
        Chart chart = AWTChartComponentFactory.chart(rendererQuality, rendererToolkit);
        chart.getScene().getGraph().add(surface);
       chart.addController(new AWTCameraKeyController());
       chart.addController(new AWTCameraMouseController());
       
       JFrame frm = new JFrame();
       frm.setTitle("JFrame mit setSize()");
       frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frm.setSize(300,200);
       frm.setLocation(50,50);
       frm.setVisible(true);
       JPanel panel  = new JPanel();
       panel.setLayout(new BorderLayout());
       panel.add((Component) chart.getCanvas());
       frm.getContentPane().add(panel, BorderLayout.CENTER);
    }
}