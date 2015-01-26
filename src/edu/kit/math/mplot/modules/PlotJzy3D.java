package edu.kit.math.mplot.modules;

//~--- non-JDK imports --------------------------------------------------------

import org.jzy3d.chart.Chart;
import org.jzy3d.chart.controllers.keyboard.camera.AWTCameraKeyController;
import org.jzy3d.chart.controllers.mouse.camera.AWTCameraMouseController;
import org.jzy3d.chart.factories.AWTChartComponentFactory;
import org.jzy3d.chart.factories.IChartComponentFactory;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Builder;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid;
import org.jzy3d.plot3d.primitives.LineStrip;
import org.jzy3d.plot3d.primitives.Scatter;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.rendering.canvas.Quality;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.maths.TicToc;
import org.jzy3d.maths.Utils;
import org.jzy3d.plot3d.primitives.AbstractDrawable;
import org.jzy3d.plot3d.primitives.Point;
import org.jzy3d.plot3d.primitives.Polygon;
import org.jzy3d.plot3d.rendering.view.Renderer2d;

//~--- JDK imports ------------------------------------------------------------

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Component;
import java.awt.BorderLayout;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

import javax.swing.JPanel;

class PlotJzy3D extends Plot {
    org.jzy3d.plot3d.rendering.canvas.Quality                rendererQuality = Quality.Advanced;
    org.jzy3d.chart.factories.IChartComponentFactory.Toolkit rendererToolkit = IChartComponentFactory.Toolkit.awt;
    private Chart                                            chart;
    private Coord3d[][]                                      points;
    private JPanel                                           panel;
    private LineStrip                                        ls;
    private AWTCameraKeyController                           ckc;
    private AWTCameraMouseController                         cmc;
    private org.jzy3d.colors.Color                           colors;

    PlotJzy3D(javax.swing.RootPaneContainer currentFigure, Data[] data, String... args) {
        points = new Coord3d[data.length][];
        chart  = AWTChartComponentFactory.chart(rendererQuality, rendererToolkit);
        panel  = new JPanel();

        for (int i = 0; i < data.length; i++) {
            points[i] = data[i].getJzy3dDataTable();
            super.parseLinespecs(args[i]);
            colors = new org.jzy3d.colors.Color(color[0], color[1], color[2]);
            setPointRenderer();
            setLineRenderer(points[i]);
        }

        chart.addController(ckc = new AWTCameraKeyController());
        chart.addController(cmc = new AWTCameraMouseController());
        panel.setLayout(new BorderLayout());
        panel.add((Component) chart.getCanvas());
        currentFigure.getContentPane().add(panel, BorderLayout.CENTER);
    }

    @Override
    void _Plot() {
        chart.stopAnimator();
        chart.removeController(ckc);
        chart.removeController(cmc);
    }

    private void setLineRenderer(Coord3d[] singlePoints) {
        switch (lStyle) {
        case solid :
            ls = new LineStrip(Arrays.asList(singlePoints));
            ls.setWireframeColor(colors);
            chart.getScene().getGraph().add(ls);

            break;

        case plain :
            Scatter scatter = new Scatter(singlePoints);

            chart.getScene().add(scatter);

            break;

        case surface : // paused this module....
            double [][]distDataProp = new double[][] {{.25,.45, .20},{.56, .89, .45}, {.6, .3,.7}};
            List<Polygon> polygons = new ArrayList<Polygon>();
            for(int i = 0; i < distDataProp.length -1; i++){
                for(int j = 0; j < distDataProp[i].length -1; j++){
                    Polygon polygon = new Polygon();
                    polygon.add(new Point( new Coord3d(i, j, distDataProp[i][j]) ));
                    polygon.add(new Point( new Coord3d(i, j+1, distDataProp[i][j+1]) ));
                    polygon.add(new Point( new Coord3d(i+1, j+1, distDataProp[i+1][j+1]) ));
                    polygon.add(new Point( new Coord3d(i+1, j, distDataProp[i+1][j]) ));
                    polygons.add(polygon);
                }
            }

            // Creates the 3d object
            Shape surface = new Shape(polygons);
            surface.setColorMapper(new ColorMapper(new ColorMapRainbow(), surface.getBounds().getZmin(), surface.getBounds().getZmax(), new org.jzy3d.colors.Color(1,1,1,1f)));
            surface.setWireframeDisplayed(true);
            surface.setWireframeColor(org.jzy3d.colors.Color.BLACK);
            surface.setFaceDisplayed(true);
            surface.setWireframeDisplayed(false);
            chart.getScene().getGraph().add(surface);

            break;

        default :
            ls = new LineStrip(Arrays.asList(singlePoints));
            ls.setWireframeColor(colors);
            chart.getScene().getGraph().add(ls);

            break;
        }
    }

    private void setPointRenderer() {
        switch (mStyle) {
        default :
            break;
        }
    }
}
