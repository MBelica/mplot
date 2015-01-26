
import java.util.ArrayList;
import java.util.List;

import org.jzy3d.chart.Chart;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.Point;
import org.jzy3d.plot3d.primitives.Polygon;
import org.jzy3d.plot3d.primitives.Shape;
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

import javax.swing.*;


public class martin3DTest {
    public static void main(String[] args) {
    // Build a polygon list

        Chart  chart  = AWTChartComponentFactory.chart(Quality.Advanced, IChartComponentFactory.Toolkit.awt);
        JPanel panel  = new JPanel();
        JFrame currentFigure = new JFrame();
        currentFigure.setVisible(true);
        currentFigure.setSize(720, 480);

        int dim = 10;
        double [][] dataTable = new double[dim][dim];

        for(int i = 0; i < dim; i++) for (int j = 0; j < dim; j++)
            dataTable[i][j] = Math.sin((double) (j*i));

        double [][] z =  dataTable;

        List<Polygon> polygons = new ArrayList<Polygon>();
        for(int i = 0; i < z.length -1; i++){
            for(int j = 0; j < z[i].length -1; j++){
                Polygon polygon = new Polygon();

                polygon.add(new Point( new Coord3d(i,   j,   z[i][j])    ));
                polygon.add(new Point( new Coord3d(i,   j+1, z[i][j+1])  ));
                polygon.add(new Point( new Coord3d(i+1, j+1, z[i+1][j+1])));
                polygon.add(new Point( new Coord3d(i+1, j,   z[i+1][j])  ));

                polygons.add(polygon);
            }
        }
        Shape surface = new Shape(polygons);

        surface.setColorMapper(new ColorMapper(new ColorMapRainbow(), surface.getBounds().getZmin(), surface.getBounds().getZmax(), new org.jzy3d.colors.Color(1,1,1,1f)));
        surface.setWireframeDisplayed(true);
        surface.setWireframeColor(org.jzy3d.colors.Color.BLACK);
        surface.setFaceDisplayed(true);

        chart.getScene().getGraph().add(surface);
        chart.addController(new AWTCameraKeyController());
        chart.addController(new AWTCameraMouseController());
        panel.setLayout(new BorderLayout());
        panel.add((Component) chart.getCanvas());
        currentFigure.getContentPane().add(panel, BorderLayout.CENTER);
    }
}