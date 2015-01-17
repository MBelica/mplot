package edu.kit.math.mplot.modules;

import java.awt.*;
import java.awt.Panel;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import org.jzy3d.chart.Chart;
import org.jzy3d.chart.factories.IChartComponentFactory;
import org.jzy3d.colors.Color;
import org.jzy3d.chart.factories.AWTChartComponentFactory;
import org.jzy3d.plot3d.primitives.*;
import org.jzy3d.plot3d.rendering.canvas.Quality;
import org.jzy3d.chart.controllers.keyboard.camera.AWTCameraKeyController;
import org.jzy3d.chart.controllers.mouse.camera.AWTCameraMouseController;
import org.jzy3d.maths.Coord3d;

public class PlotJzy3D extends Plot {

    PlotJzy3D (Figure currentFigure) {

        Color[]   colors = Data.getJzy3dDataColor(); // TODO ATM STATIC CHANGE LATER
        Coord3d[] points = Data.getJzy3dDataTable();

        Scatter scatter = new Scatter(points, colors);
        Chart chart = AWTChartComponentFactory.chart(Quality.Advanced, IChartComponentFactory.Toolkit.awt);
        chart.getScene().add(scatter);
        chart.addController(new AWTCameraKeyController());
        chart.addController(new AWTCameraMouseController());

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout()); // Use BorderLayout
        if (chart.getCanvas() instanceof Canvas) {
            panel.add((Canvas) chart.getCanvas());
        } else  {
            panel.add((Panel) chart.getCanvas());
        }
        currentFigure.getContentPane().add(panel, BorderLayout.CENTER);
    }
}