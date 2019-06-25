package src.main;

import javax.swing.*;
import javax.swing.text.Style;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.io.File;
import java.io.IOException;

public class Main implements Runnable{


    JFrame frame = new JFrame("Unknown File");
    JPanel panel = new JPanel();
    JFrame expFrame = new JFrame();
    JColorChooser colorChooser = new JColorChooser();
    JMenuBar menuBar = new JMenuBar();
    JMenu menu = new JMenu("File");
    Editor currentEditor;
    JTextPane selectedText = new JTextPane();
    public boolean autoSave;
    Editor editor;
    String programmName = "TextEditor";
    int xCount;
    String paneText;
    Text text = new Text();



    public static void main(String[] args) throws InterruptedException {
        Main main = new Main();
        main.start();
    }

    private void changeColor(){
        final JFrame frame = new JFrame("Change Color");
        JPanel panel = new JPanel();
        JButton button = new JButton("OK");

        panel.add(colorChooser);
        panel.add(button);
        button.addActionListener(e -> {
            editor.setColor(colorChooser.getColor());
            frame.setVisible(false);
        });
        frame.add(panel);
        frame.setSize(400,400);
        frame.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-200,Toolkit.getDefaultToolkit().getScreenSize().height/2-200);

        frame.setVisible(true);
    }

    private void close(){
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
        buttonYes.addActionListener(e -> System.exit(0));
        buttonNo.addActionListener(e -> frame.setVisible(false));

    }
    private void newEditor(){
        Editor editor = new Editor();
        currentEditor = editor;

    }

    private void modifySize(){
        final JFrame frame = new JFrame("Size");
        JPanel panel = new JPanel();
        JComboBox comboBox = new JComboBox();
        JButton button = new JButton("OK");
        for(int i = 2; i< 128; i+=2){
            comboBox.addItem(i);
        }
        button.addActionListener(e -> {
            frame.setVisible(false);
            editor.setSize(Integer.parseInt(comboBox.getSelectedItem().toString()));
   });

        panel.add(comboBox);
        panel.add(button);
        frame.add(panel);
        frame.setSize(250,100);
        frame.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-100,Toolkit.getDefaultToolkit().getScreenSize().height/2-100);
        frame.setVisible(true);
    }
    private void fontFamily(){

    }
    private void settingz(){
        JFrame chooserFrame = new JFrame();
        JPanel panel = new JPanel();

        chooserFrame.setSize(550, 400);
        final JFileChooser chooser = new JFileChooser();
        chooser.setDialogType(JFileChooser.SAVE_DIALOG);
        panel.add(chooser);
        chooser.addActionListener(e -> {
            try {
                //write in selected file
                editor.save(chooser.getSelectedFile());
                chooserFrame.setVisible(false);
                //addActionListener so every time someone writes something it gets saved
                editor.getText().addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        Thread.currentThread().notify();
                        System.out.println("ads");
                        super.keyTyped(e);
                        try {
                            editor.save();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }

                    }
                });
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        chooserFrame.add(panel);
        chooserFrame.setVisible(true);

    }
    private void saveToPrint(){
        JFrame chooserFrame = new JFrame();
        JPanel panel = new JPanel();
        chooserFrame.setSize(550, 400);
        final JFileChooser chooser = new JFileChooser();
        chooser.setDialogType(JFileChooser.SAVE_DIALOG);
        panel.add(chooser);
        chooser.addActionListener(e -> {
            try {
                editor.saveToPrint(chooser.getSelectedFile());
                frame.setTitle(chooser.getSelectedFile().getName());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            chooserFrame.setVisible(false);
        });
        chooserFrame.add(panel);
        chooserFrame.setVisible(true);
    }

    private void explorer(){
        JPanel panel = new JPanel();
        final File[] file = File.listRoots();
        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.accept(file[0]);
        fileChooser.addActionListener(e -> {
            if (e.getActionCommand().equals("ApproveSelection")) {
                try {
                    editor.load(fileChooser.getSelectedFile());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                frame.setTitle(editor.getFile().getName());
                expFrame.setVisible(false);
            }
        });

        panel.add(fileChooser);
        expFrame.add(panel);
        expFrame.setSize(550, 400);
        expFrame.setVisible(true);
    }

    public void menu(){

        JMenuItem settings = new JMenuItem("Settings");
        JMenuItem saveAs = new JMenuItem("Save As");
        JMenuItem color = new JMenuItem("Change Color");
        JMenuItem open = new JMenuItem("Open");
        JMenuItem close = new JMenuItem("Close");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem size = new JMenuItem("Modify size");
        JMenu changeStyle = new JMenu("Change Text Style");
        JMenuItem italic = new JMenuItem("Italic");
        JMenuItem bold = new JMenuItem("Bold");
        JMenuItem underline = new JMenuItem("Underline");
        JMenuItem saveToPrint = new JMenuItem("Save to Print");
        JMenuItem fontFamily = new JMenuItem("Font");
        JMenu modify = new JMenu("Modify");

        save.setAccelerator(KeyStroke.getKeyStroke('S',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() )   );
        italic.addActionListener(e -> editor.setStyle(Font.ITALIC));
        bold.addActionListener(e -> editor.setStyle(Font.BOLD));
        color.addActionListener(e -> changeColor());
        size.addActionListener(e -> modifySize());

        underline.addActionListener(e -> editor.setSize(3));
                save.addActionListener(e -> {
                    if (editor.isLoad()) {
                        save();
                    } else {
                        saveAs();
                    }
                });
        close.addActionListener(e -> close());
        open.addActionListener(e -> explorer());
        saveAs.addActionListener(e -> saveAs());
        saveToPrint.addActionListener(e -> {
            saveToPrint();

        });
        settings.addActionListener(e -> {

            Settings settings1 = new Settings();
            settings1.start();
        });
                changeStyle.add(italic);
        changeStyle.add(bold);
        changeStyle.add(underline);
        open.setAccelerator(KeyStroke.getKeyStroke('O', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        close.setAccelerator(KeyStroke.getKeyStroke('W',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        saveToPrint.setAccelerator(KeyStroke.getKeyStroke('P', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        menu.setMnemonic('F');
        modify.setMnemonic('M');
        menu.add(open);
        menu.add(close);
        menu.add(save);
        menu.add(saveAs);
        menu.add(saveToPrint);
        menu.add(settings);
        modify.add(size);
        modify.add(changeStyle);
        modify.add(color);
        menuBar.add(menu);
        menuBar.add(modify);
        frame.setJMenuBar(menuBar);

    }

    private void saveAs() {
        JFrame chooserFrame = new JFrame();
        JPanel panel = new JPanel();

        chooserFrame.setSize(550, 400);
        final JFileChooser chooser = new JFileChooser();
        chooser.setDialogType(JFileChooser.SAVE_DIALOG);
        panel.add(chooser);
        chooser.addActionListener(e -> {
            try {
                editor.save(chooser.getSelectedFile());
                text.setName(chooser.getSelectedFile());
                chooserFrame.setVisible(false);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        chooserFrame.add(panel);
        chooserFrame.setVisible(true);
    }

    private void start(){

        System.out.println(System.getProperty("user.home"));
        Thread.currentThread().interrupt();
        menu();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        editor = new Editor();
        text.setSuffix("");
        currentEditor = editor;
        editor.getText().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                paneText += e.getKeyChar();
                text.setText(paneText);

            }
        });
        panel.add(editor.getText(), Component.BOTTOM_ALIGNMENT);
        frame.add(panel);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());

        frame.setVisible(true);
    }

    private void save(){
        try {
            editor.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(autoSave){
            editor.getText().addVetoableChangeListener(evt -> {
                save();
            });
        }
    }
}
