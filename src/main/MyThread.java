package src.main;


import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MyThread extends Thread{
    public void run(){
        while(true) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                DataInputStream dataInputStream = new DataInputStream(new FileInputStream(System.getProperty("user.home") + System.getProperty("file.separator") + "TextEditorSettings"));
                while(dataInputStream.readBoolean()){
                    System.out.println("ja");
                    Main main = new Main();
                    main.editor.save();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
