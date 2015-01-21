package edu.kit.math.mplot.modules;

//~--- non-JDK imports --------------------------------------------------------

import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.plots.points.PointRenderer;
import de.erichseifert.gral.ui.InteractivePanel;

//~--- JDK imports ------------------------------------------------------------

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

class PlotGral extends Plot {

    private DataTable[] dataTable;
    private LineRenderer lines;
    private XYPlot currentPlot;

    PlotGral (Figure currentFigure, Data[] data, String[] args) {
        dataTable = new DataTable[data.length];

        for (int i = 0; i < data.length; i++) {
            dataTable[i] = data[i].getGralDataTable();
        }

        currentPlot    = new XYPlot(dataTable);
        currentFigure.getContentPane().add(new InteractivePanel(currentPlot));
        currentPlot.getPlotArea().setBackground(currentFigure.bgcolor);

        for (int i = 0; i < data.length; i++) {
            super.parseLinespecs(args[i]);
            lines = new DefaultLineRenderer2D();
            setPointRenderer(dataTable[i], currentPlot);
            setLineRenderer(dataTable[i],  currentPlot, lines);
        }

    }

    void _Plot () {
    }

    private void setLineRenderer(de.erichseifert.gral.data.DataSource data, XYPlot plot, LineRenderer lines) {
        switch (lStyle) {
            case solid :
                plot.setLineRenderer(data, lines);
                plot.getLineRenderer(data).setColor(color);
                break;

            case dashed :
                plot.setLineRenderer(data, lines);
                lines.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[] { 10.0f,0.0f }, 0.0f));
                plot.getLineRenderer(data).setColor(color);
                break;

            case dashdot :
                plot.setLineRenderer(data, lines);
                lines.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[] { 10.0f, 10.0f, 2.0f, 10.0f }, 0.0f));
                plot.getLineRenderer(data).setColor(color);
                break;

            case dotted :
                plot.setLineRenderer(data, lines);
                lines.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[] { 2.0f, 10.0f }, 0.0f));
                plot.getLineRenderer(data).setColor(color);
                break;

            case plain :
                break;

            default :
                plot.setLineRenderer(data, lines);
                plot.getLineRenderer(data).setColor(color);
                break;
        }
    }

    private void setPointRenderer(de.erichseifert.gral.data.DataSource data, XYPlot plot) {
        PointRenderer pointRenderer = plot.getPointRenderer(data);

        switch (mStyle) {
            case point :
                pointRenderer.setShape(new Ellipse2D.Double(-1.0, -1.0, 2.0, 2.0));
                pointRenderer.setColor(color);
                break;

            case plus :
                pointRenderer.setShape(drawPlus(2, 1));
                pointRenderer.setColor(color);
                break;

            case circle :
                Area circle = new Area(new Ellipse2D.Double(-2.0, -2.0, 4.0, 4.0));
                circle.subtract(new Area(new Ellipse2D.Double(-1.0, -1.0, 2.0, 2.0)));
                pointRenderer.setShape(circle);
                break;

            case asterisk :
                pointRenderer.setShape(drawAsterisk(2, 1));
                pointRenderer.setColor(color);
                break;

            case cross :
                pointRenderer.setShape(drawDiagonalCross(2, 1));
                pointRenderer.setColor(color);
                break;

            case dot :
                pointRenderer.setShape(new Ellipse2D.Double(-2.0, -2.0, 4.0, 4.0));
                pointRenderer.setColor(color);
                break;

            default :
                pointRenderer.setShape(new Ellipse2D.Double(-1.0, -1.0, 2.0, 2.0));
                pointRenderer.setColor(color);
                break;
        }
    }
}