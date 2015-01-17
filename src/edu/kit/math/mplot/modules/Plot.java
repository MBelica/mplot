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

    public Plot() {    }

    public Plot(Figure currentFigure, int dimension) { // TODO this is just a temp constructor for testing purpose

        new PlotJzy3D(currentFigure);
        currentFigure.getContentPane().revalidate();
        currentFigure.getContentPane().repaint();
    }

    public Plot(Figure currentFigure, int dimension, Data[] data, String[] args) {

        if (dimension == 2) {

            new PlotGral(currentFigure, data, args);

        } else if (dimension == 3) {

            new PlotJzy3D(currentFigure);

        }

        currentFigure.getContentPane().revalidate();
        currentFigure.getContentPane().repaint();
    }

    void parseLinespecs(String ls) {

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

    // the next 3 methods ceoss, asterisk and plus still have to be written but do we really want this?
    static final Shape drawDiagonalCross(final float l, final float t) {  // Todo
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

    static final Shape drawAsterisk(final float l, final float t) { // Todo
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

    static final Shape drawPlus(final float l, final float t) {  // Todo
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
}