package edu.kit.math.mplot;


class Utilities {


    protected enum outputStyle { console, file, none };

    static final outputStyle output      = outputStyle.console;
    static final outputStyle debugOutput = outputStyle.console;


    protected static void echo (String ouputText) {

        switch (output) {

            case console:
                System.out.println( ouputText );
                break;
            case file: // ToDo
                break;
            case none:
                break;
            default:
                break;
        }
    }

    protected static void debugEcho (String debugText) {

        switch (output) {

            case console:
                System.out.println( debugText );
                break;
            case file: // ToDo
                break;
            case none:
                break;
            default:
                break;
        }
    }

    protected static void debugEchoGRoot (GRootManager groot) {

        debugEcho( groot.GRootListToString() );
    }

}




