package edu.kit.math.mplot;

public class MPlot {
    protected int        activeFigureId  = -1;    // contains id of active figure
    protected int        currentFigureId = -1;    // contains highest id of all existent figures
    private GRootManager groot           = new GRootManager();

    /**
     * 
     * Figure: create a figure or set an existent figure as active
     *
     * @return integer as figure handle
     */
    public int figure() {
        activeFigureId = ++currentFigureId;

        return figure(activeFigureId, "");
    }

    public int figure(int id) {
        activeFigureId = id;

        return figure(id, "");
    }

    public int figure(String... propertyVarArgs) {
        if (propertyVarArgs.length == 1) {
            String tag = propertyVarArgs[0];
            int    id  = groot.getIdToTag(tag);

            if (id > -1) {
                activeFigureId = id;
            } else {
                activeFigureId = ++currentFigureId;
            }

            figure(activeFigureId, tag);

            return id;
        } else {
            activeFigureId = ++currentFigureId;
            groot.addNewFigureIntoGRoot(activeFigureId, "", propertyVarArgs);
            Utilities.debugEcho("New figure with index " + String.valueOf(activeFigureId)
                                + " created. activeFigureId: " + activeFigureId + ", currentFigureId: "
                                + currentFigureId);
            Utilities.debugEcho(groot.GRootListToString());

            return activeFigureId;
        }
    }

    public int figure(int id, String tag) {
        if (groot.getIndexToId(id) > -1) {    // user wants to set a figure active
            groot.setFigureActive(id);
            Utilities.debugEcho("Figure " + id + " set active. activeFigureId: " + activeFigureId
                                + ", currentFigureId: " + currentFigureId);
        } else {    // user wants to create new figure
            if (id > currentFigureId) {
                currentFigureId = id;
            }

            groot.addNewFigureIntoGRoot(id, tag);
            Utilities.debugEcho("New figure with index " + String.valueOf(id) + " created. activeFigureId: "
                                + activeFigureId + ", currentFigureId: " + currentFigureId);
            Utilities.debugEcho(groot.GRootListToString());
        }

        return activeFigureId;
    }

    /**
     * 
     * Plot: plot x, y into active figure. If no linespec is given use standards = ""
     */
    public void plot(double[] y, String linespec) {
        double[] x = Utilities.getIndexVs(y);

        plot(x, y, linespec);
    }

    public void plot(double[] x, double[] y, String linespec) {
        int index = groot.getIndexToId(activeFigureId);

        if (index > -1) {
            groot.addPlotsToGRoot(index, linespec, new double[][] {
                x, y
            });
            Utilities.debugEcho("New plot created and associated with figure " + activeFigureId + ". activeFigureId: "
                                + activeFigureId + ", currentFigureId: " + currentFigureId);
        } else if (index == (groot.size() - 1)) {
            Utilities.echo("Error! No figure created yet.");
        }

        Utilities.debugEcho(groot.GRootListToString());
    }

    public void plot(double[]... dataPoints) {
        if (dataPoints.length == 1) {
            double[] y = dataPoints[0];
            double[] x = Utilities.getIndexVs(y);

            plot(x, y, "");
        } else if (dataPoints.length == 2) {
            double[] y = dataPoints[0];
            double[] x = dataPoints[1];

            plot(x, y, "");
        } else if ((dataPoints.length > 0) && ((dataPoints.length & 1) == 0)) {
            int index = groot.getIndexToId(activeFigureId);

            if (index > -1) {    // if we've found an index (entry in groot) we are going to plot
                groot.addPlotsToGRoot(index, "#MultiplePlots", dataPoints);
                Utilities.debugEcho("New plots created and associated with figure " + activeFigureId
                                    + ". activeFigureId: " + activeFigureId + ", currentFigureId: " + currentFigureId);
            } else if (index == (groot.size() - 1)) {
                Utilities.echo("Error! No figure created yet.");
            }

            Utilities.debugEcho(groot.GRootListToString());
        } else {
            Utilities.echo("Error! Cannot plot given data.");
        }
    }

    /**
     * 
     * Clf: clear a figure, i.e. removing all plots inside etc, without parameter clf active else clf given
     */
    public void clf() {
        clf(activeFigureId);
    }

    public void clf(String... varArgs) {
        if (varArgs.length == 2) {
            clf(groot.getIdToTag(varArgs[0]), varArgs[1]);
        } else if ((varArgs.length == 1) && (varArgs[0] == "reset")) {
            clf(activeFigureId, "reset");
        } else {
            clf(groot.getIdToTag(varArgs[0]));
        }
    }

    public void clf(int id, String... resetVarArgs) {
        if ((id <= currentFigureId) && (groot.size() > 0)) {    // to be sure check if index is smaller then highest possible index and check if we have objects at all

            // lets search if we find the object to clear
            int indexHandle = groot.getIndexToId(id);

            if (indexHandle > -1) {    // > -1 means we found the element
                boolean reset = false;

                if ((resetVarArgs.length > 0) && (resetVarArgs[0] == "reset")) {
                    reset = true;
                }

                groot.clfFigureWithIndex(indexHandle, reset);
                Utilities.debugEcho("Figure with index " + id + " cleared. activeFigureId: " + activeFigureId
                                    + ", currentFigureId:" + currentFigureId);
                Utilities.debugEcho(groot.GRootListToString());
            } else {
                Utilities.echo("Error! Nothing cleared - figure with index " + id + " does not exist.");
            }
        } else {
            Utilities.echo("Error! Nothing cleared - figure with index " + id + " does not exist.");
        }
    }

    /**
     * 
     * Close: closing the figure, either with id to close, without which will close active or with string "all" to close all figures
     *
     * @return 1 if close successful , 0 if not
     */
    public int close() {
        return close(activeFigureId);
    }

    public int close(int id) {

        // to be sure check if index is smaller then highest possible index and check if we have objects at all... this might be redundant though
        if ((id <= currentFigureId) && (groot.size() > 0)) {

            // lets search if we find the object to delete if not simple error output
            int index = groot.getIndexToId(id);

            if (index > -1) {    // > -1 means we found the element
                groot.closeFigureWithIndex(index);

                // We have to reset both indices, set active as the newester and current as the highest if changed
                if (id == activeFigureId) {
                    activeFigureId = groot.getNewestId();
                }

                if (id == currentFigureId) {
                    currentFigureId = groot.getHighestId();
                }

                Utilities.debugEcho("Figure with index " + id + " deleted. activeFigureId: " + activeFigureId
                                    + ", currentFigureId:" + currentFigureId);
                Utilities.debugEcho(groot.GRootListToString());

                return 1;
            } else {
                return 0;
            }
        } else {
            Utilities.echo("Error! Nothing closed - figure with index " + id + " does not exist.");

            return 0;
        }
    }

    public int close(String param) {    // overload close method for the possibility to close all
        if (param == "all") {
            groot.closeAllFigures();
            activeFigureId = currentFigureId = -1;
            Utilities.debugEcho("All figures deleted. activeFigureId: " + activeFigureId + ", currentFigureId:"
                                + currentFigureId);
            Utilities.debugEcho(groot.GRootListToString());

            return 1;
        } else {
            return close(groot.getIdToTag(param));
        }
    }
}