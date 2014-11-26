package edu.kit.math.mplot;


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

}




