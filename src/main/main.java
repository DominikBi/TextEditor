package src.main;


import com.sun.org.apache.xpath.internal.operations.Equals;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;


public class main{
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    JFrame expFrame = new JFrame();
    JColorChooser colorChooser = new JColorChooser();
    JTextArea textArea = new JTextArea();
    int textSize = textArea.getFont().getSize();
    String fileLocation = "";
    String oldLocation;
    File open;
    int size;
    int type;
    ArrayList<File> recentOpens = new ArrayList<File>();
    Font currentFont = new Font("mainFont", Font.PLAIN,13);
    JMenuBar menuBar = new JMenuBar();
    JMenu menu = new JMenu("File");
 
    public static void main(String[] args) {
        main main = new main();
        main.start();


    }
    public void changeColor(){
        JFrame frame = new JFrame("Change Color");
        JPanel panel = new JPanel();


        panel.add(colorChooser);
        frame.add(panel);

        frame.setSize(400,400);
        frame.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-200,Toolkit.getDefaultToolkit().getScreenSize().height/2-200);

        frame.setVisible(true);
    }
    public void close(){
        final JFrame frame = new JFrame("Close");
        this.frame.setTitle("");
        JPanel panel = new JPanel();
        JTextField textField = new JTextField("Do you really want to close youre file?");
        JButton buttonYes = new JButton("Yes");
        JButton buttonNo = new JButton("No");
        textField.setEditable(false);
        textField.setPreferredSize(new Dimension(260,40));
        panel.add(textField);
        panel.add(buttonNo);
        panel.add(buttonYes);
        frame.add(panel);
        frame.setSize(300,300);
        frame.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-150,Toolkit.getDefaultToolkit().getScreenSize().height/2-150);
        frame.setVisible(true);
        buttonYes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.setText("");
                frame.setVisible(false);
            }
        });
        buttonNo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
            }
        });

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

                textArea.setFont(new Font("MyFont", currentFont.getStyle(), textSize));
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
        System.out.println(fileLocation + fileLocation.equals(""));

            JPanel panel = new JPanel();
            final File[] file = File.listRoots();
            final JFileChooser fileChooser = new JFileChooser();
            fileChooser.accept(file[0]);
            fileChooser.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getActionCommand() == "ApproveSelection") {

                        open = fileChooser.getSelectedFile();
                        fileLocation = open.getPath();
                        frame.setTitle(open.getName());
                        expFrame.setVisible(false);
                        read(open);

                    }


                }
            });

            panel.add(fileChooser);
            expFrame.add(panel);
            expFrame.setSize(550, 400);
            expFrame.setVisible(true);
        }
    public void saveAs(){
        oldLocation = fileLocation;
        fileLocation = "";
        save();
    }
    public void read(File file) {
        char c;
        String end = "";
        String s = "";
        String t = "";
        try {
            FileInputStream fis = new FileInputStream(file);
            while (fis.available() > 0) {
                c = (char) fis.read();
                //System.out.println(c);
                if (c == '%') {
                    c = (char) fis.read();
                    while (!(c == '%')) {

                        s += c;
                        c = (char) fis.read();

                    }
                    c = (char) fis.read();
                }
                 if (c == '$') {
                     c = (char) fis.read();

                    while (!(c == '$')) {
                        System.out.println(c);

                        t += c;
                        c = (char) fis.read();

                }
                    c = (char) fis.read();


                }
                end += c;
            }
            System.out.println(t + " : " + s);
                type =Integer.valueOf(t);
                size = Integer.valueOf(s);
        } catch (FileNotFoundException e) {
            JPopupMenu popupMenu = new JPopupMenu();
            popupMenu.add("You cant open this file!");
            popupMenu.setVisible(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
        textArea.setText(end);
        Font f = new Font("My font", type,size);
        currentFont = f;
        textArea.setFont(currentFont);
    }
    public void changeToItalic(){
        if(!textArea.getFont().isItalic()) {
            currentFont = new Font("Italic", Font.ITALIC, textSize);
            System.out.println(currentFont.getStyle());
            textArea.setFont(currentFont);
        }else{
            currentFont = new Font("plain", Font.PLAIN,textSize);
            textArea.setFont(currentFont);
        }
    }
    public void changeToBold(){
        if(!textArea.getFont().isBold()){
            currentFont = new Font("Bold", Font.BOLD, textSize);
            System.out.println(currentFont.getStyle());
        textArea.setFont(currentFont);
    }else{
        currentFont =    new Font("plain", Font.PLAIN,textSize);
        textArea.setFont(currentFont);
    }
    }
    public void Menu(){
        JMenuItem saveAs = new JMenuItem("Save As");
        JMenuItem color = new JMenuItem("Change Color");
        JMenuItem open = new JMenuItem("Open");
        JMenuItem close = new JMenuItem("Close");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem size = new JMenuItem("Modify size");
        JMenu changeStyle = new JMenu("Change Text Style");
        JMenuItem italic = new JMenuItem("Italic");
        JMenuItem bold = new JMenuItem("Bold");
        JMenu modify = new JMenu("Modify");

        save.setAccelerator(KeyStroke.getKeyStroke('S',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() )   );
        italic.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeToItalic();
            }
        });
        bold.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeToBold();
            }
        });
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
                save();
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
        saveAs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveAs();
            }
        });
        changeStyle.add(italic);
        changeStyle.add(bold);
        menu.add(open);
        menu.add(close);
        menu.add(save);
        menu.add(saveAs);
        modify.add(changeStyle);
        modify.add(size);
        modify.add(color);
        menuBar.add(menu);
        menuBar.add(modify);
        frame.setJMenuBar(menuBar);

    }
    public void start(){
        Menu();
        panel.add(textArea, Component.BOTTOM_ALIGNMENT);
        frame.add(panel);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        textArea.setPreferredSize(new Dimension(frame.getWidth()-30,Toolkit.getDefaultToolkit().getScreenSize().height/2));
        frame.setVisible(true);
    }
    public void save(){
        if(fileLocation.equals("")) {
            final JFrame frame = new JFrame();
            JPanel panel = new JPanel();
            final JFileChooser fileChooser = new JFileChooser();
            fileChooser.setApproveButtonText("Save");
            fileChooser.setApproveButtonMnemonic(JFileChooser.APPROVE_OPTION);
            panel.add(fileChooser);
            frame.add(panel);
            frame.setSize(550, 400);
            fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
            frame.addWindowListener(new WindowListener() {
                public void windowOpened(WindowEvent e) {

                }

                public void windowClosing(WindowEvent e) {
                    fileLocation = oldLocation;
                    System.out.println("asd");
                }

                public void windowClosed(WindowEvent e) {

                }

                public void windowIconified(WindowEvent e) {

                }

                public void windowDeiconified(WindowEvent e) {

                }

                public void windowActivated(WindowEvent e) {

                }

                public void windowDeactivated(WindowEvent e) {

                }
            });
            fileChooser.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fileLocation = fileChooser.getSelectedFile().getPath();
                    write(fileChooser.getSelectedFile().getPath(), textArea.getText());
                    frame.setVisible(false);
                }
            });
            frame.setVisible(true);
        }else{
            write(fileLocation,textArea.getText());
        }



    }
    public void write(String filename, String text){
        String size = "%" + textSize + "%";
        String type = "$" + currentFont.getStyle() + "$";
        File file = new File(filename + ".txt");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write((size + type + text).getBytes());
            fos.flush();
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
