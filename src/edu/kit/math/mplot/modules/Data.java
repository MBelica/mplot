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

    private Color[]   sjzy3dDataColor    = null;
    private Coord3d[] sjzy3dDataTable    = null;

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


    public Color[] getJzy3dDataColor() {
        if (sjzy3dDataColor == null) createJzy3dDataSystem();
        return sjzy3dDataColor;
    }

    public Coord3d[] getJzy3dDataTable() {
        if (sjzy3dDataTable == null) createJzy3dDataSystem();
        return sjzy3dDataTable;
    }

    private void createJzy3dDataSystem() {
        float a  = 1.00f;
        int size = this.dataSet.size();

        Color[]   dataColor = new Color[size];
        Coord3d[] dataTable = new Coord3d[size];

        for (int i = 0; i < size; i++) {
            Double xiD = this.dataSet.get(i)[0];
            Double yiD = this.dataSet.get(i)[1];
            Double ziD = this.dataSet.get(i)[2];

            float xiF  = xiD.floatValue();
            float yiF  = yiD.floatValue();
            float ziF  = ziD.floatValue();

            dataTable[i] = new Coord3d(xiF, yiF, ziF);
            dataColor[i] = new Color(xiF, yiF, ziF, a);
        }
        sjzy3dDataTable = dataTable;
        sjzy3dDataColor = dataColor;
    }

    public DataTable getGralDataTable() {
        if (sjzy3dDataTable == null) createGralDataSystem();
        return gralDataTable;
    }

    private void createGralDataSystem() {
        int size = this.dataSet.size();
        DataTable dataTable = new DataTable(Double.class, Double.class);
        for (int i = 0; i < size; i++) {
            dataTable.add(this.dataSet.get(i));
        }

        this.gralDataTable = dataTable;
    }
}
