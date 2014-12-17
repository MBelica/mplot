package edu.kit.math.mplot;

//~--- non-JDK imports --------------------------------------------------------

import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.plots.XYPlot;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.ListIterator;

class GRootManager {
    protected ArrayList<ArrayList> groot           = new ArrayList<ArrayList>();    // contains every figure, data and plot
    protected boolean              hold            = false;                         //
    protected int                  activeFigureId  = -1;                            // contains id of active figure
    protected int                  currentFigureId = -1;                            // contains highest id of all existent figures

    protected void addNewFigureIntoGRoot(int id, String tag, boolean addWithoutId, String... propertyVarArgs) {
        Figure newFigure;

        if (addWithoutId) activeFigureId = ++currentFigureId;
        else activeFigureId = id;
        if (id > currentFigureId)  currentFigureId = id;

        if (propertyVarArgs.length > 0) {
            newFigure = new Figure(activeFigureId , propertyVarArgs);
            tag       = newFigure.name;
        } else {
            newFigure = new Figure(activeFigureId, tag);
        }

        ArrayList tempArrayList = new ArrayList();

        tempArrayList.add(activeFigureId);
        tempArrayList.add(tag);
        tempArrayList.add(newFigure);
        groot.add(tempArrayList);
    }

    // add Plot into GRoot under given ID, for now small but I think necessary if adding more than one plot into one figure
    protected void addPlotsToGRoot(int index, String linespecsParam, double[]... dataPoints) {
        if ((groot.size() > index) && (groot.get(index).size() > 2)) {
            int existingPlotAmount;
            int newPlotAmount = (dataPoints.length / 2);

            DataTable[] data, existingDataTables;
            String[] lineSpecs, exisitingLineSpecs;

            Figure figureToPlot = getFigureToIndex(index);

            if (hold) {
                existingPlotAmount = (groot.get(index).size() - 3) / 3; // ToDo: trotz Redundanz Sicherheitsabfrage ob Ganzzahlig
                exisitingLineSpecs = getLineSpecsToIndex(index, existingPlotAmount);
                existingDataTables = getDataTablesToIndex(index, existingPlotAmount);

                lineSpecs = new String[newPlotAmount + existingPlotAmount];
                data      = new DataTable[newPlotAmount + existingPlotAmount];

                for (int i = 0; i < existingPlotAmount; i++) {

                    data[i]        = existingDataTables[i];
                    lineSpecs[i]   = exisitingLineSpecs[i];
                }
            }
            else {
                existingPlotAmount = 0;
                lineSpecs = new String[newPlotAmount];
                data      = new DataTable[newPlotAmount];

                int figureSize = groot.get(index).size();
                if (figureSize > 3) {
                    for (int i = (figureSize-1); i >= 3; i--) {
                        groot.get(index).remove(i);
                    }
                }
            }

            for (int i = 0; i < (newPlotAmount); i++) {
                double[] x = dataPoints[2 * i];
                double[] y = dataPoints[2 * i + 1];

                if (x.length == y.length) {
                    data[existingPlotAmount + i] = (Data.dress(x, y));
                } else {
                    Utilities.echo("Error! Cannot plot given data. (Every) x,y-pair must have same length");

                    return;
                }
            }

            figureToPlot.getContentPane().removeAll();
            XYPlot plot = new XYPlot(data);
            for (int i = 0; i < data.length; i++) {
                if (i >= existingPlotAmount) {
                    if (linespecsParam == "#MultiplePlots") {
                        lineSpecs[i] = "#MultiplePlots" + i;
                    } else {
                        lineSpecs[i] = linespecsParam;
                    }
                }

                Plot newPlot = new Plot(figureToPlot, plot, data[i], lineSpecs[i]);

                if (i >= existingPlotAmount) {
                    groot.get(index).add(newPlot);
                    groot.get(index).add(data[i]);
                    groot.get(index).add(lineSpecs[i]);
                }
            }

            figureToPlot.getContentPane().revalidate();
            figureToPlot.getContentPane().repaint();
        } else {
            Utilities.debugEcho("Error! Plot could not be added to Figure " + index);
        }
    }

    protected void changeHoldState(String param) { // ToDo multiple axes and then here current axes instead of whole figure
        if      (param == "on")     hold  = true;
        else if (param == "off")    hold  = false;
        else if (param == "toggle") hold ^= true;
    }

    // clear a figure
    protected void clfFigureWithIndex(int index, boolean reset) {
        if ((groot.size() > index) && (groot.get(index).size() > 2)) {
            if (groot.get(index).size() > 3) {
                for (int i = 3; i < groot.get(index).size(); i++) {
                    groot.get(index).remove(i);
                }
            }

            // CLF figure in this id
            Figure figureToCLF = getFigureToIndex(index);

            figureToCLF.getContentPane().removeAll();
            figureToCLF.getDefaultProperties();
            figureToCLF.setProperties();
            figureToCLF.getContentPane().revalidate();
            figureToCLF.getContentPane().repaint();
        }
    }

