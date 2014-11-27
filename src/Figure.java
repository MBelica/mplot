package edu.kit.math.mplot;


import javax.swing.JFrame;


class Figure extends JFrame {


    Figure () {

        super();
        setVisible(true);
        setSize(600, 400);
    }

    Figure (String... propertyVarArgs) {

        super();

        //parseProperties();

        setVisible(true);
        setSize(600, 400);
    }

    private void parseProperties () {

    }

    /**
    Figure Appearance

    Color — Figure window background color ## possible
        RGB triplet | short name | long name | 'none'
    DockControls — Interactive figure docking ## not possible
        'on' (default) | 'off'
    MenuBar — Figure menu bar display  ## dont know... which things blabla
        'figure' (default) | 'none'
    Name — Figure window title  ## possible
        '' (default) | string
    NumberTitle — Figure window title number  ## possible
        'on' (default) | 'off'
    ToolBar — Figure toolbar display  ## dont know... which things blabla
        'auto' (default) | 'figure' | 'none'
    Visible — Figure visibility  ## possible
        'on' (default) | 'off'
    Clipping — Clipping of child components to figure ## WHAT??? :D
    '   on' (default) | 'off'


    Axes and Plot Appearance

    GraphicsSmoothing — Axes graphics smoothing # don't know
        'on' (default) | 'off'
    Renderer — Rendering method used for screen display and printing ## storing plot library!!!!
        'opengl' (default) | 'painters'
    RendererMode — Renderer selection # don't know
        'auto' (default) | 'manual'


    Color and Transparency Mapping

    Alphamap — Transparency map for axes content of figure  # don't know
        array of 64 values from 0 to 1 (default) | array of finite alpha values from 0 to 1
    Colormap — Color map for axes content of figure  # don't know
        parula (default) | m-by-3 array of RGB triplets


    Location and Size

    Position — Location and size of figure's drawable area  # possible
        [left bottom width height]
    OuterPosition — Location and size of figure's outer bounds  # possible
        [left bottom width height]
    Units — Units of measurement ## guess its possible
        'pixels' (default) | 'normalized' | 'inches' | 'centimeters' | 'points' | 'characters'
    Resize — Window resize mode ## possible & easy  setResizable(false);
        'on' (default) | 'off'
    SizeChangedFcn — Window resize callback function # too long to read now
        '' (default) | function handle | cell array | string
    ResizeFcn — Figure resize callback function # too complex for now
        '' (default) | function handle | cell array | string


    Multiple Plots

    NextPlot — Directive on how to add next plot
        'add' (default) | 'new' | 'replace' | 'replacechildren'


    Interactive Control

    Selected — Selection status of figure
        'off' (default) | 'on'
    SelectionHighlight — Ability of figure to highlight when user selects it
        'on' (default) | 'off'


    Callback Execution Control

    BusyAction — Callback queuing
        'queue' (default) | 'cancel'
    Interruptible — Callback interruption
        'on' (default) | 'off'
    HitTest — Ability to become current object
        'on' (default) | 'off'


    Keyboard Control

    CurrentCharacter — Last key pressed in figure
        '' (default) | 1-character string
    KeyPressFcn — Key-press callback function
        '' (default) | function handle | cell array | string
    KeyReleaseFcn — Key-release callback function
        '' (default) | function handle | cell array | string
    WindowKeyPressFcn — Key-press callback function for the figure window
        '' (default) | function handle | cell array | string
    WindowKeyReleaseFcn — Key-release callback function for the figure window
        '' (default) | function handle | cell array | string


    Mouse Control

    ButtonDownFcn — Button-press callback function
        '' (default) | function handle | cell array | string
    CurrentPoint — Location of last button click in figure
        two-element vector: [x-coordinate, y-coordinate]
    SelectionType — Mouse selection type
        'normal' (default) | 'extend' | 'alt' | 'open'
    WindowButtonDownFcn — Button press callback function
        '' (default) | function handle | cell array | string
    WindowButtonMotionFcn — Mouse-motion callback function
        '' (default) | function handle | cell array | string
    WindowButtonUpFcn — Button-release callback function
        '' (default) | function handle | cell array | string
    WindowScrollWheelFcn — Mouse-scroll-wheel callback
        '' (default) | function handle | cell array | string
    UIContextMenu — Figure context menu
        empty GraphicsPlaceholder array (default) | uicontextmenu object


    Window Control

    CloseRequestFcn — Figure close request callback function
        '' (default) | function handle | cell array | string
    WindowStyle — Figure window behavior
        'normal' (default) | 'modal' | 'docked'


    Creation and Deletion Control

    BeingDeleted — Deletion status of figure
        'off' (default) | 'on'
    CreateFcn — Figure creation function
        function handle | cell array | string
    DeleteFcn — Figure deletion function
        function handle | cell array | string

    Identifiers

    CurrentAxes — Target axes in current figure
        axes object
    CurrentObject — Most recently selected component in figure
        object
    FileName — GUI FIG-file name
        string
    IntegerHandle — Ability to assign figure number
        'on' (default) | 'off'
    Number — Figure number
        integer | []
    Tag — Figure identifier
        ' ' (default) | string
    Type — Type of Figure object
        'figure'
    UserData — Data to associate with the figure object
        empty array (default) | array


    Parent/Child

    Parent — Figure parent
        root object
    Children — Children of figure
        empty GraphicsPlaceholder array (default) | 1-D array of objects
    HandleVisibility — Visibility of figure handle
        'on' (default) | 'callback' | 'off'


    Pointers

    Pointer — Pointer symbol
        'arrow' (default) | string
    PointerShapeCData — Pointer shape
        16-by-16 matrix
    PointerShapeHotSpot — Active area of pointer
        [1 1] — upper-left corner (default) | two-element vector


    Printing

    PaperSize — Size of the current PaperType
        [width height]
    PaperUnits — Output measurement units
        'inches' | 'centimeters' | 'normalized' | 'points'
    PaperOrientation — Figure orientation on printed page
        'portrait' (default) | 'landscape'
    PaperPosition — Figure location and size on page
        [left bottom width height]
    PaperPositionMode — Directive for honoring PaperPosition property
        'manual' (default) | 'auto'
    PaperType — Standard paper size selection
        string
    InvertHardcopy — Directive to use black on white background when printing or exporting
        'on' (default) | 'off'
     **/
}
