package edu.kit.math.mplot.modules;

//~--- JDK imports ------------------------------------------------------------

import java.awt.*;
import java.awt.geom.GeneralPath;

public class Plot implements PlotInterface {
    private static final float SQRT2 = (float) Math.pow(2.0, 0.5);
    int                        currentDimension;
    Object                     currentPlot;
    public float[]             color;
    public lineStyle           lStyle;
    public markerStyle         mStyle;

    protected enum lineStyle {
        solid, dashed, dashdot, dotted, surface, plain
    }

    protected enum markerStyle {
        point, plus, circle, asterisk, cross, dot
    }

    public Plot() {}

    public Plot(Figure currentFigure, int dimension, Data[] data, String... args) {
        this.currentDimension = dimension;

        if (currentDimension == 2) {
            switch (currentFigure.renderer2d) {
            case gral :
                currentPlot = new PlotGral(currentFigure, data, args);

                break;

            default :
                currentPlot = new PlotGral(currentFigure, data, args);
            }
        } else if (currentDimension == 3) {
            switch (currentFigure.renderer3d) {
            case jzy3d :
                currentPlot = new PlotJzy3D(currentFigure, data, args);

                break;

            default :
                currentPlot = new PlotJzy3D(currentFigure, data, args);
            }
        }

        currentFigure.getContentPane().revalidate();
        currentFigure.getContentPane().repaint();
    }

    public void removePlot() {
        ((Plot) currentPlot)._Plot();
    }

    void _Plot() {}

    void parseLinespecs(String ls) {

        /** Set different linespecs for multiple Plots */
        if (ls.contains("MultiplePlots#")) {
            int mpIndex            = Integer.parseInt(ls.substring(ls.indexOf("#") + 1).trim());
            int colorRendererIndex = mpIndex % 8;            // we have 8 colours ...
            int lineRendererIndex  = ((mpIndex / 8)) % 4;    // ... and 4 different linetypes

            ls = "";

            if (colorRendererIndex == 1) {
                ls += "r";                                   // Red
            } else if (colorRendererIndex == 2) {
                ls += "g";                                   // Green
            }  else if (colorRendererIndex == 3) {
                ls += "k";                                   // Black
            } else if (colorRendererIndex == 4) {
                ls += "c";                                   // Cyan
            } else if (colorRendererIndex == 5) {
                ls += "m";                                   // Magenta
            } else if (colorRendererIndex == 6) {
                ls += "y";                                   // Yellow
            } else if (colorRendererIndex == 7) {
                ls += "w";                                   // White
            }  else {
                ls += "b";                                   // Blue (default)
            }

            if (lineRendererIndex == 1) {
                ls += "--";                                  // Dashed line
            } else if (lineRendererIndex == 2) {
                ls += "-.";                                  // Dash-dot line
            } else if (lineRendererIndex == 3) {
                ls += ":";                                   // Dotted line
            } else {
                ls += "-";                                   // Solid line (default)
            }
        }

        /** Line Style Specifiers */
        if (ls.contains("--")) {
            lStyle = lineStyle.dashed;     // Dashed line
        } else if (ls.contains("-.")) {
            lStyle = lineStyle.dashdot;    // Dash-dot line
        } else if (ls.contains(":")) {
            lStyle = lineStyle.dotted;     // Dotted line
        } else if (ls.contains(" ")) {
            lStyle = lineStyle.plain;      // Plain
        } else {
            lStyle = lineStyle.solid;      // Solid line (default)
        }

        /** Marker Specifiers */
        if (ls.contains("+")) {
            mStyle = markerStyle.plus;        // Plus sign
        } else if (ls.contains("o")) {
            mStyle = markerStyle.circle;      // Circle
        } else if (ls.contains("*")) {
            mStyle = markerStyle.asterisk;    // Asterisk
        } else if (ls.contains("x")) {
            mStyle = markerStyle.cross;       // Cross
        } else if (ls.contains("q")) {
            mStyle = markerStyle.dot;         // dot
        } else {
            mStyle = markerStyle.point;       // Point (default)
        }

        /** Color Specifiers */
        if (ls.contains("r")) {
            color = new float[] { 1.0f, 0.0f, 0.0f };    // Red
        } else if (ls.contains("g")) {
            color = new float[] { 0.0f, 1.0f, 0.0f };    // Green
        } else if (ls.contains("b")) {
            color = new float[] { 0.0f, 0.0f, 1.0f };    // Blue
        }  else if (ls.contains("k")) {
            color = new float[] { 0.0f, 0.0f, 0.0f };    // Black
        } else if (ls.contains("c")) {
            color = new float[] { 0.0f, 1.0f, 1.0f };    // Cyan
        } else if (ls.contains("m")) {
            color = new float[] { 1.0f, 0.0f, 1.0f };    // Magenta
        } else if (ls.contains("y")) {
            color = new float[] { 1.0f, 1.0f, 0.0f };    // Yellow
        } else if (ls.contains("w")) {
            color = new float[] { 1.0f, 1.0f, 1.0f };    // White
        } else {
            color = new float[] { 0.0f, 0.0f, 1.0f };    // Blue (default)
        }
    }

    static Shape drawDiagonalCross(final float l, final float t) {
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

    static Shape drawAsterisk(final float l, final float t) {
        final GeneralPath p0 = new GeneralPath();

        p0.moveTo((-l + t) * SQRT2, -l - t);
        p0.lineTo(0.0f, l + t);
        p0.lineTo((l - t) * SQRT2, -l - t);
        p0.lineTo(-l - t, (l - t) * SQRT2);
        p0.lineTo(0.0f, 0);
        p0.lineTo(l + t, (l - t) * SQRT2);
        p0.closePath();

        return p0;
    }

    static Shape drawPlus(final float l, final float t) {
        final GeneralPath p0 = new GeneralPath();

        p0.moveTo(-l - t, -l + t);
        p0.lineTo(-l - t, l - t);
        p0.lineTo(-l + t, l - t);
        p0.lineTo(-l + t, l + t);
        p0.lineTo(l - t, l + t);
        p0.lineTo(l - t, l - t);
        p0.lineTo(l + t, l - t);
        p0.lineTo(l + t, -l + t);
        p0.lineTo(l - t, -l + t);
        p0.lineTo(l - t, -l - t);
        p0.lineTo(-l + t, -l - t);
        p0.lineTo(-l + t, -l + t);
        p0.closePath();

        return p0;
    }
}


interface PlotInterface {
    public void removePlot();
}