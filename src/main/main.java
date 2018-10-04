package src.main;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class main{
 
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        JTextArea textarea = new JTextArea();
        frame.setLayout(new DorderLayout());
        frame.add(textarea, BorderLayout.CENTER);
        textarea.setPreferredSize(new Dimension(400, 400));
        frame.setSize(frame.getMaxSize());
        frame.setVisibel(true);
}
