package edu.kit.math.mplot;


import javax.swing.JFrame;
import java.awt.*;

class Figure extends JFrame {

    protected int id = 0;
    protected String name = "";

    // Loading matlabs default values of figure properties
    protected boolean numberTitle = true,
                      visible     = true;
    protected enum BGColor { none, white }; protected BGColor bgcolor = BGColor.none;

    Figure (int id, String name) {

        super();
        this.id = id;
        this.name = name;

        setProperties ();
    }

    Figure (int id, String... propertyVarArgs) {

        super();
        this.id = id;

        getProperties(propertyVarArgs);
        setProperties ();
    }

    private void getProperties (String... propertyVarArgs) {

        if (propertyVarArgs.length > 1) {

            for (int i = 1; i <= propertyVarArgs.length; i += 2) {
                String propertyName  = (String) propertyVarArgs[i-1];
                String propertyValue = (String) propertyVarArgs[i];

                switch (propertyName) {
                    case "name":
                        if (propertyValue != "") name = propertyValue; // ToDo: set name also in GRoot
                        break;
                    case "color":
                        bgcolor = BGColor.none; // ToDo: temporary... this is going to be hard
                        break;
                    case "numberTitle":
                        if (propertyValue == "false") numberTitle = false;
                        break;
                    case "visible":
                        if (propertyValue == "false") visible     = false;
                    default:
                        break;
                }
            }
        }
    }

    private void setProperties () {

        /**
         *  Figure properties; for nor only figure appearance
         *  I really don't know any better method than this...
         *  Not (yet) possible: DockControls,  MenuBar, ToolBar, Clipping
         **/
        setSize(600, 400);
        /** Figure Appearance **/
          /* Color */
            switch (bgcolor) { // this is going to be more complex cause of the many possibilities  RGB triplet | short name | long name | 'none' AND that even the plot has to be recolored
                case white:
                    getContentPane().setBackground( new Color(1.0f, 1.0f, 1.0f) );
                    break;
                default:
                     break;
            }

          /*  Name & Number Title */
            String title = "";
            if (numberTitle) {
                title = "Figure " + id;
                if (name != "") title = title + " - ";
            }
            if (name != "") title = title + name;
            setTitle(title);

          /*  Visible */
            setVisible(visible);
    }
}
