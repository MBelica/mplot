package edu.kit.math.mplot.modules;

//~--- non-JDK imports --------------------------------------------------------

import java.awt.*;
import java.awt.Panel;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import org.jzy3d.chart.Chart;
import org.jzy3d.chart.factories.IChartComponentFactory;
import org.jzy3d.chart.factories.AWTChartComponentFactory;
import org.jzy3d.plot3d.primitives.*;
import org.jzy3d.plot3d.rendering.canvas.Quality;
import org.jzy3d.chart.controllers.keyboard.camera.AWTCameraKeyController;
import org.jzy3d.chart.controllers.mouse.camera.AWTCameraMouseController;
import org.jzy3d.maths.Coord3d;

//~--- JDK imports ------------------------------------------------------------

import java.util.Arrays;

public class PlotJzy3D extends Plot {

    PlotJzy3D (Figure currentFigure, Data[] data, String... args) {
        Chart chart = AWTChartComponentFactory.chart(Quality.Advanced, IChartComponentFactory.Toolkit.awt);

        for(  Data dataElement :  data) {

            org.jzy3d.colors.Color lineColor  = org.jzy3d.colors.Color.BLUE;
            //org.jzy3d.colors.Color pointColor = org.jzy3d.colors.Color.BLACK;

            Coord3d[] points = dataElement.getJzy3dDataTable();

            //Scatter scatter = new Scatter(points, pointColor);

            setLineRenderer(args);
            LineStrip ls = new LineStrip(Arrays.asList(points));
            ls.setWireframeColor(lineColor);

            chart.getScene().getGraph().add(ls);
        }
        chart.addController(new AWTCameraKeyController());
        chart.addController(new AWTCameraMouseController());

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        if (chart.getCanvas() instanceof Canvas) {
            panel.add((Canvas) chart.getCanvas());
        } else  {
            panel.add((Panel) chart.getCanvas());
        }

        currentFigure.getContentPane().add(panel, BorderLayout.CENTER);
    }

    private void setLineRenderer(String... args) {
        if(!args[0].equals("")) {
             System.out.println("LineRenderer");
        }
    }
}