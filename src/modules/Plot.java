package edu.kit.math.mplot;


import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.plots.points.PointRenderer;
import de.erichseifert.gral.ui.InteractivePanel;


class Plot {

    protected Color color;
    protected enum lineStyle  { solid, dashed, dashdot, dotted, plain }; protected lineStyle lStyle;
    protected enum markerStyle { point, plus, circle, asterisk, cross }; protected markerStyle mStyle;

    Plot (DataTable data, Figure currentFigure, String args) {

        parseLinespecs(args);
        XYPlot plot         = new XYPlot(data);
        LineRenderer lines  = new DefaultLineRenderer2D();

        currentFigure.getContentPane().add(new InteractivePanel(plot));

        setLineRenderer(data, plot, lines);
        setPointRenderer(data, plot, lines);

        currentFigure.revalidate();
        currentFigure.repaint();
    }

     // Die folgenden beiden Methoden machen noch nicht was sie sollen, sie Rendern irgendwie... exakte formen und Farben müssen noch gesetzt werden
    private void setLineRenderer (DataTable data, XYPlot plot, LineRenderer lines) {

        switch (lStyle) {

            case solid:
                plot.setLineRenderer(data, lines);
                plot.getLineRenderer(data).setColor(color);
                break;
            case dashed:
                plot.setLineRenderer(data, lines);
                plot.getLineRenderer(data).setColor(color);
                break;
            case dashdot:
                plot.setLineRenderer(data, lines);
                plot.getLineRenderer(data).setColor(color);
                break;
            case dotted:
                plot.setLineRenderer(data, lines);
                plot.getLineRenderer(data).setColor(color);
                break;
            case plain:
                break;
            default:
                plot.setLineRenderer(data, lines);
                plot.getLineRenderer(data).setColor(color);
                break;
        }
    }


    private void setPointRenderer (DataTable data, XYPlot plot, LineRenderer lines) {

        PointRenderer pointRenderer = plot.getPointRenderer(data);
        switch (mStyle) {

            case  point:
                pointRenderer.setShape(new Ellipse2D.Double(-1.0, -1.0, 2.0, 2.0));
                pointRenderer.setColor(color);
                break;
            case plus:
                pointRenderer.setShape(new Ellipse2D.Double(-4.0, -4.0, 8.0, 8.0));
                pointRenderer.setColor(color);
                break;
            case circle:
                pointRenderer.setShape(new Ellipse2D.Double(-4.0, -4.0, 8.0, 8.0));
                pointRenderer.setColor(color);
                break;
            case asterisk:
                pointRenderer.setShape(new Rectangle2D.Double(-2.5, -2.5, 5, 5));
                pointRenderer.setColor(color);
                break;
            case cross:
                pointRenderer.setShape(new Rectangle2D.Double(-2.5, -2.5, 5, 5));
                pointRenderer.setColor(color);
                break;
            default:
                pointRenderer.setShape(new Ellipse2D.Double(-1.0, -1.0, 2.0, 2.0));
                pointRenderer.setColor(color);
                break;
        }
    }

    private void parseLinespecs (String ls) {

        /** Line Style Specifiers **/
        if      (ls.indexOf("--") > -1) lStyle = lineStyle.dashed;           // Dashed line
        else if (ls.indexOf("-.") > -1) lStyle = lineStyle.dashdot;          // Dash-dot line
        else if (ls.indexOf(":")  > -1) lStyle = lineStyle.dotted;           // Dotted line
        else if (ls.indexOf(" ")  > -1) lStyle = lineStyle.plain;            // Plain
        else lStyle = lineStyle.solid;               // Solid line (default)
        /** Marker Specifiers **/
        if      (ls.indexOf("+") > -1) mStyle = markerStyle.plus;            // Plus sign
        else if (ls.indexOf("o") > -1) mStyle = markerStyle.circle;          // Circle
        else if (ls.indexOf("*") > -1) mStyle = markerStyle.asterisk;        // Asterisk
        else if (ls.indexOf("x") > -1) mStyle = markerStyle.cross;           // Cross
        else  mStyle = markerStyle.point;            // Point (default)
        /** Color Specifiers **/
        if      (ls.indexOf("r") > -1) color = new Color (1.0f, 0.0f, 0.0f); // Red
        else if (ls.indexOf("g") > -1) color = new Color (0.0f, 1.0f, 0.0f); // Green
        else if (ls.indexOf("b") > -1) color = new Color (0.0f, 0.0f, 1.0f); // Blue
        else if (ls.indexOf("c") > -1) color = new Color (0.0f, 1.0f, 1.0f); // Cyan
        else if (ls.indexOf("m") > -1) color = new Color (1.0f, 0.0f, 1.0f); // Magenta
        else if (ls.indexOf("y") > -1) color = new Color (1.0f, 1.0f, 0.0f); // Yellow
        else if (ls.indexOf("w") > -1) color = new Color (1.0f, 1.0f, 1.0f); // White
        else  color = new Color (0.0f, 0.0f, 0.0f);  // Black (default)
    }
}
