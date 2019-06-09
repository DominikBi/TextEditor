package src.main;


import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.MenuListener;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.io.*;


public class main{
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    JTextArea textArea = new JTextArea();
    JTextField name = new JTextField();
    int textSize;
    File open;
    JMenuBar menuBar = new JMenuBar();
    JMenu menu = new JMenu("File");
 
    public static void main(String[] args) {
        main main = new main();
        main.start();


    }
    public void changeColor(){
        JFrame frame = new JFrame("Change Color");
        JPanel panel = new JPanel();
        JColorChooser colorChooser = new JColorChooser();
        colorChooser.addVetoableChangeListener(new VetoableChangeListener() {
            public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
                System.out.println((Color)evt.getNewValue());
                textArea.setCaretColor((Color) evt.getNewValue());
            }
        });
        panel.add(colorChooser);
        frame.add(panel);
        frame.setSize(400,400);
        frame.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-200,Toolkit.getDefaultToolkit().getScreenSize().height/2-200);

        frame.setVisible(true);
    }
    public void close(){
        textArea.setText("");
}
    public void modifySize(){
        final JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        JComboBox comboBox = new JComboBox();
        JButton button = new JButton("OK");
        for(int i = 0; i< 128; i+=2){
            comboBox.addItem(i);
        }
        comboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                textSize = Integer.valueOf(e.getItem().toString());
                textArea.setFont(new Font("MyFont", Font.PLAIN, textSize));
            }
        });
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
            }
        });
        panel.add(comboBox);
        panel.add(button);
        frame.add(panel);
        frame.setSize(200,200);
        frame.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-100,Toolkit.getDefaultToolkit().getScreenSize().height/2-100);
        frame.setVisible(true);
    }
    public void explorer(){
        final JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        File[] file = File.listRoots();
        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.accept(file[0]);
        fileChooser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(fileChooser.getCurrentDirectory());
            }
        });
        fileChooser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(e.getActionCommand());
                if(e.getActionCommand() == "ApproveSelection"){
                    open = fileChooser.getSelectedFile();
                    frame.setVisible(false);
                    read();
                }

            }
        });
        panel.add(fileChooser);
        frame.add(panel);
        frame.setSize(550,400);
        frame.setVisible(true);

    }
    public void read() {
        char c;
        String end = "";
        try {
            FileInputStream fis = new FileInputStream(open);
            while (fis.available() > 0) {
                c = (char) fis.read();
                end += c;
            }
        } catch (FileNotFoundException e) {
            JPopupMenu popupMenu = new JPopupMenu();
            popupMenu.add("You cant open this file!");
            popupMenu.setVisible(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
        textArea.setText(end);
    }
    public void Menu(){
        JMenuItem color = new JMenuItem("Change Color");
        JMenuItem open = new JMenuItem("Open");
        JMenuItem close = new JMenuItem("Close");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem size = new JMenuItem("Modify size");
        JMenu modify = new JMenu("Modify");
        color.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeColor();
            }
        });
        size.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modifySize();
            }
        });
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                save(name.getText());
            }
        });
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                close();
            }
        });
        open.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                explorer();
            }
        });
        menu.add(open);
        menu.add(close);
        menu.add(save);
        modify.add(size);
        modify.add(color);
        menuBar.add(menu);
        menuBar.add(modify);
        frame.setJMenuBar(menuBar);

    }
    public void start(){
        Menu();


       name.setPreferredSize(new Dimension(100,30));
        panel.add(name, Component.TOP_ALIGNMENT);
        panel.add(textArea, Component.BOTTOM_ALIGNMENT);
        frame.add(panel);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        textArea.setPreferredSize(new Dimension(frame.getWidth()-30,Toolkit.getDefaultToolkit().getScreenSize().height/2));
        frame.setVisible(true);
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
