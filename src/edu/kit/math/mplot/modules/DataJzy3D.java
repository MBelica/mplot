package edu.kit.math.mplot.modules;

//~--- non-JDK imports --------------------------------------------------------

import org.jzy3d.maths.Coord3d;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;

class DataJzy3D {

    Coord3d[] sjzy3dDataTable;

    DataJzy3D(ArrayList<Double[]> dataSet) {
        int size = dataSet.size();

        Coord3d[] dataTable = new Coord3d[size];

        for (int i = 0; i < size; i++) {
            Double xiD = dataSet.get(i)[0];
            Double yiD = dataSet.get(i)[1];
            Double ziD = dataSet.get(i)[2];

            dataTable[i] = new Coord3d(xiD, yiD, ziD);
        }

        sjzy3dDataTable = dataTable;
    }
}
