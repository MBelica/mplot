package edu.kit.math.mplot;


import java.awt.Color;
import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.ui.InteractivePanel;


class Plot {


    protected enum lineStyle  { solid, dashed, dashdot, dotted, plain }; protected lineStyle lStyle;
    protected enum markerStyle { point, plus, circle, asterisk, cross }; protected markerStyle mStyle;
    protected enum colorStyle  { black, red, green, blue, cyan, magenta, yellow, white }; protected colorStyle  cStyle;


    Plot (DataTable data, Figure currentFigure, String args) {

        XYPlot plot         = new XYPlot(data);
        LineRenderer lines  = new DefaultLineRenderer2D();
        Color color         = new Color(0.0f, 0.3f, 1.0f);

        linespecs(args);

        currentFigure.getContentPane().add(new InteractivePanel(plot));

        linefRenderer(data, plot, lines, color);
        pointfRenderer(data, plot, lines, color);

        currentFigure.revalidate();
        currentFigure.repaint();
    }

    private void linefRenderer (DataTable data, XYPlot plot, LineRenderer lines, Color color) {

        plot.setLineRenderer(data, lines);
        plot.getLineRenderer(data).setColor(color);
    }


    private void pointfRenderer (DataTable data, XYPlot plot, LineRenderer lines, Color color) {

        plot.getPointRenderer(data).setColor(color);
    }

    private void linespecs (String ls) {

        /** Line Style Specifiers **/
        if      (ls.indexOf("--") > -1) lStyle = lineStyle.dashed;    // Dashed line
        else if (ls.indexOf("-.") > -1) lStyle = lineStyle.dashdot;   // Dash-dot line
        else if (ls.indexOf(":")  > -1) lStyle = lineStyle.dotted;    // Dotted line
        else if (ls.indexOf(" ")  > -1) lStyle = lineStyle.plain;     // Plain
        else lStyle = lineStyle.solid;   // Solid line (default)
        /** Marker Specifiers **/
        if      (ls.indexOf("+") > -1) mStyle = markerStyle.plus;     // Plus sign
        else if (ls.indexOf("o") > -1) mStyle = markerStyle.circle;   // Circle
        else if (ls.indexOf("*") > -1) mStyle = markerStyle.asterisk; // Asterisk
        else if (ls.indexOf("x") > -1) mStyle = markerStyle.cross;    // Cross
        else  mStyle = markerStyle.point; // Point (default)
        /** Color Specifiers **/
        if      (ls.indexOf("r") > -1) cStyle = colorStyle.red;       // Red
        else if (ls.indexOf("g") > -1) cStyle = colorStyle.green;     // Green
        else if (ls.indexOf("b") > -1) cStyle = colorStyle.blue;      // Blue
        else if (ls.indexOf("c") > -1) cStyle = colorStyle.cyan;      // Cyan
        else if (ls.indexOf("m") > -1) cStyle = colorStyle.magenta;   // Magenta
        else if (ls.indexOf("y") > -1) cStyle = colorStyle.yellow;    // Yellow
        else if (ls.indexOf("w") > -1) cStyle = colorStyle.white;     // White
        else  cStyle = colorStyle.black;  // Black (default)
    }
}
