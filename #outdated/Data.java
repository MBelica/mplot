package matlabInterface;

import de.erichseifert.gral.data.DataTable;

import org.matheclipse.core.basic.Config;
import org.matheclipse.core.eval.EvalUtilities;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.parser.client.SyntaxError;
import org.matheclipse.parser.client.math.MathException;



interface Data {
	
    public DataTable getDataTable();
    public void functionHandler();
}



class evalFunctionData implements Data  {
	
    private DataTable data;

    /**
     * Class to  evaluate function on points between a and b, depending on precision result /or computing time will suffer
     * @param plotFunction, given plot-function
     * @param var, variable to resolve for #todo for may not be working for other then x
     * @param intervalA, eval start
     * @param intervalB, eval end
     * @param precision, stepping for evaluation
     */
    @SuppressWarnings("unchecked")
    evalFunctionData (String plotFunction, String var, double intervalA, double intervalB, double precision) {
		
        try {

            data = new DataTable(Double.class, Double.class);
            Config.PARSER_USE_LOWERCASE_SYMBOLS = true; // don't distinguish between lower- and uppercase identifiers
            EvalUtilities util = new EvalUtilities(false, true);

            for (double x = intervalA; x <= intervalB; x += precision) {
                util.evaluate(var+"="+String.valueOf(x));
                IExpr fx =  util.evaluate(plotFunction);

                data.add(x, Double.parseDouble(fx.toString()));
            }
        } catch (SyntaxError e) { // Debug Output
            // catch Symja parser errors here
            System.out.println("Error #1:" + e.getMessage());
        } catch (MathException me) {
            // catch Symja math errors here
            System.out.println("Error #2:" + me.getMessage());
        } catch (Exception e) {
            System.out.println("Error #3:");
            e.printStackTrace();
        }
    }

    /**
     *
     */
    public void functionHandler () {
		
        //
    }

    /**
     *
     * @return data
     */
    public DataTable getDataTable () {

        return data;
    }
	
}



class evalArrayData implements Data  {
	
    private DataTable data;

    /**
     * Class to evaluate given data-arrray for plot #under construction
     * @param dataArray, passed array of form dataArray[i][0] = x, dataArray[i][1] = f(x)
     */
    evalArrayData (double[][] dataArray) {
		       
        for (int i = 0; i <= dataArray.length; i++) {
            data.add(dataArray[i][0], dataArray[i][1]);
        }
    }

    /**
     *
     */
    public void functionHandler () {
		
           //
    }

    /**
     *
     * @return data
     */
    public DataTable getDataTable () {

        return data;
    }
}



class evalFileData implements Data {

    private DataTable data;

    //test purpose to import data from a file with tab separated values
    //DataReader reader = DataReaderFactory.getInstance().get("text/tab-separated-values");
    //de.erichseifert.gral.data.DataSource data = reader.read(new FileInputStream(filename), Double.class, Double.class);

    /**
    *
    */
	@Override
	public void functionHandler() {
		// TODO Auto-generated method stub
		
	}
	
    /**
    *
    * @return data
    */
    public DataTable getDataTable() {

        return data;
    }
}
