package edu.kit.math.mplot;

public class MPlot {
    static outputStyle             echoOutput          = outputStyle.console;
    static outputStyle             debugOutput         = outputStyle.console;
    static reportingStyle          echoReportingLevel  = reportingStyle.loud;
    static reportingStyle          debugReportingLevel = reportingStyle.loud;
    public static volatile int     infPauseSequence    = 250;
    public static volatile long    systemStartTime     = System.nanoTime();
    public static volatile boolean infLoop             = false;
    public static final double
        pi                                             = Math.PI,
        PI                                             = Math.PI;
    private boolean      pausingEnabled                = true;
    private GRootManager groot                         = new GRootManager();

    static enum outputStyle { console, file, none }

    static enum reportingStyle { silent, normal, loud }

    /**
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
            Watchdog.debugEcho("[" + Utilities.getExecuteDuration() + "] " + "New figure with index "
                               + groot.getActiveFigureId() + " created. " + " activeFigureId: "
                               + groot.getActiveFigureId() + ", currentFigureId: " + groot.getCurrentFigureId(), 1);
            Watchdog.debugEcho("[" + Utilities.getExecuteDuration() + "] " + groot.GRootListToString(), 2);
        }

        return groot.getActiveFigureId();
    }

    public int figure(int id, String tag, boolean... varArgsAddWithoutId) {
        boolean addWithoutId = (varArgsAddWithoutId.length != 0) && varArgsAddWithoutId[0];

        if (!addWithoutId && (groot.getIndexToId(id) > -1)) {    // user wants to set a figure active
            groot.setFigureActive(id);
            Watchdog.debugEcho("[" + Utilities.getExecuteDuration() + "] " + "Figure " + id + " set as active. "
                               + "activeFigureId: " + groot.getActiveFigureId() + ", currentFigureId: "
                               + groot.getCurrentFigureId(), 1);
        } else {    // user wants to create new figure
            groot.addNewFigureIntoGRoot(id, tag, addWithoutId);
            Watchdog.debugEcho("[" + Utilities.getExecuteDuration() + "] " + "New figure with index " + id
                               + " created. " + "activeFigureId: " + groot.getActiveFigureId() + ", currentFigureId: "
                               + groot.getCurrentFigureId(), 1);
            Watchdog.debugEcho("[" + Utilities.getExecuteDuration() + "] " + groot.GRootListToString(), 2);
        }

        return groot.getActiveFigureId();
    }

    /**
     * Plot: plot x, y into active figure. If no linespec is given use standards = ""
     */
    public void plot(double[] y, String linespec) {
        plot(linespec, Utilities.getIndexVs(y), y);
    }

    public void plot(double[] x, double[] y, String linespec) {
        plot(linespec, x, y);
    }

    public void plot(double[]... dataPoints) {
        if (dataPoints.length > 0) {
            if (dataPoints.length == 1) {
                double[] y = dataPoints[0];
                double[] x = Utilities.getIndexVs(y);

                plot("MultiplePlots#", x, y);
            } else {
                plot("MultiplePlots#", dataPoints);
            }
        }
    }

    private void plot(String linespec, double[]... dataPoints) {
        if (groot.activeFigureId == -1) {
            figure();
        }

        int index = groot.getIndexToActiveFigure();

        if (index > -1) {    // if we've found an index (entry in groot) we are going to plot
            if ((dataPoints.length > 0) && ((dataPoints.length % 2) == 0)) {
                groot.addPlotsToGRoot(index, 2, linespec, dataPoints);
                Watchdog.debugEcho("[" + Utilities.getExecuteDuration() + "] "
                                   + "New plots created and associated with figure " + groot.getActiveFigureId()
                                   + ". activeFigureId: " + groot.getActiveFigureId() + ", currentFigureId: "
                                   + groot.getCurrentFigureId(), 1);
            } else {
                Watchdog.echo("Error! Cannot plot given data.", 0);
            }
        } else if (index == (groot.size() - 1)) {
            Watchdog.debugEcho("[" + Utilities.getExecuteDuration() + "] " + "Error! Could not get active figure.", 0);
        }

        Watchdog.debugEcho("[" + Utilities.getExecuteDuration() + "] " + groot.GRootListToString(), 2);
    }

    /**
     * Plot3: plot x, y, z into active figure. If no linespec is given use standards = ""
     */
    public void plot3(double[] x, double[] y, double[] z, String linespec) {
        plot3(linespec, x, y, z);
    }

    public void plot3(double[]... dataPoints) {
        plot3("MultiplePlots#", dataPoints);
    }

    private void plot3(String linespec, double[]... dataPoints) {
        if (groot.activeFigureId == -1) {
            figure();
        }

        int index = groot.getIndexToActiveFigure();

        if (index > -1) {
            if ((dataPoints.length > 0) && ((dataPoints.length % 3) == 0)) {
                groot.addPlotsToGRoot(index, 3, linespec, dataPoints);
                Watchdog.debugEcho("[" + Utilities.getExecuteDuration() + "] "
                                   + "New plot created and associated with figure " + groot.getActiveFigureId()
                                   + ". activeFigureId: " + groot.getActiveFigureId() + ", currentFigureId: "
                                   + groot.getCurrentFigureId(), 1);
            } else {
                Watchdog.echo("Error! Cannot plot given data.", 0);
            }
        } else if (index == (groot.size() - 1)) {
            Watchdog.echo("Error! No figure created yet.", 0);
        }

        Watchdog.debugEcho("[" + Utilities.getExecuteDuration() + "] " + groot.GRootListToString(), 2);
    }

