package edu.kit.math.mplot;


import java.awt.*;
import java.util.*;
import java.awt.geom.GeneralPath;


class Utilities {


    private static final float SQRT2 = (float) Math.pow(2.0, 0.5);


    protected static void initSystem () {

        MPlot.initialized         = true;
        MPlot.activeFigureIndex   = -1;
        MPlot.currentFigureIndex  = -1;
    }

    protected static void printArrayList (ArrayList<ArrayList> array) {

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

    protected static int getNewestIndex() {

        int index;

        if (MPlot.figureIndexList.size() > 0) {
            index = (Integer) MPlot.figureIndexList.get( ( (Integer) MPlot.figureIndexList.size() ) - 1 );
        } else index = -1;

        return index;
    }

    protected static int getHighestIndex () {

        int index;

        if (MPlot.figureIndexList.size() > 0) {
            index = Collections.max(MPlot.figureIndexList);
        } else index = -1;

        return index;
    }

    // check which index (position in ArrayList) has the figure with given id
    protected static int idToIndex (int givenId) {

        int index = 0;
        int id = (Integer) MPlot.objectList.get(index).get(0);

        while (id != givenId ) {

            index++;
            id = (Integer) MPlot.objectList.get(index).get(0);

            if ( index >= MPlot.objectList.size() ) {
                index = -1;
                break;
            }
        }

        return index;
    }

    // Check if user has passed parameters or not, once for 'int' once for 'String'
    protected static boolean isVarArgsSet (int... VarArgs) {

        if (VarArgs.length != 0) return true;
            else return false;
    }
    protected static boolean isVarArgsSet (String... VarArgs) {
        if (VarArgs.length != 0) return true;
            else return false;
    }

    // Check if given index is associated with a figure
    protected static boolean isIndexInUse (int id) {
        if (MPlot.figureIndexList.indexOf(id) > -1) return true;
            else return false;
    }

    // the next 3 methods draw shapes found in matlab, asterisk and plus still has to be written but do we really want this?
    static Shape drawDiagonalCross (final float l, final float t) {

        final GeneralPath p0 = new GeneralPath();

        p0.moveTo(-l - t, -l + t);
        p0.lineTo(-l + t, -l - t);
        p0.lineTo(0.0f, -t * SQRT2);
        p0.lineTo(l - t, -l - t);
        p0.lineTo(l + t, -l + t);
        p0.lineTo(t * SQRT2, 0.0f);
        p0.lineTo(l + t, l - t);
        p0.lineTo(l - t, l + t);
        p0.lineTo(0.0f, t * SQRT2);
        p0.lineTo(-l + t, l + t);
        p0.lineTo(-l - t, l - t);
        p0.lineTo(-t * SQRT2, 0.0f);
        p0.closePath();

        return p0;
    }

    static Shape drawAsterisk (final float l, final float t) {

        final GeneralPath p0 = new GeneralPath();

        p0.moveTo(-l - t, -l + t);
        p0.lineTo(-l + t, -l - t);
        p0.lineTo(0.0f, -t * SQRT2);
        p0.lineTo(l - t, -l - t);
        p0.lineTo(l + t, -l + t);
        p0.lineTo(t * SQRT2, 0.0f);
        p0.lineTo(l + t, l - t);
        p0.lineTo(l - t, l + t);
        p0.lineTo(0.0f, t * SQRT2);
        p0.lineTo(-l + t, l + t);
        p0.lineTo(-l - t, l - t);
        p0.lineTo(-t * SQRT2, 0.0f);
        p0.closePath();

        return p0;
    }

    static Shape drawPlus (final float l, final float t) {

        final GeneralPath p0 = new GeneralPath();

        p0.moveTo(-l - t, -l + t);
        p0.lineTo(-l + t, -l - t);
        p0.lineTo(0.0f, -t * SQRT2);
        p0.lineTo(l - t, -l - t);
        p0.lineTo(l + t, -l + t);
        p0.lineTo(t * SQRT2, 0.0f);
        p0.lineTo(l + t, l - t);
        p0.lineTo(l - t, l + t);
        p0.lineTo(0.0f, t * SQRT2);
        p0.lineTo(-l + t, l + t);
        p0.lineTo(-l - t, l - t);
        p0.lineTo(-t * SQRT2, 0.0f);
        p0.closePath();

        return p0;
    }
}




