package edu.kit.math.mplot;


import java.awt.*;
import java.util.*;


public class GRootManager { // ToDo: sort and clean this file



    protected ArrayList<Integer> figureIndexList = new ArrayList<Integer>();   // this is my "book-keeping-list" I can quickly check how many figures are open, what ids are already assigned, which is the highest/newest index etc.
    protected ArrayList<ArrayList> groot = new ArrayList<ArrayList>(); // this is the new ArrayList it does not only contain the figures but also linked with each figure its identifier (id) and p.r.n the associated plots



    // add new Figure and add entry into our figureIndexList
    protected void addNewFigureIntoGRoot (int id, Figure paramFigure) {

        ArrayList tempArrayList = new ArrayList();
        tempArrayList.add(id);
        tempArrayList.add(paramFigure);

        figureIndexList.add(id);
        groot.add(tempArrayList);
    }

    // add Plot into GRoot under given ID, for now small but I think necessary if adding more than one plot into one figure
    protected void addPlotToGRoot (int index, Plot currentPlot) {

        groot.get(index).add(currentPlot);
    }

    // clear a figure
    protected void clfFigureWithIndex (int index) {

        // Delete figure in this id
        Figure figureToCLF = (Figure) groot.get(index).get(1);
        figureToCLF.getContentPane().removeAll();
        figureToCLF.getContentPane().revalidate();
        figureToCLF.getContentPane().repaint();
    }

    // ToDo close a figure I think that it not necessary to get both id and index => use getIdToIndex =>
    protected void closeFigureWithIndex (int id, int index) {

        // Delete figure in this id
        Figure figureToClose = (Figure) groot.get(index).get(1);
        figureToClose.setVisible(false);
        figureToClose.dispose();
        // Plots are going to be deleted by javas garbage-collector
        // Remove entry in ArrayList
        groot.remove(index);
        // As last we remove the entry in our book-keeping-list
        figureIndexList.remove(new Integer(id)); // ToDo geht hier nicht auch einfach index anstatt new Integer(id)
    }

    // close all active figures
    protected void closeAllFigures () {

        // shouldn't do this with a loop instead use javas listiterator
        for (int index = 0; index < groot.size(); index++) {
            // Delete figure in this id
            Figure figureToClose = (Figure) groot.get(index).get(1);
            figureToClose.setVisible(false);
            figureToClose.dispose();
            // Plots are going to be deleted by javas garbage-collector
        }

        groot.clear(); // Remove all entries in ArrayList
        figureIndexList.clear(); // Remove all entries in our book-keeping-list
    }

    // returns the figure to given id (remember id = position in groot and not the "handle"
    protected Figure getFigureToId (int id) {

       return getFigureToIndex (getIndexToId(id));
    }

    // returns the figure to given id (remember id = position in groot and not the "handle"
    protected Figure getFigureToIndex (int index) {

        return (Figure) groot.get(index).get(1);
    }

    // check which index (position in ArrayList) has the figure with given id
    protected int getIndexToId (int givenId) {

        int index = 0;
        if (isIdInUse(givenId)) { // this line fixes bug occuring if givenId < currentIndex but not in use

            int id = (Integer) groot.get(index).get(0);
            while (id != givenId) {

                index++;
                id = (Integer) groot.get(index).get(0);

                if (index >= groot.size()) {
                    index = -1;
                    break;
                }
            }
        } else index = -1;
        return index;
    }

    // get highest active index for currentFigureId
    protected int getHighestId () {

        int id;

        if (figureIndexList.size() > 0) {
            id = Collections.max(figureIndexList);
        } else id = -1;

        return id;
    }

    // get newest index for activeFigureId
    protected int getNewestId () {

        int id;

        if (figureIndexList.size() > 0) {
            id = (Integer) figureIndexList.get( ( (Integer) figureIndexList.size() ) - 1 );
        } else id = -1;

        return id;
    }

    // Check if given index is associated with a figure
    protected boolean isIdInUse (int id) {
        if (figureIndexList.indexOf(id) > -1) return true;
        else return false;
    }

    // Output whole GRoot pretty formatted
    protected void printGRootList () {

        ListIterator<ArrayList> li = groot.listIterator();
        System.out.println("Current Figures (#"+ groot.size() +"):");
        while(li.hasNext()) {

            ArrayList content = li.next();
            System.out.println("   - Figure with index " + content.get(0) + ": " + content.get(1));
            if (content.size() > 2) {
                System.out.println("     Associated plots:");
                for (int i = 2; i < content.size(); i++) System.out.println("        * " + content.get(i));
            } else System.out.println("     No plots associated");
        }

        System.out.println();
    }

    // get figure with given index to front
    protected void setFigureActive (int index) {

        Figure tempFigure = getFigureToIndex(index);
        tempFigure.toFront(); // place in front
        tempFigure.repaint();
    }

    // get size of groot size I call it often O wanted to outsorce it
    protected int size () {

        return groot.size();
    }
}
