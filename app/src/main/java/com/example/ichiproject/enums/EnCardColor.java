package com.example.ichiproject.enums;

/**
 * Enum dei 4 colori delle carte
 */
public enum EnCardColor {

    YELLOW,
    RED,
    GREEN,
    BLUE,
    NULL;

    public static EnCardColor[] getColors(){
        return new EnCardColor[]{YELLOW,RED,GREEN,BLUE};
    }

}
