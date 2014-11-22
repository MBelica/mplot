package matlabInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;



public abstract class Panel extends JPanel {
	
    /** Version id for serialisation. */
	private static final long serialVersionUID = 5016320032557381547L;

	/**
	 * Abstract base class for all visual,
	 * performs basic initialisation like setting a default size
	 */
    public Panel() {

        super(new BorderLayout());
        setBackground(Color.WHITE);
    }

	/**
	 * Returns a short title for Frame
	 * @return title
	 */
    public abstract String getTitle();

    /**
     * Opens a frame
     * @param frameSize
     * @return the frame instance used for displaying
     */
    protected JFrame showInFrame(Dimension frameSize) {

        setPreferredSize(frameSize);
        JFrame frame = new JFrame(getTitle());

        frame.getContentPane().add(this, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(getPreferredSize());
        frame.setVisible(true);

    return frame;
	}

	@Override
    public String toString() {

        return getTitle();
    }
}