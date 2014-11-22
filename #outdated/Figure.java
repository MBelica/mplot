/**
 * matlabInterface v.0.4.02
 *
 *  Description 
 *   blabla
 *   
 *  Todo-List
 *   - Improve computing time
 *   - Get several plots in one panel
 *   - Clean permissions and visiability
 *   - Close every frame itself and not all instances
 *   - Rethink accuracy of function evaluation
 *   - Catch mistakes e.g. a > b...	
 *
 * Licensed blabla;
 *
 */

package matlabInterface;

import java.awt.Dimension;
import javax.swing.JFrame;



public class Figure extends Panel  {
	
    /** Version id for serialisation. */
    private static final long serialVersionUID = -1630939351953868774L;
    /** Just for bookkeeping version */
    private static final String version = "0.4.02";
    /** Variables for plotting styles */
    public enum graphStyle { solid, stroked, plain }; public graphStyle gStyle;
    public enum dataStyle  { circle, fcircle, squares, plain }; public dataStyle  dStyle;
    /** Frame for plotting */
    private JFrame frame;
    /** different variables for plotting */
    public static double gapSize, precision;



    /**
     * Simple constructor for plotting-frame
     *  including initialisation of vars, dim(800, 600)
     */
    protected Figure() {

        Dimension size = new Dimension(800, 600);
        frame = super.showInFrame(size);

        init();
    }

    /**
     * Advanced constructor for plotting-frame
     *  including initialisation of vars
     * @param frameSizeW, dim width
     * @param frameSizeH, dim height
     */
    @SuppressWarnings("unused")
    private Figure(int frameSizeW, int frameSizeH) {
    	
        Dimension size = new Dimension(frameSizeW, frameSizeH);
        frame = super.showInFrame(size);

        init();
    }

    /**
     * Initialisation of variables #temporary
     */
    public final void init () {
    	
        dStyle		= dataStyle.plain;
        gStyle		= graphStyle.solid;
        gapSize		= 0;
        precision	= 0.1;
    }
 	


    /**
     * Plotting from a function e.g 'f(x) = x*x + 2' or simply 'x + 1'
     * @param function
     * @param var
     * @param intervalA
     * @param intervalB
     * @param accuracy
     * @param instance
     */
    /* Simple function-plot */
    public static void plot (String function, Figure instance) {
		
        plot (function, "x", -10.0, 10.0, precision, instance);
    }
    /* Advanced function-plot */
    public static void plot (String function, String var, double intervalA, double intervalB , double accuracy, Figure instance) {
				
        evalFunctionData eval = new evalFunctionData (function, var, intervalA, intervalB, accuracy);
        new Plot (function, eval.getDataTable(), intervalA, intervalB, instance);
    }

    /**
     * Plotting from an array e.g dataArray[i][0] = x, dataArray[i][1] = f(x)
     * @param dataArray
     * @param intervalA
     * @param intervalB
     * @param instance
     */
    /* Simple data-plot */
    public static void plot (double[][] dataArray, Figure instance) {
		
        double min = -10, max = 10; // #todo - extract min and max from array
        plot(dataArray, min, max, instance);
    }
    /* Advanced data-plot */
    public static void plot (double[][] dataArray, double intervalA, double intervalB, Figure instance) {
		
        // #TODO
    }

    /**
     * Plotting from data from a given file
     * @param filename
     * @param seperator
     * @param intervalA
     * @param intervalB
     * @param instance
     */
    /* Simple file-plot */
    public static void plot (String filename, String seperator, Figure instance) {

        double min = -10, max = 10; // #todo - extract min and max from file
        plot(filename, seperator, min, max, instance);
    }
    /* Advanced file-plot */
    public static void plot (String filename, String seperator, double intervalA, double intervalB, Figure instance) {
		
        // #TODO
    }
	


    /**
     * @return frame titel
     */
    @Override
    public String getTitle() {
        return "matlabInterface v." + version;
    }

    /**
     * Simple method to close frame and instance
     * @param instance, pass handler
     */
    public static void clf(Figure instance) {
    	
        instance.frame.setVisible(false);
        instance.frame.dispose();
    }
    
    
   
    /**
     * Example test-output...
     */
    public static void main(String[] args) throws InterruptedException {

        Figure a = new Figure();
        java.lang.Thread.sleep(2000);
        plot("g(x) = x*x*x+2 + 2", a);
        java.lang.Thread.sleep(2000);
        clf(a);
    }
}