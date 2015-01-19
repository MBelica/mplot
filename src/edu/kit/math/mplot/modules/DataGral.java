package edu.kit.math.mplot.modules;

//~--- non-JDK imports --------------------------------------------------------

import java.util.ArrayList;
import de.erichseifert.gral.data.DataTable;


class DataGral  {

    DataTable gralDataTable = new DataTable(Double.class, Double.class);

    DataGral(ArrayList<Double[]> dataSet) {
        int size = dataSet.size();

        for (int i = 0; i < size; i++) {
            gralDataTable.add(dataSet.get(i));
        }
    }
}
