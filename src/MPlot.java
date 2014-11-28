package edu.kit.math.mplot;


import java.util.*;

// ToDO: Interface zur Aufteilung? Package Aufteilung? Fehlerbehebung was passiert wennn Plot aber kein Figure?

public class MPlot {



    static final boolean debug = true;


    private GRootManager groot;
    private Utilities utilitiesHandle;


    protected boolean initialized = false;

    protected int activeFigureId;  // contains id of active figure
    protected int currentFigureId; // contains highest id of all existent figures



    public MPlot () {

        if (!initialized) {

            groot           = new GRootManager();
            utilitiesHandle = new Utilities(this);

            utilitiesHandle.initSystem();
        }
    }



    /****
     * Figure: create a figure or set an existent figure as active
     * @return integer as figure handle
     */
    public int figure () {

        activeFigureId = ++currentFigureId;
        return figure(activeFigureId, "");
    }

    public int figure (int id) {

        activeFigureId = id;
        return figure(id, "");
    }

    public String figure (String tag) { // das hier habe ich neu erstellt, da dies auch in matlab möglich ist, vor allem sind einige der punkte bei figure(property) eben auf diesen name handle bezogen

        if (groot.getIdToTag(tag) > -1) activeFigureId = groot.getIdToTag(tag);
            else activeFigureId = ++currentFigureId;

        figure(activeFigureId, tag);

        return tag;
    }

   public int figure (int id, String tag) {

        if (groot.isIdInUse(id)) { // user wants to set a figure active

            groot.setFigureActive(id);

            if (debug) System.out.println("Figure " + id + " set active. activeFigureId: "+ activeFigureId + ", currentFigureId: " + currentFigureId);
        }
        else{ // user wants to create new figure

            if (id > currentFigureId) currentFigureId =  id;

            groot.addNewFigureIntoGRoot(id, tag);

            if (debug) System.out.print("New figure with index " + String.valueOf(id) + " created. activeFigureId: " + activeFigureId + ", currentFigureId: " + currentFigureId);
            if (debug) groot.printGRootList();
        }

        return activeFigureId;
    }

    public int figure (String... propertyVarArgs) {

        activeFigureId = ++currentFigureId;
        groot.addNewFigureIntoGRoot(activeFigureId, "", propertyVarArgs);

        if (debug) System.out.println("New figure with index " + String.valueOf(activeFigureId) + " created. activeFigureId: " + activeFigureId + ", currentFigureId: " + currentFigureId);
        if (debug) groot.printGRootList();

        return activeFigureId;
    }



    /****
     *  Plot: plot x, y into active figure. If no linespec is given use standards = ""
     */
    public void plot (double[] x, double[] y) {

        plot (x, y, "");
    }

    public void plot (double[] x, double[] y, String linespec) {

        // Check under all created objects whose index contains the activeFigureId and therefore also the figure we want to plot in
        int index = groot.getIndexToId(activeFigureId);
        if ( index > -1 ) { // if we've found an index (entry in groot) we are going to plot

            groot.addPlotToGRoot(index, x, y, linespec);

            if (debug) System.out.println("New plot created and associated with figure " + activeFigureId +". activeFigureId: " + activeFigureId + ", currentFigureId: " + currentFigureId);
        } else if (index == (groot.size()-1)) System.out.println("Error! No figure created yet.");

        if (debug) groot.printGRootList();
    }



    /****
     *  Clf: clear a figure, i.e. removing all plots inside etc, without parameter clf active else clf given
     */
    public void clf (String... resetVarArgs) {

        clf (activeFigureId, resetVarArgs);
    }

    public void clf (String tag, String... resetVarArgs) {

        clf (groot.getIdToTag(tag), resetVarArgs);
    }

    public void clf (int id, String... resetVarArgs) {

        if ((id <= currentFigureId) && (groot.size() > 0))  {  // to be sure check if index is smaller then highest possible index and check if we have objects at all
            // lets search if we find the object to clear
            int indexHandle = groot.getIndexToId (id);
            if ( indexHandle > -1 ) { // > -1 means we found the element

                boolean reset = false;
                if ( ( resetVarArgs.length > 0 ) && (resetVarArgs[0] == "reset") ) reset = true;
                groot.clfFigureWithIndex(indexHandle, reset);

                if (debug) System.out.println("Figure with index " + id + " cleared. activeFigureId: " + activeFigureId + ", currentFigureId:" + currentFigureId);
                if (debug) groot.printGRootList();
            } else System.out.println("Error! Nothing cleared - figure with index " + id + " does not exist.");
        } else System.out.println("Error! Nothing cleared - figure with index " + id + " does not exist.");
    }



    /****
     * Close: closing the figure, either with id to close, without which will close active or with string "all" to close all figures
     * @return 1 if close successful , 0 if not
     */
    public int close () {

        return close(activeFigureId);
    }

    public int close (int id) {
        // to be sure check if index is smaller then highest possible index and check if we have objects at all... this might be redundant though
        if ((id <= currentFigureId) && (groot.size() > 0))  {
            // lets search if we find the object to delete if not simple error output
            int index = groot.getIndexToId(id);
            if ( index > -1 ) { // > -1 means we found the element

                groot.closeFigureWithIndex(id, index);

                // We have to reset both indices, set active as the newester and current as the highest if changed
                if(id == activeFigureId)  activeFigureId  = groot.getNewestId();
                if(id == currentFigureId) currentFigureId = groot.getHighestId();

                if (debug) System.out.println("Figure with index " + id + " deleted. activeFigureId: " + activeFigureId + ", currentFigureId:" + currentFigureId);
                if (debug) groot.printGRootList();

                return 1;
            } else return 0;
    } else {

        System.out.println("Error! Nothing closed - figure with index " + id + " does not exist.");
        return 0;
    }
}

    public int close (String param) { // overload close method for the possibility to close all

        if (param == "all") {

            groot.closeAllFigures();
            activeFigureId = currentFigureId = -1;

            if (debug) System.out.println("All figures deleted. activeFigureId: " + activeFigureId + ", currentFigureId:" + currentFigureId);
            if (debug) groot.printGRootList();

            return 1;
        } else return 0;
    }
}