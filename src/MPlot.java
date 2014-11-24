package edu.kit.math.mplot;


import java.util.*;


public class MPlot {


    static boolean initialized = false;

    static List<Figure> figures;
    static int currentFigureIndex;

    /** Initialize variables if necessary; create figure and update index, new figure should be active **/
    public static void figure () {

        if (!initialized) Utilities.initSystem();

        Figure newFigure = new Figure();
        figures.add(newFigure);
        currentFigureIndex = figures.size()-1;

        System.out.println("Figure with index " + String.valueOf(currentFigureIndex) + " created");
        System.out.println(figures);
    }

    /** Create plot instance to plot function into currentFigure **/
    public static void plot (double[] x, double[] y, String linespec) {

        Figure currentFigure = figures.get(currentFigureIndex);

        new Plot(Data.dress(x, y), currentFigure, linespec);
    }

    /** Delete a figure, with no argument delete active figure else delete figure with given index **/
    public static void clf (int... indexVarArgs) {

        int index;
        if (indexVarArgs.length == 0) {
            index = currentFigureIndex;
        } else index = indexVarArgs[0];


        if ((index < figures.size()) && (currentFigureIndex >= 0))  {

            Figure currentFigure = figures.get(index);

            currentFigure.setVisible(false);
            currentFigure.dispose();
            figures.remove(index);
            currentFigureIndex = figures.size() - 1;

            System.out.println("Figure with index " + index + " deleted. Current figures: #" + (currentFigureIndex+1));
            System.out.println(figures);
        } else {

            System.out.println("Figure with index " + index + " does not exist.");
        }
    }

    /** As in matlab a general set function to manipulate with the figures **/
    public static void set (int index, String action) {

        //No switch through Strings
        if (action == "figureActive") {

            if ((index < figures.size()) && (currentFigureIndex >= 0))  {

                currentFigureIndex = index;

                System.out.println("Figure with index " + index + " set as active. Current figures: #" + (currentFigureIndex+1));
                System.out.println(figures);
            } else {

                System.out.println("Figure with index " + index + " does not exist.");
            }
        }

    }
}
