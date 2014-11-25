package edu.kit.math.mplot;


import java.util.*;


public class MPlot {


    static boolean initialized = false;

    // this is my "book-keeping-list" I can here quickly check how many figures are open, what ids are already assigned, which is the highest/newest index etc.
    static ArrayList<Integer> figureIndexList = new ArrayList<Integer>();
    // this is the new ArrayList it does not only contain the figures but also linked with each figure its identifier (id) and p.r.n the associated plots
    static ArrayList<ArrayList> objectList = new ArrayList<ArrayList>();

    static int activeFigureIndex; // index of active figure
    static int currentFigureIndex; // highest index of all 


    /** Initialize variables if necessary; create figure and update index, new figure should be active **/
    public static void figure (int... indexVarArgs) {

        if (!initialized) Utilities.initSystem();


        // check if user wants to set sth active
        if ( (indexVarArgs.length != 0) && (figureIndexList.indexOf(indexVarArgs[0]) > -1) ) {

            activeFigureIndex = indexVarArgs[0];
            System.out.println("Figure " + indexVarArgs[0] + "set active. activeFigureIndex: "+ activeFigureIndex + ", currentFigureIndex: " + currentFigureIndex);
            // TODO: Fenster in den Vordergrund bringen
        }
        else{ // user wants to create new figure

            if (indexVarArgs.length == 0) { // no index given => currentFigureIndex is highest index existent therefore to be safe take as new index currentFigureIndex+1
                currentFigureIndex++;
                activeFigureIndex = currentFigureIndex;
            } // user gave us an index since our first if ruled the possibility out that he's going to set a window active, create new figure and set index as active
            else if (indexVarArgs[0] >= currentFigureIndex) { // if this index is higher then all others set also currentFigureIndex
                activeFigureIndex = indexVarArgs[0];
                currentFigureIndex = indexVarArgs[0];
            } else activeFigureIndex = indexVarArgs[0];
            // for book-keeping take a note that we created a new figure
            figureIndexList.add(activeFigureIndex);

            // This value changes well, I seem to have done it well
            System.out.println("CurrentFigureIndex:" + figureIndexList);

            // Nevertheless create a figure and add the figure and its index into our ArrayList
            Figure newFigure = new Figure();
            ArrayList tempArrayList = new ArrayList();
                tempArrayList.add(currentFigureIndex);
                tempArrayList.add(newFigure);
            objectList.add(tempArrayList);

            // For debug give us a nice list of all created objects
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

        // Check all created objects which index contains the activeFigureIndex and therefore also the Figure we want to plot in
        for (int i = 0; i < objectList.size(); i++) {

            if (((Integer) objectList.get(i).get(0)) == activeFigureIndex ) {

                // Standard plot...
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
        if (indexVarArgs.length == 0) { // if none is given clf active figure
            index = activeFigureIndex;
        } else index = indexVarArgs[0];

        // to be sure check if index is smaller then highest possible index and check if we have objects at all
        if ((index <= currentFigureIndex) && (objectList.size() > 0))  {
            // lets search if we find the object to delete if not simple error output
            for (int i = 0; i < objectList.size(); i++) {

                if (((Integer) objectList.get(i).get(0)) == index ) {
                    // Take the figure out of our ArrayList and delete it
                    Figure figureToCLF = (Figure) objectList.get(i).get(1);
                    figureToCLF.setVisible(false);
                    figureToCLF.dispose();
                    //ToDo Plots removen!
                    // delete the whole  "row" of our ArrayList containing the already deleted figure
                    objectList.remove(i);
                    // remove the index from our "book-keeping-list"
                    figureIndexList.remove(new Integer(index));

                    // We have to reset both indices, set active as the newester and current as the highest if changed
                    if(index == activeFigureIndex)  {
                        activeFigureIndex  = Utilities.getNewestIndex();
                        System.out.println("Just deleted activeFigureIndex. New: " + activeFigureIndex);
                    }
                    if(index == currentFigureIndex) {
                        currentFigureIndex = Utilities.getNewestIndex();
                        System.out.println("Just deleted currentFigureIndex. New: " + currentFigureIndex);
                    }

                    // More Debug
                    System.out.println("CurrentFigureIndex:" + figureIndexList);
                    System.out.println("Figure with index " + i + " deleted. activeFigureIndex: " + activeFigureIndex + ", currentFigureIndex:" + currentFigureIndex);
                    Utilities.printArrayList(objectList);
                }
            }
        } else {

            System.out.println("Error! Figure with index " + index + " does not exist.");
        }
    }


}