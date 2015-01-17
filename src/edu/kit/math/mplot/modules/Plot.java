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
import java.awt.geom.GeneralPath;

public class Plot {
    private static final float SQRT2 = (float) Math.pow(2.0, 0.5);

    public Color            color;
    public lineStyle        lStyle;
    public markerStyle      mStyle;

    protected enum lineStyle { solid, dashed, dashdot, dotted, plain }
    protected enum markerStyle { point, plus, circle, asterisk, cross, dot }

    public Plot(Figure currentFigure, Data[] data, String[] args) {
        DataTable[] dataTable = new DataTable[data.length];
        for (int i = 0; i < data.length; i++) dataTable[i] =  data[i].createGralDataTable();
        XYPlot currentPlot = new XYPlot(dataTable);

        for (int i = 0; i < data.length; i++) {
            parseLinespecs(args[i]);
            LineRenderer lines = new DefaultLineRenderer2D();

            currentFigure.getContentPane().add(new InteractivePanel(currentPlot));
            currentPlot.getPlotArea().setBackground(currentFigure.bgcolor);

            setLineRenderer(dataTable[i], currentPlot, lines);
            setPointRenderer(dataTable[i], currentPlot, lines);

        }
    }

    // the next 3 methods ceoss, asterisk and plus still have to be written but do we really want this?
    private static final Shape drawDiagonalCross(final float l, final float t) {  // Todo
        final GeneralPath p0 = new GeneralPath();

        p0.moveTo(-l - t, -l + t);
        p0.lineTo(-l + t, -l - t);
        p0.lineTo(0.0f, -t * SQRT2);
        p0.lineTo(l - t, -l - t);
        p0.lineTo(l + t, -l + t);
        p0.lineTo(t * SQRT2, 0.0f);
        p0.lineTo(l + t, l - t);
        p0.lineTo(l - t, l + t);
        p0.lineTo(0.0f, t * SQRT2);
        p0.lineTo(-l + t, l + t);
        p0.lineTo(-l - t, l - t);
        p0.lineTo(-t * SQRT2, 0.0f);
        p0.closePath();

        return p0;
    }

    private static final Shape drawAsterisk(final float l, final float t) { // Todo
        final GeneralPath p0 = new GeneralPath();

        p0.moveTo(-l - t, -l + t);
        p0.lineTo(-l + t, -l - t);
        p0.lineTo(0.0f, -t * SQRT2);
        p0.lineTo(l - t, -l - t);
        p0.lineTo(l + t, -l + t);
        p0.lineTo(t * SQRT2, 0.0f);
        p0.lineTo(l + t, l - t);
        p0.lineTo(l - t, l + t);
        p0.lineTo(0.0f, t * SQRT2);
        p0.lineTo(-l + t, l + t);
        p0.lineTo(-l - t, l - t);
        p0.lineTo(-t * SQRT2, 0.0f);
        p0.closePath();

        return p0;
    }

    private static final Shape drawPlus(final float l, final float t) {  // Todo
        final GeneralPath p0 = new GeneralPath();

        p0.moveTo(-l - t, -l + t);
        p0.lineTo(-l + t, -l - t);
        p0.lineTo(0.0f, -t * SQRT2);
        p0.lineTo(l - t, -l - t);
        p0.lineTo(l + t, -l + t);
        p0.lineTo(t * SQRT2, 0.0f);
        p0.lineTo(l + t, l - t);
        p0.lineTo(l - t, l + t);
        p0.lineTo(0.0f, t * SQRT2);
        p0.lineTo(-l + t, l + t);
        p0.lineTo(-l - t, l - t);
        p0.lineTo(-t * SQRT2, 0.0f);
        p0.closePath();

        return p0;
    }

    private void setLineRenderer(DataTable data, XYPlot plot, LineRenderer lines) {
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

    private void setPointRenderer(DataTable data, XYPlot plot, LineRenderer lines) {
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

    private void parseLinespecs(String ls) {

        /** Set different linespecs for multiple Plots */
        if (ls.indexOf("MultiplePlots#") > -1) {
            int mpIndex = Integer.parseInt(ls.substring(ls.indexOf("#")+1).trim());
            int colorRendererIndex = mpIndex % 8; // we have 8 colours ...
            int lineRendererIndex  = ( (int)(mpIndex/8) ) % 4; // ... and 4 different linetypes

            ls = "";
            if (colorRendererIndex == 1) {
                ls += "r";    // Red
            } else if (colorRendererIndex == 2) {
                ls += "g";    // Green
            } else if (colorRendererIndex == 3) {
                ls += "b";    // Blue
            } else if (colorRendererIndex == 4) {
                ls += "c";    // Cyan
            } else if (colorRendererIndex == 5) {
                ls += "m";    // Magenta
            } else if (colorRendererIndex == 6) {
                ls += "y";    // Yellow
            } else if (colorRendererIndex == 7) {
                ls += "w";    // White
            } else {
                ls += "k";    // Black (default)
            }
            if (lineRendererIndex == 1) {
                ls += "--";   // Dashed line
            } else if (lineRendererIndex == 2) {
                ls += "-.";   // Dash-dot line
            } else if (lineRendererIndex == 3) {
                ls += ":";    // Dotted line
            } else {
                ls += "-";    // Solid line (default)
            }
        }


        /** Line Style Specifiers */
        if (ls.indexOf("--") > -1) {
            lStyle = lineStyle.dashed;              // Dashed line
        } else if (ls.indexOf("-.") > -1) {
            lStyle = lineStyle.dashdot;             // Dash-dot line
        } else if (ls.indexOf(":") > -1) {
            lStyle = lineStyle.dotted;              // Dotted line
        } else if (ls.indexOf(" ") > -1) {
            lStyle = lineStyle.plain;               // Plain
        } else {
            lStyle = lineStyle.solid;               // Solid line (default)
        }

        /** Marker Specifiers */
        if (ls.indexOf("+") > -1) {
            mStyle = markerStyle.plus;              // Plus sign
        } else if (ls.indexOf("o") > -1) {
            mStyle = markerStyle.circle;            // Circle
        } else if (ls.indexOf("*") > -1) {
            mStyle = markerStyle.asterisk;          // Asterisk
        } else if (ls.indexOf("x") > -1) {
            mStyle = markerStyle.cross;             // Cross
        } else if (ls.indexOf("q") > -1) {
            mStyle = markerStyle.dot;               // dot
        } else {
            mStyle = markerStyle.point;             // Point (default)
        }

        /** Color Specifiers */
        if (ls.indexOf("r") > -1) {
            color = new Color(1.0f, 0.0f, 0.0f);    // Red
        } else if (ls.indexOf("g") > -1) {
            color = new Color(0.0f, 1.0f, 0.0f);    // Green
        } else if (ls.indexOf("b") > -1) {
            color = new Color(0.0f, 0.0f, 1.0f);    // Blue
        } else if (ls.indexOf("c") > -1) {
            color = new Color(0.0f, 1.0f, 1.0f);    // Cyan
        } else if (ls.indexOf("m") > -1) {
            color = new Color(1.0f, 0.0f, 1.0f);    // Magenta
        } else if (ls.indexOf("y") > -1) {
            color = new Color(1.0f, 1.0f, 0.0f);    // Yellow
        } else if (ls.indexOf("w") > -1) {
            color = new Color(1.0f, 1.0f, 1.0f);    // White
        } else {
            color = new Color(0.0f, 0.0f, 0.0f);    // Black (default)
        }
    }
}