package edu.kit.math.mplot.modules;

//~--- JDK imports ------------------------------------------------------------

import javax.swing.*;
import java.awt.*;

public class Figure extends JFrame {

    /**
     * Figure properties; for now only some figure appearances
     */
    public int      id;
    public String   name;
    public String   position;
    public Color    bgcolor;
    public boolean  numberTitle, visible, resize;
    public Renderer2d renderer2d;
    public Renderer3d renderer3d;

    protected enum Renderer2d { gral }
    protected enum Renderer3d { jzy3d }

    public Figure() {    }

    public Figure(int id, String name) {
        super();
        getDefaultProperties();
        this.id   = id;
        this.name = name;
        setProperties();
    }

    public Figure(int id, String... propertyVarArgs) {
        super();
        getDefaultProperties();
        this.id = id;
        getProperties(propertyVarArgs);
        setProperties();
    }

    public void resetFigure() {
        // ToDo save and reset original id and name
        getDefaultProperties();
        setProperties();
    }

    private void getDefaultProperties() {
        id          = 0;
        name        = "";
        position    = "[20 20 600 400]";
        numberTitle = true;
        visible     = true;
        resize      = true;
        bgcolor     = Color.WHITE;
        renderer2d  = Renderer2d.gral;
        renderer3d  = Renderer3d.jzy3d;

        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void getProperties(String... propertyVarArgs) {
        if (propertyVarArgs.length > 1) {
            for (int i = 1; i <= propertyVarArgs.length; i += 2) {
                String propertyName  = propertyVarArgs[i - 1];
                String propertyValue = propertyVarArgs[i];

                switch (propertyName) {

                    /** Figure Appearance */
                    case "Color" :
                        if ( (propertyValue.equals("yellow"))  || (propertyValue.equals("y")) ) {
                            bgcolor = new Color(1.0f, 1.0f, 0.0f);
                        } else if ( (propertyValue.equals("magenta")) || (propertyValue.equals("m")) ) {
                            bgcolor = new Color(1.0f, 0.0f, 1.0f);
                        } else if ( (propertyValue.equals("cyan"))    || (propertyValue.equals("c")) ) {
                            bgcolor = new Color(0.0f, 1.0f, 1.0f);
                        } else if ( (propertyValue.equals("red"))     || (propertyValue.equals("r")) ) {
                            bgcolor = new Color(1.0f, 0.0f, 0.0f);
                        } else if ( (propertyValue.equals("green"))   || (propertyValue.equals("g")) ) {
                            bgcolor = new Color(0.0f, 1.0f, 0.0f);
                        } else if ( (propertyValue.equals("blue"))    || (propertyValue.equals("b")) ) {
                            bgcolor = new Color(0.0f, 0.0f, 1.0f);
                        } else if ( (propertyValue.equals("white"))   || (propertyValue.equals("w")) ) {
                            bgcolor = new Color(1.0f, 1.0f, 1.0f);
                        } else if ( (propertyValue.equals("black"))   || (propertyValue.equals("k")) ) {
                            bgcolor = new Color(0.0f, 0.0f, 0.0f);
                        } else if (java.util.regex.Pattern.matches( "(\\[).+(\\s).+(\\s).+(\\])", propertyValue)) {
                            //Todo check if string contains numbers and if numbers are in range of new Color
                            String colorString  = propertyValue.replaceAll("\\[", "").replaceAll("\\]", "");
                            String[] colorPart  = colorString.split("[ ]");
                            float r             = Float.parseFloat(colorPart[0]);
                            float g             = Float.parseFloat(colorPart[1]);
                            float b             = Float.parseFloat(colorPart[2]);
                            bgcolor = new Color(r, g, b);
                        }
                        break;

                    case "Name" :
                        name = propertyValue;
                        break;

                    case "NumberTitle" :
                        if (propertyValue.equals("false")) {
                            numberTitle = false;
                        } else if (propertyValue.equals("true")) {
                            numberTitle = true;
                        }

                        break;

                    case "Visible" :
                        if (propertyValue.equals("false")) {
                            visible = false;
                        } else if (propertyValue.equals("true")) {
                            visible = true;
                        }

                        break;

                    /** Axes and Plot Appearance */
                    case "Renderer" :
                        if (propertyValue.equals("gral")) {
                            renderer2d = Renderer2d.gral;
                        }else if (propertyValue.equals("jzy3d")) {
                            renderer3d = Renderer3d.jzy3d;
                        }
                        break;

                    /** Location and Size */
                    case "Position" :
                        if (java.util.regex.Pattern.matches( "(\\[).+(\\s).+(\\s).+(\\s).+(\\])", propertyValue)) {
                            position = propertyValue;
                        }

                        break;

                    case "Resize" :
                        if (propertyValue.equals("on")) {
                            resize = true;
                        } else if (propertyValue.equals("off")) {
                            resize = false;
                        }

                        break;

                    default :
                        break;
                }
            }
        }
    }

    private void setProperties() {

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

            if (!name.equals("")) {
                title = title + " - ";
            }
        }

        if (!name.equals("")) {
            title = title + name;
        }

        setTitle(title);

        /* Visible */
        setVisible(visible);

        /** Axes and Plot Appearance */

        /* Renderer */

        // well, we yet just have gral and jzy3d

        /* Position */ //Todo check if string contains numbers and if numbers are in range of setLocation und setSize
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
    }
}