package edu.kit.math.mplot;


import javax.swing.JFrame;


class Figure extends JFrame {


    /**
     *  Figure properties; for now only figure appearance
     *  I don't know any more elegant method than this...
     *  Not (yet) possible or don't understand or not necessary now:
     *      DockControls,  MenuBar, ToolBar, Clipping, GraphicsSmoothing, RendererMode, Alphamap, Colormap, Units, SizeChangedFcn, ResizeFcn, OuterPosition
     *  The rest is going to be added... by and by
     **/

    protected int id = 0;
    protected String name = "";

    //  matlabs figure properties
    protected String position;
    protected boolean numberTitle, visible, resize;
    protected enum BGColor { none }; protected BGColor bgcolor;
    protected enum Renderer { gral }; protected Renderer renderer;

    Figure (int id, String name) {

        super();
        this.id = id;
        this.name = name;

        getDefaultProperties();
        setProperties ();
    }

    Figure (int id, String... propertyVarArgs) {

        super();
        this.id = id;

        getDefaultProperties();
        getProperties(propertyVarArgs);
        setProperties ();
    }

    protected void getDefaultProperties () {

        name = "";
        position = "[20 20 600 400]";
        numberTitle = true;
        visible     = true;
        resize      = true;
        bgcolor     = BGColor.none;
        renderer    = Renderer.gral;

    }

    protected void getProperties (String... propertyVarArgs) {

        if (propertyVarArgs.length > 1) {

            for (int i = 1; i <= propertyVarArgs.length; i += 2) {
                String propertyName  = (String) propertyVarArgs[i-1];
                String propertyValue = (String) propertyVarArgs[i];

                switch (propertyName) {

                    /** Figure Appearance **/
                    case "Color":
                        bgcolor = BGColor.none; // ToDo: temporary... this is going to be hard
                        break;
                    case "Name":
                        if (propertyValue != "") name = propertyValue; // ToDo: set name also in GRoot. tag = name??
                        break;
                    case "NumberTitle":
                        if (propertyValue == "false") numberTitle = false;
                            else if (propertyValue == "true") numberTitle = true;
                        break;
                    case "Visible":
                        if (propertyValue == "false") visible = false;
                            else if (propertyValue == "true") visible = true;
                        break;

                     /** Axes and Plot Appearance **/
                    case "Renderer":
                        if (propertyValue == "gral") renderer = Renderer.gral;
                        break;

                     /** Location and Size **/
                    case "Position":
                        if (propertyValue != "") position = propertyValue; // ToDo: set name also in GRoot. tag = name??
                        break;
                    case "Resize":
                        if (propertyValue == "on") resize = true;
                            else if (propertyValue == "off") resize = false;
                        break;

                    default:
                        break;
                }
            }
        }
    }

    protected void setProperties () {

        /** Figure Appearance **/

        /* Color */
        switch (bgcolor) {  // ToDo: thats gonna be work
            default:
                break;
        }

        /* Name & Number Title  */
        String title = "";
        if (numberTitle) {
            title = "Figure " + id;
            if (name != "") title = title + " - ";
        }
        if (name != "") title = title + name;
        setTitle(title);

        /* Visible */
        setVisible(visible);

        /** Axes and Plot Appearance **/

        /* Renderer */
                            // ToDo: well, we yet just have gral...

        /* Position */
        position = position.replaceAll("\\[", "").replaceAll("\\]","");
        String[] positionString = position.split("[ ]");
            int left    = Integer.parseInt(positionString[0]);
            int bottom  = Integer.parseInt(positionString[1]);
            int width   = Integer.parseInt(positionString[2]);
            int height  = Integer.parseInt(positionString[3]);

        setLocation(left, bottom);
        setSize(width, height);

        /* Resize */
        setResizable( resize );
    }
}
