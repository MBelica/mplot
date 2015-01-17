package edu.kit.math.mplot.modules;

//~--- non-JDK imports --------------------------------------------------------

import de.erichseifert.gral.data.DataTable;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;

public class Data {

    private ArrayList<Double[]> dataSet = new ArrayList<Double[]>();

    // Todo this is slow!

    public Data(double[]... xi) {
        for (int j = 0; j < xi[0].length; j++) {
            Double[] vector = new Double[xi.length];
            for (int k = 0; k < xi.length; k++) {
                 vector[k] = xi[k][j];
            }

            dataSet.add(vector);
        }
    }

    public DataTable createGralDataTable() {
        DataTable dataTable = new DataTable(Double.class, Double.class);
        for (int i = 0; i < this.dataSet.size(); i++) {
            dataTable.add(this.dataSet.get(i));
        }
        return dataTable;
    }

    public int getLength() {
        return this.dataSet.get(0).length;
    }

    public int getDimension() {
        return this.dataSet.size();
    }

    // Obsolent use in groot
    private boolean checkDataLength(double[][] xi) {
        int l = xi[0].length;
        boolean fit = true;

        for (int i = 1; i < xi.length; i++) {
            if (xi[i].length != l) fit = false;
        }

        return fit;
    }
}
