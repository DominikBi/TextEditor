package src.main;


import java.io.*;
import java.util.ArrayList;

public class MyThread extends Thread{
        String text;
        File name;
        String suffix;
        ArrayList<ModifiedText> modifiedTexts = new ArrayList<>();





    public void setup(String text, File name, String suffix, ArrayList<ModifiedText> modifiedTexts){
        this.text = text;
        this.modifiedTexts = modifiedTexts;
        this.suffix = suffix;
        try {
            this.name = name;
            run();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void run(){
        while (true) {
            File file = name;
            if(!(file == null)) {
                try {

                    sleep(2000);
                    System.out.println(text);
                    try (DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(name))) {
                        dataOutputStream.writeUTF(text);
                        dataOutputStream.writeInt(modifiedTexts.size());
                        for (ModifiedText modifiedText : modifiedTexts) {
                            dataOutputStream.writeShort(modifiedText.start);
                            dataOutputStream.writeShort(modifiedText.len);
                            dataOutputStream.writeInt(modifiedText.color.getRed());
                            dataOutputStream.writeInt(modifiedText.color.getGreen());
                            dataOutputStream.writeInt(modifiedText.color.getBlue());
                            dataOutputStream.writeShort(modifiedText.size);
                            dataOutputStream.writeBoolean(modifiedText.isItalic);
                            dataOutputStream.writeBoolean(modifiedText.isBold);
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }


}
