package edu.kit.math.mplot.modules;

//~--- non-JDK imports --------------------------------------------------------

import org.jzy3d.colors.Color;
import org.jzy3d.maths.Coord3d;

import java.util.ArrayList;

class DataJzy3D {

    Color[]   sjzy3dDataColor;
    Coord3d[] sjzy3dDataTable;

    DataJzy3D(ArrayList<Double[]> dataSet) {
        float a  = 1.00f;
        int size = dataSet.size();

        Color[]   dataColor = new Color[size];
        Coord3d[] dataTable = new Coord3d[size];

        for (int i = 0; i < size; i++) {
            Double xiD = dataSet.get(i)[0];
            Double yiD = dataSet.get(i)[1];
            Double ziD = dataSet.get(i)[2];

            float xiF  = xiD.floatValue();
            float yiF  = yiD.floatValue();
            float ziF  = ziD.floatValue();

            dataTable[i] = new Coord3d(xiF, yiF, ziF);
            dataColor[i] = new Color(xiF, yiF, ziF, a);
        }

        sjzy3dDataTable = dataTable;
        sjzy3dDataColor = dataColor;
    }
}
