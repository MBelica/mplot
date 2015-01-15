package edu.kit.math.mplot;

//~--- non-JDK imports --------------------------------------------------------

import de.erichseifert.gral.data.DataTable;

class Data {
    static DataTable dress(double[] x, double[] y) {
        DataTable data = new DataTable(Double.class, Double.class);

        for (int i = 0; i < x.length; i++) {
            data.add(x[i], y[i]);
        }

        return data;
    }
}
