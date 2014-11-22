package matlabInterface;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.plots.points.PointRenderer;
import de.erichseifert.gral.ui.InteractivePanel;
import de.erichseifert.gral.util.Insets2D;



public class Plot extends JPanel {
		
    /** Version id for serialisation. */
    private static final long serialVersionUID = -2877870776407782236L;

    /**
     * + Class plot to paint everything into the created panel
     * @param plotTitel, processed function or data name
     * @param data, parsed data via data class
     * @param intervalA
     * @param intervalB
     * @param instance, figure instance to address frame, panel and plot
     */
    public Plot (String plotTitel, DataTable data, double intervalA, double intervalB, Figure instance) {
		
        XYPlot 		 plot 		  = new XYPlot(data);
        LineRenderer lineRenderer = new DefaultLineRenderer2D();

        // Format plot
        plot.getTitle().setText(plotTitel);
        plot.setBackground(Color.WHITE);
        plot.setInsets(new Insets2D.Double(20.0, 40.0, 40.0, 40.0));
        plot.setLineRenderer(data, lineRenderer); // not necessary? I think its already called in linefRenderer

        // Calling functions to draw data points and data lines
        linefRenderer(data, plot, lineRenderer, instance);
        pointfRenderer(data, plot, instance);

        // Add plot to Swing component
        instance.add(new InteractivePanel(plot), BorderLayout.CENTER);	//somewhere in frame.de.erichseifert.gral.ui.InteractivePanel hast to be the solotion for several plots in one frame
        instance.setVisible(true);
        instance.revalidate();
        instance.repaint();
	}

    /**
     * Draw connecting lines on our data depending on var gStyle defined in instance using check de.erichseifert.gral.plots.lines.DefaultLineRenderer2D
     * @param data, parsed data via data class
     * @param plot, instance for data plot
     * @param lineRenderer, instance for line plot
     * @param instance, handler for frame
     */
    private void linefRenderer (DataTable data, XYPlot plot, LineRenderer lineRenderer, Figure instance) {
		
        lineRenderer.setGap(Figure.gapSize);
        switch (instance.gStyle) {
		
            case solid:
                plot.setLineRenderer(data, lineRenderer);
                break;
          
            case stroked:
                BasicStroke lineStyle = new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[] {3f, 3f}, 0.0f);
                lineRenderer.setStroke(lineStyle);
                break;
				
            case plain:
                break;
				
            default:
                plot.setLineRenderer(data, lineRenderer);
                break;
        }
    }

    /**
     * Draw style of data points, depending on vat dStyle defined in instance
     * @param data, parsed data via data class
     * @param plot, instance for data plot
     * @param instance, handler for frame
     */
    private void pointfRenderer (DataTable data, XYPlot plot, Figure instance) {

        PointRenderer pointRenderer = plot.getPointRenderer(data);
        switch (instance.dStyle) {
		
            case circle:
                pointRenderer.setShape(new Ellipse2D.Double(-4.0, -4.0, 8.0, 8.0));
                break;
      
            case fcircle:
                Shape circle = new Ellipse2D.Double(-4.0, -4.0, 8.0, 8.0);
                pointRenderer.setShape(circle);
                break;
			
            case squares:
                Shape circle2 = new Rectangle2D.Double(-2.5, -2.5, 5, 5);
                pointRenderer.setShape(circle2);
                break;
				
            case plain:
                pointRenderer.setShape(new Ellipse2D.Double(-1.0, -1.0, 2.0, 2.0));
                break;
				
            default:
                pointRenderer.setShape(new Ellipse2D.Double(-1.0, -1.0, 2.0, 2.0));
                break;
        }
    }
}

