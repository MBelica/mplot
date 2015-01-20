package edu.kit.math.mplot.modules;

//~--- non-JDK imports --------------------------------------------------------

import java.util.ArrayList;
import de.erichseifert.gral.data.DataTable;

class DataGral  {

    DataTable gralDataTable = new DataTable(Double.class, Double.class);

    DataGral(ArrayList<Double[]> dataSet) {
        for( Double[] dataSetElement : dataSet) gralDataTable.add(dataSetElement);
    }
}
