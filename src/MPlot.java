package edu.kit.math.mplot;


import java.util.*;


public class MPlot {


    static boolean initialized = false;

    static ArrayList<Integer> figureIndexList = new ArrayList<Integer>(); // this is the new ArrayList it does not only contain the figures but also linked with each figure its identifier (id) and p.r.n the associated plots
    static ArrayList<ArrayList> objectList    = new ArrayList<ArrayList>(); // this is my "book-keeping-list" I can here quickly check how many figures are open, what ids are already assigned, which is the highest/newest index etc.

    static int activeFigureIndex;  // index of active figure
    static int currentFigureIndex; // highest index of all


    /** Initialize variables if necessary; create figure and update index, new figure should be active **/
    public static int figure (int... indexVarArgs) {

        if (!initialized) Utilities.initSystem();


        if ( Utilities.isVarArgsSet(indexVarArgs) && Utilities.isIndexInUse(indexVarArgs[0])) { // user wants to set a figure as active since index-integer is given but already exists

            int tempIndex     = (Integer) indexVarArgs[0];
            Figure tempFigure = (Figure)  objectList.get( Utilities.idToIndex(tempIndex) ).get(1);

            activeFigureIndex = tempIndex; // set index active
            tempFigure.toFront(); // place in front
            tempFigure.repaint();

            System.out.println("Figure " + tempIndex + "set active. activeFigureIndex: "+ activeFigureIndex + ", currentFigureIndex: " + currentFigureIndex);
        }
        else{ // user wants to create new figure

            if (indexVarArgs.length == 0) activeFigureIndex = ++currentFigureIndex; // no index given => currentFigureIndex is highest index existent therefore to be safe take as new index currentFigureIndex+1
             else if (indexVarArgs[0] >= currentFigureIndex) currentFigureIndex = activeFigureIndex = indexVarArgs[0]; // in case that the index is higher then everything else set currentFigureIndex = index + following line
             else activeFigureIndex = indexVarArgs[0]; // user gave us an index since our first if-statement ruled the possibility out that he's just going to set a window active, create new figure and set index as active

            // for book-keeping take a note that we created a new figure and then create & add elements into our objectList
            figureIndexList.add(activeFigureIndex);
            Figure newFigure = new Figure();
            ArrayList tempArrayList = new ArrayList();
                tempArrayList.add(currentFigureIndex);
                tempArrayList.add(newFigure);
            objectList.add(tempArrayList);

            System.out.println("New figure with index " + String.valueOf(activeFigureIndex) + " created. activeFigureIndex: " + activeFigureIndex + ", currentFigureIndex:" + currentFigureIndex);
            Utilities.printArrayList(objectList);
        }

        return activeFigureIndex;
    }

    /** Create plot instance to plot function into currentFigure **/
    public static void plot (double[] x, double[] y, String... linespecVarArgs) {

        String linespec;
        if (!Utilities.isVarArgsSet(linespecVarArgs)) {
            linespec = "";
        } else linespec = linespecVarArgs[0];

        // Check all created objects which index contains the activeFigureIndex and therefore also the Figure we want to plot in
        int indexHandle = Utilities.idToIndex(activeFigureIndex);
        if ( indexHandle > -1 ) {

                // Standard plot...
                Figure currentFigure = (Figure) objectList.get(indexHandle).get(1);
                objectList.get(indexHandle).add(new Plot(Data.dress(x, y), currentFigure, linespec));

                System.out.println("New plot created and associated with figure " + indexHandle +". activeFigureIndex: " + activeFigureIndex + ", currentFigureIndex:" + currentFigureIndex);
       } else if (indexHandle == (objectList.size()-1)) System.out.println("Error! No figure for activeFigureIndex found.");

        Utilities.printArrayList(objectList);
    }

    /** Delete a figure, with no argument delete active figure else delete figure with given index **/
    public static void clf (int... idVarArgs) {

        int id;
        if (!Utilities.isVarArgsSet(idVarArgs)) { // if none is given clf active figure
            id = activeFigureIndex;
        } else id = idVarArgs[0];

        // to be sure check if index is smaller then highest possible index and check if we have objects at all
        if ((id <= currentFigureIndex) && (objectList.size() > 0))  {
            // lets search if we find the object to delete if not simple error output
            int indexHandle = Utilities.idToIndex(id);
            if ( indexHandle > -1 ) {

                // Delete figure in this id
                Figure figureToCLF = (Figure) objectList.get(indexHandle).get(1);
                figureToCLF.setVisible(false);
                figureToCLF.dispose();
                // Plots are going to be deleted by javas garbage-collector
                // Remove entry in ArrayList
                objectList.remove(indexHandle);
                // As last we remove the entry in our book-keeping-list
                figureIndexList.remove(new Integer(id));

                // We have to reset both indices, set active as the newester and current as the highest if changed
                if(id == activeFigureIndex)  activeFigureIndex  = Utilities.getNewestIndex();
                if(id == currentFigureIndex) currentFigureIndex = Utilities.getNewestIndex();

                System.out.println("Figure with index " + id + " deleted. activeFigureIndex: " + activeFigureIndex + ", currentFigureIndex:" + currentFigureIndex);
                Utilities.printArrayList(objectList);
            }
        } else {

            System.out.println("Error! Figure with index " + id + " does not exist.");
        }
    }
}