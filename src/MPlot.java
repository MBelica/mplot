package edu.kit.math.mplot;


import java.util.*;


public class MPlot {


    static boolean initialized = false;

    // ToDo: create list of lists to handle connected figures and plots; merging and deleting those should be then be easy
    static List<Figure> figures;
    static List<DataLists> dataList = new ArrayList<DataLists>();

    static int currentFigureIndex;

    /** Initialize variables if necessary; create figure and update index, new figure should be active **/
    public static void figure () {

        if (!initialized) Utilities.initSystem();

        Figure newFigure = new Figure();
        figures.add(newFigure);
        currentFigureIndex = figures.size()-1;

        System.out.println("Figure with index " + String.valueOf(currentFigureIndex) + " created");
        Utilities.printFigureList(figures);
    }

    /** Create plot instance to plot function into currentFigure **/
    public static void plot (double[] x, double[] y, String... linespecVarArgs) {

        String linespec;
        if (linespecVarArgs.length == 0) {
            linespec = "";
        } else linespec = linespecVarArgs[0];


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

            if (index == currentFigureIndex) currentFigureIndex = figures.size() - 1;
             else if (index < currentFigureIndex) currentFigureIndex--;

            System.out.println("Figure with index " + index + " deleted.");
            Utilities.printFigureList(figures);
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

                System.out.println("Figure with index " + index + " set as active.");
                Utilities.printFigureList(figures);
            } else {

                System.out.println("Figure with index " + index + " does not exist.");
            }
        }

    }
}