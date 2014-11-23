package edu.kit.math.mplot;


import java.util.ArrayList;


class Utilities {


    static void initSystem () {

        MPlot.initialized         = true;
        MPlot.figures             = new ArrayList<Figure>();
        MPlot.currentFigureIndex  = -1;
    }
}
