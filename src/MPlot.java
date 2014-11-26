package edu.kit.math.mplot;


import java.util.*;


public class MPlot {


    private Utilities utilitiesHandle;

    protected boolean initialized = false;

    protected ArrayList<Integer> figureIndexList = new ArrayList<Integer>();   // this is my "book-keeping-list" I can quickly check how many figures are open, what ids are already assigned, which is the highest/newest index etc.
    protected ArrayList<ArrayList> groot         = new ArrayList<ArrayList>(); // this is the new ArrayList it does not only contain the figures but also linked with each figure its identifier (id) and p.r.n the associated plots

    protected int activeFigureIndex;  // index of active figure
    protected int currentFigureIndex; // highest index of all


    public MPlot () {

        if (!initialized) {

            utilitiesHandle = new Utilities(this);
            utilitiesHandle.initSystem();
        }
    }

    public int figure (int... indexVarArgs) {

        if ( utilitiesHandle.isVarArgsSet(indexVarArgs) && utilitiesHandle.isIndexInUse(indexVarArgs[0])) { // user wants to set a figure as active since index-parameter is given but is already assigned

            int tempIndex     = (Integer) indexVarArgs[0];
            Figure tempFigure = (Figure)  groot.get( utilitiesHandle.idToIndex(tempIndex) ).get(1);

            activeFigureIndex = tempIndex; // set index active
            tempFigure.toFront(); // place in front
            tempFigure.repaint();

            System.out.println("Figure " + tempIndex + " set active. activeFigureIndex: "+ activeFigureIndex + ", currentFigureIndex: " + currentFigureIndex);
        }
        else{ // user wants to create new figure
            // check if index-parameter has been passed and act appropriately
            if (indexVarArgs.length == 0) activeFigureIndex = ++currentFigureIndex; // no index given => currentFigureIndex is highest index existent therefore neww index will be currentFigureIndex + 1
             else if (indexVarArgs[0] >= currentFigureIndex) currentFigureIndex = activeFigureIndex = indexVarArgs[0]; // in case that the index is higher then everything else set active&currentFigureIndex = index
             else activeFigureIndex = indexVarArgs[0]; // user gave us an and really wants to create a new figure, set index as active

            // for book-keeping take a note that we created a new figure and then create & add elements into our groot
            figureIndexList.add(activeFigureIndex);
            Figure newFigure = new Figure();
            ArrayList tempArrayList = new ArrayList();
                tempArrayList.add(currentFigureIndex);
                tempArrayList.add(newFigure);
            groot.add(tempArrayList);

            System.out.println("New figure with index " + String.valueOf(activeFigureIndex) + " created. activeFigureIndex: " + activeFigureIndex + ", currentFigureIndex: " + currentFigureIndex);
            utilitiesHandle.printArrayList(groot);
        }

        return activeFigureIndex;
    }

    public void plot (double[] x, double[] y, String... linespecVarArgs) {

        String linespec;
        if (!utilitiesHandle.isVarArgsSet(linespecVarArgs)) {
            linespec = "";
        } else linespec = linespecVarArgs[0];

        // Check under all created objects whose index contains the activeFigureIndex and therefore also the figure we want to plot in
        int indexHandle = utilitiesHandle.idToIndex(activeFigureIndex);
        if ( indexHandle > -1 ) { // if we've found an index (entry in groot) we are going to plot

            Figure currentFigure = (Figure) groot.get(indexHandle).get(1);
            groot.get(indexHandle).add(new Plot(Data.dress(x, y), currentFigure, linespec)); // add plot into groot under the figure

            System.out.println("New plot created and associated with figure " + indexHandle +". activeFigureIndex: " + activeFigureIndex + ", currentFigureIndex: " + currentFigureIndex);
        } else if (indexHandle == (groot.size()-1)) System.out.println("Error! No figure for activeFigureIndex found.");

        utilitiesHandle.printArrayList(groot);
    }

    public void clf (int... idVarArgs) {

        int id;
        if (!utilitiesHandle.isVarArgsSet(idVarArgs)) { // if none is given clf active figure
            id = activeFigureIndex;
        } else id = idVarArgs[0];

        // to be sure check if index is smaller then highest possible index and check if we have objects at all
        if ((id <= currentFigureIndex) && (groot.size() > 0))  {
            // lets search if we find the object to clear
            int indexHandle = utilitiesHandle.idToIndex(id);
            if ( indexHandle > -1 ) { // > -1 means we found the element

                // Delete figure in this id
                Figure figureToCLF = (Figure) groot.get(indexHandle).get(1);
                figureToCLF.getContentPane().removeAll();
                figureToCLF.getContentPane().revalidate();
                figureToCLF.getContentPane().repaint();

                System.out.println("Figure with index " + id + " cleared. activeFigureIndex: " + activeFigureIndex + ", currentFigureIndex:" + currentFigureIndex);
                utilitiesHandle.printArrayList(groot);
            } else System.out.println("Error! Nothing cleared - figure with index " + id + " does not exist.");
        } else System.out.println("Error! Nothing cleared - figure with index " + id + " does not exist.");
    }

    public int close (int... idVarArgs) { // since first confused clf and close here is the real close function

        int id;
        if (!utilitiesHandle.isVarArgsSet(idVarArgs)) { // if no parameter is given close active figure
            id = activeFigureIndex;
        } else id = idVarArgs[0];

        // to be sure check if index is smaller then highest possible index and check if we have objects at all
        if ((id <= currentFigureIndex) && (groot.size() > 0))  {
            // lets search if we find the object to delete if not simple error output
            int indexHandle = utilitiesHandle.idToIndex(id);
            if ( indexHandle > -1 ) {

                // Delete figure in this id
                Figure figureToClose = (Figure) groot.get(indexHandle).get(1);
                figureToClose.setVisible(false);
                figureToClose.dispose();
                // Plots are going to be deleted by javas garbage-collector
                // Remove entry in ArrayList
                groot.remove(indexHandle);
                // As last we remove the entry in our book-keeping-list
                figureIndexList.remove(new Integer(id));

                // We have to reset both indices, set active as the newester and current as the highest if changed
                if(id == activeFigureIndex)  activeFigureIndex  = utilitiesHandle.getNewestIndex();
                if(id == currentFigureIndex) currentFigureIndex = utilitiesHandle.getHighestIndex();

                System.out.println("Figure with index " + id + " deleted. activeFigureIndex: " + activeFigureIndex + ", currentFigureIndex:" + currentFigureIndex);
                utilitiesHandle.printArrayList(groot);
                return 1;
            } else return 0;
        } else {

            System.out.println("Error! Nothing closed - figure with index " + id + " does not exist.");
            return 0;
        }
    }
    public int close (String param) { // overload close method for the possibility to close all

        if (param == "all") {

            // shouldn't do this with a loop instead use javas listiterator
            for (int indexHandle = 0; indexHandle < groot.size(); indexHandle++) {
                // Delete figure in this id
                Figure figureToClose = (Figure) groot.get(indexHandle).get(1);
                figureToClose.setVisible(false);
                figureToClose.dispose();
                // Plots are going to be deleted by javas garbage-collector
            }

            //  reset both indices
            activeFigureIndex = currentFigureIndex = -1;

            groot.clear(); // Remove all entries in ArrayList
            figureIndexList.clear(); // Remove all entries in our book-keeping-list

            System.out.println("All figures deleted. activeFigureIndex: " + activeFigureIndex + ", currentFigureIndex:" + currentFigureIndex);
            utilitiesHandle.printArrayList(groot);

            return 1;
        } else return 0;
    }
}