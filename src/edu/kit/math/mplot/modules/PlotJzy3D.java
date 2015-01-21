package edu.kit.math.mplot.modules;

//~--- non-JDK imports --------------------------------------------------------

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
import java.awt.*;
import java.awt.BorderLayout;
import javax.swing.JPanel;

class PlotJzy3D extends Plot { // todo linespecs + surface plot + resize of axes

    private Chart chart;
    private Coord3d[] points;
    private JPanel panel;
    private LineStrip ls;
    private AWTCameraKeyController ckc;
    private AWTCameraMouseController cmc;
    private org.jzy3d.colors.Color lineColor;

    org.jzy3d.plot3d.rendering.canvas.Quality rendererQuality                = Quality.Advanced;
    org.jzy3d.chart.factories.IChartComponentFactory.Toolkit rendererToolkit = IChartComponentFactory.Toolkit.awt;

    PlotJzy3D (javax.swing.RootPaneContainer currentFigure, Data[] data, String... args) {
        chart = AWTChartComponentFactory.chart(rendererQuality, rendererToolkit);
        for(  Data dataElement :  data) {

            lineColor  = org.jzy3d.colors.Color.BLUE;
            //org.jzy3d.colors.Color pointColor = org.jzy3d.colors.Color.BLACK;

            points = dataElement.getJzy3dDataTable();

            //Scatter scatter = new Scatter(points, pointColor);

            //setLineRenderer(args);
            ls = new LineStrip(Arrays.asList(points));
            ls.setWireframeColor(lineColor);

            chart.getScene().getGraph().add(ls);
        }
        chart.addController(ckc = new AWTCameraKeyController());
        chart.addController(cmc = new AWTCameraMouseController());

        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add((Component) chart.getCanvas());

        currentFigure.getContentPane().add(panel, BorderLayout.CENTER);
    }

    void _Plot () {
        chart.stopAnimator();
        chart.removeController(ckc);
        chart.removeController(cmc);
    }

}