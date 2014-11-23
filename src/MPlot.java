package edu.kit.math.mplot;


import java.util.*;


public class MPlot {


    static boolean initialized = false;

    static List<Figure> figures;
    static int currentFigureIndex;


    public static void figure () {

        if (!initialized) Utilities.initSystem();

        Figure newFigure = new Figure();
        figures.add(newFigure);
        currentFigureIndex = figures.size()-1;
    }

    public static void plot (double[] x, double[] y, String linespec) {

        Figure currentFigure = figures.get(currentFigureIndex);

        new Plot(Data.dress(x, y), currentFigure, linespec);
    }


    public static void clf () {

        Figure currentFigure = figures.get(currentFigureIndex);

        currentFigure.setVisible(false);
        currentFigure.dispose();
        currentFigureIndex--;
    }

}
