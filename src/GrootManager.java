package edu.kit.math.mplot;


import java.awt.*;
import java.util.*;


public class GRootManager {



    protected ArrayList<Integer> figureIndexList = new ArrayList<Integer>();   // this is my "book-keeping-list" I can quickly check how many figures are open, what ids are already assigned, which is the highest/newest index etc.
    protected ArrayList<ArrayList> groot = new ArrayList<ArrayList>(); // this is the new ArrayList it does not only contain the figures but also linked with each figure its identifier (id) and p.r.n the associated plots


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

    // add Plot into GRoot under given ID, for now small but I think necessary if adding more than one plot into one figure
    protected void addPlotToId (int id, Plot currentPlot) {

        groot.get(id).add(currentPlot); //
    }

    // returns the figure to given id (remember id = position in groot and not the "handle"
    protected Figure getFigureToId (int id) {

        return (Figure) groot.get(id).get(1);
    }

    // clear a figure
    protected void clfFigureWithIndex (int index) {

        // Delete figure in this id
        Figure figureToCLF = (Figure) groot.get(index).get(1);
        figureToCLF.getContentPane().removeAll();
        figureToCLF.getContentPane().revalidate();
        figureToCLF.getContentPane().repaint();
    }

    // close a figure I think that it not necessary to get both id and index => use getIdToIndex => ToDo
    protected void closeFigureWithIndex (int id, int index) {

        // Delete figure in this id
        Figure figureToClose = (Figure) groot.get(index).get(1);
        figureToClose.setVisible(false);
        figureToClose.dispose();
        // Plots are going to be deleted by javas garbage-collector
        // Remove entry in ArrayList
        groot.remove(index);
        // As last we remove the entry in our book-keeping-list
        figureIndexList.remove(new Integer(id));
    }

    // close all active figures
    protected void closeAllFigures() {

        // shouldn't do this with a loop instead use javas listiterator
        for (int indexHandle = 0; indexHandle < groot.size(); indexHandle++) {
            // Delete figure in this id
            Figure figureToClose = (Figure) groot.get(indexHandle).get(1);
            figureToClose.setVisible(false);
            figureToClose.dispose();
            // Plots are going to be deleted by javas garbage-collector
        }

        groot.clear(); // Remove all entries in ArrayList
        figureIndexList.clear(); // Remove all entries in our book-keeping-list
    }

    // also simple method maybe superfluous...
    protected void addIndexIntoFIL(int index) {

        figureIndexList.add(index);
    }

    // check which index (position in ArrayList) has the figure with given id
    protected int getIdToIndex (int givenId) {

        int index = 0;
        if (isIndexInUse(givenId)) { // this line fixes bug occuring if givenId < currentIndex but not in use

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

    // get size of groot size I call it often O wanted to outsorce it
    protected int size () {

        return groot.size();
    }

    // get newest index for activeFigureIndex
    protected int getNewestIndex () {

        int index;

        if (figureIndexList.size() > 0) {
            index = (Integer) figureIndexList.get( ( (Integer) figureIndexList.size() ) - 1 );
        } else index = -1;

        return index;
    }

    // get highest active index for currentFigureIndex
    protected int getHighestIndex () {

        int index;

        if (figureIndexList.size() > 0) {
            index = Collections.max(figureIndexList);
        } else index = -1;

        return index;
    }

    // Check if given index is associated with a figure
    protected boolean isIndexInUse (int id) {
        if (figureIndexList.indexOf(id) > -1) return true;
        else return false;
    }
}