    protected void closeFigure(int id, int index) {

        // Delete figure in this id
        if ((groot.size() > index) && (groot.get(index).size() > 2)) {
            Figure figureToClose = getFigureToIndex(index);

            figureToClose.setVisible(false);
            figureToClose.dispose();    // Plots are going to be deleted by javas garbage-collector
            groot.remove(index);

            if (id == activeFigureId)  activeFigureId = getNewestId();
            if (id == currentFigureId) currentFigureId = getHighestId();
        } else {
            System.out.println("Error! Figure with index " + index + " could not be deleted.");
        }
    }

    // close all active figures
    protected void closeAllFigures() {
        ListIterator<ArrayList> li = groot.listIterator();

        while (li.hasNext()) {    // Plots are going to be deleted by javas garbage-collector
            ArrayList object        = li.next();
            Figure    figureToClose = (Figure) object.get(2);

            figureToClose.setVisible(false);
            figureToClose.dispose();
        }

        groot.clear();
        activeFigureId = currentFigureId = -1;
    }

    protected int getActiveFigureId() { return this.activeFigureId;}
    protected int getCurrentFigureId() { return this.currentFigureId;}

    // returns the figure to given id (remember id = position in groot and not the "handle"
    protected Figure getFigureToId(int id) {
        return getFigureToIndex(getIndexToId(id));
    }

    // returns the figure to given id (remember id = position in groot and not the "handle"
    protected Figure getFigureToIndex(int index) {
        return (Figure) groot.get(index).get(2);
    }

    protected String[] getLineSpecsToIndex(int index, int existingPlotAmount) {
        String[] lineSpecs = new String[existingPlotAmount];
        for(int i = 0; i < existingPlotAmount; i++) {
            lineSpecs[i] = (String) groot.get(index).get(3 + 3*i + 2); // 3 for id, tag and figure and every plot has plot, data and params
        }

        return lineSpecs;
    }

    protected DataTable[] getDataTablesToIndex(int index, int existingPlotAmount) {
        DataTable[] DataTables = new DataTable[existingPlotAmount];
        for(int i = 0; i < existingPlotAmount; i++) {
            DataTables[i] = (DataTable) groot.get(index).get(3 + 3*i + 1); // 3 for id, tag and figure and every plot has plot, data and params
        }

        return DataTables;
    }

    protected int getIdToTag(String givenTag) {
        if ((givenTag != "") && (groot.size() > 0)) {
            int    id    = 0;
            int    index = 0;
            String tag   = (String) groot.get(index).get(1);

            while (tag != givenTag) {
                index++;

                if (index >= groot.size()) {
                    id = -1;

                    break;
                }

                tag = (String) groot.get(index).get(1);
            }

            if (id > -1) {
                id = (Integer) groot.get(index).get(0);
            }

            return id;
        } else {
            return -1;
        }
    }

    protected int getIndexToActiveFigure() {
        int index = getIndexToId( getActiveFigureId() ) ;

        return index;
    }

    // check which index (position in ArrayList) has the figure with given id
    protected int getIndexToId(int givenId) {
        int id;
        int index = 0;

        if (groot.size() > 0) {
            id = (Integer) groot.get(index).get(0);

            while (id != givenId) {
                index++;

                if (index >= groot.size()) {
                    index = -1;

                    break;
                }

                id = (Integer) groot.get(index).get(0);
            }
        } else {
            index = -1;
        }

        return index;
    }

    // get highest active index for currentFigureId
    protected int getHighestId() {
        int id;

        if (groot.size() > 0) {
            int maxValue = 0;

            for (int i = 0; i < groot.size(); i++) {
                int grootId = (Integer) groot.get(i).get(0);

                if (grootId > maxValue) {
                    maxValue = grootId;
                }
            }

            id = maxValue;
        } else {
            id = -1;
        }

        return id;
    }

    // get newest index for activeFigureId
    protected int getNewestId() {
        int id;

        if (groot.size() > 0) {
            id = (Integer) groot.get(((Integer) groot.size()) - 1).get(0);
        } else {
            id = -1;
        }

        return id;
    }

    // Convert GRoot to String for output
    protected String GRootListToString() {
        String                  grootString = "";
        ListIterator<ArrayList> li          = groot.listIterator();

        grootString += "Current Figures (#" + groot.size() + "): \n";

        while (li.hasNext()) {
            ArrayList content = li.next();

            grootString += "   - Figure with index " + content.get(0);

            if (content.get(1) != "") {
                grootString += " and tag '" + content.get(1) + "'";
            }

            grootString += ": " + content.get(2) + "\n";

            if (content.size() > 3) {
                grootString += "     Associated plots: \n";

                for (int i = 3; i < content.size(); i += 3) {
                    grootString +=   "        * " + content.get(i)  + "\n";
                        if (i+1<content.size()) grootString += "              - with DataTable: " + content.get(i+1) + "\n";
                        if (i+2<content.size()) grootString += "              - and Linespecs: "  + content.get(i+2) + "\n";
                }
            } else {
                grootString += "     No plots associated \n";
            }
        }

        grootString += "\n";

        return grootString;
    }

    // get figure with given index to front
    protected void setFigureActive(int id) {
        activeFigureId = id;
        int index = getIndexToId(id);

        if ((groot.size() > index) && (groot.get(index).size() > 2)) {
            Figure tempFigure = getFigureToIndex(index);

            tempFigure.toFront();
            tempFigure.repaint();
        } else {
            Utilities.debugEcho("Error with set figure active!");
        }
    }

    protected int size() {
        return groot.size();
    }
}