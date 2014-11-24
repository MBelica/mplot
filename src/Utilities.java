package edu.kit.math.mplot;

import java.awt.*;
import java.util.*;
import java.awt.geom.GeneralPath;
import java.util.List;


class Utilities {


    private static final float SQRT2 = (float) Math.pow(2.0, 0.5);


    protected static void initSystem () {

        MPlot.initialized         = true;
        MPlot.figures             = new ArrayList<Figure>();
        MPlot.currentFigureIndex  = -1;
    }

    // the next 3 methods draw shapes found in matlab, asterisk and plus still has to be written but do we really want this?
    public static Shape drawDiagonalCross (final float l, final float t) {

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

    public static Shape drawAsterisk (final float l, final float t) {

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

    public static Shape drawPlus (final float l, final float t) {

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


class DataLists {
    static List<Figure> figures;
    static List<Plot>plots;
}