    public void surf () {
        int index = groot.getIndexToActiveFigure();
        System.out.println("");
        groot.addPlotsToGRoot(index, 100, "<>MultiplePlots#", new double[] {1.0, 2.0, 3.0});
    }

    /**
     *
     * Clf: clear a figure, i.e. removing all plots inside etc, without parameter clf active else clf given
     */
    public void clf() {
        clf(groot.getActiveFigureId());
    }

    public void clf(String... varArgs) {
        if (varArgs.length == 2) {
            clf(groot.getIdToTag(varArgs[0]), varArgs[1]);
        } else if ((varArgs.length == 1) && (varArgs[0].equals("reset"))) {
            clf(groot.getActiveFigureId(), "reset");
        } else {
            clf(groot.getIdToTag(varArgs[0]));
        }
    }

    public void clf(int id, String... resetVarArgs) {
        if ((id <= groot.getActiveFigureId()) && (groot.size() > 0)) {
            int indexHandle = groot.getIndexToId(id);

            if (indexHandle > -1) {    // > -1 means we found the element
                boolean reset = false;

                if ((resetVarArgs.length > 0) && (resetVarArgs[0].equals("reset"))) {
                    reset = true;
                }

                groot.clfFigureWithIndex(indexHandle, reset);
                Watchdog.debugEcho("[" + Utilities.getExecuteDuration() + "] " + "Figure with index " + id
                                   + " cleared. " + "activeFigureId: " + groot.getActiveFigureId()
                                   + ", currentFigureId:" + groot.getCurrentFigureId(), 1);
                Watchdog.debugEcho("[" + Utilities.getExecuteDuration() + "] " + groot.GRootListToString(), 2);
            } else {
                Watchdog.echo("Error! Nothing cleared - figure with index " + id + " does not exist.", 0);
            }
        } else {
            Watchdog.echo("Error! Nothing cleared - figure with index " + id + " does not exist.", 0);
        }
    }

    /**
     *
     * Close: closing the figure, either with id to close, without which will close active or with string "all" to close all figures
     * @return 1 if close successful , 0 if not
     */
    public int close() {
        return close(groot.getActiveFigureId());
    }

    public int close(int id) {
        if ((id <= groot.getCurrentFigureId()) && (groot.size() > 0)) {
            int index = groot.getIndexToId(id);

            if (index > -1) {
                groot.closeFigure(id, index);
                Watchdog.debugEcho("[" + Utilities.getExecuteDuration() + "] " + "Figure with index " + id
                                   + " deleted. " + "activeFigureId: " + groot.getActiveFigureId()
                                   + ", currentFigureId:" + groot.getCurrentFigureId(), 1);
                Watchdog.debugEcho("[" + Utilities.getExecuteDuration() + "] " + groot.GRootListToString(), 2);

                return 1;
            } else {
                return 0;
            }
        } else {
            Watchdog.echo("Error! Nothing closed - figure with index " + id + " does not exist.", 0);

            return 0;
        }
    }

    public int close(String param) {
        if (param.equals("all")) {
            groot.closeAllFigures();
            Watchdog.debugEcho("[" + Utilities.getExecuteDuration() + "] " + "All figures deleted. "
                               + "activeFigureId: " + groot.getActiveFigureId() + ", currentFigureId:"
                               + groot.getCurrentFigureId(), 1);
            Watchdog.debugEcho("[" + Utilities.getExecuteDuration() + "] " + groot.GRootListToString(), 2);

            return 1;
        } else {
            return close(groot.getIdToTag(param));
        }
    }

    /**
     *
     * Pause pauses execution for n seconds before continuing, where n is any non negative real number. Pausing must be enabled for this to take effect.
     *
     */
    public void pause() throws InterruptedException {
        infLoop = true;
        Watchdog.debugEcho("[" + Utilities.getExecuteDuration() + "] " + "Pausing enabled until one presses any key",
                           1);

        while (infLoop) {
            Thread.sleep(infPauseSequence);
        }
    }

    public void pause(int sleepTime) throws InterruptedException {
        if (pausingEnabled) {
            if (sleepTime >= 0) {
                Watchdog.debugEcho("[" + Utilities.getExecuteDuration() + "] " + "Pausing for " + sleepTime
                                   + " ms enabled.", 1);
                java.lang.Thread.sleep(sleepTime);
            } else {
                Watchdog.echo("Error! Pause time has to be positive", 0);
            }
        }
    }

    public String pause(String state) throws InterruptedException {
        if (state.equals("newstate")) {
            pausingEnabled ^= true;
            Watchdog.debugEcho("[" + Utilities.getExecuteDuration() + "] " + "Pausing state switched. Pausing now "
                               + pausingEnabled, 1);

            if (pausingEnabled) {
                return "off";    // return oldstate
            } else {
                return "on";
            }
        } else {
            switch (state) {
            case "on" :
                pausingEnabled = true;

                break;

            case "off" :
                pausingEnabled = false;

                break;

            case "inf" :
                Watchdog.debugEcho("[" + Utilities.getExecuteDuration() + "] " + "Infinite pausing enabled.", 1);

                while (true) {
                    Thread.sleep(infPauseSequence);
                }
            }

            Watchdog.debugEcho("[" + Utilities.getExecuteDuration() + "] " + "Pausing state changed. Pausing now "
                               + pausingEnabled, 1);

            if (pausingEnabled) {
                return "on";    // return state
            } else {
                return "off";
            }
        }
    }

    public void hold() {
        hold("toggle");
    }

    public void hold(String param) {
        groot.changeHoldState(param);
    }

    public void help() {}

    public void help(String param) {
        Watchdog.echo("[" + Utilities.getExecuteDuration() + "] " + "help: " + param, 0);
    }
}