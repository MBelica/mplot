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

        System.out.println("Figure with index:" + String.valueOf(currentFigureIndex) + "created");
        System.out.println(figures);
    }

    public static void plot (double[] x, double[] y, String linespec) {

        Figure currentFigure = figures.get(currentFigureIndex);

        new Plot(Data.dress(x, y), currentFigure, linespec);
    }

    //** Delete the active figure **/
    public static void clf () {
        if (currentFigureIndex >= 0) {
            Figure currentFigure = figures.get(currentFigureIndex);

            currentFigure.setVisible(false);
            currentFigure.dispose();
            figures.remove(currentFigureIndex);
            currentFigureIndex = figures.size() - 1;

            System.out.println("Active figure deleted.");
        } else {

            System.out.println("No figures created yet").
        }
    }

    //** Delete a certain figure **/
    public static void clf (int index) {

        if (index <= currentFigureIndex) {

            Figure currentFigure = figures.get(index);

            currentFigure.setVisible(false);
            currentFigure.dispose();
            figures.remove(index);
            currentFigureIndex = figures.size() - 1;

            System.out.println("Figure with index " + index + "deleted.");
        } else {

            System.out.println("Figure with index " + index + "does not exist.");
        }
    }
}
