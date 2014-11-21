package edu.kit.math.mplot;

import java.util.*;
import java.awt.Color;
import javax.swing.JFrame;
import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.plots.points.PointRenderer;
import de.erichseifert.gral.ui.InteractivePanel;
import edu.kit.math.mplot.Figure;

public class MPlot {
   private List<Figure> figures;
   private int current_figure_index;
   
   public MPlot() {
      figures = new ArrayList<Figure>();
      current_figure_index = -1;
   }
   
   public int figure() {
      Figure new_figure = new Figure();
      figures.add(new_figure);
      current_figure_index = figures.size()-1;
      return current_figure_index;
   }
   
   public void plot(double[] X, double[] Y) {   
      Figure current_figure = figures.get(current_figure_index);
      
      DataTable data = new DataTable(Double.class, Double.class);
      for (int i=0; i<X.length; i++) 
         data.add(X[i],Y[i]);
         
      XYPlot plot = new XYPlot(data);
      current_figure.getContentPane().add(new InteractivePanel(plot));
      LineRenderer lines = new DefaultLineRenderer2D();
      plot.setLineRenderer(data, lines);
      Color color = new Color(0.0f, 0.3f, 1.0f);
      plot.getPointRenderer(data).setColor(color);
      plot.getLineRenderer(data).setColor(color);
      
      current_figure.revalidate();
      current_figure.repaint();
      
      
   }
}
