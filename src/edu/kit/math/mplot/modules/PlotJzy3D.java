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

    PlotJzy3D (Figure currentFigure, Data[] data, String... args) {
        Chart chart = AWTChartComponentFactory.chart(Quality.Advanced, IChartComponentFactory.Toolkit.awt);
        for (int i = 0; i < data.length; i++) {
            Color[] colors = data[i].getJzy3dDataColor();
            Coord3d[] points = data[i].getJzy3dDataTable();
            Scatter scatter = new Scatter(points, colors);
            chart.getScene().add(scatter);
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
}