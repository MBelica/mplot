package edu.kit.math.mplot;

//~--- JDK imports ------------------------------------------------------------

import javax.swing.*;
import java.awt.Color;

class Figure extends JFrame {

    /**
     * Figure properties; for now only some figure appearances
     */

    protected int    id   = 0;
    protected String name = "";

    // matlabs figure properties
    protected String   position;
    protected boolean  numberTitle, visible, resize;
    protected Color  bgcolor;
    protected Renderer renderer;

    protected enum Renderer { gral }

    Figure(int id, String name) {
        super();
        this.id   = id;
        this.name = name;
        getDefaultProperties();
        setProperties();

    }

    Figure(int id, String... propertyVarArgs) {
        super();
        this.id = id;
        getDefaultProperties();
        getProperties(propertyVarArgs);
        setProperties();
    }

    protected void getDefaultProperties() {
        name        = "";
        position    = "[20 20 600 400]";
        numberTitle = true;
        visible     = true;
        resize      = true;
        bgcolor     = null;
        renderer    = Renderer.gral;
    }

    protected void getProperties(String... propertyVarArgs) {
        if (propertyVarArgs.length > 1) {
            for (int i = 1; i <= propertyVarArgs.length; i += 2) {
                String propertyName  = (String) propertyVarArgs[i - 1];
                String propertyValue = (String) propertyVarArgs[i];

                switch (propertyName) {

                    /** Figure Appearance */
                    case "Color" :
                        if ( (propertyValue == "yellow") || (propertyValue == "y") ) {
                            bgcolor = new Color(1.0f, 1.0f, 0.0f);
                        } else if ( (propertyValue == "magenta") || (propertyValue == "m") ) {
                            bgcolor = new Color(1.0f, 0.0f, 1.0f);
                        } else if ( (propertyValue == "cyan")    || (propertyValue == "c") ) {
                            bgcolor = new Color(0.0f, 1.0f, 1.0f);
                        } else if ( (propertyValue == "red")     || (propertyValue == "r") ) {
                            bgcolor = new Color(1.0f, 0.0f, 0.0f);
                        } else if ( (propertyValue == "green")   || (propertyValue == "g") ) {
                            bgcolor = new Color(0.0f, 1.0f, 0.0f);
                        } else if ( (propertyValue == "blue")    || (propertyValue == "b") ) {
                            bgcolor = new Color(0.0f, 0.0f, 1.0f);
                        } else if ( (propertyValue == "white")   || (propertyValue == "w") ) {
                            bgcolor = new Color(1.0f, 1.0f, 1.0f);
                        } else if ( (propertyValue == "black")   || (propertyValue == "k") ) {
                            bgcolor = new Color(0.0f, 0.0f, 0.0f);
                        } else if ((propertyValue.indexOf( "[")*propertyValue.indexOf( "]")) < 0) {
                            String colorString  = propertyValue.replaceAll("\\[", "").replaceAll("\\]", "");
                            String[] colorPart  = colorString.split("[ ]");
                            float r             = Float.parseFloat(colorPart[0]);
                            float g             = Float.parseFloat(colorPart[1]);
                            float b             = Float.parseFloat(colorPart[2]);
                            bgcolor = new Color(r, g, b);
                        }
                        break;

                    case "Name" :
                        if (propertyValue != "") {
                            name = propertyValue;
                        }

                        break;

                    case "NumberTitle" :
                        if (propertyValue == "false") {
                            numberTitle = false;
                        } else if (propertyValue == "true") {
                            numberTitle = true;
                        }

                        break;

                    case "Visible" :
                        if (propertyValue == "false") {
                            visible = false;
                        } else if (propertyValue == "true") {
                            visible = true;
                        }

                        break;

                    /** Axes and Plot Appearance */
                    case "Renderer" :
                        if (propertyValue == "gral") {
                            renderer = Renderer.gral;
                        }

                        break;

                    /** Location and Size */
                    case "Position" :
                        if (propertyValue != "") {
                            position = propertyValue;
                        }

                        break;

                    case "Resize" :
                        if (propertyValue == "on") {
                            resize = true;
                        } else if (propertyValue == "off") {
                            resize = false;
                        }

                        break;

                    default :
                        break;
                }
            }
        }
    }

    protected void setProperties() {

        /** Figure Appearance */

        /* Color */
        if(bgcolor != null){
            setBackground(bgcolor);
            getContentPane().setBackground(bgcolor);
        }

        /* Name & Number Title */
        String title = "";

        if (numberTitle) {
            title = "Figure " + id;

            if (name != "") {
                title = title + " - ";
            }
        }

        if (name != "") {
            title = title + name;
        }

        setTitle(title);

        /* Visible */
        setVisible(visible);

        /** Axes and Plot Appearance */

        /* Renderer */

        // ToDo: well, we yet just have gral...

        /* Position */
        position = position.replaceAll("\\[", "").replaceAll("\\]", "");

        String[] positionString = position.split("[ ]");
        int      left           = Integer.parseInt(positionString[0]);
        int      bottom         = Integer.parseInt(positionString[1]);
        int      width          = Integer.parseInt(positionString[2]);
        int      height         = Integer.parseInt(positionString[3]);

        setLocation(left, bottom);
        setSize(width, height);

        /* Resize */
        setResizable(resize);
        setLocationRelativeTo(null);
    }
}