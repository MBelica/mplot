package edu.kit.math.mplot;


import java.util.*;


public class MPlot {


    static boolean initialized = false;

    static ArrayList<Integer> figureIndexList = new ArrayList<Integer>();
    static ArrayList<ArrayList> objectList = new ArrayList<ArrayList>();

    static int activeFigureIndex;
    static int currentFigureIndex;


    /** Initialize variables if necessary; create figure and update index, new figure should be active **/
    public static void figure (int... indexVarArgs) {

        if ( (indexVarArgs.length != 0) && (figureIndexList.indexOf(indexVarArgs[0]) > -1) ) {

            activeFigureIndex = indexVarArgs[0];
            System.out.println("Figure " + indexVarArgs[0] + "set active. activeFigureIndex: "+ activeFigureIndex + ", currentFigureIndex: " + currentFigureIndex);
            // Fenster in den Vordergrund bringen!
        }
        else{

            if (!initialized) Utilities.initSystem();


            if (indexVarArgs.length == 0) {
                currentFigureIndex++;
                activeFigureIndex = currentFigureIndex;
            }
            else if (indexVarArgs[0] >= currentFigureIndex) {
                activeFigureIndex = indexVarArgs[0];
                currentFigureIndex = indexVarArgs[0];
            } else activeFigureIndex = indexVarArgs[0];
            figureIndexList.add(activeFigureIndex);

            System.out.println("CurrentFigureIndex:" + figureIndexList);

            Figure newFigure = new Figure();
            ArrayList tempArrayList = new ArrayList();
            tempArrayList.add(currentFigureIndex);
            tempArrayList.add(newFigure);
            objectList.add(tempArrayList);


            System.out.println("New figure with index " + String.valueOf(activeFigureIndex) + " created. activeFigureIndex: " + activeFigureIndex + ", currentFigureIndex:" + currentFigureIndex);
            Utilities.printArrayList(objectList);
        }
    }

    /** Create plot instance to plot function into currentFigure **/
    public static void plot (double[] x, double[] y, String... linespecVarArgs) {

        String linespec;
        if (linespecVarArgs.length == 0) {
            linespec = "";
        } else linespec = linespecVarArgs[0];


        for (int i = 0; i < objectList.size(); i++) {

            if (((Integer) objectList.get(i).get(0)) == activeFigureIndex ) {

                Figure currentFigure = (Figure) objectList.get(i).get(1);
                objectList.get(i).add(new Plot(Data.dress(x, y), currentFigure, linespec));

                System.out.println("New plot created and associated with figure " + i +". activeFigureIndex: " + activeFigureIndex + ", currentFigureIndex:" + currentFigureIndex);
            } else if (i == (objectList.size()-1)) System.out.println("Error! No figure for activeFigureIndex found.");
        }

        Utilities.printArrayList(objectList);
    }

    /** Delete a figure, with no argument delete active figure else delete figure with given index **/
    public static void clf (int... indexVarArgs) {

        int index;
        if (indexVarArgs.length == 0) {
            index = activeFigureIndex;
        } else index = indexVarArgs[0];


        if ((index <= currentFigureIndex) && (objectList.size() > 0))  {

            for (int i = 0; i < objectList.size(); i++) {

                if (((Integer) objectList.get(i).get(0)) == index ) {

                    Figure figureToCLF = (Figure) objectList.get(i).get(1);
                    figureToCLF.setVisible(false);
                    figureToCLF.dispose();
                    //ToDo Plots removen!
                    objectList.remove(i);
                    figureIndexList.remove(new Integer(index));

                    if(index == activeFigureIndex)  {
                        activeFigureIndex  = Utilities.getNewestIndex();
                        System.out.println("Just deleted activeFigureIndex. New: " + activeFigureIndex);
                    }
                    if(index == currentFigureIndex) {
                        currentFigureIndex = Utilities.getNewestIndex();
                        System.out.println("Just deleted currentFigureIndex. New: " + currentFigureIndex);
                    }

                    System.out.println("CurrentFigureIndex:" + figureIndexList);

                    System.out.println("Figure with index " + i + " deleted. activeFigureIndex: " + activeFigureIndex + ", currentFigureIndex:" + currentFigureIndex);
                    Utilities.printArrayList(objectList);
                }
            }
        } else {

            System.out.println("Figure with index " + index + " does not exist.");
        }
    }


}