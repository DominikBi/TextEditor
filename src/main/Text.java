package src.main;

import java.io.File;

public class Text {
    String text;
    File path;
    String suffix;

    public synchronized File getName() {
        return path;
    }

    public synchronized void setName(File path) {
        this.path = path;
    }

    public synchronized String getSuffix() {
        return suffix;
    }

    public synchronized void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public synchronized String getText(){
        return text;
    }
    public synchronized void setText(String text){
        this.text = text;
    }
}
