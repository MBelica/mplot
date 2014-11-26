package edu.kit.math.mplot;


import java.awt.*;
import java.util.*;


class Utilities {

    MPlot superHandle;

    protected Utilities (MPlot handleParameter) {

        this.superHandle = handleParameter;
    }

    protected void initSystem () {

        superHandle.initialized        = true;
        superHandle.activeFigureIndex  = -1;
        superHandle.currentFigureIndex = -1;
    }

    protected void printArrayList (ArrayList<ArrayList> array) {

        ListIterator<ArrayList> li = array.listIterator();
        System.out.println("Current Figures (#"+ array.size() +"):");
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

    protected int getNewestIndex() {

        int index;

        if (superHandle.figureIndexList.size() > 0) {
            index = (Integer) superHandle.figureIndexList.get( ( (Integer) superHandle.figureIndexList.size() ) - 1 );
        } else index = -1;

        return index;
    }

    protected int getHighestIndex () {

        int index;

        if (superHandle.figureIndexList.size() > 0) {
            index = Collections.max(superHandle.figureIndexList);
        } else index = -1;

        return index;
    }

    // check which index (position in ArrayList) has the figure with given id
    protected int idToIndex (int givenId) {

        int index = 0;
        if (isIndexInUse(givenId)) { // this line fixes bug occuring if givenId < currentIndex but not in use

            int id = (Integer) superHandle.groot.get(index).get(0);
            while (id != givenId) {

                index++;
                id = (Integer) superHandle.groot.get(index).get(0);

                if (index >= superHandle.groot.size()) {
                    index = -1;
                    break;
                }
            }
        } else index = -1;
        return index;
    }

    // Check if user has passed parameters or not, once for 'int' once for 'String'
    protected boolean isVarArgsSet (int... VarArgs) {

        if (VarArgs.length != 0) return true;
            else return false;
    }
    protected boolean isVarArgsSet (String... VarArgs) {
        if (VarArgs.length != 0) return true;
            else return false;
    }

    // Check if given index is associated with a figure
    protected boolean isIndexInUse (int id) {
        if (superHandle.figureIndexList.indexOf(id) > -1) return true;
            else return false;
    }
}




