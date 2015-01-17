package edu.kit.math.mplot.modules;

//~--- non-JDK imports --------------------------------------------------------

import de.erichseifert.gral.data.DataTable;
import org.jzy3d.colors.Color;
import org.jzy3d.maths.Coord3d;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;

public class Data {

    private ArrayList<Double[]> dataSet = new ArrayList<Double[]>();

    private DataTable gralDataTable     = null;

    private static Color[]   sjzy3dDataColor    = null;
    private static Coord3d[] sjzy3dDataTable    = null;

    public Data(double[]... xi) {
        for (int j = 0; j < xi[0].length; j++) {
            Double[] vector = new Double[xi.length];
            for (int k = 0; k < xi.length; k++) {
                 vector[k] = xi[k][j];
            }

            dataSet.add(vector);
        }
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


    public static Color[] getJzy3dDataColor() {
        if (sjzy3dDataColor == null) createJzy3dDataSystem();
        return sjzy3dDataColor;
    }

    public static Coord3d[] getJzy3dDataTable() {
        if (sjzy3dDataTable == null) createJzy3dDataSystem();
        return sjzy3dDataTable;
    }

    private static void createJzy3dDataSystem() {
        int size = 5000;
        float x;
        float y;
        float z;
        float a;

        Coord3d[] dataTable = new Coord3d[size];
        Color[]   dataColor = new Color[size];
        for(int i=0; i<size; i++){
            x = (float) Math.sin(i);
            y = (float) Math.cos(i);
            z = (float) i;
            dataTable[i] = new Coord3d(x, y, z);
            a = 1.00f;
            dataColor[i] = new Color(x, y, z, a);
        }

        sjzy3dDataTable = dataTable;
        sjzy3dDataColor = dataColor;
    }

    public DataTable getGralDataTable() {
        if (sjzy3dDataTable == null) createGralDataSystem();
        return gralDataTable;
    }

    private void createGralDataSystem() {
        DataTable dataTable = new DataTable(Double.class, Double.class);
        for (int i = 0; i < this.dataSet.size(); i++) {
            dataTable.add(this.dataSet.get(i));
        }

        this.gralDataTable = dataTable;
    }
}
