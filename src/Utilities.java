package edu.kit.math.mplot;


class Utilities {


    MPlot superHandle;


    protected Utilities (MPlot handleParameter) {

        this.superHandle = handleParameter;
    }

    protected void initSystem () {

        superHandle.initialized     = true;
        superHandle.activeFigureId  = -1;
        superHandle.currentFigureId = -1;
    }

}




