package edu.kit.math.mplot;


public class MPlot {


    private GRootManager groot = new GRootManager();

    protected int activeFigureId = -1;  // contains id of active figure
    protected int currentFigureId = -1; // contains highest id of all existent figures


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

        if (groot.getIndexToId(id) > -1) { // user wants to set a figure active

            groot.setFigureActive(id);

            Utilities.debugEcho("Figure " + id + " set active. activeFigureId: "+ activeFigureId + ", currentFigureId: " + currentFigureId) ;
        }
        else{ // user wants to create new figure

            if (id > currentFigureId) currentFigureId =  id;

            groot.addNewFigureIntoGRoot(id, tag);

            Utilities.debugEcho("New figure with index " + String.valueOf(id) + " created. activeFigureId: " + activeFigureId + ", currentFigureId: " + currentFigureId);
            Utilities.debugEchoGRoot(groot);
        }

        return activeFigureId;
    }

    public int figure (String... propertyVarArgs) {

        activeFigureId = ++currentFigureId;
        groot.addNewFigureIntoGRoot(activeFigureId, "", propertyVarArgs);

        Utilities.debugEcho("New figure with index " + String.valueOf(activeFigureId) + " created. activeFigureId: " + activeFigureId + ", currentFigureId: " + currentFigureId);
        Utilities.debugEchoGRoot(groot);

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

            Utilities.debugEcho("New plot created and associated with figure " + activeFigureId +". activeFigureId: " + activeFigureId + ", currentFigureId: " + currentFigureId);
        } else if (index == (groot.size()-1)) Utilities.echo("Error! No figure created yet.");
        Utilities.debugEchoGRoot (groot);
    }


    /****
     *  Clf: clear a figure, i.e. removing all plots inside etc, without parameter clf active else clf given
     */
    public void clf () {

        clf (activeFigureId);
    }

    public void clf (String... varArgs) {

        if (varArgs.length == 2) {
            clf(groot.getIdToTag(varArgs[0]), varArgs[1]);
        }
        else if ( (varArgs.length == 1) && (varArgs[0] == "reset") ) {
            clf (activeFigureId, "reset");
        }
        else  clf(groot.getIdToTag(varArgs[0]));
    }

    public void clf (int id, String... resetVarArgs) {

        if ((id <= currentFigureId) && (groot.size() > 0))  {  // to be sure check if index is smaller then highest possible index and check if we have objects at all
            // lets search if we find the object to clear
            int indexHandle = groot.getIndexToId (id);
            if ( indexHandle > -1 ) { // > -1 means we found the element

                boolean reset = false;
                if ( ( resetVarArgs.length > 0 ) && (resetVarArgs[0] == "reset") ) reset = true;
                groot.clfFigureWithIndex(indexHandle, reset);

                Utilities.debugEcho("Figure with index " + id + " cleared. activeFigureId: " + activeFigureId + ", currentFigureId:" + currentFigureId);
                Utilities.debugEchoGRoot (groot);
            } else Utilities.echo("Error! Nothing cleared - figure with index " + id + " does not exist.");
        } else Utilities.echo("Error! Nothing cleared - figure with index " + id + " does not exist.");
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

                groot.closeFigureWithIndex(index);

                // We have to reset both indices, set active as the newester and current as the highest if changed
                if(id == activeFigureId)  activeFigureId  = groot.getNewestId();
                if(id == currentFigureId) currentFigureId = groot.getHighestId();

                Utilities.debugEcho("Figure with index " + id + " deleted. activeFigureId: " + activeFigureId + ", currentFigureId:" + currentFigureId);
                Utilities.debugEchoGRoot (groot);

                return 1;
            } else return 0;
    } else {

        Utilities.echo("Error! Nothing closed - figure with index " + id + " does not exist.");
        return 0;
    }
}

    public int close (String param) { // overload close method for the possibility to close all

        if (param == "all") {

            groot.closeAllFigures();
            activeFigureId = currentFigureId = -1;

            Utilities.debugEcho("All figures deleted. activeFigureId: " + activeFigureId + ", currentFigureId:" + currentFigureId);
            Utilities.debugEchoGRoot (groot);

            return 1;
        } else return 0;
    }
}