package src.main;

import java.awt.*;

public class ModifiedText {
    int start;
    int len;
    Color color;
    int size;
    boolean isItalic;
    boolean isBold;
    boolean isUnderlined;
    float spaceBelow;


    public ModifiedText(int start,int len, Color color, int size, boolean isItalic, boolean isBold,float spaceBelow, boolean isUnderlined){
        this.start = start;
        this.len = len;
        this.color = color;
        this.size = size;
        this.isItalic = isItalic;
        this.isBold = isBold;
        this.spaceBelow = spaceBelow;
        this.isUnderlined = isUnderlined;

    }

}
