package edu.kit.math.mplot;

public class MPlot {
    private GRootManager groot = new GRootManager();

    /**
     *
     * Figure: create a figure or set an existent figure as active
     *
     * @return integer as figure handle
     */

    public int figure() {
        return figure(-1, "", true);
    }

    public int figure(int id) {
        return figure(id, "");
    }

    public int figure(String... propertyVarArgs) {
        if (propertyVarArgs.length == 1) {
            String tag = propertyVarArgs[0];
            int    id  = groot.getIdToTag(tag);

            if (id > -1) {
                figure(id, tag);
            } else {
                figure(-1, tag, true);
            }
        } else {
            groot.addNewFigureIntoGRoot(-1, "", true, propertyVarArgs);
            Utilities.debugEcho("New figure with index "         + groot.getActiveFigureId() + " created. "
                                         + " activeFigureId: "   + groot.getActiveFigureId()
                                         + ", currentFigureId: " + groot.getCurrentFigureId());
            Utilities.debugEcho(groot.GRootListToString());
        }
        return groot.getActiveFigureId();
    }

    public int figure(int id, String tag, boolean... varArgsAddWithoutId) {
        boolean addWithoutId;
        if (varArgsAddWithoutId.length == 0) addWithoutId = false;
            else addWithoutId = varArgsAddWithoutId[0];

        if ( !addWithoutId && (groot.getIndexToId(id) > -1 ) ) {    // user wants to set a figure active
            groot.setFigureActive(id);
            Utilities.debugEcho("Figure " + id + " set as active. activeFigureId: " + groot.getActiveFigureId()
                                               + ", currentFigureId: "             + groot.getCurrentFigureId());
        } else {    // user wants to create new figure
            groot.addNewFigureIntoGRoot(id, tag, addWithoutId);
            Utilities.debugEcho("New figure with index " + id + " created. activeFigureId: " + groot.getActiveFigureId()
                                                              + ", currentFigureId: "        + groot.getCurrentFigureId());
            Utilities.debugEcho(groot.GRootListToString());
        }
        return groot.getActiveFigureId();
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
        int index = groot.getIndexToActiveFigure();
        System.out.println(index);

        if (index > -1) {
            groot.addPlotsToGRoot(index, linespec, new double[][] {
                x, y
            });
            Utilities.debugEcho("New plot created and associated with figure "  + groot.getActiveFigureId()
                                                        + ". activeFigureId: "  + groot.getActiveFigureId()
                                                        + ", currentFigureId: " + groot.getCurrentFigureId());
        } else if (index == (groot.size() - 1)) {
            Utilities.echo("Error! No figure created yet.");
        }

        Utilities.debugEcho(groot.GRootListToString());
    }

    public void plot(double[]... dataPoints) {
        if (dataPoints.length == 1) {
            double[] x = dataPoints[0];
            double[] y = Utilities.getIndexVs(x);

            plot(x, y, "");
        } else if (dataPoints.length == 2) {
            double[] x = dataPoints[0];
            double[] y = dataPoints[1];

            plot(x, y, "");
        } else if ((dataPoints.length > 0) && ((dataPoints.length & 1) == 0)) {
            int index = groot.getIndexToActiveFigure();

            if (index > -1) {    // if we've found an index (entry in groot) we are going to plot
                groot.addPlotsToGRoot(index, "#MultiplePlots", dataPoints);
                Utilities.debugEcho("New plots created and associated with figure " + groot.getActiveFigureId()
                                                            + ". activeFigureId: "  + groot.getActiveFigureId()
                                                            + ", currentFigureId: " + groot.getCurrentFigureId());
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
    public void clf() { clf( groot.getActiveFigureId() ); }

    public void clf(String... varArgs) {
        if (varArgs.length == 2) {
            clf(groot.getIdToTag(varArgs[0]), varArgs[1]);
        } else if ((varArgs.length == 1) && (varArgs[0] == "reset")) {
            clf( groot.getActiveFigureId() , "reset");
        } else {
            clf(groot.getIdToTag(varArgs[0]));
        }
    }

    public void clf(int id, String... resetVarArgs) {
        if ((id <= groot.getActiveFigureId()) && (groot.size() > 0)) {
            int indexHandle = groot.getIndexToId(id);

            if (indexHandle > -1) {    // > -1 means we found the element
                boolean reset = false;

                if ((resetVarArgs.length > 0) && (resetVarArgs[0] == "reset")) {
                    reset = true;
                }

                groot.clfFigureWithIndex(indexHandle, reset);
                Utilities.debugEcho("Figure with index "   + id + " cleared. "
                                    + "activeFigureId: "   + groot.getActiveFigureId()
                                    + ", currentFigureId:" + groot.getCurrentFigureId());
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
        return close( groot.getActiveFigureId() );
    }

    public int close(int id) {
        if ((id <= groot.getCurrentFigureId()) && (groot.size() > 0)) {
            int index = groot.getIndexToId(id);

            if (index > -1) {
                groot.closeFigure(id, index);

                Utilities.debugEcho("Figure with index "   + id + " deleted. "
                                    + "activeFigureId: "   + groot.getActiveFigureId()
                                    + ", currentFigureId:" + groot.getCurrentFigureId());
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

    public int close(String param) {
        if (param == "all") {
            groot.closeAllFigures();
            Utilities.debugEcho("All figures deleted. "
                                    + "activeFigureId: "   + groot.getActiveFigureId()
                                    + ", currentFigureId:" + groot.getCurrentFigureId());
            Utilities.debugEcho(groot.GRootListToString());

            return 1;
        } else return close(groot.getIdToTag(param));
    }
}