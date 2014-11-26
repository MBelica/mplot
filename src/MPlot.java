package edu.kit.math.mplot;


import java.util.*;


public class MPlot {


    private GrootManager grootHandle;
    private Utilities utilitiesHandle;


    protected boolean initialized = false;

    protected int activeFigureIndex;  // index of active figure
    protected int currentFigureIndex; // highest index of all



    public MPlot () {

        if (!initialized) {

            utilitiesHandle = new Utilities(this);
            grootHandle     = new GrootManager();

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

    public int figure (int index) {

        if (grootHandle.isIndexInUse(index)) { // user wants to set a figure active


            Figure tempFigure = (Figure)  grootHandle.getFigureToIndex(index);

            activeFigureIndex = index; // set index active
            tempFigure.toFront(); // place in front
            tempFigure.repaint();

            System.out.println("Figure " + index + " set active. activeFigureIndex: "+ activeFigureIndex + ", currentFigureIndex: " + currentFigureIndex);
        }
        else{ // user wants to create new figure

            activeFigureIndex = index;
            if (index > currentFigureIndex) currentFigureIndex =  activeFigureIndex;

            // for book-keeping take a note that we created a new figure and then create & add elements into our groot
            grootHandle.addIndexIntoFIL(activeFigureIndex);
            Figure newFigure = new Figure();
            ArrayList tempArrayList = new ArrayList();
                tempArrayList.add(currentFigureIndex);
                tempArrayList.add(newFigure);
            grootHandle.groot.add(tempArrayList);

            System.out.println("New figure with index " + String.valueOf(activeFigureIndex) + " created. activeFigureIndex: " + activeFigureIndex + ", currentFigureIndex: " + currentFigureIndex);
            grootHandle.printGrootList();
        }

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
        int indexHandle = grootHandle.getIdToIndex(activeFigureIndex);
        if ( indexHandle > -1 ) { // if we've found an index (entry in groot) we are going to plot

            // ToDo: Resolve grootHandle.groot
            Figure currentFigure = (Figure) grootHandle.groot.get(indexHandle).get(1);
            grootHandle.groot.get(indexHandle).add(new Plot(Data.dress(x, y), currentFigure, linespec)); // add plot into groot under the figure

            System.out.println("New plot created and associated with figure " + indexHandle +". activeFigureIndex: " + activeFigureIndex + ", currentFigureIndex: " + currentFigureIndex);
        } else if (indexHandle == (grootHandle.groot.size()-1)) System.out.println("Error! No figure for activeFigureIndex found.");

        grootHandle.printGrootList();
    }



    /****
     *  Clf: clear a figure, i.e. removing all plots inside etc, without parameter clf active else clf given
     */
    public void clf () {

        clf (activeFigureIndex);
    }

    public void clf (int id) {

        if ((id <= currentFigureIndex) && (grootHandle.groot.size() > 0))  {  // to be sure check if index is smaller then highest possible index and check if we have objects at all
            // lets search if we find the object to clear
            int indexHandle = grootHandle.getIdToIndex(id);
            if ( indexHandle > -1 ) { // > -1 means we found the element

                grootHandle.clfFigureWithIndex(indexHandle);

                System.out.println("Figure with index " + id + " cleared. activeFigureIndex: " + activeFigureIndex + ", currentFigureIndex:" + currentFigureIndex);
                grootHandle.printGrootList();
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
        if ((id <= currentFigureIndex) && (grootHandle.groot.size() > 0))  {
            // lets search if we find the object to delete if not simple error output
            int indexHandle = grootHandle.getIdToIndex(id);
            if ( indexHandle > -1 ) { // > -1 means we found the element

                grootHandle.closeFigureWithIndex(indexHandle);
                grootHandle.figureIndexList.remove(new Integer(id)); // ToDo: Outsorce into GrootManager

                // We have to reset both indices, set active as the newester and current as the highest if changed
                if(id == activeFigureIndex)  activeFigureIndex  = grootHandle.getNewestIndex();
                if(id == currentFigureIndex) currentFigureIndex = grootHandle.getHighestIndex();

                System.out.println("Figure with index " + id + " deleted. activeFigureIndex: " + activeFigureIndex + ", currentFigureIndex:" + currentFigureIndex);
                grootHandle.printGrootList();

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

            System.out.println("All figures deleted. activeFigureIndex: " + activeFigureIndex + ", currentFigureIndex:" + currentFigureIndex);
            grootHandle.printGrootList();

            return 1;
        } else return 0;
    }
}