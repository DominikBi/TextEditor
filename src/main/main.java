package src.main;


import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;


public class main{
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    JTextArea textArea = new JTextArea();
    JTextField name = new JTextField();
    JButton saveButton = new JButton("Save");
    JComboBox box = new JComboBox();
 
    public static void main(String[] args) {
        main main = new main();
        main.start();

    }
    public void start(){
       name.setPreferredSize(new Dimension(100,30));
        panel.add(name, Component.TOP_ALIGNMENT);
        panel.add(saveButton,Component.CENTER_ALIGNMENT);
        panel.add(textArea, Component.BOTTOM_ALIGNMENT);
        panel.add(box,Component.BOTTOM_ALIGNMENT);
        frame.add(panel);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        textArea.setPreferredSize(new Dimension(frame.getWidth()-30,Toolkit.getDefaultToolkit().getScreenSize().height/2));
        frame.setVisible(true);
        for(int i = 4; i< 72; i+=4){
            box.addItem(i);
        }
        box.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                textArea.setFont(new Font("MyFont",Font.PLAIN,Integer.valueOf(e.getItem().toString())));
            }
        });
    }
    public void save(String filename){
        String home = System.getProperty("user.home");
        String sep = System.getProperty("file.separator");
        File file = new File(home + sep + filename + ".txt");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(textArea.getText().getBytes());
            fos.flush();
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
