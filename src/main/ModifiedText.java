package src.main;

import java.awt.*;

public class ModifiedText {
    int start;
    int len;
    Color color;
    int size;
    boolean isItalic;
    boolean isBold;

    public ModifiedText(int start,int len, Color color, int size, boolean isItalic, boolean isBold){
        this.start = start;
        this.len = len;
        this.color = color;
        this.size = size;
        this.isItalic = isItalic;
        this.isBold = isBold;

    }

}
