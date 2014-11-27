package edu.kit.math.mplot;


import java.util.*;


public class MPlot {



    static final boolean debug = true;


    private GRootManager grootHandle;
    private Utilities utilitiesHandle;


    protected boolean initialized = false;

    protected int activeFigureIndex;  // index of active figure
    protected int currentFigureIndex; // highest index of all



    public MPlot () {

        if (!initialized) {

            utilitiesHandle = new Utilities(this);
            grootHandle     = new GRootManager();

            utilitiesHandle.initSystem();
        }
    }



    /****
     * Figure: create a figure or set an existent figure as active
     * @return integer as figure handle
     */
    public int figure () {

        activeFigureIndex = ++currentFigureIndex;
        return figure(activeFigureIndex);
    }

    public int figure (int index) { // ToDo: Clean figure!

        if (grootHandle.isIndexInUse(index)) { // user wants to set a figure active

            activeFigureIndex = index; // set index active
            grootHandle.setFigureActive(index);

            if (debug) System.out.println("Figure " + index + " set active. activeFigureIndex: "+ activeFigureIndex + ", currentFigureIndex: " + currentFigureIndex);
        }
        else{ // user wants to create new figure

            activeFigureIndex = index;
            if (index > currentFigureIndex) currentFigureIndex =  activeFigureIndex;

            Figure newFigure = new Figure();
            grootHandle.addNewFigureIntoGRoot(index, newFigure);

            if (debug) System.out.println("New figure with index " + String.valueOf(activeFigureIndex) + " created. activeFigureIndex: " + activeFigureIndex + ", currentFigureIndex: " + currentFigureIndex);
            if (debug) grootHandle.printGRootList();
        }

        return activeFigureIndex;
    }

    public int figure (String... propertyVarArgs) {

        activeFigureIndex = ++currentFigureIndex;

        Figure newFigure = new Figure("a", "b", "c", "d");
        grootHandle.addNewFigureIntoGRoot(activeFigureIndex, newFigure);

        if (debug) System.out.println("New figure with index " + String.valueOf(activeFigureIndex) + " created. activeFigureIndex: " + activeFigureIndex + ", currentFigureIndex: " + currentFigureIndex);
        if (debug) grootHandle.printGRootList();

        return activeFigureIndex;
    }



    /****
     *  Plot: plot x, y into active figure. If no linespec is given use standards = ""
     */
    public void plot (double[] x, double[] y) {

        plot (x, y, "");
    }

    public void plot (double[] x, double[] y, String linespec) {

        // Check under all created objects whose index contains the activeFigureIndex and therefore also the figure we want to plot in
        int id = grootHandle.getIdToIndex(activeFigureIndex);
        if ( id > -1 ) { // if we've found an index (entry in groot) we are going to plot

            Plot newPlot = new Plot(Data.dress(x, y), grootHandle.getFigureToId(id), linespec);
            grootHandle.addPlotToGRoot(id, newPlot);

            if (debug) System.out.println("New plot created and associated with figure " + activeFigureIndex +". activeFigureIndex: " + activeFigureIndex + ", currentFigureIndex: " + currentFigureIndex);
        } else if (id == (grootHandle.size()-1)) System.out.println("Error! No figure for activeFigureIndex found.");

        if (debug) grootHandle.printGRootList();
    }



    /****
     *  Clf: clear a figure, i.e. removing all plots inside etc, without parameter clf active else clf given
     */
    public void clf () {

        clf (activeFigureIndex);
    }

    public void clf (int id) {

        if ((id <= currentFigureIndex) && (grootHandle.size() > 0))  {  // to be sure check if index is smaller then highest possible index and check if we have objects at all
            // lets search if we find the object to clear
            int indexHandle = grootHandle.getIdToIndex(id);
            if ( indexHandle > -1 ) { // > -1 means we found the element

                grootHandle.clfFigureWithIndex(indexHandle);

                if (debug) System.out.println("Figure with index " + id + " cleared. activeFigureIndex: " + activeFigureIndex + ", currentFigureIndex:" + currentFigureIndex);
                if (debug) grootHandle.printGRootList();
            } else System.out.println("Error! Nothing cleared - figure with index " + id + " does not exist.");
        } else System.out.println("Error! Nothing cleared - figure with index " + id + " does not exist.");
    }



    /****
     * Close: closing the figure, either with id to close, without which will close active or with string "all" to close all figures
     * @return 1 if close successful , 0 if not
     */
    public int close () {

        return close(activeFigureIndex);
    }

    public int close (int id) {

        // to be sure check if index is smaller then highest possible index and check if we have objects at all... this might be redundant though
        if ((id <= currentFigureIndex) && (grootHandle.size() > 0))  {
            // lets search if we find the object to delete if not simple error output
            int indexHandle = grootHandle.getIdToIndex(id);
            if ( indexHandle > -1 ) { // > -1 means we found the element

                grootHandle.closeFigureWithIndex(id, indexHandle);

                // We have to reset both indices, set active as the newester and current as the highest if changed
                if(id == activeFigureIndex)  activeFigureIndex  = grootHandle.getNewestIndex();
                if(id == currentFigureIndex) currentFigureIndex = grootHandle.getHighestIndex();

                if (debug) System.out.println("Figure with index " + id + " deleted. activeFigureIndex: " + activeFigureIndex + ", currentFigureIndex:" + currentFigureIndex);
                if (debug) grootHandle.printGRootList();

                return 1;
            } else return 0;
        } else {

            System.out.println("Error! Nothing closed - figure with index " + id + " does not exist.");
            return 0;
        }
    }

    public int close (String param) { // overload close method for the possibility to close all

        if (param == "all") {

            grootHandle.closeAllFigures();
            activeFigureIndex = currentFigureIndex = -1;

            if (debug) System.out.println("All figures deleted. activeFigureIndex: " + activeFigureIndex + ", currentFigureIndex:" + currentFigureIndex);
            if (debug) grootHandle.printGRootList();

            return 1;
        } else return 0;
    }
}