package edu.kit.math.mplot.modules;

//~--- non-JDK imports --------------------------------------------------------

import de.erichseifert.gral.data.DataTable;
import org.jzy3d.maths.Coord3d;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;

public class Data {
    ArrayList<Double[]> dataSet = new ArrayList<>();

    DataGral  gralHandle        = null;
    DataJzy3D jzy3dHandle       = null;

    public Data(double[]... xi) {
        for (int j = 0; j < xi[0].length; j++) {
            Double[] vector = new Double[xi.length];
            for (int k = 0; k < xi.length; k++) {
                vector[k] = xi[k][j];
            }
            dataSet.add(vector);
        }
    }


    public DataTable getGralDataTable() {
        if (gralHandle == null) gralHandle   = new DataGral(dataSet);
        return gralHandle.gralDataTable;
    }

    public Coord3d[] getJzy3dDataTable() {
        if (jzy3dHandle == null) jzy3dHandle = new DataJzy3D(dataSet);
        return jzy3dHandle.sjzy3dDataTable;
    }


    public int getDimension() {
        return this.dataSet.size();
    }

    public int getLength() {
        return this.dataSet.get(0).length;
    }

    public int getSize() {
        return this.dataSet.size();
    }

}